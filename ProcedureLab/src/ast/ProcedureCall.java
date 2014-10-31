package ast;

import java.util.List;

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
	private List<Expression> args;
	/**
	 * basic constructor that initializes instance variables
	 * @param id
	 * @param stmt
	 */
	public ProcedureCall(String id,List<Expression> arguments)
	{
		args = arguments;
		this.id = id;
	}
	
	/**
	 * creates a procedure that maps to a particular statement
	 * in the environment passed. returns 0 for now.
	 */
	public int eval(Environment env)
	{
		Environment newEnvironment = new Environment(env);
		env.declareVariable(id, 0);
		ProcedureDeclaration declaration = env.getProcedure(id); 
		List<String> argVars = declaration.getArgNames();
		for (int i=0; i<argVars.size();i++)
		{
			Expression arg = args.get(i);
			newEnvironment.declareVariable(argVars.get(i),arg.eval(newEnvironment));
		}
		Statement stmt = declaration.getStatement();
		stmt.exec(newEnvironment);
		return env.getVariable(id);
	}
}
