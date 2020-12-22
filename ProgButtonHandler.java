// Eric Landquist
// Mike Melton
// CS 2704
// ProgButtonHandler.java

import java.awt.*;
import java.awt.event.*;

// class ProgButtonHandler
// This class will handle the events done by the buttons on the program screen.

public class ProgButtonHandler implements ActionListener {

	// Integers to specify the particular button that was pressed.
	public static final int CLEAR = 1;
	public static final int NEWNUM = 2;
	public static final int DELETE = 3;
	public static final int DONE = 4;
	public static final int UNSLCT = 5;

	// The integer passed into the method to say which button specifically was pressed.
	int id;

	// The program screen.
	ProgStack screen;
	
	// The list of operations.
	List listBox;

	// The RPNCalculator.
	RPNCalculator app;
	
	// The top panel for the calculator.
	Panel top;

	// The main screen for the calculator.
	Panel calcScreen;

	// The constructor: setting local variables to variables passed in.
	public ProgButtonHandler ( int id, ProgStack screen, RPNCalculator app ) {
		this.id = id;
		this.screen = screen;
		this.listBox = screen.listBox;
		this.app = app;
		this.top = top;
		this.calcScreen = app.topCenter;
	} // end constructor.

	public void actionPerformed (ActionEvent e) {
		switch (id) {
			// Clears the screen.
			case CLEAR:
				listBox.removeAll();
				break;

			// This allows for a new number to be inserted above to any selected 
			// item.
			case NEWNUM:
				screen.numFlag = true;
				break;

			// This button deletes a selected item from the list.
			case DELETE:
				// If no item is selected:
				if (listBox.getSelectedIndex() < 0)
					System.out.println("Nothing selected.");
				// If an item is selected, delete it.
				else 
					listBox.remove(listBox.getSelectedIndex());
				break;

			// Return to the main screen.
			case DONE:
			// Whatever screen was there previously, take it out and make it 					// invisible.
		
				screen.setVisible(false);

				// Set the name for the active screen: 0 for the main screen, set 					// the size.
				app.activeScreen = 0;
				break;
			
			// To unselect a line.
			case UNSLCT:
				listBox.deselect(listBox.getSelectedIndex());
				break;

			default:
				System.out.println("Bad button.");
		
		} // end switch.	

	} // end actionPerformed()
} // end class ProgButtonHandler