package parser;

import java.util.List;
import java.util.ArrayList;

import environment.Environment;

import scanner.Scanner;
import scanner.ScanErrorException;

import ast.Assignment;
import ast.BinOp;
import ast.Block;
import ast.Condition;
import ast.Expression;
import ast.If;
import ast.Number;
import ast.Statement;
import ast.Variable;
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
	
	public void parseScript() throws IllegalArgumentException, ScanErrorException
	{
		Environment env = new Environment();
		while(!currentToken.equals("."))
		{
			Statement stmt = parseStatement();
			stmt.exec(env);
		}
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
		if (currentToken.equals("WRITELN"))
		{
			eat("WRITELN");
			eat("(");
			Expression expr = parseExpression();
			Statement returnVal = new Writeln(expr);
			eat(")");
			eat(";");
			return returnVal;
		}
		else if (currentToken.equals("BEGIN"))
		{
			return parseStatements();
		}
		else if (currentToken.equals("IF"))
		{
			eat("IF");
			Expression lhs = parseExpression();
			String relop = currentToken;
			eat(relop);
			Expression rhs = parseExpression();
			Condition cond = new Condition(lhs,relop,rhs);
			eat("THEN");
			If ifstatement = new If(cond,parseStatement());
			return ifstatement;
		}
		else
		{
			String key = currentToken;
			eat(key);
			eat(":=");
			Expression exp = parseExpression();
			eat(";");
			return new Assignment(key, exp);
		}
	}
	
	/**
	 * parses a block of statements defined by the grammar
	 * 
	 * @throws ScanErrorException 
	 * @throws IllegalArgumentException 
	 */
	private Statement parseStatements() throws IllegalArgumentException, ScanErrorException
	{
		//List<Statement> block = new ArrayList<Statement>();
		Block block = new Block();
		eat("BEGIN");
		while (! currentToken.equals("END"))
		{
			block.add(parseStatement());
		}
		eat("END");
		eat(";");
		return block;
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
		if (Scanner.isDigit(currentToken.charAt(0))) 
		{
			return parseNumber(); // base case
		}
		else if (Scanner.isLetter(currentToken.charAt(0)))
		{
			String var = currentToken;
			eat(var);
			return new Variable(var);
		}
		else if (currentToken.equals("(")) // factor -> ( term )    
		{
			eat(currentToken);
			Expression expr = parseExpression();
			eat(")");
			return expr;
		}
		else if (currentToken.equals("-")) // factor -> - factor
		{
			eat(currentToken);
			Number num = (Number)(parseFactor());
			return new Number(0-num.getValue());
		}
		else
		{
			System.out.println("error");
			return null;
		}
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
				Expression rhs = parseFactor();		
				lhs = new BinOp(lhs,"*",rhs);
			}
			else
			{
				eat("/");
				Expression rhs = parseFactor();
				lhs = new BinOp(lhs,"/",rhs);
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

		while (currentToken.equals("-") || currentToken.equals("+"))
		{
			if (currentToken.equals("+"))
			{
				eat("+");
				Expression rhs = parseExpression();
				lhs= new BinOp(lhs,"+",rhs);
			}
			else
			{
				eat("-");
				Expression rhs = parseExpression();
				lhs= new BinOp(lhs,"-",rhs);
			}
		}
		return lhs;
	}

}
