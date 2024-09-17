package com.barros.routes;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HelloHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        String greetings = "Hello World!";

        exchange.sendResponseHeaders(200, greetings.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(greetings.getBytes());
        os.close();
    }
}
