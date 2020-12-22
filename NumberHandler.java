// Eric Landquist
// Mike Melton
// CS2704
// NumberHandler.java

import java.awt.*;
import java.awt.event.*;


/*
 *  Class NumberHandler
 *
 *  This class includes the functionality to be implemented when
 *  a digit, '.', 'i', or '+I' is pressed.
 *
 */

class NumberHandler implements ActionListener {

    String		number;
    Label		screen;
    StringBuffer	display;
    String		progDisplay;
    RPNCalculator	app;
    DoubleStack		stack;
    ProgStack[]		screenArray;
    List		listBox;
    boolean		signFlag;
    int 		decBeforeSign;
    int			decAfterSign;

    public NumberHandler( String label, RPNCalculator app ) {

// number is the label on the keypad button.
	this.number = label;
	this.app = app;
	this.screenArray = app.screenArray;
	signFlag = false;
	decBeforeSign = 0;
	decAfterSign = 0;
    }

	public void actionPerformed(ActionEvent e) {
		int index1 = 0;	// The selected item.

	// If we are on the main screen...
	if (app.activeScreen == 0) {
	    screen = app.xData;
	    display = new StringBuffer( screen.getText() );
	    stack = app.stack;

		/*
		 *  Don't allow insertion of more than one decimal
		 *  point (per part), i, or +I in one number
		 */
	    if ( !app.opFlag2 ) {
		if (!isValidEntry(number, display.toString()))
			return;
	    }

	    app.opFlag1 = app.opFlag2;
	    app.opFlag2 = false;

		/*
		 *  If the key pressed before this one was a
		 *  non-digit, begin a new number; else, append
		 *  the number pressed to the current number
		 */
	    if ( app.opFlag1 ) {
		// If the first digit of a new number is the +I, it would be redundant, so we don't want
		// to insert it or a plus sign into the string
		if(!number.equals("+I")) {
			display = new StringBuffer( number );
			app.updateDisplay();

			// For here and in similar statements below, if the key pressed 
			// is a number, we don't want to be able to delete a whole line.
			app.deleteable = false;
		}
	    }
	    else {
		// Including the case in which we are adding the imaginary part of a number on.
		if (number.equals("+I")) { 
			display.append("+");
			app.deleteable = false;
		}
		else {
			// A number cannot follow an i. In all cases, i is the last element of a 				// number.
			for ( int n = 0; n < display.length(); n++ ) {
				if ((display.charAt(n) == 'i'))
			   		 return;
			}
			display.append( number );
			app.deleteable = false;
		}
	    }
	    screen.setText( display.toString() );
	} // end if

	// If we are using a program screen
	else if ((app.activeScreen >= 1) && (app.activeScreen <= 5)) {

		listBox = screenArray[app.activeScreen-1].listBox;
			
		// If there is an item selected in the middle or beginning of the list somewhere.
		// Note if you single click on a number, it will append. Unless you hit the new
		// number button.
		if ((listBox.getSelectedIndex() >= 0) && (listBox.getSelectedIndex() < listBox.getItemCount())) {
			progDisplay = listBox.getSelectedItem();
			// If the selected item is an operation
			if (isOper(progDisplay)) {
				// Make sure that the label is an invalid value to put at this point 					// in the expression: only trying to do a +I would be invalid here.
				if (number.equals("+I"))
					return;
				else {
					listBox.add(number, index1 = listBox.getSelectedIndex());
					listBox.select(index1);
				}	
			}
			// The case where we are adding onto a number or creating a new 
			// number.
			else {
				// If the flag allows us to insert a new number, do so if valid.
				if(screenArray[app.activeScreen-1].numFlag) {
					if (number.equals("+I"))
						return;
					else {
						listBox.add(number, index1 = listBox.getSelectedIndex());
						listBox.select(index1);
						screenArray[app.activeScreen-1].numFlag = false;
					}
				}
				else {
					// Return if we don't have a valid entry.
					if (!isValidEntry(number, progDisplay))
						return;
					// Otherwise, put the number on. If the +I button is pushed, however,
					// only add on a "+".
					else {
						if(number.equals("+I"))
							listBox.replaceItem(progDisplay+"+", index1 = listBox.getSelectedIndex());
						else if(isValidEntry(number, progDisplay))
							listBox.replaceItem(progDisplay+number, index1 = listBox.getSelectedIndex());	
						else 
							return;
						listBox.select(index1);
					}
				}
			}
		} // end case for adding the digit in the middle of the list.

		// Otherwise add the number to the end of the list.
		else {
			// picking the last element in the list.
			if(listBox.getItemCount() > 0)
				progDisplay = listBox.getItem(listBox.getItemCount()-1);

			// Return if we don't have a valid entry.
			if (!isValidEntry(number, progDisplay))
				return;
			// Otherwise, put the number on.
			else {
				if (!number.equals("+I")) {
					listBox.add(number);
					listBox.select(listBox.getItemCount()-1);
				}
			}
		} // end case for adding to the end of the list
	} // end else if for the program screens

	else 
		System.out.println("Bad screen name.");
	} // end actionPerformed().

