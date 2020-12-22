// Eric Landquist
// Mike Melton
// CS2704
// Parser.java


import java.awt.*;
import java.util.*;

/*  Infix to postfix parser code adapted from "Data Structures, 
 *  Algorithms, & Software Principles In C" by Thomas A. Standish,
 *  pages 598-599.
 *
 *
 *  This class works in the following manner to convert an infix
 *  expression (with no spaces) to postfix:
 *
 *  In order to account for parentheses, order of operations, and
 *  other such problems with parsing, recursion must be used to have
 *  any type of efficiency.  There are basically four levels of
 *  recursion here:  ParseE, for Expression; ParseT, for Term; 
 *  ParseF, for Factor; and ParseP, for Primary.  Simply put, the
 *  InfixToPostfix() method climbs up these levels of recursion
 *  from lowest to highest and then moves down, going back up
 *  occasionally when an operation of higher order is encountered.
 *  In this way, complex expressions with varying orders of operations
 *  and multiple sets of parentheses can be parsed with a relatively
 *  small amount of code.
 *
 *
 *  Known bug:  When parsing an expression that includes one of
 *  the textual functions (sin, cos, ln, sqrt, etc.) followed by
 *  any expression, it takes the following expression to be part of
 *  the function parameters.  For example, it thinks sin(x)+2 is the
 *  same as sin(x+2).  I am not sure why this is happening but it
 *  must have something to do with the order of recursion; for some
 *  reason it is not regarding the ending parenthesis as the end of
 *  an expression.  Things like (x+3)*(x-2)^2 work just fine, so I
 *  know it is not a parentheses problem.
 */


public class Parser {
    String expr, temp1, temp2;
    double val;
    Stack hold = new Stack();
    public int pos = -1;
    StringBuffer postfix = new StringBuffer();
    boolean format = true;

    public Parser( String expr, double val ) {
	this.expr = expr;
	this.val = val;
    }

    public String InfixToPostfix( ) {
	ParseE( );

	if (!format) return "ERROR";
	else return (postfix.toString() + ";");
    }

    public void ParseE( ) {
	char operator;

	ParseT( );
	while ( Next() == '+' || ( Next() == '-' ) ) {
	    operator = Next();
	    Shift(operator);
	    ParseT( );
	    postfix.append( operator );
	    postfix.append( " " );
	}
    }

    public void ParseT( ) {
	char operator;

	ParseF( );
	while ( (Next() == '*') || ( Next() == '/' ) || (Next() == '%') ) {
	    operator = Next();
	    Shift(operator);
	    ParseF( );
	    postfix.append( operator );
	    postfix.append( " " );
	}
    }

    public void ParseF( ) {
	char operator;

	try { ParseP( ); }
	catch (ExprFormatException e) {
	    format = false;
	    return;
	}
	
	while ( Next() == '^' && format ) {
	    operator = Next();
	    Shift(operator);
	    try { ParseP( ); }
	    catch (ExprFormatException e) {
		format = false;
		return;
	    }
	    postfix.append( operator );
	    postfix.append( " " );
	}
    }

