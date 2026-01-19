package DesignPattern.behavioralDesign.chainOfResponsibility;

public abstract class ATMHandler {

    protected ATMHandler nextHandler;

    public ATMHandler(ATMHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handlerRequest(int amount);

    public void setNextHandler(ATMHandler nextHandler){
        this.nextHandler = nextHandler;
    }
    
}
