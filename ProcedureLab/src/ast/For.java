package ast;

import environment.Environment;

/**
 * a for loop object contains the initial assignment, the end value, and
 * the statements to execute.
 * 
 * this for loop object can execute itself.
 * 
 * @author sadhika
 * @date   101814
 */
public class For extends Statement 
{
	private String id;
	private Number expr;
	private Number endCond;
	private Statement stmt;
	
	/**
	 * initializes instance variables to parameters
	 * @param id
	 * @param expr
	 * @param endCondition
	 * @param statement
	 */
	public For(String id, Number expr, Number endCondition, Statement statement)
	{
		this.id = id;
		this.expr = expr;
		endCond = endCondition;
		stmt = statement;
	}
	
	/**
	 * evaluates this for loop
	 */
	public void exec(Environment env)
	{
		Assignment assign = new Assignment(id,(Expression)expr);
		assign.exec(env);
		int start = expr.getValue();
		int end = endCond.getValue();
		for (int i = start; i<end; i++)
			stmt.exec(env);
	}
}
