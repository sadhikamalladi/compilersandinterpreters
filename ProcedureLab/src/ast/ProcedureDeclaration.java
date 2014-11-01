package ast;

import java.util.List;

import environment.Environment;

/**
 * The ProcedureDeclaration class defines a procedure
 * when it is being declared. When executed, the ProcedureDeclaration object
 * declares the procedure.
 * 
 * This ProcedureDeclaration object knows the id of the procedure, the
 * statement associated with the procedure, and the names of the arguments
 * passed to the procedure. 
 */
public class ProcedureDeclaration extends Statement
{
	private String id;
	private Statement stmt;
	private List<String> argNames;
	
	/**
	 * This basic constructor initializes the instance variables to the values
	 * passed as parameters.
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
	
	public Statement getStatement()
	{
		return stmt;
	}
	
	/**
	 * The execution of a ProcedureDeclaration involves mapping the procedure id
	 * to this object in the passed environment.
	 */
	public void exec(Environment env)
	{
		env.setProcedure(id,this);
	}
}
