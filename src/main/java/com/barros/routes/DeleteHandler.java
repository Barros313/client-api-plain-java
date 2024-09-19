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
import java.util.Scanner;

public class DeleteHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream responseBody = exchange.getResponseBody();

        // Check request method
        if (!exchange.getRequestMethod().equals("DELETE")) {
            String errorMessage = "Bad request";
            exchange.sendResponseHeaders(400, errorMessage.length());
            exchange.getResponseHeaders().add("Content-Type", "text/plain");
            responseBody.write(errorMessage.getBytes());

            responseBody.flush();
            responseBody.close();
            return;
        }

        InputStream requestBody = exchange.getRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        Scanner scanner = new Scanner(requestBody);
        ClientOperations op = new ClientOperations();

        // Get request path
        URI requestURI = exchange.getRequestURI();
        String path = requestURI.getPath();

        // Split path parts
        String[] pathParts = path.split("/");

        int clientId;
        try {
            clientId = Integer.parseInt(pathParts[pathParts.length - 1]);
        } catch (NumberFormatException exp) {
            // Print error message to terminal
            System.err.println(exp.getMessage());

            // Print to body
            String errorMessage = "Bad request, invalid ID.";
            exchange.getResponseHeaders().add("Content-Type", "text/plain");
            exchange.sendResponseHeaders(400, errorMessage.length());
            responseBody.write(errorMessage.getBytes());

            // End
            responseBody.flush();
            responseBody.close();
            return;
        }

        try {
            op.delete(clientId);
        } catch (SQLException exp) {
            // Print to terminal
            System.err.println(exp.getMessage());

            // Print to body
            String errorMessage = "Failed to delete.";
            exchange.getResponseHeaders().add("Content-Type", "text/plain");
            exchange.sendResponseHeaders(500, errorMessage.length());
            responseBody.write(errorMessage.getBytes());

            // End
            responseBody.flush();
            responseBody.close();
            return;
        }

        // Print success message to body
        String successMessage = "Deleted " + clientId + " successfully";
        exchange.getResponseHeaders().add("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, successMessage.length());
        responseBody.write(successMessage.getBytes());

        // End
        responseBody.flush();
        responseBody.close();
    }
}
