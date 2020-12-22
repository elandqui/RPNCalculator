// Eric Landquist
// Mike Melton
// CS2704
// Grapher.java


/*
 *  Sets up a screen for a single-variable graphing utility.
 */

import java.awt.*;

public class Grapher extends Frame {
    Canvas c = new Canvas();
    Button graph1, graph2, graph3, clear1, clear2, clear3, exit, help;
    TextField expr1 = new TextField(30);
    TextField expr2 = new TextField(30);
    TextField expr3 = new TextField(30);
    TextField tfxMin, tfxMax, tfyMin, tfyMax, tfSteps;
    Checkbox cbAuto;

    public Grapher() {
	super("Grapher");
	graph1 = new Button("Graph");
	graph2 = new Button("Graph");
	graph3 = new Button("Graph");
	clear1 = new Button("Clear");
	clear2 = new Button("Clear");
	clear3 = new Button("Clear");

	Panel top = new Panel();
	top.setLayout( new GridLayout( -1, 1, 0, 0 ) );

	// The input and buttons for the 1st graph.
	Panel p1 = new Panel();
	p1.setLayout( new FlowLayout(FlowLayout.LEFT) );
	Label lExpr1 = new Label("Expression:");
	lExpr1.setForeground(Color.blue);
	p1.add(lExpr1);
	p1.add(expr1);
	p1.add(graph1);
	p1.add(clear1);
	top.add(p1);
	p1.setBackground(Color.gray);

	// The input and buttons for the 2nd graph.
	Panel p2 = new Panel();
	p2.setLayout( new FlowLayout(FlowLayout.LEFT) );
	Label lExpr2 = new Label("Expression:");
	lExpr2.setForeground(Color.red);
	p2.add(lExpr2);
	p2.add(expr2);
	p2.add(graph2);
	p2.add(clear2);
	top.add(p2);
	p2.setBackground(Color.gray);

	// The input and buttons for the 3rd graph.
	Panel p3 = new Panel();
	p3.setLayout( new FlowLayout(FlowLayout.LEFT) );
	Label lExpr3 = new Label("Expression:");
	lExpr3.setForeground(Color.green);
	p3.add(lExpr3);
	p3.add(expr3);
	p3.add(graph3);
	p3.add(clear3);
	top.add(p3);
	p3.setBackground(Color.gray);

	// Panel for the x scaling.
	Panel p4 = new Panel();
	p4.setLayout( new FlowLayout(FlowLayout.LEFT) );
	Label lxMin = new Label("x Min:");
	p4.add(lxMin);
	tfxMin = new TextField("-10", 4);
	p4.add(tfxMin);
	Label lxMax = new Label("x Max:");
	p4.add(lxMax);
	tfxMax = new TextField("10", 4);
	p4.add(tfxMax);
	Label lSteps = new Label("Steps:");
	p4.add(lSteps);
	tfSteps = new TextField("100", 4);
	p4.add(tfSteps);
	top.add(p4);
	p4.setBackground(Color.gray);

	// Panel for the y scales and the auto-scaling button.
	Panel p5 = new Panel();
	p5.setLayout( new FlowLayout(FlowLayout.LEFT) );
	Label lyMin = new Label("y Min:");
	p5.add(lyMin);
	tfyMin = new TextField("-10", 4);
	p5.add(tfyMin);
	Label lyMax = new Label("y Max:");
	p5.add(lyMax);
	tfyMax = new TextField("10", 4);
	p5.add(tfyMax);
	cbAuto = new Checkbox("Autoscale", false);
	cbAuto.addItemListener( new AutoScaleCheckboxHandler( this ) );
	p5.add(cbAuto);
	top.add(p5);
	p5.setBackground(Color.gray);

	// Bottom most panel for the help and exit buttons.
	Panel p6 = new Panel();
	p6.setLayout( new FlowLayout(FlowLayout.RIGHT) );
	help = new Button( "  Help  " );
	help.addActionListener( new MenuEventHandler( 
	    MenuEventHandler.HELP_INDEX, new RPNCalculator() ));
	exit = new Button( "  Exit  " );
	exit.addActionListener( new CloseFrameHandler( this ) );
	p6.add(help);
	p6.add(exit);
	p6.setBackground(Color.gray);
	
	c.setSize(400,400);
	c.setBackground(Color.gray);

	GraphHandler gh1 = new GraphHandler( this, 1 );
	GraphHandler gh2 = new GraphHandler( this, 2 );
	GraphHandler gh3 = new GraphHandler( this, 3 );

		// Action Listeners for the graphing and clearing buttons.
	graph1.addActionListener( gh1 );
	graph2.addActionListener( gh2 );
	graph3.addActionListener( gh3 );
	clear1.addActionListener( new ClearGraphHandler( this, gh1, 1 ) );
	clear2.addActionListener( new ClearGraphHandler( this, gh2, 2 ) );
	clear3.addActionListener( new ClearGraphHandler( this, gh3, 3 ) );

	this.add("North", top);
	this.add("Center", c);
	this.add("South", p6);
	this.pack();
    }

    public static void main( String args[] ) {
	Grapher graph = new Grapher();
	graph.setVisible(true);
    }
	
}