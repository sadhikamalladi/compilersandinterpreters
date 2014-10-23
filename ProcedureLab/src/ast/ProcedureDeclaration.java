package ast;

import environment.Environment;

/**
 * the ProcedureDeclaration class defines a procedure
 * when it is being declared. the procedure can declare itself. 
 */
public class ProcedureDeclaration extends Statement
{
	private String id;
	private Statement stmt;
	
	/**
	 * basic constructor takes in parameters for instance vars
	 * id and stmt.
	 */
	public ProcedureDeclaration(String id, Statement stmt)
	{
		this.id = id;
		this.stmt = stmt;
	}
	
	/**
	 * executes the procedure declaration by mapping a procedure
	 * into the statement
	 */
	public void exec(Environment env)
	{
		env.setProcedure(id,stmt);
	}
}
