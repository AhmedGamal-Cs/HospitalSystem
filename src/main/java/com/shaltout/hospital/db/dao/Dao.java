package com.shaltout.hospital.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Dao {
    private static final String URL = "jdbc:mysql://localhost:3306/HOSPITAL_SYSTEM";
    private static final String USERNAME = "abstract-programmer";
    private static final String PASSWORD = "22904110";

    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            throw new SQLException("Database connection failed: Driver not found.", e);
        }
    }
    
    public static void closeConnection(Connection conn) throws Exception{
        if(conn != null){
            conn.close();
        }
    }
}
