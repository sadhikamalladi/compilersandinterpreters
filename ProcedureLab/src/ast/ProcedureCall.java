package ast;

import java.util.List;

import environment.Environment;

/**
 * A ProcedureCall is an Expression object. This ProcedureCall
 * can keep track of its id (a String) and a list of the 
 * names of the arguments passed to the call. This ProcedureCall
 * object can also execute itself.
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
	 * Below is a basic constructor that initializes instance variables
	 * @param id
	 * @param stmt
	 */
	public ProcedureCall(String id,List<Expression> arguments)
	{
		args = arguments;
		this.id = id;
	}
	
	/**
	 * This method is used to evaluate the ProcedureCall.
	 * 
	 * First, a new environment must be created as a child of the passed
	 * environment. This environment will be used to keep track of all 
	 * the local variables, and it is discarded after the procedure is
	 * executed.
	 * 
	 * In order to assign a return value for this procedure, a new variable
	 * with the same name as this procedure (stored in instance variable id)
	 * is created in the global environment. This is done to ensure that the
	 * return value can be accessed from outside the scope of this procedure call.
	 * 
	 * Then, the ProcedureDeclaration is retrieved in order to extract
	 * the names of the arguments; keep in mind that a ProcedureCall object
	 * only stores the values of the arguments. Each of the arguments are
	 * declared as variables in the local environment and assigned the values
	 * stored in this ProcedureCall.
	 * 
	 * Finally, the statement associated with the ProcedureDeclaration (and
	 * thus with the procedure) is executed in the local environment and the
	 * value of the procedure variable is returned. 
	 * 
	 * Keep in mind that the procedure variable will be set sometime during
	 * executing the statement, or will remain 0 and unused.
	 * 
	 * @param env  the parent environment for the procedure being executed.
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
