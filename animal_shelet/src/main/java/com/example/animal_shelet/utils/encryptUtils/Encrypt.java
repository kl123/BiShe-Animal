package com.example.animal_shelet.utils.encryptUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class Encrypt {
    // 密钥长度(字节) - AES-256
    private static final int KEY_LENGTH = 32;
    // IV长度(字节)
    private static final int IV_LENGTH = 16;
    // 算法名称
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    /**
     * 加密身份证号码
     *
     * @param idCard 身份证号码
     * @param secretKey 密钥(32字节)
     * @return Base64编码的加密字符串
     * @throws Exception 加密异常
     */
    public static String encryptIdCard(String idCard, byte[] secretKey) throws Exception {
        // 生成随机IV
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        // 创建密钥规格
        SecretKeySpec keySpec = new SecretKeySpec(secretKey, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // 初始化加密器
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        // 加密数据
        byte[] encrypted = cipher.doFinal(idCard.getBytes(StandardCharsets.UTF_8));

        // 组合IV和加密数据
        byte[] ivAndEncrypted = new byte[IV_LENGTH + encrypted.length];
        System.arraycopy(iv, 0, ivAndEncrypted, 0, IV_LENGTH);
        System.arraycopy(encrypted, 0, ivAndEncrypted, IV_LENGTH, encrypted.length);

        // 返回Base64编码结果
        return Base64.getEncoder().encodeToString(ivAndEncrypted);
    }

    /**
     * 解密身份证号码
     *
     * @param encryptedIdCard Base64编码的加密字符串
     * @param secretKey 密钥(32字节)
     * @return 原始身份证号码
     * @throws Exception 解密异常
     */
    public static String decryptIdCard(String encryptedIdCard, byte[] secretKey) throws Exception {
        // 解码Base64
        byte[] ivAndEncrypted = Base64.getDecoder().decode(encryptedIdCard);

        // 分离IV和加密数据
        byte[] iv = new byte[IV_LENGTH];
        byte[] encrypted = new byte[ivAndEncrypted.length - IV_LENGTH];
        System.arraycopy(ivAndEncrypted, 0, iv, 0, IV_LENGTH);
        System.arraycopy(ivAndEncrypted, IV_LENGTH, encrypted, 0, encrypted.length);

        // 创建密钥规格
        SecretKeySpec keySpec = new SecretKeySpec(secretKey, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // 初始化解密器
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        // 解密数据
        byte[] decrypted = cipher.doFinal(encrypted);

        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * 从字符串生成安全密钥(仅用于演示，生产环境应使用更安全的密钥管理)
     */
    public static byte[] generateKeyFromPassword(String password) {
        // 实际应用中应使用PBKDF2等密钥派生函数
        byte[] key = new byte[KEY_LENGTH];
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);

        // 简单填充(仅演示用，生产环境需更安全的方法)
        for (int i = 0; i < KEY_LENGTH; i++) {
            key[i] = passwordBytes[i % passwordBytes.length];
        }
        return key;
    }
}
