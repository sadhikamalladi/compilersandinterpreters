package expressionTree;

/**
 * this class encapsulates an expnode that holds a constant (i.e. integer). a constant node implements
 * the interface expnode and overrides methods getType (to make it return constantType) and eval 
 * (to make it return the stored value of this node.
 * 
 * @author Sadhika Malladi
 * @date 091614
 *
 */
public class ConstantNode implements ExpNode 
{
	private int value;
	
	
	/**
	 * constructor for constant node.
	 * @param value the value to store in this node
	 */
	public ConstantNode(int value)
	{
		this.value=value;
	}
	
	/**
	 * evaluates the node and returns the value inside of it
	 * 
	 * @param state  the hash table to look up symbols
	 */
	@Override
	public int eval(EvalState state) 
	{
		return value;
	}
	
	/**
	 * @return expTypeT of form constant node
	 */
	@Override
	public expTypeT getType() 
	{
		return expTypeT.constantType;
	}
	
	/**
	 * @return a string representation of this node
	 */
	public String toString()
	{
		return "This constant node has a value of "+value;
	}


}
