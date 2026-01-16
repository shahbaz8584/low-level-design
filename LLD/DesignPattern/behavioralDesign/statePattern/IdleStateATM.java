package DesignPattern.behavioralDesign.statePattern;

public class IdleStateATM implements ATMState{

    private ATM atm;

    public IdleStateATM(ATM atm) {
        this.atm = atm;
    }


    @Override
    public void insertCard() {
        System.out.println("Card Inserted.");
        atm.setATMState(atm.getCardInsertedState());
    }

    @Override
    public void enterPin() {
        System.out.println("Please insert your card first.");
    }

    @Override
    public void withdrawCash() {
        // TODO Auto-generated method stub
       System.out.println("Please insert your card and enter PIN first.");
    }
    
    
}
