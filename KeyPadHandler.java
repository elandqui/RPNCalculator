// Eric Landquist and Mike Melton 
// CS 2704
// KeyPadHandler.java

// This class will handle all the operation buttons that get 
// pressed on the keypad.

import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import KeyPad;

public class KeyPadHandler implements ActionListener {

    int id;
    static double[] x = new double[2];
    static double[] y = new double[2];		// the top two elements on the stack
    Label xNum, yNum, zNum;	// the display
    String xTemp, yTemp, zTemp; // temporary strings
				// converted from a double
    DoubleStack values;
    RPNCalculator app;
    ProgStack[] screenArray;
    List listBox;
    boolean hold;	// Used in a case of a double
			// entry on ititialization.

    public static final double[] I = { 0.0, 1.0 };	// The imaginary number i
    public static final double[] INEG = {0.0, -1.0};	// -I
    public static final double[] ONE = {1.0, 0.0 };	// 1

    public double[] realCopy = new double[values.MAX];	// Copies of the complex number stacks.
    public double[] imagCopy = new double[values.MAX];
  
    // The various possibilities for the button presses:

    public KeyPadHandler (RPNCalculator app, int operation){
	this.id = operation;
	this.app = app;
	this.screenArray = app.screenArray;
	this.values = app.stack;
    }

    // The method that actually does the event handling.

