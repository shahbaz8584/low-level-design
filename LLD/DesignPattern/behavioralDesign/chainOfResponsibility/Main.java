package DesignPattern.behavioralDesign.chainOfResponsibility;

public class Main {

    public static void main(String args[]) {

        ATMHandler hundredHandler = new HundredHandler(null, 10);
        ATMHandler twoHundredHandler = new TwoHundredHandler(hundredHandler, 10);
        ATMHandler fiveHundredHandler = new FiveHundredHandler(twoHundredHandler, 10);
        ATMHandler thousandHandler = new ThousandHandler(fiveHundredHandler, 10);

        int amountToWithdraw = 7600;
        thousandHandler.handlerRequest(amountToWithdraw);
    }
    
}
