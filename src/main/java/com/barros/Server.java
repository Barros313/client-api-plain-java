package com.barros;

import com.barros.routes.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/test", new HelloHandler());
        server.createContext("/clients", new FetchAllHandler());
        server.createContext("/find", new FindHandler());
        server.createContext("/register", new RegisterHandler());
        server.createContext("/update", new UpdateHandler());
        server.createContext("/delete", new DeleteHandler());

        server.setExecutor(null);
        server.start();
    }
}