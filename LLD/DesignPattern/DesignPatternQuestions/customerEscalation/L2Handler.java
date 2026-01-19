package DesignPattern.DesignPatternQuestions.customerEscalation;

public class L2Handler extends CustomerHandler{


    public L2Handler(CustomerHandler nexHandler) {
        super(nexHandler);
        
    }

    @Override
    public void handleRequest(Ticket ticket) {
        // TODO Auto-generated method stub
        
        // L2 handles MEDIUM severity technical/configuration issues
        if(ticket.getPriorityLevel() != null && ticket.getPriorityLevel().equalsIgnoreCase("MEDIUM")){
            System.out.println("L2 handled ticket " + ticket.getTicketId());
            ticket.setStatus("RESOLVED");
        }
        else {
            if(nextHandler != null) {
                nextHandler.handleRequest(ticket);
            }
        }
    }
    
}
