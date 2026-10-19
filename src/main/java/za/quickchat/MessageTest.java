package za.quickchat;

public class MessageTest {

    public static void main(String[] args) {

        Message msg = new Message(
                "1234567890",
                1,
                "+27718693002",
                "Hi Mike"
        );

        System.out.println("Message Hash: "
                + msg.createMessageHash());
        Message valid250 = new Message(
        "1234567891",
        2,
        "+27718693002",
        "A".repeat(250)
);

Message invalid251 = new Message(
        "1234567892",
        3,
        "+27718693002",
        "A".repeat(251)
);

System.out.println("250 characters: "
        + valid250.checkMessageLength());

System.out.println("251 characters: "
        + invalid251.checkMessageLength());
Message validRecipient = new Message(
        "1234567893",
        4,
        "+27718693002",
        "Hello Glen"
);

Message invalidRecipient = new Message(
        "1234567894",
        5,
        "08575975889",
        "Hello Glen"
);

System.out.println("Valid recipient test: "
        + validRecipient.checkRecipientCell());

System.out.println("Invalid recipient test: "
        + invalidRecipient.checkRecipientCell());

System.out.println("Hash test: "
        + validRecipient.createMessageHash());
System.out.println("Storage test: "+ validRecipient .storeMessage());

        System.out.println("Message Length: "
                + msg.checkMessageLength());

        System.out.println("Recipient Valid: "
                + msg.checkRecipientCell());

        System.out.println("Message Details:");
        System.out.println(msg.printMessage());

        System.out.println("Send Option: "
                + msg.sendMessage(1));
    }
}