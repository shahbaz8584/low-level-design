package DesignPattern.StructuralDesign.compositePattern.withPatternSolution1;

public class Main {

    public static void main(String args[]) {
        Directory movies = new Directory("Movies");
        File ddlj = new File("DDLJ");
        movies.addList(ddlj);
        Directory horrorMovies = new Directory("Horror Movies");
        File conjuring = new File("Conjuring");
        FileSystem annabelle = new File("Annabelle");
        horrorMovies.addList(conjuring);
        horrorMovies.addList(annabelle);
        movies.addList(horrorMovies);

        movies.showDetails();
    }   
    
}