	// isOper() will check to see if the inputted string is an operation. If it is, isOper
	// will return true. Otherwise it will return false.

	public boolean isOper (String input) {
		// If this is any one of the operations that could possibly be on the screen.
		if( (input.equals("+")) || (input.equals("-")) || (input.equals("*")) || (input.equals("/")) || 
(input.equals("%")) || (input.equals("^")) || (input.equals("^2")) || (input.equals("sqrt")) || (input.equals("^-1")) || 
(input.equals("e^")) || (input.equals("10^")) || (input.equals("ln")) || (input.equals("log")) || (input.equals("!")) || 
(input.equals("y C x")) ||(input.equals("y P x")) || (input.equals("x<->y")) || (input.equals("+")) || (input.equals("ROLL")) || 
(input.equals("sin")) || (input.equals("cos")) || (input.equals("tan")) || (input.equals("arcsin")) || (input.equals("arccos")) ||
(input.equals("arctan")) || (input.equals("sinh")) || (input.equals("cosh")) || (input.equals("tanh")) || 
(input.equals("arcsinh")) || (input.equals("arccosh")) || (input.equals("arctanh")) || (input.equals("RCL")) || 
(input.equals("STORE")) )
			return true;
		else 
			return false;
	} // end isOper()

// isValidEntry() will return true if the entry is a valid extension on a complex number, and will 
// return false if it is not valid.

	public boolean isValidEntry(String entry, String complexNum) {

		if ( entry.equals(".") ) {

		    try{
			    if(complexNum.equals(""));
			} catch (NullPointerException npe) {
				return true;
			}
		   
		    for ( int n = 0; n < complexNum.length(); n++ ) {
			if ((complexNum.charAt(n) == '+') || (complexNum.charAt(n) == '-'))
				signFlag = true;
			// A decimal point cannot follow an 'i'.
			if (complexNum.charAt(n) == 'i')
			    	return false;
			// If a decimal comes up, increment the counter depending on where the
			// decimal point is.
			if ((complexNum.charAt(n) == '.') && (signFlag == false))
				decBeforeSign++;
			if ((complexNum.charAt(n) == '.') && (signFlag == true))
				decAfterSign++;			
		    }
		    if ((signFlag == false) && (decBeforeSign >= 1))
			return false;
		    if ((signFlag == true) && ((decBeforeSign >= 1) || (decAfterSign >= 1)))
			return false;
		}
		// An i cannot follow another i.
		if ( entry.equals("i") ) {

		    try{
			    if(complexNum.equals(""));
			} catch (NullPointerException npe) {
				return true;
			}		 

		    for ( int n = 0; n < complexNum.length(); n++ ) {
			if ((complexNum.charAt(n) == 'i'))
			    return false;
		    }
		}
// If an i, +, or nothing is in the string, a plus cannot follow it (or nothing). I.e. + must always 
// follow a digit.
		if ( entry.equals("+I") ) {
		    try{
			    if(complexNum.equals(""));
			} catch (NullPointerException npe) {
				return false;
			}
		    for ( int n = 0; n < complexNum.length(); n++ ) {
			if ((complexNum.charAt(n) == '+') || (complexNum.charAt(n) == 'i') || (complexNum.length() == 0))
			    return false;
		    }
		}

		// If we have a digit, 0-9, it cannot follow an i.
		if( entry.equals("0") || entry.equals("1") || entry.equals("2") || entry.equals("3") || entry.equals("4") ||
		    entry.equals("5") || entry.equals("6") || entry.equals("7") || entry.equals("8") || entry.equals("9") ) {
			
		    try{
			    if(complexNum.equals(""));
			} catch (NullPointerException npe) {
				return true;
			}

			for ( int n = 0; n < complexNum.length(); n++ ) {
				if(complexNum.charAt(n) == 'i')
					return false;
			}
		}

		// If false hasn't been returned yet, then we have a valid entry.
		return true;
	} // end isValidEntry();

} // End class NumberHandler