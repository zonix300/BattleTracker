package com.zonix.dndapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Connector {

    public void connect() {
        String url = "jdbc:h2:mem:testdb"; // In-memory database
        String user = "sa";
        String password = "";

        try(Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement()) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
