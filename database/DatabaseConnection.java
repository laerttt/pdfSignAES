package database;

import model.PdfMetadata;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {

    public void storeMetadata(PdfMetadata metadata) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/pdf_store";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO pdf_metadata (name, path, date_time) VALUES (?, ?, NOW())")) {

            statement.setString(1, metadata.getName());
            statement.setString(2, metadata.getPath());
            statement.executeUpdate();
        }
    }
}

