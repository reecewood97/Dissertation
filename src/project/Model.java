package project;

import java.lang.Math;

public class Model {
	
	private int num;		//number of Qbits
	private int dimension;	//number of possibles states
	private Complex[] states;

	public Model(int num) {
		this.num = num;
		this.dimension = (int)Math.pow(2,num);
		this.states = new Complex[this.dimension];
		this.states[0] = new Complex(1,0);
		for(int i = 1; i < dimension; i++) { //Initiates all Qbits to 0
			this.states[i] = new Complex(0,0);
		}
	}
	
	public Model copy() {
		return this;
	}
	
	public void teleInit(Complex a1is0, Complex a1is1) throws Exception { //a1, (a2, b1)
		if(num!=3) {
			throw new Exception("teleInit called on wrong size of qbits, num is" + num + ", should be 3");
		}
		if(a1is0.mult(a1is0).add(a1is1.mult(a1is1)).getReal()!=1) {
			throw new Exception("teleInit did not receive normalised values");
		}
		states[0] = new Complex(a1is0.getReal(),a1is0.getImag()); //000
		states[4] = new Complex(a1is1.getReal(),a1is1.getImag()); //100
	}
	
	public void H(int num) {
		Complex[] newstates = new Complex[dimension];
		for(int i = 0; i < dimension; i++) {
			newstates[i] = new Complex(states[i]);
		}
	}

}
