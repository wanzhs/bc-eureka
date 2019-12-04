package com.ga.com.eureka.provider.controller;

import org.bouncycastle.crypto.generators.SCrypt;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class TestCryptController {
    public static void main(String[] args) throws Exception {
        testSCrypt1();
    }

    public static void testBCrypt1() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hasedPwd = bCryptPasswordEncoder.encode("123");
        bCryptPasswordEncoder.matches("123", hasedPwd);
        // Hash a password for the first time
        String password = "testpassword";
        String salt = BCrypt.gensalt();
        System.out.println(salt);
        String hashed = BCrypt.hashpw(password, salt);
        System.out.println(hashed);
        // gensalt's log_rounds parameter determines the complexity
        // the work factor is 2**log_rounds, and the default is 10
//        String hashed2 = BCrypt.hashpw(password, BCrypt.gensalt(12));
//        System.out.println(hashed2);
        // Check that an unencrypted password matches one that has
        // previously been hashed
        String candidate = "testpassword";
        System.out.println("hashed:" + hashed);
        System.out.println("second:" + BCrypt.hashpw(password, hashed));
        System.out.println(BCrypt.hashpw(password, BCrypt.hashpw(password, hashed)).equals(hashed));
        //String candidate = "wrongtestpassword";
        if (BCrypt.checkpw(candidate, hashed)) {
            System.out.println("It matches");
        } else {
            System.out.println("It does not match");
        }
    }

    public static void testSCrypt1() {
        String password = "123456";
//        String salt = BCrypt.gensalt();
        String salt = "Cdd1A4rpAimBR/9JbYLBpe";
        int n = 8, r = 4, p = 100, dkLen = 16;
        byte[] hashBytes = SCrypt.generate(password.getBytes(), salt.getBytes(), n, r, p, dkLen);
        String hash = DatatypeConverter.printHexBinary(hashBytes);
        System.out.println(hash);

        SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder();
        String hash1 = sCryptPasswordEncoder.encode(password);
        System.out.println("hash1:" + hash1);
        System.out.println(sCryptPasswordEncoder.matches(password, hash1));
    }

    public static void testPBKDF2() throws Exception {
        String password = "123456";
        String salt = PBKDF2.getSalt();
        String hash = PBKDF2.getPBKDF2(password, salt);
        System.out.println("salt:" + salt);
        System.out.println("hash:" + hash);
        System.out.println(PBKDF2.verify(password, salt, hash));
    }

    public static class PBKDF2 {
        public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

        //盐的长度
        public static final int SALT_SIZE = 16;

        //生成密文的长度
        public static final int HASH_SIZE = 32;

        // 迭代次数
        public static final int PBKDF2_ITERATIONS = 1000;

        /**
         * 对输入的密码进行验证
         */
        public static boolean verify(String password, String salt, String key)
                throws NoSuchAlgorithmException, InvalidKeySpecException {
            // 用相同的盐值对用户输入的密码进行加密
            String result = getPBKDF2(password, salt);
            // 把加密后的密文和原密文进行比较，相同则验证成功，否则失败
            return result.equals(key);
        }

        /**
         * 根据password和salt生成密文
         */
        public static String getPBKDF2(String password, String salt) throws NoSuchAlgorithmException,
                InvalidKeySpecException {
            //将16进制字符串形式的salt转换成byte数组
            byte[] bytes = DatatypeConverter.parseHexBinary(salt);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), bytes, PBKDF2_ITERATIONS, HASH_SIZE * 4);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            byte[] hash = secretKeyFactory.generateSecret(spec).getEncoded();
            //将byte数组转换为16进制的字符串
            return DatatypeConverter.printHexBinary(hash);
        }


        /**
         * 生成随机盐值
         */
        public static String getSalt() throws NoSuchAlgorithmException {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = new byte[SALT_SIZE / 2];
            random.nextBytes(bytes);
            //将byte数组转换为16进制的字符串
            String salt = DatatypeConverter.printHexBinary(bytes);
            return salt;
        }
    }
}
