package com.vibent.vibentback.common;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.common.util.AESUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AESUtilsTest extends VibentTest {

    @Autowired
    AESUtils aesUtils;

    @Before
    public void setUp() {
        super.setUp();
    }

    /**
     * Test all token sizes possible to ensure algorithm is functional
     */
    @Test
    public void encryptDecryptSimpleTest() throws UnsupportedEncodingException {
        // Use bad data to ensure that it is recognized as false and ciphers still work
        boolean foundError = false;
        try {
            aesUtils.decrypt("not a token");
        } catch (Exception e) {
            foundError = true;
        }
        Assert.assertTrue(foundError);

        String plainText = "";
        for (int i = 0; i < 100; i++) {
            plainText += "x";
            String cipherText = aesUtils.encrypt(plainText);
            log.info("{} mm", cipherText.getBytes("utf-8").length);
            Assert.assertEquals(plainText, aesUtils.decrypt(cipherText));
        }
    }
}
