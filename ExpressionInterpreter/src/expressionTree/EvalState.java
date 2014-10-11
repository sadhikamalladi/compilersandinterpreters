package expressionTree;
import java.util.*;

import parser.ParseErrorException;
/**
 * EvalState will encapsulate a symbol table in the form of a hash table. Keys will be stored in the form of
 * strings, while values will be stored in the form of an integer.
 * 
 * @author sadhika
 *
 */
public class EvalState 
{
	private Hashtable<String,Integer> table;
	
	/*
	 * constructor initializes the hash table
	 */
	public EvalState()
	{
		table = new Hashtable<String,Integer>();
	}
	
	/**
	 * checks to see if the given key is found in the table
	 * 
	 * runtime: O(1)
	 * 
	 * @param var a string describing the key to be searched for
	 * @return true if var is a stored key; otherwise, false.
	 */
	public boolean isDefined(String var)
	{
		return table.containsKey(var);
	}
	
	/**
	 * returns the value defined by a particular key var. throws an error if
	 * key var does not exist in hash table.
	 * 
	 * runtime: O(1)

	 * 
	 * @param var the key corresponding to the value that should be returned
	 * @return the value at key var
	 */
	public int getValue(String var)
	{
		return table.get(var);
	}
	
	/**
	 * sets the corresponding value of key var to parameter value
	 * 
	 * runtime: O(1)
	 */
	public void setValue(String var, int value)
	{
		table.put(var, value);
	}

}
