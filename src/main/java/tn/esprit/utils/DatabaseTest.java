package tn.esprit.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTest {

    public static void main(String[] args) {
        // Attempt to establish a connection to the database
        DataBase database = DataBase.getInstance();
        Connection connection = database.getCnx();

        if (connection != null) {
            System.out.println("Connected to the database successfully.");

            // Execute a simple SQL query to verify database connectivity
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT 1")) {

                // If the query executes without errors, print a success message
                System.out.println("Database connectivity test successful.");

            } catch (SQLException e) {
                // If an SQL exception occurs, print an error message
                System.out.println("Failed to execute SQL query: " + e.getMessage());
            }
        } else {
            // If connection is null, print an error message
            System.out.println("Failed to connect to the database.");
        }
    }
}
