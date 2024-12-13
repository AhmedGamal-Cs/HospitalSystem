/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shaltout.hospital.db.dao;
import com.shaltout.hospital.db.model.User;
import com.shaltout.hospital.db.model.UserDetails;
import com.shaltout.hospital.db.role.UserType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ahmed
 */
public class UserDetailsDao implements DaoList<UserDetails> {
    private static UserDetailsDao userDetailsDao;
    
    private UserDetailsDao(){}
    
    public static UserDetailsDao getInstance(){
        if(userDetailsDao == null){
            userDetailsDao = new UserDetailsDao();
        }
        return userDetailsDao;
    }

    @Override
    public List<UserDetails> loadAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insert(UserDetails t) throws Exception {
        int count = 0;
        Connection conn = Dao.getConnection();
        conn.setAutoCommit(false);
        PreparedStatement ps = null;
        try {
            // Insert into USERS table
            String userSql = "INSERT INTO USERS VALUES(?,?,?,?)";
            ps = conn.prepareStatement(userSql);
            ps.setInt(1, t.getUser().getId());
            ps.setString(2, t.getUser().getUserName());
            ps.setString(3, t.getUser().getPassword());
            ps.setInt(4, t.getUser().getUserType().getId());
            ps.executeUpdate();

            // Insert into USERS_DETAILS table
            String userDetailsSql = "INSERT INTO USERS_DETAILS(FIRST_NAME,LAST_NAME,MOBILE,USER_ID,SPECIALIZATION) VALUES(?,?,?,?,?)";
            ps = conn.prepareStatement(userDetailsSql);
            ps.setString(1, t.getFirstName());
            ps.setString(2, t.getLastName());
            ps.setString(3, t.getMobile());
            ps.setInt(4, t.getUser().getId());
            ps.setString(5, t.getSpecialization());
            ps.executeUpdate();

            conn.commit();
            count=1;
        } catch (SQLException ex) {
            conn.rollback();
            Logger.getLogger(UserDetailsDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (ps != null) ps.close();
            Dao.closeConnection(conn);
        }
        return count;
    }

    @Override
    public int update(UserDetails t) throws Exception {
        int count = 0;
        Connection conn = Dao.getConnection();
        conn.setAutoCommit(false);
        PreparedStatement ps = null;
        try {
            String userSql = "UPDATE USERS SET USER_NAME=?,PASSWORD=?,USER_TYPE_ID=? WHERE ID=?";
            ps = conn.prepareStatement(userSql);
            ps.setString(1, t.getUser().getUserName());
            ps.setString(2, t.getUser().getPassword());
            ps.setInt(3, t.getUser().getUserType().getId());
            ps.setInt(4, t.getUser().getId());
            ps.executeUpdate();
            
            String userDetailsSql = "UPDATE USERS_DETAILS SET FIRST_NAME=?,LAST_NAME=?,MOBILE=? WHERE USER_ID=?";
            ps = conn.prepareStatement(userDetailsSql);
            ps.setString(1, t.getFirstName());
            ps.setString(2, t.getLastName());
            ps.setString(3, t.getMobile());
            ps.setInt(4, t.getUser().getId());
            ps.executeUpdate();

            conn.commit();
            count = 1;
        } catch (SQLException ex) {
            conn.rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage()+"");
        } finally {
            if (ps != null) ps.close();
            Dao.closeConnection(conn);
        }
        return count;
    }

    @Override
    public int delete(UserDetails t) throws Exception {
        int count = 0;
        Connection conn = Dao.getConnection();
        conn.setAutoCommit(false);
        PreparedStatement ps = null;
        try {
            // delete from USERS_DETAILS table
            String userDetailsSql = "DELETE FROM USERS_DETAILS WHERE USER_ID=?";
            ps = conn.prepareStatement(userDetailsSql);
            ps.setInt(1, t.getUser().getId());
            ps.executeUpdate();

            // delete from USERS table
            String userSql = "DELETE FROM USERS WHERE ID=?";
            ps = conn.prepareStatement(userSql);
            ps.setInt(1, t.getUser().getId());
            ps.executeUpdate();
            
            conn.commit();
            count = 1;
        } catch (SQLException ex) {
            conn.rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage()+"");
        } finally {
            if (ps != null) ps.close();
            Dao.closeConnection(conn);
        }
        return count;
    }

    @Override
    public UserDetails getData(UserDetails t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody        
    }

    @Override
    public UserDetails getDataById(int id) throws Exception {
        String sql = "SELECT U.ID, U.USER_NAME, U.PASSWORD, U.USER_TYPE_ID, "
           + "UD.FIRST_NAME, UD.LAST_NAME, UD.MOBILE, UD.SPECIALIZATION " // Ensure there is a space after U.SPECIALIZATION
           + "FROM USERS U "
           + "INNER JOIN USERS_DETAILS UD ON U.ID = UD.USER_ID "
           + "WHERE U.ID = ?";


        try (Connection conn = Dao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserDetails ud = new UserDetails();
                    User u = new User();

                    // Populate User object
                    u.setId(rs.getInt("ID"));
                    u.setUserName(rs.getString("USER_NAME"));
                    u.setPassword(rs.getString("PASSWORD"));
                    
                    // Retrieve UserType by ID (Ensure this method works as intended)
                    UserType ut = UserType.getUsersTypeById(rs.getInt("USER_TYPE_ID"));
                    u.setUserType(ut);

                    // Populate UserDetails object
                    ud.setFirstName(rs.getString("FIRST_NAME"));
                    ud.setLastName(rs.getString("LAST_NAME"));
                    ud.setMobile(rs.getString("MOBILE"));
                    ud.setUser(u);
                    ud.setSpecialization(rs.getString("SPECIALIZATION"));
                    return ud; // Return populated UserDetails
                }
            }
        } catch (SQLException ex) {
            // Log the exception and rethrow as a custom or general exception
            ex.printStackTrace();
            throw new Exception("Error retrieving user details: " + ex.getMessage());
        }

        // Return null or throw an exception if no data found
        return null;
    }

}
