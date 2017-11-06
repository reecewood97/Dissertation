package project;

import java.lang.Math;
import java.util.Random;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Model {
	
	private int num;		//number of Qbits
	private int dimension;	//number of possibles states
	private Complex[] states;
	private RoundingMode rmode = RoundingMode.valueOf(1);

	public Model(int num) {
		this.num = num;
		this.dimension = (int)Math.pow(2,num);
		this.states = new Complex[this.dimension];
		this.states[0] = new Complex(new BigDecimal(1), new BigDecimal(0));
		for(int i = 1; i < dimension; i++) { //Initiates all Qbits to 0
			this.states[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
		}
	}
	
	public Complex[] getState() {
		return states;
	}
	
	public int getNum() {
		return num;
	}
																		  //3     2   1
	public void teleInit(Complex a1is0, Complex a1is1) throws Exception { //a1, (a2, b1)
		if(num!=3) {
			throw new Exception("teleInit called on wrong size of qbits, num is" + num + ", should be 3");
		}
		if(!(a1is0.mult(a1is0).mod().add(a1is1.mult(a1is1).mod()).getReal().doubleValue()>0.99999999999
				&& a1is0.mult(a1is0).mod().add(a1is1.mult(a1is1).mod()).getReal().doubleValue()<1.000000001)) {
			throw new Exception("teleInit did not receive normalised values");
		}
		states[0] = new Complex(a1is0.getReal(),a1is0.getImag()); //000
		states[4] = new Complex(a1is1.getReal(),a1is1.getImag()); //100
	}
	
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
				newstates[i] = newstates[i].add(new Complex(states[i].getReal().divide(new BigDecimal(Math.sqrt(2)), 20, rmode),states[i].getImag().divide(new BigDecimal(Math.sqrt(2)), 20, rmode)));
				
				//0 -> 1
				newstates[x] = newstates[x].add(new Complex(states[i].getReal().divide(new BigDecimal(Math.sqrt(2)), 20, rmode),states[i].getImag().divide(new BigDecimal(Math.sqrt(2)), 20, rmode)));
			}
			else { //For every state where that number would be 1
				//1 -> 1
				newstates[i] = newstates[i].sub(new Complex(states[i].getReal().divide(new BigDecimal(Math.sqrt(2)), 20, rmode),states[i].getImag().divide(new BigDecimal(Math.sqrt(2)), 20, rmode)));
				
				//1 -> 0
				newstates[y] = newstates[y].add(new Complex(states[i].getReal().divide(new BigDecimal(Math.sqrt(2)), 20, rmode),states[i].getImag().divide(new BigDecimal(Math.sqrt(2)), 20, rmode)));
			}
		}
		Complex fin = new Complex(new BigDecimal(0),new BigDecimal(0));
		System.out.println("After H on "+target);
		for(int i = 0; i < dimension; i++) {
			Complex sqrd = newstates[i].mult(newstates[i]).mod();
			fin = fin.add(sqrd);
			System.out.println("P(state "+i+") is: "+sqrd.getReal());
		}
		//System.out.println("P(state "+i+") is: "+sqrd.getReal()*sqrd.getReal());
		System.out.println("Real is : "+fin.getReal());
		System.out.println("Imag is : "+fin.getImag());
		System.out.println(fin.getReal().doubleValue()==1);
		System.out.println();
		states = newstates;
	}

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
		Complex fin = new Complex(new BigDecimal(0),new BigDecimal(0));
		System.out.println("After Cnot on "+control+ ", " +target);
		for(int i = 0; i < dimension; i++) {
			Complex sqrd = newstates[i].mult(newstates[i]).mod();
			fin = fin.add(sqrd);
			System.out.println("P(state "+i+") is: "+sqrd.getReal());
		}
		//System.out.println("P(state "+i+") is: "+sqrd.getReal()*sqrd.getReal());
		System.out.println("Real is : "+fin.getReal());
		System.out.println("Imag is : "+fin.getImag());
		System.out.println(fin.getReal().doubleValue()==1);
		System.out.println();
		states = newstates;
	}

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
					newstates[i] = new Complex(states[i].getReal().divide(new BigDecimal(Math.sqrt(p0.doubleValue())), 20, rmode),
							states[i].getImag().divide(new BigDecimal(Math.sqrt(p0.doubleValue())), 20, rmode));
				} else { //target is 0 in this state and measured as 1
					newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
				}
			} else {
				if(is0) { //target is 1 in this state and measured as 0
					newstates[i] = new Complex(new BigDecimal(0),new BigDecimal(0));
				} else { //target is 1 in this state and measured as 1
					newstates[i] = new Complex(states[i].getReal().divide(new BigDecimal(Math.sqrt(p1.doubleValue())), 20, rmode),
							states[i].getImag().divide(new BigDecimal(Math.sqrt(p1.doubleValue())), 20, rmode));
				}
			}
		}
		Complex fin = new Complex(new BigDecimal(0),new BigDecimal(0));
		System.out.println("After M on "+target);
		for(int i = 0; i < dimension; i++) {
			Complex sqrd = newstates[i].mult(newstates[i]).mod();
			fin = fin.add(sqrd);
			System.out.println("P(state "+i+") is: "+sqrd.getReal());
		}
		//System.out.println("P(state "+i+") is: "+sqrd.getReal()*sqrd.getReal());
		System.out.println("Real is : "+fin.getReal());
		System.out.println("Imag is : "+fin.getImag());
		System.out.println(fin.getReal().doubleValue()==1);
		System.out.println();
		states = newstates;
	}
	
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
		Complex fin = new Complex(new BigDecimal(0),new BigDecimal(0));
		BigDecimal finImag = new BigDecimal(0);
		System.out.println("After CZ on "+control+ ", " +target);
		for(int i = 0; i < dimension; i++) {
			Complex sqrd = newstates[i].mult(newstates[i]).mod();
			fin = fin.add(sqrd);
			finImag = finImag.add(newstates[i].getImag());
			System.out.println("P(state "+i+") is: "+sqrd.getReal());
		}
		//System.out.println("P(state "+i+") is: "+sqrd.getReal()*sqrd.getReal());
		System.out.println("Total probability is: "+fin.getReal());
		System.out.println("Imag is : "+fin.getImag());
		System.out.println(fin.getReal().doubleValue()==1);
		System.out.println();
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
