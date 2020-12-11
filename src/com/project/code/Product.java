/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.code;

import com.project.ui.Sales;
import com.project.ui.UpdateStock;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class Product {

    public static void addNewProductInfo(int PID, String Name, String Type, double UnitPrice, int Quantity) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("Insert into product(PID,Name,Type,UnitPrice,Quantity) values(?,?,?,?,?)");
            st.setInt(1, PID);
            st.setString(2, Name);
            st.setString(3, Type);
            st.setDouble(4, UnitPrice);
            st.setInt(5, Quantity);
            st.executeUpdate();

            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void updateProductStock() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

            PreparedStatement st = con.prepareStatement("select * from product");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String PID = String.valueOf(rs.getInt("PID"));
                String Name = rs.getString("Name");
                String Type = rs.getString("Type");
                String UnitPrice = String.valueOf(rs.getDouble("UnitPrice"));
                String Quantity = String.valueOf(rs.getInt("Quantity"));
                UpdateStock.allProductInfo(PID, Name, UnitPrice, Quantity); 
            }
                
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static boolean checkUniquePID(int a) {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement s = con.createStatement();
            ResultSet r = s.executeQuery("Select PID from product where PID=" + a);
            if (r.next()) {
                return false;
            } else {
                return true;
            }
              
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public static void updateUPriceQnty(String pid, String UP, String qnty) {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement s = con.createStatement();
            int productID = Integer.parseInt(pid);

            ResultSet r = s.executeQuery("Select * from product where PID=" + productID);
            if (r.next()) {

                PreparedStatement st = con.prepareStatement("update product Set UnitPrice=?,Quantity=? where PID=?");

                if (!UP.isEmpty()) {
                    double uprice = Double.parseDouble(UP);
                    st.setDouble(1, uprice);
                } else {
                    st.setDouble(1, r.getInt(4));
                }

                if (!qnty.isEmpty()) {
                    int qn = Integer.parseInt(qnty);
                    st.setInt(2, qn);
                } else {
                    st.setInt(2, r.getInt(5));
                }
                st.setInt(3, productID);

                st.executeUpdate();
            }
            
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e);
        }
    }

    public static void addForSell(String p, String qnt) {
        try {
            int q = Integer.parseInt(qnt);
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement s = con.createStatement();
            int pid = Integer.parseInt(p);
            ResultSet rs = s.executeQuery("Select * from product where PID=" + pid);
            if (rs.next()) {
                if (rs.getInt(5) >= q) {
                    String PID = String.valueOf(rs.getInt("PID"));
                    String Name = rs.getString("Name");
                    String Type = rs.getString("Type");
                    String UnitPrice = String.valueOf(rs.getDouble("UnitPrice"));
                    String Quantity = String.valueOf(q);
                    String subtotal = String.valueOf(rs.getDouble("UnitPrice") * q);
                    Sales.sellDataAdd(PID, Name, qnt, UnitPrice, subtotal);
                    updateStock(pid, q);
                } else {
                    JOptionPane.showMessageDialog(null, "Quantity short in stock");
                }
            }
            
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e);
        }
    }

    public static void updateStock(int pid, int qnty) {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement s = con.createStatement();
            ResultSet r = s.executeQuery("Select * from product where PID=" + pid);
            if (r.next()) {

                PreparedStatement st = con.prepareStatement("update product Set Quantity=? where PID=?");
                qnty = r.getInt(5) - qnty;
                st.setInt(1, qnty);

                st.setInt(2, pid);

                st.executeUpdate();
            }

             
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void disSelected(int pid, int qnty) {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement s = con.createStatement();

            ResultSet r = s.executeQuery("Select * from product where PID=" + pid);
            if (r.next()) {

                PreparedStatement st = con.prepareStatement("update product Set Quantity=? where PID=?");
                qnty = r.getInt(5) + qnty;
                st.setInt(1, qnty);
                st.setInt(2, pid);

                st.executeUpdate();
            }
           
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
