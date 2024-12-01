package org.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.sql.*;
import static org.example.DBConnection.getConnection;

public class Customer {
    private String customerID;
    private String password;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime createdAt;

    public int fetch(String firstName , String password) throws Exception{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            String sql = "SELECT * FROM Customer WHERE first_name = ? AND pass = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String fetchedName = rs.getString("first_name");
                String fetchedPassword = rs.getString("pass");
                setFirstName(fetchedName);
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
    public void insert(String firstName, String lastName, String pass ,String email, String phone, String address) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();

            String sql = "INSERT INTO Customer (first_name, last_name, pass ,email, phone, address) VALUES (?, ?, ? , ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3,pass);
            stmt.setString(4, email);
            stmt.setString(5, phone);
            stmt.setString(6, address);

            stmt.executeUpdate();
            System.out.println("Customer inserted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting customer", e);
        }
    }
    public Integer getCustomerIdByFirstName(String firstName) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer customerId = null;

        try {

            conn = DBConnection.getConnection();


            String sql = "SELECT customer_id FROM Customer WHERE first_name = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstName);


            rs = stmt.executeQuery();


            if (rs.next()) {
                customerId = rs.getInt("customer_id");
            } else {
                System.out.println("No customer found with the first name: " + firstName);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving customer ID by first name", e);
        }
        return customerId;
    }
}
