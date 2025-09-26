package com.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.*;
import java.sql.*;
import com.util.DB;

public class AnimalPanel extends JPanel implements ActionListener {
    JTextField idTxt = new JTextField();
    JTextField nameTxt = new JTextField();
    JTextField typeTxt = new JTextField();
    JTextField ageTxt = new JTextField();
    JTextField cageTxt = new JTextField();

    JButton addBtn = new JButton("Add");
    JButton updateBtn = new JButton("Update");
    JButton deleteBtn = new JButton("Delete");
    JButton retrieveBtn = new JButton("Retrieve");

    JTable table;
    DefaultTableModel model;

    public AnimalPanel() {
        setLayout(null);
        String[] columns = {"ID", "Name", "Type", "Age", "Cage"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 250, 800, 300);
        add(sp);
        setBackground(Color.orange);

        int y = 20;
        addField("ID", idTxt, y); idTxt.setEditable(false); y+=30;
        addField("Name", nameTxt, y); y+=30;
        addField("Type", typeTxt, y); y+=30;
        addField("Age", ageTxt, y); y+=30;
        addField("Cage", cageTxt, y);

        addButtons();
        loadAnimals();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if(row >= 0) {
                    idTxt.setText(model.getValueAt(row, 0).toString());
                    nameTxt.setText(model.getValueAt(row, 1).toString());
                    typeTxt.setText(model.getValueAt(row, 2).toString());
                    ageTxt.setText(model.getValueAt(row, 3).toString());
                    cageTxt.setText(model.getValueAt(row, 4).toString());
                }
            }
        });
    }

    private void addField(String lbl, JTextField txt, int y){
        JLabel l = new JLabel(lbl);
        l.setBounds(20, y, 80, 25);
        txt.setBounds(100, y, 150, 25);
        add(l); add(txt);
    }

    private void addButtons() {
        addBtn.setBounds(300, 20, 100, 30);
        updateBtn.setBounds(300, 60, 100, 30);
        deleteBtn.setBounds(300, 100, 100, 30);
        retrieveBtn.setBounds(300, 140, 100, 30);
        add(addBtn); add(updateBtn); add(deleteBtn); add(retrieveBtn);
        addBtn.addActionListener(this); updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this); retrieveBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try(Connection con = DB.getConnection()){
            if(e.getSource() == addBtn){
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Animals(Name,Type,Age,CageNumber) VALUES(?,?,?,?)"
                );
                ps.setString(1, nameTxt.getText());
                ps.setString(2, typeTxt.getText());
                ps.setInt(3, Integer.parseInt(ageTxt.getText()));
                ps.setString(4, cageTxt.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this,"Employee Added!!!!!!");
            } else if(e.getSource() == updateBtn && !idTxt.getText().isEmpty()){
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE Animals SET Name=?, Type=?, Age=?, CageNumber=? WHERE AnimalID=?"
                );
                ps.setString(1, nameTxt.getText());
                ps.setString(2, typeTxt.getText());
                ps.setInt(3, Integer.parseInt(ageTxt.getText()));
                ps.setString(4, cageTxt.getText());
                ps.setInt(5, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this,"Employee Updated!!!!!");
            } else if(e.getSource() == deleteBtn && !idTxt.getText().isEmpty()){
                PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM Animals WHERE AnimalID=?"
                );
                ps.setInt(1, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this,"Employee Deleted!!!!!");
            }
            loadAnimals();
            clearFields();
        } catch(Exception ex){ ex.printStackTrace(); }
    }

    private void loadAnimals(){
        try(Connection con = DB.getConnection()){
            model.setRowCount(0);
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Animals");
            while(rs.next()){
                model.addRow(new Object[]{
                    rs.getInt("AnimalID"),
                    rs.getString("Name"),
                    rs.getString("Type"),
                    rs.getInt("Age"),
                    rs.getString("CageNumber")
                });
            }
        } catch(Exception ex){ ex.printStackTrace(); }
    }

    private void clearFields(){
        idTxt.setText(""); nameTxt.setText(""); typeTxt.setText(""); ageTxt.setText(""); cageTxt.setText("");
    }
}
