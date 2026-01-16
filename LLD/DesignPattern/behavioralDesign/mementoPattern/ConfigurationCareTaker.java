package DesignPattern.behavioralDesign.mementoPattern;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationCareTaker {

    List<ConfigurationMemento> history = new ArrayList<>();

    public void addMemento(ConfigurationMemento memento){
        history.add(memento);
    }

    public ConfigurationMemento undo(){
        if(!history.isEmpty()){
            int lastIndexValue = history.size() - 1;
            ConfigurationMemento lastHistory = history.get(lastIndexValue);
            history.remove(lastIndexValue);
            return lastHistory;
        }
        return null;
    }
    
}
