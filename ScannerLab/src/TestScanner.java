import java.io.*;

/**
 * a class created to test the Scanner class and methods getNextChar and eat.
 * 
 * @author Sadhika Malladi
 * @version 082914 created class and implemented main
 *
 */
public class TestScanner {
	
	/**
	 * test the Scanner class and methods getNextChar and eat.
	 * @throws FileNotFoundException if the scannerTest text file is not found in the current folder
	 * @throws ScanErrorException 
	 * 
	 */
	public static void main(String[] args) throws FileNotFoundException, ScanErrorException {
		InputStream inStream = new FileInputStream("/home/sadhika/workspace/ScannerLab/src/ScannerTestFile.txt");
	    Scanner lex = new Scanner(inStream);
	    
	}

}
