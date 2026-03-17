package ie.atu.sw;

import static java.lang.System.out;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {
	// -------------------------------------------------------
	// Link and create the cipherArray for use in Menu Methods
	// -------------------------------------------------------
	private cipherArray cip = new cipherArray();

	// ----------------------------------
	// Allows users to input from Console
	// ----------------------------------
	private Scanner s;

	// -------------------------------------------------
	// For storing the plaintext which will be encrypted
	// -------------------------------------------------
	private String plaintext;

	// -------------------------------------------------
	// For storing the ciphertext which will be decrypted
	// -------------------------------------------------
	private String cipherText;

	// ---------------------------------------------------------
	// For storing the output directory location (Menu Option 2)
	// ---------------------------------------------------------
	private File outputDirectory;

	// ----------------------------------------------------------------------
	// Encrypted key storage (Menu options 3, 6, 7, 8, using Vigenère Cipher)
	// ----------------------------------------------------------------------
	private String encryptedKey1;
	private String encryptedKey2;
	private String tempKey1;
	private String tempKey2;
	private boolean keysEncrypted = false;

	// -------------------------------------------------------
	// Fixed Vigenère Key (DO NOT CHANGE) (MENU Options 6 & 7)
	// -------------------------------------------------------
	private static final String FIXED_VIGENERE_KEY = "THISISAFOURSQUARECIPHERKEY";

	// ------------------------------------------------
	// To start program and not end up in infinite loop
	// ------------------------------------------------
	private boolean keepRunning = true;

	public Menu() {
		s = new Scanner(System.in);
	}

	// -------------------------------------
	// Methods for each option from the Menu
	// -------------------------------------
	public void start() throws Exception {
		while (keepRunning) {
			showOptions();

			// Had to make them String instead of Integer to have info option "i"
			// Used toLowerCase to allow i or I to be input
			String choice = s.next().toLowerCase();

			// ---------------------------------------
			// Switch Statement for Menu Input Methods
			// ---------------------------------------
			switch (choice) {
				case "0" -> keepRunning = false;
				case "1" -> inputFileToEncrypt();
				case "2" -> outputFile();
				case "3" -> inputCipherKeys();
				case "4" -> encryptFile();
				case "5" -> decryptFile();
				case "6" -> encryptGeneratedKeys();
				case "7" -> decryptGeneratedKeys();
				case "8" -> outputEncryptedKeys();
				case "9" -> outputCipherSquare();
				case "i" -> showInfoMenu();
				default -> out.println("[Error] Invalid Selection");
				}
			}
			out.println("Exiting 4 Square Cipher...Goodbye!");
		}

	
	// -------------------------------------------------
	// Option 1 - Input File / URL For Encryption / Decryption
	// -------------------------------------------------
	private void inputFileToEncrypt() throws Exception {
		// Ask user do they want to Encrypt or Decrypt
		// while loop to keep asking until given valid mode (1 or 2)
		int mode = 0;

		while (mode != 1 && mode != 2) {
			out.println("Is this for:");
			out.println("(1) Encryption");
			out.println("(2) Decryption");
			out.print("Please enter your choice: ");

			// reads input choice
			mode = s.nextInt();
			s.nextLine();

			// If user enters an invalid option it asks again
			if (mode != 1 && mode != 2) {
				out.println("Invalid selection. Please try again.\n");
			}
		}

		if (mode == 1) { // ENCRYPTING
			// Ask user if text file or URL
			// while loop to keep asking until given valid choice (1 or 2)
			int choice = 0;

			while (choice != 1 && choice != 2) {
			out.println("Select your input type:");
			out.println("(1) Text File");
			out.println("(2) URL");
			out.println("Please enter your choice: ");

			// reads input choice
			choice = s.nextInt();
			s.nextLine();
			
			// If user enters an invalid option it asks again
			if (choice != 1 && choice != 2) {
				out.println("Invalid selection. Please try again.\n");
			}
		}
				// Nested If Loop
				// Text File Input
				if (choice == 1) {
					// asks user to input text file path
					out.println("Enter path to text file: ");	// User enters files path
					String filePath = s.nextLine();
					// confirms if file loaded correctly
					plaintext = Parser.readFromFile(filePath);	// file is saved as plainText for encryption (Option 4)
					out.println("You have successfully loaded the file.");
				} else { // URL Input
					// asks user to input URL address
					out.println("Enter URL: ");					// User enters URL
					String url = s.nextLine();
					// confirms if URL entered correctly
					plaintext = Parser.readFromURL(url);		// URL is saved as plainText for encryption (Option 4)
					out.println("You have successfully input the URL.");
				}
				
			out.println("Plaintext loaded for encryption.");
				
				
		} else { // DECRYPT
				out.println("Enter path to encrypted file: "); 	//User enters files path
				String filePath = s.nextLine();
				cipherText = Parser.readFromFile(filePath);		// file is saved as cipherText for decryption (Option 5)
				out.println("Ciphertext loaded for decryption.");
			}
	}

	
	// --------------------------------------------------------
	// Option 2 - Select Output Destination For Encrypted File
	// --------------------------------------------------------
	private void outputFile() throws Exception {

		// To use in while loop to ensure valid directory is set
		boolean validDirectory = false;

		while (!validDirectory) {
			out.println("Select path for Output Destination (press Enter for default): ");
			String path = s.nextLine();
			
			s.nextLine();
			
			// If user presses Enter, use current directory as default
	        if (path.isEmpty()) {
	            outputDirectory = new File("."); // current working directory
	            validDirectory = true;
	            out.println("Using default output location: " + outputDirectory.getAbsolutePath());
	            return;
	        }

			File dir = new File(path);

			// nested if loop to check whether file directory exists
			// and is a directory
			if (dir.exists() && dir.isDirectory()) {
				outputDirectory = dir;
				validDirectory = true;

				out.println("The path output has been set to: " + dir.getAbsolutePath());
			} else {
				out.println("The path output selected in not valid.");
				out.println("Please enter a valid directory path.");
			}
		}

	}

	
	// ----------------------------------------
	// Option 3 - Input (Encrypted) Key for use
	// ----------------------------------------
	private void inputCipherKeys() throws Exception {
		// Ask user do they want to upload Plain text or Encrypted keys
		// while loop to keep asking until given valid choice (1 or 2)
		int choice = 0;		
		
		while (choice != 1 && choice != 2) {
			out.println("Select key format:");
			out.println("(1) Plain text key");
			out.println("(2) Encrypted keys");
			out.print("Please enter your choice: ");

			// reads input choice
			choice = s.nextInt();
			s.nextLine();

			// If user enters an invalid option it asks again
			if (choice != 1 && choice != 2) {
				out.println("Invalid selection. Please try again.\n");
			}
		}
		

		// -----------------
		// Plain keys
		// -----------------
		if (choice == 1) {

				// While loop boolean condition
				boolean validKeys = false;
				
				// While loop to ensure keys are correct length without letter 'J'
				while (!validKeys) {
					
					// Input first key
					out.println("Paste Key 1 (25 characters, no J):");
					tempKey1 = s.nextLine().trim().toUpperCase();
					// Input second key
					out.println("Paste Key 2 (25 characters, no J):");
					tempKey2 = s.nextLine().trim().toUpperCase();
					
					if (tempKey1.length() == 25 &&
		                    tempKey2.length() == 25 &&
		                    tempKey1.matches("[A-Z&&[^J]]{25}") &&
		                    tempKey2.matches("[A-Z&&[^J]]{25}")) {
	
		                    validKeys = true;
		                } else {
		                    out.println("[Error] Keys must be exactly 25 characters and contain no 'J'.");
		                }
					}
					
					cip.setKeys(tempKey1, tempKey2);
		            out.println("Keys loaded successfully.");
				}

		// -----------------
		// Encrypted keys
		// -----------------
		else {
				
				// While loop boolean condition
				boolean validKeys = false;
				
				// While loop to ensure keys are correct length, can contain 'J' as its encrypted with Vigenère Cipher
				while (!validKeys) {
					
					// Input first key
					out.println("Input Encrypted Key 1 (25 characters): ");
					tempKey1 = s.nextLine().trim().toUpperCase();
		
					// Input second key
					out.println("Input Encrypted Key 2 (25 characters): ");
					tempKey2 = s.nextLine().trim().toUpperCase();
		
					if (tempKey1.length() == 25 && tempKey2.length() == 25) {
	                    validKeys = true;
	                	} else {
	                		out.println("[Error] Encrypted keys must be exactly 25 characters.");
	                	}
	            }
					
					keysEncrypted = true;
		
					out.println("Encrypted keys loaded.");
					out.println("Decrypting keys...");
					
					// Invokes option 7 automatically to decrypt keys
					decryptGeneratedKeys();
					
					out.println("Keys decrypted and loaded successfully.");
			
			}
		}

	
	// --------------------------------
	// Option 4 - Encrypt Selected File
	// --------------------------------
	private void encryptFile() throws Exception {
		out.println("Encrypting File...");
		
		// Check if a file has been uploaded for encrypting
		if (plaintext == null) {
			out.println("No input text loaded. Please use Option 1 first.");
			return;
		}

		// Check if an output directory has been selected before proceeding
		if (outputDirectory == null) {
			out.println("No output directory set. Please select option 2 on the Menu before encrypting your file.");
			return;
		}
		
		String encryptedText = cip.encryptFS(plaintext);
		
		// variable for file to print - destination and file name
		File outFile = new File(outputDirectory, "encryptedFile.txt");
		
		PrintWriter pw = new PrintWriter(outFile);
		
		pw.print(encryptedText);
		pw.close();
		
		out.println("File encrypted successfully:");
		out.println(outFile.getAbsolutePath());	

	}

	
	// --------------------------------
	// Option 5 - Decrypt Selected File
	// --------------------------------
	private void decryptFile() throws Exception {
		out.println("Decrypting File...");
		
		// Check if a file has been uploaded for encrypting
			if (cipherText == null) {
				out.println("No input text loaded. Please use Option 1 first.");
				return;
			}

		// Check if an output directory has been selected before proceeding
		if (outputDirectory == null) {
			out.println("No output directory set. Please select option 2 on the Menu before decrypting your file.");
			return;
		}
		
		String decryptedText = cip.decryptFS(cipherText);
		
		File outFile = new File(outputDirectory, "decryptedFile.txt");
		
		PrintWriter pw = new PrintWriter(outFile);
		
		pw.print(decryptedText);
		pw.close();
		
		out.println("File decrypted successfully:");
		out.println(outFile.getAbsolutePath());
	
	}

	
	// -----------------------------------------
	// Option 6 - Encrypt Current Generated Keys
	// -----------------------------------------
	private void encryptGeneratedKeys() throws Exception {

		// check if the cipher has been started, otherwise there are no generated keys
		if (cip == null) {
			out.println("Cipher not initialised.");
			return;
		}

		out.println("Encrypt Current Session Generated Keys: ");

		// Use Vigenère Cipher for the Key Encryption,
		// the fixed keyword will be: FIXED_VIGENERE_KEY = "THISISAFOURSQUARECIPHERKEY"
		// set at top of Menu.java file, before Methods
		// key is 26 characters so more than enough for the encryption (only needs 25)

		VigenèreCipher vc = new VigenèreCipher(FIXED_VIGENERE_KEY);

		encryptedKey1 = vc.encryptVin(cip.getKey1());
		encryptedKey2 = vc.encryptVin(cip.getKey2());

		keysEncrypted = true;

		out.println("Keys encrypted successfully.");
	}

	
	// ----------------------
	// Option 7 - Decrypt Key
	// ----------------------
	private void decryptGeneratedKeys() throws Exception {
		out.println("Decrypt Encrypted Keys: ");
		out.println();

		// Check if the keys are already Encrypted
		if (!keysEncrypted || tempKey1 == null || tempKey2 == null) {
			out.println("No encrypted keys available.");
			return;
		}

		// Use VigenèreCipher.java to Decrypt Keys
		VigenèreCipher vc = new VigenèreCipher(FIXED_VIGENERE_KEY);

		String decKey1 = vc.decryptVin(tempKey1);
		String decKey2 = vc.decryptVin(tempKey2);

		// Save decrypted keys for use in cipherArray.java
		cip.setKeys(decKey1, decKey2);

		keysEncrypted = false;

		out.println("Keys decrypted and loaded successfully.");

	}

	
	// -------------------------------------------------
	// Option 8 - Print Out (Encrypted) Keys Into a File
	// -------------------------------------------------
	private void outputEncryptedKeys() throws Exception {
		out.println("Output Current Session Generated Keys: ");

		// Check if an output directory has been selected before proceeding
		if (outputDirectory == null) {
			out.println(
					"No output directory set. Please select option 2 on the Menu before printing your encrypted keys.");
			return;
		}

		// set the already Encrypted as false (for printing)
		boolean printEncrypted = false;

		// Check are Keys Encrypted (Option 6), if not give the option to use Option 6
		if (!keysEncrypted) {
			out.println("Keys have not been encrypted yet.");
			out.println("Would you like to encrypt them now? (Y/N): ");

			s.nextLine();
			
			
			// Get users response in terminal
			String response = s.nextLine().trim().toUpperCase();
			
			// Encrypt Keys if users selected Yes Above (Nested Loop)
			if (response.equals("Y")) {

				// This will use Option 6 to encrypt the keys
				encryptGeneratedKeys();
				printEncrypted = true;
			} else if (response.equals("N")) {

				// User does not want to encrypt the keys
				printEncrypted = false;
			} else {
				out.println("Invalid selection. Operation cancelled.");
				return;
			}
		} else {
			// Keys are already encrypted
			printEncrypted = true;
		}

		// Decide which filename based on whether the keys are encrypted
		String fileName = printEncrypted ? "Encrypted_Generated_Keys.txt" : "Generated_Keys.txt";

		// Create the output file in the selected directory
		File outFile = new File(outputDirectory, fileName);

		try (PrintWriter pw = new PrintWriter(outFile)) {

			// Print the Encrypted Keys
			if (printEncrypted) {
				pw.println("KEY STATUS: ENCRYPTED");
				pw.println();
				pw.println("Key 1 (Encrypted):");
				pw.println(encryptedKey1);
				pw.println();
				pw.println("Key 2 (Encrypted):");
				pw.println(encryptedKey2);
			} else {
				// Print the normally generated Keys
				pw.println("KEY STATUS: PLAIN TEXT");
				pw.println();
				pw.println("Key 1:");
				pw.println(cip.getKey1());
				pw.println();
				pw.println("Key 2:");
				pw.println(cip.getKey2());
			}

			out.println("Keys successfully written to:");
			out.println(outFile.getAbsolutePath());

		} catch (Exception e) {
			out.println("Error writing keys to file.");
		}
	}


	
	// ----------------------------------------------------
	// Option 9 - Print Out the 4 Square Cipher Into a File
	// ----------------------------------------------------
	
	// I tried to implement a try catch block in this for handling exceptions
	
	private void outputCipherSquare() {
		out.println("Output Current Session 4 Square Cipher");

		// Check if an output directory has been selected before proceeding
		if (outputDirectory == null) {
			out.println(
					"No output directory set. Please select option 2 on the Menu before printing the 4 Square Cipher.");
			return;
		}
		
		// 1) Preview in console (with colours)
	    out.println("\n--- Four Square Cipher Preview ---\n");
	    out.print(cip.getCipherAsColouredString());
	    out.println(ConsoleColour.RESET);

		// Create the output file in the selected directory
		File outFile = new File(outputDirectory, "4SquareLayout.txt");

		try (PrintWriter pw = new PrintWriter(outFile)) {

			// write the cipher layout to the file
			pw.print(cip.getCipherAsString());
			pw.close();

			// message to say print a success
			out.println("4 Square Cipher successfully written to: ");
			out.println(outFile.getAbsolutePath());

		} catch (Exception e) {
			out.println("Error writing cipher layout to file.");
		}
	}

	
	// --------------------------------------------------------------------
	// Option i - Menu Information Options Explaining What Each Choice Does
	// --------------------------------------------------------------------
	private void showInfoMenu() {
		out.println("\n--- Menu Information ---");
		out.print("Enter option number [0-9] for details: ");

		String infoChoice = s.next();

		// -----------------------------------------------
		// Switch Statement for Info Menu Input (Option i)
		// -----------------------------------------------
		switch (infoChoice) {
			case "0" -> out.println("Option 0 - Exit the application safely.");
			case "1" -> out.println("Option 1 - Selects the users file or URL which will be encrypted / decrypted using the Four Square Cipher.");
			case "2" -> out.println("Option 2 - Specifies the output folder path.");
			case "3" -> out.println("Option 3 - Allows user to enter existing cipher keys used for encryption / decryption.");
			case "4" -> out.println("Option 4 - Encrypts the selected plaintext file using the current cipher keys.");
			case "5" -> out.println("Option 5 - Decrypts a previously encrypted file using the current cipher keys.");
			case "6" -> out.println("Option 6 - Encrypts cipher keys generated during the current session.");
			case "7" -> out.println("Option 7 - Decrypt loaded enchrypted cipher keys, or existing ones if encrypted during this session.");
			case "8" -> out.println("Option 8 - Outputs cipher keys into file path from option 2. User can encrypt them via option 6 if required.");
			case "9" -> out.println("Option 9 - Outputs the current Four Square Cipher layout into file path from option 2. File will be called 4SquareLayout.txt");
			case "i" -> out.println("Option i - Opens the Information Menu.");
			default -> out.println("[Error] Invalid option for information.");
		}
		out.println();
	}

	
	// -------------------------
	// Menu print out to Console
	// -------------------------

	private void showOptions() {

		out.println(ConsoleColour.WHITE);
		out.println("************************************************************");
		out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		out.println("*                                                          *");
		out.println("*      Encrypting Files with a Four Square Cipher 1.0      *");
		out.println("*                                                          *");
		out.println("*              Robert Nolan - G00474404 - OOSD             *");
		out.println("*                                                          *");
		out.println("************************************************************");
		out.println("(1) Specify Text File / URL to Encrypt / Decrypt");
		out.println("(2) Specify Output Directory (default: ./out.txt)");
		out.println("(3) Enter Cipher Keys"); 
		out.println("(4) Encrypt Text File / URL");
		out.println("(5) Decrypt Text File / URL");
		out.println("(6) Encrypt Generated Keys");
		out.println("(7) Decrypt Encrypted Keys");
		out.println("(8) Output Encrypted Keys");
		out.println("(9) Output 4 Square Cipher");
		out.println("(0) Quit");
		out.println("(i) Information about menu options");

		// -------------------------------------------------------
		// Output a menu of options and solicit text from the user
		// -------------------------------------------------------
		out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
		out.print("Select Option [0-9]>");
		out.println();

	}

}


//------------------------------------------------------------------------------------------------
/*
 * COMMAND LINE MENU LAYOUT
 * 
 * (1) Specify Text File to Encrypt - Choose which file to use 
 * (2) Specify Output File (default: ./out.txt) - Choose which file to output 
 * (3) Enter Cipher Keys - Input your existing cipher keys (4) Encrypt Text File -Encrypt chosen file
 * (5) Decrypt Text File - Decrypt chosen file 
 * (6) Encrypt Generated Keys - Puts encryption on the randomly generated keys 
 * (7) Decrypt Generated Keys - Used to decrypt existing randomly generated keys 
 * (8) Output Keys - Print the encrypted keys
 * (9) Output 4 Square Cipher - Print the 4 Square Cipher 
 * (0) Quit - Quit the Program
 * (i) Information 
 * 
 */
//------------------------------------------------------------------------------------------------