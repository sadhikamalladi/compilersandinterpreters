package ast;

import environment.Environment;

/**
 * a ProcedureCall object is able to keep track of the 
 * id and statement of this ProcedureCall
 * 
 * @author Sadhika
 * @date   102114
 *	
 */
public class ProcedureCall extends Expression
{
	private String id;	
	/**
	 * basic constructor that initializes instance variables
	 * @param id
	 * @param stmt
	 */
	public ProcedureCall(String id)
	{
		this.id = id;
	}
	
	/**
	 * creates a procedure that maps to a particular statement
	 * in the environment passed. returns 0 for now.
	 */
	public int eval(Environment env)
	{
		Statement stmt = env.getProcedure(id);
		stmt.exec(env);
		return 0;
	}
}
