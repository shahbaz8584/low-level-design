package OpenClosePrincipal;

import singleResposibility.Invoice;

public class InvoiceDao {

    Invoice invoice;

    public InvoiceDao(Invoice invoice){
        this.invoice = invoice;
    }
    
    public void saveToDB(Invoice invoice){
        // code to save invoice to database
    }

    /* Now new type of save we want to add here Let say file based save or different DB save for 
    each time need to create a different mehtod here. */
    public void saveToFile(Invoice invoice){
        // code to save invoice to file
    }
    public void saveToNoSQLDB(Invoice invoice){
        // code to save invoice to NoSQL database
    }

    /*
    this doesn't follow Open Close Principle as every time we need to add a new type of save
    we need to modify this class. How do we fix it?
    We can create an interface InvoiceDaoInterface with a method save(Invoice invoice)
    and then create different classes implementing this interface for each type of save
    like InvoiceDBDao, InvoiceFileDao, InvoiceNoSQLDao etc.
    This way we can add new types of save without modifying existing code.
     */
    
}
