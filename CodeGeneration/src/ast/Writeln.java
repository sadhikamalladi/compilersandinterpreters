package ast;

import environment.Environment;

/**
 * the Writeln class defines a statement that writes
 * the value of the expression in the console.
 * 
 * @author Sadhika Malladi
 * @date   101014
 *
 */
public class Writeln extends Statement 
{
	private Expression exp;
	
	/**
	 * initializes instance variable exp
	 * 
	 * @param expression
	 */
	public Writeln(Expression expression)
	{
		exp=expression;
	}
	
	/**
	 * executes this Writeln statement by printing
	 * out the value of the expression
	 */
	public void exec(Environment env)
	{
		System.out.println(exp.eval(env));
	}
}
