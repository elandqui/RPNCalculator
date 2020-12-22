// Eric Landquist and Mike Melton
// CS 2704 Scott Guyer
// StoredVariable.java

// This class will be storing the user defined variables and names.

class StoredVariable {
    String name;
    double[] value = new double[2];
    public static final int MAX = 100;

    public StoredVariable( String name, double[] value ) {
	this.name = name;
	this.value = value;
    }
}