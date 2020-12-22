// Eric Landquist
// Mike Melton
// CS2704 Scott Guyer
// HelpGraphing.java

import java.awt.*;
import java.awt.event.*;

// This is a help screen for the Grapher.

public class HelpGraphing extends Frame {

	public HelpGraphing () {
		super("Help - Graphing");
		this.setLayout(new BorderLayout());
		TextArea field = new TextArea(100, 100);
		// We want the help screen to be a read-only field.
		field.setEditable(false);
		field.append("		                           HELP - GRAPHING ");
field.append("\n\n This calculator has one-variable graphing capability.  By opening the File menu and choosing");
field.append("\n Grapher, you can open the graphing screen.  There are several user-definable fields on this");
field.append("\n screen, and they are as follows:");
field.append("\n\n Expression:  Type the expression you wish to graph here.  Formatting rules are described");
field.append("\n\tlater.  The curve graphed will be in the color indicated by the label color.");
field.append("\n xMin, xMax, yMin, yMax:  The boundaries between which you wish to graph the expressions.");
field.append("\n Steps:  The number of points you wish to use to graph the expression.  Enter a number between");
field.append("\n\t50 and 1000 here; anything less than 50 will not create a smooth curve, and anything ");
field.append("\n\tgreater than 1000 will create redundant points and slow processing time considerably.");
field.append("\n Autoscale:  Check this box if you wish the grapher to automatically choose your y-scale for you.");
field.append("\n\tThe grapher will calculate the maximum and minimum y-values in the given x-range,");
field.append("\n\tand set the scale accordingly.");
field.append("\n Graph buttons:  Graph the corresponding expression.");
field.append("\n Clear buttons:  Clear the corresponding curve.");
field.append("\n\n\nFORMATTING RULES:");
field.append("\n\n Expressions must be entered in lower case, with no spaces, and using only the variable x.");
field.append("\n You will receive errors on the terminal screen for expressions not entered in such manner.");
field.append("\n You may use the standard mathematical operators:  +, -, *, /, ^, %, e");
field.append("\n The following functions are available for use and must be typed exactly as shown here, where");
field.append("\n any legal expression may be put between the parentheses:");
field.append("\n\tsin()");
field.append("\n\tcos()");
field.append("\n\ttan()");
field.append("\n\tsinh()");
field.append("\n\tcosh()");
field.append("\n\ttanh()");
field.append("\n\tarcsin()");
field.append("\n\tarccos()");
field.append("\n\tarctan()");
field.append("\n\tarcsinh()");
field.append("\n\tarccosh()");
field.append("\n\tarctanh()");
field.append("\n\tln()");
field.append("\n\tlog()");
field.append("\n\tsqrt()");
field.append("\n\tfact()");
field.append("\n\n Examples of legal expressions are as follows:");
field.append("\n\tsin(x)");
field.append("\n\tx^2");
field.append("\n\t(x+3)^2");
field.append("\n\t3^-(x+10)");
field.append("\n\tx");
field.append("\n\t ... and so on.");

		this.add("Center", field);
		// Closes the screen.
		Button ok = new Button("OK");
		ok.addActionListener(new CloseFrameHandler(this));
		this.add("South", ok);
		
	} // end constructor

} // end class HelpGraphing
 