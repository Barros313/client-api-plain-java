package com.barros.routes;

import com.barros.models.Client;
import com.barros.view.ClientOperations;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UpdateHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream responseBody = exchange.getResponseBody();

        // Check request method
        if (!exchange.getRequestMethod().equals("PUT")) {
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

        // Get body JSON input
        String bodyInput;
        try {
            bodyInput = scanner.useDelimiter("\\A").next();
        } catch (IllegalStateException illegalStateException) {
            // Print exception to console
            System.err.println(illegalStateException.getMessage());

            // Print error message to body
            String errorMessage = "Error reading data";
            exchange.getResponseHeaders().add("Content-Type", "plain/text");
            exchange.sendResponseHeaders(500, errorMessage.length());
            responseBody.write(errorMessage.getBytes());

            // End
            responseBody.flush();
            responseBody.close();
            return;
        } catch (NoSuchElementException noSuchElementException) {
            // Print exception to console
            System.err.println(noSuchElementException.getMessage());

            // Print error message to body
            String errorMessage = "Empty body";
            exchange.getResponseHeaders().add("Content-Type", "plain/text");
            exchange.sendResponseHeaders(400, errorMessage.length());
            responseBody.write(errorMessage.getBytes());

            // End
            responseBody.flush();
            responseBody.close();
            return;
        }

        Client updatedClient;
        try {
            updatedClient = mapper.readValue(bodyInput, Client.class);
        } catch (JsonProcessingException jsonProcessingException) {
            System.err.println(jsonProcessingException.getMessage());

            // Print error message
            String errorMessage = "Not a valid JSON document";
            exchange.getResponseHeaders().add("Content-Type", "plain/text");
            exchange.sendResponseHeaders(400, errorMessage.length());
            responseBody.write(errorMessage.getBytes());

            // End
            responseBody.flush();
            responseBody.close();
            return;
        }

        // Update client
        try {
            op.update(updatedClient);
        } catch (SQLException sqlException) {
            // Print exception to terminal
            System.err.println(sqlException.getMessage());

            // Print error message to body
            String errorMessage = "Error updating client";
            exchange.getResponseHeaders().add("Content-Type", "plain/text");
            exchange.sendResponseHeaders(400, errorMessage.length());
            responseBody.write(errorMessage.getBytes());

            // End
            responseBody.flush();
            responseBody.close();
            return;
        }

        // Print success message to terminal
        System.out.println("ID: " + updatedClient.getId() + " successfully updated");

        // Print success message to body
        String successMessage = "Successfully updated the client";
        exchange.getResponseHeaders().add("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, successMessage.length());
        responseBody.write(successMessage.getBytes());

        // End
        responseBody.flush();
        responseBody.close();
    }
}
