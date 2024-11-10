# Java Task: PDF Document Signing with AES Encryption

## Objective

Develop a Java application that:

- Accepts a PDF file.
- Generates a digital signature using a simple self-signed certificate.
- Encrypts its content using AES.
- Decrypts the PDF.

## Requirements

### Functional Requirements

#### Input Handling
- Accept a PDF file as input, with the user specifying the file path.

#### Digital Signature
- Generate a digital signature for the original PDF file using a self-signed certificate (You may generate the certificate or use a Java library to create it).
- Attach the signature in the encrypted PDF.

#### AES Encryption
- Encrypt the PDF file’s content using AES-256 encryption.
- Store the encrypted file in a secure, temporary location for later verification.
- Generate the AES encryption key within the application, ensuring it is stored securely (in memory only).

#### Verification and Decryption
- Decrypt the PDF file and validate that it matches the original file.

#### database Storage
- Store basic information about each document in a database, including:
  - Document name.
  - Path or reference to the encrypted file.
  - Date and time of encryption and signature.
  - (Feel free to add more tables and data).

### Technical Requirements

#### Project Structure
- Organize the application into layers:
  - **Controller**: Handles input/output operations.
  - **Service**: Contains the main business logic for encryption, decryption, and signature handling.

#### Centralized Exception Management
- Implement centralized exception handling for missing files, encryption/decryption issues, and signature mismatches.

#### Logging
- Create a log file to record key actions.
- Log errors with meaningful information for debugging.

#### Documentation
- Write the documentation in the Documentation.md file with some basic and technical details.

#### Code deployment
- Create a branch from the main branch with the name of the task
- Merge the branch to master when the task is finished 

### Suggested Libraries
- **PDF Manipulation**: Use libraries like Apache PDFBox or iText for handling PDF files.
- **AES Encryption**: Use Java’s `javax.crypto` package.
- **Digital Signature**: Use Java’s `java.security` and `java.security.cert` packages.
