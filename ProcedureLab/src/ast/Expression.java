package ast;

import environment.Environment;

/**
 * the abstract Expression class defines an expression
 * that is able to evaluate itself.
 * 
 * @author sadhika
 * @date   101014
 */
public abstract class Expression 
{
	public abstract int eval(Environment env);
}
