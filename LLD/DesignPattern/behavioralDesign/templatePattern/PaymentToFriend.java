package DesignPattern.behavioralDesign.templatePattern;

public class PaymentToFriend extends PaymentFlow {

    @Override
    public void validateAccountBalance() {
        System.out.println("Validating account balance for payment to friend");
    }

    @Override
    public void debitMoney() {
        System.out.println("Debiting money from sender's account for payment to friend");
    }

    @Override
    public void calculateBalance() {
        System.out.println("Calculating new balance after payment to friend");
    }

    @Override
    public void sendNotification() {
        System.out.println("Sending notification for payment to friend");
    }

    @Override
    public void creditMoney() {
        System.out.println("Crediting money to friend's account");
    }
    
}
