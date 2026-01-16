package singleResposibility;

public class Invoice {

    final Marker marker;
    final int quantity;

    public Invoice(Marker marker, int quantity){
        this.marker = marker;
        this.quantity = quantity;
    }
    /* Moving to different class */
    // public void saveToDB(){
    //     // code to save invoice to database
    // }

    // public int calculateTotal(){
    //    int price = ((marker.price) * quantity);
    //    return price;
    // }

    public int calculateTotal(){
       int price = (marker.getPrice() * quantity);
       return price;
    }

    /*
    So this class doesn't follow Single Responsibility Principle as it has two reasons to change
    1. If there is a change in the way invoice is saved to database
    2. If there is a change in the way total is calculated for an invoice
    3. How do we fix it?
    We can create a separate class for each responsibility
    1. InvoiceRepository class to handle saving invoice to database
    2. InvoiceCalculator class to handle calculating total for an invoice
    3. This way each class has only one reason to change     
    4. We will keep calculateTotal method in Invoice class only and other we will move to different classes.   
    */
}
