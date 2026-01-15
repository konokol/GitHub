package com.github.utils;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * crypto utils
 *
 * @author  Ivan on 2018-12-06 23:33.
 * @version v0.1
 * @since   v0.1.0
 */
public class CryptoUtils {

    public static SecretKey genSecretKey(String secret) {
        byte[] salt = new byte[32];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        KeySpec keySpec = new PBEKeySpec(secret.toCharArray(), salt, 1000, 256);
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] keyBytes = new byte[0];
        try {
            if (keyFactory != null) {
                keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
            }
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(keyBytes, "AES");
    }

    public static String AESEncrypt(String key, String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            byte[] contentBytes;
            contentBytes = content.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedBytes = new byte[0];
            try {
                encryptedBytes = cipher.doFinal(contentBytes);
            } catch (BadPaddingException | IllegalBlockSizeException e) {
                e.printStackTrace();
            }
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
//            return java.util.Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String AESDecrypt(String key, String encryptedContent) {
        if (encryptedContent == null || encryptedContent.isEmpty()) {
            return encryptedContent;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
            SecretKey secretKeySpec = genSecretKey(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            byte[] contentBytes = Base64.decode(encryptedContent.getBytes(), Base64.DEFAULT);
//            byte[] contentBytes = java.util.Base64.getDecoder().decode(encryptedContent.getBytes());
            try {
                byte[] decryptedBytes = cipher.doFinal(contentBytes);
                return new String(decryptedBytes);
            } catch (BadPaddingException | IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return encryptedContent;
    }

}
