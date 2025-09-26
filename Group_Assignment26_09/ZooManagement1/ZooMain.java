package com.form;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.Panel.AnimalPanel;
import com.Panel.EmployeePanel;
import com.Panel.VisitorPanel;

public class ZooMain extends JFrame {

    public ZooMain(String role, int userid) {
        setTitle("Zoo Management System");
        setSize(900, 600);
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();

        if (role.equalsIgnoreCase("admin")) {
            tabs.add("Animals", new AnimalPanel());
            tabs.add("Employees", new EmployeePanel());
            tabs.add("Visitors", new VisitorPanel());
        } else if (role.equalsIgnoreCase("employee")) {
            tabs.add("Animals", new AnimalPanel());
            tabs.add("Visitors", new VisitorPanel());
        } else if (role.equalsIgnoreCase("visitor")) {
            tabs.add("My Visits", new VisitorPanel());
        }

        add(tabs, BorderLayout.CENTER);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        // Example: open as admin
        new ZooMain("admin", 1);
    }
}
