package ast;

import environment.Environment;

/** 
 * the class If defines an if statement that has a condition and statement
 * to be executed if the condition is true. This If object can evaluate itself by
 * evaluating the condition, checking if it is true, and then executing the
 * statements if so.
 * 
 * @author Sadhika Malladi
 * @date   101614
 */
public class If extends Statement
{
	private Condition cond;
	private Statement stmt;
	
	/**
	 * constructor that initializes instance variables to parameters
	 * @param condition
	 * @param stmts
	 */
	public If(Condition condition,Statement stmts)
	{
		cond = condition;
		stmt = stmts;
	}
	
	/**
	 * executes this If statement by checking if the condition is true and
	 * evaluating the statement if necessary.
	 */
	public void exec(Environment env)
	{
		int conditionValue = cond.eval(env);
		if (conditionValue == 1)
			stmt.exec(env);
	}
}