    public void actionPerformed(ActionEvent e) {
        double[] temp = new double[2]; 	// A temporary complex number.
	this.xNum = app.xData;
	this.yNum = app.yData;
	this.zNum = app.zData;

	if(app.activeScreen > 0)
		this.listBox = screenArray[app.activeScreen-1].listBox;

	app.updateStack();

	app.opFlag1 = app.opFlag2;
	app.opFlag2 = true;

	hold = values.dupFlag;	// was isDup(values); 
// If we are on the main screen, operations are run when a button on the 
// KeyPad is pressed.
	if (app.activeScreen == 0) {

// This switch runs through all the binary operations.
// For all the cases, the top element gets popped, and is checked
// to make sure that there is a value in the stack. If there are no
// elements, nothing happens, and the stack goes back to the way
// it was.
	switch (id) {
	    case KeyPad.EXP:
		x = values.pop();

// Here and in almost every other case, we are testing the output to make sure the output
// is a number and is not infinity. If so, we spit out an error message and keep the screen
// the way it was before the operation was pressed.

		if( !isCool(exp(x))){
			values.push(x);
			System.out.println("Sorry, dude, out of our range. Try something else.");
			break;
  		}
		temp = exp(x);
		values.push(temp);
		break;

	    case KeyPad.RECIPROCAL:
		x = values.pop();

		if(isZero(x)) {
		    System.out.println("Thou shalt not divideth by zero.");
		    if( isZero(x)){
			if (values.SIZE > 0)
				values.SIZE--;
		    }
		    values.push(x);
		    break;
		}

// Actually finding the reciprocal.

		if( !isCool(reciprocal(x))){
			values.push(x);
			System.out.println("Sorry, dude, out of our range. Try something else.");
			break;
  		}
		temp = reciprocal(x);
		values.push(temp);
		break;
	
// squaring a complex number.

	    case KeyPad.SQUARE:
		x = values.pop();

		if( !isCool(square(x))){
			values.push(x);
			System.out.println("Sorry, dude, out of our range. Try something else.");
			break;
  		}
		temp = square(x);
		values.push(temp);
		break;

// Taking the square root.

	    case KeyPad.SQRT:
		x = values.pop();

		if( !isCool(sqrt(x))){
			values.push(x);
			System.out.println("Sorry, dude, out of our range. Try something else.");
			break;
  		}
		else
	   	    temp = sqrt(x);
		    values.push(temp);
		break;

// Switches the sign of the complex number.

	    case KeyPad.SIGN:
		x = values.pop();

// If a number is zero, -0.0 will do nothing.

		if (isZero(x)) {
		    values.push(x);
		    break;
		}
		temp = subtract(makeComplex(0.0, 0.0),x);
		values.push(temp);
		break;	

// Switches the sign of the imaginary part of a complex number.

	    case KeyPad.COMCONJ:
		x = values.pop();
		
// If a number is zero, -0.0i will do nothing.

		if (x[1] == 0.0) {
		    values.push(x);
		    break;
		}
		temp[0] = x[0];
		temp[1] = 0 - x[1];
		values.push(temp);
		break;	

// The natural log. (log base e)

	    case KeyPad.LN:
		x = values.pop();

// Can't take the log of zero.
	
		if(isZero(x)){
			values.push(x);
			//values.SIZE--;
			System.out.println("Zero is not in the domain of ln(x).");
			break;
  		}

		else {

		    if (isZero(ln(x))) {
			if (values.SIZE > 0)
				values.SIZE--;
		    }
		    if (!isCool(ln(x))) {
			System.out.println("Sorry, dude, out of our range. Try something else.");
			values.push(x);
		    }
		}	

		temp = ln(x);
	        values.push(temp);
		break;

// Base ten log.

	    case KeyPad.LOG:
		x = values.pop();

		if(isZero(x)){
			values.push(x);
			//values.SIZE--;
			System.out.println("Zero is not in the domain of log(x).");
			break;
  		}

		else {
		    if (isZero(log(x))) {
			if (values.SIZE > 0)
				values.SIZE--;
		    }
		    if (!isCool(temp)) {
			System.out.println("Sorry, dude, out of our range. Try something else.");
			values.push(x);
		    }
		}	
		temp = log(x);
	        values.push(temp);
		break;

// Finding 10^x.

	    case KeyPad.TENEXP:
		x = values.pop();

		if( !isCool(tenexp(x))){
			values.push(x);
			System.out.println("Sorry, dude, out of our range. Try something else.");
			break;
  		}
		temp = tenexp(x);
		values.push(temp);
		break;

// Finding x! = 1*2*3*...*(x-1)*x.
// The factorial function will convert all doubles to ints before finding the factorial.
		
	    case KeyPad.FACTORIAL:
		x = values.pop();

		if( isImaginary(x)){
			values.push(x);
			System.out.println("Sorry, dude, out of our range. Try something else.");
			break;
		    }
		if (!isCool(factorial(x))){
			values.push(x);
			System.out.println("Sorry, dude, out of our range. Try something else.");			
			break;
		}	
		temp = factorial(x);
		values.push(temp);
		break;

//  Adding.

	    case KeyPad.ADD:
		x = values.pop();
		y = values.pop();

// Here and in almost every other case, we are testing the output to make sure the output
// is a number and is not infinity. If so, we spit out an error message and keep the screen
// the way it was before the operation was pressed.

		if( !isCool(add(y,x))){
			values.push(y);
			values.push(x);
			System.out.println("Sorry, dude, out of our range. Try something else.");
			break;
		}
		temp = add(y,x);		
		values.push(temp);
		break;

// Subtracting.

	    case KeyPad.SUBTRACT:
		x = values.pop();
		y = values.pop();

		if(!isCool(subtract(y,x))){
			values.push(y);
			values.push(x);
			System.out.println("Sorry, dude, out of our range. Try something else.");
			break;
		}
		temp = subtract(y,x);
		values.push (temp);
		break;

// Multplying.

	    case KeyPad.MULTIPLY:
		x = values.pop();
		y = values.pop();

		if( !isCool(multiply(y,x))){
			values.push(y);
			values.push(x);
			System.out.println("Sorry, dude, out of our range. Try something else.");
			break;
		}
		temp = multiply(y,x);
		values.push (temp);
		break;

// Dividing.

	    case KeyPad.DIVIDE:
		x = values.pop();
		y = values.pop();

		if( !isCool(divide(y,x))){
			values.push(y);
			values.push(x);

			System.out.println("Sorry, dude, out of our range. Try something else.");
			break;
		}

		temp = divide(y,x);
		values.push (temp);
		break;

// Finding y^x.

	    case KeyPad.POWER:
		x = values.pop();
		y = values.pop();

		if( !isCool(power(y,x))){
			values.push(y);
			values.push(x);
			System.out.println("Sorry, dude, out of our range. Try something else.");
			break;
		}

		temp = power(y,x);
		values.push(temp);
		break;

// Finding y%x, the remainder when y is divided by x.

	    case KeyPad.MOD:
		x = values.pop();
		if(isZero(x) || isImaginary(x)){
		    values.push(x);
		    break;
		}

		y = values.pop();
		if(isImaginary(y)) {
			values.push(y);
			break;
		}

		if( !isCool(mod(y,x))){
			values.push(y);
			values.push(x);
			System.out.println("Sorry, dude, out of our range. Try something else.");
			break;
		}

		temp = mod(y,x);
		values.push(temp);
		break;

// Finds yPx, the number of permutations of y in x.

	    case KeyPad.PERM:
// Definition of a combinatorial permutation: yPx = y!/(y-x)!
// Can't have negative factorials.
		x = values.pop();
		y = values.pop();

		if ( y[0] < x[0] ){	
		    values.push(y);
		    values.push(x);
		    System.out.println("Error: Negative factorial.");
		    break;
		}
		else if (isImaginary(x) || isImaginary(y)){
			values.push(y);
			values.push(x);
			System.out.println("can't take the factorial of a complex number.");
		}

		else {
		    if( !isCool(perm(y,x))){
			values.push(y);
			values.push(x);
			System.out.println("Sorry, dude, out of our range. Try something else.");
			break;
  		    }

		    temp = perm(y,x);
		    values.push(temp);
		    break;
		}

// Combinations: yCx, y choose x.

	    case KeyPad.COMB:
// Definition of a combination: yCx = y!/((x!)(y-x)!).
// Can't have negative factorials.
		x = values.pop();
		y = values.pop();

		if ( y[0] < x[0] ){
		    values.push(y);
		    values.push(x);
		    System.out.println("Error: Negative factorial.");
		    break;
		}
		else if (isImaginary(x) || isImaginary(y)){
			values.push(y);
			values.push(x);
			System.out.println("can't take the factorial of a complex number.");
		}
		else {
		    if( !isCool(comb(y,x))){
			values.push(y);
			values.push(x);
			System.out.println("Sorry, dude, out of our range. Try something else.");
			break;
  		    }	

   		    temp = comb(y,x);
		    values.push(temp);		
		    break;
		}

// Store the top element in the stack to the memory.
	    case KeyPad.STORE:
		values.store(makeComplex(values.numStack[values.SIZE], values.imagStack[values.SIZE]));
		break;

// Recalling the elements, and writing the values to the
// screen labels.	
	    case KeyPad.RECALL:
		values.push(values.recall());
		app.updateDisplay();
		break;

// delete the last typed digit
	    case KeyPad.DELETE:
		double[] valueHold = new double[2];
		StringBuffer sb = new StringBuffer(xNum.getText());
			// If you are just putting a number in, digit by digit, you do
			// not want to delete the whole line, just one digit at a time.
		    if(!app.deleteable) {
			// If the length is greater than zero and the number in the field is not 0.0.
			// If the length of the string is zero, then deleting does nothing.
		    if (sb.length() > 0 &&
			    !xNum.getText().equals("0.0")) {
			// Set the last digit to a space and chop off the last piece.
			sb.setCharAt(sb.length()-1, ' ');
			sb.setLength(sb.length()-1);

			// Now if there is still a number left and there is a decimal point left,
			// then chop that off too.

			if (sb.length() > 0) {
			    if (sb.charAt(sb.length()-1) == '.') {
				sb.setCharAt(sb.length()-1, ' ');
				sb.setLength(sb.length()-1);
			    }
			}

			// Set the string to the label on the screen

			xNum.setText(sb.toString());

			// Now if we are left with an empty string, put zero into the stack and 				
			// update the display.

			if (xNum.getText().equals("")) {
			    values.numStack[values.SIZE] = 0.0;
			    values.imagStack[values.SIZE] = 0.0;
			    app.updateDisplay();
			    values.SIZE--;
		    	}
	
			// Otherwise we take whatever is left over and put that into the stack.
			else {
			    valueHold = app.stringToComplex(xNum.getText());
			    values.numStack[values.SIZE] = valueHold[0];
			    values.imagStack[values.SIZE] = valueHold[1];	
			}
		    }
		    }
			// Otherwise, if an operation was just pushed, delete the entire line.
		    else {
			values.pop();
			app.updateDisplay();
		   	app.outputStack();
		    }			
		    break;

	    case KeyPad.ROLL:
		x[0] = values.numStack[1];
		x[1] = values.imagStack[1];
		
		// This loop puts the item at the top of the
		// stack on the bottom, if the stack is not empty.
		for(int i=1; i<values.SIZE; i++) {
		    values.numStack[i] = values.numStack[i+1];
		    values.imagStack[i] = values.imagStack[i+1];
		}
		
		values.numStack[values.SIZE] = x[0];
		values.imagStack[values.SIZE] = x[1];
				
		// Write the elements in the stack to the labels.
		app.updateDisplay();
		break;	

	    case KeyPad.SWITCH:
// If there is less than one element in the stack,then switching it will
// only keep it the same.
		if( values.SIZE <= 1){
		    break;
		}
		else {
		    x = values.pop();
		    y = values.pop();


// Switching the top two elements in the stack.
		    values.push(x);
		    values.push(y);			

// And writing them to the screen labels.
		    app.updateDisplay();			
		    break;
		}

// Clearing the stack.

	    case KeyPad.CLSTACK:
		clearStack(app);			
		    break;

// Finding the sine of an angle/complex number.

	    case KeyPad.SIN:
		x = values.pop();
		if (!isCool(sin(x))){
		    System.out.println("Entered value not in range of function.");
		    values.push(x);
		    break;
		}
		temp = sin(x);
		values.push(temp);
		break;

// The cosine of an angle/complex number.

	    case KeyPad.COS:
		x = values.pop();
		if (!isCool(cos(x))){
		    System.out.println("Entered value not in range of function.");
		    values.push(x);
		    break;
		}
		temp = cos(x);
		values.push(temp);
		break;

// The tangent of an angle/complex number.

	    case KeyPad.TAN:
		x = values.pop();

		if( !isCool(tan(x))){
		    System.out.println("Entered value not in range of function.");
		    values.push(x);
		    break;
		}
		temp = tan(x);
		values.push(temp);
		break;

// The arcsine of a number.

	    case KeyPad.ARCSIN:
		x = values.pop();
		if (!isCool(asin(x))){
		    System.out.println("Entered value not in range of function.");
		    values.push(x);
		    break;
		}
		temp = asin(x);
		values.push(temp);
		break;

// The arccosine of a number

	    case KeyPad.ARCCOS:
		x = values.pop();
		if (!isCool(acos(x))){
		    System.out.println("Entered value not in range of function.");
		    values.push(x);
		    break;
		}
		temp = acos(x);
		values.push(temp);
		break;

// The arctangent of a number.

	    case KeyPad.ARCTAN:
		x = values.pop();
		
// Cannot find arctan(i) or arctan(-i)
		if ((x[0] == 0.0) && ((x[1] == 1.0) || (x[1] == -1.0))){
			values.push(x);
			System.out.println("Cannot find arctan("+x[1]+"i).");
			break;
		}

		if (!isCool(atan(x))){
		    System.out.println("Entered value not in range of function.");
		    values.push(x);
		    break;
		}

		temp = atan(x);
		values.push(temp);
		break;

// The hyberbolic sine of a number.

	    case KeyPad.SINH:
		x = values.pop();
		temp = sinh(x);
		if (!isCool(temp)){
		    System.out.println("Entered value not in range of function.");
		    values.push(x);
		    break;
		}
		values.push(temp);
		break;

// The hyberbolic cosine of a number.

	    case KeyPad.COSH:
		x = values.pop();
		temp = cosh(x);
		if (!isCool(temp)){
		    System.out.println("Entered value not in range of function.");
		    values.push(x);
		    break;
		}
		values.push(temp);
		break;

// The hyberbolic tangent of a number.

	    case KeyPad.TANH:
		x = values.pop();
		temp = tanh(x);
		if (!isCool(temp)){
		    System.out.println("Entered value not in range of function.");
		    values.push(x);
		    break;
		}
		values.push(temp);
		break;

// The inverse hyperbolic sine.

	    case KeyPad.ASINH:
		x = values.pop();
		temp = asinh(x);
		if (!isCool(temp)){
		    System.out.println("Entered value not in range of function.");
		    values.push(x);
		    break;
		}
		values.push(temp);
		break;

// The inverse hyperbolic cosine.

	    case KeyPad.ACOSH:
		x = values.pop();
		temp = acosh(x);
		if (!isCool(temp)){
		    System.out.println("Entered value not in range of function.");
		    values.push(x);
		    break;
		}
		values.push(temp);
		break;

// Finds the inverse hyperbolic tangent of a complex number.

	    case KeyPad.ATANH:
		x = values.pop();

// Cannot find arctanh(-1) or arctanh(1)
		if ( (x[1] == 0.0) && ((x[0] == -1.0) || (x[0] == 1.0))) {
			values.push(x);
			System.out.println("Cannot find arctanh("+x[0]+").");
			break;
		}
		temp = atanh(x);
		if (!isCool(temp)){
		    System.out.println("Entered value not in range of function.");
		    values.push(x);
		    break;
		}
		values.push(temp);
		break;

// Adding in cases for the programmable buttons: F1 - F5.
	
	    case KeyPad.F1:
		values.push(progEval(screenArray[0]));
		break;

	    case KeyPad.F2:
		values.push(progEval(screenArray[1]));
		break;

	    case KeyPad.F3:
		values.push(progEval(screenArray[2]));
		break;

	    case KeyPad.F4:
		values.push(progEval(screenArray[3]));
		break;

	    case KeyPad.F5:
		values.push(progEval(screenArray[4]));
		break;
		
	    default:
		    System.out.println("Crap, I didn't think of that one!");
		} // End switch
	} // end if

// If howeverwe are on an alternate screen: one of the program 
// screens, then we push operations onto the operation stack.
	else {
			// For an exponential: e^x.
			// For this case and all cases below it we will put the given string in
			// a selected position. If there is no selected position, the string will
			// be inserted at the end of the list.
			if (id == KeyPad.EXP) {
				if ((app.activeScreen>=1) && (app.activeScreen<=5)) 
					listBox.add("e^", listBox.getSelectedIndex());
				else 	
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.RECIPROCAL) {     
				if ((app.activeScreen>=1) && (app.activeScreen<=5))						
					listBox.add("^-1", listBox.getSelectedIndex());										
				else 			
					System.out.println ("Bad name for program screen.");			
                        }           
                        if (id == KeyPad.SQUARE) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("^2", listBox.getSelectedIndex());				
				else 				
					System.out.println ("Bad name for program screen.");				
                        }           
                        if (id == KeyPad.SQRT) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))					
					listBox.add("sqrt", listBox.getSelectedIndex());				
				else
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.SIGN) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("-x", listBox.getSelectedIndex());				
				else 												
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.COMCONJ) { 
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("-I", listBox.getSelectedIndex());				
				else 												
					System.out.println ("Bad name for program screen.");			
                        }
                        if (id == KeyPad.LN) { 
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("ln", listBox.getSelectedIndex());				
				else 			
					System.out.println ("Bad name for program screen.");			
                        }
                        if (id == KeyPad.LOG) { 
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("log", listBox.getSelectedIndex());			
				else 			
					System.out.println ("Bad name for program screen.");			
                        } 
                        if (id == KeyPad.TENEXP) { 
				if((app.activeScreen>=1)&&(app.activeScreen<=5))					
					listBox.add("10^", listBox.getSelectedIndex());				
				else 			
					System.out.println ("Bad name for program screen.");			
                        }
                        if (id == KeyPad.FACTORIAL) { 
				if((app.activeScreen>=1)&&(app.activeScreen<=5))					
					listBox.add("!", listBox.getSelectedIndex());				
				else 			
					System.out.println ("Bad name for program screen.");			
                        }
                        if (id == KeyPad.ADD) {         
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("+", listBox.getSelectedIndex());				
				else 				
					System.out.println ("Bad name for program screen.");	
                        }
                        if (id == KeyPad.SUBTRACT) {                                    
				if((app.activeScreen>=1)&&(app.activeScreen<=5))					
					listBox.add("-", listBox.getSelectedIndex());			
				else 	
					System.out.println ("Bad name for program screen.");		
                        }
                        if (id == KeyPad.MULTIPLY) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("*", listBox.getSelectedIndex());			
				else 				
					System.out.println ("Bad name for program screen.");	
                        }
                        if (id == KeyPad.DIVIDE) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("/", listBox.getSelectedIndex());			
				else 				
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.POWER) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("^", listBox.getSelectedIndex());			
				else 			
					System.out.println ("Bad name for program screen.");				
                        } 
                        if (id == KeyPad.MOD) { 
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("%", listBox.getSelectedIndex());			
				else 		
					System.out.println ("Bad name for program screen.");			
                        }
                        if (id == KeyPad.PERM) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("y P x", listBox.getSelectedIndex());			
				else 	
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.COMB) {        
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("y C x", listBox.getSelectedIndex());				
				else 	
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.STORE) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))
					listBox.add("STORE", listBox.getSelectedIndex());		
				else 			
					System.out.println ("Bad name for program screen.");			
                        }
                        if (id == KeyPad.RECALL) {              
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("RCL", listBox.getSelectedIndex());				
				else 	
					System.out.println ("Bad name for program screen.");			
                        }
                        if (id == KeyPad.ROLL) { 
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("ROLL", listBox.getSelectedIndex());			
				else 	
					System.out.println ("Bad name for program screen.");			
                        }
                        if (id == KeyPad.SWITCH) { 
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("x<->y", listBox.getSelectedIndex());
				else 	
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.SIN) {         
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("sin", listBox.getSelectedIndex());			
				else 	
					System.out.println ("Bad name for program screen.");			
                        }
                        if (id == KeyPad.COS) {         
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("cos", listBox.getSelectedIndex());					
				else 		
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.TAN) { 
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("tan", listBox.getSelectedIndex());			
				else 	
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.ARCSIN) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("arcsin", listBox.getSelectedIndex());		
				else 	
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.ARCCOS) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("arccos", listBox.getSelectedIndex());			
				else 			
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.ARCTAN) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("arctan", listBox.getSelectedIndex());			
				else 			
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.SINH) { 
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("sinh", listBox.getSelectedIndex());					
				else 				
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.COSH) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("cosh", listBox.getSelectedIndex());				
				else 	
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.TANH) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("tanh", listBox.getSelectedIndex());	
				else
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.ASINH) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("arcsinh", listBox.getSelectedIndex());				
				else
					System.out.println ("Bad name for program screen.");				
                        }
                        if (id == KeyPad.ACOSH) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))						
					listBox.add("arccosh", listBox.getSelectedIndex());			
				else 
					System.out.println ("Bad name for program screen.");			
                        }
                        if (id == KeyPad.ATANH) {
				if((app.activeScreen>=1)&&(app.activeScreen<=5))					
					listBox.add("arctanh", listBox.getSelectedIndex());		
				else 	
					System.out.println ("Bad name for program screen.");				
                        }

