package com.vibent.vibentback.common.util;

import com.amazonaws.util.Base64;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class AESUtils implements InitializingBean {
    @Value("${vibent.aes.key")
    private String KEY;

    @Value("${vibent.aes.ivVector}")
    private String IV_VECTOR;

    private Cipher encryptCipher;
    private Cipher decryptCipher;

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM_INSTANCE = "AES";

    @Override
    public void afterPropertiesSet() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_INSTANCE);
        keyGenerator.init(new SecureRandom(KEY.getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        IvParameterSpec iv = new IvParameterSpec(IV_VECTOR.getBytes());
        encryptCipher = Cipher.getInstance(ALGORITHM);
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        decryptCipher = Cipher.getInstance(ALGORITHM);
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
    }

    public String encrypt(String data) {
        try {
            String base64 = Base64.encodeAsString(encryptCipher.doFinal(data.getBytes()));
            base64 = base64.replaceAll("/", "-");
            return base64;
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new VibentException(VibentError.FAILED_TO_CREATE_TOKEN);
        }
    }

    public String decrypt(String encryptedToken) {
        try {
            encryptedToken = encryptedToken.replaceAll("-", "/");
            byte[] bytes = Base64.decode(encryptedToken);
            return new String(decryptCipher.doFinal(bytes));
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new VibentException(VibentError.MALFORMED_TOKEN);
        }
    }
}