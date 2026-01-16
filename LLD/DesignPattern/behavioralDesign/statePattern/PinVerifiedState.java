package DesignPattern.behavioralDesign.statePattern;

public class PinVerifiedState implements ATMState {

    private ATM atm;

    public PinVerifiedState(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void insertCard() {
        System.out.println("Transaction in progress. Cannot insert another card.");
    }

    @Override
    public void enterPin() {
        System.out.println("PIN is already verified.");
    }

    @Override
    public void withdrawCash() {
        System.out.println("Cash withdrawn successfully.");
        atm.setATMState(atm.getIdleState());
        
    }
    
}
