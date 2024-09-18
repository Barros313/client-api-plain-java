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
        // Get instance of client operations
        ClientOperations op = new ClientOperations();

        // Get response body
        OutputStream responseBody = exchange.getResponseBody();

        // Get list of clients
        List<Client> clients = null;
        try {
            clients = op.getAllClients();
        } catch (SQLException e) {
            // Define SQL Exception body text
            String sqlExceptionMessage = "500: SQL Exception";

            // Send server side error status code
            exchange.getResponseHeaders().add("Content-Type", "plain/text");
            exchange.sendResponseHeaders(500, sqlExceptionMessage.length());

            // Write error to body
            responseBody.write(sqlExceptionMessage.getBytes());

            // Print exception to console
            System.err.println(sqlExceptionMessage + "/n" + e.getMessage());

            // End program
            responseBody.flush();
            responseBody.close();
            return;
        }

        if (clients == null) {
            // Set null message
            String nullClients = "Error fetching list of clients";

            // Send error status code
            exchange.getResponseHeaders().add("Content-Type", "plain/text");
            exchange.sendResponseHeaders(500, nullClients.length());

            // Write error to body
            responseBody.write(nullClients.getBytes());

            // Print error to console
            System.out.println(nullClients + ": List<Client> clients == null");

            // End Program
            responseBody.flush();
            responseBody.close();
            return;
        }

        if (clients.isEmpty()) {
            // Not found error message
            String emptyClientList = "Table clients empty";

            // Send not found status code
            exchange.getResponseHeaders().add("Content-Type", "plain/text");
            exchange.sendResponseHeaders(404, emptyClientList.length());

            // Write error to body
            responseBody.write(emptyClientList.getBytes());

            // Print error to console
            System.err.println(emptyClientList);

            // End program
            responseBody.flush();
            responseBody.close();
            return;
        }

        // Define String Builder to store JSON strings
        StringBuilder bodyPage = new StringBuilder();

        // Write each JSON to bodyPage
        ObjectMapper mapper = new ObjectMapper();
        for (Client client : clients) {
            bodyPage.append(mapper.writeValueAsString(client)).append("\n");
        }

        // Send success status code
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, bodyPage.toString().getBytes().length);

        // Write response
        responseBody.write(bodyPage.toString().getBytes());

        // Print success message to console
        System.out.println("/clients Success");

        // End program
        responseBody.flush();
        responseBody.close();
    }
}
