package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The abstract class Statement defines a statement that is able
 * to execute itself.
 * 
 * @author sadhika
 * @date   101014
 */
public abstract class Statement 
{
	public abstract void exec(Environment env); 
	
	public void compile(Emitter e)
	{
		throw new RuntimeException("Implement me!!!!!");
	}
}
