package project;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.Random;

public class JUnit {
	private Model teleModel;
	private Model errorModel5;
	private View view;
	private Controller controller;
	private Model errorModel7;
	private Random random;
	private Complex error5is0;
	private Complex error5is1;

	@Before
	public void setUp() throws Exception {
		//Teleport test with zero
		teleModel = new Model(3);
		teleModel.teleInit(new Complex(new BigDecimal(1), new BigDecimal(0)),
				new Complex(new BigDecimal(0), new BigDecimal(0))); //Set up the model
		/*view = new View(model); //Initialise all of the components of the system
		controller = new Controller(model, view);
		controller.addListeners();*/
		
		//Error correction 5 test with random input
		errorModel5 = new Model(9);
		random = new Random();
		error5is0 = new Complex(new BigDecimal(random.nextDouble()), new BigDecimal(random.nextDouble()));
		error5is1 = new Complex(new BigDecimal(random.nextDouble()), new BigDecimal(random.nextDouble()));
		Complex total = new Complex(error5is0.prob().add(error5is1.prob()), new BigDecimal(0));
		error5is0 = error5is0.div(total.sqrt());
		error5is1 = error5is1.div(total.sqrt());
		errorModel5.errorInit5(error5is0, error5is1, false);
		view = new View(errorModel5); //Initialise all of the components of the system
		controller = new Controller(errorModel5, view);
		controller.addListeners();
		view.setVisible(false);
		
		//Error correction 7 test with 50/50 superposition
		errorModel7 = new Model(13);
		errorModel7.errorInit7(new Complex(new BigDecimal(Math.sqrt(0.5)), new BigDecimal(0)),
				new Complex(new BigDecimal(Math.sqrt(0.5)), new BigDecimal(0)), true); //Set up the model
		/*view = new View(model); //Initialise all of the components of the system
		controller = new Controller(model, view);
		controller.addListeners();*/
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		while(teleModel.maxPos()>teleModel.getPos()) {
			teleModel.incPos();
		}
		assertTrue(BDcloseTo(teleModel.p(3), 0));
		
		while(errorModel7.maxPos()>errorModel7.getPos()) {
			errorModel7.incPos();
		}
		errorModel7.decode();
		assertTrue(BDcloseTo(errorModel7.p(1), 0.5));
		for(int y = 0; y < 16; y++) {
			for(int i = 0; i < 8; i++) {
				errorModel7.decPos();
			}
			for(int i = 0; i < 8; i++) {
				errorModel7.incPos();
			}
			errorModel7.decode();
			assertTrue(BDcloseTo(errorModel7.p(1), 0.5));
		}
		
		while(errorModel5.maxPos()>errorModel5.getPos()) {
			errorModel5.incPos();
		}
		view.setVisible(true);
		view.updateState();
		view.setVisible(false);
		for(int y = 0; y < 16; y++) {
			for(int i = 0; i < 8; i++) {
				errorModel5.decPos();
			}
			for(int i = 0; i < 8; i++) {
				errorModel5.incPos();
			}
			errorModel5.decode();
			Complex[] decode = errorModel5.getDecoded();
	    	Complex[] init = errorModel5.getInitial();
	    	Complex l1 = decode[0];
	    	Complex l2 = decode[1];
	    	try {
	    		l1 = decode[0].div(init[0]);
	    		l2 = decode[1].div(init[1]);
	    	} catch(ArithmeticException e) {
	    		if(decode[0].equals(new Complex(new BigDecimal(0), new BigDecimal(0))) &&
	    				init[0].equals(new Complex(new BigDecimal(0), new BigDecimal(0)))) {
	    			l1 = decode[1].div(init[1]);
	    		}
	    		if(decode[1].equals(new Complex(new BigDecimal(0), new BigDecimal(0))) &&
	    				init[1].equals(new Complex(new BigDecimal(0), new BigDecimal(0)))) {
	    			l2 = decode[0].div(init[0]);
	    		}
	    	}
	    	double lreal = Math.abs((l1.getReal().doubleValue()-l2.getReal().doubleValue()));
	    	double limag = Math.abs((l1.getImag().doubleValue()-l2.getImag().doubleValue()));
	    	assertTrue(lreal<0.0000000001 && limag<0.0000000001);
		}
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

}
