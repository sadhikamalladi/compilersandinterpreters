package ast;

import emitter.Emitter;
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
	
	public void compile(Emitter e)
	{
		exp.compile(e);
		e.emit("move $a0 $v0");
		e.emit("li $v0 1");
		e.emit("syscall");
		e.emit("la $a0, msg");
		e.emit("li $v0 4");
		e.emit("syscall");
	}
}
