package project;

import java.math.BigDecimal;

//A class for debugging purposes.
public class Test {

	public static void main(String[] args) throws Exception {
		Model model = new Model(9);
		model.errorInit5(new Complex(new BigDecimal(Math.sqrt(0.5)),new BigDecimal(0)),
				new Complex(new BigDecimal(Math.sqrt(0.5)),new BigDecimal(0)),false);
		model.incPos();
		model.incPos();
		model.decode();
		Complex[] init = model.getInitial();
		Complex[] dec = model.getDecoded();
		System.out.println("Initial state is ("+init[0].getReal().doubleValue()+
        		"+"+init[0].getImag().doubleValue()+"i) |0> + ("+
        		init[1].getReal().doubleValue()+"+"+init[1].getImag().doubleValue()
        		+"i) |1>");
		System.out.println("Decoded state is ("+dec[0].getReal().doubleValue()+
        		"+"+dec[0].getImag().doubleValue()+"i) |0> + ("+
        		dec[1].getReal().doubleValue()+"+"+dec[1].getImag().doubleValue()
        		+"i) |1>");
		System.out.println("done");

	}
	
}
