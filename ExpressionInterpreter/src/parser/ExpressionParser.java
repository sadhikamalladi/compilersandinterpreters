package parser;

import expressionTree.CompoundNode;
import expressionTree.ConstantNode;
import expressionTree.ExpNode;
import expressionTree.IdentifierNode;
import scanner.*;

/**
 * This class parses a given expression.
 * 
 * Grammar:
 * 	Expr -> Term op Expr | Term
 * 	Term -> identifier | number | (Expr)
 * 
 * @author Sadhika Malladi
 * @date  091914
 *
 */
public class ExpressionParser 
{
	private Scanner scan;
	private String currentToken;

	/**
	 * default constructor
	 */
	public ExpressionParser()
	{
	}
	
	/**
	 * parses the input expression and returns a compound node that can be post-order
	 * walked to evaluate the expression.
	 * 
	 * @param input expression to be parsed
	 * @return the expression node containing the expressoin
	 * @throws ScanErrorException if scanner breaks
	 * @throws ParseErrorException if parser breaks
	 */
	public ExpNode parse(String input) throws ScanErrorException, ParseErrorException
	{
		scan=new Scanner(input);
		if (scan.hasNext())
			currentToken=scan.nextToken();
		return parseExp();
	}

	/**
	 * eats the token and advances if the given token matches what is known to be next
	 * 
	 * @param expected				given token
	 * @throws ScanErrorException	if the next token is irretrievable
	 */
	private void eat(String expected) throws ScanErrorException
	{
		if (currentToken.compareTo(expected)==0) 
			currentToken=scan.nextToken();
		else
			throw new ScanErrorException("Not looking ahead properly.");
	}

	/**
	 * parses the expression. recognizes the end as the end of a paranthesis or the "END"
	 * keyword returned by the scanner. creates a compound node with the abstract syntax
	 * tree attached
	 * 
	 * @return abstract syntax tree in the form of a compound node
	 * @throws ParseErrorException if there is a left hand side followed by a character that
	 * 							  isn't an operand, then an error is thrown
	 * @throws ScanErrorException  if eating doesn't work
	 */
	private ExpNode parseExp() throws ScanErrorException,ParseErrorException
	{
		ExpNode lhs = parseTerm();
		if (currentToken.compareTo("END")==0 || currentToken.compareTo(")")==0)
		{
			return lhs;
		}
		char op = currentToken.charAt(0);
		eat(currentToken);
		switch(op)
		{
		case '=':
		case '+':
		case '/':
		case '*':
		case '-':
			return new CompoundNode(lhs,op,parseExp());
		default: 
			throw(new ParseErrorException("There should be an operator following the expression"));
		}
	}

	/**
	 * Term -> identifier | constant | (exp)
	 * 
	 * @precondition  the token considered a term is stored in currentToken
	 * @postcondition currentToken is the token after the term
	 * 
	 * @return exp node with term in its value
	 * @throws ScanErrorException if the currentToken isn't defined by the above grammar.
	 * @throws ParseErrorException if the expression is not valid
	 */
	private ExpNode parseTerm() throws ScanErrorException, ParseErrorException
	{
		char first = currentToken.charAt(0);
		ExpNode returnValue;
		
		if (Scanner.isLetter(first))
		{
			returnValue= new IdentifierNode(currentToken);
			eat(currentToken);
		}
		else if (Scanner.isDigit(first))
		{
			returnValue= new ConstantNode(Integer.parseInt(currentToken));
			eat(currentToken);
		}
		else
		{
			eat("(");
			returnValue= parseExp();
			eat(")");
		}
		return returnValue;
	}


}
