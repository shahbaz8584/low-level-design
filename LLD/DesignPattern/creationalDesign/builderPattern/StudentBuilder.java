package DesignPattern.creationalDesign.builderPattern;

import java.util.List;

public abstract class StudentBuilder {

    int rollNo;
    String name;
    String courseName;
    int age;
    String fatherName;
    String motherName;
    int phoneNo;
    String emailId;
    List<String> subjects;

    public StudentBuilder setRollNo(int rollNo) {
        this.rollNo = rollNo;
        return this;
    }  
    public StudentBuilder setName(String name) {
        this.name = name;
        return this;
    }
    public StudentBuilder setCourseName(String courseName) {
        this.courseName = courseName;
        return this;
    }
    public StudentBuilder setAge(int age) {
        this.age = age;
        return this;
    }
    public StudentBuilder setFatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }
    public StudentBuilder setMotherName(String motherName) {
        this.motherName = motherName;
        return this;
    }
    public StudentBuilder setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
        return this;
    }
    public StudentBuilder setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    abstract StudentBuilder setSubjects();

    public Student build(){
        return new Student(this);
    }


    // StudentBuilder setRollNo(int rollNo);
    // StudentBuilder setName(String name);
    // StudentBuilder setCourseName(String courseName);
    // StudentBuilder setAge(int age);
    // StudentBuilder setFatherName(String fatherName);
    // StudentBuilder setMotherName(String motherName);
    // StudentBuilder setPhoneNo(int phoneNo);
    // StudentBuilder setEmailId(String emailId);
    // StudentBuilder setSubjects(List<String> subjects);
    // Student build();
    
}
