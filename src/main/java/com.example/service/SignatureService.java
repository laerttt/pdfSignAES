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
        // Add BouncyCastleProvider for enhanced security and cryptographic functions
        Security.addProvider(new BouncyCastleProvider());
    }

    // Method to generate an RSA KeyPair for signing the PDF
    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // Initialize key size to 2048 bits
        return keyGen.generateKeyPair();
    }

    // Method to generate a self-signed X509 certificate using the provided KeyPair
    public X509Certificate generateSelfSignedCertificate(KeyPair keyPair) throws Exception {
        Date startDate = new Date(); // Certificate start date (current date)
        Date endDate = new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000); // 1 year validity

        X500Principal dnName = new X500Principal("CN=NASA, O=KelvinEnterprise, C=US");
        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA").build(keyPair.getPrivate());

        // Build the certificate using Bouncy Castle's certificate builder
        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                dnName, BigInteger.valueOf(startDate.getTime()), startDate, endDate, dnName, keyPair.getPublic());

        // Convert and return the certificate
        return new JcaX509CertificateConverter().setProvider("BC").getCertificate(certBuilder.build(contentSigner));
    }

    // Method to digitally sign a PDF file located at the given path
    public byte[] signPdf(String inputPdfPath) throws Exception {
        KeyPair keyPair = generateKeyPair();
        X509Certificate cert = generateSelfSignedCertificate(keyPair);
        PrivateKey privateKey = keyPair.getPrivate();

        try (PDDocument document = PDDocument.load(new FileInputStream(inputPdfPath));
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            // Configure PDF signature details
            PDSignature signature = new PDSignature();
            signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
            signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
            signature.setName("Test User"); // Name of the signer
            signature.setLocation("Test Location"); // Location of signing
            signature.setReason("Document Signing with PDFBox and Bouncy Castle"); // Reason for signing
            signature.setSignDate(Calendar.getInstance()); // Set the signature date to current date

            // Add the signature to the document
            document.addSignature(signature, content -> {
                try {
                    // Hash the content to be signed using SHA-256
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    byte[] hash = md.digest(content.readAllBytes());

                    // Sign the hashed content using RSA with SHA-256
                    Signature signer = Signature.getInstance("SHA256withRSA");
                    signer.initSign(privateKey);
                    signer.update(hash);
                    return signer.sign();
                } catch (Exception e) {
                    throw new IOException("Error while signing the content", e);
                }
            });

            // Save the signed document incrementally to the output stream
            document.saveIncremental(output);
            return output.toByteArray(); // Return the signed PDF as a byte array
        }
    }
}