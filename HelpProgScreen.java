// Eric Landquist
// Mike Melton
// CS2704 Scott Guyer
// HelpProgScreen.java

import java.awt.*;
import java.awt.event.*;

// A help screen to describe the program screen feautre in RPNCalculator

public class HelpProgScreen extends Frame {

	public HelpProgScreen () {
		super("Help - Program Screens");
		this.setLayout(new BorderLayout());
		TextArea field = new TextArea(100, 100);
		// We want the help screen to be a read-only field.
		field.setEditable(false);
		field.append("			HELP - PROGRAM SCREENS		");
field.append("\n				");
field.append("\n This help screen will explain the purposes of the five program screens. ");
field.append("\n				");
field.append("\n BUTTONS:			");
field.append("\n EF1, EF2, EF3, EF4, EF5:	Edit one of the five program screens 	");
field.append("\n F1, F2, F3, F4, F5:		Run one the five programs. 		");
field.append("\n Unselect Line		Will unselect any selected line on the program screen. 	");
field.append("\n New Number		Will allow you to add in a number next to another number. ");
field.append("\n Delete Line		Will delete a given line. 	");
field.append("\n Clear Screen		Will clear the program screen. 	");
field.append("\n Done			Back to the Main Screen. 	");
field.append("\n Any number will also work in the program screen. ");
field.append("\n			");
field.append("\n PURPOSE: 		");
field.append("\n The program screens' purpose is to perform several operations at once 	");
field.append("\n producing a single output. 	");
field.append("\n The program screen operates using Reverse Polish Notation. Enter in a list	");
field.append("\n of operations, and when the F button the stack on the main screen is copied 	");
field.append("\n and the operations on the corresponding program screen are performed on these 	");
field.append("\n values, beginning with the first operation that was entered. Any number that is ");
field.append("\n entered in the program stack will be pused on the top of the stack. The final value ");
field.append("\n that is received is then put onto the main screen. 	");
field.append("\n					");
field.append("\n EXAMPLE: 				");
field.append("\n Suppose we want to find the square root of the sum of squares of two numbers, 3 and 4. ");
field.append("\n Main Screen: 	Program Screen:	Function: 			Stack Copy: 	");
field.append("\n z: 	0.0	x^2		Squares x			[3.0, 16.0] ");
field.append("\n y:	3.0	x<->y		Switches x and y		[16.0, 3.0] ");
field.append("\n x: 	4.0	x^2		Squares x (formerly y)	[16.0, 9.0] ");
field.append("\n [3.0, 4.0]		+		Adds x and y		[25.0] 	");
field.append("\n 		sqrt		Takes the square root	[5.0] 	");
field.append("\n				");
field.append("\n FINAL OUTPUT: 			");
field.append("\n z:	3.0 			");
field.append("\n y:	4.0 			");
field.append("\n x:	5.0 			");
field.append("\n [3.0, 4.0, 5.0] 		");
	
		this.add("Center", field);
		// Closes the help screen.
		Button ok = new Button("OK");
		ok.addActionListener(new CloseFrameHandler(this));
		this.add("South", ok);
	}

} // end class HelpOp