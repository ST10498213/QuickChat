package za.quickchat;

import java.util.Scanner;

public class QuickChat {

    public static void main(String[] args) {
        Message.loadStoredMessages();

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
            System.out.println("4) Stored Messages");

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
                            
                            Message.sentMessages.add(msg.printMessage());
                            
                            messagesSent++;
                        } else if (messageChoice == 2) {
    System.out.println(msg.sendMessage(2));
    
    Message.disregardedMessages.add(msg.printMessage());
    
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
                    System.out.println("Thank you for using QuickChat.");
                    running = false;
                    break;
                
                case 4:
    System.out.println("\nStored Messages Menu");
    System.out.println("1) Display all stored messages");
    System.out.println("2) Display longest stored message");
    System.out.println("3) Search message by ID");
    System.out.println("4) Search messages by recipient");
    System.out.println("5) Delete message by hash");
    System.out.println("6) Display full message report");
    System.out.println("7) Back to main menu");

    System.out.print("Choose an option: ");
    int storedChoice = input.nextInt();
    input.nextLine();

    switch (storedChoice) {
        case 1:
    if (Message.storedMessages.isEmpty()) {
        System.out.println("No stored messages available.");
    } else {
        System.out.println("\nAll Stored Messages:");

        for (String storedMessage : Message.storedMessages) {
            System.out.println(storedMessage);
            System.out.println("-------------------------");
        }
    }
    break;
        case 2:
    if (Message.storedMessages.isEmpty()) {
        System.out.println("No stored messages available.");
    } else {
        String longestMessage = Message.storedMessages.get(0);

        for (String storedMessage : Message.storedMessages) {
            if (storedMessage.length() > longestMessage.length()) {
                longestMessage = storedMessage;
            }
        }

        System.out.println("\nLongest Stored Message:");
        System.out.println(longestMessage);
        System.out.println("Characters: " + longestMessage.length());
    }
    break;
        case 3:
    System.out.print("Enter message ID to search: ");
    String searchID = input.nextLine();

    boolean found = false;

    for (int i = 0; i < Message.storedMessageIDs.size(); i++) {
        if (Message.storedMessageIDs.get(i).equals(searchID)) {
            System.out.println("Message found:");
            System.out.println(Message.storedMessages.get(i));
            found = true;
            break;
        }
    }

    if (!found) {
        System.out.println("Message ID not found.");
    }
    break;
        case 4:
    System.out.print("Enter recipient number to search: ");
    String searchRecipient = input.nextLine();
    boolean recipientFound = false;

    for (String storedMessage : Message.storedMessages) {
        if (storedMessage.contains(searchRecipient)) {
            System.out.println("Message found:");
            System.out.println(storedMessage);
            System.out.println("-------------------------");
            recipientFound = true;
        }
    }

    if (!recipientFound) {
        System.out.println("No messages found for that recipient.");
    }
    break;        
        case 5:
    System.out.print("Enter message hash to delete: ");
    String deleteHash = input.nextLine();
    boolean deleted = false;

    for (int i = 0; i < Message.storedMessageIDs.size(); i++) {
        String storedID = Message.storedMessageIDs.get(i);
        String matchingHash = "";

        for (int j = 0; j < Message.messageIDs.size(); j++) {
            if (Message.messageIDs.get(j).equals(storedID)) {
                matchingHash = Message.messageHashes.get(j);
                break;
            }
        }

        if (matchingHash.equals(deleteHash)) {
            if (Message.removeFromJsonFile(storedID)) {
                Message.storedMessages.remove(i);
                Message.storedMessageIDs.remove(i);

                for (int j = 0; j < Message.messageIDs.size(); j++) {
                    if (Message.messageIDs.get(j).equals(storedID)) {
                        Message.messageIDs.remove(j);
                        Message.messageHashes.remove(j);
                        break;
                    }
                }

                System.out.println("Message deleted successfully.");
                deleted = true;
            } else {
                System.out.println("Message could not be deleted from file.");
            }
            break;
        }
    }

    if (!deleted) {
        System.out.println("Message hash not found.");
    }
    break;
        case 6:
    if (Message.storedMessages.isEmpty()) {
        System.out.println("No stored messages available.");
    } else {
        System.out.println("\nFULL MESSAGE REPORT");
        System.out.println("========================");

        for (int i = 0; i < Message.storedMessages.size(); i++) {
            String storedID = Message.storedMessageIDs.get(i);

            System.out.println("Message ID: " + storedID);

            for (int j = 0; j < Message.messageIDs.size(); j++) {
                if (Message.messageIDs.get(j).equals(storedID)) {
                    System.out.println("Message Hash: "
                            + Message.messageHashes.get(j));
                    break;
                }
            }

            System.out.println(Message.storedMessages.get(i));
            System.out.println("-------------------------");
        }
    }
    break;
        case 7:
            break;
        default:
            System.out.println("Invalid option.");
    }
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