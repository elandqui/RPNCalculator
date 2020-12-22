// Eric Landquist
// Mike Melton
// CS2704
// ProgStack.java

// This class will create and monitor the stack for the programmed screen.
// It will also create the program screen, which is basically run by the stack.

import java.awt.*;
import java.awt.event.*;

public class ProgStack extends Frame {

// Putting together the programmable screen.
	public static final int MAX = 10;
	List listBox = new List(MAX);

// An operation stack.
	public static final int OP_MAX = 100;
	public static int SIZE;
	public static String opStack[] = new String[OP_MAX];

// The RPNCalculator
	RPNCalculator app;

// A flag to indicate whether or not a number can be inserted above another number.
	boolean numFlag = false;

	public ProgStack () {
		SIZE = 0;
	}

// Constructor to initialize the stack to spaces.

	public ProgStack ( RPNCalculator app) {
		super();
		
		SIZE = 0;
		
		this.app = app;		

	this.setLayout(new BorderLayout());

// Setting colors.

	Color gray = new Color(128,128,128);
	Color white = new Color(255,255,255);
	Color darkGray = new Color(64,64,64);
	Color black = new Color(0,0,0);

	this.setBackground(gray);

//Putting together the program screen.
	Panel progLeft = new Panel();
	Panel progRight = new Panel();	

	progLeft.setBackground(gray);
	progLeft.setForeground(black);
	progRight.setForeground(white);

//Buttons for the screen:
	Button unsel = new Button("Unselect Line");
		unsel.addActionListener( new ProgButtonHandler(ProgButtonHandler.UNSLCT, this, app));
	Button newnum = new Button("New Number");
		newnum.addActionListener( new ProgButtonHandler(ProgButtonHandler.NEWNUM, this, app));
	Button delete = new Button("Delete Line");
		delete.addActionListener( new ProgButtonHandler(ProgButtonHandler.DELETE, this, app));
	Button clear = new Button("Clear Screen");
		clear.addActionListener( new ProgButtonHandler(ProgButtonHandler.CLEAR, this, app));
	Button done = new Button("Done");
		done.addActionListener( new ProgButtonHandler(ProgButtonHandler.DONE, this, app));
		
	// The actual screen.
	progLeft.add(listBox);
	
	// The buttons invloved.
	progRight.setLayout(new GridLayout(5,1));
	progRight.add(unsel);
	progRight.add(newnum);
	progRight.add(delete);
	progRight.add(clear);
	progRight.add(done);
	this.add("West", progLeft);
	this.add("Center", progRight);
	this.setVisible(false);

	this.setBounds(400, 0, 250, 200);

	} // End constructor.

	// To push an operation onto the operation stack.
	public static void push (String pushed) {
		// The the stack is full, do nothing.
		if (SIZE == OP_MAX-1) {
			System.out.println("Operation stack is full.");
			return;
		}
		// Otherwise put the element on.
		else {
			opStack[SIZE] = pushed;
			SIZE++;
		}
	} // end push().

	// To pop an element off the opStack.
	public static String pop() {
		// If the stack is empty, return the empty string.
		if (SIZE == 0) {
			System.out.println("Stack is empty.");
			return "";
		}
		// Otherwise send back the real thing.
		else {
			SIZE--;
			return opStack[SIZE+1];
		}
	} // end pop()
} // End ProgStack.java