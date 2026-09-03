package com.team7.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USERNAME = "YOUR_USERNAME";
    private static final String PASSWORD = "YOUR_PASSWORD";

    public static Connection getConnection() {

        Connection con = null;

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            System.out.println("Database Connected Successfully.");

        } catch (Exception e) {

            e.printStackTrace();

        }

        return con;
    }
}