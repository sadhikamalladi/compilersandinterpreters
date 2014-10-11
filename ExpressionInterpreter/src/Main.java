import java.io.BufferedReader;
import java.io.IOException;

import expressionTree.*;
import parser.*;
import scanner.*;
import scanner.ScanErrorException;
import java.io.*;

public class Main {

	/**
	 * tests the arithmetic expression parser and evaluates the exp node returned.
	 * 
	 * @param args
	 * @throws ParseErrorException 
	 * @throws ScanErrorException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ScanErrorException, ParseErrorException, IOException 
	{
		EvalState state = new EvalState();
		while (true)
		{
			ExpressionParser parser = new ExpressionParser();
			
			System.out.println("Enter arithmetic expression: ");
			BufferedReader inStream = new BufferedReader(new InputStreamReader(System.in));
			String in = inStream.readLine();
			ExpNode node = parser.parse(in);
			System.out.println("Finished parse");
			System.out.println(node.eval(state));
		}
	}

}