// Any operation that is not included here is one that isn't supposed to 
// work on the program screens.
                        else {} 
	} // end else
	
	if(hold && (app.activeScreen == 0)) {	
	    values.numStack[values.SIZE+1] = 0.0;
	    values.imagStack[values.SIZE+1] = 0.0;
	}
	
// Put in the values for the labels from the elements in the stack 
// they correspond with. Except, don't update if a program screen button has been pushed.
	if ((!((id == KeyPad.EF1) || (id == KeyPad.EF2) || (id == KeyPad.EF3) || (id == KeyPad.EF4) ||
		(id == KeyPad.EF5) || (id == KeyPad.DELETE) )) && (app.activeScreen == 0)) {
	    app.updateDisplay();
	    app.outputStack();
	}
	if( id != KeyPad.DELETE )
		app.deleteable = true;
    } // End actionPerformed.

// This method creates a complex number with the double entries real and complex.

	public static double[] makeComplex( double real, double imaginary ) {
		double[] complex = new double[2]; 	// an arbitrary complex number	
		complex[0] = real;
		complex[1] = imaginary;
		return (complex);
	}

// Checks if the complex number is zero: 0 + 0i.

	public static boolean isZero(double[] temp) {
		if ((temp[0] == 0.0) && (temp[1] == 0.0)){
			return true;
		}
		else 
			return false;
	}

