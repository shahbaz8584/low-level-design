package DesignPattern.StructuralDesign.compositePattern.withPatternSolution1;

import java.util.ArrayList;
import java.util.List;

public class Directory implements FileSystem{

    String directoryName;
    List<FileSystem> fileSystemList;

    public Directory(String directoryName) {
        this.directoryName = directoryName;
        this.fileSystemList = new ArrayList<>();
    }

    public void addList(FileSystem fileSystem) {
        fileSystemList.add(fileSystem);
    }

    @Override
    public void showDetails(){
        System.out.println("Directory : " + directoryName);
        for(FileSystem file : fileSystemList) {
            file.showDetails();
        }
    }
    
}
