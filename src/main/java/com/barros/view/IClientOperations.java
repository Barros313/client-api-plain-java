package com.barros.view;

import com.barros.models.Client;

import java.sql.SQLException;
import java.util.List;

public interface IClientOperations {
    int add(Client client) throws SQLException;

    void delete(int id) throws SQLException;

    Client getClient(int id) throws SQLException;

    List<Client> getAllClients() throws SQLException;

    void update(Client client) throws SQLException;
}