// Checks to see if the ouput of the function is valid.
	public static boolean isCool (double[] temp) {
		if( Double.isNaN(temp[0]) || Double.isNaN(temp[1]) || 
		    Double.isInfinite(temp[0]) || Double.isInfinite(temp[1]))
			return false;
		else 
			return true;
	}

// Amazingly, this method will find the natural logarithm of any complex number, except,
// of course, 0+0i. Trust me, it works.

	public static double[] ln(double[] num1){
		// For positive reals:
		if( (num1[0] > 0.0) && (num1[1] == 0.0) ) 
			return (makeComplex(Math.log(num1[0]), 0.0));

		// For negative reals:
		if( (num1[0] < 0.0) && (num1[1] == 0.0) ) 
			return (makeComplex(Math.log(-num1[0]), Math.PI));

		// For positive imaginaries:
		if( (num1[0] == 0.0) && (num1[1] > 0.0) ) 
			return (makeComplex(Math.log(num1[1]), Math.PI/2));

		// For negative imaginaries:
		if( (num1[0] == 0.0) && (num1[1] < 0.0) ) 
			return (makeComplex(Math.log(-num1[1]), -Math.PI/2));

		// For complexes of the form (+/-)a + bi: (a and b are positive reals)
		if(num1[0] > 0.0)  
			return (makeComplex(Math.log(Math.sqrt(num1[0]*num1[0] + num1[1]*num1[1])), Math.atan(num1[1]/num1[0])));

		// For complexes of the form -a + bi:
		if( (num1[0] < 0.0) && (num1[1] > 0.0 )) 
			return (makeComplex(Math.log(Math.sqrt(num1[0]*num1[0] + num1[1]*num1[1])), Math.atan(num1[1]/num1[0]) + Math.PI));

		// For complexes ofthe form a + bi:
		if( (num1[0] < 0.0) && (num1[1] < 0.0) ) 
			return (makeComplex(Math.log(Math.sqrt(num1[0]*num1[0] + num1[1]*num1[1])), Math.atan(num1[1]/num1[0]) - Math.PI));

		// Anything else is just 0+0i:
		else
			return makeComplex(Double.NaN, Double.NaN);
	}

