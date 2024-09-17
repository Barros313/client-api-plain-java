package com.barros.routes;

import com.barros.models.Client;
import com.barros.view.ClientOperations;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.sql.SQLException;

public class FindHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();

        URI requestURI = exchange.getRequestURI();
        String path = requestURI.getPath();

        String[] pathParts = path.split("/");

        System.out.println(pathParts[2]);

        OutputStream responseBody = exchange.getResponseBody();
        long clientId = 0;
        try {
            clientId = Integer.parseInt(pathParts[2]);
            System.out.println(clientId);
        } catch (NumberFormatException exp) {
            responseBody.write("Bad request".getBytes());
            System.err.println("Bad request: " + exp.getMessage());
            return;
        }

        ClientOperations op = new ClientOperations();
        Client client;

        try {
            client = op.getClient((int) clientId);
        } catch (SQLException e) {
            responseBody.write("Error".getBytes());
            System.err.println("Bad request: " + e.getMessage());
            return;
        }

        if (client == null) {
            responseBody.write("Client not found".getBytes());
            System.err.println("Client not found");
            return;
        }

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        ObjectMapper mapper = new ObjectMapper();

        String response = mapper.writeValueAsString(client);
        System.out.println(response);

        responseBody.write(response.getBytes());
        responseBody.flush();
        responseBody.close();
    }
}
