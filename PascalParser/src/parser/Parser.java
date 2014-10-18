package parser;

import java.util.HashMap;

import scanner.*;


/**
 * parses pascal statement, defined by the below grammar:
 * 	stmt -> WRITELN ( expr ) ; | BEGIN stmts END ; | id := expr ;
 * 	stmts -> stmts stmt | Empty String
 * 	expr -> expr - term | expr + term | term
 * 	term -> term * factor | term / factor | term mod num | factor
 * 	factor -> ( expr ) | - factor | num | id
 * 
 * certain parts have been left-factored to allow for recursive descent parsing.
 * 
 * added functionality for comments in format (* *) and modulus
 * 	
 * @author sadhika
 *
 */
public class Parser 
{
	private Scanner scannie;
	private String currentToken;
	private HashMap map;

	/**
	 * initializes scannie instance variable and reads in first token. initializes
	 * hashmap to keep track of variable assignments.
	 * 
	 * @param scanny scanner for this parser
	 */
	public Parser(Scanner scanny) 
	{
		scannie=scanny;
		try 
		{
			currentToken=scannie.nextToken();
		} 
		catch (ScanErrorException e) 
		{}
		map=new HashMap<String,Integer>();
	}

	/**
	 * takes in an expected token, updates instance variable currentToken to
	 * next token if the expected token matches currentToken. otherwise, throws
	 * an illegal argument exception. a scan error exception is thrown if the 
	 * scanner is at the end of the file.
	 * 
	 * @param expectedToken	the token expected to be the currentToken
	 * @throws IllegalArgumentException	if the given token does not match the current
	 * 									token
	 * @throws ScanErrorException if the scanner cannot read the next token.
	 */
	private void eat(String expectedToken) throws IllegalArgumentException, ScanErrorException
	{
		if(expectedToken.compareTo(currentToken)==0)
			currentToken=scannie.nextToken();
		else
			throw new IllegalArgumentException("Cannot eat "+expectedToken+" because current token is "+currentToken);
	}

	/**
	 * eats token (a number) and returns value
	 * 
	 * @precondition the current token is an integer
	 * @postcondition the number has been eaten
	 * @return the value of the parsed integer
	 * @throws ScanErrorException if the scanner cannot read the next token
	 * @throws IllegalArgumentException if the scanner is not on currentToken
	 */
	private int parseNumber() throws IllegalArgumentException, ScanErrorException
	{
		int returnValue=Integer.parseInt(currentToken);
		eat(currentToken);
		return returnValue;
	}

	/**
	 * parses a statement, defined by the grammar (tokens separated by spaces):
	 * 	stmt -> WRITELN ( expr ) ; | BEGIN whilebegin | id := expr ;
	 * 	whilebegin -> END ; | stmt whilebegin
	 * 
	 * eats all tokens associated with the above grammar and prints out the evaluation
	 * of the expression within WRITELN()
	 * 
	 * @throws ScanErrorException  if eat fails
	 * @throws IllegalArgumentException if the statement is not WRITELN
	 */
	public void parseStatement() throws IllegalArgumentException, ScanErrorException
	{
		if (currentToken.equals("WRITELN"))
		{
			eat("WRITELN");
			eat("(");
			System.out.println(parseExpression());
			eat(")");
			eat(";");
		}
		else if (currentToken.equals("BEGIN"))
		{
			parseStatements();
		}
		else if (currentToken.equals("*"))
		{
			eat("(");
			boolean commentEnd=false;
			while (!commentEnd && scannie.hasNext())
			{
				eat(currentToken);
				if (currentToken.equals("*"))
				{
					eat("*");
					commentEnd=currentToken.equals(")");
				}
			}
			if(!commentEnd)
				throw new ScanErrorException("Comment not completed.");
			else
				eat(")");
		}
		else
		{
			String key = currentToken;
			eat(key);
			eat(":=");
			int value = parseExpression();
			map.put(key, value);
		}
	}
	
	/**
	 * parses a block of statements defined by the grammar
	 * 
	 * @throws ScanErrorException 
	 * @throws IllegalArgumentException 
	 */
	private void parseStatements() throws IllegalArgumentException, ScanErrorException
	{
		eat("BEGIN");
		while (! currentToken.equals("END"))
			parseStatement();
		eat("END");
		eat(";");
	}

	/**
	 * recursively parses a factor, defined by the below grammar:
	 * 	stmt -> WRITELN ( factor ) ;
	 * 	factor -> ( expr )
	 * 	factor -> - factor
	 * 	factor -> num
	 * mediate filaments are extremely stable. Even after extraction with solutions containing detergents and high concentratio
	 * the base case is when the factor is a number.
	 * @throws ScanErrorException 
	 * @throws IllegalArgumentException 
	 */
	public int parseFactor() throws IllegalArgumentException, ScanErrorException
	{
		int returnValue=0;
		if (Scanner.isDigit(currentToken.charAt(0))) 
			returnValue=parseNumber(); // base case
		if (currentToken.equals("(")) // factor -> ( term )
		{
			eat(currentToken);
			returnValue=parseExpression();
			eat(")");
		}
		if (currentToken.equals("-")) // factor -> - factor
		{
			eat(currentToken);
			returnValue=-1*parseFactor();
		}
		return returnValue;
	}

	/**
	 * recursively parses a term, defined by the below grammar:
	 * 	term -> factor whileterm
	 * 	whileterm -> * factor whileterm | / factor whileterm | Empty String
	 * 	factor -> ( term ) | - factor | num | id
	 * 
	 * parse the first factor, and parse the whileterm using a while loop, 
	 * multiplying or dividing factors while the current token is * or /
	 * 
	 * @return integer value of the term
	 * @throws ScanErrorException if scanning fails
	 * @throws IllegalArgumentException if there is no valid factor
	 */
	public int parseTerm() throws IllegalArgumentException, ScanErrorException
	{
		int lhs = parseFactor();

		while (currentToken.equals("*") || currentToken.equals("/"))
		{
			if (currentToken.equals("*"))
			{
				eat("*");
				int rhs = parseFactor();		
				lhs = lhs*rhs;
			}
			else
			{
				eat("/");
				int rhs = parseFactor();
				lhs = lhs/rhs;
			}
		}

		return lhs;
	}

	/**
	 * parses an expression, defined by the below grammar:
	 * 	expr -> expr + term | expr - term | term mod num | term
	 * 
	 * @return the value of the expression
	 * @throws ScanErrorException 
	 * @throws IllegalArgumentException 
	 */
	public int parseExpression() throws IllegalArgumentException, ScanErrorException
	{
		int lhs = parseTerm();

		if (currentToken.equals("mod"))
		{
			eat("mod");
			int rhs = parseNumber();
			lhs = lhs % rhs;
		}
		else
		{
			while (currentToken.equals("-") || currentToken.equals("+"))
			{
				if (currentToken.equals("+"))
				{
					eat("+");
					int rhs = parseExpression();
					lhs= lhs+rhs;
				}
				else
				{
					eat("-");
					int rhs = parseExpression();
					lhs= lhs-rhs;
				}
			}
		}
		return lhs;
	}

}
