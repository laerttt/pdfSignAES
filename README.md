# Java Project: PDF Document Signing with AES Encryption

## Table of Contents

- [Overview](#overview)
- [Project Structure](#project-structure)
- [Classes and Methods](#classes-and-methods)
    - [PdfController](#pdfcontroller)
    - [DatabaseConnection](#databaseconnection)
    - [PdfMetadata](#pdfmetadata)
    - [DecryptionService](#decryptionservice)
    - [EncryptionService](#encryptionservice)
    - [PdfService](#pdfservice)
    - [SignatureService](#signatureservice)
    - [LogMe](#logme)
- [Main Class](#main-class)
- [Additional Notes](#additional-notes)

---

## Overview

This Java application enables secure processing of PDF documents by providing functionalities for signing, encrypting, and decrypting PDFs. It incorporates AES-256 encryption for confidentiality and uses a self-signed certificate for document authenticity. The application also stores document metadata in a MySQL database, logging actions and errors in a structured manner.

---

## Project Structure

This project is organized into the following layers:

- **Controller**: Manages input/output and handles user interactions.
- **Service**: Contains core logic for encryption, decryption, and digital signing.
- **Database**: Manages database connections and handles data storage.

---

## Classes and Methods

### PdfController

**Package**: `com.example.controller`

This class handles user interactions for processing and decrypting PDF files. It prompts the user for inputs and passes file paths and keys to the respective services.

#### Methods

- **handlePdfProcessing()**: Prompts the user for a PDF file path, processes the PDF by signing and encrypting it, and logs success or error messages.
- **handleDecrypting()**: Prompts for an AES key, encrypted file path, and output path, then decrypts the PDF using the provided key.

---

### DatabaseConnection

**Package**: `com.example.database`

This class manages the MySQL database connection, ensuring the database and necessary tables are created if they don’t exist. It supports metadata storage for processed PDF documents.

#### Methods

- **initializeDatabase()**: Sets up the `pdf_store` database and `pdf_metadata` and `users` tables, if not already created.
- **connectToDatabase()**: Establishes a connection to the `pdf_store` database.
- **storeMetadata(PdfMetadata metadata)**: Inserts metadata about a processed PDF file into the `pdf_metadata` table.
- **close()**: Closes the database connection, ensuring resources are properly released.

---

### PdfMetadata

**Package**: `com.example.model`

Holds metadata information about processed PDF documents, such as document name and file path.

#### Attributes

- `name`: The document name.
- `path`: The file path where the document is stored.

#### Methods

- **getName()**, **getPath()**: Accessors for retrieving document name and path.
- **setName(String name)**, **setPath(String path)**: Setters for updating document name and path.
- **toString()**: Returns a string representation of the metadata for logging and debugging.

---

### DecryptionService

**Package**: `com.example.service`

Handles decryption of encrypted PDF files using AES keys.

#### Methods

- **DecryptionService(String aesKey)**: Initializes the service with a Base64-encoded AES key.
- **decrypt(byte[] encryptedContent)**: Decrypts byte data using AES.
- **decryptPdfFile(File encryptedFile, File outputFile)**: Reads an encrypted PDF file, decrypts it, and writes the result to the output file.

---

### EncryptionService

**Package**: `com.example.service`

Handles AES-256 encryption for PDF documents. The AES key is generated at runtime and securely stored in memory.

#### Methods

- **EncryptionService()**: Generates a new AES-256 key.
- **encrypt(byte[] content)**: Encrypts byte content using the AES key.
- **saveEncryptedPdf(byte[] encryptedContent)**: Saves encrypted PDF content to a temporary file.

---

### PdfService

**Package**: `com.example.service`

This class coordinates the signing, encryption, and metadata storage processes for PDF documents.

#### Methods

- **processPdf(String pdfPath)**: Signs, encrypts, and saves metadata for a given PDF file. Logs successful processing and stores file metadata in the database.
- **decrypt(String encryptedFilePath, String outputFilePath)**: Decrypts a previously encrypted PDF file and writes it to the specified output location.

---

### SignatureService

**Package**: `com.example.service`

Generates a self-signed certificate and attaches a digital signature to a PDF document.

#### Methods

- **generateKeyPair()**: Generates an RSA key pair for signing.
- **generateSelfSignedCertificate(KeyPair keyPair)**: Creates a self-signed X.509 certificate using the generated key pair.
- **signPdf(String inputPdfPath)**: Signs a PDF document and returns the signed document as a byte array.

---

### LogMe

**Package**: `com.example.utils`

A utility class for logging information, warnings, and errors to a log file (`application.log`). This is essential for tracking application activities and debugging errors.

#### Methods

- **info(String message)**: Logs informational messages.
- **warn(String message)**: Logs warning messages.
- **error(String message, Exception e)**: Logs error messages with exception details.
- **close()**: Closes the log file handler when logging is no longer needed.

---

## Main Class

**Package**: `com.example`

The `Main` class initializes the application and provides a user interface in the console for processing and decrypting PDFs.

#### Methods

- **main(String[] args)**: The main entry point for the application. Displays options for PDF processing or decryption, then delegates tasks to the `PdfController` class based on user selection.

---

## Additional Notes

### Dependencies

- **PDFBox**: Library for PDF manipulation.
- **BouncyCastle**: Provides cryptographic functions for generating digital certificates.

### Database

- The database configuration uses a local MySQL database. Ensure that `DB_USER` and `DB_PASSWORD` are correctly set in the `DatabaseConnection` class.

### Logging

- All logs are saved to `application.log`, capturing key actions, errors, and database connection statuses.

---

This documentation provides a structured overview of the project, covering all core functionalities, classes, and methods. Use this document as a reference while developing, testing, and debugging the application.
