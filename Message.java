package message;

import java.util.ArrayList;
import java.util.Random;

public class Message {
    private int messageNumber;
    private String messageID;
    private String messageHash;
    private String messageText;
    private String recipient;

    // Stores messages sent during the program
    private static ArrayList<Message> sentMessages = new ArrayList<>();
    
    // Stores messages that were disregarded
    private static ArrayList<Message> disregardedMessages = new ArrayList<>();
    
    // Stores messages saved for later
    private static ArrayList<Message> storedMessages = new ArrayList<>();
    
    // Total number of messages sent 
    private static int totalMessages = 0;

    // ==================================
    // CONSTRUCTOR
    // ==================================
    public Message(String messageID, int messageNumber, String recipient, String messageText) { 
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();  
    }

    // Generates a random 10-digit tracking ID
    public static String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + Math.abs(rand.nextLong() % 9000000000L);
        return String.valueOf(id);
    }

    // Method: checkMessageID() - ensures ID is not more than 10 characters
    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() <= 10;
    }

    // Method: checkRecipientCell()
    public boolean checkRecipientCell() { 
        return recipient != null && recipient.matches("^\\+27\\d{8,9}$");
    }

    // CREATE MESSAGE HASH
    public String createMessageHash() {
        String firstTwoNumbers;
        
        if (messageID != null && messageID.length() >= 2) { 
            firstTwoNumbers = messageID.substring(0, 2);
        } else {
            firstTwoNumbers = messageID != null ? messageID : "";
        }
        
        String firstWord = "";
        String lastWord = "";
        
        if (messageText != null && !messageText.trim().isEmpty()){
            String cleanMessage = messageText.trim();
            String[] words = cleanMessage.split("\\s+");
            
            firstWord = words[0];
            lastWord = words[words.length - 1];
            
            // Remove punctuation 
            firstWord = firstWord.replaceAll("[^a-zA-Z0-9]", "");
            lastWord = lastWord.replaceAll("[^a-zA-Z0-9]", "");
        }
        
        return (firstTwoNumbers + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public static void storeMessageJSON(String ID, String Hash, String recipient, String Text) {
        System.out.println("[Research Task]: Writing message ID " + ID + " to local JSON storage file.");
    }
}