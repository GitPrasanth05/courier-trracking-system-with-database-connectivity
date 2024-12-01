package org.example;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.time.LocalDateTime;
import static org.example.DBConnection.getConnection;
public class Package {
    private int PackageId;
    private int CustomerId;
    private double weight;
    private String dimension;
    private String pickupAddress;
    private String deliveryAddress;
    private String pickupDate;
    private String deliveryDate;
    private LocalDateTime createdAt;

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    private int customerId;
    private String fromName;
    private String toName;

    public int getPackageId() {
        return PackageId;
    }

    public void setPackageId(int packageId) {
        PackageId = packageId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public int getLatestPackageId() throws Exception {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT package_id FROM Package ORDER BY package_id DESC LIMIT 1";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("package_id");
            } else {
                return -1; // or throw an exception
            }
        } catch (SQLException e) {
            throw new RuntimeException("package ID not found", e);
        }
    }


    public void insert() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {

            conn = DBConnection.getConnection();


            String sql = "INSERT INTO Package (customer_id, weight, dimension, from_name, to_name, pickup_address, delivery_address, pickup_date, delivery_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


            stmt = conn.prepareStatement(sql);


            stmt.setInt(1, this.getCustomerId());
            stmt.setDouble(2, this.getWeight());
            stmt.setString(3, this.getDimension());
            stmt.setString(4, this.getFromName());
            stmt.setString(5, this.getToName());
            stmt.setString(6, this.getPickupAddress());
            stmt.setString(7, this.getDeliveryAddress());
            stmt.setTimestamp(8, Timestamp.valueOf(this.getPickupDate() + " 00:00:00"));
            stmt.setTimestamp(9, Timestamp.valueOf(this.getDeliveryDate() + " 00:00:00"));

            stmt.executeUpdate();
            System.out.println("YOUR COURIER IS READY TO POST");

        } catch (SQLException e) {
            throw new RuntimeException("SERVER IS DOWN PLEASE TRY AGAIN LATER ...", e);
        }
    }

    public int getCustomerIdByPackageId(int packageId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT customer_id FROM Package WHERE package_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, packageId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("customer_id");
            } else {
                return -1; // or throw an exception
            }
        } catch (SQLException e) {
            throw new RuntimeException("package ID is not found", e);
        }
    }
}