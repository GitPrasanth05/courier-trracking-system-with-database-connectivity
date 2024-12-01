package org.example;
import java.sql.*;

public class Payment {
    private int price;
    private int CustomerID;


    public int getPrice() {return price;}

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void insertNotification(int packid) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO notification (pack_id, notify) VALUES (?, ' NEW PACKAGE ASSIGNED : ')";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, packid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting notification", e);
        }
    }

        public void insert(int packid) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO Payment (pack_id ,customer_id ,price, status) VALUES (?,?,?, 'SUCCESSFULL')";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, packid);
            pstmt.setInt(2, this.CustomerID);
            pstmt.setInt(3, this.price);
            pstmt.executeUpdate();
            System.out.println("Payment successfull.");
        } catch (SQLException e) {
            throw new RuntimeException("payment server down if debitted it will be refunded within 48 HRS", e);
        }
    }

    public int getPriceByPackId(int packId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT price FROM payment WHERE pack_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, packId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("price");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException("pack ID not found", e);
        }
    }

}