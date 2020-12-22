// Eric Landquist
// Mike Melton
// CS 2704 Scott Guyer
// InputVariableHandler.java

import java.awt.*;
import java.awt.event.*;

// This class will work with the user defined variables.

public class InputVariableHandler implements ActionListener {
    RPNCalculator app;
    StoreVariableDialog d;
    TextField tf1, tf2;
    String name;
    double [] value = new double[2];
    StoredVariable[] sv;
    Menu variables;
    MenuItem mi;

    public InputVariableHandler( RPNCalculator app, StoreVariableDialog d ) {
	this.app = app;
	this.d = d;
    }

    public void actionPerformed(ActionEvent e) {
	this.tf1 = d.name;
	this.tf2 = d.value;
	this.sv = app.vars;
	this.variables = app.variables;
	this.name = tf1.getText();

// Tests to see if what the user entered is a valid entry. If it is, it goes into value.
// If it isn't the user has another crack at entering a value.
	try {
	    this.value = RPNCalculator.stringToComplex(tf2.getText());
	} catch (NumberFormatException nfe) {
	    System.out.println("ERROR:  Invalid value.  Please try again.");
	    return;
	}

	// This checks to see that only one number can be stored under a given name.
	// So you cannot overwrite a value.
	for ( int i = 0; i < app.numVars; i++ ) {
	    if (sv[i].name.equals(name)) {
		System.out.println("ERROR:  A variable is already stored under that name.  Please choose another.");
		return;
	    }
	}

	// Value and name are stored in an array.
	sv[app.numVars] = new StoredVariable( name, value );
	app.numVars++;

	// And the name is added to the menu.
	mi = new MenuItem(name);
	mi.addActionListener( new MenuVariableHandler( app, name, value ) );
	variables.add(mi);
		
	d.setVisible(false);
	d.dispose();
    }
} // end InputVariableHandler