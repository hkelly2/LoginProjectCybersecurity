import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private static final String FILE_NAME = "users.txt";
    private static Map<String, User> users = new HashMap<>();

    // Load users from file
    public static void load() {
        users.clear();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Each line: username,passwordHash,email
                String[] parts = line.split(",", 3); // limit 3 in case PII has commas
                if (parts.length == 3) {
                    String username = parts[0];
                    String passwordHash = parts[1];
                    String email = parts[2];
                    users.put(username, new User(username, passwordHash, email));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save all users to file
    public static void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : users.values()) {
                writer.write(user.username + "," + user.passwordHash + "," + user.email);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check if username exists
    public static boolean usernameExists(String username) {
        return users.containsKey(username);
    }

    // Add new user
    public static void addUser(User user) {
        users.put(user.username, user);
        save();
    }

    // Get user by username
    public static User getUser(String username) {
        return users.get(username);
    }
}