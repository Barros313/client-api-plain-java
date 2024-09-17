package com.barros.view;

import com.barros.DBConnection;
import com.barros.models.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClientOperations implements IClientOperations {
    static Connection connection = DBConnection.getConnection();

    @Override
    public int add(Client client) throws SQLException {
        return 0;
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public Client getClient(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Client> getAllClients() throws SQLException {
        return List.of();
    }

    @Override
    public void update(Client client) throws SQLException {

    }
}
