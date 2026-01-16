package DesignPattern.creationalDesign.prototypePattern.deepCloning;

public class Main {

    public static void main(String args[]) throws InterruptedException {

        System.out.println("Creating Object using Protptype Pattern");

        NetworkConnection nc1 = new NetworkConnection();
        nc1.setIp("192.168.2.1");
        nc1.loadData();
        System.out.println("Original Object: " + nc1);

        try{
            System.out.println(nc1);

            NetworkConnection nc2 = (NetworkConnection) nc1.clone();
            NetworkConnection nc3 = (NetworkConnection) nc1.clone();
            nc1.getDomains().remove(0);
            System.out.println(nc1);
            System.out.println(nc2);
            System.out.println(nc3);
        }
        catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
    
}
