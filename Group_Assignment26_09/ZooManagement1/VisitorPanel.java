package com.Panel;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import com.util.DB;

public class VisitorPanel extends JPanel implements ActionListener {
    JTextField idTxt = new JTextField();
    JTextField nameTxt = new JTextField();
    JTextField ticketTxt = new JTextField();
    JTextField dateTxt = new JTextField();

    JButton addBtn = new JButton("Add");
    JButton updateBtn = new JButton("Update");
    JButton deleteBtn = new JButton("Delete");
    JButton retrieveBtn = new JButton("Retrieve");

    JTable table;
    DefaultTableModel model;

    public VisitorPanel() {
        setLayout(null);

        String[] labels = {"ID", "Name", "TicketID", "VisitDate"};
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
        addField("TicketID", ticketTxt, y); y += 30;
        addField("VisitDate (yyyy-mm-dd)", dateTxt, y);

        addButtons();
        add(sp);

        loadVisitors();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if(row >= 0){
                    idTxt.setText(model.getValueAt(row, 0).toString());
                    nameTxt.setText(model.getValueAt(row, 1).toString());
                    ticketTxt.setText(model.getValueAt(row, 2).toString());
                    dateTxt.setText(model.getValueAt(row, 3).toString());
                }
            }
        });
    }

    private void addField(String lbl, JTextField txt, int y){
        JLabel l = new JLabel(lbl);
        l.setBounds(20,y,150,25);
        txt.setBounds(180,y,150,25);
        add(l); add(txt);
    }

    private void addButtons(){
        addBtn.setBounds(400,20,100,30);
        updateBtn.setBounds(400,60,100,30);
        deleteBtn.setBounds(400,100,100,30);
        retrieveBtn.setBounds(400,140,100,30);

        add(addBtn); add(updateBtn); add(deleteBtn); add(retrieveBtn);

        addBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        retrieveBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        try(Connection con = DB.getConnection()){
            if(e.getSource() == addBtn){
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Visitors(Name,TicketID,VisitDate) VALUES(?,?,?)");
                ps.setString(1, nameTxt.getText());
                ps.setString(2, ticketTxt.getText());
                ps.setDate(3, Date.valueOf(dateTxt.getText()));
                ps.executeUpdate();
                loadVisitors();
                clearFields();
                JOptionPane.showMessageDialog(this,"Visitor Added!");
            }
            else if(e.getSource() == updateBtn){
                if(idTxt.getText().isEmpty()) return;
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE Visitors SET Name=?,TicketID=?,VisitDate=? WHERE VisitorID=?");
                ps.setString(1, nameTxt.getText());
                ps.setString(2, ticketTxt.getText());
                ps.setDate(3, Date.valueOf(dateTxt.getText()));
                ps.setInt(4, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                loadVisitors();
                clearFields();
                JOptionPane.showMessageDialog(this,"Visitor Updated!");
            }
            else if(e.getSource() == deleteBtn){
                if(idTxt.getText().isEmpty()) return;
                PreparedStatement ps = con.prepareStatement("DELETE FROM Visitors WHERE VisitorID=?");
                ps.setInt(1, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                loadVisitors();
                clearFields();
                JOptionPane.showMessageDialog(this,"Visitor Deleted!");
            }
            else if(e.getSource() == retrieveBtn){
                loadVisitors();
            }
        }catch(Exception ex){ ex.printStackTrace(); }
    }

    private void loadVisitors(){
        try(Connection con = DB.getConnection()){
            model.setRowCount(0);
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Visitors");
            while(rs.next()){
                model.addRow(new Object[]{
                    rs.getInt("VisitorID"),
                    rs.getString("Name"),
                    rs.getString("TicketID"),
                    rs.getDate("VisitDate")
                });
            }
        }catch(Exception ex){ ex.printStackTrace(); }
    }

    private void clearFields(){
        idTxt.setText(""); nameTxt.setText(""); ticketTxt.setText(""); dateTxt.setText("");
    }
}
