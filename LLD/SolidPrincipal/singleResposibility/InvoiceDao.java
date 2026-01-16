package singleResposibility;

public class InvoiceDao {

    Invoice invoice;

    public InvoiceDao(Invoice invoice){
        this.invoice = invoice;
    }
    
    public void saveToDB(Invoice invoice){
        // code to save invoice to database
    }
    
}
