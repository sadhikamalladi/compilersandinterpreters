package environment;

import java.util.Hashtable;
import java.util.List;

import ast.ProcedureDeclaration;
import ast.Statement;

import scanner.ScanErrorException;

/**
 * an environment object describes a hashtable mapping strings (variables)
 * to integers (assigned values).
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
	 * initializes instance variables
	 */
	public Environment()
	{
		table = new Hashtable<String,Integer>();
		procedures = new Hashtable<String,ProcedureDeclaration>();
		parent = null;
	}
	
	public Environment(Environment parent)
	{
		this.parent = parent;
		table = new Hashtable<String,Integer>();
		procedures = new Hashtable<String,ProcedureDeclaration>();
	}
	
	
	/**
	 * sets (in the hash table) the variable to the value given.
	 * @param variable
	 * @param value
	 */
	public void setVariable(String variable, int value)
	{
		table.put(variable,value);
	}
	
	/**
	 * sets (in the hash table procedures) the procedure id 
	 * to the statement given.
	 * @param variable
	 * @param value
	 */
	public void setProcedure(String variable, ProcedureDeclaration decl)
	{
		if (parent != null)
			parent.setProcedure(variable, decl);
		else
			setProcedure(variable, decl);
	}
	
	/**
	 * returns the value mapped to the given variable
	 * @param variable
	 * @return
	 * @throws ScanErrorException 
	 */
	public int getVariable(String variable)
	{
		if (table.contains(variable))
			return table.get(variable);
		else
			return parent.getVariable(variable);
	}
	
	/**
	 * returns the value mapped to the given variable
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
