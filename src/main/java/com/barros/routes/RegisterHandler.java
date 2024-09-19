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

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        OutputStream responseBody = exchange.getResponseBody();
        ObjectMapper mapper = new ObjectMapper();
        Scanner scanner = new Scanner(requestBody);
        ClientOperations op = new ClientOperations();

        // Get body JSON input
        String bodyInput;
        try {
            bodyInput = scanner.useDelimiter("\\A").next();
        } catch (IllegalStateException ise) {
            // Print exception to console
            System.err.println(ise.getMessage());

            // Print error message
            String errorMessage = "Error reading data";
            exchange.getResponseHeaders().add("Content-Type", "plain/text");
            exchange.sendResponseHeaders(500, errorMessage.length());
            responseBody.write(errorMessage.getBytes());

            // End
            responseBody.flush();
            responseBody.close();
            return;
        } catch (NoSuchElementException nsee) {
            // Print exception to console
            System.err.println(nsee.getMessage());

            // Print error message
            String errorMessage = "Empty body";
            exchange.getResponseHeaders().add("Content-Type", "plain/text");
            exchange.sendResponseHeaders(400, errorMessage.length());
            responseBody.write(errorMessage.getBytes());

            // End
            responseBody.flush();
            responseBody.close();
            return;

        }

        // Map to client object
        Client newClient;
        try {
            newClient = mapper.readValue(bodyInput, Client.class);
        } catch (JsonProcessingException e) {
            // Print exception to console
            System.err.println(e.getMessage());

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

        int newClientId;
        try {
            newClientId = op.add(newClient);
        } catch (SQLException exp) {
            // Print exception to console
            System.err.println(exp.getMessage());

            // Print error message
            String errorMessage = "Failed to add client";
            exchange.getResponseHeaders().add("Content-Type", "plain/text");
            exchange.sendResponseHeaders(502, errorMessage.length());
            responseBody.write(errorMessage.getBytes());

            // End
            responseBody.flush();
            responseBody.close();
            return;
        }

        // Print success message to console
        System.out.println("Client id: " + newClientId + " successfully added");

        // Print success message to response body
        String success = "Successfully added client";
        exchange.getResponseHeaders().add("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, success.length());
        responseBody.write(success.getBytes());

        // End
        responseBody.flush();
        responseBody.close();
    }
}
