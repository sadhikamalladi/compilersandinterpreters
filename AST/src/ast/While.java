package ast;

import environment.Environment;

/**
 * objects of the while loop class execute statement object (instance variable stmt)
 * while condition object (instance variable cond) is true. 
 * 
 * @author sadhika
 * @date   101714
 */
public class While extends Statement
{
	private Condition cond;
	private Statement stmt;
	
	/**
	 * initialize instance variables
	 * 
	 * @param condition
	 * @param statement
	 */
	public While(Condition condition, Statement statement)
	{
		cond = condition;
		stmt = statement;
	}
	
	/**
	 * executes this while loop by executing the statement (instance var) while condition
	 * (instance var) is true.
	 * 
	 * @param env
	 */
	public void exec(Environment env)
	{
		int conditionTruth = cond.eval(env);
		while (conditionTruth == 1)
		{
			stmt.exec(env);
			conditionTruth = cond.eval(env);
		}
	}

}
