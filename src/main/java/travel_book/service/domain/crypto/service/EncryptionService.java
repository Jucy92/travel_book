package travel_book.service.domain.crypto.service;

import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

@Service
public class EncryptionService {
    private static final String ALGORITHM = "AES";
    private static final String KEY = "mysecretkey12345"; // 실제 사용 시 안전하게 관리해야 합니다

    public String encrypt(String data) {
        try {
            /*
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
            */

            /*
            */
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException e) {

            throw new EncryptionException("암호화 알고리즘 초기화 실패", e);
        } catch (InvalidKeyException /*| InvalidAlgorithmParameterException*/ e) {
            throw new EncryptionException("잘못된 암호화 키", e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new EncryptionException("데이터 암호화 실패", e);
        }
    }

    public String decrypt(String encryptedData) {
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM, "BC");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException e) {
            throw new RuntimeException("암호화 알고리즘 초기화 실패", e);       // 여긴 직접 런타임 던짐
        } catch (InvalidKeyException e) {
            throw new RuntimeException("잘못된 암호화 키", e);                // 여긴 직접 런타임 던짐
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("데이터 암호화 실패", e);              // 여긴 직접 런타임 던짐
        }
    }

    public class EncryptionException extends RuntimeException {
        public EncryptionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