// This method finds the logarithm to base ten.

	public static double[] log(double[] num1){
	    double[] temp = new double[2]; 	// The complex number we are returning.
		temp = ln(num1);
		temp[0] = temp[0]/Math.log(10);
		temp[1] = temp[1]/Math.log(10);
		return temp;
	}

// Finds the sine of an angle/complex number using the identity:
// sin(z) = (e^(zi) - e^(-zi))/(2i)

	public static double[] sin(double[] num1){
	    double[] temp = new double[2]; 	// The complex number we are returning.    
		if (num1[1] == 0.0)
			temp = makeComplex(Math.sin(num1[0]), 0.0);
		else 
			temp = divide(subtract(exp(multiply(num1, I)), exp(multiply(num1, INEG))), makeComplex(0.0, 2.0)); 
		return temp;
	}

// Finds the cosine of an angle/complex number using the identity:
// cos(z) = (e^(zi) + e^(-zi))/(2)

	public static double[] cos(double[] num1){
	    double[] temp = new double[2]; 	// The complex number we are returning.    
		if (num1[1] == 0.0)
			temp = makeComplex(Math.cos(num1[0]), 0.0);
		else 
			temp = divide(add(exp(multiply(num1, I)), exp(multiply(num1, INEG))), makeComplex(2.0, 0.0)); 
		return temp;
	}

