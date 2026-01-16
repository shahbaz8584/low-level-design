package DesignPattern.behavioralDesign.statePattern;

public class Main {

    public static void main(String[] args) {
        ATM atm = new ATM();

        atm.insertCard();
        atm.enterPin();
        atm.withdrawCash();
    }
    
}
