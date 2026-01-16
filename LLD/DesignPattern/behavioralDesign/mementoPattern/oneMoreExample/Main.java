package DesignPattern.behavioralDesign.mementoPattern.oneMoreExample;

public class Main {

    public static void main(String args[]) {
        UndoManager manager = new UndoManager(); // caretaker

        TextEditor textEditor = new TextEditor("Hello"); // originator

        EditorMemento editorMemento = textEditor.creaEditorMemento(); // memento

        manager.save(editorMemento); // save 1
    

        textEditor.setWord("Hello World");

        EditorMemento editorMemento2 = textEditor.creaEditorMemento();

        manager.save(editorMemento2); // save2

        // System.out.println(editorMemento2.context);
        // System.out.println(manager.getHistory());
        textEditor.setWord("Hello World!");

        System.out.println("Current: " + textEditor);
        
        EditorMemento restoreObj = manager.undo();
        textEditor.restoreEditorMemento(restoreObj);
        System.out.println("First Undo" + restoreObj);
        EditorMemento restoreObj2 = manager.undo();
        textEditor.restoreEditorMemento(restoreObj2);
        System.out.println("Second Undo " + restoreObj2);



    }
    
}
