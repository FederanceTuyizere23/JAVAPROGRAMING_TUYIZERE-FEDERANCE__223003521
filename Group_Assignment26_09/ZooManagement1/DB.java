package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/zoo_mgnmnt?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = ""; // your MySQL password

        // Correct driver for Connector/J 9.x
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        return DriverManager.getConnection(url, user, password);
    }
}
