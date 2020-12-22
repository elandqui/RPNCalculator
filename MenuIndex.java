// Eric Landquist
// Mike Melton
// CS 2704 Scott Guyer
// MenuIndex.java

import java.awt.*;
import java.awt.event.*;

// This class will create a help screen which provides a pull down choice bar to
// select the particular topic for help.

public class MenuIndex extends Frame {

	Label select = new Label("Select a help topic");
	// The pull down choice bar.
	Choice topic = new Choice();

	MenuIndex(){
		super("RPNCalculator Help");
		this.setLayout(new GridLayout(0, 1));
		this.add(select);

		// The choices for hte help screens.
		topic.add("Constants and Variables");
		topic.add("Graphing");
		topic.add("Numbers");
		topic.add("Operations");
		topic.add("Program Screens");
		topic.add("RPN Principles");
		this.add(topic);
		// Takes the choice for hte help screen.
		Button ok = new Button("OK");
			ok.addActionListener( new HelpIndexHandler(this, topic));
		// Closes the screen.
		Button cancel = new Button("Cancel");
			cancel.addActionListener(new CloseFrameHandler(this));
		this.add(ok);
		this.add(cancel);
		this.setBounds(300, 200, 300, 150);
		this.setVisible(true);
	}
} // End class MenuIndexFrame 