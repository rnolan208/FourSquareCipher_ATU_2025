package ie.atu.sw;

//import the random package
import java.util.Random;

public class cipherArray {
	
	//create the cihper layout with the 10x10 grid
	private char[][] cipherGrid = new char[10][10];
	
	
	// create two string Keys for the cipher
	private String key1;
	private String key2;
	
	// creates a shared random
	private Random rand = new Random(); 
	
    public cipherArray() {
    	// there are 2 keys to ensure both random parts 
    	// it will redo making key2 if its the same as key1 (unlikely but possible)
    	// both keys are guaranteed to be different to each other
    	key1 = generateRandomKey();
        do {
            key2 = generateRandomKey();
        } while (key1.equals(key2));
        
        //to build the cipher layout
        createCipherLayout();
        
        //------TEMPORARY PRINT FOR DEBUGGING------
        //---(NEEDS TO BE RUN IN RUNNER FOR NOW)---
        //printCipherGrid();
        //-----------------------------------------
        
    }
	
    /*------------------------------------------------------
     * making the 4 square cipher layout with Alphabet in 5x5 
     * and then random in 5x5, then inverted below 
     * with different random
     *---------------------*/
    
    private void createCipherLayout() {

        fillAlphabet(0, 0);   // top-left
        fillAlphabet(5, 5);   // bottom-right

        fillKeyedSquare(key1, 0, 5); // top-right
        fillKeyedSquare(key2, 5, 0); // bottom-left
    }
    
    //Making Alphabetical parts
    private void fillAlphabet(int rowStart, int colStart) {
    	String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ"; // no J
        int index = 0;
        
        //loops to place the letters (i for row, j for column)
        for (int i = rowStart; i < rowStart + 5; i++) {
        	for (int j = colStart; j < colStart + 5; j++) {
        		cipherGrid[i][j] = alphabet.charAt(index++);
        	}
        }
    }
    
    //Making random key parts
    private void fillKeyedSquare(String key, int rowStart, int colStart) {
        int index = 0;
        
        for (int i = rowStart; i < rowStart + 5; i++) {
            for (int j = colStart; j < colStart + 5; j++) {
                cipherGrid[i][j] = key.charAt(index++);
            }
        }
    }
    
    
    private String generateRandomKey() {
        char[] alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ".toCharArray();
        
        // Fisher–Yates shuffle
        for (int i = alphabet.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            char temp = alphabet[i];
            alphabet[i] = alphabet[j];
            alphabet[j] = temp;
        }

        return new String(alphabet);
    }
    
    
    // ------------------------------------
    // METHOD TO PRINT CIPHER FOR DEBUGGING
    // ------------------------------------
    /*
    public void printCipherGrid() {
        System.out.println("Four-Square Cipher Grid:\n");

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(cipherGrid[i][j] + " ");
            }
            System.out.println();
        }
    }
    */
    
    // ------------------------------------------------------------------------
    // METHOD TO PRINT CIPHER TO FILE WITHOUT COLOUR BACKGROUND (MENU OPTION 9)
    // ------------------------------------------------------------------------
    public char[][] getCipherGrid() {
  	  return cipherGrid;
    }
    
