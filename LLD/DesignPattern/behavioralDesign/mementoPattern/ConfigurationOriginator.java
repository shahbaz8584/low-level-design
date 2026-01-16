package DesignPattern.behavioralDesign.mementoPattern;

public class ConfigurationOriginator {

    int height;
    int weight;

    public ConfigurationOriginator(int height, int weight) {
        this.height = height;
        this.weight = weight;
    }
        
    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    public ConfigurationMemento createMemento(){
        return new ConfigurationMemento(height, weight);
    }

    public void restoreMemento(ConfigurationMemento memento) {
        this.height = height;
        this.weight = weight;
    }

}
