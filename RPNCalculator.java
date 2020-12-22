// RPNCalculator
// Eric Landquist
// Mike Melton
// CS2704 Scott Guyer

// The main class. This is the actual main 

import java.awt.*;

public class RPNCalculator extends Frame {
	
    Label xData = new Label(); // create the labels to display x,y,z
    Label yData = new Label();
    Label zData = new Label();


	/*
	 *  Create two flags that will control how the stack and the
	 *  display will update.  opFlag2 indicates the most
	 *  recently pressed button; it is false for any digit
	 *  and '.', and true for any other button.  opFlag1
	 *  indicates the button pressed directly before opFlag2.
	 */
    public boolean opFlag1;
    public boolean opFlag2 = true;

    public  boolean deleteable = true;	// Will say if we can delete a whole number or not.

	// The class that stores the complex number stacks.
    public DoubleStack stack = new DoubleStack();

    public StoredVariable[] vars = new StoredVariable[StoredVariable.MAX];
    public int numVars = 0;

	/*
	 *  Set the initial display to 0.0 and set the length to
	 *  be long enough to accomodate for all digits in a double
	 *  precision output
	 */
    public static final String
	ORIGINAL_DISPLAY = "0.0                                                                                            ";
	
	/*
	 *  MenuBar declarations
	 */
    MenuBar	appMenu;
    Menu	file;
    Menu	help;
    Menu	constants;
    Menu	variables;
    MenuItem	newScreen;
    MenuItem	calc;
    MenuItem	grapher;
    MenuItem	prog1;
    MenuItem	prog2;
    MenuItem	prog3;
    MenuItem	prog4;
    MenuItem	prog5;
    MenuItem	quit;
    MenuItem	index;
    MenuItem	about;
    MenuItem	pi;
    MenuItem	constE;
    MenuItem	addVar;


	Panel top = new Panel();
	Panel bottom = new Panel();
	Panel topLeft = new Panel();
	Panel topRight = new Panel();
	Panel topCenter = new Panel();

// An array of program screens to keep track of.

	ProgStack array1 = new ProgStack(this);
	ProgStack array2 = new ProgStack(this);
	ProgStack array3 = new ProgStack(this);
	ProgStack array4 = new ProgStack(this);
	ProgStack array5 = new ProgStack(this);

	ProgStack[] screenArray = new ProgStack[5];

// This will keep track of what screen we are on: 0 for the main calculator, and 1-5 for
// the corresponding program screens.
	public int activeScreen = 0;

