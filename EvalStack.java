// Eric Landquist
// Mike Melton
// CS2704
// EvalStack.java


// This class will evaluate the given stack.

public class EvalStack {

	public ProgStack mainstack;
	public final static int EMPTY = 0;
	public final static int NUMBER = 1;
	public final static int OP_BIN = 2;
	public final static int OP_UNY = 3;
	public final static int MAX = 100;
	public int SIZE = 0;
	public double[] temp = new double[2];
	public double[] complexX = new double[2]; // Complex duplicates of popped values ...
	public double[] complexY = new double[2]; //
	public double[] complexR = new double[2]; // ... and results.
	public int i;
	public boolean graph = false;

// Used to evaluate a binary or unary operation.
	public double x;
	public double y;

// A specific entry for a given program stack.
	public char[] charArray = new char[MAX];	

// The evaluation stack.
	public double[] evalStack = new double[MAX];

	public ProgStack mainStack;

// A temporary character holder.
	public char h;

		// Constructor for the grapher to use for evaluating.
	public EvalStack (ProgStack mainStack, boolean graph) {
		this.mainStack = mainstack;
		this.graph = graph;
	}


// Actually evaluating the stack.

	public void evaluate(ProgStack mainStack){

		for (i=0; i<mainStack.SIZE; i++) {
			// for a given operator, finds the kind of operator, binary, unary, etc.
			int k = kind(mainStack.opStack[i]);

			// If k is a mumber.
			if (k == NUMBER){
				temp[0] = new Double(mainStack.opStack[i]).doubleValue();
				push(temp[0]);
			}
			// For the binary operators, we pop two elemets off, and operate on
			// them.
			else if(k == OP_BIN) {
				x = pop();		 
				y = pop();
				temp[0] = binaryOp(x, y, mainStack.opStack[i]);
				if (KeyPadHandler.isCool(temp))
					push(temp[0]);
				else{
					push(y);
					push(x);
					System.out.println("Breaking evaluation: Bad operation.");
					break;
				}
			}
			// If the operation is unary, we only pop one element off the 
			// stack and operate on that, testing to see if the output is valid.
			else if (k == OP_UNY){
				x = pop();
				temp[0] = unaryOp(x, mainStack.opStack[i]);
				if (KeyPadHandler.isCool(temp))
					push(temp[0]);
				else{
					push(x);
					System.out.println("Breaking evaluation: Bad operation.");
					break;
				}
			}	

			else 
				System.out.println("Stack empty or something else is messed up.");

		} // End for loop.

// The only element that should be in the stack at this time, if the user knows how to
// do postfix form for ealuating an expression, is the element evalStack[0], the answer.

		if (SIZE != 1)
			System.out.println("Stack not functioning correctly, must change.");

	} // End evaluate

// This method evaluates binary operations.
	public double binaryOp(double x, double y, String operation) {

		// Making complex duplicates.
		complexX = KeyPadHandler.makeComplex(x, 0.0);
		complexY = KeyPadHandler.makeComplex(y, 0.0);
		// All these operations are exactly what they look like.
		if(operation.compareTo("+") == 0){
			if (graph)
				return (y+x);
			else {
				complexR = KeyPadHandler.add(complexX, complexY);
				return complexR[0];
			}
		}

		if(operation.compareTo("-") == 0){
			if (graph)
				return (y-x);
			else {
				complexR = KeyPadHandler.subtract(complexX, complexY);
				return complexR[0];
			}
		}

		if(operation.compareTo("*") == 0){
			if (graph)
				return (y*x);
			else {
				complexR = KeyPadHandler.multiply(complexX, complexY);
				return complexR[0];
			}
		}

		if(operation.compareTo("/") == 0){
			if (graph)
				return (y/x);
			else {
				complexR = KeyPadHandler.divide(complexX, complexY);
				return complexR[0];
			}
		}

		if(operation.compareTo("%") == 0){
			complexR = KeyPadHandler.mod(complexX, complexY);
			return complexR[0];
		}

		if(operation.compareTo("^") == 0){
			return Math.pow(y,x);
		}

		if(operation.compareTo("C") == 0){
			complexR = KeyPadHandler.comb(complexX, complexY);
			return complexR[0];
		}

		if(operation.compareTo("P") == 0){
			complexR = KeyPadHandler.perm(complexX, complexY);
			return complexR[0];
		}

		else {
			System.out.println("Forgot an operation.");
			return Double.NaN;
		}
	
	} // End binaryOp.

