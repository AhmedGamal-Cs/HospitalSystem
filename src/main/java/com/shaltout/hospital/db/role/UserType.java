/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shaltout.hospital.db.role;

/**
 *
 * @author ahmed
 */
public enum UserType {
    ADMIN(1,"admin"),DOCTOR(2,"doctor"),NURSE(3,"nurse"),RECEPTION(4,"reception");
    
    private int id;
    
    private String type;

    private UserType(int id, String type) {
        this.id = id;
        this.type = type;
    }
    
    public static UserType getUsersTypeById(int id){
        for(UserType userType:UserType.values()){
            if(userType.getId() == id){
                return userType;
            }
        }
        return null;
    }

    public static UserType getUsersTypeByType(String type){
        for(UserType usersType:UserType.values()){
            if(usersType.getType().toUpperCase().equals(type.toUpperCase())){
                return usersType;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
