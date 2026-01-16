package DesignPattern.behavioralDesign.templatePattern;

public class PaymentToMerchant extends PaymentFlow {

    @Override
    public void validateAccountBalance() {
        System.out.println("Validating account balance for merchant payment");
    }

    @Override
    public void debitMoney() {
        System.out.println("Debiting money from customer account for merchant payment");
    }

    @Override
    public void calculateBalance() {
        System.out.println("Calculating balance after merchant payment");
    }

    @Override
    public void sendNotification() {
        System.out.println("Sending notification for merchant payment");
    }

    @Override
    public void creditMoney() {
        System.out.println("Crediting money to merchant account");
    }
    
}
