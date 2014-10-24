package ast;

import java.util.List;

import environment.Environment;

/**
 * the ProcedureDeclaration class defines a procedure
 * when it is being declared. the procedure can declare itself. 
 */
public class ProcedureDeclaration extends Statement
{
	private String id;
	private Statement stmt;
	private List<String> argNames;
	
	/**
	 * basic constructor takes in parameters for instance vars
	 * id and stmt.
	 */
	public ProcedureDeclaration(String id, List<String> argNames, Statement stmt)
	{
		this.argNames = argNames;		
		this.id = id;
		this.stmt = stmt;
	}
	
	public List<String> getArgNames()
	{
		return argNames;
	}
	
	/**
	 * executes the procedure declaration by mapping a procedure
	 * into the statement it contains
	 */
	public void exec(Environment env)
	{
		env.setProcedure(id,stmt);
	}
}
