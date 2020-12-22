// Eric Landquist
// Mike Melton
// CS2704 Scott Guyer
// HelpOp.java

import java.awt.*;
import java.awt.event.*;

// Help screen decribing the various operations the RPNCalculator uses.

public class HelpOp extends Frame {

	public HelpOp () {
		super("Help - Operations");
		this.setLayout(new BorderLayout());
		TextArea field = new TextArea(100, 100);
		// We want the help screen to be a read-only field.
		field.setEditable(false);
		field.append("				HELP - OPERATIONS 	");
field.append("\n			");
field.append("\n This help screen will show you the different operations that can be performed with the	");
field.append("\n RPNCalculator and what they mean.");
field.append("\n	");
field.append("\n y + x		Addition ");
field.append("\n y - x		Subtraction ");
field.append("\n y * x		Multiplication 	");
field.append("\n y / x		Division 	");
field.append("\n y ^ x		Power: y to the x power	");
field.append("\n e^x		Exponential: e to the x power	");
field.append("\n y % x		Mod: The remainder when y is divided by x. ");					
field.append("\n 1/x		Reciprocal: 1 divided by x. 	");
field.append("\n x ^ 2		Square: x squared. 	");
field.append("\n SQRT(x)		Square root of x 	");
field.append("\n + / -		Changes the sign of x. 	");
field.append("\n -I		Finds the complex conjugate of x: It changes the sign on the imaginary ");
field.append("\n			part of x. Treated as an operation. Therefore it will only work ");
field.append("\n			with an i in the field.");
field.append("\n sin x 		Takes the sine of angle x. ");
field.append("\n cos x		Takes the cosine of angle x. ");
field.append("\n tan x		Takes the tangent of angle x. ");
field.append("\n arcsin x		Takes the inverse sine of x. ");
field.append("\n arccos x		Takes the inverse cosine of x. 	");
field.append("\n arctan x 		Takes the inverse tangent of x. ");
field.append("\n sinh x		Takes the hyperbolic sine of angle x.	");
field.append("\n cosh x 		Takes the hyperbolic cosine of angle x. ");
field.append("\n tanh x 		Takes the hyperbolic tangent of angle x. ");
field.append("\n arcsinh x		Takes the inverse hyperbolic sine of x. ");
field.append("\n arccosh x		Takes the inverse hyperbolic cosine of x. ");
field.append("\n arctanh x		Takes the inverse hyperbolic tangent of x. ");
field.append("\n ln x		Takes the natural log of x: Logarithm base e. ");
field.append("\n log x		Takes the logarithm base 10 of x. 	");
field.append("\n 10^x		10 to the power x. 		");
field.append("\n ROLL		Takes the top element of the stack (x) and puts it at the bottom of the stack.");
field.append("\n STORE		Stores the top element of the stack (x) in memory. ");
field.append("\n RCL		Recalls the stored element, if one is stored. ");
field.append("\n x <-> y		Switches the top two elements of the stack (x and y). 	");
field.append("\n y C x		Combination: Finds the number of combinations of y in x (y choose x). ");
field.append("\n			Can only be used with real numbers. Any real number ");
field.append("\n			will be rounded down to an integer (2.9 rounds to 2).");						
field.append("\n y P x		Permutation: Finds the number of permutations of y in x. ");
field.append("\n			Can only be used with real numbers. Any real number ");
field.append("\n			will be rounded down to an integer (2.9 rounds down to 2).");
field.append("\n x!		Factorial: Finds x*(x-1)*(x-2)*...*3*2*1. 0! = 1. Can only be used");
field.append("\n			with real numbers. Any real number will be rounded down");
field.append("\n			to the nearest integer (2.9 rounds down to 2).	");
field.append("\n CLST		Clear stack: Clears the screen and the stack. 		");
field.append("\n ENTER		Enters a value into the stack				");
field.append("\n DEL		If you are in the process of writing in a number, DEL will only delete ");
field.append("\n			one digit at a time, taking off a decimal and a digit when it ");
field.append("\n			reaches that point. ");
field.append("\n		However, if you have performed an operation or entered in a number, the ");
field.append("\n			DEL key will wipe out an entire line. ");
field.append("\n EF1, EF2, EF3, EF4, EF5: 						");
field.append("\n		See the Program Screens help screen. 			");
field.append("\n F1, F2, F3, F4, F5: 							");
field.append("\n		See the Program Screens help screen. 			");
field.append("\n +I		See the Numbers help screen. 				");
field.append("\n i		See the Numbers help screen. 				");
field.append("\n 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 						");
field.append("\n		See the Numbers help screen.		 		");
field.append("\n .		See the Numbers help screen. 				");
field.append("\n Pi		See the Numbers help Screen. 				");
			        
		this.add("Center", field);
		// Closes the help screen.
		Button ok = new Button("OK");
		ok.addActionListener(new CloseFrameHandler(this));
		this.add("South", ok);
	}

} // end class HelpOp