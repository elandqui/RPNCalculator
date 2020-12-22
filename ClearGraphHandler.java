// Eric Landquist
// Mike Melton
// CS 2704 Scott Guyer
// ClearGraphHandler.java

import java.awt.*;
import java.awt.event.*;

// This class handles the button to clear the graph on the garaphing screen.

public class ClearGraphHandler implements ActionListener {
    Grapher grapher;
    GraphHandler gh;
    int numGraph;
    Canvas c;
    Graphics g;


    public ClearGraphHandler( Grapher grapher, GraphHandler gh, int numGraph ) {
	this.grapher = grapher;
	this.gh = gh;
	this.numGraph = numGraph;
    }

    public void actionPerformed( ActionEvent e ) {
	c = grapher.c;
	g = c.getGraphics();
	Color color = g.getColor();
	g.setColor( c.getBackground() );

	switch (numGraph) {

	    case(1):
		/*
		 *  The user could change the number of steps between graphs so
		 *  we need to check the Point array up to the maximum possible
		 *  steps, 1000.  We'll catch an ArrayIndexOutOfBoundsException 
		 *  if there are less than 1000 steps in the previous graph and
		 *  stop graphing at that point.
		 */
		for (int n = 0; n < 1000; n++) {
		    try {
			g.drawLine( gh.points1[n].x, gh.points1[n].y, gh.points1[n+1].x, gh.points1[n+1].y );
		    } catch ( ArrayIndexOutOfBoundsException a ) {
			break;
		    }
		}

		gh.points1 = new GraphPoint[gh.numPoints+1];
		gh.graph1 = false;
		grapher.expr1.setText("");
		break;

	    case(2):
		for (int n = 0; n < 1000; n++) {
		    try {
			g.drawLine( gh.points2[n].x, gh.points2[n].y, gh.points2[n+1].x, gh.points2[n+1].y );
		    } catch ( ArrayIndexOutOfBoundsException a ) {
			break;
		    }
		}

		gh.graph2 = false;
		gh.points2 = new GraphPoint[gh.numPoints+1];
		grapher.expr2.setText("");
		break;

	    case(3):
		for (int n = 0; n < 1000; n++) {
		    try {
			g.drawLine( gh.points3[n].x, gh.points3[n].y, gh.points3[n+1].x, gh.points3[n+1].y );
		    } catch ( ArrayIndexOutOfBoundsException a ) {
			break;
		    }
		}

		gh.graph3 = false;
		gh.points3 = new GraphPoint[gh.numPoints+1];
		grapher.expr3.setText("");
		gh.autoScaleMinY = Double.MAX_VALUE;
		gh.autoScaleMaxY = Double.MIN_VALUE;
		break;
	}
    }
}// end ClearGraphHandler