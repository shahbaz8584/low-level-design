package DesignPattern.behavioralDesign.mementoPattern.oneMoreExample;


// Originator
public class TextEditor {

    String word;

    public TextEditor(String word){
        this.word = word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public EditorMemento creaEditorMemento() {
        return new EditorMemento(word);
    }

    public void restoreEditorMemento(EditorMemento memento){
        this.word = memento.getContext();
    }

    @Override
    public String toString() {
        return word;
    }

    
}
