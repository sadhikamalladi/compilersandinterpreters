package ast;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import emitter.Emitter;
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
	
	/**
	 * Executing this Program object involves executing all
	 * of the procedures and then executing the statement, all
	 * within the global environment.
	 */
	public void exec(Environment env)
	{
		for(ProcedureDeclaration proc: procedures)
		{
			proc.exec(env);
		}
		stmt.exec(env);
	}
	
	public void compile(String filePath) throws FileNotFoundException, UnsupportedEncodingException
	{
		Emitter e = new Emitter(filePath);
		e.emit(".text");
		e.emit(".globl main");
		e.emit("main: #QTSPIM will automatically look for main");
		stmt.compile(e);
		e.emit("li $v0 10");
		e.emit("syscall # halt");
		e.emit(".data");
		e.emit("msg:");
		e.emit(".asciiz \"\\n\"");
		e.close();
	}
}
