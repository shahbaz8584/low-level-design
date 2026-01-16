package DesignPattern.creationalDesign.builderPattern;

import java.util.List;

public class Student {

    int rollNo;
    String name;
    String courseName;
    int age;
    String fatherName;
    String motherName;
    int phoneNo;
    String emailId;
    List<String> subjects;


    // Student(int rollNo, String name, String courseName, int age, String fatherName, String motherName, int phoneNo, String emailId) {
    //     this.rollNo = rollNo;
    //     this.name = name;
    //     this.courseName = courseName;
    //     this.age = age;
    //     this.fatherName = fatherName;
    //     this.motherName = motherName;
    //     this.phoneNo = phoneNo;
    //     this.emailId = emailId;
    // }

    // Student(int rollNo, String name, int age) {
    //     this.rollNo = rollNo;
    //     this.name = name;
    //     this.age = age;
    // }

    // Student(int rollNo, String name, String courseName, int age) {
    //     this.rollNo = rollNo;
    //     this.name = name;
    //     this.courseName = courseName;
    //     this.age = age;
    // }

    /* here the problem is based in each constructor having different parameters and the need to initialize all fields properly
    this will cause issues with maintainability and readability
     */

    Student(StudentBuilder builder) {
        this.rollNo = builder.rollNo;
        this.name = builder.name;
        this.courseName = builder.courseName;
        this.age = builder.age;
        this.fatherName = builder.fatherName;
        this.motherName = builder.motherName;
        this.phoneNo = builder.phoneNo;
        this.emailId = builder.emailId;
        this.subjects = builder.subjects;
    }

    public String toString() {
        return "Student [rollNo=" + rollNo + ", name=" + name + ", courseName=" + courseName + ", age=" + age
                + ", fatherName=" + fatherName + ", motherName=" + motherName + ", phoneNo=" + phoneNo + ", emailId="
                + emailId + ", subjects=" + subjects + "]";
    }
    
}
