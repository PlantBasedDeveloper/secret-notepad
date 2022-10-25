package com.challenge.secretnotepad;

import com.challenge.secretnotepad.core.cryptography.CryptographyService;
import com.challenge.secretnotepad.core.cryptography.RSACryptographyService;
import com.challenge.secretnotepad.exceptions.DecryptionException;
import com.challenge.secretnotepad.exceptions.EncryptionException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CryptographyServiceTests {

    public static final String MSG1 = "ABCD def.öäü#123!";
    public static final String MSG2 = "ialmcWYFjYMgKoUcK8xQICL2u0husSsqSgUrFcCZfNJ0Xq19h76Xmxcr3DPv839Ydb0vDgeXjSVq8JfM hDt7IPtzKhanQ2L0SE3LiPB7vIcK4Jwq8GlDhqq6inrrx6Vy2uoKUpgAXqJy5hiQhEBp0DH863sSM9bzRs8Nqx0t2Enpu3lMHhSd1p9QZoGvNrq156IEv5E0MQugMQ2kClFdAiogOlg2CCtK3mUVgHaVonhmHS2KrZziy1VvnfKRb4hPOSaSnRwjGEwrxkUjsopmujD7xfuvc8jLaÜstHJFgKmqzUKrki5lyNmPBxAkwYuLÄN783wBctLB0BL42CäMQwM3k82dG6xZwoOz2OEToXKXsq1hy8LbjsRmf10x9 so21CLWv4MHgrC8qrZ OAb3rwyuAUTD69XqcMPU4X9lQwhi2hESGRsX8N kOboYJOaof3cvWWaA0WJ p0Gj32H0OwIch5rVaPiPW8609V3i45QV49DX7NnP";

    private CryptographyService service;

    @Before
    public void init() {
        service = new RSACryptographyService();
    }

    @Test
    public void RSACryptographyServiceTest() {
        try {
            String result1 = encryptDecrypt(MSG1);
            String result2 = encryptDecrypt(MSG2);
            Assert.assertEquals(MSG1, result1);
            Assert.assertEquals(MSG2, result2);
        } catch (EncryptionException | DecryptionException e) {
            e.printStackTrace();
        }
    }

    private String encryptDecrypt(String s) throws EncryptionException, DecryptionException {
        String encMsg = service.encrypt(s);
        return service.decrypt(encMsg);
    }
}