// Finds the tangent of an angle/complex number using the identity:
// tan(z) = sin(z)/cos(z)

	public static double[] tan(double[] num1){
	    double[] temp = new double[2]; 	// The complex number we are returning.    
		if (num1[1] == 0.0)
			temp = makeComplex(Math.tan(num1[0]), 0.0);
		else 
			temp = divide(sin(num1), cos(num1)); 
		return temp;
	}

// Finds the hyberbolic sine of an angle/complex number using the identity:
// sinh(z) = sin(iz)/i

	public static double[] sinh(double[] num1){
	    double[] temp = new double[2]; 	// The complex number we are returning.
		temp = divide(sin(multiply(I, num1)), I); 
		return temp;
	}

// Finds the hyperbolic cosine of an angle/complex number using the identity:
// cosh(z) = cos(iz)

	public static double[] cosh(double[] num1){
	    double[] temp = new double[2]; 	// The complex number we are returning.
		temp = cos(multiply(I, num1)); 
		return temp;
	}

// Finds the hyperbolic tangent of an angle/complex number using the identity:
// tanh(z) = sinh(z)/cosh(z)

	public static double[] tanh(double[] num1){
	    double[] temp = new double[2]; 	// The complex number we are returning.
		temp = divide(sinh(num1), cosh(num1)); 
		return temp;
	}

// Find the arcsine using the identity, arcsin(z) = -i*ln(iz+(1 - z^2)^(1/2)).

	public static double[] asin(double[] num1){
	    double[] temp = new double[2]; 	// The complex number we are returning.
		if (num1[1] == 0.0)
			temp = makeComplex(Math.asin(num1[0]), 0.0);
		else 
			temp = multiply(INEG, ln(add(multiply(I, num1), sqrt(subtract(ONE, square(num1))))));
		return temp;
	}

// Find the arccosine using the identity, arccos(z) = -i*ln(z+(z^2 - 1)^(1/2)).

	public static double[] acos(double[] num1){
	    double[] temp = new double[2]; 	// The complex number we are returning.
		if (num1[1] == 0.0)
			temp = makeComplex(Math.acos(num1[0]), 0.0);
		else 
			temp = multiply(INEG, ln(add(num1, sqrt(subtract(square(num1), ONE)))));
		return temp;
	}

// Find the arctangent using the identity, arctan(z) = (i/2)*ln((i+z)/(i-z)).

	public static double[] atan(double[] num1){
	    double[] temp = new double[2]; 	// The complex number we are returning.
		if (num1[1] == 0.0)
			temp = makeComplex(Math.atan(num1[0]), 0.0);
		else 
			temp = multiply(divide(I, makeComplex(2.0, 0.0)), ln(divide(add(I, num1),subtract(I, num1))));
		return temp;
	}

// Find the inverse hyperbolic sine using the identity, arcsinh(z) = -i*arcsin(iz).

	public static double[] asinh(double[] num1){
	    double[] temp = new double[2]; 	// The complex number we are returning.
		temp = multiply(INEG,asin(multiply(I, num1)));
		return temp;
	}

// Find the inverse hyperbolic cosine using the identity, arccosh(z) = -arccos(z)/i.

	public static double[] acosh(double[] num1){
	    double[] temp = new double[2]; 	// The complex number we are returning.
		temp = divide(acos(num1), INEG);
		return temp;
	}

// Find the inverse hyperbolic tangent using the identity, arctanh(z) = ln((1+z)/(1-z))/2

	public static double[] atanh(double[] num1){
	    double[] temp = new double[2]; 	// The complex number we are returning.
		temp = divide(ln(divide(add(ONE, num1), subtract(ONE, num1))), makeComplex(2.0, 0.0));
		return temp;
	}

	/*  factorial()
	 *
	 *  This method converts a double to an int, finds the
	 *  factorial, then returns the double equivalent.
	 */
    public static double[] factorial (double[] num) {
	    double[] temp = new double[2]; 	// The complex number we are returning.
	int i;
	int fact = (int)num[0];	
	int returnVal = 1;

	// The factorial of 0 is always 1.

	if(fact == 0) {
	    temp = makeComplex((double)returnVal, 0.0);
	    return (temp);
	}

	// We can't find the factorial of a negative number.

	else if (fact < 0) {
	    System.out.println("Error: Negative factorial.");
	    temp = makeComplex(Double.NaN, Double.NaN);
	    return (temp);
	}

// Really finding the factorial of an integer.

	else {		
    	    for(i=1; i<=fact ; i++){
		returnVal*=i;				
	    }		
	}
	temp = makeComplex( (double)returnVal, 0.0);      
	return (temp);	
	
    } // End factorial.

// Adding

	public static double[] add(double[] num1, double[] num2) {
	    double[] temp = new double[2]; 	// The complex number we are returning.
		temp[0] = num1[0] + num2[0];
		temp[1] = num1[1] + num2[1];
		return (temp);
	}

// Subtracting.

	public static double[] subtract(double[] num1, double[] num2){
	    double[] temp = new double[2]; 	// The complex number we are returning.
		temp[0] = num1[0] - num2[0];
		temp[1] = num1[1] - num2[1];
		return (temp);
	}

