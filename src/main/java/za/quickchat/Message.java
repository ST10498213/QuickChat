package za.quickchat;

import java.io.FileWriter;
import java.io.IOException;

public class Message {

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
            return "Message successfully sent.";

        case 2:
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

        return true;

    } catch (IOException e) {

        System.out.println("Error storing message: " + e.getMessage());
        return false;
    }
}
}