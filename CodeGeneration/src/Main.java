import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import ast.Expression;
import ast.Program;
import ast.Statement;
import ast.Block;

import scanner.ScanErrorException;
import scanner.Scanner;

import parser.Parser;

import environment.Environment;

/**
 * The purpose of this class Main is to test the ability of the Parser, Scanner, and Environment
 * to work together. A testerFile is included in the package with this project.
 * 
 * @author Sadhika Malladi
 * @date   103114
 *
 */
public class Main 
{
	public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException, ScanErrorException, UnsupportedEncodingException 
	{
		FileInputStream test0= new FileInputStream(new File("/home/sadhika/compilersandinterpreters/CodeGeneration/src/testerFile.txt"));
		Environment env = new Environment(null);
		Scanner sascandra = new Scanner(test0);
		Parser parsela = new Parser(sascandra);
		Program prog = parsela.parseScript();
		String filePath = "/home/sadhika/Documents/outputSPIM.txt";
		prog.compile(filePath);
		
		
	}
}
