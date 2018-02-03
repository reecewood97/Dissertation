package project;

import java.lang.Math;
import java.util.Random;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Model {
	
	private Instruction[] teleInstructions = { new Instruction("H",2), new Instruction("Cnot",2,1),
			new Instruction("Cnot",3,2), new Instruction("H",3), new Instruction("M",3),
			new Instruction("M",2), new Instruction("Cnot",2,1), new Instruction("CZ",3,1)};
	
	private Instruction[] errorInstructions = {new Instruction("H",1), new Instruction("H",2),
			new Instruction("H",3), new Instruction("Cnot",4,5), new Instruction("Cnot",4,6),
			new Instruction("Cnot",3,4), new Instruction("Cnot",3,5), new Instruction("Cnot",3,7),
			new Instruction("Cnot",2,4), new Instruction("Cnot",2,6), new Instruction("Cnot",2,7),
			new Instruction("Cnot",1,5), new Instruction("Cnot",1,6), new Instruction("Cnot",1,7),
			new Instruction("?",1)/*new Instruction("Y",5)*/, new Instruction("H",8), new Instruction("H",9),
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
	
	private int num;		//number of Qbits
	private int dimension;	//number of possibles states
	private Complex[] states;
	private Complex[][] process;
	private int position;
	private int maxPos;
	private Instruction[] instructions;
	private String teleOrError;
	private RoundingMode rmode = RoundingMode.valueOf(1);
	private Complex[] ancillas;

	/**
	 * Initiates a model with the state of all zero's
	 * @param num The number of qubits to be simulated
	 */
	public Model(int num) {
		this.num = num;
		this.dimension = (int)Math.pow(2,num);
		this.states = new Complex[this.dimension];
		this.states[0] = new Complex(new BigDecimal(1), new BigDecimal(0));
		for(int i = 1; i < dimension; i++) { //Initiates all Qbits to 0
			this.states[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
		}
		ancillas = new Complex[6];
		for(int i = 0; i < 6; i++) {
			ancillas[i] = new Complex(new BigDecimal(0), new BigDecimal(0));
		}
		position = 0;
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
	 * @return The state at the current position being observed
	 */
	public Complex[] getState() {
		return process[position];
	}
	
	public void errorCorrect() {
		boolean[] ancs = new boolean[6];
		for(int i = 0; i < ancs.length; i++) {
			ancs[i] = false;
		}
		for(int i = 0; i < 6; i++) {
			ancs[i] = BDcloseTo(p(i+8),1);
		}
		if(!(ancs[0]||ancs[1]||ancs[2]||ancs[3]||ancs[4]||ancs[5])) { //All good
			errorInstructions[errorInstructions.length-1] = new Instruction("",1); return;
		}
		if(!(ancs[0]||ancs[1]||ancs[2])) { //X noise
			if(ancs[3]&&ancs[4]&&ancs[5]) {
				errorInstructions[errorInstructions.length-1] = new Instruction("X",7); return;
			}
			if(ancs[3]&&ancs[4]) {
				errorInstructions[errorInstructions.length-1] = new Instruction("X",6); return;
			}
			if(ancs[3]&&ancs[5]) {
				errorInstructions[errorInstructions.length-1] = new Instruction("X",5); return;
			}
			if(ancs[4]&&ancs[5]) {
				errorInstructions[errorInstructions.length-1] = new Instruction("X",4); return;
			}
			if(ancs[5]) {
				errorInstructions[errorInstructions.length-1] = new Instruction("X",3); return;
			}
			if(ancs[4]) {
				errorInstructions[errorInstructions.length-1] = new Instruction("X",2); return;
			}
			if(ancs[3]) {
				errorInstructions[errorInstructions.length-1] = new Instruction("X",1); return;
			}
		}
		if(!(ancs[3]||ancs[4]||ancs[5])) { //Z noise
			if(ancs[0]&&ancs[1]&&ancs[2]) {
				errorInstructions[errorInstructions.length-1] = new Instruction("Z",7); return;
			}
			if(ancs[0]&&ancs[1]) {
				errorInstructions[errorInstructions.length-1] = new Instruction("Z",6); return;
			}
			if(ancs[0]&&ancs[2]) {
				errorInstructions[errorInstructions.length-1] = new Instruction("Z",5); return;
			}
			if(ancs[1]&&ancs[2]) {
				errorInstructions[errorInstructions.length-1] = new Instruction("Z",4); return;
			}
			if(ancs[2]) {
				errorInstructions[errorInstructions.length-1] = new Instruction("Z",3); return;
			}
			if(ancs[1]) {
				errorInstructions[errorInstructions.length-1] = new Instruction("Z",2); return;
			}
			if(ancs[0]) {
				errorInstructions[errorInstructions.length-1] = new Instruction("Z",1); return;
			}
		}
		if(ancs[0]&&ancs[1]&&ancs[2]&&ancs[3]&&ancs[4]&&ancs[5]) {
			errorInstructions[errorInstructions.length-1] = new Instruction("Y",7); return;
		}
		if(ancs[0]&&ancs[1]&&ancs[3]&&ancs[4]) {
			errorInstructions[errorInstructions.length-1] = new Instruction("Y",6); return;
		}
		if(ancs[0]&&ancs[2]&&ancs[3]&&ancs[5]) {
			errorInstructions[errorInstructions.length-1] = new Instruction("Y",5); return;
		}
		if(ancs[1]&&ancs[2]&&ancs[4]&&ancs[5]) {
			errorInstructions[errorInstructions.length-1] = new Instruction("Y",4); return;
		}
		if(ancs[2]&&ancs[5]) {
			errorInstructions[errorInstructions.length-1] = new Instruction("Y",3); return;
		}
		if(ancs[1]&&ancs[4]) {
			errorInstructions[errorInstructions.length-1] = new Instruction("Y",2); return;
		}
		if(ancs[0]&&ancs[3]) {
			errorInstructions[errorInstructions.length-1] = new Instruction("Y",1); return;
		}
		errorInstructions[errorInstructions.length-1] = new Instruction("",1);
	}
	
	public void Xnoise(int target, BigDecimal propChange, Complex[] newstates) {
		BigDecimal propStay = new BigDecimal(1).subtract(propChange);
		System.out.println("Xnoise on qubit "+target+", propChange is "+propChange);
		propChange = sqrt(propChange, 64);
		propStay = sqrt(propStay, 64);
		Complex compChange = new Complex(propChange, new BigDecimal(0));
		Complex compStay = new Complex(propStay, new BigDecimal(0));
		double tar = (double)target;
		
		//Complex[] newstates = new Complex[dimension];
		//for(int i = 0; i < dimension; i++) {
		//	newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
		//}
		for(int i = 0; i < dimension; i++) {
			int x = i + (int)Math.pow(2,tar-1); //Increase to find state from zeros to ones
			int y = i - (int)Math.pow(2,tar-1); //Decrease to find state from ones to zeros
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
				//newstates[i] = newstates[i].add(states[i].mult(compStay));
				newstates[i] = newstates[i].add(new Complex(states[x].getReal(),states[x].getImag()).mult(compChange));
			} else {
				//newstates[i] = newstates[i].add(states[i].mult(compStay));
				newstates[i] = newstates[i].add(new Complex(states[y].getReal(),states[y].getImag()).mult(compChange));
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
		//states = newstates;
		
	}
	
	public void Ynoise(int target, BigDecimal propChange, Complex[] newstates) {
		BigDecimal propStay = new BigDecimal(1).subtract(propChange);
		System.out.println("Ynoise on qubit "+target+", propChange is "+propChange);
		propChange = sqrt(propChange, 64);
		propStay = sqrt(propStay, 64);
		Complex compChange = new Complex(propChange, new BigDecimal(0));
		Complex compStay = new Complex(propStay, new BigDecimal(0));
		double tar = (double)target;
		
		//Complex[] newstates = new Complex[dimension];
		//for(int i = 0; i < dimension; i++) {
		//	newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
		//}
		for(int i = 0; i < dimension; i++) {
			int x = i + (int)Math.pow(2,tar-1); //Increase to find state from zeros to ones
			int y = i - (int)Math.pow(2,tar-1); //Decrease to find state from ones to zeros
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
				//newstates[i] = newstates[i].add(states[i].mult(compStay));
				newstates[x] = newstates[x].add(new Complex(states[i].getImag().negate(),
						states[i].getReal()).mult(compChange));
			} else {
				//newstates[i] = newstates[i].add(states[i].mult(compStay));
				newstates[y] = newstates[y].add(new Complex(states[i].getImag(),
						states[i].getReal().negate()).mult(compChange));
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
		//states = newstates;
	}
	
	public void Znoise(int target, BigDecimal propChange, Complex[] newstates) {
		BigDecimal propStay = new BigDecimal(1).subtract(propChange);
		System.out.println("Znoise on qubit "+target+", propChange is "+propChange);
		propChange = sqrt(propChange, 64);
		propStay = sqrt(propStay, 64);
		Complex compChange = new Complex(propChange, new BigDecimal(0));
		Complex compStay = new Complex(propStay, new BigDecimal(0));
		double tar = (double)target;
		
		//Complex[] newstates = new Complex[dimension];
		//for(int i = 0; i < dimension; i++) {
		//	newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
		//}
		for(int i = 0; i < dimension; i++) {
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
				newstates[i] = newstates[i].add(states[i].mult(compChange));
			} else {
				//newstates[i] = newstates[i].add(states[i].mult(compStay));
				newstates[i] = newstates[i].sub(states[i].mult(compChange));
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
		//states = newstates;
	}
	
	/**
	 * Increments the current position, to be used by controller, and updates the state of the process
	 * @return The new position
	 */
	public int incPos() {
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
			case "M0": anc(0); break;
			case "M1": anc(1); break;
			case "M2": anc(2); break;
			case "N0": anc(3); break;
			case "N1": anc(4); break;
			case "N2": anc(5); break;
			case "Correct": errorCorrect(); break;
			case "": break;
			case "?": Random random = new Random();
			BigDecimal[] amounts = new BigDecimal[22];
			Complex[] newstates = new Complex[dimension];
			for(int i = 0; i < dimension; i++) {
				newstates[i] = new Complex(new BigDecimal(0), new BigDecimal(0));
			}
			amounts[0] = new BigDecimal(random.nextDouble()*20);
			BigDecimal total = amounts[0];
			for(int i = 1; i < 22; i++) {
				amounts[i] = new BigDecimal(random.nextDouble());
				total = total.add(amounts[i]);
			}
			for(int i = 0; i < 22; i++) {
				amounts[i] = amounts[i].divide(total, 64, RoundingMode.HALF_UP); //12 13 14
			}
			//System.out.println(amounts[1].add(amounts[5]).add(amounts[6]).add(amounts[7]).add(amounts[8])
			//		.add(amounts[12]).add(amounts[13]).add(amounts[14]));
			for(int i = 0; i < dimension; i++) {
				newstates[i] = newstates[i].add(states[i].mult(new Complex(sqrt(amounts[0], 64), new BigDecimal(0))));
			}
			for(int i = 1; i < 8; i++) {
				Xnoise(i, amounts[i], newstates);
			}
			for(int i = 8; i < 15; i++) {
				Ynoise(i-7, amounts[i], newstates);
			}
			for(int i = 15; i < 22; i++) {
				Znoise(i-14, amounts[i], newstates);
			}
			states = newstates;
			break;
			default: System.out.println("Instruction " + in + " is unrecognised in incPos"); break;
			}
			position++;
			process[position] = states;
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
	 * @return The values of the ancillas
	 */
	public Complex[] getAncillas() {
		return ancillas;
	}
	
	//For every ancilla; set qubit 8 to 0, measure values, set ancillas[i] to value.
	public void anc(int op) {
		double tar = 8;
		Complex[] newstates = new Complex[dimension];
		for(int i = 0; i < dimension; i++) {
			newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
		}
		Complex prob0 = new Complex(new BigDecimal(0),new BigDecimal(0));
		for(int i = 0; i < dimension; i++) {
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
				prob0 = prob0.add(states[i].mult(states[i]).mod());
			}
		}
		BigDecimal p0 = prob0.getReal();
		for(int i = 0; i < dimension; i++) {
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
				newstates[i] = new Complex(states[i].getReal().divide(new BigDecimal(Math.sqrt(p0.doubleValue())), 20, rmode),
					states[i].getImag().divide(new BigDecimal(Math.sqrt(p0.doubleValue())), 20, rmode));
			} else {
				if(p0.doubleValue()!=0) {
					newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
				} else {
					newstates[i - (int)Math.pow(2,tar-1)] = newstates[i].add(states[i]);
				}
			}
		}
		
		int x = 0;
		boolean loop = true;
		double res = 0;
		//Complex res = new Complex(new BigDecimal(0), new BigDecimal(0));
		while(loop) {
			if(newstates[x].prob().doubleValue()>0) {
				switch(op) {
				case 0: if((Math.floor (x/Math.pow(2,1-1))  %2)==1) {
					res = (res+1)%2; //XOR the result with the value
				}
				if((Math.floor (x/Math.pow(2,5-1))  %2)==1) {
					res = (res+1)%2;
				}
				if((Math.floor (x/Math.pow(2,6-1))  %2)==1) {
					res = (res+1)%2;
				}
				if((Math.floor (x/Math.pow(2,7-1))  %2)==1) {
					res = (res+1)%2;
				}
				break;
				case 1: Cnot(2,8); Cnot(4,8); Cnot(6,8); Cnot(7,8); break;
				case 2: Cnot(3,8); Cnot(4,8); Cnot(5,8); Cnot(7,8); break;
				case 3: CZ(1,8); CZ(5,8); CZ(6,8); CZ(7,8); break;
				case 4: CZ(2,8); CZ(4,8); CZ(6,8); CZ(7,8); break;
				case 5: CZ(3,8); CZ(4,8); CZ(5,8); CZ(7,8); break;
				}
				loop = false;
			}
			x++;
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
		
		Complex fin = new Complex(new BigDecimal(0), new BigDecimal(0));
		for(int i = 0; i < dimension; i++) {
			if((Math.floor (i/Math.pow(2,8-1))  %2)==1) { //For every state where that number would be 1
				fin = fin.add(newstates[i]);
			}
		}
		ancillas[op] = new Complex(new BigDecimal(Math.sqrt(res)), new BigDecimal(0));
		for(int i = 0; i < ancillas.length; i++) {
			System.out.println("ancilla "+i+" is: "+ancillas[i].prob().doubleValue());
		}
		
		states = newstates;
	}
	
	/**
	 * @param num The qubit in question
	 * @return The probability of that qubit being 1
	 */
	public BigDecimal p(int num) {
		BigDecimal res = new BigDecimal(0);
		for(int i = 0; i < dimension; i++) {
			if((Math.floor (i/Math.pow(2,num-1))  %2)==1) { //For every state where that number would be 1
				res = res.add(process[position][i].prob());
			}
		}
		return res;
	}
	
	/**
	 * Initiates a state for teleportation for a qubit of given state
	 * @param a1is0 A complex number describing the chance of the qubit being zero
	 * @param a1is1 A complex number describing the chance of the qubit being one
	 * @throws Exception If the state has the wrong number of bits or if the values are not normalised
	 */ // 																	3	 2	 1
	public void teleInit(Complex a1is0, Complex a1is1) throws Exception { //a1, (a2, b1)
		if(num!=3) {
			throw new Exception("teleInit called on wrong size of qbits, num is" + num + ", should be 3");
		}
		if(!(a1is0.mult(a1is0).mod().add(a1is1.mult(a1is1).mod()).getReal().doubleValue()>0.99999999999
				&& a1is0.mult(a1is0).mod().add(a1is1.mult(a1is1).mod()).getReal().doubleValue()<1.000000001)) {
			throw new Exception("teleInit did not receive normalised values");
		}
		process = new Complex[9][dimension];
		instructions = teleInstructions;
		maxPos = 8;
		teleOrError = "Teleportation";
		states[0] = new Complex(a1is0.getReal(),a1is0.getImag()); //000
		states[4] = new Complex(a1is1.getReal(),a1is1.getImag()); //100
		process[0] = states;
		H(2);
		process[1] = states;
		Cnot(2,1);
		process[2] = states;
		Cnot(3,2);
		process[3] = states;
		H(3);
		process[4] = states;
		M(3);
		process[5] = states;
		M(2);
		process[6] = states;
		Cnot(2,1);
		process[7] = states;
		CZ(3,1);
		process[8] = states;
	}
	
	/**
	 * Initiates a state for error correction for a qubit of given state
	 * @param a1is0 A complex number describing the chance of the qubit being zero
	 * @param a1is1 A complex number describing the chance of the qubit being one
	 * @throws Exception If the state has the wrong number of bits or if the values are not normalised
	 */
	public void errorInit(Complex a1is0, Complex a1is1) throws Exception {
		if(num!=13) {
			throw new Exception("errorInit called on wrong size of qbits, num is" + num + ", should be 13");
		}
		if(!(a1is0.mult(a1is0).mod().add(a1is1.mult(a1is1).mod()).getReal().doubleValue()>0.99999999999
				&& a1is0.mult(a1is0).mod().add(a1is1.mult(a1is1).mod()).getReal().doubleValue()<1.000000001)) {
			throw new Exception("errorInit did not receive normalised values");
		}
		process = new Complex[errorInstructions.length+1][dimension];
		instructions = errorInstructions;
		maxPos = errorInstructions.length;
		teleOrError = "Error Correction";
		states[0] = new Complex(a1is0.getReal(),a1is0.getImag()); //00000000
		states[8] = new Complex(a1is1.getReal(),a1is1.getImag()); //00001000 (qubit 4 is the input)
		process[0] = states;
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
		
		/*Complex fin = new Complex(new BigDecimal(0),new BigDecimal(0));
		System.out.println("After H on "+target);
		for(int i = 0; i < dimension; i++) {
			Complex sqrd = newstates[i].mult(newstates[i]).mod();
			fin = fin.add(sqrd);
			System.out.println("P(state "+i+") is: "+sqrd.getReal());
		}
		System.out.println("Real is : "+fin.getReal());
		System.out.println(fin.getReal().doubleValue()==1);
		System.out.println();*/
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
				//System.out.print("Control is 1 in state "+i);
				if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
					newstates[i] = newstates[i].add(new Complex(states[x].getReal(),states[x].getImag()));
					//System.out.println(", target was 0");
				} else {
					newstates[i] = newstates[i].add(new Complex(states[y].getReal(),states[y].getImag()));
					//System.out.println(", target was 1");
				}
			} else {
				newstates[i] = newstates[i].add(states[i]);
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
		
		/*Complex fin = new Complex(new BigDecimal(0),new BigDecimal(0));
		System.out.println("After Cnot on "+control+ ", " +target);
		for(int i = 0; i < dimension; i++) {
			Complex sqrd = newstates[i].mult(newstates[i]).mod();
			fin = fin.add(sqrd);
			System.out.println("P(state "+i+") is: "+sqrd.getReal());
		}
		System.out.println("Real is : "+fin.getReal());
		System.out.println(fin.getReal().doubleValue()==1);
		System.out.println();*/
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
				newstates[i] = newstates[i].add(new Complex(states[x].getReal(),states[x].getImag()));
			} else {
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
		Complex prob0 = new Complex(new BigDecimal(0),new BigDecimal(0));
		for(int i = 0; i < dimension; i++) {
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
				prob0 = prob0.add(states[i].mult(states[i]).mod());
			}
		}
		BigDecimal p0 = prob0.getReal();
		BigDecimal p1 = new BigDecimal(1).subtract(p0);
		Random random = new Random();
		boolean is0 = random.nextDouble()<p0.doubleValue();
		for(int i = 0; i < dimension; i++) {
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
				if(is0) { //target is 0 in this state and measured as 0
					newstates[i] = new Complex(states[i].getReal().divide(sqrt(new BigDecimal(p0.doubleValue()), 64), 20, rmode),
							states[i].getImag().divide(sqrt(new BigDecimal(p0.doubleValue()), 64), 20, rmode));
				} else { //target is 0 in this state and measured as 1
					newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
				}
			} else {
				if(is0) { //target is 1 in this state and measured as 0
					newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
				} else { //target is 1 in this state and measured as 1
					newstates[i] = new Complex(states[i].getReal().divide(sqrt(new BigDecimal(p1.doubleValue()), 64), 20, rmode),
							states[i].getImag().divide(sqrt(new BigDecimal(p1.doubleValue()), 64), 20, rmode));
				}
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
		
		/*Complex fin = new Complex(new BigDecimal(0),new BigDecimal(0));
		System.out.println("After M on "+target);
		for(int i = 0; i < dimension; i++) {
			Complex sqrd = newstates[i].mult(newstates[i]).mod();
			fin = fin.add(sqrd);
			System.out.println("P(state "+i+") is: "+sqrd.getReal());
		}
		System.out.println("Real is : "+fin.getReal());
		System.out.println(fin.getReal().doubleValue()==1);
		System.out.println();*/
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
				//System.out.print("Control is 1 in state "+i);
				if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
					newstates[i] = newstates[i].add(states[i]);
					//System.out.println(", target was 0");
				} else {
					newstates[i] = newstates[i].sub(states[i]);
					//System.out.println(", target was 1");
				}
			} else {
				newstates[i] = newstates[i].add(states[i]);
			}
		}
		
		for(int i = 0; i < newstates.length; i++){
			newstates[i] = new Complex(BDround(newstates[i].getReal()),BDround(newstates[i].getImag()));
		}
		
		/*Complex fin = new Complex(new BigDecimal(0),new BigDecimal(0));
		BigDecimal finImag = new BigDecimal(0);
		System.out.println("After CZ on "+control+ ", " +target);
		for(int i = 0; i < dimension; i++) {
			Complex sqrd = newstates[i].mult(newstates[i]).mod();
			fin = fin.add(sqrd);
			finImag = finImag.add(newstates[i].getImag());
			System.out.println("P(state "+i+") is: "+sqrd.getReal());
		}
		System.out.println("Total probability is: "+fin.getReal());
		System.out.println(fin.getReal().doubleValue()==1);
		System.out.println();
		
		double res = 0;
		double res2 = 0;
		for(int i = 0; i < dimension; i++) {
			if((Math.floor (i/Math.pow(2,1-1)) %2)==1) { //For every state where that number would be 1
				res2 = res2 + newstates[i].getReal().multiply(newstates[i].getReal()).doubleValue();
				res = res + newstates[i].getImag().multiply(newstates[i].getImag()).doubleValue();
			}
		}
		System.out.println("Final real state of b1 is: " + res2);
		System.out.println("Final imag state of b1 is: " + res);*/
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
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
				newstates[i] = newstates[i].add(states[i]);
			} else {
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
			if((Math.floor (i/Math.pow(2,tar-1))  %2)==0) {
				newstates[x] = newstates[x].add(new Complex(states[i].getImag().negate(),states[i].getReal()));
			} else {
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
	
	
	
	/*private Complex[] copy() {
		Complex[] newstates = new Complex[dimension];
		for(int i = 0; i < dimension; i++) {
			newstates[i] = new Complex(states[i]);
		}
		return newstates;
	}*/
}
