package project;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.*;

public class Main {
	
	private static RoundingMode rmode = RoundingMode.valueOf(1);
	
	//... Create model, view, and controller.  They are
	//    created once here and passed to the parts that
	//    need them so there is only one copy of each.
	public static void main(String[] args) throws Exception {
		
		String[] possibleValues = { "Teleportation", "Error Correction" };
		String selectedValue = (String)JOptionPane.showInputDialog(null,
		"Select the quantum process to be demonstrated", "Input", JOptionPane.INFORMATION_MESSAGE,
		null, possibleValues, possibleValues[1]);
		if(selectedValue == null){
			System.exit(0);
		}
		
		if(selectedValue.equals(possibleValues[0])) {
			Model model      = new Model(3);
			Object[] choices = { "Other", "50/50", "One", "Zero" };
			Object defaultChoice = null;
			String startState = null;
			try {
			startState = (String)choices[JOptionPane.showOptionDialog(null,
					"Select an inital state for the qubit", "Inital state",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, defaultChoice)];
			} catch (ArrayIndexOutOfBoundsException e) {
				System.exit(0);
			}
			Complex a1is0 = new Complex(new BigDecimal(1), new BigDecimal(0));
			Complex a1is1 = new Complex(new BigDecimal(0), new BigDecimal(0));
			switch(startState) {
			case "Zero" : break;
			
			case "One"  : a1is0 = new Complex(new BigDecimal(0), new BigDecimal(0));
				a1is1 = new Complex(new BigDecimal(1), new BigDecimal(0)); break;
				
			case "50/50": a1is0 = new Complex(new BigDecimal(Math.sqrt(0.5)), new BigDecimal(0));
				a1is1 = new Complex(new BigDecimal(Math.sqrt(0.5)), new BigDecimal(0)); break;
			
			case "Other": try {
				Complex[] input = complexInput(); 
				a1is0 = input[0];
				a1is1 = input[1];
			} catch(NumberFormatException e) {
				System.err.println("Did not enter parseable text");
				System.exit(0);
			} catch(ArithmeticException e){
				System.err.println("Total probability summed to 0");
				System.exit(0);
			} break;
			}
			//try {
				model.teleInit(a1is0, a1is1);
			/*} catch (Exception e) {
				System.err.println("Normalisation failed");
				System.exit(1);
			}*/
			
			View view = new View(model);
			Controller controller = new Controller(model, view);
			controller.init();
			
			view.setVisible(true);
		} else {
			Model model      = new Model(13);
			Object[] choices = { "Other", "50/50", "One", "Zero" };
			Object defaultChoice = null;
			String startState = null;
			try {
			startState = (String)choices[JOptionPane.showOptionDialog(null,
					"Select an inital state for the qubit", "Inital state",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, defaultChoice)];
			} catch (ArrayIndexOutOfBoundsException e) {
				System.exit(0);
			}
			Complex a1is0 = new Complex(new BigDecimal(1), new BigDecimal(0));
			Complex a1is1 = new Complex(new BigDecimal(0), new BigDecimal(0));
			switch(startState) {
			case "Zero" : break;
			
			case "One"  : a1is0 = new Complex(new BigDecimal(0), new BigDecimal(0));
				a1is1 = new Complex(new BigDecimal(1), new BigDecimal(0)); break;
				
			case "50/50": a1is0 = new Complex(new BigDecimal(Math.sqrt(0.5)), new BigDecimal(0));
				a1is1 = new Complex(new BigDecimal(Math.sqrt(0.5)), new BigDecimal(0)); break;
			
			case "Other": try {
				Complex[] input = complexInput(); 
				a1is0 = input[0];
				a1is1 = input[1];
			} catch(NumberFormatException e) {
				System.err.println("Did not enter parseable text");
				System.exit(0);
			} catch(ArithmeticException e){
				System.err.println("Total probability summed to 0");
				System.exit(0);
			} break;
			}
			
			model.errorInit(a1is0, a1is1);
			View view = new View(model);
			Controller controller = new Controller(model, view);
			controller.init();
			view.setVisible(true);
		}

	}
	