    public RPNCalculator() {  // constructor
	super("RPNCalculator v. 2.0");

	screenArray[0] = array1;
	screenArray[1] = array2;
	screenArray[2] = array3;
	screenArray[3] = array4;
	screenArray[4] = array5;

	// Putting the menus together.
	appMenu = new MenuBar();
	file = new Menu("File");
	constants = new Menu("Constants");
	variables = new Menu("Variables");
	help = new Menu("Help");
	newScreen = new MenuItem("New");
	calc = new MenuItem("Main Screen");
	grapher = new MenuItem("Grapher");
	prog1 = new MenuItem("Program Screen F1");
	prog2 = new MenuItem("Program Screen F2");
	prog3 = new MenuItem("Program Screen F3");
	prog4 = new MenuItem("Program Screen F4");
	prog5 = new MenuItem("Program Screen F5");
	quit = new MenuItem("Quit");
	index = new MenuItem("Index");
	about = new MenuItem("About");
	constE = new MenuItem("e");
	pi = new MenuItem("Pi");
	addVar = new MenuItem("Add New Variable");

	// Adding action Listeners.
	newScreen.addActionListener( new MenuEventHandler(
	    MenuEventHandler.FILE_NEW, this ));
	calc.addActionListener(new MenuEventHandler(
	    MenuEventHandler.FILE_CALC, this));
	grapher.addActionListener( new GraphOpener() );
	prog1.addActionListener(new MenuEventHandler(
	    MenuEventHandler.FILE_PROG1, this));
	prog2.addActionListener(new MenuEventHandler(
	    MenuEventHandler.FILE_PROG2, this));
	prog3.addActionListener(new MenuEventHandler(
	    MenuEventHandler.FILE_PROG3, this));
	prog4.addActionListener(new MenuEventHandler(
	    MenuEventHandler.FILE_PROG4, this));
	prog5.addActionListener(new MenuEventHandler(
	    MenuEventHandler.FILE_PROG5, this));
	quit.addActionListener( new MenuEventHandler(
	    MenuEventHandler.FILE_QUIT, this ));
	pi.addActionListener( new MenuEventHandler(
	    MenuEventHandler.CONST_PI, this));
	constE.addActionListener( new MenuEventHandler(
	    MenuEventHandler.CONST_E, this));
	index.addActionListener( new MenuEventHandler( 
	    MenuEventHandler.HELP_INDEX, this));
	about.addActionListener( new MenuEventHandler(
	    MenuEventHandler.HELP_ABOUT, this ));
	addVar.addActionListener( new MenuEventHandler(
	    MenuEventHandler.VAR_ADD, this));

	file.add(newScreen);
	file.insertSeparator(1);
	file.add(calc);
	file.add(grapher);
	file.insertSeparator(4);
	file.add(prog1);
	file.insertSeparator(6);
	file.add(prog2);
	file.insertSeparator(8);
	file.add(prog3);
	file.insertSeparator(10);
	file.add(prog4);
	file.insertSeparator(12);
	file.add(prog5);
	file.insertSeparator(14);
	file.add(quit);
	constants.add(pi);
	constants.insertSeparator(1);
	constants.add(constE);
	variables.add(addVar);
	variables.insertSeparator(1);
	help.add(index);
	help.add(about);
	appMenu.add(file);
	appMenu.add(constants);
	appMenu.add(variables);
	appMenu.add(help);
	appMenu.setHelpMenu(help);
   	
	// Setting the text in the string to create a width 
	// wide enough to hold the longest numbers.
	xData.setText(ORIGINAL_DISPLAY);
	yData.setText(ORIGINAL_DISPLAY);
	zData.setText(ORIGINAL_DISPLAY);
	xData.setAlignment(Label.LEFT);
	yData.setAlignment(Label.LEFT);
	zData.setAlignment(Label.LEFT);

	Color gray = new Color(128,128,128);
	Color white = new Color(255,255,255);
	Color darkGray = new Color(64,64,64);

	this.setBackground(gray);

	KeyPad kp = new KeyPad( this );

// Putting together the screen for the calculations.
	topLeft.setLayout(new GridLayout(0,1,-2,-2));
	topLeft.add(new Label("z:   ", Label.RIGHT));
	topLeft.add(new Label("y:   ", Label.RIGHT));
	topLeft.add(new Label("x:   ", Label.RIGHT));
	topLeft.setBackground(gray);
	topLeft.setForeground(white);

	topRight.setLayout(new GridLayout(0,1,-2,-2));
	topRight.add(zData);
	topRight.add(yData);
	topRight.add(xData);
	topRight.setBackground(white);

	topCenter.setLayout(new BorderLayout());
	topCenter.add("West", topLeft);
	topCenter.add("Center", topRight);
	topCenter.setVisible(true);

// Add in the main calculator screen.

	top.add("Center", topCenter);

	bottom.add(kp);

	this.add("North", top);
	this.add("South", bottom);
	this.pack();
	this.setMenuBar(appMenu);
	this.setSize(340,390);  // set the size to accomodate menu 
 
    }

    public static void main(String args[]) {
	RPNCalculator app = new RPNCalculator();
	app.setVisible(true);
    }

