// Eric Landquist 
// Mike Melton
// CS 2704
// ExprFormatException.java

public class ExprFormatException extends Exception {

// Prints an error message if an expression format error occurs.

    public ExprFormatException( char c ) {
	System.out.println("ERROR:  Expression format invalid near " + c);
    }
}
	