// Eric Landquist
// Mike Melton
// CS 2704
// CloseDialogHandler.java

import java.awt.*;
import java.awt.event.*;


/*
 *  class CloseDialogHandler
 *
 *  Simply closes the passes dialog.
 *
 */

class CloseDialogHandler implements ActionListener {
    Dialog d;

    CloseDialogHandler( Dialog d ) {
	this.d = d;
    }

    public void actionPerformed(ActionEvent e) {
	d.setVisible(false);
 	d.dispose();
    }
}
