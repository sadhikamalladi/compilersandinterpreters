package ast;

import scanner.ScanErrorException;
import environment.Environment;

/**
 * the Variable class defines a variable (i.e. string)
 * 
 * @author Sadhika Malladi
 * @date   101014
 */
public class Variable extends Expression
{
	private String name;
	
	/**
	 * basic constructor that initializes instance
	 * variable name
	 * @param name
	 */
	public Variable(String name)
	{
		this.name=name;
	}
	
	/**
	 * @return the value for the instance variable name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * evaluates this Variable by returning the value it 
	 * stands for (found through env)
	 */
	public int eval(Environment env)
	{
		return env.getVariable(name);
	}
}
