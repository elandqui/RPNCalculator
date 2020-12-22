// Eric Landquist
// Mike Melton
// CS2704
// MenuEventHandler.java

import java.awt.*;
import java.awt.event.*;

/*
 *  Class MenuEventHandler
 *
 *  Handles menu events.
 *
 */

class MenuEventHandler implements ActionListener {
    public final static int FILE_QUIT = 1;
    public final static int FILE_CALC = 11;
    public final static int FILE_PROG1 = 6;
    public final static int FILE_PROG2 = 7;
    public final static int FILE_PROG3 = 8;
    public final static int FILE_PROG4 = 9;
    public final static int FILE_PROG5 = 10;
    public final static int HELP_INDEX = 12;
    public final static int HELP_ABOUT = 2;
    public final static int FILE_NEW = 3;
    public final static int CONST_PI = 4;
    public final static int CONST_E = 13;
    public final static int VAR_ADD = 5;

    int id;
    RPNCalculator app;
    DoubleStack stack;
    ProgStack[] screenArray;
    Panel calcScreen;
    Panel top;

    MenuEventHandler(int type, RPNCalculator app) {
	this.id = type;
	this.app = app;
	this.top = app.top;
	this.stack = app.stack;	
	this.screenArray = app.screenArray;
	this.calcScreen = app.topCenter;

    }

    public void actionPerformed(ActionEvent e) {
	switch (id) {
		
		// For a new stack and screen.
	    case FILE_NEW:
		if(app.activeScreen == 0) {
			KeyPadHandler.clearStack(app);
			app.updateDisplay();
		}
		if((app.activeScreen >= 1) && (app.activeScreen <= 5)) {	
			screenArray[app.activeScreen-1].listBox.removeAll();
		}
		break;	

		// When the main screen is selected.
	    case FILE_CALC:
		// Whatever screen was there previously, take it out and make it invisible.
		// This procedure is the same for the cases below.
		
		if ((app.activeScreen >= 1) && (app.activeScreen == 5))
			screenArray[app.activeScreen-1].setVisible(false); 


// Set the name for the active screen: 0 for the main screen, set the size.
		app.activeScreen = 0;
		app.setSize(333,390);

		calcScreen.setVisible(true);
		break;

// Case for the program screen one.

	    case FILE_PROG1:

		if ((app.activeScreen >= 1) && (app.activeScreen == 5))
			screenArray[app.activeScreen-1].setVisible(false); 
		
		// Set the name for the active screen: 1 for program screen 1.
		app.activeScreen = 1;
		screenArray[app.activeScreen-1].setTitle("Program Screen "+app.activeScreen);
		screenArray[app.activeScreen-1].setVisible(true); 
		break;		

// Case for program screen 2.

	    case FILE_PROG2:
		if ((app.activeScreen >= 1) && (app.activeScreen == 5))
			screenArray[app.activeScreen-1].setVisible(false); 
		
		// Set the name for the active screen: 2 for program screen 2.
		app.activeScreen = 2;

		screenArray[app.activeScreen-1].setVisible(true); 	
		screenArray[app.activeScreen-1].setTitle("Program Screen "+app.activeScreen);		
		break;

// Case for program screen 3.

	    case FILE_PROG3:

		if ((app.activeScreen >= 1) && (app.activeScreen == 5))
			screenArray[app.activeScreen-1].setVisible(false); 
		
		// Set the name for the active screen: 3 for program screen 3.
		app.activeScreen = 3;
		screenArray[app.activeScreen-1].setTitle("Program Screen "+app.activeScreen);
		screenArray[app.activeScreen-1].setVisible(true); 
		break;

// Case for program screen 4.

	    case FILE_PROG4:

		if ((app.activeScreen >= 1) && (app.activeScreen == 5))
			screenArray[app.activeScreen-1].setVisible(false); 
		
		// Set the name for the active screen: 4 for program screen 4.
		app.activeScreen = 4;
		screenArray[app.activeScreen-1].setTitle("Program Screen "+app.activeScreen);
		screenArray[app.activeScreen-1].setVisible(true); 		
		break;

// Case for program screen 5.

	    case FILE_PROG5:
		if ((app.activeScreen >= 1) && (app.activeScreen == 5))
			screenArray[app.activeScreen-1].setVisible(false); 
		
		// Set the name for the active screen: 5 for program screen 5.
		app.activeScreen = 5;
		screenArray[app.activeScreen-1].setTitle("Program Screen "+app.activeScreen);
		screenArray[app.activeScreen-1].setVisible(true); 
		
		break;

		// Exits the program.
	    case FILE_QUIT:
		app.setVisible(false);
	 	app.dispose();
	  	System.exit(1);
	  	break;

		// Pushes pi onto the appropriate stack.
	    case CONST_PI:
		enterPi();
		break;

		// Pushes e ontp the appropriate stack.
	    case CONST_E:
		enterE();
		break;

		// Opens a screen to ada in a user-defined variable.
	    case VAR_ADD:
		StoreVariableDialog svd = new StoreVariableDialog( app );
		break;
		
		// Opens a screen that prompts for various help screens.
	    case HELP_INDEX:
		MenuIndex index = new MenuIndex();
		break;
	
		// About the creators of this fine software.
	    case HELP_ABOUT:
		Dialog d = new Dialog( app, "About", true );
		d.setResizable(false);
		Label l1 = new Label("RPN Calculator", Label.CENTER);
		Label l2 = new Label("Version 2.0", Label.CENTER);
		Label l3 = new Label(" ", Label.CENTER);
		Label l4 = new Label("Eric Landquist", Label.CENTER);
		Label l5 = new Label("Mike Melton", Label.CENTER);
		Label l6 = new Label("CS2704", Label.CENTER);
		Label l7 = new Label("December, 1997", Label.CENTER);
		Button close = new Button("Close");
		close.addActionListener( 
		    new CloseDialogHandler( d ) );
		d.setLayout(new GridLayout(0,1));
		d.add(l1);
		d.add(l2);
		d.add(l3);
		d.add(l4);
		d.add(l5);
		d.add(l6);
		d.add(l7);
		d.add(close);
		d.pack();
		d.setVisible(true);
		break;

 	    default: 
		System.out.println("Oops. Didn't think of that one.");
	} // end switch
    } // end actionPerformed()

// This method will pop Pi onto the appropriate stack.

	public void enterPi() {
		// Onto the main screen.
		if(app.activeScreen == 0) {		
			stack.push(KeyPadHandler.makeComplex(Math.PI, 0.0));
			app.updateDisplay();
			app.outputStack();
		}
		// Onto the program screens.
		if((app.activeScreen >= 1) && (app.activeScreen <= 5)) {
			screenArray[app.activeScreen-1].listBox.add(String.valueOf(Math.PI), screenArray[app.activeScreen-1].listBox.getSelectedIndex());
		}
	} // end EnterPi()

// This method will pop e onto the appropriate stack.

	public void enterE() {
		// Onto the main screen.
		if(app.activeScreen == 0) {		
			stack.push(KeyPadHandler.makeComplex(Math.exp(1), 0.0));
			app.updateDisplay();
			app.outputStack();
		}
		// Onto a program screen.
		if((app.activeScreen >= 1) && (app.activeScreen <= 5)) {
			screenArray[app.activeScreen-1].listBox.add(String.valueOf(Math.exp(1)), screenArray[app.activeScreen-1].listBox.getSelectedIndex());
		}
	} // end EnterE()
} // end class MenuEventHandler