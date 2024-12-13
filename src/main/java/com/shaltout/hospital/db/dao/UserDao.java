/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shaltout.hospital.db.dao;

import com.shaltout.hospital.db.model.User;
import com.shaltout.hospital.db.role.UserType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.sql.SQLException;

/**
 *
 * @author ahmed
 */
public class UserDao implements DaoList<User>{
    private static UserDao usersDao;
    
    private UserDao(){}
    
    public static UserDao getInstance(){
        if(usersDao == null){
            usersDao=new UserDao();
        }
        return usersDao;
    }

    @Override
    public List<User> loadAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insert(User user) throws Exception {
        int count = 0;
        try (Connection conn = Dao.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "INSERT INTO USERS (ID, USER_NAME, PASSWORD, USER_TYPE_ID) VALUES (?, ?, ?, ?)"
             )) {
            ps.setInt(1, user.getId());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getUserType().getId());
            count = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the error
            throw new Exception("Error inserting User", ex);
        }
        return count;
    }


    @Override
    public int update(User user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(User user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User getData(User u) throws Exception {
        Connection conn=null;
        User user = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = Dao.getConnection();
            String sql="SELECT * FROM USERS WHERE USER_NAME='"+u.getUserName()+"' "
                    + "AND PASSWORD='"+u.getPassword()+"'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                user=new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("USER_NAME"));
                user.setPassword(rs.getString("PASSWORD"));
                UserType userType=UserType.getUsersTypeById(rs.getInt("USER_TYPE_ID"));
                user.setUserType(userType);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            ps.close();
            Dao.closeConnection(conn);
        }
        return user;
    }

    @Override
    public User getDataById(int id) throws Exception {
        Connection conn=null;
        User user = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = Dao.getConnection();
            String sql="SELECT * FROM USERS WHERE ID=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                user=new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("USERS_NAME"));
                user.setPassword(rs.getString("PASSWORD"));
                UserType userType=UserType.getUsersTypeById(rs.getInt("USERS_TYPE"));
                user.setUserType(userType);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            ps.close();
            Dao.closeConnection(conn);
        }
        return user;        
    }
    
}
