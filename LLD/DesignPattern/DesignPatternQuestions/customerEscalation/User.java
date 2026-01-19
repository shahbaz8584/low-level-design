package DesignPattern.DesignPatternQuestions.customerEscalation;

public class User {

    public static void main(String args[]) {
        Ticket ticket = new Ticket(123, "Production down", "Technical", "CRITICAL");

        CustomerHandler l3 = new L3Handler(null);
        CustomerHandler l2 = new L2Handler(l3);
        CustomerHandler l1 = new L1Handler(l2);

        l1.handleRequest(ticket);

        System.out.println("Final ticket status: " + ticket.getStatus());

    }
    
}
