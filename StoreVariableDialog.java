// Eric Landquist and Mike Melton
// CS 2704 Scott Guyer
// StoreVariableDialog.java

// This class creates the frame that allows the user to enter in a user defined variable
// and a name for that variable.

import java.awt.*;

class StoreVariableDialog extends Dialog {
	// The fields for the user input.
    TextField name = new TextField( 20 );
    TextField value = new TextField( 20 );

    public StoreVariableDialog( RPNCalculator app ) {
	super( app, "Store New Variable", true );

	Panel p1 = new Panel();
	Panel p2 = new Panel();
	Panel p3 = new Panel();
	// When you are done entering the information
	Button done = new Button( "Done" );
	// If yo want to back out of the screen.
	Button cancel = new Button("Cancel");
	this.setResizable(false);
	this.setLayout( new BorderLayout() );
	p1.add( new Label("Input variable name:  ") );
	p1.add( name );
	p2.add( new Label("        Input value:  " ) );
	p2.add( value );
	p3.add( done );
	p3.add( cancel );
	this.add( "North", p1 );
	this.add( "Center", p2 );
	this.add( "South", p3 );
	done.addActionListener( new InputVariableHandler( app, this ) );
	cancel.addActionListener( new CloseDialogHandler( this ) );
	this.pack();
	this.setVisible(true);
    }
} // end StoreVariableDialog