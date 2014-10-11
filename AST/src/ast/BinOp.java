package ast;

import environment.Environment;

/**
 * the BinOp class consists of an expression, an operator, and
 * another expression. it is able to evaluate the arithmetic
 * expression represented by exp1 op exp2 and return the 
 * equivalent value.
 * 
 * @author Sadhika Malladi
 * @date   101014
 */
public class BinOp extends Expression
{
	private String op;
	private Expression exp1;
	private Expression exp2;
	
	/**
	 * basic constructor that initializes instance variables
	 * 
	 * @param exp1   first expression
	 * @param op	 operator
	 * @param exp2	 second expression
	 */
	public BinOp(Expression exp1, String op, Expression exp2)
	{
		this.op=op;
		this.exp1=exp1;
		this.exp2=exp2;
	}
	
	/**
	 * evaluates one of the following arithmetic expressions and returns
	 * the equivalent value:
	 * 		exp1 - exp2
	 * 		exp1 + exp2
	 * 		exp1 / exp2
	 * 		exp1 * exp2
	 * 		var = exp2 (for which the method returns 0)
	 */
	public int eval(Environment env)
	{
		if (op=="-")
			return exp1.eval(env) - exp2.eval(env);
		if (op=="+")
			return exp1.eval(env) + exp2.eval(env);
		if (op=="/")
			return exp1.eval(env) / exp2.eval(env);
		if (op=="*")
			return exp1.eval(env) * exp2.eval(env);
		else //variable assignment
		{
			env.setVariable(((Variable)exp1).getName(),exp2.eval(env));
			return 0;
		}
	}
}
