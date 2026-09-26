package com.studentapp.dao;

import com.studentapp.auth.PasswordUtil;
import com.studentapp.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    public boolean authenticate(String username, String rawPassword) {
        String sql = "SELECT password_hash FROM admin_users WHERE username = ?";
        String hashedInput = PasswordUtil.hashPassword(rawPassword);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    return storedHash.equalsIgnoreCase(hashedInput);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}