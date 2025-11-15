import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database.load();

        while (true) {
            System.out.println("1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    signup();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void signup() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (Database.usernameExists(username)) {
            System.out.println("Sorry, that username already exists. :(");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        String hash = Security.hashPassword(password);
        email = Security.encodeEmail(email);

        User user = new User(username, hash, email);
        Database.addUser(user);

        System.out.println("Signup successful!");
    }

     private static void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        User user = Database.getUser(username);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (Security.verifyPassword(password, user.passwordHash)) {
            System.out.println("Login successful!");
            String decryptedPII = Security.decodeEmail(user.email);
            System.out.println("Your email: " + decryptedPII);
        } else {
            System.out.println("Incorrect password.");
        }
    }
}