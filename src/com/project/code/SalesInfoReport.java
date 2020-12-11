/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.code;

import com.project.ui.SalesReport;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class SalesInfoReport {
    public static void transactionInfo(String time, String amount,int uID) {
        try {
            double am=Double.parseDouble(amount);
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("Insert into sales(TransactionTime,Amount,TransactionBy) values(?,?,?)");
            st.setString(1, time);
            st.setDouble(2,am);
            st.setInt(3, uID);
            st.executeUpdate();
            
             
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e);
        }
    }
    
     public static void transactionTrack() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("select * from sales");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String TID = String.valueOf(rs.getInt("TransID"));
                String Time = rs.getString("TransactionTime");
                String amount = String.valueOf(rs.getDouble("Amount"));
                String UID=String.valueOf(rs.getInt("TransactionBy"));
                
                SalesReport.allTransactionInfo(TID, Time, amount, UID);

               
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
