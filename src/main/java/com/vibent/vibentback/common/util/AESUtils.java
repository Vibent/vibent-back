package com.vibent.vibentback.common.util;

import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

@Component
public class AESUtils implements InitializingBean {
    @Value("${vibent.aes.key}")
    private String KEY;

    @Value("${vibent.aes.ivVector}")
    private String IV_VECTOR;

    private SecretKey secretKey;
    private IvParameterSpec iv;

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM_INSTANCE = "AES";
    private static final String ENCODING = "utf-8";

    @Override
    public void afterPropertiesSet() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_INSTANCE);
        keyGenerator.init(new SecureRandom(KEY.getBytes(ENCODING)));
        secretKey = keyGenerator.generateKey();
        iv = new IvParameterSpec(IV_VECTOR.getBytes(ENCODING));
    }

    public String encrypt(String... data) {
        return encrypt(Strings.join(Arrays.asList(data), ' '));
    }

    public String encrypt(String data) {
        try {
            Cipher encryptCipher = Cipher.getInstance(ALGORITHM);
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] cipherText = encryptCipher.doFinal(data.getBytes(ENCODING));
            String token = new String(Base64.encodeBase64(cipherText), ENCODING);
            // Make string URL safe
            token = token.replaceAll("\\+", "-");
            token = token.replaceAll("/", "_");
            // Remove padding for shorter token
            while (token.endsWith("=")) {
                token = token.substring(0, token.length() - 1);
            }
            return token;
        } catch (BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new VibentException(VibentError.FAILED_TO_CREATE_TOKEN);
        }
    }

    public String decrypt(String token) {
        try {
            Cipher decryptCipher = Cipher.getInstance(ALGORITHM);
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            // Change URL Safe String to initial text
            token = token.replaceAll("-", "+");
            token = token.replaceAll("_", "/");
            byte[] bytes = Base64.decodeBase64(token.getBytes(ENCODING));
            return new String(decryptCipher.doFinal(bytes));
        } catch (BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new VibentException(VibentError.MALFORMED_TOKEN);
        }
    }
}