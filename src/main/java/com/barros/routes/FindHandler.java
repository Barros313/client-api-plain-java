package com.barros.routes;

import com.barros.models.Client;
import com.barros.view.ClientOperations;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.sql.SQLException;

public class FindHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream responseBody = exchange.getResponseBody();

        // Check request method
        if (!exchange.getRequestMethod().equals("GET")) {
            String errorMessage = "Bad request";
            exchange.sendResponseHeaders(400, errorMessage.length());
            exchange.getResponseHeaders().add("Content-Type", "text/plain");
            responseBody.write(errorMessage.getBytes());

            responseBody.flush();
            responseBody.close();
            return;
        }

        ClientOperations op = new ClientOperations();
        ObjectMapper mapper = new ObjectMapper();

        // Get request path
        URI requestURI = exchange.getRequestURI();
        String path = requestURI.getPath();

        // Split path parts
        String[] pathParts = path.split("/");

        // Get client id through path part
        int clientId;
        try {
            // Get integer from url path
            clientId = Integer.parseInt(pathParts[pathParts.length - 1]);
        } catch (NumberFormatException exp) {
            // Set bad request message
            String nfeMessage = "Bad request";

            // Set bad request status code and message length
            exchange.getResponseHeaders().add("Content-Type", "text/plain");
            exchange.sendResponseHeaders(400, nfeMessage.length());

            // Write to body
            responseBody.write(nfeMessage.getBytes());

            // Print exception to terminal
            System.err.println(nfeMessage + "\n" + exp.getMessage());

            // End program
            responseBody.flush();
            responseBody.close();
            return;
        }

        // Get client
        Client client;
        try {
            // Get client
            client = op.getClient(clientId);
        } catch (SQLException e) {
            // Define SQL error message
            String sqlError = "SQL Error";

            // Set status code and response length
            exchange.getResponseHeaders().add("Content-Type", "text/plain");
            exchange.sendResponseHeaders(500, sqlError.length());

            // Write error message to body
            responseBody.write(sqlError.getBytes());

            // Print exception message to terminal
            System.err.println(sqlError + "\n" + e.getMessage());

            // End program
            responseBody.flush();
            responseBody.close();
            return;
        }

        // NULL check
        if (client == null) {
            // Define null client message
            String nullClient = "Client not found";

            // Set client not found status code and message length
            exchange.getResponseHeaders().add("Content-Type", "text/plain");
            exchange.sendResponseHeaders(404, nullClient.length());

            // Write error message to body
            responseBody.write(nullClient.getBytes());

            // Print exception message
            System.err.println(nullClient + "\n" + nullClient);

            // End program
            responseBody.flush();
            responseBody.close();
            return;
        }

        // Write to body
        String response = mapper.writeValueAsString(client);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.length());
        responseBody.write(response.getBytes());

        // End
        responseBody.flush();
        responseBody.close();
    }
}
