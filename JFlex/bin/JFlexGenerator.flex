
/**
* this file defines a simple lexer for the compilers course 2014-2015
* 
* this lexer can recognize identifiers, numbers, operands, and line and
* block comments.
*
* for identifiers, numbers, and operands, the lexical rule is to return the lexeme.
* for comments of any flavor, lexical rule is to ignore the text.
* 
* nested block comments are limited to two levels of nesting in this version
*
* @author Sadhika Malladi (P7)
* @date 9/4/14
*/
import java.io.*;

he patient might have not responded or might have had tumor growth instead, which is why it 
%%
/* lexical functions */
/* specify that the class will be called Scanner and the function to get the next
 * token is called nextToken.  
 */
%class Scanner
%unicode
%line
%public
%function nextToken
/*  return String objects - the actual lexemes */
/*  returns the String "END: at end of file */
%type String
%eofval{
return "END";
%eofval}

%function blockComment
/* doesn't return anything */
%type void
%{}


/**
 * Pattern definitions
 */
whitespace			= \r|\n|\t|" "
letter				= [a-z|A-Z]
digit				= 0 | [1-9][0-9]*
eol 				= \n|\r|\r\n
startlinecomment 	= "//"
startblockcomment 	= "/*"
endblockcomment 	= "*/"
onelevelcomment 	= {startblockcomment} ({validchar}|{eol})*

operand				= "="|"+"|"-"|"*"|"/"|"%"|"("|")"|";"|":"|"."|">"|"<"
validchar			= [^\n\r]
identifier			= {letter} ({letter}|{digit})*
number				= {digit} ({digit})*
linecomment 		= {startlinecomment} ({validchar})* {eol}?
blockcomment		= {startblockcomment} ({validchar}|{eol})* {endblockcomment} {eol}?
nestedblock			= {onelevelcomment} {onelevelcomment} {endblockcomment} ({validchar}|{eol})* {endblockcomment} {eol}?

%state commentOpen
%state commentClosed

%%
/**
 * lexical rules
 */
{identifier}	{return yytext();}
{operand}		{return yytext();}
{number}		{return yytext();}
{whitespace}	{/* ignore */}
{linecomment}	{/* ignore */}
{blockcomment} 	{/* ignore */}
{nestedblock}	{/* ignore */}
{startblockcomment}	{yybegin(commentOpen); yyend(commentClosed)}
<commentOpen> {endblockcomment} {}

.				{System.out.println("illegal character "+yytext());}
