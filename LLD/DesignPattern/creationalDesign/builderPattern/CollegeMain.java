package DesignPattern.creationalDesign.builderPattern;

public class CollegeMain {

    public static void main(String args[]) {
        // Student student = new Student(101, "Shahbaz", 20);
        // System.out.println("Student Name: " + student.name + ", Roll No: " + student.rollNo + ", Age: " + student.age);
        StudentRegistration engineerStudentRegistration = new StudentRegistration(new EngineerStudentBuilder());
        StudentRegistration mbaStudentRegistration = new StudentRegistration(new MBAStudentBuilder());
        
        Student engineerStudent = engineerStudentRegistration.createStudent();
        Student mbaStudent = mbaStudentRegistration.createStudent();

        System.out.println("Student Details: " + engineerStudent.toString());
        System.out.println("Student Details: " + mbaStudent.toString());
        
    }
    
}
