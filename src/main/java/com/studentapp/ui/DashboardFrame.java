package com.studentapp.ui;

import com.studentapp.dao.ResultDAO;
import com.studentapp.dao.StudentDAO;
import com.studentapp.model.Result;
import com.studentapp.model.Student;
import com.studentapp.service.GPACalculator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DashboardFrame extends JFrame {
    private final StudentDAO studentDAO = new StudentDAO();
    private final ResultDAO resultDAO = new ResultDAO();

    // Student Registration Fields
    private JTextField rollField, nameField, courseField, branchField, contactField;
    private JComboBox<String> genderBox;
    private DefaultTableModel studentTableModel;
    private JTable studentTable;

    // Marks Fields
    private JTextField resRollField, semField, s1Field, s2Field, s3Field, s4Field, s5Field;

    public DashboardFrame() {
        setTitle("Student Result Management System - Dashboard");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Register Student", createStudentPanel());
        tabbedPane.addTab("Enter Marks & GPA", createMarksPanel());
        tabbedPane.addTab("View Result Card", createViewResultPanel());

        add(tabbedPane);
        loadStudentsTable();
    }

    private void deleteSelectedStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please click on a row in the table first to select it.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String rollNo = (String) studentTableModel.getValueAt(selectedRow, 0);
        String name = (String) studentTableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete student: " + name + " (Roll: " + rollNo + ")?\nThis will also delete all their exam results.",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (studentDAO.deleteStudent(rollNo)) {
                    JOptionPane.showMessageDialog(this, "Student deleted successfully.");
                    loadStudentsTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete student.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        rollField = new JTextField();
        nameField = new JTextField();
        courseField = new JTextField();
        branchField = new JTextField();
        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        contactField = new JTextField();

        formPanel.add(new JLabel("Roll Number:"));
        formPanel.add(rollField);
        formPanel.add(new JLabel("Full Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Course:"));
        formPanel.add(courseField);
        formPanel.add(new JLabel("Branch:"));
        formPanel.add(branchField);
        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderBox);
        formPanel.add(new JLabel("Contact:"));
        formPanel.add(contactField);

        JButton saveStudentBtn = new JButton("Register Student");
        formPanel.add(new JLabel(""));
        formPanel.add(saveStudentBtn);

        saveStudentBtn.addActionListener(e -> registerStudent());

        // Table View
        studentTableModel = new DefaultTableModel(new String[]{"Roll No", "Name", "Course", "Branch", "Gender", "Contact"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(studentTableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // Right-side panel with Table and Delete Button
        JPanel tablePanel = new JPanel(new BorderLayout(5, 5));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel tableActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton deleteBtn = new JButton("Delete Selected Student");
        deleteBtn.setForeground(new Color(180, 0, 0));
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteBtn.addActionListener(e -> deleteSelectedStudent());
        tableActions.add(deleteBtn);

        tablePanel.add(tableActions, BorderLayout.SOUTH);

        panel.add(formPanel, BorderLayout.WEST);
        panel.add(tablePanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createMarksPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        resRollField = new JTextField(15);
        semField = new JTextField(15);
        s1Field = new JTextField(15);
        s2Field = new JTextField(15);
        s3Field = new JTextField(15);
        s4Field = new JTextField(15);
        s5Field = new JTextField(15);

        String[] labels = {"Roll No:", "Semester (1-8):", "Subject 1 Marks:", "Subject 2 Marks:", "Subject 3 Marks:", "Subject 4 Marks:", "Subject 5 Marks:"};
        JTextField[] fields = {resRollField, semField, s1Field, s2Field, s3Field, s4Field, s5Field};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            panel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1; gbc.gridy = i;
            panel.add(fields[i], gbc);
        }

        JButton submitMarksBtn = new JButton("Calculate & Save Result");
        gbc.gridx = 0; gbc.gridy = labels.length; gbc.gridwidth = 2;
        panel.add(submitMarksBtn, gbc);

        submitMarksBtn.addActionListener(e -> saveMarks());
        return panel;
    }

    private JPanel createViewResultPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField searchRoll = new JTextField(10);
        JTextField searchSem = new JTextField(5);
        JButton searchBtn = new JButton("Search Result");

        searchPanel.add(new JLabel("Roll No:"));
        searchPanel.add(searchRoll);
        searchPanel.add(new JLabel("Semester:"));
        searchPanel.add(searchSem);
        searchPanel.add(searchBtn);

        JTextArea resultDisplay = new JTextArea();
        resultDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultDisplay.setEditable(false);

        searchBtn.addActionListener(e -> {
            try {
                String roll = searchRoll.getText().trim();
                int sem = Integer.parseInt(searchSem.getText().trim());
                Result r = resultDAO.getResultByRollAndSem(roll, sem);

                if (r != null) {
                    resultDisplay.setText(
                            "==============================================\n" +
                                    "            OFFICIAL REPORT CARD              \n" +
                                    "==============================================\n" +
                                    " Roll Number : " + r.getRollNo() + "\n" +
                                    " Semester    : " + r.getSemester() + "\n" +
                                    "----------------------------------------------\n" +
                                    " Subject 1   : " + r.getSubject1() + "\n" +
                                    " Subject 2   : " + r.getSubject2() + "\n" +
                                    " Subject 3   : " + r.getSubject3() + "\n" +
                                    " Subject 4   : " + r.getSubject4() + "\n" +
                                    " Subject 5   : " + r.getSubject5() + "\n" +
                                    "----------------------------------------------\n" +
                                    " Calculated GPA : " + r.getGpa() + " / 10.0\n" +
                                    " Final Status   : " + r.getStatus() + "\n" +
                                    "==============================================\n"
                    );
                } else {
                    resultDisplay.setText("No result record found for the provided Roll No and Semester.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric semester and roll number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultDisplay), BorderLayout.CENTER);
        return panel;
    }

    private void registerStudent() {
        try {
            Student s = new Student(
                    rollField.getText().trim(),
                    nameField.getText().trim(),
                    courseField.getText().trim(),
                    branchField.getText().trim(),
                    (String) genderBox.getSelectedItem(),
                    contactField.getText().trim()
            );

            if (s.getRollNo().isEmpty() || s.getName().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Roll number and Name are required.");
                return;
            }

            if (studentDAO.addStudent(s)) {
                JOptionPane.showMessageDialog(this, "Student registered successfully!");
                loadStudentsTable();
                rollField.setText(""); nameField.setText("");
                courseField.setText(""); branchField.setText(""); contactField.setText("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void saveMarks() {
        try {
            String roll = resRollField.getText().trim();
            if (!studentDAO.studentExists(roll)) {
                JOptionPane.showMessageDialog(this, "Student with Roll No " + roll + " does not exist. Register student first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int sem = Integer.parseInt(semField.getText().trim());
            double[] marks = new double[]{
                    Double.parseDouble(s1Field.getText().trim()),
                    Double.parseDouble(s2Field.getText().trim()),
                    Double.parseDouble(s3Field.getText().trim()),
                    Double.parseDouble(s4Field.getText().trim()),
                    Double.parseDouble(s5Field.getText().trim())
            };

            double gpa = GPACalculator.calculateGPA(marks);
            String status = GPACalculator.determineStatus(marks);

            Result result = new Result(roll, sem, marks[0], marks[1], marks[2], marks[3], marks[4], gpa, status);

            if (resultDAO.addOrUpdateResult(result)) {
                JOptionPane.showMessageDialog(this, "Result saved successfully!\nCalculated GPA: " + gpa + "\nStatus: " + status);
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Please ensure semester and marks are valid numbers.", "Format Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving result: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStudentsTable() {
        studentTableModel.setRowCount(0);
        try {
            List<Student> students = studentDAO.getAllStudents();
            for (Student s : students) {
                studentTableModel.addRow(new Object[]{
                        s.getRollNo(), s.getName(), s.getCourse(), s.getBranch(), s.getGender(), s.getContact()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}