package com.Panel;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import com.util.DB;

public class EmployeePanel extends JPanel implements ActionListener {
    JTextField idTxt = new JTextField();
    JTextField nameTxt = new JTextField();
    JTextField roleTxt = new JTextField();
    JTextField phoneTxt = new JTextField();

    JButton addBtn = new JButton("Add");
    JButton updateBtn = new JButton("Update");
    JButton deleteBtn = new JButton("Delete");
    JButton retrieveBtn = new JButton("Retrieve");

    JTable table;
    DefaultTableModel model;

    public EmployeePanel() {
        setLayout(null);

        String[] labels = {"ID", "Name", "Role", "Phone"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 250, 800, 300);
        setBackground(Color.orange);

        int y = 20;
        addField("ID", idTxt, y);
        idTxt.setEditable(false);
        y += 30;
        addField("Name", nameTxt, y); y += 30;
        addField("Role", roleTxt, y); y += 30;
        addField("Phone", phoneTxt, y);

        addButtons();
        add(sp);

        loadEmployees();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if(row >= 0){
                    idTxt.setText(model.getValueAt(row, 0).toString());
                    nameTxt.setText(model.getValueAt(row, 1).toString());
                    roleTxt.setText(model.getValueAt(row, 2).toString());
                    phoneTxt.setText(model.getValueAt(row, 3).toString());
                }
            }
        });
    }

    private void addField(String lbl, JTextField txt, int y){
        JLabel l = new JLabel(lbl);
        l.setBounds(20, y, 80, 25);
        txt.setBounds(100, y, 150, 25);
        add(l); 
        add(txt);
    }

    private void addButtons(){
        addBtn.setBounds(300,20,100,30);
        updateBtn.setBounds(300,60,100,30);
        deleteBtn.setBounds(300,100,100,30);
        retrieveBtn.setBounds(300,140,100,30);

        add(addBtn); add(updateBtn); add(deleteBtn); add(retrieveBtn);

        addBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        retrieveBtn.addActionListener(this);
    }

    private void loadEmployees(){
        try(Connection con = DB.getConnection()){
            model.setRowCount(0); // Clear existing rows
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Employees");
            while(rs.next()){
                model.addRow(new Object[]{
                    rs.getInt("EmployeeID"),
                    rs.getString("Name"),
                    rs.getString("Role"),
                    rs.getString("Phone")
                });
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void clearFields(){
        idTxt.setText("");
        nameTxt.setText("");
        roleTxt.setText("");
        phoneTxt.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e){
        try(Connection con = DB.getConnection()){
            if(e.getSource() == addBtn){
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Employees(Name,Role,Phone) VALUES(?,?,?)");
                ps.setString(1, nameTxt.getText());
                ps.setString(2, roleTxt.getText());
                ps.setString(3, phoneTxt.getText());
                ps.executeUpdate();
                loadEmployees();
                clearFields();
                JOptionPane.showMessageDialog(this,"Employee Added!");
            }
            else if(e.getSource() == updateBtn){
                if(idTxt.getText().isEmpty()) return;
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE Employees SET Name=?,Role=?,Phone=? WHERE EmployeeID=?");
                ps.setString(1, nameTxt.getText());
                ps.setString(2, roleTxt.getText());
                ps.setString(3, phoneTxt.getText());
                ps.setInt(4, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                loadEmployees();
                clearFields();
                JOptionPane.showMessageDialog(this,"Employee Updated!");
            }
            else if(e.getSource() == deleteBtn){
                if(idTxt.getText().isEmpty()) return;
                PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM Employees WHERE EmployeeID=?");
                ps.setInt(1, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                loadEmployees();
                clearFields();
                JOptionPane.showMessageDialog(this,"Employee Deleted!");
            }
            else if(e.getSource() == retrieveBtn){
                loadEmployees();
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
