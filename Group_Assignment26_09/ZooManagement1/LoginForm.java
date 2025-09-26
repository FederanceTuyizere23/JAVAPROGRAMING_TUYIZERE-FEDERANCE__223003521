package com.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import com.util.DB;
import com.form.ZooMain;
public class LoginForm extends JFrame implements ActionListener {
    JTextField userTxt = new JTextField("");
    JPasswordField passTxt = new JPasswordField("");
    JButton loginbtn = new JButton("Login");
    JButton cancelbtn = new JButton("Cancel");
    

    public LoginForm() {
        setTitle("====ZOO MANAGEMENT SYSTEM LOGIN FORM====");
        setBounds(100, 100, 300, 200);
        setLayout(null);
        setBackground(Color.orange);

        userTxt.setBounds(50, 30, 200, 25);
        passTxt.setBounds(50, 70, 200, 25);
        loginbtn.setBounds(50, 120, 80, 30);
        cancelbtn.setBounds(150, 120, 80, 30);

        add(userTxt);
        add(passTxt);
        add(loginbtn);
        add(cancelbtn);
        

        loginbtn.addActionListener(this);
        cancelbtn.addActionListener(this);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try (Connection con = DB.getConnection()) {
            String sql = "SELECT * FROM user WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userTxt.getText());
            ps.setString(2, new String(passTxt.getPassword()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                int userId = rs.getInt("userid");
                dispose();
                new ZooMain(role, userId);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
