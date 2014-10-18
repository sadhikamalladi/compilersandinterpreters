package ast;

import java.util.ArrayList;
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
	public Block()
	{
		stmts=new ArrayList<Statement>();
	}
	
	/**
	 * add a statement to the list of statements inside of this block
	 * @param stmt
	 */
	public void add(Statement stmt)
	{
		stmts.add(stmt);
	}
	
	public int getSize()
	{
		return stmts.size();
	}
	
	/**
	 * executes the statements inside of this block
	 */
	public void exec(Environment env)
	{
		for(Statement stmt : stmts)
		{
			stmt.exec(env);
		}
	}
}
