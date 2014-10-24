package ast;

import java.util.ArrayList;

import environment.Environment;

/**
 * keeps track of the list of procedures and the
 * statement after
 * 
 * program -> PROCEDURE id ( ) ; stmt program | stmt .
 * 
 * @author sadhika
 *
 */
public class Program extends Statement
{
	private ArrayList<ProcedureDeclaration> procedures;
	private Statement stmt;
	
	public Program()
	{
		procedures = new ArrayList<ProcedureDeclaration>();
	}
	
	public void addStatement(Statement statement)
	{
		stmt = statement;
	}
	
	public void addProcedure(ProcedureDeclaration procedure)
	{
		procedures.add(procedure);
	}
	
	public void exec(Environment env)
	{
		for(ProcedureDeclaration proc: procedures)
			proc.exec(env);
		stmt.exec(env);
	}
}
