package com.barros.routes;

import com.barros.DBConnection;
import com.barros.models.Client;
import com.barros.view.ClientOperations;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

public class FetchAllHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ClientOperations op = new ClientOperations();

        List<Client> clients = null;
        try {
            clients = op.getAllClients();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        if (clients == null) return;

        StringBuilder bodyPage = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();
        for (Client client : clients) {
            bodyPage.append(mapper.writeValueAsString(client)).append("\n");
        }

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, bodyPage.toString().getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(bodyPage.toString().getBytes());
        os.close();
    }
}
