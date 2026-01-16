package DesignPattern.creationalDesign.prototypePattern;

public class Main {

    public static void main(String args[]) throws InterruptedException {

        System.out.println("Creating Object using Protptype Pattern");

        NetworkConnection nc1 = new NetworkConnection();
        nc1.setIp("192.168.2.1");
        nc1.loadData();
        System.out.println("Original Object: " + nc1);

        try{
            NetworkConnection nc2 = (NetworkConnection) nc1.clone();
            NetworkConnection nc3 = (NetworkConnection) nc1.clone();
            System.out.println("Cloned Object 1: " + nc2);
            System.out.println("Cloned Object 1: " + nc3);
        }
        catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
    
}
