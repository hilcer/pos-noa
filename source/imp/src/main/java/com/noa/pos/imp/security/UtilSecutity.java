package com.noa.pos.imp.security;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;

import java.io.*;
import java.nio.charset.Charset;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.logging.Level;

public class UtilSecutity {

    private static final JcaPEMKeyConverter CONVERTER = new JcaPEMKeyConverter();

    public static PrivateKey stringToPrivKeyPEMParser(String privKey, String passKeyPriv) throws Exception {
        PEMParser pemParser = new PEMParser(new StringReader(privKey));
        Object keyPair = pemParser.readObject();
        PEMDecryptorProvider decryptionProv = (new JcePEMDecryptorProviderBuilder()).build(passKeyPriv.toCharArray());
        PrivateKeyInfo keyInfo;
        if (keyPair instanceof PEMEncryptedKeyPair) {
            PEMKeyPair decryptedKeyPair = ((PEMEncryptedKeyPair) keyPair).decryptKeyPair(decryptionProv);
            keyInfo = decryptedKeyPair.getPrivateKeyInfo();
        } else {
            keyInfo = (PrivateKeyInfo) keyPair;
        }

        pemParser.close();
        return CONVERTER.getPrivateKey(keyInfo);

    }

    public static X509Certificate getX509Certificate(Object certificadoDigital) throws Exception {
        if (certificadoDigital == null) {
            throw new Exception("Cert not found");
        } else {
            Object characterStream;
            if (certificadoDigital instanceof Clob) {
                characterStream = ((Clob) certificadoDigital).getCharacterStream();
            } else if (certificadoDigital instanceof CharSequence) {
                characterStream = new StringReader(certificadoDigital.toString());
            } else {
                if (!(certificadoDigital instanceof String)) {
                    throw new IllegalArgumentException("tipo " + certificadoDigital.getClass() + " no soportado");
                }

                characterStream = new StringReader((String) certificadoDigital);
            }

            X509Certificate certificate = null;

                certificate = obtainCertificate((Reader) characterStream);
                return certificate;
        }
    }

    public static X509Certificate obtainCertificate(Reader initialReader) throws CertificateException, NoSuchProviderException, IOException {
        X509Certificate certificate = null;
        CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
        InputStream in = toInputStream(initialReader);
        certificate = (X509Certificate)cf.generateCertificate(in);
        return certificate;
    }

    public static InputStream toInputStream(Reader initialReader) throws IOException {
        char[] charBuffer = new char[8192];
        StringBuilder builder = new StringBuilder();

        int numCharsRead;
        while((numCharsRead = initialReader.read(charBuffer, 0, charBuffer.length)) != -1) {
            builder.append(charBuffer, 0, numCharsRead);
        }

        InputStream targetStream = new ByteArrayInputStream(builder.toString().getBytes(Charset.forName("UTF-8")));
        initialReader.close();
        return targetStream;
    }

}
