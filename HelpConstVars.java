// Eric Landquist
// Mike Melton
// CS2704 Scott Guyer
// HelpConstVars.java

import java.awt.*;
import java.awt.event.*;

// This is a help screen for the Constants and the Variable menus

public class HelpConstVars extends Frame {

	public HelpConstVars () {
		super("Help - Constants and Variables");
		this.setLayout(new BorderLayout());
		TextArea field = new TextArea(100, 100);
		// We want the help screen to be a read-only field.
		field.setEditable(false);
		field.append("		HELP - CONSTANTS AND VARIABLES ");
field.append("\n This screen will give you information on how the constants work ");
field.append("\n and how to use the user-defined variable function.");
field.append("\n  ");
field.append("\n All constants and varibles can be selected straight from the menus. ");
field.append("\n When selected, both will appear on either the main screen or program ");
field.append("\n screen, whichever is selected. Details below. ");
field.append("\n ");
field.append("\n  CONSTANTS: ");
field.append("\n ");
field.append("\n Pi	Has a value of 3.141592653589793... ");
field.append("\n  ");
field.append("\n e	Has a value of 2.718281828459045... ");
field.append("\n ");
field.append("\n VARIABLES: ");
field.append("\n ");
field.append("\n Variables are all user-defined. ");
field.append("\n To add a new variable, go to 'Add New Variable' in the variables menu.");
field.append("\n Under 'Input variable name,' enter in a name for the variable you want.");
field.append("\n Under 'Input value,' enter in a numerical value (yes, can be complex, ");
field.append("\n but you cannot use spaces). ");
field.append("\n When you are done, click, 'Done.' ");
field.append("\n If you want to go back to the main screen, simply click 'Cancel.' ");
field.append("\n If you have entered a new variable in, you can then access it again. ");
field.append("\n By looking for the variable name under the Variables menu. ");
field.append("\n Variables will work on both the main screen and the program screens. ");

		this.add("Center", field);
		// Closes the screen.
		Button ok = new Button("OK");
		ok.addActionListener(new CloseFrameHandler(this));
		this.add("South", ok);
		
	} // end constructor

} // end class HelpConstVars
 