// Eric Landquist
// Mike Melton
// CS2704
// GraphHandler.java


import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

public class GraphHandler implements ActionListener {

    Grapher grapher;
    Canvas c;
    Dimension d;
    Graphics g;
    double prevX, prevY, currX, currY,  	// actual values of the function
	   xScale, yScale,			// scale of value-to-pixel
	   xSteps,				// size of steps between points
    	   xMax, xMin, yMax, yMin;		// boundaries of the graph
    static double autoScaleMinY = Double.MAX_VALUE;
    static double autoScaleMaxY = Double.MIN_VALUE;
    int	numPoints,				// number of points user wants graphed
	scrWidth,				// width of graphing area
	x1, x2, y1, y2,				// points to graph in pixels
	graphNum;				// which expression is being graphed
    TextField tf;
    String expr;
    boolean error, autoScale;
    static boolean graph1 = false;
    static boolean graph2 = false;
    static boolean graph3 = false;
    static GraphPoint[] points1;	// stores for the points of the graphs
    static GraphPoint[] points2;
    static GraphPoint[] points3;
    static boolean got1 = false;
    static boolean got2 = false;
    static boolean got3 = false;
    static boolean gotauto1 = false;
    static boolean gotauto2 = false;
    static boolean gotauto3 = false;

    public GraphHandler( Grapher g, int graphNum ) {
	this.grapher = g;
	this.graphNum = graphNum;
    }

    public void actionPerformed(ActionEvent e) {
	c = grapher.c;
	d = c.getSize();
	g = c.getGraphics();

	    /*
	     *  Depending on the number of steps the user chose, the graph could
	     *  take a while to generate, so we want to let the user know we're
	     *  actually doing something.
	     */
	System.out.print("Please wait....     ");

	error = false;

	getPoints( c, g, graphNum );
	
	switch (graphNum) {

	    case (1):

		// redraw graph 2 if needed
		if (graph2) {
		    g.setColor( Color.red );
		    for (int n = 0; n < 1000; n++) {
			try {
			    g.drawLine( points2[n].x, points2[n].y, points2[n+1].x, points2[n+1].y );
			} catch ( ArrayIndexOutOfBoundsException a ) {
			    break;
			}
		    }
		}

		// redraw graph 3 if needed
		if (graph3) {
		    g.setColor( Color.green );
		    for (int n = 0; n < 1000; n++) {
			try {
			    g.drawLine( points3[n].x, points3[n].y, points3[n+1].x, points3[n+1].y );
			} catch ( ArrayIndexOutOfBoundsException a ) {
			    break;
			}
		    }
		}
		g.setColor(Color.blue);
		break;

	    case (2):

		// redraw graph 1 if needed
		if (graph1) {
		    g.setColor( Color.blue );
		    for (int n = 0; n < 1000; n++) {
			try {
			    g.drawLine( points1[n].x, points1[n].y, points1[n+1].x, points1[n+1].y );
			} catch ( ArrayIndexOutOfBoundsException a ) {
			    break;
			}
		    }
		}

		// redraw graph 3 if needed
		if (graph3) {
		    g.setColor( Color.green );
		    for (int n = 0; n < 1000; n++) {
			try {
			    g.drawLine( points3[n].x, points3[n].y, points3[n+1].x, points3[n+1].y );
			} catch ( ArrayIndexOutOfBoundsException a ) {
			    break;
			}
		    }
		}

		g.setColor(Color.red);
		break;

	    case (3):

		// redraw graph 2 if needed
		if (graph2) {
		    g.setColor( Color.red );
		    for (int n = 0; n < 1000; n++) {
			try {
			    g.drawLine( points2[n].x, points2[n].y, points2[n+1].x, points2[n+1].y );
			} catch ( ArrayIndexOutOfBoundsException a ) {
			    break;
			}
		    }
		}

		// redraw graph 1 if needed
		if (graph1) {
		    g.setColor( Color.blue );
		    for (int n = 0; n < 1000; n++) {
			try {
			    g.drawLine( points1[n].x, points1[n].y, points1[n+1].x, points1[n+1].y );
			} catch ( ArrayIndexOutOfBoundsException a ) {
			    break;
			}
		    }
		}

		g.setColor(Color.green);
		break;
	}
	
	    /*
	     *  Now draw the graph in the appropriate color for the expression number.
	     */
	switch (graphNum) {

	    case (1):
		g.setColor(Color.blue);
		for (int n = 1; n <= numPoints; n++) {
		    g.drawLine( points1[n-1].x, points1[n-1].y, points1[n].x, points1[n].y );
		}
		graph1 = true;
		break;

	    case (2):
		g.setColor(Color.red);
		for (int n = 1; n <= numPoints; n++) {
		    g.drawLine( points2[n-1].x, points2[n-1].y, points2[n].x, points2[n].y );
		}
		graph2 = true;
		break;

	    case (3):
		g.setColor(Color.green);
		for (int n = 1; n <= numPoints; n++) {
		    g.drawLine( points3[n-1].x, points3[n-1].y, points3[n].x, points3[n].y );
		}
		graph3 = true;
		break;
	}

	System.out.println("Okay.");
	
    }

