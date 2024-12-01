package org.example;
import java.sql.*;
public class DBmanager {
    public int dbId =42;
    public String dbPass = "database123";
    public void insertAdmin(int adminId, String adminName, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO Admin (admin_id, admin_name, pass) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, adminId);
            pstmt.setString(2, adminName);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            System.out.println("Admin inserted successfully!");
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting admin", e);
        }
    }
}