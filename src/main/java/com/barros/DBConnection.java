package com.barros;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection = null;

    static {
        Config config = new Config();

        String url = config.getProperty("db.url");
        String username = config.getProperty("db.username");
        String password = config.getProperty("db.password");

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }

    }

    public static Connection getConnection() {
        return connection;
    }
}
