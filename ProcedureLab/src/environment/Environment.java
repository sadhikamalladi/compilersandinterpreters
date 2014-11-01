package environment;

import java.util.Hashtable;
import java.util.List;

import ast.ProcedureDeclaration;
import ast.Statement;

import scanner.ScanErrorException;

/**
 * An Environment object can have a parent environment; only the 
 * global Environment has a null parent. In addition, an Environment
 * stores mappings of variable names (Strings) to variable values
 * (Integers). The map is in the form of a Hashtable. There is also
 * a mapping of procedure names (Strings) to ProcedureDeclaration objects.
 * 
 * @author sadhika
 * @date 100414
 */
public class Environment 
{
	private Hashtable<String,Integer> table;
	private Hashtable<String,ProcedureDeclaration> procedures;
	private Environment parent;
	
	/**
	 * This basic constructor initializes instance variables.
	 */
	public Environment()
	{
		table = new Hashtable<String,Integer>();
		procedures = new Hashtable<String,ProcedureDeclaration>();
		parent = null;
	}
	
	/**
	 * If a parent is passed to the Environment, then it sets
	 * the instance variable accordingly.
	 * @param parent
	 */
	public Environment(Environment parent)
	{
		this.parent = parent;
		table = new Hashtable<String,Integer>();
		procedures = new Hashtable<String,ProcedureDeclaration>();
	}
	
	
	/**
	 * If there is a parent and the variable passed
	 * is not contained in this Environment, then the variable
	 * value is set in the parent environment. Otherwise,
	 * the variable is declared in this Environment.
	 */
	public void setVariable(String variable, int value)
	{
		if (parent!=null && !table.contains(variable))
			parent.setVariable(variable, value);
		else
			declareVariable(variable,value);
	}
	
	/**
	 * The method follows the trial up to the global Environment
	 * (identified by a null parent Environment) and adds the procedure
	 * to the procedures instance variable Hashtable.
	 * 
	 * @param variable id of the procedure
	 * @param value	   declaration associated with the procedure
	 */
	public void setProcedure(String variable, ProcedureDeclaration decl)
	{
		if (parent != null)
		{
			parent.setProcedure(variable, decl);
		}
		else
		{
			procedures.put(variable,decl);
		}
	}
	
	/**
	 * If the variable is in this Environment, then its value 
	 * is returned. Otherwise, this method is called on the
	 * parent environment.
	 * 
	 * @param variable
	 * @return
	 */
	public int getVariable(String variable)
	{
		if (table.keySet().contains(variable))
			return table.get(variable);
		else
			return parent.getVariable(variable);
	}
	
	/**
	 * This method adds a variable to the Hashtable instance
	 * variable table in this Environment.
	 * @param variable
	 * @param value
	 */
	public void declareVariable(String variable, int value)
	{
		table.put(variable,value);
	}
	
	/**
	 * This method returns the procedure listed in the global
	 * environment.
	 * 
	 * @param variable
	 * @return
	 * @throws ScanErrorException 
	 */
	public ProcedureDeclaration getProcedure(String variable)
	{
		if (parent != null)
			return parent.getProcedure(variable);
		return procedures.get(variable);
	}

}
