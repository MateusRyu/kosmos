import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class PasswordHasher {
    private static final String ALGORITHM = "SHA-256";
    private static final int ITERATIONS = 100000;
    private static final int SALT_LENGTH = 9;

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        byte[] salt = generateSalt();
        byte[] finalHash = performHash(password, salt, ITERATIONS);

        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(finalHash);

        return saltBase64 + "_" + ITERATIONS + "_" + hashBase64;
    }


    public static boolean verifyPassword(String password, String storedHash) throws NoSuchAlgorithmException {
        String[] parts = storedHash.split("_");
        if (parts.length != 3) {
            return false;
        }

        byte[] salt = Base64.getDecoder().decode(parts[0]);
        int iterations = Integer.parseInt(parts[1]);
        byte[] expectedHash = Base64.getDecoder().decode(parts[2]);

        byte[] actualHash = performHash(password, salt, iterations);

        return Arrays.equals(expectedHash, actualHash);
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    private static byte[] performHash(String password, byte[] salt, int iterations) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM);

        digest.reset();
        digest.update(salt);
        byte[] hash = digest.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));

        for (int i = 0; i < iterations - 1; i++) {
            digest.reset();
            hash = digest.digest(hash);
        }

        return hash;
    }
}