# ATU Four-Square Cipher (Java) – ATU 2025

![Status](https://img.shields.io/badge/status-Completed-brightgreen)
![Java](https://img.shields.io/badge/language-Java-orange)

## Overview

This repository contains a Java-based implementation of the Four-Square Cipher, developed as part of the Software Development Higher Diploma at Atlantic Technological University (ATU).

> A command-line encryption tool that implements the classical Four-Square Cipher with file handling, key management, and enhanced security using Vigenère encryption for key encryption.

---

## Key Features

### Encryption & Decryption
- Encrypt and decrypt text from files or URLs using the Four-Square Cipher
- Supports both file and URL input within the same session
- Preserves original formatting (spacing and non-alphabetical characters)
- Handles padding by storing original message length to ensure accurate decryption

### Key Management
- Generate and manage cipher keys dynamically
- Encrypt keys using the Vigenère Cipher for added security
- Import and reuse previously generated keys
- Automatically regenerates Four-Square Cipher grids each session
- Replaces letter 'J' with 'I' to comply with classical cipher rules

### User Interface & Output
- Interactive menu-driven command-line interface
- Built-in help and information system
- Display cipher grids visually in the terminal
- Export encrypted/decrypted text, keys, and Four-Square Cipher layouts

---

## Technologies Used

- Java
- Command Line Interface (CLI)
- File I/O (Java)
- Classical Cryptography Algorithms

---

## How to Run

1. Clone the repository
   ```bash
   git clone https://github.com/rnolan208/FourSquareCipher_ATU_2025.git
   cd FourSquareCipher_ATU_2025

2. Compile the project
   javac *.java

3. Run the program
   java Runner

4. Navigate the menu
 - Options 0–9 for functionality
 - Option i for information about features
 - Ensure an input source (file or URL) and output directory are specified before processing

---
<!--
## Application Images

### Application Menu
![]()
*Figure 1: *

### Application
![]()
*Figure 2: *

### Application
![]()
*Figure 4: *

---
-->

## Testing & Validation

- Successfully encrypted and decrypted text files and URLs
- Verified correctness of cipher transformations
- Tested key generation and reuse functionality
- Confirmed proper file import/export operations
- Verified correct handling of padding and character formatting during encryption/decryption  

---

## Runtime Behaviour

- Four-Square Cipher keys are randomly generated at runtime for each session  
- Vigenère Cipher uses a fixed key: `THISISAFOURSQUARECIPHERKEY`  
- Users can process both encryption and decryption in a single session using separate inputs  
- Output files are automatically named and saved to the selected directory  
- Program ensures output directory is defined before allowing encryption/decryption  
- Cipher grids can be displayed visually in the terminal and exported as text files 

## Skills Demonstrated

- Java programming and object-oriented design
- File handling and input/output operations
- Implementation of classical cryptographic algorithms
- CLI application development
- User interaction and menu-driven program design
- Debugging and program validation

---

## Project Requirements

- Implement the Four-Square Cipher for encryption and decryption  
- Support file-based and URL input and output  
- Provide a menu-driven command-line interface  
- Allow generation and reuse of cipher keys   
- Display cipher grids and layouts in the terminal 

---

## Project Structure

- `Runner.java` – Entry point and application launcher  
- `Menu.java` – Handles user interaction and menu navigation  
- `Parser.java` – Processes input from files and URLs  
- `cipherArray.java` – Implements Four-Square Cipher logic  
- `VigenèreCipher.java` – Handles key encryption  
- `ConsoleColour.java` – Adds coloured terminal output  
- `README.md` – Project documentation  

---

## Learning Outcomes

Through this project, I developed practical skills in:

- Applying cryptography concepts in software development
- Building structured Java applications
- Managing user input and file processing
- Designing interactive terminal-based tools

---

## Author

- Robert Nolan  
- ATU Student – Object-Oriented Software Development

---

## Disclaimer

This project was created for academic purposes as part of coursework.