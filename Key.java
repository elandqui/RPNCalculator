// Eric Landquist
// Mike Melton
// CS2704
// Key.java

import java.awt.*;

/*
 *  class Key
 *
 *  This class contains the functionality to create the keys on the
 *  calculator display keypad.  It adds a different button handler
 *  according to the type of the Key; doing so sets up a pseudo-
 *  hierarchical Key set-up to eliminate a long case statement.
 *
 */

public class Key extends Button {
    private int op;
    private int keyType;
    RPNCalculator app;
    Label xNum;
    DoubleStack stack;

    public Key(String label, int type, int operation,
	    RPNCalculator app) {
	super(label);

	this.app = app;
	xNum = app.xData;
	stack = app.stack;
	this.op = operation;

	keyType = type;
	
	this.setFont(new Font("Courier", Font.PLAIN, 11));
	this.setForeground(new Color(255,255,255));

	// Setting up the action listeners for each button.
	if (keyType == KeyPad.ENTER){
		this.addActionListener( new EnterEventHandler( app ) );
	}

	// A number, i, or a decimal point.
	else if (keyType == KeyPad.NUMBER){
		this.addActionListener( new NumberHandler( label, app ));
	}
	// Actually, I think I took Pi out to make room
	else if (keyType == KeyPad.NUM_PI){
		this.addActionListener( new MenuEventHandler(MenuEventHandler.CONST_PI, app));
	}

	// For the program screens
	else if (keyType == KeyPad.PROG) {	
		if (op == KeyPad.EF1)
			this.addActionListener(new MenuEventHandler(MenuEventHandler.FILE_PROG1, app));
		if (op == KeyPad.EF2)
			this.addActionListener(new MenuEventHandler(MenuEventHandler.FILE_PROG2, app));
		if (op == KeyPad.EF3)
			this.addActionListener(new MenuEventHandler(MenuEventHandler.FILE_PROG3, app));
		if (op == KeyPad.EF4)
			this.addActionListener(new MenuEventHandler(MenuEventHandler.FILE_PROG4, app));
		if (op == KeyPad.EF5)
			this.addActionListener(new MenuEventHandler(MenuEventHandler.FILE_PROG5, app));

		// If we have a program button that will actually run the built in program, then
		// it should have KeyPadHandler, since the F1 - F5 buttons are on the KeyPad.
		else
			this.addActionListener(new KeyPadHandler(app, op));
	}
	// Anything else is just a normal operation.
	else {
		this.addActionListener( new KeyPadHandler(app, op));
	}    
    }

    public int getType() {
	return this.keyType;
    }
}