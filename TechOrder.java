public class TechOrder {
    private static int counter = 1;
    private int ticketID;
    private String issue;
    private String description;
    private String createdBy; // Логин пользователя, создавшего заявку
    private boolean accepted = false;
    private boolean completed = false;

    public TechOrder(String issue, String description, String createdBy) {
        this.ticketID = counter++;
        this.issue = issue;
        this.description = description;
        this.createdBy = createdBy;
    }

    public int getTicketID() {
        return ticketID;
    }

    public String getIssue() {
        return issue;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void acceptOrder() {
        this.accepted = true;
    }
    public void completeOrder() {
        this.completed = true;
    }
    public void markAsCompleted() {
        if (accepted) {
            this.completed = true;
        }
    }


    @Override
    public String toString() {
        return "TicketID: " + ticketID + ", Issue: " + issue + ", Description: " + description +
                ", Created By: " + createdBy + ", Accepted: " + accepted + ", Completed: " + completed;
    }
}
