package DesignPattern.StructuralDesign.compositePattern.withoutPattern;

import java.util.ArrayList;
import java.util.List;

public class Directory {

    String directoryName;
    List<Object> children;

    public Directory(String directoryName) {
        this.directoryName = directoryName;
        this.children = new ArrayList<>();
    }

    public void addChild(Object child) {
        children.add(child);
    }

    public void showDetails(){
        System.out.println("Directory : " + directoryName);
        for(Object child : children) {
            if(child instanceof File) {
                ((File)child).showDetails();
            }
            else if(child instanceof Directory) {
                ((Directory)child).showDetails();
            }

        }
    }
    
}
