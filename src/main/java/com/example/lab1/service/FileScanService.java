package com.example.lab1.service;

import com.example.lab1.dto.SignatureScanResult;
import com.example.lab1.entity.Signature;
import com.example.lab1.repository.SignatureRepository;
import com.example.lab1.util.RabinKarp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileScanService {
    private final SignatureRepository repo;
    private static final int WINDOW=8;
    private static final int BUFFER=8192;

    public List<SignatureScanResult> scan(MultipartFile mf) throws Exception{
        File temp = Files.createTempFile("upload-",".bin").toFile();
        mf.transferTo(temp);

        List<Signature> sigs = repo.findByStatus(Signature.Status.ACTUAL);
        if(sigs.isEmpty()){ temp.delete(); return List.of(); }

        RabinKarp rk=new RabinKarp(WINDOW);
        Map<Long,List<Signature>> map=new HashMap<>();
        for(Signature s: sigs){
            byte[] fb=hexToBytes(s.getFirstBytes());
            map.computeIfAbsent(rk.hashBytes(fb),k->new ArrayList<>()).add(s);
        }

        List<SignatureScanResult> res=new ArrayList<>();
        try(RandomAccessFile raf=new RandomAccessFile(temp,"r")){
            long fileLen=raf.length();
            if(fileLen<WINDOW){ return res; }
            byte[] buf=new byte[BUFFER+WINDOW];
            int read=raf.read(buf,0,BUFFER+WINDOW);
            long offset=0;
            RabinKarp r=new RabinKarp(WINDOW);
            long cur=r.initialHash(buf,0);
            int pos=WINDOW;
            while(true){
                List<Signature> cand=map.get(cur);
                if(cand!=null){
                    for(Signature s:cand){
                        if(match(raf,offset,s)){
                            res.add(toResult(s,offset,fileLen));
                        }
                    }
                }
                if(pos>=read){
                    if(offset+pos>=fileLen) break;
                    System.arraycopy(buf,pos-WINDOW,buf,0,WINDOW);
                    int n=raf.read(buf,WINDOW,BUFFER);
                    read = WINDOW + (n==-1?0:n);
                    pos=WINDOW;
                }
                byte left=buf[pos-WINDOW];
                byte add=buf[pos];
                cur=r.roll(cur,left,add);
                pos++; offset++;
            }
        }
        temp.delete();
        return res;
    }

    private boolean match(RandomAccessFile raf,long offset,Signature s) throws Exception{
        byte[] fb=hexToBytes(s.getFirstBytes());
        byte[] temp=new byte[fb.length];
        raf.seek(offset);
        raf.readFully(temp);
        if(!Arrays.equals(temp,fb)) return false;
        int tailLen=s.getRemainderLength();
        byte[] tail=new byte[tailLen];
        raf.readFully(tail);
        MessageDigest md=MessageDigest.getInstance("SHA-256");
        String hex=bytesToHex(md.digest(tail));
        return hex.equalsIgnoreCase(s.getRemainderHash());
    }
    private SignatureScanResult toResult(Signature s,long offset,long fileLen){
        return SignatureScanResult.builder()
                .signatureId(s.getId())
                .threatName(s.getThreatName())
                .offsetFromStart(offset)
                .offsetFromEnd(offset+8+s.getRemainderLength())
                .matched(true).build();
    }
    private static byte[] hexToBytes(String hex){
        int len=hex.length(); byte[] out=new byte[len/2];
        for(int i=0;i<len;i+=2) out[i/2]=(byte)Integer.parseInt(hex.substring(i,i+2),16);
        return out;
    }
    private static String bytesToHex(byte[] b){
        StringBuilder sb=new StringBuilder();
        for(byte x:b) sb.append(String.format("%02x",x));
        return sb.toString();
    }
}
