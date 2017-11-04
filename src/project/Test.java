package project;

import java.math.BigDecimal;

public class Test {

	public static void main(String[] args) throws Exception {
		Model model = new Model(3);
		model.teleInit(new Complex(new BigDecimal(Math.sqrt(0.5)),new BigDecimal(0)),
				new Complex(new BigDecimal(Math.sqrt(0.7)),new BigDecimal(Math.sqrt(0.2)))); //a1 = 3, a2 = 2, b1 = 1
		model.H(2);
		model.Cnot(2,1);
		model.Cnot(3,2);
		model.H(3);
		model.M(3);
		model.M(2);
		model.Cnot(2,1);
		model.CZ(3,1);
		System.out.println("done");
	}
	
}
