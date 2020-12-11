/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class AdminWork {
        public static boolean checkUser(int id) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            
            PreparedStatement st = con.prepareStatement("select * from userdetails where ID="+id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                
                String role = rs.getString("Role");
                if(role.equalsIgnoreCase("Manager"))
                {
                    return true;
                }else
                {
                    return false;
                }        
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

        public static boolean removeUser(int id) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            
            PreparedStatement st = con.prepareStatement("delete from userdetails where ID="+id);
            st.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
       
}
