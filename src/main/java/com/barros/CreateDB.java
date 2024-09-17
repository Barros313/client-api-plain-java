package com.barros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {
    static Connection connection = DBConnection.getConnection();

    public static void main(String[] args) {
        String query = "CREATE TABLE IF NOT EXISTS client (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(60) NOT NULL, " +
                "age INT NOT NULL);";

        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);

            System.out.println("Table created");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
}
