package com.studentapp.model;

public class Student {
    private String rollNo;
    private String name;
    private String course;
    private String branch;
    private String gender;
    private String contact;

    public Student() {}

    public Student(String rollNo, String name, String course, String branch, String gender, String contact) {
        this.rollNo = rollNo;
        this.name = name;
        this.course = course;
        this.branch = branch;
        this.gender = gender;
        this.contact = contact;
    }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
}