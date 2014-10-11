package environment;

import java.util.Hashtable;

public class Environment 
{
	private Hashtable<String,Integer> table;
	
	public Environment()
	{
		table = new Hashtable<String,Integer>();
	}
	
	public void setVariable(String variable, int value)
	{
		table.put(variable,value);
	}
	
	public int getVariable(String variable)
	{
		return table.get(variable);
	}
}
