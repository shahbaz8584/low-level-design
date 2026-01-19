package DesignPattern.DesignPatternQuestions.customerEscalation;

public class L1Handler extends CustomerHandler{
    public L1Handler(CustomerHandler nexHandler) {
        super(nexHandler);
        
    }

    @Override
    public void handleRequest(Ticket ticket) {
        // L1 handles LOW severity issues (FAQ/basic/general queries)
        if(ticket.getPriorityLevel() != null && ticket.getPriorityLevel().equalsIgnoreCase("LOW")){
            System.out.println("L1 handled ticket " + ticket.getTicketId());
            ticket.setStatus("RESOLVED");
        }
        else {
            if(nextHandler != null) {
                nextHandler.handleRequest(ticket);
            }
        }
    }
    
}
