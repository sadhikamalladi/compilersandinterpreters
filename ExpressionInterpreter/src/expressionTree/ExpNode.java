package expressionTree;

/**
 * This interface is used to encapsulate the three different types of nodes encountered
 * in arithmetic expressions:
 * 	1. constant node - has a numeric value
 * 	2. identifier node - has a string value
 *  3. compound node - expNode + operation + expNode
 * 
 * classes that implement this interface must overwrite the eval method to return
 * an integer object. For the compound node, this will require a post-order walk.
 * 	
 * @author Sadhika Malladi
 * @date 091514
 *
 */
public interface ExpNode {
	
	enum expTypeT{constantType,identifierType,compoundType};
	
	/**
	 * evaluates the node and returns an integer value. for compound node, this method
	 * requires a post-order walk rooted at the node.
	 * 
	 * @param state hash table with keys and values for symbols
	 * @return		integer representation of the node
	 */
	int eval(EvalState state);
	
	/**
	 * @return the type of node (constant, identifier, compound) in enumerated form
	 */
	expTypeT getType();
	
	/**
	 * string representation of object -- useful for debugging
	 * @return
	 */
	String toString();
}