	/*
	 *  updateDisplay()
	 *  
	 *  Checks the opFlags, then updates the x,y,z labels on the
	 *  display to show what they should; returns the number of
	 *  fields updated.
	 *
	 */
    public int updateDisplay() {
	    /*
	     *  If the last button pressed was a digit and the
	     *  button before that was an operator, we want to
	     *  move everything up on the display (ie, x->y, y->z)
	     *  but we can't push the value being typed onto the
	     *  stack yet because the number may be more than
	     *  one digit.
	     */ 
	if (opFlag1 && !opFlag2) {
		/*
		 *  If the next element in the stack is the same
		 *  as this one, it is only there because the user
		 *  pressed enter (as the logic of the program
		 *  dictates) after entering the first element of
		 *  the stack, so we don't want the element in y
		 *  to scroll to z since it isn't actually in the
		 *  stack.
		 */  
	    // if ( !stack.isDup(stack))
	    if ( !stack.dupFlag )
		zData.setText(yData.getText());
	    yData.setText(xData.getText());
	    return 3;
	}

	else {  
		// update the display using what's in the stack.
		// note that if some numbers are complex, then the screen will include
		// the plus signs and the imaginary number i to show that.
		
	    if (stack.SIZE >= 0) {
		Double x = new Double(stack.numStack[stack.SIZE]);
		Double xi = new Double(stack.imagStack[stack.SIZE]);
		if (xi.doubleValue() == 0.0)
			xData.setText(x.toString());
		else if (x.doubleValue() == 0.0)
			xData.setText(xi.toString()+"i");
		else if (xi.doubleValue() > 0.0)
			xData.setText(x.toString()+"+"+xi.toString()+"i");
		else 
			xData.setText(x.toString()+xi.toString()+"i");
	    }

	    if (stack.SIZE - 1 >= 0) {
		Double y = new Double(stack.numStack[stack.SIZE-1]);
		Double yi = new Double(stack.imagStack[stack.SIZE-1]);
		if (yi.doubleValue() == 0.0)
			yData.setText(y.toString());
		else if (yi.doubleValue() > 0.0)
			yData.setText(y.toString()+"+"+yi.toString()+"i");
		else 
			yData.setText(y.toString()+yi.toString()+"i");
	    }
	    else
		return 1;

	    if (stack.SIZE - 2 >= 0) {
		Double z = new Double(stack.numStack[stack.SIZE-2]);
		Double zi = new Double(stack.imagStack[stack.SIZE-2]);
		if (zi.doubleValue() == 0.0 )
			zData.setText(z.toString());
		else if (zi.doubleValue() > 0.0)
			zData.setText(z.toString()+"+"+zi.toString()+"i");
		else 
			zData.setText(z.toString()+zi.toString()+"i");
	    }
	    else
		return 2;
   
	return 3;
   	}
    }


	/*
	 *  updateStack()
	 *
	 *  Fires when an operator button is pressed, before the
	 *  opFlag2 is set to true.  Checks to see if the last
	 *  button pressed was a digit; if it was, the number on the
	 *  display needs to be pushed onto the stack before the
	 *  operation is attempted.
	 *
	 */
    public void updateStack() {
	double[] value = new double[2];
   
	value = stringToComplex(xData.getText());

	if ( !opFlag2 )
	    stack.push(value);
    }


	/*
	 *  outputStack()
	 *
	 *  Fires whenever a non-digit number is pressed.  Outputs
	 *  the elements of the stack to the terminal.
	 *
	 */
    public void outputStack() {
	int n;

	System.out.print("STACK = [ ");
	for (n = 1; n <= stack.SIZE; n++) {

		// If statements for the various kinds of complex numbers:
		// real.
	    if (stack.imagStack[n] == 0.0)	
		System.out.print(stack.numStack[n]);
		// pure imaginary.
	    else if (stack.numStack[n] == 0.0)	
		System.out.print(stack.imagStack[n]+"i");
		// a+bi
	    else if (stack.imagStack[n] > 0.0)
		System.out.print(String.valueOf(stack.numStack[n])+"+"+String.valueOf(stack.imagStack[n])+"i");	
		// a-bi
	    else 
		System.out.print(String.valueOf(stack.numStack[n])+String.valueOf(stack.imagStack[n])+"i");
	   
		// Separating the entries of the printed dtack.
	    if (n != stack.SIZE)
		System.out.print(", ");
	    else {
		// if (stack.isDup(stack)) {
		if (stack.dupFlag) {
		    // All the different cases for the first inputted number. 
		    if (stack.imagStack[n] == 0.0) 
			System.out.print(", " + stack.numStack[n+1]);
		    else if (stack.numStack[n] == 0.0)
			System.out.print(", " + stack.imagStack[n+1]+"i");
		    else if (stack.imagStack[n] > 0.0)
			System.out.print(", " + stack.numStack[n+1]+"+"+stack.imagStack[n+1]+"i");	
		    else if (stack.imagStack[n+1] < 0.0)
			System.out.print(", " + stack.numStack[n+1]+stack.imagStack[n+1]+"i");
		    else
			System.out.println("Bad name for the number.");
		}
	    }
	}
	System.out.println(" ]");
    } // end outputStack()

// stringToComplex()
// This method will take in a string, particularly from the screen of the calculator,
// And will convert it to a complex number is the form of a size 2 double array. The first
// cell being the real part, the second cell being the imaginary part.

