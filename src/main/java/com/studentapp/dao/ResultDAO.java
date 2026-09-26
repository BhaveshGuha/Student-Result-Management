package com.studentapp.dao;

import com.studentapp.db.DBConnection;
import com.studentapp.model.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultDAO {

    public boolean addOrUpdateResult(Result result) throws SQLException {
        String sql = "INSERT INTO results (roll_no, semester, subject1, subject2, subject3, subject4, subject5, gpa, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "subject1 = VALUES(subject1), subject2 = VALUES(subject2), subject3 = VALUES(subject3), " +
                "subject4 = VALUES(subject4), subject5 = VALUES(subject5), gpa = VALUES(gpa), status = VALUES(status)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, result.getRollNo());
            ps.setInt(2, result.getSemester());
            ps.setDouble(3, result.getSubject1());
            ps.setDouble(4, result.getSubject2());
            ps.setDouble(5, result.getSubject3());
            ps.setDouble(6, result.getSubject4());
            ps.setDouble(7, result.getSubject5());
            ps.setDouble(8, result.getGpa());
            ps.setString(9, result.getStatus());
            return ps.executeUpdate() > 0;
        }
    }

    public Result getResultByRollAndSem(String rollNo, int semester) throws SQLException {
        String sql = "SELECT * FROM results WHERE roll_no = ? AND semester = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rollNo);
            ps.setInt(2, semester);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Result res = new Result(
                            rs.getString("roll_no"),
                            rs.getInt("semester"),
                            rs.getDouble("subject1"),
                            rs.getDouble("subject2"),
                            rs.getDouble("subject3"),
                            rs.getDouble("subject4"),
                            rs.getDouble("subject5"),
                            rs.getDouble("gpa"),
                            rs.getString("status")
                    );
                    res.setResultId(rs.getInt("result_id"));
                    return res;
                }
            }
        }
        return null;
    }
}