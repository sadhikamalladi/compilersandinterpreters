package ast;

import java.util.List;
import environment.Environment;

/**
 * the Block class defines a block (denoted by BEGIN and END tokens)
 * that contains multiple Statement objects. this class has the
 * ability to execute itself (i.e. execute all of the statements
 * in this Block).
 * 
 * @author Sadhika Malladi
 * @date   101014
 *
 */
public class Block extends Statement
{
	private List<Statement> stmts;
	
	/**
	 * initializes instance variable stmts
	 * @param statements the list of statements to initialize
	 * 					 stmts to
	 */
	public Block(List<Statement> statements)
	{
		stmts=statements;
	}
	
	/**
	 * executes the statements inside of this block
	 */
	public void exec(Environment env)
	{
		for(int i=1; i<stmts.size(); i++)
		{
			Statement stmt = stmts.get(i);
			stmt.exec(env);
		}
	}
}
