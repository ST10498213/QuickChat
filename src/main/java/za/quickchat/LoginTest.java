package za.quickchat;

public class LoginTest {

    public static void main(String[] args) {

        Login login = new Login();

        boolean registered =
                login.registerUser("Glen123", "Pass123");

        System.out.println("Registration: " + registered);

        boolean correctLogin =
                login.loginUser("Glen123", "Pass123");

        System.out.println("Correct login: " + correctLogin);

        boolean wrongLogin =
                login.loginUser("Glen123", "WrongPass");

        System.out.println("Wrong password: " + wrongLogin);
    }
}