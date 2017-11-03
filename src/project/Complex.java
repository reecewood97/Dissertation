package project;

public class Complex {
	
	private double real;
	private double imag;

	//An immutable class for implementing complex numbers
	public Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}
	
	public Complex(Complex original) {
		this.real = original.getReal();
		this.imag = original.getImag();
	}
	
	public double getReal(){
		return real;
	}
	
	public double getImag(){
		return imag;
	}
	
	public Complex add(Complex arg){
		return new Complex(getReal()+arg.getReal(),getImag()+arg.getImag());
	}
	
	public Complex sub(Complex arg){
		return new Complex(getReal()-arg.getReal(),getImag()-arg.getImag());
	}
	
	public Complex mult(Complex arg){
		return new Complex(getReal()*arg.getReal()+getImag()*arg.getImag(),
				getReal()*arg.getImag()+getImag()*arg.getReal());
	}
	
	public Complex conj() {
		return new Complex(getReal(),-getImag());
	}

}
