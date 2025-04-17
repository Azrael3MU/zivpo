package com.example.lab1.util;
import org.springframework.stereotype.Component;
import java.security.*;

@Component
public class SignatureCrypto {
    private final KeyPair keyPair;
    public SignatureCrypto() throws NoSuchAlgorithmException {
        KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
        g.initialize(2048);
        keyPair = g.generateKeyPair();
    }
    public byte[] sign(byte[] data) throws GeneralSecurityException {
        Signature s = Signature.getInstance("SHA256withRSA");
        s.initSign(keyPair.getPrivate()); s.update(data); return s.sign();
    }
    public boolean verify(byte[] data, byte[] sign) throws GeneralSecurityException {
        Signature s = Signature.getInstance("SHA256withRSA");
        s.initVerify(keyPair.getPublic()); s.update(data); return s.verify(sign);
    }
}
