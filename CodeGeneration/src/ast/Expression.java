package ast;

import emitter.Emitter;
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
	
	public void compile(Emitter e)
	{
		throw new RuntimeException("Implement me!!!!!");
	}
}
