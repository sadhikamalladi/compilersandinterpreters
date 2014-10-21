import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ast.Expression;
import ast.Statement;
import ast.Block;

import scanner.ScanErrorException;
import scanner.Scanner;

import parser.Parser;

import environment.Environment;


public class Main 
{

	public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException, ScanErrorException 
	{
		FileInputStream test0= new FileInputStream(new File("/home/sadhika/Documents/testFile.txt"));
		Environment env = new Environment();
		Scanner sascandra = new Scanner(test0);
		Parser parsela = new Parser(sascandra);
		parsela.parseScript();
	}
}
