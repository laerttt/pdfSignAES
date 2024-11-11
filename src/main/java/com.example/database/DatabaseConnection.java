package com.example.database;

import com.example.model.PdfMetadata;
import com.example.utils.LogMe;

import java.sql.*;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "pdf_store";
    private static final String DB_USER = "root";  // Replace with your MySQL username
    private static final String DB_PASSWORD = "";  // Replace with your MySQL password
    private Connection connection;

    public DatabaseConnection() throws SQLException {
        try {
            initializeDatabase();
            connectToDatabase();
        } catch (SQLException e) {
            LogMe.error("Error initializing or connecting to the database.", e);
            throw e;
        }
    }

    // Method to initialize the database and tables if they do not exist
    private void initializeDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Check if the database exists, and if not, create it
            String createDbQuery = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            stmt.executeUpdate(createDbQuery);
            LogMe.info("Database checked/created: " + DB_NAME);

            // Switch to the pdf_store database
            stmt.execute("USE " + DB_NAME);

            // Create pdf_metadata table if it does not exist
            String createPdfMetadataTable = """
                CREATE TABLE IF NOT EXISTS pdf_metadata (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    document_name VARCHAR(255) NOT NULL,
                    file_path VARCHAR(255) NOT NULL,
                    encryption_status BOOLEAN DEFAULT 0,
                    signed_status BOOLEAN DEFAULT 0,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;
            stmt.executeUpdate(createPdfMetadataTable);
            LogMe.info("Table checked/created: pdf_metadata");

            // Optionally create users table if your application needs user management
            String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(100) UNIQUE NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    email VARCHAR(255),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;
            stmt.executeUpdate(createUsersTable);
            LogMe.info("Table checked/created: users");

        } catch (SQLException e) {
            LogMe.error("Error during database or table creation.", e);
            throw e;
        }
    }

    // Method to connect to the `pdf_store` database
    private void connectToDatabase() throws SQLException {
        try {
            String fullDbUrl = DB_URL + DB_NAME;
            connection = DriverManager.getConnection(fullDbUrl, DB_USER, DB_PASSWORD);
            LogMe.info("Connected to database: " + DB_NAME);
        } catch (SQLException e) {
            LogMe.error("Failed to connect to database: " + DB_NAME, e);
            throw e;
        }
    }

    // Getter for the connection to be used in other parts of the application
    public Connection getConnection() {
        return connection;
    }

    // Close connection when done
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            try {
                connection.close();
                LogMe.info("Database connection closed.");
            } catch (SQLException e) {
                LogMe.error("Failed to close the database connection.", e);
                throw e;
            }
        }
    }

    // Method to store metadata into the pdf_metadata table
    public void storeMetadata(PdfMetadata metadata) throws SQLException {
        String insertQuery = "INSERT INTO pdf_metadata (document_name, file_path, created_at) VALUES (?, ?, NOW())";

        // Use the existing database connection to insert metadata
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            // Set the metadata values for document_name and file_path in the SQL statement
            statement.setString(1, metadata.getName());
            statement.setString(2, metadata.getPath());

            // Execute the SQL statement to insert the metadata into the database
            statement.executeUpdate();
            LogMe.info("Metadata stored successfully for document: " + metadata.getName());

        } catch (SQLException e) {
            LogMe.error("Failed to store metadata for document: " + metadata.getName(), e);
            throw e;
        }
    }
}
