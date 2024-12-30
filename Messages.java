public class Messages {
    private int messageId;
    private User sender;
    private String content;
    private String status; // SENT, READ, ARCHIVED
    private String urgency; // LOW, MEDIUM, HIGH

    public Messages(int messageId, User sender, String content, String status, String urgency) {
        this.messageId = messageId;
        this.sender = sender;
        this.content = content;
        this.status = status;
        this.urgency = urgency;
    }

    public boolean isUrgent() {
        return "HIGH".equalsIgnoreCase(urgency);
    }

    public void markAsRead() {
        this.status = "READ";
    }

    public void archiveMessage() {
        this.status = "ARCHIVED";
    }

    @Override
    public String toString() {
        return String.format("Message ID: %d, Sender: %s, Status: %s, Urgency: %s, Content: %s",
                messageId, sender.getLogin(), status, urgency, content);
    }
}
