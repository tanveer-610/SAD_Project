/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.code;

import com.project.ui.UpdateProfile;
import com.project.ui.ViewProfile;
import java.sql.*;

/**
 *
 * @author USER
 */
public class User {

    public static int currentID;

    public static void signUp(int ID, String ActingRole, String password) {
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

    public static boolean signIn(int ID, String password) {
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

    public static void showPrevious() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement s = con.createStatement();

            ResultSet r = s.executeQuery("Select * from userdetails where ID=" + currentID);
            if (r.next()) {
                UpdateProfile.prev(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getString(7), r.getString(9));
            }
           
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void updateInfo(String FN, String Email, String AE, String Phone, String pass, String DOB, String Address) {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement s = con.createStatement();
            ResultSet r = s.executeQuery("Select * from userdetails where ID=" + currentID);
            if (r.next()) {

                PreparedStatement st = con.prepareStatement("update userdetails Set FullName=?,Email=?,AlternateEmail=?,Phone=?,Password=?,DOB=?,Address=? where ID=?");

                if (!FN.isEmpty()) {
                    st.setString(1, FN);
                } else {
                    st.setString(1, r.getString(2));
                }

                if (!Email.isEmpty()) {
                    st.setString(2, Email);
                } else {
                    st.setString(2, r.getString(3));
                }

                if (!AE.isEmpty()) {
                    st.setString(3, AE);
                } else {
                    st.setString(3, r.getString(4));
                }

                if (!Phone.isEmpty()) {
                    st.setString(4, Phone);
                } else {
                    st.setString(4, r.getString(5));
                }

                if (!pass.isEmpty()) {
                    st.setString(5, pass);
                } else {
                    st.setString(5, r.getString(6));
                }

                if (!DOB.isEmpty()) {
                    st.setString(6, DOB);
                } else {
                    st.setString(6, r.getString(7));
                }

                if (!Address.isEmpty()) {
                    st.setString(7, Address);
                } else {
                    st.setString(7, r.getString(8));
                }
                st.setInt(8, currentID);

                st.executeUpdate();
            }
            
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void viewProfile() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement s = con.createStatement();
            ResultSet r = s.executeQuery("Select * from userdetails where ID=" + currentID);
            if (r.next()) {
                ViewProfile.view(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getString(7), r.getString(8), r.getString(9));
            }
             
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static boolean checkUniqueUID(int a) {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dailysalesrecord?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement s = con.createStatement();
            ResultSet r = s.executeQuery("Select ID from userdetails where ID=" + a);
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
}
