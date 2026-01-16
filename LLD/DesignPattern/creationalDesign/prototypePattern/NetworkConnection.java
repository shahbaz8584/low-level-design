package DesignPattern.creationalDesign.prototypePattern;

public class NetworkConnection implements Cloneable {

    String ip;
    String heavyData;

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
        Thread.sleep(5000);
        this.heavyData = "This is some heavy data loaded from network.";
    }

    @Override
    public String toString() {
        return "NetworkConnection [ip=" + ip + ", heavyData=" + heavyData + "]";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
    
}
