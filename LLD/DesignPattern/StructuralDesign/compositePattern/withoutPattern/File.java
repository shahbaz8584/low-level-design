package DesignPattern.StructuralDesign.compositePattern.withoutPattern;

public class File {

    String fileName;

    public File(String fileName) {
        this.fileName = fileName;
    }

    public void showDetails() {
        System.out.println("File: " + fileName);
    }
    
}
