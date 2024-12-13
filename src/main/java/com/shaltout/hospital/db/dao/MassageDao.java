/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shaltout.hospital.db.dao;

import com.shaltout.hospital.db.model.Massage;
import com.shaltout.hospital.db.model.PatientInfo;
import com.shaltout.hospital.db.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ahmed
 */
public class MassageDao implements DaoList<Massage>{
    
    private static MassageDao messageDao;
    
    private MassageDao(){}
    
    public static MassageDao getInstance(){
        if(messageDao == null)
            messageDao = new MassageDao();
        return messageDao;
    }

    @Override
    public List<Massage> loadAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insert(Massage t) throws Exception {
        int count = 0;
        try (Connection conn = Dao.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "INSERT INTO MESSAGES (MESSAGE_BODY, MESSAGE_DATE, FROM_USER, TO_USER, PATIENT_ID) VALUES (?, ?, ?, ?, ?)"
             )) {
            ps.setString(1, t.getMessageBody());
            ps.setDate(2, t.getMessageDate());
            ps.setInt(3, t.getFromUser().getId());
            ps.setInt(4, t.getToUser().getId());
            ps.setInt(5, t.getPatientInfo().getId());
            count = ps.executeUpdate();
            Dao.closeConnection(conn);
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the error
            throw new Exception("Error inserting User", ex);
        }
        
        return count;
    }

    @Override
    public int update(Massage t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(Massage t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Massage getData(Massage t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Massage getDataById(int id) throws Exception {
        Connection conn=null;
        Massage message = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PatientInfo patientInfo = null;
        User fromUser = null;
        User toUser = null;
        try{
            conn = Dao.getConnection();
            String sql="SELECT * FROM MESSAGES WHERE ID=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                message = new Massage();
                message.setMessageBody(rs.getString("MESSAGE_BODY"));
                fromUser = new User();
                toUser = new User();
                patientInfo = new PatientInfo();
                fromUser.setId(rs.getInt("FROM_USER")) ;
                toUser.setId(rs.getInt("TO_USER"));
                patientInfo.setId(rs.getInt("PATIENT_ID"));
                message.setFromUser(fromUser);
                message.setToUser(toUser);
                message.setMessageDate(rs.getDate("MESSAGE_DATE"));
                message.setPatientInfo(patientInfo);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            rs.close();
            ps.close();
            Dao.closeConnection(conn);
        }
        return message;    
    }
    
    public Massage getDataByPatientIdAndUserId(int patientId, int userId) throws Exception{
        Connection conn=null;
        Massage message = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PatientInfo patientInfo = null;
        User fromUser = null;
        User toUser = null;
        try{
            conn = Dao.getConnection();
            String sql="SELECT * FROM MESSAGES WHERE FROM_USER=? AND PATIENT_ID=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, patientId);
            rs = ps.executeQuery();
            
            while(rs.next()){
                fromUser = new User();
                fromUser.setId(rs.getInt("FROM_USER"));
                toUser = new User();
                toUser.setId(rs.getInt("TO_USER"));
                patientInfo = new PatientInfo();
                patientInfo.setId(rs.getInt("PATIENT_ID"));
                
                message = new Massage();
                message.setId(rs.getInt("ID"));
                message.setMessageBody(rs.getString("MESSAGE_BODY"));
                message.setFromUser(fromUser);
                message.setMessageDate(rs.getDate("MESSAGE_DATE"));
                message.setToUser(toUser);
                message.setPatientInfo(patientInfo);
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            rs.close();
            ps.close();
            Dao.closeConnection(conn);
        }
        return message;           
    }
}
