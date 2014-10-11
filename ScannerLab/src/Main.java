import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Tests the functionality of the Scanner class by creating an object and tokenizing a test file
 * @author Sadhika Malladi
 * @date 09.02.14
 *
 */
public class Main {

	/**
	 * reads the test file, continues even if invalid token is encountered
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		InputStream inStream = new FileInputStream("/home/sadhika/workspace/ScannerLab/src/ScannerTestFile.txt");
	    Scanner lex = new Scanner(inStream);
	    while(lex.hasNext())
	    {
	    	try 
	    	{
				System.out.println(lex.nextToken());
			} 
	    	catch (ScanErrorException e) {}
	    }
				
	}

}
