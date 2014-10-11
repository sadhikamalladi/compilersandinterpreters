package expressionTree;

/**
 * the identifier node stores a string value that stands for an integer value (which
 * can be found by looking up the hash table, as done in the eval method). this class
 * implements expnode.
 * 
 * @author Sadhika Malladi
 * @date 091614
 *
 */
public class IdentifierNode implements ExpNode 
{
	private String value;
	
	/**
	 * default constructor to store string value in this node
	 */
	public IdentifierNode(String val)
	{
		value=val;
	}
	
	/**
	 * return the integer value corresponding to the key (string) stored at this node.
	 * uses the provided EvalState hash table for lookup.
	 *
	 * 
	 * @return integer representation of instance variable string value.
	 * @throws ArithmeticErrorException 
	 */
	@Override
	public int eval(EvalState state)
	{
			return state.getValue(value);
	}
	
	/**
	 * @return the enumerated type identifier node
	 */
	@Override
	public expTypeT getType() 
	{
		return expTypeT.identifierType;
	}
	
	public String getValue()
	{
		return value;
	}
	
	
	/**
	 * create and return a useful string representation of this node
	 */
	public String toString()
	{
		return "";
	}

}
