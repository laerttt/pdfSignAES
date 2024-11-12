package com.example.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogMe {
    private static final Logger logger = Logger.getLogger(LogMe.class.getName());
    private static FileHandler fileHandler;

    static {
        try {
            // Configure the logger to write to a file named "application.log" in append mode
            fileHandler = new FileHandler("application.log", true);
            fileHandler.setFormatter(new SimpleFormatter()); // Use a simple text format for log entries
            logger.addHandler(fileHandler); // Attach the file handler to the logger
            logger.setLevel(Level.ALL); // Set logger to capture all levels of logs
        } catch (IOException e) {
            // Log an error message if file handler setup fails
            logger.log(Level.SEVERE, "Failed to initialize logger file handler.", e);
        }
    }

    // Log an informational message
    public static void info(String message) {
        logger.log(Level.INFO, message);
    }

    // Log a warning message
    public static void warn(String message) {
        logger.log(Level.WARNING, message);
    }

    // Log an error message along with the exception stack trace
    public static void error(String message, Exception e) {
        logger.log(Level.SEVERE, message, e);
    }

    // Close the file handler when the application is done
    public static void close() {
        if (fileHandler != null) {
            fileHandler.close(); // Close the file handler to release resources
        }
    }
}