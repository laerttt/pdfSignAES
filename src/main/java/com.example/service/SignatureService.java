package com.example.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import javax.security.auth.x500.X500Principal;

public class SignatureService {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    public X509Certificate generateSelfSignedCertificate(KeyPair keyPair) throws Exception {
        Date startDate = new Date();
        Date endDate = new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000); // 1 year

        X500Principal dnName = new X500Principal("CN=NASA, O=KelvinEnterprise, C=US");
        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA").build(keyPair.getPrivate());

        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                dnName, BigInteger.valueOf(startDate.getTime()), startDate, endDate, dnName, keyPair.getPublic());

        return new JcaX509CertificateConverter().setProvider("BC").getCertificate(certBuilder.build(contentSigner));
    }

    public byte[] signPdf(String inputPdfPath) throws Exception {
        KeyPair keyPair = generateKeyPair();
        X509Certificate cert = generateSelfSignedCertificate(keyPair);
        PrivateKey privateKey = keyPair.getPrivate();

        try (PDDocument document = PDDocument.load(new FileInputStream(inputPdfPath));
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            PDSignature signature = new PDSignature();
            signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
            signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
            signature.setName("Test User");
            signature.setLocation("Test Location");
            signature.setReason("Document Signing with PDFBox and Bouncy Castle");
            signature.setSignDate(Calendar.getInstance());

            document.addSignature(signature, content -> {
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    byte[] hash = md.digest(content.readAllBytes());

                    Signature signer = Signature.getInstance("SHA256withRSA");
                    signer.initSign(privateKey);
                    signer.update(hash);
                    return signer.sign();
                } catch (Exception e) {
                    throw new IOException("Error while signing the content", e);
                }
            });

            document.saveIncremental(output);
            return output.toByteArray();
        }
    }
}
