package za.quickchat;

public class Login {

    private String registeredUsername;
    private String registeredPassword;

    public boolean registerUser(String username, String password) {

        if (username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {
            return false;
        }

        registeredUsername = username.trim();
        registeredPassword = password;

        return true;
    }

    public boolean loginUser(String username, String password) {

        if (registeredUsername == null || registeredPassword == null) {
            return false;
        }

        return registeredUsername.equals(username.trim())
                && registeredPassword.equals(password);
    }
}