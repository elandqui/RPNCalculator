// Eric Landquist and Mike Melton
// CS 2704
// EnterEventHandler.java

import java.awt.*;
import java.awt.event.*;
import DoubleStack;

// This class will handle the procedures when the enter key is
// pressed on the calculator.

public class EnterEventHandler implements ActionListener {

	public Label xNum;
	public DoubleStack nums;
	public double[] newEntry = new double[2];
	RPNCalculator app;

	String iTest;		// Test string.
	boolean iFlag; 		// Will test to see if an I is present in a string.
	boolean plusFlag;	// Will test to see if a plus sign is present in a string.
	boolean minusFlag;	// Will test to see if a minus sign is present in a string.

	int i;

	public EnterEventHandler (RPNCalculator app) {
		this.app = app;
	}

// The actual process for adding the newest entry of the calculator
// into the stack.
	public void actionPerformed(ActionEvent e) {

		this.nums = app.stack;
		this.xNum = app.xData;

// This bit of code will test to see if a plus sign is associated with an i.
// If not, then the enter will have no effect.

		if (!isPlusWithI(xNum.getText()))
			return;			

		app.opFlag1 = app.opFlag2;
		app.opFlag2 = true;

		// Since the enter key is pressed, we want to be able to delete an item that
		// is deleteable.
		app.deleteable = true;

// Converting the string from the label to a double.

                newEntry = app.stringToComplex(xNum.getText());

// Push that double onto the stack.

		nums.push(newEntry);

// If this is the first element to be placed on the stack, the push
// that same element on but decrement the counter since the user
// may want the second element to be different.
		if (nums.SIZE == 1) {
			nums.push(newEntry);
			nums.dupFlag = true;
			app.updateDisplay();
			nums.SIZE--;
		}
		else
			app.updateDisplay();

		app.outputStack();

	} // end actionPerformed

// This bit of code will test to see if a plus sign is associated with an i.

	public boolean isPlusWithI(String str) {
		char[] chars = str.toCharArray();
		plusFlag = false;
		minusFlag = false;
		iFlag = false;

		// loop through the string to find a plus or i.
			for( int i=0; i<chars.length; i++){
				if (chars[i] == '+')
					plusFlag = true;
				if (chars[i] == 'i')
					minusFlag = true;
				if (chars[i] == 'i')
					iFlag = true;
			}
	
		// If there were no plus signs or minus signs, we're OK.
			if ((plusFlag == false) && (minusFlag == false))
				return true;
		// If there was a plus sign or minus sign without an i, we're not OK.
			else if (((plusFlag == true) || (minusFlag == true)) && (iFlag == false))
				return false;
		// Otherwise we have a plus or minus sign with an i, and we're OK.
			else
				return true;
	} // end isPlusWithI()		

} // end class EnterEventHandler