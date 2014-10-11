import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Tests the functionality of the Scanner class by creating an object and tokenizing a test file
 * @author Sadhika Malladi
 * @date 09.02.14
 *
 */
public class Main 
{

	/**
	 * reads the test file, stops if invalid token is given as input and writes the appropriate error
	 * @param args
	 * @throws IOException when the arithmetic parser breaks
	 */
	public static void main(String[] args) throws IOException 
	{
		InputStream inStream = new FileInputStream("/home/sadhika/workspace/ScannerLab/src/ScannerTestFile.txt");
	    Scanner lex = new Scanner(inStream);
	    while(lex.nextToken() != "END")
	    {
			try 
			{
				System.out.println(lex.nextToken());
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
	    }
				
	}

}
