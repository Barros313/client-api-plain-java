package com.barros;

import com.barros.routes.FetchAllHandler;
import com.barros.routes.FindHandler;
import com.barros.routes.HelloHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/hello", new HelloHandler());
        server.createContext("/clients", new FetchAllHandler());
        server.createContext("/client", new FindHandler());
        server.setExecutor(null);
        server.start();
    }
}