	/*
	 *  method EvalPoint
	 *
	 *  Finds the postfix notation of the expression entered and evaluates it using
	 *  a stack.  Returns the result for this particular value of x.
	 *
	 */

    public double EvalPoint( double x, int graphNum ) {
	ProgStack stack = new ProgStack();
	char c;
	int n = 0;
	StringBuffer sb;

	switch (graphNum) {
	    case (1):
		expr = grapher.expr1.getText();
		break;

	    case (2):
		expr = grapher.expr2.getText();
		break;

	    case (3):
		expr = grapher.expr3.getText();
		break;
	}

	if ( (expr.trim()).length() < 1 ) {
	    System.out.println("Please enter an expression before trying to graph it.");
	    error = true;
	    return 0;
	}

	Parser p = new Parser( expr, x );
	String postfix = p.InfixToPostfix();

	if ( postfix.compareTo("ERROR") == 0 ) {
		error = true;
		return 0;
	}

	do {
	    sb = new StringBuffer();

	    do {
		c = postfix.charAt(n);
		sb.append(c);
		n++;
	    } while (postfix.charAt(n) != ' ' && postfix.charAt(n) != ';');

	    if ( sb.toString().compareTo(" ") != 0 )
		stack.push( (sb.toString()).trim() );
	    
	} while (postfix.charAt(n) != ';');

	EvalStack eval = new EvalStack( stack, true );
	eval.evaluate( stack );

	return eval.evalStack[0];
    }


	/*
	 *  Draws the axes on the canvas according to the boundaries that the user set.
	 */

    public void drawAxes( Canvas c, Graphics g, double xMin, double xMax, double yMin, double yMax ) {
	double xSize,ySize,percent;
	int xAxis,yAxis;

	g.clearRect(0,0,d.width,d.height);

	g.setColor(Color.black);

	xSize = xMax - xMin;
	ySize = yMax - yMin;

	d = c.getSize();

	if (xMin < 0 && xMax > 0) {
	    percent = -xMin / xSize;
	    xAxis = (int)(d.width * percent);
	    g.drawLine(xAxis, 0, xAxis, d.height);
	}

	if (yMin < 0 && yMax > 0) {
	    percent = -yMin / ySize;
	    yAxis = (int)(d.height - d.height * percent);
	    g.drawLine(0, yAxis, d.width, yAxis);
	}

	    // draw borders
	g.drawLine(1,1,1,d.height-1);
	g.drawLine(1,1,d.width-1,1);
	g.drawLine(1,d.height-1,d.width-1,d.height-1);
	g.drawLine(d.width-1,1,d.width-1,d.height-1);	

    }

