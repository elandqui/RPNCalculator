// Eric Landquist and Mike Melton
// CS 2704 Scott Guyer
// ComplexCopy.java

// This class runs a copy of the DoubleStack, but is a lot simpler.
// This is the class used to run the program evaluator.

public class ComplexCopy {

	public static final int MAX = 100;
	public static int SIZECOPY = 0;
	double[] realCopy = new double[MAX];
	double[] imagCopy = new double[MAX];

	public ComplexCopy(double[] real, double[] imaginary, int size) {

		// Creating copies of the complex number stacks to perform 
		// operations on them.
		System.arraycopy(real, 1, realCopy, 0, MAX-1);
		System.arraycopy(imaginary, 1, imagCopy, 0, MAX-1); 

		// Since the index of the main stacks basically starts at index 1,
		// instead of 0, we need to set SIZECOPY to one less than the size	
		// of the main stacks.
		SIZECOPY = size-1;

	}

	// popCopy will pop a complex number off the copy of the complex stacks.

    public double[] popCopy() {
	
	double[] popped = new double[2];
	
	// If there are elements in the stack.
	if (SIZECOPY >= 0){
		SIZECOPY--;
		popped[0] = realCopy[SIZECOPY+1];
		popped[1] = imagCopy[SIZECOPY+1];
		return popped;
	}
	// If there are no elements left in the stack, return 0.
	else {
		System.out.println("Stack is empty: returning 0");
		return (KeyPadHandler.makeComplex(0.0, 0.0));
	}
    } // end popCopy().

// This method will push a number onto a copy of the complex number stacks.

    public void pushCopy (double[] pushed){
	// If the stack is full.
	if (SIZECOPY >= DoubleStack.MAX-1) {
		System.out.println("Error: stack is full.");
	}
	// If the stack is not full, add in the new elements.
	else {
		SIZECOPY++;
		realCopy[SIZECOPY] = pushed[0];
		imagCopy[SIZECOPY] = pushed[1];
	}
    } // end pushCopy().
} // end class ComplexCopy