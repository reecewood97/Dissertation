package project;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Complex {
	
	private BigDecimal real;
	private BigDecimal imag;

	//An immutable class for implementing complex numbers
	public Complex(BigDecimal real, BigDecimal imag) {
		this.real = real;
		this.imag = imag;
	}
	
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
	
	public Complex add(Complex arg){
		return new Complex(getReal().add(arg.getReal()),getImag().add(arg.getImag()));
	}
	
	public Complex sub(Complex arg){
		return new Complex(getReal().subtract(arg.getReal()),getImag().subtract(arg.getImag()));
	}
	
	public Complex mult(Complex arg){
		return new Complex(getReal().multiply(arg.getReal()).subtract(getImag().multiply(arg.getImag())),
				getReal().multiply(arg.getImag()).add(getImag().multiply(arg.getReal())));
	}
	
	public Complex div(Complex arg) {
		Complex denom = arg.mult(arg.conj());
		assert(denom.getImag().equals(new BigDecimal(0)));
		BigDecimal denominator = denom.getReal();
		Complex numer = this.mult(arg.conj());
		return new Complex(numer.getReal().divide(denominator, 64, RoundingMode.HALF_UP),
				numer.getImag().divide(denominator, 64, RoundingMode.HALF_UP));
	}
	
	public Complex mod(){
		BigDecimal real = this.real;
		BigDecimal imag = this.imag;
		if(real.signum()==-1) {
			real = real.negate();
		}
		if(imag.signum()==-1) {
			imag = imag.negate();
		}
		return new Complex(real, imag);
	}
	
	public BigDecimal prob() {
		return this.mult(this).mod().getReal();
	}
	
	public Complex conj() {
		return new Complex(getReal(),getImag().negate());
	}

}
