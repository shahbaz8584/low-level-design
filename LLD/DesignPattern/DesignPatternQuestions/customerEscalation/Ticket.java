package DesignPattern.DesignPatternQuestions.customerEscalation;

public class Ticket {

    private int ticketId;
    private String issue;
    private String issueCategory;
    private String priorityLevel;
    private String status;

    public Ticket(int ticketId, String issue, String issueCategory, String priorityLevel) {
        this.ticketId = ticketId;
        this.issue = issue;
        this.issueCategory = issueCategory;
        this.priorityLevel = priorityLevel;
        this.status = "OPEN";
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getIssue() {
        return issue;
    }

    public String getIssueCategory() {
        return issueCategory;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