    public void ParseP( ) throws ExprFormatException {
	char operand;

	if ( Next() == '(' ) {
	    Shift( '(' );
	    ParseE( );
	    if (Next() == ')' )
		Shift( ')' );
	    else
		throw new ExprFormatException( '(' );

	} else {
	    operand = Next();
	    
	    if (operand == 'E') return;

	    else if ( operand == 'x' ) {
		Shift('x');
		postfix.append( val );
		if ( Next() == 'x' ) throw new ExprFormatException( operand );
	    }

		// Added statements: for inverse trig functions.
	    else if (operand == 'a') {
		try {
		    temp1 = expr.substring( pos+1, pos+7 );
		    temp2 = expr.substring( pos+1, pos+8 );
		} catch (StringIndexOutOfBoundsException e) {
		    throw new ExprFormatException( operand );
		}
		
		if ( temp1.equals("arcsin") && !temp2.equals("arcsinh") ) {
		   pos+=6;

		    if ( Next() == '(' )
			ParseE( );
		    else
			throw new ExprFormatException( operand );

		    postfix.append( "arcsin" );
		}

		else if ( temp1.equals("arccos") && !temp2.equals("arccosh") ) {
		   pos+=6;

		    if ( Next() == '(' )
			ParseE( );
		    else
			throw new ExprFormatException( operand );

		    postfix.append( "arccos" );
		}

		else if ( temp1.equals("arctan") && !temp2.equals("arctanh") ) {
		   pos+=6;

		    if ( Next() == '(' )
			ParseE( );
		    else
			throw new ExprFormatException( operand );

		    postfix.append( "arctan" );
		}

		else if ( temp2.equals("arcsinh") ) {
		   pos+=7;

		    if ( Next() == '(' )
			ParseE( );
		    else
			throw new ExprFormatException( operand );

		    postfix.append( "arcsinh" );
		}

		else if ( temp2.equals("arccosh") ) {
		   pos+=7;

		    if ( Next() == '(' )
			ParseE( );
		    else
			throw new ExprFormatException( operand );

		    postfix.append( "arccosh" );
		}

		else if ( temp2.equals("arctanh") ) {
		   pos+=7;

		    if ( Next() == '(' )
			ParseE( );
		    else
			throw new ExprFormatException( operand );

		    postfix.append( "arctanh" );
		}
		
		else throw new ExprFormatException( operand );
	    }
		// sine, square root, and hyperbolic sine.
	    else if ( operand == 's' ) {
		try {
		    temp1 = expr.substring( pos+1, pos+4 );
		    temp2 = expr.substring( pos+1, pos+5 );
		} catch (StringIndexOutOfBoundsException e) {
		    throw new ExprFormatException( operand );
		}

		if ( temp1.equals("sin") && !temp2.equals("sinh") ) {
		    pos += 3;

		    if ( Next() == '(' )
			ParseE( );
		    else
			throw new ExprFormatException( operand );

		    postfix.append( "sin" );
		}

		else if ( temp2.equals("sqrt") ) {
		    pos += 4;
		    
		    if ( Next() == '(' )
			ParseE( );
		    else
			throw new ExprFormatException( operand );

		    postfix.append( "sqrt" );
		}

		else if ( temp2.equals("sinh") ) {
		    pos += 4;
		    
		    if ( Next() == '(' )
			ParseE( );
		    else
			throw new ExprFormatException( operand );

		    postfix.append( "sinh" );
		}
	
		else throw new ExprFormatException( operand );
		
	    }
		// cosine and hyperbolic cosine
	    else if ( operand == 'c' ) {
		try {
		    temp1 = expr.substring( pos+1, pos+4 );
		    temp2 = expr.substring( pos+1, pos+5 );
		} catch (StringIndexOutOfBoundsException e) {
		    throw new ExprFormatException( operand );
		}

		if ( temp1.equals("cos") && !temp2.equals("cosh") ) {
		    pos += 3;

		    if ( Next() == '(' )
			ParseE( );
		    else
			throw new ExprFormatException( operand );

		    postfix.append( "cos" );
		}

		else if ( temp2.equals("cosh") ) {
		    pos += 4;

		    if ( Next() == '(' )
			ParseE( );
		    else
			throw new ExprFormatException( operand );

		    postfix.append( "cosh" );
		}

		else throw new ExprFormatException( operand );
	    }
		// tangent and hyperbolic tangent.
	    else if ( operand == 't' ) {
		try {
		    temp1 = expr.substring( pos+1, pos+4 );
		    temp2 = expr.substring( pos+1, pos+5 );
		} catch (StringIndexOutOfBoundsException e) {
		    throw new ExprFormatException( operand );
		}

		if ( temp1.equals("tan") && !temp2.equals("tanh") ) {
		    pos += 3;

		    if ( Next() == '(' )
			ParseE( );
		    else
			throw new ExprFormatException( operand );

		    postfix.append( "tan" );
		}

		else if ( temp2.equals("tanh") ) {
		    pos += 4;

		    if ( Next() == '(' )
			ParseE( );
		    else
			throw new ExprFormatException( operand );
			
		    postfix.append( "tanh" );
		}

		else throw new ExprFormatException( operand );
	    }
		// log and natural log
	    else if ( operand == 'l' ) {
		try {
		    temp1 = expr.substring( pos+1, pos+3 );
		    temp2 = expr.substring( pos+1, pos+4 );
		} catch (StringIndexOutOfBoundsException e) {
		    throw new ExprFormatException( operand );
		}

		if ( temp1.equals("ln") ) {
		    pos += 2;

		    if ( Next() == '(' )
			ParseE( );
		    else
			throw new ExprFormatException( operand );

		    postfix.append( "ln" );
		}
		
		else if (temp2.equals("log")){
		    pos += 3;

		    if ( Next() == '(' )
			ParseE( );
		    else
			throw new ExprFormatException( operand );

		    postfix.append( "log" );
		}

		else throw new ExprFormatException( operand );
	    }
		// For a digit, decimal point, and e.		
	    else if ( Character.isDigit( operand ) || (operand == '.') || ((operand == 'e') || (operand =='E')) ){
		// Adding in e.
		if((operand == 'e') || (operand == 'E'))  {
			pos++;
			postfix.append("2.718281828459045");
		}
		
		else {
			Shift(operand);
			postfix.append( operand );
			while ( Character.isDigit( Next() ) || Next() == '.') {
			    operand = Next();
			    Shift(operand);
				// Add in the digit.
			    postfix.append( operand );
			}
		}
	    }

		// The case of negative signs.
	    else if ( operand == '-' ) {
		Shift(operand);
		postfix.append( "0 " );
		ParseP( );
		postfix.append( '-' );
	    }

	    else
		throw new ExprFormatException( operand );

	    postfix.append( " " );
	}
    }

	// Next() returns the char one spot over from operator.
    public char Next( ) {
	char c;

	try {
	    c = expr.charAt(pos + 1);
	} catch (StringIndexOutOfBoundsException e) {
	   return 'E';
	}

	return c;
	
    }
	// Shift() increments the position of the string, pointing to the next char.
    public void Shift( char c ) {
	hold.push( new Character( c ) );
	pos++;
    }
} // end Parser