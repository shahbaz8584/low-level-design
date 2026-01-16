package DesignPattern.creationalDesign.prototypePattern.deepCloning;

import java.util.ArrayList;
import java.util.List;

public class NetworkConnection implements Cloneable {

    String ip;
    String heavyData;
    List<String> domains = new ArrayList<>();

    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getHeavyData() {
        return heavyData;
    }
    public void setHeavyData(String heavyData) {
        this.heavyData = heavyData;
    }

    public void loadData() throws InterruptedException {
        // Simulating heavy data loading
        this.heavyData = "This is some heavy data loaded from network.";
        domains.add("wwww.google.com");
        domains.add("facebook.com");
        domains.add("netflix.com");
        
        Thread.sleep(5000);
        
    }

    @Override
    public String toString() {
        return "NetworkConnection [ip=" + ip + ", heavyData=" + heavyData + ", domains=" + domains + "]";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException{
        NetworkConnection networkConnection = new NetworkConnection();
        networkConnection.setIp(this.getIp());
        networkConnection.setHeavyData(this.getHeavyData());
        for(String domain : this.domains) {
            networkConnection.getDomains().add(domain);
        }

        return networkConnection;
    }

    public List<String> getDomains() {
        return domains;
    }
}
