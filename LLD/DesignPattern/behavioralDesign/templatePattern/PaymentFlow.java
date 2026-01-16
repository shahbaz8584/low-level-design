package DesignPattern.behavioralDesign.templatePattern;

public abstract class PaymentFlow {

    public abstract void validateAccountBalance();
    public abstract void debitMoney();
    public abstract void calculateBalance();
    public abstract void sendNotification();
    public abstract void creditMoney();

    public final void executePayment() {
        validateAccountBalance();
        debitMoney();
        calculateBalance();
        creditMoney();
        sendNotification();
    }
    
    
}
