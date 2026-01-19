package DesignPattern.DesignPatternQuestions.customerEscalation;

public abstract class CustomerHandler {

    protected CustomerHandler nextHandler;

    private static String L1 = "MEDIUM";
    private static String L2 = "High";
    private static String L3 = "CRITICAL";

    public CustomerHandler(CustomerHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
    
    public abstract void handleRequest(Ticket ticket);

    public void setNextHandler(CustomerHandler nextHandler){
        this.nextHandler = nextHandler;
    }
    
}
