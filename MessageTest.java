package message;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the methods currently implemented in Message.java.
 *
 * These tests use the test data supplied in the assignment:
 * 1) +27718693002 / "Hi Mike, can you join us for dinner tonight?"
 * 2) 08575975889 / "Hi Keegan, did you receive the payment?"
 *
 * JUnit 5 is required.
 */
public class MessageTest {

    @Test
    void generatedMessageIdContainsExactlyTenDigits() {
        String generatedId = Message.generateMessageID();

        assertNotNull(generatedId, "The generated message ID must not be null.");
        assertTrue(generatedId.matches("\\d{10}"),
                "The generated message ID must contain exactly 10 digits.");

        Message message = new Message(generatedId, 0, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");
        assertTrue(message.checkMessageID(),
                "A generated 10-digit ID should pass checkMessageID().");
    }

    @Test
    void messageIdAcceptsTenCharacters() {
        Message message = new Message("1234567890", 0, "+27718693002", "Hello there");

        assertTrue(message.checkMessageID(),
                "A message ID with 10 characters should be accepted.");
    }

    @Test
    void messageIdRejectsMoreThanTenCharacters() {
        Message message = new Message("12345678901", 0, "+27718693002", "Hello there");

        assertFalse(message.checkMessageID(),
                "A message ID longer than 10 characters should be rejected.");
    }

    @Test
    void messageIdRejectsNull() {
        Message message = new Message(null, 0, "+27718693002", "Hello there");

        assertFalse(message.checkMessageID(), "A null message ID should be rejected.");
    }

    @Test
    void acceptsRecipientWithSouthAfricanInternationalCode() {
        Message message = new Message("00", 0, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");

        assertTrue(message.checkRecipientCell(),
                "The international number +27718693002 should be accepted.");
    }

    @Test
    void rejectsRecipientWithoutInternationalCode() {
        Message message = new Message("01", 1, "08575975889",
                "Hi Keegan, did you receive the payment?");
         assertFalse(message.checkRecipientCell(),
                "The local number 08575975889 should fail the international-format check.");
    }

    @Test
    void rejectsRecipientWithIncorrectInternationalFormat() {
        Message message = new Message("02", 2, "+12345678901", "Hello there");

        assertFalse(message.checkRecipientCell(),
                "A number that does not match the required +27 format should be rejected.");
    }

    @Test
    void createsRequiredHashForTestCaseOne() {
        Message message = new Message("00", 0, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");

        assertEquals("00:0:HITONIGHT", message.createMessageHash());
    }

    @Test
    void createsHashForTestCaseTwo() {
        Message message = new Message("01", 1, "08575975889",
                "Hi Keegan, did you receive the payment?");

        assertEquals("01:1:HIPAYMENT", message.createMessageHash());
    }

    @Test
    void hashUsesFirstTwoCharactersOfMessageId() {
        Message message = new Message("9876543210", 2, "+27718693002", "Hello world!");

        assertEquals("98:2:HELLOWORLD", message.createMessageHash());
    }
}