package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import scanner.*;
import ast.Assignment;
import ast.Block;
import ast.Expression;
import ast.Number;
import ast.Statement;
import ast.Writeln;


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
	private Number parseNumber() throws IllegalArgumentException, ScanErrorException
	{
		int returnValue=Integer.parseInt(currentToken);
		eat(currentToken);
		return new Number(returnValue);
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
	public Statement parseStatement() throws IllegalArgumentException, ScanErrorException
	{
		while (!currentToken.equals("."))
		{
			if (currentToken.equals("WRITELN"))
			{
				eat("WRITELN");
				eat("(");
				Expression exp = parseExpression();
				eat(")");
				eat(";");
				return new Writeln(exp);
			}
			else if (currentToken.equals("BEGIN"))
			{
				eat("BEGIN");
				List<Statement> stmts = new ArrayList<Statement>();
				while (! currentToken.equals("END"))
				{
					Statement stmt = parseStatement();
					stmts.add(stmt);
				}
				eat("END");
				eat(";");
				return new Block(stmts);
			}
			else
			{
				String key = currentToken;
				eat(key);
				eat(":=");
				Expression value = parseExpression();
				return new Assignment(key,value);
			}
		}
		eat(".");
		return null;
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
	public Expression parseFactor() throws IllegalArgumentException, ScanErrorException
	{
		Expression returnValue=new Number(0);
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
			Number intermediateValue = (Number)(parseFactor());
			returnValue= new Number(0-intermediateValue.getValue());
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
	public Expression parseTerm() throws IllegalArgumentException, ScanErrorException
	{
		Expression lhs = parseFactor();

		while (currentToken.equals("*") || currentToken.equals("/"))
		{
			if (currentToken.equals("*"))
			{
				eat("*");
				Number rhs = (Number)parseFactor();		
				lhs = new Number(((Number)lhs).getValue()*rhs.getValue());
			}
			else
			{
				eat("/");
				Number rhs = (Number)parseFactor();
				lhs = new Number(((Number)lhs).getValue()/rhs.getValue());
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
	public Expression parseExpression() throws IllegalArgumentException, ScanErrorException
	{
		Expression lhs = parseTerm();

		if (currentToken.equals("mod"))
		{
			eat("mod");
			Number rhs = parseNumber();
			lhs = new Number(((Number)lhs).getValue() % rhs.getValue());
		}
		else
		{
			while (currentToken.equals("-") || currentToken.equals("+"))
			{
				if (currentToken.equals("+"))
				{
					eat("+");
					Number rhs = (Number)parseExpression();
					lhs= new Number(((Number)lhs).getValue() + rhs.getValue());
				}
				else;
				{
					eat("-");
					Number rhs = (Number)parseExpression();
					new Number(((Number)lhs).getValue() - rhs.getValue());
				}
			}
		}
		return lhs;
	}

}
