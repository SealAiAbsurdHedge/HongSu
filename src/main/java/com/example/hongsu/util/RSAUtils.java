package com.example.hongsu.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


/**
 * 非对称加密工具
 * 
 * @author lKF71480
 */
public class RSAUtils {
    /**
     * PublicKey4UP:用户中心公钥匙
     */
    public static Map<String, Object> keyMap = new HashMap<String, Object>();

    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成公钥和私钥
     * 
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * 
     */
    public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException, NoSuchProviderException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        map.put("public", publicKey);
        map.put("private", privateKey);
        return map;
    }

    /**
     * 使用模和指数生成RSA公钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     * 
     * @param modulus
     *            模
     * @param exponent
     *            指数
     * @return
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static RSAPublicKey getPublicKey(X509EncodedKeySpec publicKey) {
        try {
            KeyFactory keyfactoryNew = KeyFactory.getInstance("RSA", "BC");
            return  (RSAPublicKey) keyfactoryNew.generatePublic(publicKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用模和指数生成RSA私钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     * @param modulus 模
     * @param exponent 指数
     * @return
     */
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公钥加密
     * 
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 模长
        int key_len = publicKey.getModulus().bitLength() / 8;
        // 加密数据长度 <= 模长-11
        String[] datas = splitString(data, key_len - 11);
        String mi = "";
        // 如果明文长度大于模长-11则要分组加密
        for (String s : datas) {
            mi += bcd2Str(cipher.doFinal(s.getBytes()));
        }
        return mi;
    }

    /**
     * 私钥解密
     * 
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // 模长
        int key_len = privateKey.getModulus().bitLength() / 8;
        byte[] bytes = data.getBytes();
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
        System.err.println(bcd.length);
        // 如果密文长度大于模长则要分组解密
        String ming = "";
        byte[][] arrays = splitArray(bcd, key_len);
        for (byte[] arr : arrays) {
            ming += new String(cipher.doFinal(arr));
        }
        return ming;
    }
    
    /**
     * 公钥解密
     * 
     * @param data
     * @param: privateKey
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        // 模长
        int key_len = publicKey.getModulus().bitLength() / 8;
        byte[] bytes = data.getBytes();
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
        System.err.println(bcd.length);
        // 如果密文长度大于模长则要分组解密
        String ming = "";
        byte[][] arrays = splitArray(bcd, key_len);
        for (byte[] arr : arrays) {
            ming += new String(cipher.doFinal(arr));
        }
        return ming;
    }
    /**
     * <p>
     * 解密
     * </P>
     * <功能详细描述>
     * 
     * @return 解密后的明文
     * @see [类、类#方法、类#成员]
     */
    public static String decrypt(String cryptStr) {
        return null;
    }

    /**
     * ASCII码转BCD码
     * 
     */
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    public static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }

    /**
     * BCD转字符串
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 拆分字符串
     */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     * 拆分数组
     */
    public static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }
    
    public static String getUpPrivateEncodeStr (String encodeStr) {
        String result = "";
        try {
            RSAPrivateKey priKey = null;
            if (keyMap.get("PrivateKey4UP") == null) {
                String rootPath = StringUtils.remove(Thread.currentThread().getContextClassLoader().getResource("").getPath(), "classes/");
                String localPath = rootPath + File.separator + "keystore";
                File privateKeyFile = new File(localPath + File.separator + "private.key");
                FileInputStream privateKeyInputStream = new FileInputStream(privateKeyFile);
                byte[] privateKeyByte = new byte[(int) privateKeyFile.length()];
                // 将私钥文件读入内存
                privateKeyInputStream.read(privateKeyByte);
                privateKeyInputStream.close();
                // 生成私钥钥对象privateKey
                PKCS8EncodedKeySpec priPKCS8new = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyByte));
                KeyFactory keyfactoryNew = KeyFactory.getInstance("RSA", "BC");
                priKey = (RSAPrivateKey) keyfactoryNew.generatePrivate(priPKCS8new);
                keyMap.put("PrivateKey4UP", priKey);
            } else {
                priKey = (RSAPrivateKey) keyMap.get("PrivateKey4UP");
            }
            // 私钥解密
            result = RSAUtils.decryptByPrivateKey(encodeStr, priKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getUpPublicEncodeStr(String str) {
        // 公钥加密
        String result = "";
        try {
            RSAPublicKey pubKey = null;
            if (keyMap.get("PublicKey4UP") == null){
                // 公钥文件
                String rootPath = StringUtils.remove(Thread.currentThread().getContextClassLoader().getResource("").getPath(), "classes/");
                String localPath = rootPath + File.separator + "keystore";
                File publicKeyFile = new File(localPath + File.separator + "publicUp.key");
                Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                FileInputStream publicKeyInputStream = new FileInputStream(publicKeyFile);
                byte[] publicKeyByte = new byte[(int) publicKeyFile.length()];
                // 将公钥文件读入内存
                publicKeyInputStream.read(publicKeyByte);
                publicKeyInputStream.close();
                // 生成公钥对象PublicKey
                X509EncodedKeySpec pubX509new = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyByte));
                KeyFactory keyfactoryNew = KeyFactory.getInstance("RSA", "BC");
                pubKey = (RSAPublicKey) keyfactoryNew.generatePublic(pubX509new);
                keyMap.put("PublicKey4UP", pubKey);
            } else {
                pubKey = (RSAPublicKey)keyMap.get("PublicKey4UP");
            }
            result = RSAUtils.encryptByPublicKey(str, pubKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取秘钥对文件
     * @param args
     * @throws Exception
     */
    public static void makeThePairFile(String[] args) throws Exception {

        /*HashMap<String, Object> map = RSAUtils.getKeys();
        // 生成公钥和私钥
        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
        // 生成公钥文件
        File publicKeyFile = new File("D:" + File.separator + "public.key");
        FileOutputStream publicKeyStream = new FileOutputStream(publicKeyFile);
        // 获取公钥标准编码并进行base64加密
        String publicKeyBase = new String(Base64.encodeBase64(publicKey.getEncoded()));
        System.out.println("公钥为：" + new String(publicKey.getEncoded()));
        System.out.println("BASE64公钥为：" + publicKeyBase);
        publicKeyStream.write(publicKeyBase.getBytes());
        publicKeyStream.close();
        //
        // // // 生成私钥文件
        File privateKeyFile = new File("D:" + File.separator + "private.key");
        FileOutputStream privateKeyStream = new FileOutputStream(privateKeyFile);
        // 获取私钥标准编码并进行base64加密
        String privateKeyBase = new String(Base64.encodeBase64(privateKey.getEncoded()));
        System.out.println("私钥为：" + new String(privateKey.getEncoded()));
        System.out.println("BASE64私钥为：" + privateKeyBase);
        privateKeyStream.write(privateKeyBase.getBytes());
        privateKeyStream.close();

        X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyBase.getBytes()));
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyBase.getBytes()));

        KeyFactory keyfactory = KeyFactory.getInstance("RSA");
        RSAPublicKey newPublicKey = (RSAPublicKey) keyfactory.generatePublic(pubX509);
        RSAPrivateKey newPrivateKey = (RSAPrivateKey) keyfactory.generatePrivate(priPKCS8);
        System.out.println(publicKey.equals(newPublicKey));
        System.out.println(privateKey.equals(newPrivateKey));*/
    }
}