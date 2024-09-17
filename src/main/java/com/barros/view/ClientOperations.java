package com.barros.view;

import com.barros.DBConnection;
import com.barros.models.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientOperations implements IClientOperations {
    static Connection connection = DBConnection.getConnection();

    @Override
    public int add(Client client) throws SQLException {
        String query = "INSERT INTO client (name, age) VALUES (?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, client.getName());
        statement.setInt(2, client.getAge());

        return statement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM client WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, id);
        statement.executeUpdate();
    }

    @Override
    public Client getClient(int id) throws SQLException {
        String query = "SELECT * FROM client WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);

        Client client = new Client();

        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        boolean found = false;

        while (result.next()) {
            found = true;
            client.setId(result.getInt("id"));
            client.setName(result.getString("name"));
            client.setAge(result.getInt("age"));
        }

        if (found) return client;
        else return null;
    }

    @Override
    public List<Client> getAllClients() throws SQLException {
        String query = "SELECT * FROM client";

        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet result = statement.executeQuery();

        List<Client> clients = new ArrayList<>();

        while (result.next()) {
            Client client = new Client();

            client.setId(result.getInt("id"));
            client.setName(result.getString("name"));
            client.setAge(result.getInt("age"));

            clients.add(client);
        }

        return clients;
    }

    @Override
    public void update(Client client) throws SQLException {
        String query = "UPDATE client SET name = ?, age = ? WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, client.getName());
        statement.setInt(2, client.getAge());

        statement.setInt(3, client.getId());

        statement.executeUpdate();
    }
}
