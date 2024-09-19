package com.barros.routes;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HelloHandler implements HttpHandler {
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


        // Print to body
        String greetings = "Hello World!";
        exchange.sendResponseHeaders(200, greetings.getBytes().length);
        exchange.getResponseHeaders().add("Content-Type", "text/plain");
        responseBody.write(greetings.getBytes());

        // End
        responseBody.flush();
        responseBody.close();
    }
}
