package DesignPattern.DesignPatternQuestions.AtmCashWithdrawl;



public class ThousandHandler extends ATMHandler{

    private int availableNotes;

    public ThousandHandler(ATMHandler nextHandler, int availableNotes) {
        super(nextHandler);
        this.availableNotes = availableNotes;
    }

    @Override
    public void handlerRequest(int amount) {
        // TODO Auto-generated method stub
        int notesNeeded = amount / 1000;
        if(notesNeeded > availableNotes){
            notesNeeded = availableNotes;
            availableNotes = 0;
        }
        else {
            availableNotes = availableNotes - notesNeeded;
        }
        if(notesNeeded > 0) {
            System.out.println("Dispensing " + notesNeeded + " x thousand notes");
        }
        int remainingAmount = amount -( notesNeeded * 1000);
        if(remainingAmount > 0) {
            if(nextHandler != null) {
                nextHandler.handlerRequest(remainingAmount);
            } else {
                System.out.println("Cannot dispense remaining amount: " + remainingAmount);
            }
        }

    }
    
}