    public String getCipherAsString() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                sb.append(cipherGrid[i][j]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
    
    // ----------------------------------------------
	// COLOURED CIPHER LAYOUT FOR CONSOLE PREVIEW (MENU OPTION 9)
	// ----------------------------------------------
    public String getCipherAsColouredString() {

        StringBuilder sb = new StringBuilder();

        // prints using the ANSI colours provided
        sb.append(ConsoleColour.BLACK_BRIGHT)
          .append("Four-Square Cipher Grid\n\n")
          .append(ConsoleColour.RESET);

        // i is the row index
        // j is the column index
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                boolean topLeft     = i < 5 && j < 5;		// standard alphabet
                boolean topRight    = i < 5 && j >= 5;		// random alphabet
                boolean bottomLeft  = i >= 5 && j < 5;		// random alphabet
                boolean bottomRight = i >= 5 && j >= 5;		// standard alphabet

                if (topLeft || bottomRight) {
                    // For standard Alphabet -> Blue Solid background, white text
                    sb.append(ConsoleColour.WHITE_BOLD)
                      .append(ConsoleColour.BLUE_BACKGROUND_BRIGHT);
                } 
                else if (topRight) {
                    // Top Right -> Red Checkered Pattern background every second cell, black text
                    if ((i + j) % 2 == 0) {
                        sb.append(ConsoleColour.BLACK_BOLD)
                          .append(ConsoleColour.RED_BACKGROUND_BRIGHT);
                    } else {
                        sb.append(ConsoleColour.BLACK_BOLD);
                    }
                }
                else if (bottomLeft) {
                        // Bottom-left -> Green Checkered Pattern background, black text
                		if ((i + j) % 2 == 0) {
                        sb.append(ConsoleColour.BLACK_BOLD)
                          .append(ConsoleColour.GREEN_BACKGROUND_BRIGHT);
                		} else if ((j) % 2 == 0) {
                			// Bottom-left checker pattern: yellow on remaining uneven rows, black text
                            sb.append(ConsoleColour.BLACK_BOLD)
                              .append(ConsoleColour.YELLOW_BACKGROUND_BRIGHT);
                        } else {
                        sb.append(ConsoleColour.BLACK_BOLD);
                    }
                }

                sb.append(" ")
                  .append(cipherGrid[i][j])
                  .append(" ")
                  .append(ConsoleColour.RESET);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
	
	
	 // --------------------------------------
	 // GETTERS FOR MENU OPTION 8 (KEY OUTPUT)
	 // --------------------------------------
	 public String getKey1() {
	     return key1;
	 }
	
	 public String getKey2() {
	     return key2;
	 }
	    
	 //-------------------------------------------
	 // SETTERS FOR LOADED KEYS (MENU OPTIONS 3, 7)
	 //-------------------------------------------
	 public void setKeys(String key1, String key2) {
		 this.key1 = key1;
		 this.key2 = key2;

	     // rebuild cipher grid with new keys
	     createCipherLayout();
	 }
	 
	 
	//------------------------------------------------------------
	// Method Find in Grid (For Encryption and Decryption Methods)
	//------------------------------------------------------------
	
	/* Used to search for a character within the 4 Square Cipher Grid and return position
	 * 
	 * c		-> character to locate (uppercase letter A-Z)
	 * rowStart	-> starting row index to search
	 * colStart	-> starting column index to search
	 * numRows	-> number of rows in the grid
	 * numCols	-> number of columns in the
	 */
	
	private int[] findInGrid(char c, int rowStart, int colStart, int numRows, int numCols) {
		
		// loops through each row from rowStart to rowStart + numRows
		for (int i = rowStart; i < rowStart + numRows; i++) {
			   
			//loops through each column from colStart to colStart + numCols
		    for (int j = colStart; j < colStart + numCols; j++) {
		    	   
		    	// compares the value within the grid cell with the search character c
		        if (cipherGrid[i][j] == c) {
		        	   
		        	// returns location if found (relative to square not full grid)
		            return new int[]{i - rowStart, j - colStart};
		        }
		    }
		}
		return null; // character not found
	}
	 
	 
	 
	//-----------------------------------------
	// ENCRYPTIN OF FILE / URL (MENU OPTIONS 4)
	//-----------------------------------------
	 
	/*
	  * Does the encryption in 2 Phases
	  * Extracts and encrypts the letters in pairs
	  * re-injects the encrypted letters back in original text format (punctuation)
	  */
	
	

	
	public String encryptFS(String plainText) {
		 
		
	    // PART 1 – Extract and Normalise Letters
		 
        // It works in pairs of letters, so stringbuilder for letters (only letters will be encrypted)
		StringBuilder letters = new StringBuilder();
		 
		// for loop which loops through input (plainText)
		// appends characters A-Z to the letters stringbuilder
		// makes them all uppercase 
		for (char c : plainText.toCharArray()) {
			if (Character.isLetter(c)) {
				// Makes all the letters uppercase for cipher
				char upper = Character.toUpperCase(c);
				// Replace letter J with letter I
				if (upper == 'J') upper = 'I';
	            letters.append(upper);
			}
		}
		
		// store original letter count of text (before adding padding)
	    int originalLettersCount = letters.length();
			
		// Pad with filler 'X' if odd number of letters (4 Square works in pairs of letters)
	    if (letters.length() % 2 != 0) {
	    	letters.append('X');
	    }
	    
	    
	    // PART 2 – Encrypt Letters in Pairs
	    
	    // To encrypt the letters in pairs
	    StringBuilder encLetters = new StringBuilder();
	    
	    // for the Progress Meter (from runner.java)
	    int totalPairs = letters.length() / 2;
	    int pairsProcessed = 0;
	    
	    // process through the letters in pairs
	    for (int i = 0; i < letters.length(); i +=2) {
	    	char a = letters.charAt(i);
	    	char b = letters.charAt(i + 1);
	    	
	    	// finds the position of char a in top-left 5x5 square
	    	// Arguments: start row, start column, number of rows, number of columns
	    	int[] posA = findInGrid(a, 0, 0, 5, 5); // top-left
	    	
	    	// finds the position of char b in bottom-right 5x5 square
	    	int[] posB = findInGrid(b, 5, 5, 5, 5); // bottom-right
	    	
	    	// applies the encrypted letters in place of the original letters
	    	char encA = cipherGrid[posA[0]][posB[1] + 5]; // top-right
            char encB = cipherGrid[posB[0] + 5][posA[1]]; // bottom-left
            
            // appends the letters in pairs
            encLetters.append(encA).append(encB);
	    	
	    	// for the Progress Meter (from runner.java)
	    	pairsProcessed++;
	    	Runner.printProgress(pairsProcessed, totalPairs);
	    }
	    
	    
	    // PART 3 – Rebuild the Original Formatting
	    
	    StringBuilder result = new StringBuilder();
	    int encIndex = 0;
	    
	    for (char c : plainText.toCharArray()) {
	        if (!Character.isLetter(c)) {
	        	// maintains the original punctuation and spacing
	            result.append(c);
	        } else {
	            result.append(encLetters.charAt(encIndex++));
	        }
	    }
	        // adds the original letter count and : at the beginning of the file
	    	// so it can be used in decryption if there is any padding
	        return originalLettersCount + ":" + result.toString();
	    }
		

	 
	 //-----------------------------------------
	 // DECRYPTIN OF FILE / URL (MENU OPTIONS 5)
	 //-----------------------------------------
	 
	 /*
	  * Does the decryption in 2 Phases
	  * Extracts and decrypts the letters in pairs
	  * re-injects the decrypted letters back in original text format (punctuation)
	  */
	 
	 public String decryptFS(String cipherText) {
		 
		 
		 // PART 1 – Read data relating the letters count (to remove padding)
		 
		 // finds the index of where ':' is at the beginning of file (original letters count)
		 int colonIndex = cipherText.indexOf(":");
		 
		 // stores the number of letters within the encrypted text so padding can be removed if required
		 int originalLettersCount = Integer.parseInt(cipherText.substring(0, colonIndex));
		 
		 // stores the encrypted content without the original letters count or ':'
		 String actualCipher = cipherText.substring(colonIndex + 1);
		 
		 
		 // PART 2 – Extract Letters
		 
		 // It works in pairs of letters, so stringbuilder for letters (only A-Z letters will be encrypted)
		 StringBuilder letters = new StringBuilder();
		
		 // for loop which loops through input (actualCipher = cipherText without original letters count)
		 // appends characters A-Z to letters stringbuilder
		 // makes them all uppercase 
		 for (char c : actualCipher.toCharArray()) {
			 if (Character.isLetter(c)) {
				letters.append(Character.toUpperCase(c));
			 }
		 }
		 
		 
		 // PART 3 – Decrypt in Pairs
		 
		 // Decrypt letters in pairs
		 StringBuilder decryptedLetters = new StringBuilder();
		 
		 // for the Progress Meter (from runner.java)
		 int totalPairs = letters.length() / 2;
		 int pairsProcessed = 0;
		 
		 for (int i = 0; i < letters.length(); i += 2) {
			 char a = letters.charAt(i);
			 char b = (i + 1 < letters.length()) ? letters.charAt(i + 1) : 'X'; // safety padding
		 
			 	// finds the position of char a in top-right 5x5 square
				// Arguments: start row, start column, number of rows, number of columns
				int[] posA = findInGrid(a, 0, 5, 5, 5); // top-right
				
				// finds the position of char b in bottom-left 5x5 square
	            int[] posB = findInGrid(b, 5, 0, 5, 5); // bottom-left
	            
	            // applies the decrypted letters in place of the original letters
	            char decA = cipherGrid[posA[0]][posB[1]]; // top-left
	            char decB = cipherGrid[posB[0] + 5][posA[1] + 5]; // bottom-right

	            // appends them to the stringbuilder in their original locations
	            decryptedLetters.append(decA).append(decB);
			
	            // update the Progress Meter (from runner.java)
	            pairsProcessed++;
	            Runner.printProgress(pairsProcessed, totalPairs);
		}
		
		 
		// PART 4 – Rebuild the Original Formatting

		StringBuilder finalOutput = new StringBuilder();
		int lettersCount = 0;  
		for (char c : actualCipher.toCharArray()) {
	        if (Character.isLetter(c)) {
	            // Only append up to the original letters count
	            if (lettersCount < originalLettersCount) {
	                finalOutput.append(decryptedLetters.charAt(lettersCount++));
	            } else {
	                break; // stop adding letters after original count
	            }
	        } else {
	            finalOutput.append(c); // keep punctuation in place
	        }
	    }

	    // append remaining non-letter characters after last letter (punctuation)
	    for (int i = finalOutput.length(); i < actualCipher.length(); i++) {
	        char c = actualCipher.charAt(i);
	        if (!Character.isLetter(c)) finalOutput.append(c);
	    }
		
		// returns the fully decrypted text with
	    // original spacing, punctuation, and formatting
		return finalOutput.toString();
	 }
	 

}