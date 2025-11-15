import org.mindrot.jbcrypt.BCrypt;
import java.util.Base64;

public class Security {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String password, String storedHash) {
        return BCrypt.checkpw(password, storedHash);
    }


    public static String encodeEmail(String email) {
        return Base64.getEncoder().encodeToString(email.getBytes());
    }

    public static String decodeEmail(String encodedEmail) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedEmail);
        return new String(decodedBytes);
    }
}