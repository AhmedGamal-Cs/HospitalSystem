/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shaltout.hospital.db.dao;

import com.shaltout.hospital.db.model.PatientInfo;
import com.shaltout.hospital.db.model.User;
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
public class PatientInfoDao implements DaoList<PatientInfo>{

    private static PatientInfoDao patientInfoDao;
    
    private PatientInfoDao(){}
    
    public static PatientInfoDao getInstance(){
        if(patientInfoDao == null) patientInfoDao = new PatientInfoDao();
        return patientInfoDao;
    }
    
    @Override
    public List<PatientInfo> loadAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insert(PatientInfo t) throws Exception {
        int count = 0;
        Connection conn = Dao.getConnection();
        PreparedStatement ps = null;
        try {

            String sql = "INSERT INTO PATIENT_INFO(ID,FIRST_NAME,LAST_NAME,MOBILE,EMAIL,USER_ID)"
                    + " VALUES(?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, t.getId());
            ps.setString(2, t.getFirstName());
            ps.setString(3, t.getLastName());
            ps.setString(4, t.getMobile());
            ps.setString(5, t.getEmail());
            ps.setInt(6, t.getUser().getId());
            
            count = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDetailsDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (ps != null) ps.close();
            Dao.closeConnection(conn);
        }
        return count; 
    }

    @Override
    public int update(PatientInfo t) throws Exception {

        int count = 0;
        Connection conn = Dao.getConnection();
        PreparedStatement ps = null;
        try {
            // update USERS table
            String sql = "UPDATE PATIENT_INFO SET FIRST_NAME=?,LAST_NAME=?,EMAIL=?,MOBILE=?,USER_ID=? WHERE ID=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, t.getFirstName());
            ps.setString(2, t.getLastName());
            ps.setString(3, t.getEmail());
            ps.setString(4, t.getMobile());
            ps.setInt(5, t.getUser().getId());
            ps.setInt(6, t.getId());
            
            count = ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage()+"");
        } finally {
            if (ps != null) ps.close();
            Dao.closeConnection(conn);
        }
        return count;
    }

    @Override
    public int delete(PatientInfo t) throws Exception {
        int count = 0;
        Connection conn = Dao.getConnection();
        conn.setAutoCommit(false);
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM PATIENT_INFO WHERE ID=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, t.getId());
            count = ps.executeUpdate();

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
    public PatientInfo getData(PatientInfo t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PatientInfo getDataById(int id) throws Exception {
        Connection conn=null;
        PatientInfo patientInfo = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try{
            conn = Dao.getConnection();
            String sql="SELECT * FROM PATIENT_INFO WHERE ID=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                patientInfo=new PatientInfo();
                patientInfo.setId(rs.getInt("id"));
                patientInfo.setFirstName(rs.getString("FIRST_NAME"));
                patientInfo.setLastName(rs.getString("LAST_NAME"));
                user = new User();
                user.setId(rs.getInt("USER_ID"));
                patientInfo.setUser(user);
                patientInfo.setEmail(rs.getString("EMAIL"));
                patientInfo.setMobile(rs.getString("MOBILE"));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            rs.close();
            ps.close();
            Dao.closeConnection(conn);
        }
        return patientInfo;     
    }
    
}