    public static double[] stringToComplex(String str) {
	double[] complex = new double[2];
	char[] charHold = new char[str.length()];
	char[] chars = str.toCharArray();
	int length = chars.length;
	double hold;

// This loop looks through the input string and if it gets to a plus sign, it knows it's 
// hit the end of the real part and the beginning of the imaginary part, so the method
// chops of the real part, puts it into complex[0] and chops of the imaginary part to 
// put into complex[1].

	for(int i=0; i<length; i++){
		if( chars[i] == '+') {
			System.arraycopy(chars, 0, charHold, 0, i);
			hold = new Double(new String(charHold, 0, i)).doubleValue();
			complex[0] = hold;
		
			// Clear out charHold.
	
			for (int j=0; j<length; j++)
				charHold[j] = ' ';

			// If the distance between the + and i is zero, then the imaginary 
			// part is just 1.
						
			if (length-2-i == 0) {
				complex[1] = 1.0;
				return complex;
			}

			// If '+' is the last element in the string, then the imaginary part is just 0.0.
			if (i == length-1) {
				complex[1] = 0.0;
				return complex;
			}

			else {
				System.arraycopy(chars, i+1, charHold, 0, length-2-i);
				hold = new Double(new String(charHold, 0, length-2-i)).doubleValue();
				complex[1] = hold;
				return complex;
			}
		} // end if for a '+' appearing.

		// If a minus sign appears in the middle of the string, i.e. for the imaginary part.
		if ((chars[i] == '-') && (i!=0)) {
			System.arraycopy(chars, 0, charHold, 0, i);
			hold = new Double(new String(charHold, 0, i)).doubleValue();
			complex[0] = hold;
		
			// Clear out charHold.
	
			for (int j=0; j<length; j++)
				charHold[j] = ' ';

			// If the distance between the - and i is zero, then the imaginary 
			// part is just -1.
						
			if (length-2-i == 0) {
				complex[1] = -1.0;
				return complex;
			}

			// If '-' is the last element in the string, then the imaginary part is just 0.0.
			if (i == length-1) {
				complex[1] = 0.0;
				return complex;
			}

			else {
				System.arraycopy(chars, i+1, charHold, 0, length-2-i);
				hold = new Double(new String(charHold, 0, length-2-i)).doubleValue();
				complex[1] = hold;
				// Same as above for '+', but just make it negative.
				complex[1] = 0.0 - complex[1];
				return complex;
			}
		} // end if for a '-' appearing.

		// if the number is a pure imaginary number: 
		if (chars[i] == 'i') {
			complex[0] = 0.0;	

// If the only number in the string is i, then the value of it is 1.0i.
			if(i==0) {
				complex[1] = 1.0;
				return complex;
			}
			else {
				System.arraycopy(chars, 0, charHold, 0, i);
				hold = new Double(new String(charHold, 0, i)).doubleValue();
				complex[1] = hold;
				return complex;
			}
		} // end if
	}

	// if the number is a pure real:

	hold = new Double(str).doubleValue();
	complex[0] = hold;
	complex[1] = 0.0;
	return complex;
    } // end stringToComplex

} // end RPNCalculator