package ast;

import environment.Environment;

/**
 * this Condition object contains an expression, a relative operator, and another
 * expression. Essentially, this object defines a boolean expression. This object
 * can evaluate itself. 
 * 
 * grammar:
 * 	cond -> expr relop expr
 * 	relop -> = | <> | < | > | <= | >=
 * 
 * @author Sadhika Malladi
 * @date   101314
 */
public class Condition extends Expression 
{
	private Expression expr1;
	private String relop;
	private Expression expr2;
	
	/**
	 * constructor that initializes instance variables expr1, relop, and expr2 to
	 * the given parameters
	 */
	public Condition(Expression expression1,String relativeoperator,Expression expression2)
	{
		expr1=expression1;
		expr2=expression2;
		relop=relativeoperator;
	}
	
	/**
	 * evaluates this condition object. returns 1 if the boolean expression is
	 * true, 0 otherwise. the grammar for relop is in the class comments.
	 */
	public int eval(Environment env)
	{
		int value1=expr1.eval(env);
		int value2=expr2.eval(env);
		boolean stmtVal=false;
		
		if (relop.equals("="))
			stmtVal=(value1==value2);
		else if (relop.equals("<>"))
			stmtVal=(value1!=value2);
		else if (relop.equals("<"))
			stmtVal=(value1<value2);
		else if (relop.equals(">"))
			stmtVal=(value1>value2);
		else if (relop.equals("<="))
			stmtVal=(value1<=value2);
		else
			stmtVal=(value1>=value2);
		
		int retValue=0;
		if (stmtVal==true)
			retValue=1;
		return retValue;
	}
}
