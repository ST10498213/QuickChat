package za.quickchat;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class Message {
    
    public static ArrayList<String>sentMessages = new ArrayList<>();
    public static ArrayList<String>disregardedMessages = new ArrayList<>();
    public static ArrayList<String>storedMessages = new ArrayList<>();
    public static ArrayList<String>messageHashes = new ArrayList<>();
    public static ArrayList<String>messageIDs = new ArrayList<>();
    public static ArrayList<String>storedMessageIDs = new ArrayList<>();

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    public Message(String messageID, int messageNumber,
               String recipient, String message) {

    this.messageID = messageID;
    this.messageNumber = messageNumber;
    this.recipient = recipient;
    this.message = message;
    
    messageIDs.add(messageID);
    
    messageHash = createMessageHash();
    messageHashes.add(messageHash);
}
    public String createMessageHash() {

    String firstTwoDigits = messageID.substring(0, 2);

    String[] words = message.trim().split("\\s+");

    String firstWord = words[0];
    String lastWord = words[words.length - 1];

    messageHash = firstTwoDigits + ":"
            + messageNumber + ":"
            + firstWord.toUpperCase()
            + lastWord.toUpperCase();
            
    messageHashes.add(messageHash);

    return messageHash;
}
    public String checkMessageLength() {

    if (message.length() <= 250) {
        return "Message ready to send.";
    }

    int excessCharacters = message.length() - 250;

    return "Message exceeds 250 characters by "
            + excessCharacters + " characters.";
}
    public boolean checkRecipientCell() {

    return recipient != null
            && recipient.matches("\\+\\d{10,14}");
}
    public String printMessage() {

    return "Message ID: " + messageID
            + "\nMessage Number: " + messageNumber
            + "\nRecipient: " + recipient
            + "\nMessage: " + message
            + "\nMessage Hash: " + createMessageHash();
}
    public String sendMessage(int choice) {

    switch (choice) {
        case 1:
            sentMessages.add(message);
            return "Message successfully sent.";

        case 2:
            disregardedMessages.add(message);
            return "Message discarded.";

        case 3:
            return "Message successfully stored.";

        default:
            return "Invalid choice.";
    }
}
    public boolean storeMessage() {

    try (FileWriter writer = new FileWriter("messages.json", true)) {

        String json = "{"
                + "\"messageID\":\"" + messageID + "\","
                + "\"messageNumber\":" + messageNumber + ","
                + "\"recipient\":\"" + recipient + "\","
                + "\"message\":\"" + message.replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
                        .replace("\r", "\\r") + "\","
                + "\"messageHash\":\"" + createMessageHash() + "\""
                + "}";

        writer.write(json + System.lineSeparator());
        
        storedMessages.add(printMessage());
        storedMessageIDs.add(messageID);

        return true;

    } catch (IOException e) {

        System.out.println("Error storing message: " + e.getMessage());
        return false;
    }
}
    public static void loadStoredMessages() {
        storedMessages.clear();
    storedMessageIDs.clear();
    messageIDs.clear();
    messageHashes.clear();
    
    try (BufferedReader reader = new BufferedReader(new FileReader("messages.json"))) {
        String line;

        while ((line = reader.readLine()) != null) {
            String messageID = getJsonValue(line, "messageID");
            String numberText = getJsonValue(line, "messageNumber");

if (numberText == null) {
    int numberStart = line.indexOf("\"messageNumber\":");
    if (numberStart != -1) {
        numberStart += "\"messageNumber\":".length();
        int numberEnd = line.indexOf(",", numberStart);

        if (numberEnd != -1) {
            numberText = line.substring(numberStart, numberEnd).replace("\"", "").trim();
        }
    }
}
            String recipient = getJsonValue(line, "recipient");
            String messageText = getJsonValue(line, "message");

            if (messageID != null && numberText != null
                    && recipient != null && messageText != null) {

                int messageNumber = Integer.parseInt(numberText);

                Message loadedMessage = new Message(
                        messageID, messageNumber, recipient, messageText);

                storedMessages.add(loadedMessage.printMessage());
                storedMessageIDs.add(messageID);
            }
        }

    } catch (IOException e) {
        System.out.println("No stored messages file found yet.");
    }
}
    private static String getJsonValue(String json, String key) {
    String search = "\"" + key + "\":\"";
    int start = json.indexOf(search);

    if (start == -1) {
        return null;
    }

    start += search.length();
    StringBuilder value = new StringBuilder();
    boolean escaped = false;

    for (int i = start; i < json.length(); i++) {
        char c = json.charAt(i);

        if (escaped) {
            switch (c) {
                case 'n':
                    value.append('\n');
                    break;
                case 'r':
                    value.append('\r');
                    break;
                case 't':
                    value.append('\t');
                    break;
                default:
                    value.append(c);
            }
            escaped = false;
        } else if (c == '\\') {
            escaped = true;
        } else if (c == '"') {
            return value.toString();
        } else {
            value.append(c);
        }
    }

    return null;
}
    public static boolean removeFromJsonFile(String idToRemove) {
    ArrayList<String> remainingLines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader("messages.json"))) {
        String line;

        while ((line = reader.readLine()) != null) {
            String fileID = getJsonValue(line, "messageID");

            if (fileID == null || !fileID.equals(idToRemove)) {
                remainingLines.add(line);
            }
        }

    } catch (IOException e) {
        System.out.println("Error reading messages.json: " + e.getMessage());
        return false;
    }

    try (FileWriter writer = new FileWriter("messages.json", false)) {
        for (String line : remainingLines) {
            writer.write(line + System.lineSeparator());
        }
        return true;

    } catch (IOException e) {
        System.out.println("Error updating messages.json: " + e.getMessage());
        return false;
    }
}
}