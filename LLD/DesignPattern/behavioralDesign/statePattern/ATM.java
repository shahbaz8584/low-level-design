package DesignPattern.behavioralDesign.statePattern;

public class ATM {

    private ATMState currentState;
    private ATMState idleState;
    private ATMState cardInsertedState;
    private ATMState pinVerifiedState;

    public ATM(){
        idleState = new IdleStateATM(this);
        cardInsertedState = new CardInsertedState(this);
        pinVerifiedState = new PinVerifiedState(this);
        currentState = idleState;
    }

    public void setATMState(ATMState state){
        this.currentState = state;
    }

    public ATMState getIdleState() {
        return idleState;
    }

    public ATMState getCardInsertedState(){
        return cardInsertedState;
    }

    public ATMState getPinVerifiedState(){
        return pinVerifiedState;
    }

    public void insertCard(){
        currentState.insertCard();
    }
    
    public void enterPin(){
        currentState.enterPin();
    }

    public void withdrawCash(){
        currentState.withdrawCash();
    }
    
}
