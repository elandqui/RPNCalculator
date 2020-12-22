// Eric Landquist and Mike Melton
// CS2704 Scott Guyer
// MenuVariableHandler.java

import java.awt.*;
import java.awt.event.*;

// This class handles the case where  a user is requesting a variable
// that he defined earlier.

class MenuVariableHandler implements ActionListener {
    RPNCalculator app;
    String name;
    double[] value = new double[2];
    DoubleStack stack;
    ProgStack[] screenArray;

    public MenuVariableHandler( RPNCalculator app, String name, double[] value ) {
	this.app = app;
	this.name = name;
	this.value = value;
	this.screenArray = app.screenArray;
    }

    public void actionPerformed(ActionEvent e) {

	// If the variable is being called for the main screen.
	if (app.activeScreen == 0) {
		this.stack = app.stack;
		stack.push(value);
		app.updateDisplay();
		app.outputStack();
	}

	// If the variable is being called on a program screen, add the value to the list.
	else if ((app.activeScreen >= 1) && (app.activeScreen <= 5)) {
		// For pure real numbers
		if (value[1] == 0.0)
			screenArray[app.activeScreen-1].listBox.add(String.valueOf(value[0]), screenArray[app.activeScreen-1].listBox.getSelectedIndex());		
		// For pure imaginary numbers
		else if (value[0] == 0.0)
			screenArray[app.activeScreen-1].listBox.add(String.valueOf(value[1])+"i", screenArray[app.activeScreen-1].listBox.getSelectedIndex());		
		// For complex numbers with the imaginary part less than zero.
		else if (value[1] < 0.0)
			screenArray[app.activeScreen-1].listBox.add(String.valueOf(value[0])+String.valueOf(value[1])+"i", screenArray[app.activeScreen-1].listBox.getSelectedIndex());		
		// For complex numbers with the imaginary part greater than zero.
		else if (value[1] > 0.0)
			screenArray[app.activeScreen-1].listBox.add(String.valueOf(value[0])+"+"+String.valueOf(value[1])+"i", screenArray[app.activeScreen-1].listBox.getSelectedIndex());		
		// Otherwise something is messed up.
		else {
			System.out.println("Bad value for the variable.");
			return;
		}
	}

	// If there is an invalid screen name.
	else
		System.out.println("Bad screen name.");

    } // end actionPerformed().
} // end class MenuVariableHandler
