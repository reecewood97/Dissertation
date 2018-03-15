package project;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.*;

public class Main {
	
	private static RoundingMode rmode = RoundingMode.valueOf(1);
	
	public static void main(String[] args) throws Exception {
		
		String[] possibleValues = { "Teleportation", "7 Qubit Error Correction", "5 Qubit Error Correction" };
		String selectedValue = (String)JOptionPane.showInputDialog(null,
		"Select the quantum process to be demonstrated", "Input", JOptionPane.INFORMATION_MESSAGE,
		null, possibleValues, possibleValues[2]); //Request which process to run
		if(selectedValue == null){
			System.exit(0);
		}
		
		if(selectedValue.equals(possibleValues[0])) { //Teleportation selected
			
			Model model      = new Model(3);
			Object[] choices = { "Other", "50/50", "One", "Zero" };
			Object defaultChoice = null;
			String startState = null;
			try { //Selection of initial state of qubit
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
				Complex[] input = complexInput(); //Arbitrary input
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
			
			model.teleInit(a1is0, a1is1); //Set up the model
			View view = new View(model); //Initialise all of the components of the system
			Controller controller = new Controller(model, view);
			controller.init();
			view.setVisible(true);
			
		} else if(selectedValue.equals(possibleValues[1])) { //7 Qubit Error Correction selected
			
			Model model      = new Model(13);
			Object[] choices = { "Other", "50/50", "One", "Zero" };
			Object defaultChoice = null;
			String startState = null;
			try { //Request specification of initial state of input qubit
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
				Complex[] input = complexInput(); //Allows arbitrary input if desired
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
			
			String enc = null;
			Object [] encChoices = { "Yes", "No" };
			Object encDefaultChoice = null;
			try { //Request whether to replace encoding with one step
				enc = (String)encChoices[JOptionPane.showOptionDialog(null,
						"Would you like to skip the encoding stage?", "Encoding option",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, encChoices, encDefaultChoice)];
			} catch (ArrayIndexOutOfBoundsException e) {
				System.exit(0);
			}
			
			model.errorInit7(a1is0, a1is1, enc.equals("No")); //Set up all components of the system
			View view = new View(model);
			Controller controller = new Controller(model, view);
			controller.init();
			view.setVisible(true);
			
		} else if(selectedValue.equals(possibleValues[2])) {
			
			Model model      = new Model(9);
			Object[] choices = { "Other", "50/50", "One", "Zero" };
			Object defaultChoice = null;
			String startState = null;
			try { //Request specification of initial state of input qubit
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
				Complex[] input = complexInput(); //Allows arbitrary input if desired
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
			
			String enc = null;
			Object [] encChoices = { "Yes", "No" };
			Object encDefaultChoice = null;
			try { //Request whether to replace encoding with one step
				enc = (String)encChoices[JOptionPane.showOptionDialog(null,
						"Would you like to skip the encoding stage?", "Encoding option",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, encChoices, encDefaultChoice)];
			} catch (ArrayIndexOutOfBoundsException e) {
				System.exit(0);
			}
			
			model.errorInit5(a1is0, a1is1, enc.equals("No")); //Set up all components of the system
			View view = new View(model);
			Controller controller = new Controller(model, view);
			controller.init();
			view.setVisible(true);
		}

	}
	
	/**
	 * For when the user wants to input an arbitrary initial state for the input qubit
	 * @return The two complex numbers describing the qubit
	 * @throws NumberFormatException If the total probability is zero
	 */
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

		int result = JOptionPane.showConfirmDialog(null, myPanel, //Request input
				"Enter relative amplitudes", JOptionPane.OK_CANCEL_OPTION);
		if (!(result == JOptionPane.OK_OPTION)) {
			System.exit(0);
		}
		
		BigDecimal r0;
		BigDecimal i0;
		BigDecimal r1;
		BigDecimal i1;
		
		//Parse them. If parsing fails, replace with 0
		try{
			r0 = new BigDecimal(Double.parseDouble(r0Field.getText()));
		} catch(NumberFormatException e) {
			r0 = new BigDecimal(0);
		}
		try{
			i0 = new BigDecimal(Double.parseDouble(i0Field.getText()));
		} catch(NumberFormatException e) {
			i0 = new BigDecimal(0);
		}
		try{
			r1 = new BigDecimal(Double.parseDouble(r1Field.getText()));
		} catch(NumberFormatException e) {
			r1 = new BigDecimal(0);
		}
		try{
			i1 = new BigDecimal(Double.parseDouble(i1Field.getText()));
		} catch(NumberFormatException e) {
			i1 = new BigDecimal(0);
		}
		
		//Normalise them so the sum of their squares is 1
		BigDecimal totProb = r0.multiply(r0).add(i0.multiply(i0)).add(r1.multiply(r1)).add(i1.multiply(i1));
		
		BigDecimal totProbRoot = Model.sqrt(totProb, 64);
		
		BigDecimal r0norm = r0.divide(totProbRoot, 20, rmode);
		BigDecimal i0norm = i0.divide(totProbRoot, 20, rmode);
		BigDecimal r1norm = r1.divide(totProbRoot, 20, rmode);
		BigDecimal i1norm = i1.divide(totProbRoot, 20, rmode);
		
		//Return the processed complex numbers
		Complex[] ret = new Complex[2];
		ret[0] = new Complex(r0norm, i0norm);
		ret[1] = new Complex(r1norm, i1norm);
		
		return ret;
	}

}
