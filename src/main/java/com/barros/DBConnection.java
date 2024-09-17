package com.barros;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection = null;

    static {
        String url = "jdbc:postgres://localhost:5432/new_java_db";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "postgres");

        try {
            connection = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }

    }

    public static Connection getConnection() {
        return connection;
    }
}
