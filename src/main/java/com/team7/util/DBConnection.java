package com.team7.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    private static final Properties props = new Properties();

    static {
        try (InputStream in = DBConnection.class.getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (in == null) {
                throw new RuntimeException(
                    "db.properties not found on classpath. " +
                    "Copy it into src/main/resources and fill in your credentials.");
            }
            props.load(in);
            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize DBConnection", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.username"),
                props.getProperty("db.password"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
