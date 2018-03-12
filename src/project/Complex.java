package project;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Complex {
	
	private BigDecimal real;
	private BigDecimal imag;

	//An immutable class for implementing complex numbers
	/**
	 * Constructor
	 * @param real The real component
	 * @param imag The imaginary component
	 */
	public Complex(BigDecimal real, BigDecimal imag) {
		this.real = real;
		this.imag = imag;
	}
	
	/**
	 * A deep copy of a complex number
	 * @param original The object to be copied
	 */
	public Complex(Complex original) {
		this.real = original.getReal();
		this.imag = original.getImag();
	}
	
	public BigDecimal getReal(){
		return real;
	}
	
	public BigDecimal getImag(){
		return imag;
	}
	
	/**
	 * Adds another complex number
	 * @param arg The number to be added
	 * @return The result of the addition
	 */
	public Complex add(Complex arg){
		return new Complex(getReal().add(arg.getReal()),getImag().add(arg.getImag()));
	}
	
	/**
	 * Subtracts a complex number
	 * @param arg The number to be subtracted
	 * @return The result of the subtraction
	 */
	public Complex sub(Complex arg){
		return new Complex(getReal().subtract(arg.getReal()),getImag().subtract(arg.getImag()));
	}
	
	/**
	 * Multiplies by another complex number
	 * @param arg The number to be multiplied by
	 * @return The result of the multiplication
	 */
	public Complex mult(Complex arg){
		return new Complex(getReal().multiply(arg.getReal()).subtract(getImag().multiply(arg.getImag())),
				getReal().multiply(arg.getImag()).add(getImag().multiply(arg.getReal())));
	}
	
	/**
	 * Divides by another complex number
	 * @param arg The number to divide by
	 * @return The result of the division
	 */
	public Complex div(Complex arg) {
		Complex denom = arg.mult(arg.conj());
		assert(denom.getImag().equals(new BigDecimal(0)));
		BigDecimal denominator = denom.getReal();
		Complex numer = this.mult(arg.conj());
		return new Complex(numer.getReal().divide(denominator, 64, RoundingMode.HALF_UP),
				numer.getImag().divide(denominator, 64, RoundingMode.HALF_UP));
	}
	
	/**
	 * @return The modulus of the complex number
	 */
	public Complex mod(){
		BigDecimal real = this.real;
		BigDecimal imag = this.imag;
		/*if(real.signum()==-1) {
			real = real.negate();
		}
		if(imag.signum()==-1) {
			imag = imag.negate();
		}*/
		return new Complex(Model.sqrt(real.multiply(real).add(imag.multiply(imag)), 64), new BigDecimal(0));
	}
	
	/**
	 * @return The modulus of the square of the complex number
	 */
	public BigDecimal prob() {
		return this.mod().mult(this.mod()).getReal();
		//return this.mult(this).mod().getReal();
	}
	
	/**
	 * @return The complex conjugate
	 */
	public Complex conj() {
		return new Complex(getReal(),getImag().negate());
	}
	
	/**
	 * @return The argument of this complex number
	 */
	public double arg() {
        return Math.atan2(real.doubleValue(),imag.doubleValue());
    }
	
	/**
	 * @return The square root of this complex number
	 */
	public Complex sqrt() {
        BigDecimal rmod = Model.sqrt(this.mod().getReal(), 64);
        double theta = this.arg()/2;
        return new Complex(rmod.multiply(new BigDecimal(Math.cos(theta))),
        		rmod.multiply(new BigDecimal(Math.sin(theta))));
    }
	
	public boolean equals(Complex other) {
		return((real.subtract(other.getReal()).abs().doubleValue()<0.000001) && 
				(imag.subtract(other.getImag()).abs().doubleValue()<0.0000001));
	}

}
