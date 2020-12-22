// Eric Landquist
// Mike Melton
// CS2704
// Keypad.java

import java.awt.*;


/*
 *  Class KeyPad
 *
 *  This class builds the keypad
 *
 */

public class KeyPad extends Panel {
    
	/*
	 *  Assign arbitrary integer constants to all the
	 *  Key types.
	 */
    public final static int NUMBER = 0;
    public final static int BINARY = 1;
    public final static int UNARY = 2;
    public final static int TRIG = 3;
    public final static int STACK = 4;
    public final static int OTHER = 5;
    public final static int ENTER = 6;
    public final static int PROG = 7;
    public final static int NUM_PI = 8;

	/*
	 *  Assign arbitrary integer constants to all the
	 *  Key operations.
	 */
    public final static int ADD = 1;		// y + x
    public final static int SUBTRACT = 2;	// y - x
    public final static int MULTIPLY = 3;	// y * x
    public final static int DIVIDE = 4;		// y / x
    public final static int POWER = 5;		// y ^ x
    public final static int EXP = 6;		// e^x 
    public final static int MOD = 7;		// y % x
    public final static int RECIPROCAL = 8;	// 1/x
    public final static int SQUARE = 9;		// x ^ 2	
    public final static int SQRT = 10;		// sqrt(x)
    public final static int SIGN = 11;		// -x
    public final static int COMCONJ = 46;	// -i
    public final static int SIN = 12;		// sin x
    public final static int COS = 13;		// cos x
    public final static int TAN = 14;		// tan x
    public final static int ARCSIN = 15;	// arcsin x
    public final static int ARCCOS = 16;	// arccos x
    public final static int ARCTAN = 17;	// arctan x
    public final static int LN = 18;		// ln x
    public final static int LOG = 19;		// log x
    public final static int TENEXP = 20;	// 10 ^ x
    public final static int ROLL = 21;		// roll the values
						// of the stack.
    public final static int STORE = 22;		// store a value
    public final static int RECALL = 23;	// recall stored
    public final static int SWITCH = 24;	// x <-> y
    public final static int PERM = 25;		// y P x Permutation
    public final static int COMB = 26;		// y C x Combination
    public final static int CLSTACK = 27;	// Clears the stack.
    public final static int DELETE = 28;	// delete last
						// inputted digit.
    public final static int FACTORIAL = 39;	// x! = 1*2*3*...*(x-1)*x.
    public final static int ASINH = 40;		// arc hyperbolic sine.
    public final static int ACOSH = 41;		// arc hyperbolic cosine.
    public final static int ATANH = 42;		// arc hyperbolic tangent.
    public final static int SINH = 43;		// hyperbolic sine.
    public final static int COSH = 44;		// hyperbolic cosine.
    public final static int TANH = 45;		// hyperbolic tangent.

    public final static int EF1 = 29;		// For the program screens
    public final static int EF2 = 30;
    public final static int EF3 = 31;
    public final static int EF4 = 32;
    public final static int EF5 = 33;
    public final static int F1 = 34;		// For evaluating hte programs.
    public final static int F2 = 35;
    public final static int F3 = 36;
    public final static int F4 = 37;
    public final static int F5 = 38;

    RPNCalculator app;

    public KeyPad( RPNCalculator app ) {
	super();

	this.app = app;
	
	    /*
	     *  Add the keys
	     */
	this.setLayout(new GridLayout(0,5,0,0));
	this.add(new Key("Edit F1", PROG, EF1, app));
	this.add(new Key("Edit F2", PROG, EF2, app));
	this.add(new Key("Edit F3", PROG, EF3, app));
	this.add(new Key("Edit F4", PROG, EF4, app));
	this.add(new Key("Edit F5", PROG, EF5, app));
	this.add(new Key("F1", PROG, F1, app));
	this.add(new Key("F2", PROG, F2, app));
	this.add(new Key("F3", PROG, F3, app));
	this.add(new Key("F4", PROG, F4, app));
	this.add(new Key("F5", PROG, F5, app));
	this.add(new Key("x!", UNARY, FACTORIAL, app));
	this.add(new Key("y C x", BINARY, COMB, app));
	this.add(new Key("log x", UNARY, LOG, app));
	this.add(new Key("ln x", UNARY, LN, app));
	this.add(new Key("y ^ x", BINARY, POWER, app));	
	this.add(new Key("-I", UNARY, COMCONJ, app));
	this.add(new Key("y P x", BINARY, PERM, app));
	this.add(new Key("10 ^ x", UNARY, TENEXP, app));
	this.add(new Key("e ^ x", UNARY, EXP, app));
	this.add(new Key("x ^ -1", UNARY, RECIPROCAL, app));
	this.add(new Key("y % x", BINARY, MOD, app));
	this.add(new Key("arcsinh", TRIG, ASINH, app));
	this.add(new Key("arccosh", TRIG, ACOSH, app));
	this.add(new Key("arctanh", TRIG, ATANH, app));
	this.add(new Key("+I", NUMBER, NUMBER, app));
	this.add(new Key("x<->y", STACK, SWITCH, app));
	this.add(new Key("sinh", TRIG, SINH, app));
	this.add(new Key("cosh", TRIG, COSH, app));
	this.add(new Key("tanh", TRIG, TANH, app));
	this.add(new Key("i", NUMBER, NUMBER, app));
	this.add(new Key("ROLL", STACK, ROLL, app));
	this.add(new Key("arcsin", TRIG, ARCSIN, app));
	this.add(new Key("arccos", TRIG, ARCCOS, app));
	this.add(new Key("arctan", TRIG, ARCTAN, app));
	this.add(new Key("SQRT(x)", UNARY, SQRT, app));
	this.add(new Key("STORE", OTHER, STORE, app));
	this.add(new Key("sin", TRIG, SIN, app));
	this.add(new Key("cos", TRIG, COS, app));
	this.add(new Key("tan", TRIG, TAN, app));
	this.add(new Key("x ^ 2", UNARY, SQUARE, app));
	this.add(new Key("RCL", OTHER, RECALL, app));
	this.add(new Key("7", NUMBER, NUMBER, app));
	this.add(new Key("8", NUMBER, NUMBER, app));
	this.add(new Key("9", NUMBER, NUMBER, app));
	this.add(new Key("y / x", BINARY, DIVIDE, app));
	this.add(new Key("DEL", OTHER, DELETE, app));
	this.add(new Key("4", NUMBER, NUMBER, app));
	this.add(new Key("5", NUMBER, NUMBER, app));
	this.add(new Key("6", NUMBER, NUMBER, app));
	this.add(new Key("y * x", BINARY, MULTIPLY, app));
	this.add(new Key("CLST", STACK, CLSTACK, app));
	this.add(new Key("1", NUMBER, NUMBER, app));
	this.add(new Key("2", NUMBER, NUMBER, app));
	this.add(new Key("3", NUMBER, NUMBER, app));
	this.add(new Key("y - x", BINARY, SUBTRACT, app));
	this.add(new Key("ENTER", ENTER, NUMBER, app));
	this.add(new Key("0", NUMBER, NUMBER, app));
	this.add(new Key(".", NUMBER, NUMBER, app));
	this.add(new Key("+ / -", UNARY, SIGN, app));
	this.add(new Key("y + x", BINARY, ADD, app));
    }
} // end KeyPad