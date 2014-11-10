package ast;

import environment.Environment;

/**
 * an Assignment object consists of a variable and the expression that
 * the variable needs to be assigned to. this object can execute itself
 * by inputting the variable and corresponding value in the environment.
 * 
 * @author Sadhika Malladi
 * @date   101014
 *
 */
public class Assignment extends Statement 
{
	private String var;
	private Expression exp;
	
	/**
	 * basic constructor
	 * 
	 * @param variable   the variable to be assigned
	 * @param expression the expression to be assigned to the variable
	 */
	public Assignment(String variable, Expression expression)
	{
		var=variable;
		exp=expression;
	}
	
	/**
	 * executes the assignment by adding the variable and its corresponding
	 * value to the environment passed
	 * 
	 * @param env the environment to add the variable to
	 */
	public void exec(Environment env)
	{
		env.setVariable(var, exp.eval(env));
	}
}
