package classes.model.dao;

import java.sql.*;

public class DBConnector {
    private Connection connection;

    public DBConnector() {
        System.setProperty("org.sqlite.lib.verbose", "true");
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        String url = "jdbc:sqlite:/Users/andrew.bennett/Documents/GitHub/IoTBay/IoTBayStore.db";
        String url = "jdbc:sqlite:IoTBayStore.db";
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to database");
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("PRAGMA journal_mode=WAL;");
                stmt.execute("PRAGMA busy_timeout = 3000;");
            }
            connection.setAutoCommit(true);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}