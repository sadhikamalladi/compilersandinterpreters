package ast;

import environment.Environment;

/**
 * the Number class defines an integer. an evaluation of a 
 * Number will simply return the integer value.
 * 
 * @author Sadhika Malladi
 * @date   101014
 *
 */
public class Number extends Expression
{
	private int value;
	
	/**
	 * initializes instance variable value to val
	 * 
	 * @param val
	 */
	public Number(int val)
	{
		value=val;
	}
	
	/**
	 * returns the value of this number
	 */
	public int getValue()
	{
		return value;
	}
	
	/**
	 * evaluates this number (i.e. eturns the value
	 * of this number).
	 */
	public int eval(Environment env)
	{
		return value;
	}
}
