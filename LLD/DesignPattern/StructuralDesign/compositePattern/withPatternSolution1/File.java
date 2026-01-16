package DesignPattern.StructuralDesign.compositePattern.withPatternSolution1;

public class File implements FileSystem {

    String fileName;

    public File(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void showDetails() {
        System.out.println("File: " + fileName);
    }
    
}
