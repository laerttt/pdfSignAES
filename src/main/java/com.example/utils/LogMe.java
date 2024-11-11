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
            // Configure the logger to write to a file named "application.log"
            fileHandler = new FileHandler("application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger file handler.", e);
        }
    }

    public static void info(String message) {
        logger.log(Level.INFO, message);
    }

    public static void warn(String message) {
        logger.log(Level.WARNING, message);
    }

    public static void error(String message, Exception e) {
        logger.log(Level.SEVERE, message, e);
    }

    // Close the file handler when the application is done
    public static void close() {
        if (fileHandler != null) {
            fileHandler.close();
        }
    }
}