    public void getPoints( Canvas c, Graphics g, int graphNum) {
	double xScale, yScale, xSteps;

	xMax = new Double( (grapher.tfxMax).getText() ).doubleValue();
	xMin = new Double( (grapher.tfxMin).getText() ).doubleValue();
	yMax = new Double( (grapher.tfyMax).getText() ).doubleValue();
	yMin = new Double( (grapher.tfyMin).getText() ).doubleValue();
	numPoints = new Integer( (grapher.tfSteps).getText() ).intValue();
	autoScale = grapher.cbAuto.getState();

	xSteps = ((double)(xMax-xMin)/(double)numPoints);
	xScale = ((double)(xMax-xMin)/(double)d.width);
	yScale = ((double)(yMax-yMin)/(double)d.height);

	if (xMin >= xMax) {
	    System.out.println("ERROR:  Maximum x value must be greater than the minimum.");
	    return;
	}

	if (yMin >= yMax) {
	    System.out.println("ERROR:  Maximum y value must be greater than the minimum.");
	    return;
	}

	    /*
	     *  Anything less than 50 points is not smooth but anything above
	     *  1000 is wasted processing time since the points have to be
	     *  converted to integer before they can be drawn.
	     */
	if (numPoints < 50 || numPoints > 1000) {
	    System.out.println("ERROR:  Enter between 50 and 1000 steps.");
	    return;
	}

	switch (graphNum) {
	    case (1):
		points1 = new GraphPoint[numPoints+1];
		autoScaleMinY = Double.MAX_VALUE;
		autoScaleMaxY = Double.MIN_VALUE;
		got1 = true;
		if (graph2 && !got2) {
		    getPoints( c, g, 2 );
		}
		if (graph3 && !got3) {
		    getPoints( c, g, 3 );
		}
		got1 = false;
		break;
	    case (2):
		points2 = new GraphPoint[numPoints+1];
		autoScaleMinY = Double.MAX_VALUE;
		autoScaleMaxY = Double.MIN_VALUE;
		got2 = true;
		if (graph1 && !got1) {
		    getPoints( c, g, 1 );
		}
		if (graph3 && !got3) {
		    getPoints( c, g, 3 );
		}
		got2 = false;
		break;
	    case (3):
		points3 = new GraphPoint[numPoints+1];
		autoScaleMinY = Double.MAX_VALUE;
		autoScaleMaxY = Double.MIN_VALUE;
		got3 = true;
		if (graph2 && !got2) {
		    getPoints( c, g, 2 );
		}
		if (graph1 && !got1) {
		    getPoints( c, g, 1 );
		}
		got3 = false;
		break;
	}

	    /*
	     *  Populate the GraphPoint array with all the points for this graph.
	     */
	for (int n = 0; n <= numPoints; n++) {
	    if (error) return;

	    if (n == 0) {
		prevX = currX = xMin;
		prevY = currY = EvalPoint( currX, graphNum );
	    }
	    else {
		prevX = currX;
	        currX = xMin + n * xSteps;
		prevY = currY;
		currY = EvalPoint( currX, graphNum );
	    }

	    if (prevX < 0)
		x1 = new Double((prevX - xMin) / xScale).intValue();
	    else {
		x1 = new Double((-xMin / (xMax-xMin))*d.width + prevX / xScale).intValue();
	    }

	    if (currX < 0)
	 	x2 = new Double((currX - xMin) / xScale).intValue();
	    else
		x2 = new Double((-xMin / (xMax-xMin))*d.width + currX / xScale).intValue();

	    if (prevY < 0)
		y1 = new Double(d.height * yMax / (yMax-yMin) + (-currY / yScale)).intValue();
	    else
		y1 = new Double((yMax - prevY) / yScale).intValue();

	    if (currY < 0)
		y2 = new Double(d.height * yMax / (yMax-yMin) + (-currY / yScale)).intValue();
	    else
		y2 = new Double((yMax - currY) / yScale).intValue();

	    switch (graphNum) {

		case (1):
		    points1[n] = new GraphPoint(x2, y2, currX, currY);
		    break;

		case (2):
		    points2[n] = new GraphPoint(x2, y2, currX, currY);
		    break;

		case (3):
		    points3[n] = new GraphPoint(x2, y2, currX, currY);
		    break;
	    }
	}

	if (autoScale) {

	    switch (graphNum) {

		case (1):
		  
		    for (int n = 0; n < 1000; n++) {
			try {
			    if (points1[n].actualY < autoScaleMinY)
				autoScaleMinY = points1[n].actualY;
			    if (points1[n].actualY > autoScaleMaxY)
				autoScaleMaxY = points1[n].actualY;
			} catch ( ArrayIndexOutOfBoundsException a ) {
			    break;
		        }
		    }
		    break;

		case (2):
		    
		    for (int n = 0; n < 1000; n++) {
			try {
			    if (points2[n].actualY < autoScaleMinY)
				autoScaleMinY = points2[n].actualY;
			    if (points2[n].actualY > autoScaleMaxY)
				autoScaleMaxY = points2[n].actualY;
			} catch ( ArrayIndexOutOfBoundsException a ) {
			    break;
		        }
		    }
		    break;

		case (3):
		    
		    for (int n = 0; n < 1000; n++) {
			try {
			    if (points3[n].actualY < autoScaleMinY)
				autoScaleMinY = points3[n].actualY;
			    if (points3[n].actualY > autoScaleMaxY)
				autoScaleMaxY = points3[n].actualY;
			} catch ( ArrayIndexOutOfBoundsException a ) {
			    break;
		        }
		    }
		    break;
	    }

		grapher.tfyMin.setText( new Double(autoScaleMinY).toString() );
		grapher.tfyMax.setText( new Double(autoScaleMaxY).toString() );

		yMin = autoScaleMinY;
		yMax = autoScaleMaxY;

		switch (graphNum) {
		    case (1):
			if (!gotauto1) {
			    gotauto1 = true;
			    getPoints( c, g, 1 );
			}
			gotauto1 = false;
			break;
		    case (2):
			if (!gotauto2) {
			    gotauto2 = true;
			    getPoints( c, g, 2 );
			}
			gotauto2 = false;
			break;
		    case (3):
			if (!gotauto3) {
			    gotauto3 = true;
			    getPoints( c, g, 3 );
			}
			gotauto3 = false;
			break;
		}		

	}

	drawAxes( c, g, xMin, xMax, yMin, yMax );

    }
}










