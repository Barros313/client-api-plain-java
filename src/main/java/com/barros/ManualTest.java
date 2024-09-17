package com.barros;

import com.barros.models.Client;
import com.barros.view.ClientOperations;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManualTest {
    public static void main(String[] args) throws SQLException {
        ClientOperations exec = new ClientOperations();

        List<Client> clients = new ArrayList<>();

        clients.add(new Client("Mylene Cavalcanti", 22));
        clients.add(new Client("Davi Barros", 20));
        clients.add(new Client("Hugo Vasconcelos", 20));

        for (Client client : clients) {
            exec.add(client);
        }

        System.out.println(exec.getAllClients());
    }
}