	public double unaryOp(double x, String operation){

		// Making complex duplicates.
		complexX = KeyPadHandler.makeComplex(x, 0.0);
		
		if(operation.compareTo("-x") == 0){
			if (graph)
				return (0-x);
			else {
				complexR = KeyPadHandler.subtract(KeyPadHandler.makeComplex(0.0, 0.0), complexX);
				return complexR[0];
			}
		}

		if(operation.compareTo("sqrt") == 0){
			if (x < 0) return 0.0;
			return Math.sqrt(x);
		}

		if(operation.compareTo("ln") == 0){
			return Math.log(x);
		}
		
		if(operation.compareTo("log") == 0){
			return (Math.log(x)/Math.log(10));
		}
		
		if(operation.compareTo("!") == 0){
			complexX = KeyPadHandler.factorial(complexX);
			return complexR[0];
		}
		
		// Trig functions, including inverse and hyperbolic:
		if(operation.compareTo("sin") == 0){
			return Math.sin(x);
		}

		if(operation.compareTo("cos") == 0){
			return Math.cos(x);
		}

		if(operation.compareTo("tan") == 0){
			return Math.tan(x);
		}

		if(operation.compareTo("arcsin") == 0){
			complexR = KeyPadHandler.asin(complexX);
			return complexR[0];
		}

		if(operation.compareTo("arccos") == 0){
			complexR = KeyPadHandler.acos(complexX);
			return complexR[0];
		}

		if(operation.compareTo("arctan") == 0){
			complexR = KeyPadHandler.atan(complexX);
			return complexR[0];
		}

		if(operation.compareTo("sinh") == 0){
			complexR = KeyPadHandler.sinh(complexX);
			return complexR[0];
		}

		if(operation.compareTo("cosh") == 0){
			complexR = KeyPadHandler.cosh(complexX);
			return complexR[0];
		}

		if(operation.compareTo("tanh") == 0){
			complexR = KeyPadHandler.tanh(complexX);
			return complexR[0];
		}

		if(operation.compareTo("arcsinh") == 0){
			complexR = KeyPadHandler.asinh(complexX);
			return complexR[0];
		}

		if(operation.compareTo("arccosh") == 0){
			complexR = KeyPadHandler.acosh(complexX);
			return complexR[0];
		}

		if(operation.compareTo("arctanh") == 0){
			complexR = KeyPadHandler.atanh(complexX);
			return complexR[0];
		}

		else {
			System.out.println("Forgot an operation.");
			return Double.NaN;
		}

	} // End unaryOp()


// This method returns the type of the element.

	public int kind (String element) {

// Convert the string element to a character array.
		charArray = element.toCharArray();		

// Check the first character in the string, if it is not empty, to see if it is a digit.
		if (element.length() > 0) {
			h = charArray[0];

// If it's a number.
			if (Character.isDigit(h) || h == '-' ){
			    if (h == '-') {
				if (element.length() > 1)
				    if (Character.isDigit(charArray[1])) return NUMBER;
			    }
			    else return NUMBER;
			}

// If it's an operartion: binary.
			if (h=='+' || h=='-' || h=='*' || h=='/' || h=='%' || h=='C' || h=='P'
				|| h=='^')
				return OP_BIN;

// Otherwise it's a unary operator.
			else
				return OP_UNY;
		}
		else {
			System.out.println("Empty element.");
			return EMPTY;
		}
	} // End kind.	

// The pop method pops the top element off the stack and decrements the counter.

	public double pop (){

		if (SIZE > 0) {
			SIZE--;
			return evalStack[SIZE];
		}
// If the stack is empty, return the empty string.
		else {
			System.out.println("Stack is empty.");
			return Double.NaN;
		}
	} // End pop.

// This method adds an element to the stack.
	public void push (double element) {
// If the stack is full, say so.
		if (SIZE == 100)
			System.out.println("Stack is full.");
		else {
			SIZE++;
			evalStack[SIZE-1] = element;
		}
	} // End push.			
	
} // End class EvalStack
