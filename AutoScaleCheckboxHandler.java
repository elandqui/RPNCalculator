// Eric Landquist
// Mike Melton
// CS 2704 Scott Guyer
// AutoScaleCheckboxHandler.java

import java.awt.*;
import java.awt.event.*;

// This class handles the check box for the auto-scaling feature on our grapher.

public class AutoScaleCheckboxHandler implements ItemListener {
    Grapher g;

    public AutoScaleCheckboxHandler( Grapher g ) {
	this.g = g;
    }

    public void itemStateChanged( ItemEvent i ) {
	if ( g.cbAuto.getState() ) {
	    g.tfyMax.setEnabled(false);
	    g.tfyMin.setEnabled(false);
	} else {
	    g.tfyMax.setEnabled(true);
	    g.tfyMin.setEnabled(true);
	}
    }
}