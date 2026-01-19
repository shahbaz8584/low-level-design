package DesignPattern.DesignPatternQuestions.customerEscalation;

public class L3Handler extends CustomerHandler{


    public L3Handler(CustomerHandler nexHandler) {
        super(nexHandler);
        
    }

    @Override
    public void handleRequest(Ticket ticket) {
        // TODO Auto-generated method stub
        
            // L3 handles HIGH or CRITICAL severity, otherwise marks UNRESOLVED and notifies
            if(ticket.getPriorityLevel() != null &&
               (ticket.getPriorityLevel().equalsIgnoreCase("HIGH") ||
                ticket.getPriorityLevel().equalsIgnoreCase("CRITICAL"))) {

                System.out.println("L3 handled ticket " + ticket.getTicketId());
                ticket.setStatus("RESOLVED");
            }
            else {
                // No further level: mark unresolved and notify admin/engineering
                ticket.setStatus("UNRESOLVED");
                System.out.println("Ticket " + ticket.getTicketId() + " could not be resolved at L3. Marking UNRESOLVED.");
                System.out.println("Notifying admin/engineering team for ticket " + ticket.getTicketId());
            }
    }
    
}
