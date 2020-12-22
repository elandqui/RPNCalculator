// Eric Landquist and Mike Melton
// CS 2704 Scott Guyer
// CloseFrameHandler.java

// This method will close any frame that is passed into it.

import java.awt.*;
import java.awt.event.*;


/*
 *  class CloseFrameHandler
 *
 *  Simply closes the frame.
 *
 */

class CloseFrameHandler implements ActionListener {
    Frame f;

    CloseFrameHandler( Frame f ) {
	this.f = f;
    }

    public void actionPerformed(ActionEvent e) {
	f.setVisible(false);
 	f.dispose();
    }
}
