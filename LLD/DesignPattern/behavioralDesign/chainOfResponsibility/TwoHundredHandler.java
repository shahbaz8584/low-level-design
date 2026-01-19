package DesignPattern.behavioralDesign.chainOfResponsibility;



public class TwoHundredHandler extends ATMHandler{

    private int availableNotes;

    public TwoHundredHandler(ATMHandler nextHandler, int availableNotes) {
        super(nextHandler);
        this.availableNotes = availableNotes;
    }

    @Override
    public void handlerRequest(int amount) {
        int notesNeeded = amount / 200;
        if(notesNeeded > availableNotes){
            notesNeeded = availableNotes;
            availableNotes = 0;
        }
        else {
            availableNotes = availableNotes - notesNeeded;
        }
        if(notesNeeded > 0) {
            System.out.println("Dispensing " + notesNeeded + " x two hundred notes");
        }
        int remainingAmount = amount -( notesNeeded * 200);
        if(remainingAmount > 0) {
            if(nextHandler != null) {
                nextHandler.handlerRequest(remainingAmount);
            } else {
                System.out.println("Cannot dispense remaining amount: " + remainingAmount);
            }
        }

    }
    
}
