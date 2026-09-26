package com.studentapp.service;

public class GPACalculator {

    /**
     * Converts individual subject marks (0-100) to a standard 10-point scale.
     */
    public static double getGradePoint(double marks) {
        if (marks >= 90) return 10.0;
        if (marks >= 80) return 9.0;
        if (marks >= 70) return 8.0;
        if (marks >= 60) return 7.0;
        if (marks >= 50) return 6.0;
        if (marks >= 40) return 5.0;
        return 0.0; // Fail grade point
    }

    /**
     * Calculates average GPA across 5 subjects.
     */
    public static double calculateGPA(double[] marks) {
        if (marks == null || marks.length == 0) return 0.0;

        double totalPoints = 0;
        for (double mark : marks) {
            totalPoints += getGradePoint(mark);
        }
        return Math.round((totalPoints / marks.length) * 100.0) / 100.0;
    }

    /**
     * Determines whether the student passed or failed (passing score: 40+ in all subjects).
     */
    public static String determineStatus(double[] marks) {
        for (double m : marks) {
            if (m < 40.0) return "FAIL";
        }
        return "PASS";
    }
}