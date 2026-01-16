package DesignPattern.behavioralDesign.templatePattern;

public class Main {

    public static void main(String args[]) {

        PaymentFlow payment = new PaymentToFriend();
        payment.executePayment();

    }
    
}
