// Eric Landquist
// Mike Melton
// CS2704 Scott Guyer
// HelpNumbers.java

import java.awt.*;
import java.awt.event.*;

// Help screen describing hte general format for the numbers teh RPNCalculator uses.
// Also gives definitions for terms used in the help text.

public class HelpNumbers extends Frame {

	public HelpNumbers () {
		super("Help - Numbers");
		this.setLayout(new BorderLayout());
		TextArea field = new TextArea();
		// We want the help screen to be a read-only field.
		field.setEditable(false);
		field.append(" 				Help - Numbers ");
field.append("\n						");
field.append("\n This screen will give you information on how numbers are represented on the screen");
field.append("\n and the formatting used, along with some definitions of different numbers. ");
field.append("\n 							");
field.append("\n Complex number:        A real number plus an imaginary number. ");
field.append("\n 			Examples: 0 + 5i, 4.98 + 0i, -1.0 + 3.1415i	");
field.append("\n 									");
field.append("\n Imaginary number:      Any real number times i, where i is the square root");
field.append("\n 			of -1. That is, i^2 = -1. 	");
field.append("\n 					");
field.append("\n Real number:		Any number from negative infinity to positive 	");
field.append("\n 			infinity: Any number on the line below: 	");
field.append("\n 			negative infinity <--|-----|-----|-----|-----|-----|-----|-----|-----|--> infinity ");
field.append("\n 					   -4  -3   -2   -1    0    1    2    3    4 	");
field.append("\n 						");
field.append("\n E-4			E stands for 10^, thus E-4 = 0.0001	");
field.append("\n E+6			Same idea as above, but E+6 = 1000000 ");
field.append("\n 						");
field.append("\n 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 			");	
field.append("\n		Enters that number on the screen, unless");
field.append("\n 		an i is already on the screen. 		");
field.append("\n 							");
field.append("\n .	Inserts a decimal point on the screen: only one per pure real number and only	");
field.append("\n 		one per pure imaginary number. two maximum for a complex number. 	");
field.append("\n 							");
field.append("\n i	The imaginary number i. Equal to SQRT(-1). Only one i allowed per number.");
field.append("\n							");
field.append("\n +I	Inserts a plus sign into a field to indicate the separation between the real and ");
field.append("\n 		imaginary part of a number. Only one allowed per number.");
field.append("\n 							");
field.append("\n -I	Takes the opposite of an imaginary number, i.e., it finds the complex conjugate. ");
field.append("\n 		See the Operations help screen for more. ");
field.append("\n 							");
field.append("\n Pi	A mathematical constant equal to 3.141592653589793...	");
field.append("\n 		");
field.append("\n e 	A mathematical constant equal to 2.718281828459045...	");
field.append("\n 								");
field.append("\n FORMATTING: 			");
field.append("\n On the screen, all real numbers will be displayed a decimal: 0.0, -5.0, or 7.123. ");
field.append("\n These numbers are entered on the screen as expected.		");
field.append("\n All imaginary numbers will be displayed as a pure imaginary number: 0.1i, 1.0i, -7.123i.");
field.append("\n These numbers are entered on the screen as expected, using the 'i' button to enter in i.");
field.append("\n All complex numbers, with both parts non zero are represented on the screen as: ");
field.append("\n 5.0+6.7i, -0.97+7.123i, 190.0-1.23i. 			");
field.append("\n Note the plus sign can only be entered using the '+I' button. Otherwise the ");
field.append("\n RPNCalculator will add two numbers.					");
field.append("\n The negative sign can only be entered when you have a positive imaginary and press '-I'.");
field.append("\n 							");
		this.add("Center", field);
		// Closes the screen.
		Button ok = new Button("OK");
		ok.addActionListener(new CloseFrameHandler(this));
		this.add("South", ok);
	}
} // end class HelpNumbers