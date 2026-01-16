package DesignPattern.behavioralDesign.mementoPattern.oneMoreExample;


// Memento
public class EditorMemento {

    String context;

    public EditorMemento(String context) {
        this.context = context;
    }

    public String getContext(){
        return this.context;
    }

    @Override
    public String toString(){
        return "EditorMemento{" + "context='" + context + '\'' + '}';
    }
    
}