	private static Complex[] complexInput() throws NumberFormatException {
		JTextField r0Field = new JTextField(5);
		JTextField i0Field = new JTextField(5);
		JTextField r1Field = new JTextField(5);
		JTextField i1Field = new JTextField(5);
		      
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Real(0):"));
		myPanel.add(r0Field);
		myPanel.add(Box.createHorizontalStrut(10)); // a spacer
		myPanel.add(new JLabel("Imag(0):"));
		myPanel.add(i0Field);
		myPanel.add(Box.createHorizontalStrut(10)); // a spacer
		myPanel.add(new JLabel("Real(1):"));
		myPanel.add(r1Field);
		myPanel.add(Box.createHorizontalStrut(10)); // a spacer
		myPanel.add(new JLabel("Imag(1):"));
		myPanel.add(i1Field);

		int result = JOptionPane.showConfirmDialog(null, myPanel, 
				"Enter relative probability magntiute", JOptionPane.OK_CANCEL_OPTION);
		if (!(result == JOptionPane.OK_OPTION)) {
			System.exit(0);
		}
		
		BigDecimal r0 = new BigDecimal(Double.parseDouble(r0Field.getText()));
		BigDecimal i0 = new BigDecimal(Double.parseDouble(i0Field.getText()));
		BigDecimal r1 = new BigDecimal(Double.parseDouble(r1Field.getText()));
		BigDecimal i1 = new BigDecimal(Double.parseDouble(i1Field.getText()));
		
		if( r0.signum()==-1 || i0.signum()==-1 || r1.signum()==-1 || i1.signum()==-1 ) {
			//Is this an error?
		}
		
		BigDecimal totProb = r0.subtract(i0).add(r1.subtract(i1));
		if(totProb.signum()==0){
			
		}
		if(totProb.signum()==-1){
			totProb=totProb.negate();
		}
		BigDecimal r0norm = r0.divide(totProb, 20, rmode);
		BigDecimal i0norm = i0.divide(totProb, 20, rmode);
		BigDecimal r1norm = r1.divide(totProb, 20, rmode);
		BigDecimal i1norm = i1.divide(totProb, 20, rmode);
		
		BigDecimal r0rt;
		BigDecimal i0rt;
		BigDecimal r1rt;
		BigDecimal i1rt;
		
		if(r0norm.signum()==-1){
			r0norm = r0norm.negate();
			r0rt = new BigDecimal(Math.sqrt(r0norm.doubleValue()));
			r0rt = r0rt.negate();
		} else {
			r0rt = new BigDecimal(Math.sqrt(r0norm.doubleValue()));
		}
		if(i0norm.signum()==-1){
			i0norm = i0norm.negate();
			i0rt = new BigDecimal(Math.sqrt(i0norm.doubleValue()));
			i0rt = i0rt.negate();
		} else {
			i0rt = new BigDecimal(Math.sqrt(i0norm.doubleValue()));
		}
		if(r1norm.signum()==-1){
			r1norm = r1norm.negate();
			r1rt = new BigDecimal(Math.sqrt(r1norm.doubleValue()));
			r1rt = r1rt.negate();
		} else {
			r1rt = new BigDecimal(Math.sqrt(r1norm.doubleValue()));
		}
		if(i1norm.signum()==-1){
			i1norm = i1norm.negate();
			i1rt = new BigDecimal(Math.sqrt(i1norm.doubleValue()));
			i1rt = i1rt.negate();
		} else {
			i1rt = new BigDecimal(Math.sqrt(i1norm.doubleValue()));
		}
		
		Complex[] ret = new Complex[2];
		ret[0] = new Complex(r0rt, i0rt);
		ret[1] = new Complex(r1rt, i1rt);
		
		return ret;
	}

}
