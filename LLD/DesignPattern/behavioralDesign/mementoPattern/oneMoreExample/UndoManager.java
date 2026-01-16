package DesignPattern.behavioralDesign.mementoPattern.oneMoreExample;

import java.util.Stack;


// CareTaker
public class UndoManager {

    Stack<EditorMemento> history = new Stack<>();

    public void save(EditorMemento memento){
        history.push(memento);
    }

    public EditorMemento undo(){
        if(!history.empty()) {
            return history.pop();
        }
        return null;
    }

    public Stack<EditorMemento> getHistory() {
        return history;
    }
    
}
