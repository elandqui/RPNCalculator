// Eric Landquist
// MIke Melton
// CS2704 Scott Guyer
// HelpIndexHandler.java

import java.awt.*;
import java.awt.event.*;

// This class will handle the events for the Menu Index.

public class HelpIndexHandler implements ActionListener {

	MenuIndex index;
	Choice selection;
	String choice;

	public HelpIndexHandler (MenuIndex index, Choice selection) {
		this.index = index;
		this.selection = selection;
	}
	
	public void actionPerformed (ActionEvent e) {

		// Running through the possibilities of the selection on the help screen.
		
		choice = selection.getSelectedItem();

		// The choices for the operations, creating and positioning the help screens.
		if (choice.equals("Operations")) {
			HelpOp helpOp = new HelpOp();
			helpOp.setBounds(300, 100, 550, 400);
			helpOp.setVisible(true);
		}	

		else if (choice.equals("Numbers")) {
			HelpNumbers helpNum = new HelpNumbers();
			helpNum.setBounds(300, 100, 550, 400);
			helpNum.setVisible(true);
		}

		else if (choice.equals("Program Screens")) {
			HelpProgScreen helpProg = new HelpProgScreen();
			helpProg.setBounds(300, 100, 550, 400);
			helpProg.setVisible(true);
		}

		else if (choice.equals("RPN Principles")) {
			HelpRPN helpRevPol = new HelpRPN();
			helpRevPol.setBounds(300, 100, 550, 400);
			helpRevPol.setVisible(true);
		}
	
		else if (choice.equals("Constants and Variables")) {
			HelpConstVars helpVars = new HelpConstVars();
			helpVars.setBounds(300, 100, 550, 400);
			helpVars.setVisible(true);
		}

		else if (choice.equals("Graphing")) {
			HelpGraphing helpGraph = new HelpGraphing();
			helpGraph.setBounds(275,100,600,400);
			helpGraph.setVisible(true);
		}

		else 
			System.out.println ("Bad name for choice.");

	} // end actionPerformed

} // end class HelpIndexHandler