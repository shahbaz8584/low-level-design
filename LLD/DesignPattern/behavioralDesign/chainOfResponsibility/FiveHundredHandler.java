package DesignPattern.behavioralDesign.chainOfResponsibility;

public class FiveHundredHandler extends ATMHandler{

    private int availableNotes;

    public FiveHundredHandler(ATMHandler nexAtmHandler, int availableNotes) {
        super(nexAtmHandler);
        this.availableNotes = availableNotes;
    }

    @Override
    public void handlerRequest(int amount) {
         int notesNeeded = amount / 500;
        if(notesNeeded > availableNotes){
            notesNeeded = availableNotes;
            availableNotes = 0;
        }
        else {
            availableNotes = availableNotes - notesNeeded;
        }
        if(notesNeeded > 0) {
            System.out.println("Dispensing " + notesNeeded + " x five hundred notes");
        }
        int remainingAmount = amount -( notesNeeded * 500);
        if(remainingAmount > 0) {
            if(nextHandler != null) {
                nextHandler.handlerRequest(remainingAmount);
            } else {
                System.out.println("Cannot dispense remaining amount: " + remainingAmount);
            }
        }

    }
    
}
