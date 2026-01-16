package DesignPattern.behavioralDesign.objectPoolPattern;

import java.sql.Connection;

public class DBConnection {

    // Connection mysqlConnection;

    DBConnection(){
        // For now, just create a dummy object to test the pool pattern
        // Actual DB connection logic can be added later
        System.out.println("Creating DBConnection object");
    }
    
}