// Multiplying

	public static double[] multiply(double[] num1, double[] num2){
	    	double[] temp = new double[2]; 	// The complex number we are returning.
		temp[0] = num1[0]*num2[0]-num1[1]*num2[1];
		temp[1] = num1[1]*num2[0]+num1[0]*num2[1];
		return(temp);
	}

// Dividing

	public static double[] divide(double[] num1, double[] num2){
	    double[] temp = new double[2]; 	// The complex number we are returning.
		temp[0] = (num1[0]*num2[0]+num1[1]*num2[1])/(num2[0]*num2[0]+num2[1]*num2[1]);
		temp[1] = (num1[1]*num2[0]-num1[0]*num2[1])/(num2[0]*num2[0]+num2[1]*num2[1]);
		return (temp);
	}

// This method finds the result of (a+bi)^(c+di) using the fact that if x and y are complex,
// x^y = e^(y*ln(x)).

	public static double[] power(double[] num1, double[] num2){

		// In x^y, if x is zero, then x^y = 0^y = 0.0
		if (isZero(num1))
			return makeComplex(0.0, 0.0);
		else if ((num1[1] == 0.0) && (num2[1] == 0.0))
			return makeComplex(Math.pow(num1[0], num2[0]), 0.0);
		else 
			return exp(multiply(num2, ln(num1)));
	}

// Finding e^x using the identity e^(a+bi) = (e^a)(cos(b) + i*sin(b)).

	public static double[] exp(double[] num1){
	    double[] temp = new double[2]; 	// The complex number we are returning.
		if (num1[1] == 0.0)
			return makeComplex(Math.exp(num1[0]), 0.0);
		else { 
			temp[0] = Math.exp(num1[0])*Math.cos(num1[1]);
			temp[1] = Math.exp(num1[0])*Math.sin(num1[1]);
			return (temp);
		}
	}

// Finds 10^x.

	public static double[] tenexp(double[] num1) {
		if(num1[1] == 0.0)
			return makeComplex(Math.pow(10, num1[0]), 0.0);
		else
			return (power(makeComplex(10.0, 0.0), num1));
	}

// This method finds the reciprocal of a complex number: x^(-1).

	public static double[] reciprocal(double[] num1) {
		return (divide(ONE, num1));
	}

// This method takes the square x^2.

	public static double[] square(double[] num1){
		return multiply(num1, num1); //(power(num1, temp));
	}

// A means to find the square root of a complex number.

	public static double[] sqrt(double[] num1){
	    double[] temp = new double[2]; 	// The complex number we are returning.
	    temp = makeComplex(0.5, 0.0);
	    	if (num1[1] == 0.0)
			return makeComplex(Math.sqrt(num1[0]), 0.0);
		else
			return exp(multiply(temp, ln(num1)));
	}

// Finds y modulo x, the remainder when y is divided by x.

	public static double[] mod(double[] num1, double[] num2){
		return makeComplex( num1[0] % num2[0], 0.0 );
	}
		
// Permutation

	public static double[] perm(double[] num1, double[] num2){
		return divide(factorial(num1),factorial(makeComplex(num1[0]-num2[0], 0.0)));
	}

// Combination

	public static double[] comb(double[] num1, double[] num2){
		return divide(factorial(num1),multiply(factorial(num2),factorial(makeComplex(num1[0]-num2[0], 0.0))));
	}

// Clears the two stacks for complex numbers.

    public static void clearStack (RPNCalculator app) {
	Label xNum, yNum, zNum;
	DoubleStack values;

	xNum = app.xData;
	yNum = app.yData;
	zNum = app.zData;
	values = app.stack;

// Clear the stack, setting every element to 0.0.
	for(int i=1; i<=values.SIZE-1; i++) {
	    values.numStack[i] = 0.0;
	    values.imagStack[i] = 0.0;
	} 
	values.SIZE = 0;
// And clear the screen, set the text on all three labels to an
// empty screen.
	xNum.setText(app.ORIGINAL_DISPLAY);
	yNum.setText(app.ORIGINAL_DISPLAY);
	zNum.setText(app.ORIGINAL_DISPLAY);	
    }

	/*
	 *  isDup()
	 *
	 *  If the element in the spot one greater than SIZE is
	 *  equal to the element in SIZE, the logic of the program
	 *  dictates that the enter button has just been pressed
	 *  to push a number onto an  empty stack.  This method
	 *  checks for that special situation.
	 *
	 */
    public boolean isDup (DoubleStack stack) {
	if ((stack.numStack[stack.SIZE+1] == stack.numStack[stack.SIZE]) &&
	    (stack.imagStack[stack.SIZE+1] == stack.imagStack[stack.SIZE]))
	    return true;
	else 
	    return false;
    }

// Checks to see if a number is real or not.

    public boolean isImaginary (double[] num1) {
	if (num1[1] == 0.0)
		return false;
	else
		return true;
    }

