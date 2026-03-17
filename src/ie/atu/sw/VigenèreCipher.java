package ie.atu.sw;

public class VigenèreCipher {
	// Vigenère Cipher from Lectures, used for Encrypting the Generated Keys
	// within the 4 Square Cipher
	private char[] key;
	
	public VigenèreCipher(String key) {
		setKey(key);
	}
	
	public void setKey(String key) {
		this.key = key.trim().toUpperCase().toCharArray();
	}
	
	
	// ------------------------
    // Encrypt
    // ------------------------
	
	//renamed to encryptVin to avoid confusion with the 4 Square Cipher
	public String encryptVin(String plainText) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < plainText.length(); i++) {
			sb.append(getEncryptedCharacter(key[i], plainText.charAt(i)));
		}
		
		return sb.toString();
	}
	
	//Intersection of the row with the key and column with plaintext
	private char getEncryptedCharacter(char key, char plain) {
		for (int row = 0; row < TABULA_RECTA.length; row++) {
			if (TABULA_RECTA[row][0] == key) {
				for (int col = 0; col < TABULA_RECTA[row].length; col++) {
					if (TABULA_RECTA[0][col] == plain) {
						return TABULA_RECTA[row][col];
					}
				}
			}
		}
		return plain;
	}
	
	
	// ------------------------
    // Decrypt
    // ------------------------
	
	//renamed to decryptVin to avoid confusion with the 4 Square Cipher
	public String decryptVin(String plainText) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < plainText.length(); i++) {
			sb.append(getDecryptedCharacter(key[i], plainText.charAt(i)));
		}
		
		return sb.toString();
	}

	//Find column with the key and read down for the row with the cipher 
	private char getDecryptedCharacter(char key, char cipher) {
		for (int col = 0; col < TABULA_RECTA[0].length; col++) {
			if (TABULA_RECTA[0][col] == key) {
				for (int row = 0; row < TABULA_RECTA.length; row++) {
					if (TABULA_RECTA[row][col] == cipher) {
						return TABULA_RECTA[row][0];
					}
				}
			}
		}
		
		return cipher;
	}
	
	private static final char[][] TABULA_RECTA = { 
			{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'},
			{'B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A'},
			{'C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B'},
			{'D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C'},
			{'E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D'},
			{'F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E'},
			{'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F'},
			{'H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G'},
			{'I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H'},
			{'J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I'},
			{'K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J'},
			{'L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K'},
			{'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L'},
			{'N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M'},
			{'O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N'},
			{'P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O'},
			{'Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P'},
			{'R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q'},
			{'S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R'},
			{'T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S'},
			{'U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T'},
			{'V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U'},
			{'W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V'},
			{'X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W'},
			{'Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X'},
			{'Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y'}
		};
}