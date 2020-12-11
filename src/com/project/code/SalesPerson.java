/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.code;

import static com.project.code.User.currentID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class SalesPerson {
     public static void salesPersonSignUp(int ID, String ActingRole, String password) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("Insert into userdetails(ID,Role,Password) values(?,?,?)");
            st.setInt(1, ID);
            st.setString(2, ActingRole);
            st.setString(3, password);
            st.executeUpdate();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean salesPersonSignIn(int ID, String password) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("Select ID,Password from userdetails where ID=? and Password=?");
            st.setInt(1, ID);
            st.setString(2, password);
            ResultSet a = st.executeQuery();
            if (a.next()) {
                currentID = ID;
                return true;
            } else {
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println(e);

        }
        return false;
    }
}
