package scanner;
import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab exercise 1
 * 
 * @author Sadhika Malladi
 * @date 9.02.14
 * 
 * Instance Variables:
 * 	1. eof (boolean) - if the buffered reader is at the end of the file
 * 	2. in (BufferedReader) - IO stream
 * 	3. currentChar (char) - the character just read in by the IO stream
 *  
 * Methods:
 * 	1. public void getNextChar() - sets currentChar to the next character or -1 if at the end of the file
 * 	2. private void eat(char) - if the given character matches current character, advances IO stream.
 * 	3. public boolean hasNext() - returns boolean indicating if the IO stream isn't at the end of the file.
 * 	4. public String nextToken() - returns the next token, which can be an operand, identifier, or number
 * 	5. public static boolean isDigit(char) - returns if the given character is defined in reg. expression [0-9]
 *  6. public static boolean isLetter(char) - returns if the given character is defined in reg. expression [a-zA-Z]
 *  7. public static boolean isWhitespace(char) - returns if given character is a new line, carriage return, or tab 
 *  											  escape sequence.
 *  8. private String scanNumber() - scans + returns a number defined by digit(digit)*
 *  9. private String scanIdentifier() - scans + returns an identifier defined by letter(letter)*
 *  10. private String scanOperand() - scans + returns an operand defined by comments in the method
 *  11. private void eatLineComment() - eats from the beginning to the end of a line comment
 *  12. private void eatBlockComment() - eats from start to end of block comment 
 *
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }
    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }
    /**
     * Method: getNextChar
     * Usage: private method - getNextChar()
     * -------
     * Sets instance field currentChar to value read using input stream 'read' method
     * input stream 'read' method returns -1 when at end of line, this is used to set eof 
     * instance variable. if an IO exception is thrown then the system exits with a status of 1
     * and the stack trace is printed. 
     */
    public void getNextChar()
    {
    	int next=0;
		try 
		{
			next = in.read();
		}
		catch (IOException e) 
		{
			System.exit(1);
			e.printStackTrace();
		}
    	if (next == -1)
    		eof=true;
    	else
    		currentChar=(char)(next);
    }
    /**
     * Method: eat
     * Usage: private method - eat(char expected)
     * -----
     * takes in the expected character and advances the io stream to the next character if
     * the current and expected characters are equivalent. if the characters are not equivalent
     * then a ScanErrorException is thrown with the strings included in the reason for exception.
     * 
     * @param expected 	the character to compare with currentChar, result determines IO stream advance
     * @throws ScanErrorException if expected is not equivalent to currentChar
     */
    private void eat(char expected) throws ScanErrorException
    {
        if (expected==currentChar)
        {
        	getNextChar();
        }
        else
        {
        	String reason="Illegal character - expected " + currentChar + " and found " + expected;
        	throw(new ScanErrorException(reason));
        }
    }
    /**
     * Method: hasNext
     * Usage: public method - Scanner.hasNext()
     * ----
     * determines if  input is at the end of the file using instance variable eof
     * @return true if input stream is not at the end of the file; otherwise, false.
     */
    public boolean hasNext()
    {
       return !eof;
    }
    /**
     * Method: nextToken
     * Usage:  Scanner.nextToken()
     * -----
     * looks ahead one character and decides to scan number, identifier, or operand based
     * on currentChar type. 
     * 
     * @postcondition io stream is left at the beginning of the next token or whitespace character 
     * 				  immediately after scanned token
     * 
     * @return the next token: a number, identifier, or operand.
     */
    public String nextToken() throws ScanErrorException
    {
    	if (eof)
    		return "";
    	while(isWhitespace(currentChar))
    		eat(currentChar);
    	if(isDigit(currentChar))
    		return scanNumber();
    	if(isLetter(currentChar))
    		return scanIdentifier();
    	return scanOperand();
    }    
    /**
     * Method: isDigit
     * Usage: Scanner.isDigit(char digit)
     * -------
     * checks to see if the input char, param digit, is defined in the regular expression [0-9].
     * 
     * @param digit a char to be checked
     * @return true if parameter digit is included in [0-9]; otherwise, false.
     */
    public static boolean isDigit(char digit) 
    {
    	return digit>='0' && digit<='9';
    }
    
    /**
     * Method: isLetter
     * Usage: Scanner.isLetter(char letter)
     *-------
     * checks to see if the input char, param letter, is defined in the regular expression
     * [a-zA-Z]
     * 
     * @param letter a char to be checked
     * @param true if the input char is a letter defined by the regular expression [a-zA-Z].
     */
    public static boolean isLetter(char letter)
    {
    	return letter>='a' && letter<='z' || letter>='A' && letter<='Z';
    }
    
    /**
     * Method: isWhitespace
     * Usage: Scanner.isWhitespace(char testchar)
     * ---------
     * checks to see if the input char, param testchar, is one of the following:
     * 		1. ' '
     * 		2. tab or new line escape sequence
     * 		3. carriage return escape sequence
     * 
     * @param testchar the character to be tested as whitespace
     * @return true if testchar is whitespace, otherwise, false.
     */
    public static boolean isWhitespace(char testchar) 
    {
    	return testchar==' ' || testchar == '\n' || testchar == '\r' || testchar=='\t'; 
    }
    
    /**
     * Method: scanNumber
     * Usage: private method - scanNumber()
     * ---------
     * advances input stream and concatenates characters to return string until number is completed. 
     * 
     * @precondition  the input stream has read the first digit into currentChar
     * @postcondition the input stream has read past the last digit, and the next character
     * 				  is stored in currentChar
     * 
     * @throws ScanErrorException if no lexeme is recognized (i.e. currentChar at the start of the 
     * 							  method is not a digit).
     * @returns lexeme of one number
     */
    private String scanNumber() throws ScanErrorException
    {
    	if (!isDigit(currentChar))
    		throw(new ScanErrorException("No number is recognized"));
    	String number="";
    	while (hasNext() && isDigit(currentChar))
    	{
    		number+=currentChar;
    		eat(currentChar);
    	}
    	return number;
    }
    
    /**
     * Method: scanIdentifier
     * Usage: private method - scanIdentifier()
     * -----------
     * advances input stream and concatenates characters to return string until identifier is completed
     * 
     * @precondition  the input stream has read the first identifier into currentChar
     * @postcondition the input stream has read past the last identifier, and the next character
     * 				  is stored in currentChar
     * 
     * @throws ScanErrorException if no lexeme is recognized (i.e. currentChar at the start of the 
     * 							  method is not a letter).
     * @returns lexeme of one identifier
     */
    private String scanIdentifier() throws ScanErrorException
    {
    	if (!isLetter(currentChar))
    		throw(new ScanErrorException("No identifier is recognized"));
    	String identifier="";
    	while(hasNext() && (isLetter(currentChar) || isDigit(currentChar)))
    	{
    		identifier+=currentChar;
    		eat(currentChar);
    	}
    	return identifier;
    }
    
   /**
    * Method: scanOperand
    * Usage: private method - scanOperand()
    * --------------
    * matches currentChar to list of valid operands: ['=','+','-','*','/','%','(',')',';',':','.','>','<'] 
    * and returns it. 
    * operands may only be 1 character long, with the exception of >=, <=, and :=
    * checks if line comment is started
    * 
    * @return string containing operand token
    */
    private String scanOperand() throws ScanErrorException
    {
    	char[] validOperands = {'=','+','-','*','/','%','(',')',';',':','.','>','<',','};
    	int findIndex=0;
    	boolean found = currentChar==validOperands[findIndex];
    	while(!found && findIndex < validOperands.length-1)
    	{
    		findIndex++;
    		found = currentChar==validOperands[findIndex];
    	}
    	if (!found)
    		throw(new ScanErrorException("No operand is recognized"));
    	char returnedOperand=currentChar;
    	eat(currentChar);
    	String fullOperand="";
    	// account for <>
    	if (returnedOperand=='<' && currentChar=='>')
    	{
    		fullOperand=""+returnedOperand+currentChar;
    		eat(currentChar);
    	}
    	// account for <=, >=, and :=
    	else if ((returnedOperand=='<' || returnedOperand=='>' || returnedOperand==':') && currentChar=='=')
    	{
    		fullOperand=""+returnedOperand+currentChar;
    		eat(currentChar);
    	}
    	else
    		fullOperand=""+returnedOperand;
    	// account for line comments
    	if (returnedOperand=='/' && currentChar=='/')
    		eatLineComment();
    	// account for block comments
    	if (returnedOperand=='/' && currentChar=='*')
    		eatBlockComment();
    	return fullOperand; 
    }
    
    /**
     * Method: isComment
     * Usage:  private method - isComment()
     * -------------------
     * called when the currentchar is the 2nd "/" at the end of "//" indicating the start of a comment.
     * eats until and including the end of the line is reached.
     * @throws ScanErrorException if eat doesn't work
     */
    private void eatLineComment() throws ScanErrorException
    {
    	while(currentChar!='\n')
    		eat(currentChar);
    	eat(currentChar);
    }
    
    /**
     * Method: eatBlockComment
     * Usage: private method - eatBlockComment
     * ----------
     * eats the block comment. called when current char is on the '*' indicating the start of a block comment.
     * eats up to and including the '/'
     * 
     * @throws ScanErrorException if eat doesn't work
     */
    private void eatBlockComment() throws ScanErrorException
    {
    	boolean endBlock=false;
    	while(hasNext() && !endBlock)
    	{
    		char temp = currentChar;
    		eat(currentChar);
    		if (temp=='*' && currentChar=='/')
    			endBlock=true;
    	}
    	if(!hasNext() && !endBlock)
    	{
    		throw(new ScanErrorException("Your block comment was never completed."));
    	}
    	eat(currentChar); // eats the '/'
    }
}
