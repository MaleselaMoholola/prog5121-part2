package message;

import java.util.Scanner;

public class QuickChat1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to QuickChat.");
        
        // Simulating login check
        boolean isLoggedIn = true; 
        if (!isLoggedIn) {
            System.out.println("You must be logged in to send messages.");
            return;
        }

        int choice;
        do {
            System.out.println("\n==== QuickChat Menu ====");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages (Coming Soon)");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter how many messages you wish to send: ");
                    int numMessages = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    for (int i = 0; i < numMessages; i++) {
                        System.out.print("Enter recipient cell number (e.g., +2718693002): ");
                        String recipient = scanner.nextLine();

                        System.out.print("Enter your message (max 250 characters): ");
                        String messageText = scanner.nextLine();

                        if (messageText.length() > 250) {
                            System.out.println("Please enter a message of less than 250 characters.");
                            i--; // Retry this iteration
                            continue;
                        }

                        // Create message object and process using correct class name (Message)
                        String randomID = Message.generateMessageID();
                        Message msg = new Message(randomID, i, recipient, messageText);

                        if (!msg.checkRecipientCell()) {
                            System.out.println("Cell phone number is incorrectly formatted or does not contain an international code.");
                            continue;
                        }

                        System.out.println("Message ready to send.");
                        System.out.println("Generated Message Hash: " + msg.createMessageHash());
                    }
                    break;
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                case 3:
                    System.out.println("Exiting QuickChat. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose between 1, 2, or 3.");
            }
        } while (choice != 3);
        
        scanner.close();
    }
}