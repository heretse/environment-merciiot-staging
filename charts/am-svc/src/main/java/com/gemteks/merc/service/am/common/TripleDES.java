package com.gemteks.merc.service.am.common;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class TripleDES {

    private static final String Algorithm = "DESede";
    private static final String Transformation = "DESede/CBC/PKCS5Padding";
    private static final byte[] SaltedPrefix = "Salted__".getBytes(StandardCharsets.ISO_8859_1);
    private static final int SaltSize = 8;
    private static final int KeySize = 24;
    private static final int IvSize = 8;
    private static final int IterationCount = 1;

    public static String encrypt(String message, String password) throws Exception {

        final SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance(Algorithm);
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        // generate random salt
        byte[] salt = new byte[SaltSize];
        new Random().nextBytes(salt);

        // generate key
        byte[][] keyAndIv = EVP_BytesToKey(KeySize, IvSize, md5, salt, password.getBytes(StandardCharsets.UTF_8), IterationCount);
        DESedeKeySpec keySpec = new DESedeKeySpec(keyAndIv[0]);
        SecretKey key = KeyFactory.generateSecret(keySpec);
        IvParameterSpec iv = new IvParameterSpec(keyAndIv[1]);
        Cipher cipher = Cipher.getInstance(Transformation);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        // encrypt
        byte[] cipherData = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

        byte[] saltedCipherData = new byte[SaltedPrefix.length + SaltSize + cipherData.length];
        ByteBuffer buf = ByteBuffer.wrap(saltedCipherData);
        buf.put(SaltedPrefix).put(salt).put(cipherData);

        // base64 encode
        byte[] cipherMessage = Base64.getEncoder().encode(saltedCipherData);
        String out = new String(cipherMessage);;

        return out;
    }

    public static String decrypt(String message, String password) throws Exception {

        final SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance(Algorithm);
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        // base64 decode
        byte[] str2byte = Base64.getDecoder().decode(message);
        byte[] salt = Arrays.copyOfRange(str2byte, SaltedPrefix.length, SaltedPrefix.length + SaltSize);

        // generate key
        byte[][] keyAndIv = EVP_BytesToKey(KeySize, IvSize, md5, salt, password.getBytes(StandardCharsets.UTF_8), IterationCount);
        DESedeKeySpec keySpec = new DESedeKeySpec(keyAndIv[0]);
        SecretKey key = KeyFactory.generateSecret(keySpec);
        IvParameterSpec iv = new IvParameterSpec(keyAndIv[1]);
        Cipher cipher = Cipher.getInstance(Transformation);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        byte[] cipherData = Arrays.copyOfRange(str2byte, SaltedPrefix.length + SaltSize, str2byte.length);
        String out = new String(cipher.doFinal(cipherData));

        return out;
    }

    public static byte[][] EVP_BytesToKey(int key_len, int iv_len, MessageDigest md, byte[] salt, byte[] data, int count) {

        byte[][] both = new byte[2][];
        byte[] key = new byte[key_len];
        int key_ix = 0;
        byte[] iv = new byte[iv_len];
        int iv_ix = 0;
        both[0] = key;
        both[1] = iv;
        byte[] md_buf = null;
        int nkey = key_len;
        int niv = iv_len;
        int i = 0;
        if (data == null) {
            return both;
        }
        int addmd = 0;
        for (;;) {
            md.reset();
            if (addmd++ > 0) {
                md.update(md_buf);
            }
            md.update(data);
            if (null != salt) {
                md.update(salt, 0, 8);
            }
            md_buf = md.digest();
            for (i = 1; i < count; i++) {
                md.reset();
                md.update(md_buf);
                md_buf = md.digest();
            }
            i = 0;
            if (nkey > 0) {
                for (;;) {
                    if (nkey == 0)
                        break;
                    if (i == md_buf.length)
                        break;
                    key[key_ix++] = md_buf[i];
                    nkey--;
                    i++;
                }
            }
            if (niv > 0 && i != md_buf.length) {
                for (;;) {
                    if (niv == 0)
                        break;
                    if (i == md_buf.length)
                        break;
                    iv[iv_ix++] = md_buf[i];
                    niv--;
                    i++;
                }
            }
            if (nkey == 0 && niv == 0) {
                break;
            }
        }
        for (i = 0; i < md_buf.length; i++) {
            md_buf[i] = 0;
        }

        return both;
    }
}
