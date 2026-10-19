package za.quickchat;

import java.util.Scanner;

public class QuickChat {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int messagesSent = 0;

        System.out.println("Welcome to QuickChat.");

        Login login = new Login();

        System.out.print("Enter username to register: ");
        String username = input.nextLine();

        System.out.print("Enter password to register: ");
        String password = input.nextLine();

        if (login.registerUser(username, password)) {
            System.out.println("Registration successful.");
        } else {
            System.out.println("Registration failed.");
            input.close();
            return;
        }

        System.out.print("Enter username to login: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter password to login: ");
        String loginPassword = input.nextLine();

        if (login.loginUser(loginUsername, loginPassword)) {

            System.out.println("Login successful.");
            boolean running = true;
            
            while (running){

            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");

            System.out.print("Choose an option: ");
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:
                    System.out.print(
                            "How many messages do you want to send? ");
                    int totalMessages = input.nextInt();
                    input.nextLine();

                    for (int i = 1; i <= totalMessages; i++) {

                        System.out.println("\nMessage " + i);

                        System.out.print(
                                "Enter recipient phone number: ");
                        String recipient = input.nextLine();

                        System.out.print("Enter your message: ");
                        String messageText = input.nextLine();

                        Message msg = new Message(
                                String.format("%010d", i),
                                i,
                                recipient,
                                messageText
                        );

                        if (!msg.checkRecipientCell()) {
                            System.out.println(
                                    "Invalid recipient number.");
                            continue;
                        }

                        if (messageText.length() > 250) {
                            System.out.println(
                                    msg.checkMessageLength());
                            continue;
                        }

                        System.out.println(
                                "Recipient number is valid.");
                        System.out.println(
                                "Message length is valid.");

                        System.out.println("\nMessage details:");
                        System.out.println(msg.printMessage());

                        System.out.println("\nChoose an option:");
System.out.println("1) Send Message");
System.out.println("2) Discard Message");
System.out.println("3) Store Message");

                        System.out.print("Enter choice: ");
                        int messageChoice = input.nextInt();
                        input.nextLine();

                        if (messageChoice == 1) {
                            System.out.println(msg.sendMessage(1));
                            messagesSent++;
                        } else if (messageChoice == 2) {
    System.out.println(msg.sendMessage(2));
} else if (messageChoice == 3) {

    if (msg.storeMessage()) {
        System.out.println("Message successfully stored.");
    } else {
        System.out.println("Message could not be stored.");
    }
} else {
    System.out.println("Invalid choice.");
}
                    }

                    System.out.println(
                            "Total messages sent: " + messagesSent);
                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    System.out.println(
                            "Thank you for using QuickChat.");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
                    break;
            }

            }
        } else {
            System.out.println("Login failed.");
        }

        input.close();
    }
}