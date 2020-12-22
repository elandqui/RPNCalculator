// Eric Landquist
// Mike Melton
// CS2704 Scott Guyer
// HelpRPN.java

import java.awt.*;
import java.awt.event.*;

// This will create a help screen to explain the principles of Reverse
// Polish Notation, or Postfix notation.

public class HelpRPN extends Frame {

	public HelpRPN () {
		super("Help - RPN Principles");
		this.setLayout(new BorderLayout());
		TextArea field = new TextArea(100, 100);
		// We want the help screen to be a read-only field.
		field.setEditable(false);
		field.append("		HELP - RPN PRINCIPLES ");
field.append("\n "); 
field.append("\n This screen will give you information on the basics of RPN notation. ");
field.append("\n ");
field.append("\n RPN stands for 'Reverse Polish Notation,' also known as 'Postfix Notation.' ");
field.append("\n For writing equations and expressions, we usually use what is called, ");
field.append("\n 'Infix Notation.' Infix writes [binary] operations between two operands.");  
field.append("\n For example: to express 'four minus five,' we write '4-5.' ");
field.append("\n If there was only one operand for an [unary] operation, notation varies: ");
field.append("\n For example: sin(x) and x! ");
field.append("\n For Postfix Notation, the operator always goes after the operands. ");
field.append("\n For example: 'four minus five' is expressed: '4 5 -' ");
field.append("\n ");
field.append("\n For the RPNCalculator main screen, if you want to find 4-5, you must  ");
field.append("\n enter in '4', then '5', then hit the 'y - x' button. ");
field.append("\n Similarly, if you want to find '6!,' enter in '6', then hit the 'x!' button. ");
field.append("\n ");
field.append("\n STACKS ");
field.append("\n The Main Screen relies on what is called a stack. ");
field.append("\n A stack is a list of elements, here numbers, such that the last number. ");
field.append("\n That was used will be the first to get used for an operation. ");
field.append("\n The Program Screens use this method, but only for the numbers. ");
field.append("\n Thus, the first operators that are pused onto the program screen will ");
field.append("\n be the first to be used as an operation. ");
field.append("\n All numbers entered on the program screen will be pushed onto the top of ");
field.append("\n the stack of numbers, and the last numbers pushed onto the stack will ");
field.append("\n be the first to be used in an operation. ");
field.append("\n ");

		this.add("Center", field);
		// Closes the help screen.
		Button ok = new Button("OK");
		ok.addActionListener(new CloseFrameHandler(this));
		this.add("South", ok);
		
	} // end constructor

} // end class HelpRPN