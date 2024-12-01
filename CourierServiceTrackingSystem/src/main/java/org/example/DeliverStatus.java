package org.example;
import java.sql.*;
public class DeliverStatus  {
    private int packageId;
    private StatusOfDelivery status;
    private String location;

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public StatusOfDelivery getStatus() {
        return status;
    }

    public void setStatus(StatusOfDelivery status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void insertIntoDatabase() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO delivery_status (package_id, status, location) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, this.getPackageId());
            pstmt.setString(2, this.getStatus().toString());
            pstmt.setString(3, this.getLocation());
            pstmt.executeUpdate();
            System.out.println("STATUS UPDATED");
        } catch (SQLException e) {
            throw new RuntimeException("STATUS NOT INSERTED", e);
        }
    }



    public StatusOfDelivery getStatusByPackageId(int packageId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT status FROM delivery_status WHERE package_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, packageId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return StatusOfDelivery.valueOf(rs.getString("status"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("PACKAGE ID NOT FOUND", e);
        }
    }

    public void updateStatusByPackageId(int packageId, String status) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE delivery_status SET status = ? WHERE package_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, packageId);
            pstmt.executeUpdate();
            System.out.println(" STATUS UPDATED SUCCESSFULLY");
        } catch (SQLException e) {
            throw new RuntimeException("TRACKING STATUS NOT UPDATED", e);
        }
    }
}