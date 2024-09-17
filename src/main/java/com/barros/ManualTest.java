package com.barros;

import com.barros.models.Client;
import com.barros.view.ClientOperations;

import java.sql.Connection;
import java.sql.SQLException;

public class ManualTest {
    public static void main(String[] args) throws SQLException {
        ClientOperations exec = new ClientOperations();

        System.out.println(exec.getClient(1));
    }
}
