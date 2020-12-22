// Eric Landquist
// Mike Melton
// CS 2704 Scott Guyer
// GraphPoint.java

import java.awt.*;

// Stores the point of a graphed point.

public class GraphPoint {
    int x,y;
    double actualX, actualY;

    public GraphPoint( int x, int y, double actualX, double actualY ) {
	this.x = x;
	this.y = y;
	this.actualX = actualX;
	this.actualY = actualY;
    }
}