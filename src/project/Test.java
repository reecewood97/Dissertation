package project;

import java.math.BigDecimal;

//An old class for testing the model. Not needed now due to existence of the view
public class Test {

	public static void main(String[] args) throws Exception {
		Model model = new Model(9);
		model.errorInit5(new Complex(new BigDecimal(1),new BigDecimal(0)),
				new Complex(new BigDecimal(0),new BigDecimal(0)),true);
		Complex[] orig = model.getState();
		model.encode();
		model.decode();
		Complex[] dec = model.getState();
		for(int i = 0; i < model.getDimension(); i++) {
			if(!orig[i].equals(dec[i])) {
				System.out.println(i);
			}
		}
		System.out.println("done");

	}
	
}
