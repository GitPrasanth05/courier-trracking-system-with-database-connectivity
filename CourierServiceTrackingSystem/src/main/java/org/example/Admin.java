package org.example;
import java.sql.*;

import static org.example.DBConnection.getConnection;

public class Admin{
    private String password;
    private String AdminName;

    public String getPassword() {
        return password;
    }

    public String getAdminName() {
        return AdminName;
    }

    public int fetch() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            String sql = "SELECT * FROM Admin WHERE admin_name = ? AND pass = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, AdminName);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String fetchedName = rs.getString("admin_name");
                String fetchedPassword = rs.getString("pass");
                setAdminName(fetchedName);
                setPassword(fetchedPassword);

                System.out.println("Login successful! Welcome, " + fetchedName);
                return 1;

            } else {
                System.out.println("Invalid username or password. Please try again.");
                return -1;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  String[] fetchNotification() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT notify, pack_id FROM notification ORDER BY pack_id DESC LIMIT 1";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return new String[] {rs.getString("notify"), String.valueOf(rs.getInt("pack_id"))};
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("NO NOTIFICATION", e);
        }
    }

    public void setAdminName(String adminName) {
        AdminName = adminName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void deletePackage(int packId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM Package WHERE package_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, packId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("SERVER DOWN AND PACKAGE NOT CANCELLED", e);
        }
    }

    public void deletePayment(int packId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM payment WHERE pack_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, packId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("SERVER DOWN AND PACKAGE NOT CANCELLED", e);
        }
    }

    public void deleteDeliveryStatus(int packId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM delivery_status WHERE package_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, packId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("SERVER DOWN AND PACKAGE NOT CANCELLED", e);
        }
    }

    public void deleteNotification(int packId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM notification WHERE pack_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, packId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("SERVER DOWN AND PACKAGE NOT CANCELLED", e);
        }
    }

}