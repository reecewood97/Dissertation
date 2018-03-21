package project;

import java.lang.Math;
import java.util.Random;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Model {
	
	//Instructions for the teleportation protocol
	private Instruction[] teleInstructions = { new Instruction("H",2), new Instruction("Cnot",2,1),
			new Instruction("Cnot",3,2), new Instruction("H",3), new Instruction("M",2),
			new Instruction("M",3), new Instruction("Cnot",2,1), new Instruction("CZ",3,1)};
	
	//Instructions for the 5 qubit error correction protocol, with encoding
	private Instruction[] errorInstructions5 = { new Instruction("Z",1), new Instruction("H",1),
			new Instruction("Z",1), new Instruction("Cnot",1,2), new Instruction("H",1), new Instruction("H",2),
			new Instruction("Cnot",1,3), new Instruction("Cnot",2,3), new Instruction("H",3),
			new Instruction("Cnot",1,4), new Instruction("Cnot",3,4), new Instruction("H",1),
			new Instruction("H",4), new Instruction("Cnot",1,5), new Instruction("Cnot",2,5),
			new Instruction("Cnot",3,5), new Instruction("H",1), new Instruction("H",2), new Instruction("?",1),
			new Instruction("H",6), new Instruction("H",7), new Instruction("H",8), new Instruction("H",9),
			new Instruction("CZ",6,2), new Instruction("Cnot",6,3), new Instruction("Cnot",6,4),
			new Instruction("CZ",6,5), new Instruction("CZ",7,1), new Instruction("CZ",7,3),
			new Instruction("Cnot",7,4), new Instruction("Cnot",7,5), new Instruction("Cnot",8,1),
			new Instruction("CZ",8,2), new Instruction("CZ",8,4), new Instruction("Cnot",8,5),
			new Instruction("Cnot",9,1), new Instruction("Cnot",9,2), new Instruction("CZ",9,3),
			new Instruction("CZ",9,5), new Instruction("H",6), new Instruction("H",7), new Instruction("H",8),
			new Instruction("H",9), new Instruction("M",6), new Instruction("M",7), new Instruction("M",8),
			new Instruction("M",9), new Instruction("Correct",1), new Instruction("?",1)};
	
	//Instructions for the 5 qubit error correction protocol, without encoding
	private Instruction[] errorInstructions5trunc = { new Instruction("enc",1), new Instruction("?",1),
			new Instruction("H",6), new Instruction("H",7), new Instruction("H",8), new Instruction("H",9),
			new Instruction("CZ",6,2), new Instruction("Cnot",6,3), new Instruction("Cnot",6,4),
			new Instruction("CZ",6,5), new Instruction("CZ",7,1), new Instruction("CZ",7,3),
			new Instruction("Cnot",7,4), new Instruction("Cnot",7,5), new Instruction("Cnot",8,1),
			new Instruction("CZ",8,2), new Instruction("CZ",8,4), new Instruction("Cnot",8,5),
			new Instruction("Cnot",9,1), new Instruction("Cnot",9,2), new Instruction("CZ",9,3),
			new Instruction("CZ",9,5), new Instruction("H",6), new Instruction("H",7), new Instruction("H",8),
			new Instruction("H",9), new Instruction("M",6), new Instruction("M",7), new Instruction("M",8),
			new Instruction("M",9), new Instruction("Correct",1), new Instruction("?",1)};
	
	//Instructions for the 7 qubit error correction protocol, with encoding
	private Instruction[] errorInstructions7 = {new Instruction("H",1), new Instruction("H",2),
			new Instruction("H",3), new Instruction("Cnot",4,5), new Instruction("Cnot",4,6),
			new Instruction("Cnot",3,4), new Instruction("Cnot",3,5), new Instruction("Cnot",3,7),
			new Instruction("Cnot",2,4), new Instruction("Cnot",2,6), new Instruction("Cnot",2,7),
			new Instruction("Cnot",1,5), new Instruction("Cnot",1,6), new Instruction("Cnot",1,7),
			new Instruction("?",1), new Instruction("H",8), new Instruction("H",9),
			new Instruction("H",10), new Instruction("H",11), new Instruction("H",12), new Instruction("H",13),
			new Instruction("Cnot",8,1), new Instruction("Cnot",8,5), new Instruction("Cnot",8,6),
			new Instruction("Cnot",8,7), new Instruction("Cnot",9,2), new Instruction("Cnot",9,4),
			new Instruction("Cnot",9,6), new Instruction("Cnot",9,7), new Instruction("Cnot",10,3),
			new Instruction("Cnot",10,4), new Instruction("Cnot",10,5), new Instruction("Cnot",10,7),
			new Instruction("CZ",11,1), new Instruction("CZ",11,5), new Instruction("CZ",11,6),
			new Instruction("CZ",11,7), new Instruction("CZ",12,2), new Instruction("CZ",12,4),
			new Instruction("CZ",12,6), new Instruction("CZ",12,7), new Instruction("CZ",13,3),
			new Instruction("CZ",13,4), new Instruction("CZ",13,5), new Instruction("CZ",13,7),
			new Instruction("H",8), new Instruction("H",9), new Instruction("H",10), new Instruction("H",11),
			new Instruction("H",12), new Instruction("H",13), new Instruction("M",8), new Instruction("M",9),
			new Instruction("M",10), new Instruction("M",11), new Instruction("M",12), new Instruction("M",13),
			new Instruction("Correct",1), new Instruction("?",1)};
	
	//Instructions for the 7 qubit error correction protocol, without encoding
	private Instruction[] errorInstructions7trunc = {new Instruction("enc",1),
			new Instruction("?",1), new Instruction("H",8), new Instruction("H",9),
			new Instruction("H",10), new Instruction("H",11), new Instruction("H",12), new Instruction("H",13),
			new Instruction("Cnot",8,1), new Instruction("Cnot",8,5), new Instruction("Cnot",8,6),
			new Instruction("Cnot",8,7), new Instruction("Cnot",9,2), new Instruction("Cnot",9,4),
			new Instruction("Cnot",9,6), new Instruction("Cnot",9,7), new Instruction("Cnot",10,3),
			new Instruction("Cnot",10,4), new Instruction("Cnot",10,5), new Instruction("Cnot",10,7),
			new Instruction("CZ",11,1), new Instruction("CZ",11,5), new Instruction("CZ",11,6),
			new Instruction("CZ",11,7), new Instruction("CZ",12,2), new Instruction("CZ",12,4),
			new Instruction("CZ",12,6), new Instruction("CZ",12,7), new Instruction("CZ",13,3),
			new Instruction("CZ",13,4), new Instruction("CZ",13,5), new Instruction("CZ",13,7),
			new Instruction("H",8), new Instruction("H",9), new Instruction("H",10), new Instruction("H",11),
			new Instruction("H",12), new Instruction("H",13), new Instruction("M",8), new Instruction("M",9),
			new Instruction("M",10), new Instruction("M",11), new Instruction("M",12), new Instruction("M",13),
			new Instruction("Correct",1), new Instruction("?",1)};
	
	private int num;			//number of Qbits
	private int dimension;		//number of possible states
	private Complex[] states; 	//represents the current state
	private Complex[][] process;//stores the whole process, including past states
	private int position;		//current position in the process
	private int maxPos;			//maximum position possible
	private Instruction[] instructions;//instructions for the current process
	private String teleOrError;	//teleportation or error correction?
	private RoundingMode rmode = RoundingMode.valueOf(1);//for consistent rounding
	private Complex[] decoded;	//The decoded state, for use in error correcting codes
	private Complex[] initial;	//The initial state for comparison, for use in error correcting codes

	/**
	 * Initiates a model with the state of all zero's
	 * @param num The number of qubits to be simulated
	 */
	public Model(int num) {
		this.num = num;
		this.dimension = (int)Math.pow(2,num);
		this.states = new Complex[this.dimension];
		this.states[0] = new Complex(new BigDecimal(1), new BigDecimal(0)); //All zero state has probability 1
		for(int i = 1; i < dimension; i++) { //Initiates all other states to 0
			this.states[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
		}
		decoded = new Complex[2];
		decoded[0] = new Complex(new BigDecimal(1), new BigDecimal(0));
		decoded[1] = new Complex(new BigDecimal(0), new BigDecimal(0));
		initial = new Complex[2];
		initial[0] = new Complex(new BigDecimal(1), new BigDecimal(0));
		initial[1] = new Complex(new BigDecimal(0), new BigDecimal(0));
		position = 0;
	}
	
	
	/**
	 * Element 0 is the coefficient for the 0 state, element 1 is the coefficient for the 1 state
	 * (Only accurate immediately after decode button is pressed)
	 * @return The state of the decoded qubit
	 */
	public Complex[] getDecoded() {
		return decoded;
	}
	
	/**
	 * Element 0 is the coefficient for the 0 state, element 1 is the coefficient for the 1 state
	 * @return The state of the initial qubit
	 */
	public Complex[] getInitial() {
		return initial;
	}
	
	/**
	 * Returns whether teleportation or error correction is being simulated
	 * @return "Teleportation" or "Error Correction"
	 */
	public String teleOrError() {
		return teleOrError;
	}
	
	/**
	 * @return The instructions for the current process
	 */
	public Instruction[] getInstructions() {
		return instructions;
	}
	
	/**
	 * @return The number of steps in the process minus one
	 */
	public int maxPos() {
		return maxPos;
	}
	
	/**
	 * @return The current position being observed
	 */
	public int getPos() {
		return position;
	}
	
	/**
	 * @return 2 to the power of the number of qubits i.e. the number of possible states
	 */
	public int getDimension() {
		return dimension;
	}
	
	/**
	 * @return The state at the current position being observed
	 */
	public Complex[] getState() {
		return process[position];
	}
	
	/**
	 * Read off the values of the ancillas and replace the final instruction with the one necessary
	 * to correct whatever error has taken place
	 */
	public void errorCorrect5() {
		boolean[] ancs = new boolean[4];
		for(int i = 0; i < ancs.length; i++) {
			ancs[i] = false;
		}
		for(int i = 0; i < 4; i++) { //Set booleans to true if corresponding qubit is 1
			ancs[i] = !BDcloseTo(p(i+6),1);
		}
		if(ancs[0]&&ancs[1]&&ancs[2]&&ancs[3]) { //All good, enter dummy instruction
			errorInstructions5[errorInstructions5.length-1] = new Instruction("",1);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("",1); return;

		}
		if((ancs[0]&&ancs[2]&&ancs[3]) && !(ancs[1])) { //X0
			errorInstructions5[errorInstructions5.length-1] = new Instruction("X",1);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("X",1); return;
		}
		if((ancs[1]&&ancs[3]) && !(ancs[0] || ancs[2])) { //X1
			errorInstructions5[errorInstructions5.length-1] = new Instruction("X",2);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("X",2); return;
		}
		if((ancs[0]&&ancs[2]) && !(ancs[1] || ancs[3])) { //X2
			errorInstructions5[errorInstructions5.length-1] = new Instruction("X",3);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("X",3); return;
		}
		if((ancs[0]&&ancs[1]&&ancs[3]) && !(ancs[2])) { //X3
			errorInstructions5[errorInstructions5.length-1] = new Instruction("X",4);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("X",4); return;
		}
		if((ancs[1]&&ancs[2]) && !(ancs[0] || ancs[3])) { //X4
			errorInstructions5[errorInstructions5.length-1] = new Instruction("X",5);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("X",5); return;
		}
		
		if((ancs[0]) && !(ancs[1] || ancs[2] || ancs[3])) { //Y0
			errorInstructions5[errorInstructions5.length-1] = new Instruction("Y",1);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("Y",1); return;
		}
		if((ancs[1]) && !(ancs[0] || ancs[2] || ancs[3])) { //Y1
			errorInstructions5[errorInstructions5.length-1] = new Instruction("Y",2);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("Y",2); return;
		}
		if((ancs[2]) && !(ancs[0] || ancs[1] || ancs[3])) { //Y2
			errorInstructions5[errorInstructions5.length-1] = new Instruction("Y",3);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("Y",3); return;
		}
		if((ancs[3]) && !(ancs[0] || ancs[1] || ancs[2])) { //Y3
			errorInstructions5[errorInstructions5.length-1] = new Instruction("Y",4);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("Y",4); return;
		}
		if(!(ancs[0] || ancs[1] || ancs[2] || ancs[3])) { //Y4
			errorInstructions5[errorInstructions5.length-1] = new Instruction("Y",5);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("Y",5); return;
		}
		
		if((ancs[0] && ancs[1]) && !(ancs[2] || ancs[3])) { //Z0
			errorInstructions5[errorInstructions5.length-1] = new Instruction("Z",1);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("Z",1); return;
		}
		if((ancs[0] && ancs[1] && ancs[2]) && !(ancs[3])) { //Z1
			errorInstructions5[errorInstructions5.length-1] = new Instruction("Z",2);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("Z",2); return;
		}
		if((ancs[1] && ancs[2] && ancs[3]) && !(ancs[0])) { //Z2
			errorInstructions5[errorInstructions5.length-1] = new Instruction("Z",3);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("Z",3); return;
		}
		if((ancs[2] && ancs[3]) && !(ancs[0] || ancs[1])) { //Z3
			errorInstructions5[errorInstructions5.length-1] = new Instruction("Z",4);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("Z",4); return;
		}
		if((ancs[0] && ancs[3]) && !(ancs[1] || ancs[2])) { //Z4
			errorInstructions5[errorInstructions5.length-1] = new Instruction("Z",5);
			errorInstructions5trunc[errorInstructions5trunc.length-1] = new Instruction("Z",5); return;
		}
		//This state should not be reached, enter dummy instruction
		System.out.println("error in errorCorrect5" + ancs[0] + ancs[1] + ancs[2] + ancs[3]);
		errorInstructions5[errorInstructions5.length-1] = new Instruction("",1);
	}
	
	/**
	 * Reads the measurements of the ancillas and decides the necessary operation, if any, 
	 * which is needed to return the qubits to their correct state
	 */
	public void errorCorrect7() {
		boolean[] ancs = new boolean[6];
		for(int i = 0; i < ancs.length; i++) {
			ancs[i] = false;
		}
		for(int i = 0; i < 6; i++) { //Set booleans to true if corresponding qubit is 1
			ancs[i] = BDcloseTo(p(i+8),1);
		}
		if(!(ancs[0]||ancs[1]||ancs[2]||ancs[3]||ancs[4]||ancs[5])) { //All good, enter dummy instruction
			errorInstructions7[errorInstructions7.length-1] = new Instruction("",1);
			errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("",1); return;

		}
		if(!(ancs[0]||ancs[1]||ancs[2])) { //X noise
			if(ancs[3]&&ancs[4]&&ancs[5]) {
				errorInstructions7[errorInstructions7.length-1] = new Instruction("X",7);
				errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("X",7); return;
			}
			if(ancs[3]&&ancs[4]) {
				errorInstructions7[errorInstructions7.length-1] = new Instruction("X",6);
				errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("X",6); return;
			}
			if(ancs[3]&&ancs[5]) {
				errorInstructions7[errorInstructions7.length-1] = new Instruction("X",5);
				errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("X",5); return;
			}
			if(ancs[4]&&ancs[5]) {
				errorInstructions7[errorInstructions7.length-1] = new Instruction("X",4);
				errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("X",4); return;
			}
			if(ancs[5]) {
				errorInstructions7[errorInstructions7.length-1] = new Instruction("X",3);
				errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("X",3); return;
			}
			if(ancs[4]) {
				errorInstructions7[errorInstructions7.length-1] = new Instruction("X",2);
				errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("X",2); return;
			}
			if(ancs[3]) {
				errorInstructions7[errorInstructions7.length-1] = new Instruction("X",1);
				errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("X",1); return;
			}
		}
		if(!(ancs[3]||ancs[4]||ancs[5])) { //Z noise
			if(ancs[0]&&ancs[1]&&ancs[2]) {
				errorInstructions7[errorInstructions7.length-1] = new Instruction("Z",7);
				errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("Z",7); return;
			}
			if(ancs[0]&&ancs[1]) {
				errorInstructions7[errorInstructions7.length-1] = new Instruction("Z",6);
				errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("Z",6); return;
			}
			if(ancs[0]&&ancs[2]) {
				errorInstructions7[errorInstructions7.length-1] = new Instruction("Z",5);
				errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("Z",5); return;
			}
			if(ancs[1]&&ancs[2]) {
				errorInstructions7[errorInstructions7.length-1] = new Instruction("Z",4);
				errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("Z",4); return;
			}
			if(ancs[2]) {
				errorInstructions7[errorInstructions7.length-1] = new Instruction("Z",3);
				errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("Z",3); return;
			}
			if(ancs[1]) {
				errorInstructions7[errorInstructions7.length-1] = new Instruction("Z",2);
				errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("Z",2); return;
			}
			if(ancs[0]) {
				errorInstructions7[errorInstructions7.length-1] = new Instruction("Z",1);
				errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("Z",1); return;
			}
		} //Y noise
		if(ancs[0]&&ancs[1]&&ancs[2]&&ancs[3]&&ancs[4]&&ancs[5]) {
			errorInstructions7[errorInstructions7.length-1] = new Instruction("Y",7);
			errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("Y",7); return;
		}
		if(ancs[0]&&ancs[1]&&ancs[3]&&ancs[4]) {
			errorInstructions7[errorInstructions7.length-1] = new Instruction("Y",6);
			errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("Y",6); return;
		}
		if(ancs[0]&&ancs[2]&&ancs[3]&&ancs[5]) {
			errorInstructions7[errorInstructions7.length-1] = new Instruction("Y",5);
			errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("Y",5); return;
		}
		if(ancs[1]&&ancs[2]&&ancs[4]&&ancs[5]) {
			errorInstructions7[errorInstructions7.length-1] = new Instruction("Y",4);
			errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("Y",4); return;
		}
		if(ancs[2]&&ancs[5]) {
			errorInstructions7[errorInstructions7.length-1] = new Instruction("Y",3);
			errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("Y",3); return;
		}
		if(ancs[1]&&ancs[4]) {
			errorInstructions7[errorInstructions7.length-1] = new Instruction("Y",2);
			errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("Y",2); return;
		}
		if(ancs[0]&&ancs[3]) {
			errorInstructions7[errorInstructions7.length-1] = new Instruction("Y",1);
			errorInstructions7trunc[errorInstructions7trunc.length-1] = new Instruction("Y",1); return;
		}
		//This state should not be reached, enter dummy instruction
		System.err.println("Error in errorcorrect7(): "+ancs[0]+ancs[1]+ancs[2]+ancs[3]+ancs[4]+ancs[5]);
		errorInstructions7[errorInstructions7.length-1] = new Instruction("",1);
	}
	
	/**
	 * Applies a partial X gate to a qubit
	 * @param target		The target qubit
	 * @param propChange	The proportion of the gate to apply
	 * @param newstates		The states to contribute to
	 */
	public void Xnoise(int target, Complex propChange, Complex[] newstates) {
		double tar = (double)target;
		
		for(int i = 0; i < dimension; i++) {
			int x = i + (int)Math.pow(2,tar-1); //Increase to find state from zeros to ones
			int y = i - (int)Math.pow(2,tar-1); //Decrease to find state from ones to zeros
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
				newstates[i] = newstates[i].add(new Complex(states[x].getReal(),states[x].getImag()).mult(propChange));
			} else {
				newstates[i] = newstates[i].add(new Complex(states[y].getReal(),states[y].getImag()).mult(propChange));
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
		
	}
	
	/**
	 * Applies a partial Y gate to a qubit
	 * @param target		The target qubit
	 * @param propChange	The proportion of the gate to apply
	 * @param newstates		The states to contribute to
	 */
	public void Ynoise(int target, Complex propChange, Complex[] newstates) {
		double tar = (double)target;
		
		for(int i = 0; i < dimension; i++) {
			int x = i + (int)Math.pow(2,tar-1); //Increase to find state from zeros to ones
			int y = i - (int)Math.pow(2,tar-1); //Decrease to find state from ones to zeros
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
				newstates[x] = newstates[x].add(new Complex(states[i].getImag().negate(),
						states[i].getReal()).mult(propChange));
			} else {
				newstates[y] = newstates[y].add(new Complex(states[i].getImag(),
						states[i].getReal().negate()).mult(propChange));
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
	}
	
	/**
	 * Applies a partial Z gate to a qubit
	 * @param target		The target qubit
	 * @param propChange	The proportion of the gate to apply
	 * @param newstates		The states to contribute to
	 */
	public void Znoise(int target, Complex propChange, Complex[] newstates) {
		double tar = (double)target;
		
		for(int i = 0; i < dimension; i++) {
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
				newstates[i] = newstates[i].add(states[i].mult(propChange));
			} else {
				newstates[i] = newstates[i].sub(states[i].mult(propChange));
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
	}
	
	/**
	 * Applies X, Y, and Z noise to each qubit in the 5 qubit codeword
	 */
	public void introduceNoise5() {
		Random random = new Random();
		Complex[] amounts = new Complex[16]; //How much each "error" will contribute to the result
		Complex[] newstates = new Complex[dimension];
		for(int i = 0; i < dimension; i++) {
			newstates[i] = new Complex(new BigDecimal(0), new BigDecimal(0));
		}
		
		amounts[0] = new Complex(new BigDecimal(random.nextDouble()), new BigDecimal(random.nextDouble()));
		for(int i = 1; i < 16; i++) { //Find the total of the randomly generated numbers
			amounts[i] = new Complex(new BigDecimal(random.nextDouble()), new BigDecimal(random.nextDouble()));
		}
		//Print the probabilities that each ancilla should have of being one TODO remove printlns
		/*System.out.println("M0: "+ amounts[8].prob().add(amounts[12].prob()).add(amounts[13].prob()).add(
				amounts[14].prob()).add(amounts[15].prob()).add(amounts[19].prob()).add(amounts[20].prob()).add(
				amounts[21].prob()).doubleValue());
		System.out.println("M1: "+ amounts[9].prob().add(amounts[11].prob()).add(amounts[13].prob()).add(
				amounts[14].prob()).add(amounts[16].prob()).add(amounts[18].prob()).add(amounts[20].prob()).add(
				amounts[21].prob()).doubleValue());
		System.out.println("M2: "+ amounts[10].prob().add(amounts[11].prob()).add(amounts[12].prob()).add(
				amounts[14].prob()).add(amounts[17].prob()).add(amounts[18].prob()).add(amounts[19].prob()).add(
				amounts[21].prob()).doubleValue());
		System.out.println("M3: "+ amounts[1].prob().add(amounts[5].prob()).add(amounts[6].prob()).add(
				amounts[7].prob()).add(amounts[8].prob()).add(amounts[12].prob()).add(
				amounts[13].prob()).add(amounts[14].prob()).doubleValue());*/
		for(int i = 0; i < dimension; i++) { //Add the component from the probability of having no error
			newstates[i] = newstates[i].add(states[i].mult(amounts[0]));
		}
		for(int i = 1; i < 6; i++) { //Add the X error components
			Xnoise(i, amounts[i], newstates);
		}
		for(int i = 6; i < 11; i++) { //Add the Y error components
			Ynoise(i-5, amounts[i], newstates);
		}
		for(int i = 11; i < 16; i++) { //Add the Z error components
			Znoise(i-10, amounts[i], newstates);
		}
		Complex total = new Complex(new BigDecimal(0), new BigDecimal(0));
		for(int i = 0; i < dimension; i++) {
			total = total.add(new Complex(newstates[i].prob(), new BigDecimal(0)));
		}
		for(int i = 0; i < dimension; i++) {
			newstates[i] = newstates[i].div(total.sqrt());
		}
		states = newstates;
	}
	
	/**
	 * Applies X, Y, and Z noise to each qubit in the 7 qubit codeword
	 */
	public void introduceNoise7() {
		Random random = new Random();
		Complex[] amounts = new Complex[22]; //How much each "error" will contribute to the result
		Complex[] newstates = new Complex[dimension];
		for(int i = 0; i < dimension; i++) {
			newstates[i] = new Complex(new BigDecimal(0), new BigDecimal(0));
		}
		
		amounts[0] = new Complex(new BigDecimal(random.nextDouble()), new BigDecimal(random.nextDouble()));
		for(int i = 1; i < 22; i++) { //Find the total of the randomly generated numbers
			amounts[i] = new Complex(new BigDecimal(random.nextDouble()), new BigDecimal(random.nextDouble()));
		}
		
		//Print the probabilities that each ancilla should have of being one TODO remove printlns
		/*System.out.println("M0: "+ amounts[8].prob().add(amounts[12].prob()).add(amounts[13].prob()).add(
				amounts[14].prob()).add(amounts[15].prob()).add(amounts[19].prob()).add(amounts[20].prob()).add(
				amounts[21].prob()).doubleValue());
		System.out.println("M1: "+ amounts[9].prob().add(amounts[11].prob()).add(amounts[13].prob()).add(
				amounts[14].prob()).add(amounts[16].prob()).add(amounts[18].prob()).add(amounts[20].prob()).add(
				amounts[21].prob()).doubleValue());
		System.out.println("M2: "+ amounts[10].prob().add(amounts[11].prob()).add(amounts[12].prob()).add(
				amounts[14].prob()).add(amounts[17].prob()).add(amounts[18].prob()).add(amounts[19].prob()).add(
				amounts[21].prob()).doubleValue());
		System.out.println("N0: "+ amounts[1].prob().add(amounts[5].prob()).add(amounts[6].prob()).add(
				amounts[7].prob()).add(amounts[8].prob()).add(amounts[12].prob()).add(
				amounts[13].prob()).add(amounts[14].prob()).doubleValue());
		System.out.println("N1: "+ amounts[2].prob().add(amounts[4].prob()).add(amounts[6].prob()).add(
				amounts[7].prob()).add(amounts[9].prob()).add(amounts[11].prob()).add(
				amounts[13].prob()).add(amounts[14].prob()).doubleValue());
		System.out.println("N2: "+ amounts[3].prob().add(amounts[4].prob()).add(amounts[5].prob()).add(
				amounts[7].prob()).add(amounts[10].prob()).add(amounts[11].prob()).add(
				amounts[12].prob()).add(amounts[14].prob()).doubleValue());*/
		
		for(int i = 0; i < dimension; i++) { //Add the component from the probability of having no error
			newstates[i] = newstates[i].add(states[i].mult(amounts[0]));
		}
		for(int i = 1; i < 8; i++) { //Add the X error components
			Xnoise(i, amounts[i], newstates);
		}
		for(int i = 8; i < 15; i++) { //Add the Y error components
			Ynoise(i-7, amounts[i], newstates);
		}
		for(int i = 15; i < 22; i++) { //Add the Z error components
			Znoise(i-14, amounts[i], newstates);
		}
		Complex total = new Complex(new BigDecimal(0), new BigDecimal(0));
		for(int i = 0; i < dimension; i++) { //Normalise the states
			total = total.add(new Complex(newstates[i].prob(), new BigDecimal(0)));
		}
		for(int i = 0; i < dimension; i++) {
			newstates[i] = newstates[i].div(total.sqrt());
		}
		states = newstates;
	}
	
	/**
	 * Increments the current position, to be used by controller, and updates the state of the process
	 * Synchronized so as not not interfere with the decode method
	 * @return The new position
	 */
	public synchronized int incPos() {
		if(position!=maxPos){
			states = process[position];
			//perform instruction
			Instruction inst = instructions[position];
			String in = inst.getI();
			switch(in) {
			case "H": H(inst.getTar()); break;
			case "Cnot": Cnot(inst.getCon(),inst.getTar()); break;
			case "M": M(inst.getTar()); break;
			case "CZ": CZ(inst.getCon(), inst.getTar()); break;
			case "X": X(inst.getTar()); break;
			case "Y": Y(inst.getTar()); break;
			case "Z": Z(inst.getTar()); break;
			case "Correct":
				if(teleOrError.equals("Error Correction 5")) {
					errorCorrect5();
				} else {
					errorCorrect7();
				} break;
			case "?": 
				if(teleOrError.equals("Error Correction 5")) {
					introduceNoise5();
				} else {
					introduceNoise7();
				} break;
			case "enc": encode(); break;
			case "": break;
			default: System.out.println("Instruction " + in + " is unrecognised in incPos"); break;
			}
			position++;
			process[position] = states;
			BigDecimal total = new BigDecimal(0);
			for(int i = 0; i < dimension; i ++) { //Print the total probability in the current state
				total = total.add(states[i].prob());
			}
		}
		return position;
	}
	
	/**
	 * Decrements the current position, to be used by controller
	 * @return The new position
	 */
	public int decPos() {
		if(position!=0) {
			position--;
		}
		return position;
	}
	
	/**
	 * @return The number of qubits being simulated
	 */
	public int getNum() {
		return num;
	}
	
	/**
	 * Applies the necessary gates in order to encode the codeword for the current process
	 */
	public void encode() {
		if(teleOrError.equals("Error Correction 5")) {
			Z(1); H(1); Z(1); Cnot(1,2); H(1); H(2); Cnot(1,3); Cnot(2,3); H(3); Cnot(1,4);
			Cnot(3,4); H(1); H(4); Cnot(1,5); Cnot(2,5); Cnot(3,5); H(1); H(2);
		} else {
			H(1); H(2); H(3); Cnot(4,5); Cnot(4,6); Cnot(3,4); Cnot(3,5); Cnot(3,7); Cnot(2,4); Cnot(2,6); 
			Cnot(2,7); Cnot(1,5); Cnot(1,6); Cnot(1,7);
		}
	}
	
	/**
	 * Applies the necessary gates in order to decode the codeword for the current process,
	 * and stores it in the decoded array
	 */
	public synchronized void decode() {
		states = process[position];
		if(teleOrError.equals("Error Correction 5")) {
			H(2); H(1); Cnot(3,5); Cnot(2,5); Cnot(1,5); H(4); H(1); Cnot(3,4); Cnot(1,4);
			H(3); Cnot(2,3); Cnot(1,3); H(2); H(1); Cnot(1,2); Z(1); H(1); Z(1);
			decoded[0] = new Complex(new BigDecimal(0), new BigDecimal(0));
			decoded[1] = new Complex(new BigDecimal(0), new BigDecimal(0));
			for(int i = 0; i < dimension; i++) {
				if((Math.floor (i/Math.pow(2,1-1))  %2)==0) { 	//For every state where qubit 1 would be 0
					decoded[0] = decoded[0].add(states[i]);
				} else {										//Else, if it is 1
					decoded[1] = decoded[1].add(states[i]);
				}
			}
		} else {
			Cnot(1,7); Cnot(1,6); Cnot(1,5); Cnot(2,7); Cnot(2,6); Cnot(2,4); Cnot(3,7); Cnot(3,5);
			Cnot(3,4); Cnot(4,6); Cnot(4,5); H(3); H(2); H(1);
			decoded[0] = new Complex(new BigDecimal(0), new BigDecimal(0));
			decoded[1] = new Complex(new BigDecimal(0), new BigDecimal(0));
			for(int i = 0; i < dimension; i++) {
				if((Math.floor (i/Math.pow(2,4-1))  %2)==0) { 	//For every state where qubit 4 would be 0
					decoded[0] = decoded[0].add(states[i]);
				} else {										//Else, if it is 1	
					decoded[1] = decoded[1].add(states[i]);
				}
			}
		}
	}
	
	/**
	 * @param num The qubit in question
	 * @return The probability of that qubit being 1
	 */
	public BigDecimal p(int num) {
		BigDecimal res1 = new BigDecimal(0);
		BigDecimal res0 = new BigDecimal(0);
		for(int i = 0; i < dimension; i++) {
			if((Math.floor (i/Math.pow(2,num-1))  %2)==1) { //For every state where that number would be 1
				res1 = res1.add(process[position][i].prob());
			} else {
				res0 = res0.add(process[position][i].prob());
			}
		}
		return res1.divide(res1.add(res0), 64, RoundingMode.HALF_DOWN);
	}
	
	/**
	 * Initiates a state for teleportation for a qubit of given state
	 * @param a1is0 A complex number describing the chance of the qubit being zero
	 * @param a1is1 A complex number describing the chance of the qubit being one
	 * @throws Exception If the state has the wrong number of bits or if the values are not normalised
	 */																      // 3	 2	 1
	public void teleInit(Complex a1is0, Complex a1is1) throws Exception { //a1, (a2, b1)
		if(num!=3) {
			throw new Exception("teleInit called on wrong size of qbits, num is" + num + ", should be 3");
		}
		if(!(a1is0.prob().add(a1is1.prob()).doubleValue()>0.99999999999)
				|| !(a1is0.prob().add(a1is1.prob()).doubleValue()<1.000000001)) {
			throw new Exception("teleInit did not receive normalised values");
		}
		process = new Complex[9][dimension];
		instructions = teleInstructions;
		maxPos = 8;
		teleOrError = "Teleportation";
		states[0] = new Complex(a1is0.getReal(),a1is0.getImag()); //000
		states[4] = new Complex(a1is1.getReal(),a1is1.getImag()); //100
		process[0] = states;
	}
	
	/**
	 * Initiates a state for 7 qubit error correction for a qubit of given state
	 * @param a1is0 A complex number describing the chance of the qubit being zero
	 * @param a1is1 A complex number describing the chance of the qubit being one
	 * @throws Exception If the state has the wrong number of bits or if the values are not normalised
	 */
	public void errorInit7(Complex a1is0, Complex a1is1, boolean encode) throws Exception {
		if(num!=13) {
			throw new Exception("errorInit called on wrong size of qbits, num is" + num + ", should be 13");
		}
		if(!(a1is0.prob().add(a1is1.prob()).doubleValue()>0.99999999999)
				|| !(a1is0.prob().add(a1is1.prob()).doubleValue()<1.000000001)) {
			throw new Exception("errorInit did not receive normalised values");
		}
		if(encode) {
			process = new Complex[errorInstructions7.length+1][dimension];
			instructions = errorInstructions7;
			maxPos = errorInstructions7.length;
		} else {
			process = new Complex[errorInstructions7trunc.length+1][dimension];
			instructions = errorInstructions7trunc;
			maxPos = errorInstructions7trunc.length;
		}
		teleOrError = "Error Correction 7";
		states[0] = new Complex(a1is0.getReal(),a1is0.getImag()); //0000000
		states[8] = new Complex(a1is1.getReal(),a1is1.getImag()); //0001000 (qubit 4 is the input)
		process[0] = states;
		decoded[0] = a1is0;
		decoded[1] = a1is1;
		initial[0] = a1is0;
		initial[1] = a1is1;
	}
	
	/**
	 * Initiates a state for 5 qubit error correction for a qubit of given state
	 * @param a1is0 A complex number describing the chance of the qubit being zero
	 * @param a1is1 A complex number describing the chance of the qubit being one
	 * @throws Exception If the state has the wrong number of bits or if the values are not normalised
	 */
	public void errorInit5(Complex a1is0, Complex a1is1, boolean encode) throws Exception {
		if(num!=9) {
			throw new Exception("errorInit called on wrong size of qbits, num is" + num + ", should be 9");
		}
		if(!(a1is0.prob().add(a1is1.prob()).doubleValue()>0.99999999999)
				|| !(a1is0.prob().add(a1is1.prob()).doubleValue()<1.000000001)) {
			throw new Exception("errorInit did not receive normalised values");
		}
		if(encode) {
			process = new Complex[errorInstructions5.length+1][dimension];
			instructions = errorInstructions5;
			maxPos = errorInstructions5.length;
		} else {
			process = new Complex[errorInstructions5trunc.length+1][dimension];
			instructions = errorInstructions5trunc;
			maxPos = errorInstructions5trunc.length;
		}
		teleOrError = "Error Correction 5";
		states[0] = new Complex(a1is0.getReal(),a1is0.getImag()); //00000
		states[1] = new Complex(a1is1.getReal(),a1is1.getImag()); //00001 (qubit 1 is the input)
		process[0] = states;
		decoded[0] = a1is0;
		decoded[1] = a1is1;
		initial[0] = a1is0;
		initial[1] = a1is1;
	}
	
	/**
	 * Implements a Hadamard gate
	 * @param target The target qubit
	 */
	public void H(int target) {
		double tar = (double)target;
		Complex[] newstates = new Complex[dimension];
		for(int i = 0; i < dimension; i++) {
			newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
		}
		for(int i = 0; i < dimension; i++) {
			int x = i + (int)Math.pow(2,tar-1); //Increase to find state from zeros to ones
			int y = i - (int)Math.pow(2,tar-1); //Decrease to find state from ones to zeros
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) { //For every state where that number would be 0
				//0 -> 0
				newstates[i] = newstates[i].add(new Complex(states[i].getReal().divide(sqrt(new BigDecimal(2), 64), 20, rmode),states[i].getImag().divide(new BigDecimal(Math.sqrt(2)), 20, rmode)));
				
				//0 -> 1
				newstates[x] = newstates[x].add(new Complex(states[i].getReal().divide(sqrt(new BigDecimal(2), 64), 20, rmode),states[i].getImag().divide(new BigDecimal(Math.sqrt(2)), 20, rmode)));
			}
			else { //For every state where that number would be 1
				//1 -> 1
				newstates[i] = newstates[i].sub(new Complex(states[i].getReal().divide(sqrt(new BigDecimal(2), 64), 20, rmode),states[i].getImag().divide(new BigDecimal(Math.sqrt(2)), 20, rmode)));
				
				//1 -> 0
				newstates[y] = newstates[y].add(new Complex(states[i].getReal().divide(sqrt(new BigDecimal(2), 64), 20, rmode),states[i].getImag().divide(new BigDecimal(Math.sqrt(2)), 20, rmode)));
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
		states = newstates;
	}

	/**
	 * Implements a controlled not gate.
	 * @param control The control qubit
	 * @param target The target qubit
	 */
	public void Cnot(int control, int target) {
		double con = (double)control;
		double tar = (double)target;
		Complex[] newstates = new Complex[dimension];
		for(int i = 0; i < dimension; i++) {
			newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
		}
		for(int i = 0; i < dimension; i++) {
			int x = i + (int)Math.pow(2,tar-1); //Increase to find state from zeros to ones
			int y = i - (int)Math.pow(2,tar-1); //Decrease to find state from ones to zeros
			if((Math.floor (i/Math.pow(2,con-1))  %2)==1) { //For every state where control would be 1
				if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
					//0 -> 1
					newstates[i] = newstates[i].add(new Complex(states[x].getReal(),states[x].getImag()));
				} else {
					//1 -> 0
					newstates[i] = newstates[i].add(new Complex(states[y].getReal(),states[y].getImag()));
				}
			} else {
				//0 -> 0 or 1 -> 1
				newstates[i] = newstates[i].add(states[i]);
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
		states = newstates;
	}
	
	/**
	 * Implements a not gate.
	 * @param target The target qubit
	 */
	public void X(int target) {
		double tar = (double)target;
		Complex[] newstates = new Complex[dimension];
		for(int i = 0; i < dimension; i++) {
			newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
		}
		for(int i = 0; i < dimension; i++) {
			int x = i + (int)Math.pow(2,tar-1); //Increase to find state from zeros to ones
			int y = i - (int)Math.pow(2,tar-1); //Decrease to find state from ones to zeros
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
				//0 -> 1
				newstates[i] = newstates[i].add(new Complex(states[x].getReal(),states[x].getImag()));
			} else {
				//1 -> 0
				newstates[i] = newstates[i].add(new Complex(states[y].getReal(),states[y].getImag()));
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
		states = newstates;
	}

	/**
	 * Implements a measurement gate
	 * @param target The target qubit
	 */
	public void M(int target) {
		double tar = (double)target;
		Complex[] newstates = new Complex[dimension];
		for(int i = 0; i < dimension; i++) {
			newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
		}
		BigDecimal prob0 = new BigDecimal(0);
		BigDecimal prob1 = new BigDecimal(0);
		for(int i = 0; i < dimension; i++) {
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
				prob0 = prob0.add(states[i].prob()); //Find the probability of being zero
			} else {
				prob1 = prob1.add(states[i].prob()); //Find the probability of being one
			}
		}
		BigDecimal p0 = prob0.divide(prob0.add(prob1), 64, RoundingMode.HALF_UP);
		BigDecimal p1 = new BigDecimal(1).subtract(p0);
		Random random = new Random();
		boolean is0 = random.nextDouble()<p0.doubleValue(); //Random measurement
		for(int i = 0; i < dimension; i++) {
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
				if(is0) { //target is 0 in this state and measured as 0, increase state
					newstates[i] = new Complex(states[i].getReal().divide(sqrt(new BigDecimal(p0.doubleValue()), 64), 20, rmode),
							states[i].getImag().divide(sqrt(new BigDecimal(p0.doubleValue()), 64), 20, rmode));
				} else { //target is 0 in this state and measured as 1, remove state
					newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
				}
			} else {
				if(is0) { //target is 1 in this state and measured as 0, remove state
					newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
				} else { //target is 1 in this state and measured as 1, increase state
					newstates[i] = new Complex(states[i].getReal().divide(sqrt(new BigDecimal(p1.doubleValue()), 64), 20, rmode),
							states[i].getImag().divide(sqrt(new BigDecimal(p1.doubleValue()), 64), 20, rmode));
				}
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
		states = newstates;
	}
	
	/**
	 * Implements a controlled Z gate
	 * @param control The control qubit
	 * @param target The target qubit
	 */
	public void CZ(int control, int target) {
		double con = (double)control;
		double tar = (double)target;
		Complex[] newstates = new Complex[dimension];
		for(int i = 0; i < dimension; i++) {
			newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
		}
		for(int i = 0; i < dimension; i++) {
			if((Math.floor (i/Math.pow(2,con-1))  %2)==1) { //For every state where control would be 1
				if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) { //Check whether the target is 0
					//0 -> 0
					newstates[i] = newstates[i].add(states[i]);
				} else {
					//1 -> -1
					newstates[i] = newstates[i].sub(states[i]);
				}
			} else {
				//Control is 0, do not change state
				newstates[i] = newstates[i].add(states[i]);
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
		states = newstates;
	}
	
	/**
	 * Implements a Z gate
	 * @param target The target qubit
	 */
	public void Z(int target) {
		double tar = (double)target;
		Complex[] newstates = new Complex[dimension];
		for(int i = 0; i < dimension; i++) {
			newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
		}
		for(int i = 0; i < dimension; i++) {
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) { //Check whether target is 0 or 1
				//0 -> 0
				newstates[i] = newstates[i].add(states[i]);
			} else {
				//1 -> -1
				newstates[i] = newstates[i].sub(states[i]);
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
		states = newstates;
	}
	
	/**
	 * Implements a Y gate
	 * @param target The target qubit
	 */
	public void Y(int target) {
		double tar = (double)target;
		Complex[] newstates = new Complex[dimension];
		for(int i = 0; i < dimension; i++) {
			newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
		}
		for(int i = 0; i < dimension; i++) {
			int x = i + (int)Math.pow(2,tar-1); //Increase to find state from zeros to ones
			int y = i - (int)Math.pow(2,tar-1); //Decrease to find state from ones to zeros
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) { //Check whether target is 0 or 1
				//0 -> i*(1)
				newstates[x] = newstates[x].add(new Complex(states[i].getImag().negate(),states[i].getReal()));
			} else {
				//1 -> -i*(0)
				newstates[y] = newstates[y].add(new Complex(states[i].getImag(),states[i].getReal().negate()));
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
		states = newstates;
	}
	
	/**
	 * Rounds a BigDecimal to 0, 0.25, -0.25, 0.5, -0.5, 1, or -1 if it is close enough
	 * @param param The BigDecimal to be rounded
	 * @return The rounded BigDecimal
	 */
	private static BigDecimal BDround(BigDecimal param) {
		if(BDcloseTo(param, 0)){
			return new BigDecimal(0);
		}
		if(BDcloseTo(param, 1)){
			return new BigDecimal(1);
		}
		if(BDcloseTo(param, 0.5)){
			return new BigDecimal(0.5);
		}
		if(BDcloseTo(param, 0.25)){
			return new BigDecimal(0.25);
		}
		if(BDcloseTo(param, -1)){
			return new BigDecimal(-1);
		}
		if(BDcloseTo(param, -0.5)){
			return new BigDecimal(-0.5);
		}
		if(BDcloseTo(param, -0.25)){
			return new BigDecimal(-0.25);
		}
		return param;
	}
	
	/**
	 * Calculates whether the BigDecimal is close enough to a double to be rounded
	 * @param dec The BigDecimal that may be rounded
	 * @param doub The double being rounded to
	 * @return True if it should be rounded, false if not
	 */
	private static boolean BDcloseTo(BigDecimal dec, double doub) {
		return (dec.doubleValue()>=(doub-0.0000000001) && dec.doubleValue()<=(doub+0.0000000001));
	}
	
	/**
	 * Calculates the square root of a BigDecimal correct to a given scale
	 * @param A The input BigDecimal
	 * @param SCALE How many decimal places it is correct to
	 * @return The square root of the given BigDecimal
	 */
	public static BigDecimal sqrt(BigDecimal A, final int SCALE) {
	    BigDecimal x0 = new BigDecimal("0");
	    BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));
	    while (!x0.equals(x1)) {
	        x0 = x1;
	        x1 = A.divide(x0, SCALE, BigDecimal.ROUND_HALF_UP);
	        x1 = x1.add(x0);
	        x1 = x1.divide(new BigDecimal(2), SCALE, BigDecimal.ROUND_HALF_UP);

	    }
	    return x1;
	}
}
