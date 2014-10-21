package environment;

import java.util.Hashtable;

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
	
	/**
	 * initializes table instance variable to new hash tabl
	 */
	public Environment()
	{
		table = new Hashtable<String,Integer>();
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
	 * returns the value mapped to the given variable
	 * @param variable
	 * @return
	 * @throws ScanErrorException 
	 */
	public int getVariable(String variable)
	{
		return table.get(variable);
	}
}
