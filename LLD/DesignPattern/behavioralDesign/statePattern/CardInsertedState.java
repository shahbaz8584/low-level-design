package DesignPattern.behavioralDesign.statePattern;

public class CardInsertedState implements ATMState{
    private ATM atm;

    public CardInsertedState(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void insertCard() {
        System.out.println("Card is already inserted.");  
    }

    @Override
    public void enterPin() {
        System.out.println("Please enter your PIN.");
        atm.setATMState(atm.getPinVerifiedState());
    }

    @Override
    public void withdrawCash() {
        System.out.println("Please enter your PIN first.");
    }
    
}
