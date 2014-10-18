import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * tester class for pascal parser using given test files
 * @author sadhika
 *
 */
import scanner.*;
import parser.*;
public class Main 
{

	/**
	 * this method tests the parser
	 * 
	 * @param args
	 * @throws ScanErrorException 
	 * @throws IllegalArgumentException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, ScanErrorException, FileNotFoundException 
	{
		FileInputStream test0= new FileInputStream(new File("parserTest0.txt"));
		FileInputStream test1= new FileInputStream(new File("parserTest1.txt"));
		FileInputStream test2= new FileInputStream(new File("parserTest01.txt"));
		FileInputStream test3= new FileInputStream(new File("parserTest2.txt"));
		FileInputStream test4= new FileInputStream(new File("parserTest3.txt"));
		FileInputStream test5= new FileInputStream(new File("parserTest4.txt"));
		
		Scanner scannie = new Scanner(test0);
		Parser parsey = new Parser(scannie);
		while (scannie.hasNext())
			parsey.parseStatement();
		
		Scanner sascandra = new Scanner(test1);
		Parser parsanne = new Parser(sascandra);
		while (sascandra.hasNext())
			parsanne.parseStatement();
		
		Scanner scabrina = new Scanner(test2);
		Parser parmela = new Parser(scabrina);
		while (scabrina.hasNext())
			parmela.parseStatement();
		
	}

}
