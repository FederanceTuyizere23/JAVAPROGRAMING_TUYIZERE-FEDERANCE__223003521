package com.Panel;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import com.util.DB;

public class UserPanel extends JPanel implements ActionListener {

    JTextField idTxt = new JTextField();
    JTextField usernameTxt = new JTextField();
    JPasswordField passwordTxt = new JPasswordField();
    JTextField roleTxt = new JTextField();

    JButton addBtn = new JButton("Add");
    JButton updateBtn = new JButton("Update");
    JButton deleteBtn = new JButton("Delete");
    JButton loadBtn = new JButton("Load");

    JTable table;
    DefaultTableModel model;

    public UserPanel() {
        setLayout(null);

        String[] labels = {"ID", "Username", "Password", "Role"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 250, 800, 300);
        setBackground(Color.orange);
        

        int y = 20;
        addField("ID", idTxt, y);
        idTxt.setEditable(false);
        y += 30;
        addField("Username", usernameTxt, y); y += 30;
        addField("Password", passwordTxt, y); y += 30;
        addField("Role", roleTxt, y);

        addButtons();
        add(sp);

        loadUsers();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if(row >= 0){
                    idTxt.setText(model.getValueAt(row, 0).toString());
                    usernameTxt.setText(model.getValueAt(row, 1).toString());
                    passwordTxt.setText(model.getValueAt(row, 2).toString());
                    roleTxt.setText(model.getValueAt(row, 3).toString());
                }
            }
        });
    }

    private void addField(String lbl, JTextField txt, int y){
        JLabel l = new JLabel(lbl);
        l.setBounds(20,y,80,25);
        txt.setBounds(100,y,150,25);
        add(l); add(txt);
    }

    private void addField(String lbl, JPasswordField txt, int y){
        JLabel l = new JLabel(lbl);
        l.setBounds(20,y,80,25);
        txt.setBounds(100,y,150,25);
        add(l); add(txt);
    }

    private void addButtons(){
        addBtn.setBounds(300,20,100,30);
        updateBtn.setBounds(300,60,100,30);
        deleteBtn.setBounds(300,100,100,30);
        loadBtn.setBounds(300,140,100,30);

        add(addBtn); add(updateBtn); add(deleteBtn); add(loadBtn);

        addBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        loadBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        try(Connection con = DB.getConnection()){
            if(e.getSource() == addBtn){
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO user(username,password,role) VALUES(?,?,?)");
                ps.setString(1, usernameTxt.getText());
                ps.setString(2, new String(passwordTxt.getPassword()));
                ps.setString(3, roleTxt.getText());
                ps.executeUpdate();
                loadUsers();
                clearFields();
                JOptionPane.showMessageDialog(this,"User Added!");
            }
            else if(e.getSource() == updateBtn){
                if(idTxt.getText().isEmpty()) return;
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE user SET username=?,password=?,role=? WHERE userid=?");
                ps.setString(1, usernameTxt.getText());
                ps.setString(2, new String(passwordTxt.getPassword()));
                ps.setString(3, roleTxt.getText());
                ps.setInt(4, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                loadUsers();
                clearFields();
                JOptionPane.showMessageDialog(this,"User Updated!");
            }
            else if(e.getSource() == deleteBtn){
                if(idTxt.getText().isEmpty()) return;
                PreparedStatement ps = con.prepareStatement("DELETE FROM user WHERE userid=?");
                ps.setInt(1, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                loadUsers();
                clearFields();
                JOptionPane.showMessageDialog(this,"User Deleted!");
            }
            else if(e.getSource() == loadBtn){
                loadUsers();
            }
        }catch(Exception ex){ ex.printStackTrace(); }
    }

    private void loadUsers(){
        try(Connection con = DB.getConnection()){
            model.setRowCount(0);
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM user");
            while(rs.next()){
                model.addRow(new Object[]{
                    rs.getInt("userid"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                });
            }
        }catch(Exception ex){ ex.printStackTrace(); }
    }

    private void clearFields(){
        idTxt.setText(""); usernameTxt.setText(""); passwordTxt.setText(""); roleTxt.setText("");
    }
}
