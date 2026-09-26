package com.studentapp.model;

public class Result {
    private int resultId;
    private String rollNo;
    private int semester;
    private double subject1;
    private double subject2;
    private double subject3;
    private double subject4;
    private double subject5;
    private double gpa;
    private String status;

    public Result() {}

    public Result(String rollNo, int semester, double subject1, double subject2, double subject3, double subject4, double subject5, double gpa, String status) {
        this.rollNo = rollNo;
        this.semester = semester;
        this.subject1 = subject1;
        this.subject2 = subject2;
        this.subject3 = subject3;
        this.subject4 = subject4;
        this.subject5 = subject5;
        this.gpa = gpa;
        this.status = status;
    }

    public int getResultId() { return resultId; }
    public void setResultId(int resultId) { this.resultId = resultId; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public double getSubject1() { return subject1; }
    public void setSubject1(double subject1) { this.subject1 = subject1; }

    public double getSubject2() { return subject2; }
    public void setSubject2(double subject2) { this.subject2 = subject2; }

    public double getSubject3() { return subject3; }
    public void setSubject3(double subject3) { this.subject3 = subject3; }

    public double getSubject4() { return subject4; }
    public void setSubject4(double subject4) { this.subject4 = subject4; }

    public double getSubject5() { return subject5; }
    public void setSubject5(double subject5) { this.subject5 = subject5; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}