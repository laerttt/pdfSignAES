package com.example.model;

public class PdfMetadata {
    private String name;
    private String path;

    public PdfMetadata(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() { return name; }
    public String getPath() { return path; }
    public void setName(String name) { this.name = name; }
    public void setPath(String path) { this.path = path; }
}