// This function will evaluate the operations given in the program screen using data from the 
// main screen.

    public double[] progEval (ProgStack screen) {
	List listBox = screen.listBox;
	String op; 	// The operation that will be performed in the evaluation.
	double[] result = new double[2];
	
	ComplexCopy stackCopy = new ComplexCopy(values.numStack, values.imagStack, values.SIZE);

	// Temporary elements popped off the stack for noncommutative operations.
	double[] x = new double[2];
	double[] y = new double[2];

	// Looping through the entire operation stack.
	for (int j=0; j < listBox.getItemCount(); j++) {		
		op = listBox.getItem(j);
		if (op.equals("+")){
			stackCopy.pushCopy(add(stackCopy.popCopy(), stackCopy.popCopy()));
		}
		else if (op.equals("-")) {
			x = stackCopy.popCopy();
			y = stackCopy.popCopy();
			stackCopy.pushCopy(subtract(y, x));
		}

		else if (op.equals("*")){
			stackCopy.pushCopy(multiply(stackCopy.popCopy(), stackCopy.popCopy()));
		}	
		else if (op.equals("/")) {
			x = stackCopy.popCopy();
			y = stackCopy.popCopy();
			stackCopy.pushCopy(divide(y, x));
		}

		else if (op.equals("%")) {
			x = stackCopy.popCopy();
			y = stackCopy.popCopy();
			stackCopy.pushCopy(mod(y, x));
		}

		else if (op.equals("-x")){
			stackCopy.pushCopy(subtract(makeComplex(0.0, 0.0), stackCopy.popCopy()));
		}
		else if (op.equals("-I")) {
			x = stackCopy.popCopy();
			x[1] = 0 - x[1];
			stackCopy.pushCopy(x);
		}

		else if (op.equals("^")) {
			x = stackCopy.popCopy();
			y = stackCopy.popCopy();
			stackCopy.pushCopy(power(y, x));
		}

		else if (op.equals("^2")){
			stackCopy.pushCopy(square(stackCopy.popCopy()));
		}
		else if (op.equals("sqrt")){
			stackCopy.pushCopy(sqrt(stackCopy.popCopy()));
		}
		else if (op.equals("^-1")){
			stackCopy.pushCopy(reciprocal(stackCopy.popCopy()));
		}
		else if (op.equals("e^")){
			stackCopy.pushCopy(exp(stackCopy.popCopy()));
		}
		else if (op.equals("10^")){
			stackCopy.pushCopy(tenexp(stackCopy.popCopy()));
		}
		else if (op.equals("ln")){
			stackCopy.pushCopy(ln(stackCopy.popCopy()));
		}
		else if (op.equals("log")){
			stackCopy.pushCopy(log(stackCopy.popCopy()));
		}
		else if (op.equals("!")){
			stackCopy.pushCopy(factorial(stackCopy.popCopy()));
		}
		else if (op.equals("y C x")) {
			x = stackCopy.popCopy();
			y = stackCopy.popCopy();
			stackCopy.pushCopy(comb(y, x));
		}

		else if (op.equals("y P x")) {
			x = stackCopy.popCopy();
			y = stackCopy.popCopy();
			stackCopy.pushCopy(perm(y, x));
		}

		else if (op.equals("x<->y")) {
			// If the size of the copied stack is less than or equal to 1, then 					
			// switching any elements will keep it the same.
			// Otherwise switch the two elements.
			if(stackCopy.SIZECOPY >= 1) {
				x = stackCopy.popCopy();
				y = stackCopy.popCopy();
				stackCopy.pushCopy(x);
				stackCopy.pushCopy(y);
			}
		}

		// This loop will put the top element of the stack on the bottom, and push 
		// every other one up a spot on the stack.

		else if (op.equals("ROLL")) {
			// If there are 1 or less elements in the stack, it will remain the same.
			if (stackCopy.SIZECOPY <= 1)
				break;
			// Otherwise put the top element at the bottom.
			else {
				x[0] = stackCopy.realCopy[0];
				x[1] = stackCopy.imagCopy[0];
				for(int i = 0; i<stackCopy.SIZECOPY; i++){
					stackCopy.realCopy[i] = stackCopy.realCopy[i+1];
					stackCopy.imagCopy[i] = stackCopy.imagCopy[i+1];
				}
				stackCopy.realCopy[stackCopy.SIZECOPY] = x[0];
				stackCopy.imagCopy[stackCopy.SIZECOPY] = x[1]; 
			}
		}

		else if (op.equals("sin")){
			stackCopy.pushCopy(sin(stackCopy.popCopy()));
		}
		else if (op.equals("cos")){
			stackCopy.pushCopy(cos(stackCopy.popCopy()));
		}
		else if (op.equals("tan")){
			stackCopy.pushCopy(tan(stackCopy.popCopy()));
		}
		else if (op.equals("arcsin")){
			stackCopy.pushCopy(asin(stackCopy.popCopy()));
		}
		else if (op.equals("arccos")){
			stackCopy.pushCopy(acos(stackCopy.popCopy()));
		}
		else if (op.equals("arctan")){
			stackCopy.pushCopy(atan(stackCopy.popCopy()));
		}
		else if (op.equals("sinh")){
			stackCopy.pushCopy(sinh(stackCopy.popCopy()));
		}
		else if (op.equals("cosh")){
			stackCopy.pushCopy(cosh(stackCopy.popCopy()));
		}
		else if (op.equals("tanh")){
			stackCopy.pushCopy(tanh(stackCopy.popCopy()));
		}
		else if (op.equals("arcsinh")){
			stackCopy.pushCopy(asinh(stackCopy.popCopy()));
		}
		else if (op.equals("arccosh")){
			stackCopy.pushCopy(acosh(stackCopy.popCopy()));
		}
		else if (op.equals("arctanh")){
			stackCopy.pushCopy(atanh(stackCopy.popCopy()));
		}
		else if (op.equals("RCL")){
			stackCopy.pushCopy(values.recall());
		}
		else if (op.equals("STORE")){
			stackCopy.pushCopy(factorial(stackCopy.popCopy()));
		}
		//  If it's not one of these, then it must be a number.
		else{
			stackCopy.pushCopy(RPNCalculator.stringToComplex(op));
		}
	} // end for loop.
	return stackCopy.popCopy();	// was return result;
    } // end progEval().

} // end class KeyPadHandler
