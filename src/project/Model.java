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
	
	public void H(int number) {
		double num = (double)number;
		Complex[] newstates = new Complex[dimension];
		for(int i = 0; i < dimension; i++) {
			newstates[i] = new Complex(0,0);
		}
		for(int i = 0; i < dimension; i++) {
			int x = i + (int)Math.pow(2,num-1); //Increase to find state from zeros to ones
			int y = i - (int)Math.pow(2,num-1); //Decrease to find state from ones to zeros
			if((Math.floor (i/Math.pow(2,num-1))  %2)==0) { //For every state where that number would be 0
				//0 -> 0
				newstates[i] = newstates[i].add(new Complex(states[i].getReal()/Math.sqrt(2),states[i].getImag()));
				
				//0 -> 1
				newstates[x] = newstates[x].add(new Complex(states[i].getReal()/Math.sqrt(2),states[i].getImag()));
			}
			else { //For every state where that number would be 1
				//1 -> 1
				newstates[i] = newstates[i].sub(new Complex(states[i].getReal()/Math.sqrt(2),states[i].getImag()));
				
				//1 -> 0
				newstates[y] = newstates[y].add(new Complex(states[i].getReal()/Math.sqrt(2),states[i].getImag()));
			}
		}
		/*Complex fin = new Complex(0,0);
		for(int i = 0; i < dimension; i++) {
			Complex sqrd = newstates[i].mult(newstates[i]);
			fin = fin.add(sqrd);
			//System.out.println("P(state "+i+") is: "+sqrd.getReal()*sqrd.getReal());
			//System.out.println("Real is : "+fin.getReal());
			//System.out.println("Imag is : "+fin.getImag());
			//System.out.println(fin.getReal()==1);
		}*/
		states = newstates;
	}
	

	
	
	
	
	
	/*private Complex[] copy() {
		Complex[] newstates = new Complex[dimension];
		for(int i = 0; i < dimension; i++) {
			newstates[i] = new Complex(states[i]);
		}
		return newstates;
	}*/

}
