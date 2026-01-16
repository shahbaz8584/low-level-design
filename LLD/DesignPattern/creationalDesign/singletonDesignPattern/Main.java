package DesignPattern.creationalDesign.singletonDesignPattern;

public class Main {

    public static void main(String args[]) {
        DBConnection dbConnection1 = DBConnection.getDBConnectionInstance();
        DBConnection dbConnection2 = DBConnection.getDBConnectionInstance();

        System.out.println(dbConnection1);
        System.out.println(dbConnection2);

        System.out.println(dbConnection1 == dbConnection2);
    }
    
}
