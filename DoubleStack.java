// Eric Landquist
// Mike Melton
// CS 2704
// DoubleStack.java

import java.lang.Double;

// This class contains several utilities. It stores a stack of
// doubles first of all, and contains methods to pop (take off),
// push (put on) elements of type double. It also has a store
// and recall method for memory storage.

public class DoubleStack {
	
	// These are the actual stacks: numStack for the real part, imagStack for
	// the imginary part.
	public double[] numStack = new double[MAX];	
	public double[] imagStack = new double[MAX];

// The size of the stack
	public static int SIZE = 0;

	// maximum size.	
	public static final int MAX = 100;
	public boolean dupFlag;		// Tells us if there is a situation where an element is
					// duplicated beyond the range of the stack.
	public int i;
	public double rHold, iHold;	// A temporary storage for the real and imaginary
					// parts of a complex number.

// This holds the memory value.
	public double[] memory = new double[2];

	public DoubleStack (){

// Initialize the DoubleStack to all zeroes.
		for(i=0; i<MAX; i++){
			numStack[i] = 0.0;
			imagStack[i] = 0.0;	
		}
		// Nothing is in the stack except for the initialized
		// values, so nothing is duplicated
		// beyond the range of the stack.
		dupFlag = false;	
	}

// pop() takes an element off the top of the stack and
// decreases the size of the stack.

	public double[] pop() {
		double[] complex = new double[2];	// returns a complex number.
		if (SIZE > 0) {
			// If something had not just been the first element to get 
			// popped on, decrement the size of hte stack and continue.
			if (!dupFlag)
				SIZE--;	
			complex[0] = numStack[SIZE+1];
			complex[1] = imagStack[SIZE+1];
			numStack[SIZE+1] = 0.0;	
			imagStack[SIZE+1] = 0.0;
			// since we're popping an element, the dupFlag condition becomes false.
			dupFlag = false;
			return complex;
		}

// If the stack is empty, return 0.0, since the stack is all zeroes
// by default.
		else {
			SIZE = 0;
			return (KeyPadHandler.makeComplex(0.0, 0.0));
		}
	}

// This method places items on top of the stack, giving an error
// if the stack is full.
	public void push (double[] num) {
		
		if (SIZE == MAX-1) {	
			System.out.println("Error: Stack is full!");
		}
		else {
			if (SIZE != 0)
			    SIZE++;
			else
			    SIZE = 1;
			numStack[SIZE] = num[0];
			imagStack[SIZE] = num[1];
		}
		// Since we're pushing on an element, we no longer have the duplicate value 	
		// situation.
		dupFlag = false;
	}
	
// Stores a value in memory.

	public void store (double[] memory) {
		this.memory[0] = memory[0];
		this.memory[1] = memory[1];
	}
		
// Returns the value from memory. Note after retuning it, the value
// stays in memory.
	public double[] recall () {
		return memory;
	}

} // End class DoubleStack