package environment;

import java.util.Hashtable;

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
	private Hashtable<String,Statement> procedures;
	
	/**
	 * initializes table instance variable to new hash tabl
	 */
	public Environment()
	{
		table = new Hashtable<String,Integer>();
		procedures = new Hashtable<String,Statement>();
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
	public void setProcedure(String variable, Statement stmt)
	{
		procedures.put(variable,stmt);
	}
	
	/**
	 * returns the value mapped to the given variable
	 * @param variable
	 * @return
	 * @throws ScanErrorException 
	 */
	public int getVariable(String variable)
	{
		return table.get(variable);
	}
	
	/**
	 * returns the value mapped to the given variable
	 * @param variable
	 * @return
	 * @throws ScanErrorException 
	 */
	public Statement getProcedure(String variable)
	{
		return procedures.get(variable);
	}

}
