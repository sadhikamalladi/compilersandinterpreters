package expressionTree;

import parser.ParseErrorException;

/**
 * the compound node stores the entire expression or equatoin and parses through it. at the 
 * end of a successful parse, only the operand will remain in this compound node.
 * 
 * the evaluation of this compound node is done on-the-fly, meaning we parse left to right.
 * when we look at the first character, it can be classified as one of these three:
 * 	1. constant: in this case, we create a constant node with the given value
 * 	2. identifier: in this case, we create an identifier node with the given value. if the
 * 		identifier does not exist in our symbol table, then we add it and set its value 
 * 		to be the right hand side of the given expression
 * 	3. operand: in this case, we store the operand in the compound node and make sure we
 * 		keep track of what side of the equation we are on. 
 * if we are setting a symbol equal to an expression, then the evaluation will return the
 * expression. otherwise, we return what the expression evaluates to.
 * 
 * @author Sadhika Malladi
 * @date   091614
 */
public class CompoundNode implements ExpNode 
{
	private ExpNode lhs;
	private ExpNode rhs;
	private char op;

	/**
	 * constructor stores given expression in instance variable
	 */
	public CompoundNode(ExpNode lhs, char op, ExpNode rhs)
	{
		this.lhs = lhs;
		this.op = op;
		this.rhs = rhs;
	}

	/**
	 * the evaluation of this compound node is done on-the-fly, meaning we parse left to right.
	 * when we look at the first character, it can be classified as one of these three:
	 * 	1. constant: in this case, we create a constant node with the given value
	 * 	2. identifier: in this case, we create an identifier node with the given value. if the
	 * 		identifier does not exist in our symbol table, then we add it and set its value 
	 * 		to be the right hand side of the given expression
	 * 	3. operand: in this case, we store the operand in the compound node and make sure we
	 * 		keep track of what side of the equation we are on. 
	 * if we are setting a symbol equal to an expression, then the evaluation will return the
	 * expression. otherwise, we return what the expression evaluates to.
	 * 
	 * @param state the symbol table
	 * @return the value of the expression
	 */
	@Override
	public int eval(EvalState state)
	{
		switch(op)
		{
		case '=':
		{
			if (lhs.getType() == expTypeT.identifierType)
			{
				int value = rhs.eval(state);
				state.setValue(((IdentifierNode)lhs).getValue(), value);
				return value;
			}
		}
		case '+':return lhs.eval(state) + rhs.eval(state);
		case '-':return lhs.eval(state) - rhs.eval(state);
		case '*':return lhs.eval(state)*rhs.eval(state);
		case '/':return lhs.eval(state)/rhs.eval(state);
		default: return lhs.eval(state);
		}
	}

	@Override
	public expTypeT getType() 
	{
		return expTypeT.compoundType;
	}

}
