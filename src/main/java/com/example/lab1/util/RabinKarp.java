package com.example.lab1.util;

public class RabinKarp {
    private static final long BASE = 256;
    private static final long MOD  = 1000000007L;
    private final int windowSize;
    private final long basePow;
    public RabinKarp(int windowSize){
        this.windowSize = windowSize;
        long p=1;
        for(int i=1;i<windowSize;i++) p = (p*BASE)%MOD;
        this.basePow = p;
    }
    public long initialHash(byte[] buf,int start){
        long h=0;
        for(int i=0;i<windowSize;i++)
            h = (h*BASE + (buf[start+i] & 0xFF)) % MOD;
        return h;
    }
    public long roll(long prev, byte left, byte add){
        long without = (prev - ((left & 0xFF)*basePow)%MOD + MOD)%MOD;
        return (without*BASE + (add & 0xFF)) % MOD;
    }
    public long hashBytes(byte[] b){
        long h=0;
        for(byte x: b) h=(h*BASE + (x&0xFF))%MOD;
        return h;
    }
}
