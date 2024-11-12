package com.example.model;

public class PdfMetadata {
    private String name;  // Name of the PDF document
    private String path;  // File path of the PDF document

    // Constructor to initialize PdfMetadata with document name and path
    public PdfMetadata(String name, String path) {
        this.name = name;
        this.path = path;
    }

    // Getter for document name
    public String getName() { return name; }

    // Getter for document path
    public String getPath() { return path; }

    // Setter to update document name
    public void setName(String name) { this.name = name; }

    // Setter to update document path
    public void setPath(String path) { this.path = path; }

    // Override toString method to display PdfMetadata information in a readable format
    @Override
    public String toString() {
        return "PdfMetadata {" +
                "\nname='" + name + '\'' +
                ", \npath='" + path + '\'' +
                "\n}";
    }
}