package DesignPattern.creationalDesign.builderPattern;

public class StudentRegistration {

    StudentBuilder studentBuilder;

    StudentRegistration(StudentBuilder studentBuilder){
        this.studentBuilder = studentBuilder;
    }

    public Student createStudent(){
        if(studentBuilder instanceof EngineerStudentBuilder){
        return createEngineerStudent();
    }
    else if(studentBuilder instanceof MBAStudentBuilder){
        return createMbaStudent();
    }
    return null;
    }
    
    public Student createEngineerStudent(){
        return studentBuilder
                .setRollNo(201)
                .setName("Shahbaz")
                .setSubjects()
                .build();
    }

        public Student createMbaStudent(){
        return studentBuilder
                .setRollNo(201)
                .setName("Shahbaz")
                .setCourseName("Engineering")
                .setAge(20)
                .setFatherName("Mr. Hussain")
                .setMotherName("Mrs. Hussain")
                .setPhoneNo(987654321)
                .setEmailId("")
                .setSubjects()
                .build();
    }

    
}
