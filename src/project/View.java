package project;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class View extends JFrame {
	
	private static final long serialVersionUID = 1L;
    
    //... Components
    private JDesktopPane pane;
    
    //Controls
    private JInternalFrame controls;
    private JButton forwardBtn;
    private JButton backBtn;
    private TextField count;
    
    //View for displaying the decoded qubit
    private JInternalFrame viewDec;
    private JButton decButton;
    private TextField initial;
    private TextField decoded;
    private TextField lambda;
    
    //View containing list of all states
    private JInternalFrame viewSimp;
    private JScrollPane viewScroll;
    private JPanel viewPanel;
    private ArrayList<TextField> states;
    
    //View showing probability of each qubit
    private JInternalFrame qubitView;
    private JScrollPane qubitScroll;
    private JPanel qubitPanel;
    private ArrayList<TextField> qubitStates;
    
    //View showing current progress through the process
    private JInternalFrame processView;
    private JScrollPane processScroll;
    private JPanel processPanel;
    private ProcessComp processComp;
    
    //View showing probability of each ancilla (similar to qubit view)
    private JInternalFrame ancillaView;
    private JScrollPane ancillaScroll;
    private JPanel ancillaPanel;
    private ArrayList<TextField> ancillaStates;
    
    private Model model;
    
    View(Model model) {
    	
        this.model = model;
        
        //Prepare the components
        states = new ArrayList<TextField>();
        for(int i = 0; i < model.getState().length; i++) {
        	BigDecimal prob = BDround(model.getState()[i].prob()).setScale(6, RoundingMode.HALF_DOWN);
        	if(prob.doubleValue()>0) {
        		TextField newText = new TextField("Probability of state "+intToBin(i, model.getNum())+": "+prob,30);
        		newText.setEditable(false);
        		states.add(newText);
        	}
        }
        
        qubitStates = new ArrayList<TextField>();
        for(int i = 0; i < model.getNum(); i++) {
        	BigDecimal prob = BDround(model.p(i+1)).setScale(6, RoundingMode.HALF_DOWN);
        	TextField text = new TextField("Probability of qubit "+ (i+1) +" being 1: "+prob, 30);
        	text.setEditable(false);
        	qubitStates.add(text);
        }
        
        processComp = new ProcessComp(model);
        
        ancillaStates = new ArrayList<TextField>();
        if(model.teleOrError().equals("Error Correction 7")) {
        	String anc = "";
        	for(int i = 0; i < 6; i++) {
        		if(i<3) {
        			anc = "M";
        		} else {
        			anc = "N";
        		}
        		anc = anc + (i%3);
        		ancillaStates.add(new TextField("Value of ancilla "+ anc + " is " + model.p(i+8).doubleValue(), 30));
        		ancillaStates.get(i).setEditable(false);
        	}
        } else {
        	for(int i = 0; i < 4; i++) {
        		String anc = "M"+i;
        		ancillaStates.add(new TextField("Value of ancilla "+ anc + " is " + model.p(i+6).doubleValue(), 30));
        		ancillaStates.get(i).setEditable(false);
        	}
        }
        
        //Layout the components  
        pane = new JDesktopPane();
        
        controls = new JInternalFrame("Controls", true, true);
        backBtn = new JButton("Back");
        forwardBtn = new JButton("Forward");
        controls.add(backBtn, BorderLayout.WEST);
        controls.add(forwardBtn, BorderLayout.EAST);
        controls.add(new JLabel(model.teleOrError()), BorderLayout.NORTH);
        count = new TextField("Viewing stage: "+(model.getPos()+1)+"/"+(model.maxPos()+1));
        count.setEditable(false);
        controls.add(count, BorderLayout.SOUTH);
        controls.setSize(200,150);
        controls.setLocation(0,0);
        controls.setVisible(true);
        
        viewDec = new JInternalFrame("Decoding", true, true);
        decButton = new JButton("Decode");
        viewDec.add(decButton, BorderLayout.EAST);
        initial = new TextField("Initial state is ("+model.getInitial()[0].getReal().doubleValue()+
        		"+"+model.getInitial()[0].getImag().doubleValue()+"i) |0> + ("+
        		model.getInitial()[1].getReal().doubleValue()+"+"+model.getInitial()[1].getImag().doubleValue()
        		+"i) |1>");
        initial.setEditable(false);
        decoded = new TextField("Decoded state is ("+model.getDecoded()[0].getReal().doubleValue()+
        		"+"+model.getDecoded()[0].getImag().doubleValue()+"i) |0> + ("+
        		model.getDecoded()[1].getReal().doubleValue()+"+"+model.getDecoded()[1].getImag().doubleValue()
        		+"i) |1>");
        decoded.setEditable(false);
        lambda = new TextField("States are equivalent. Lambda is 1 + 0i");
        lambda.setEditable(false);
        viewDec.add(lambda, BorderLayout.CENTER);
        viewDec.add(initial, BorderLayout.NORTH);
        viewDec.add(decoded, BorderLayout.SOUTH);
        viewDec.setSize(600, 150);
        viewDec.setLocation(200,000);
        viewDec.setVisible(true);
        
        
        viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.PAGE_AXIS));
        for(int i = 0; i < states.size(); i++) {
        	viewPanel.add(states.get(i));
		}
        viewScroll = new JScrollPane(viewPanel);
        viewSimp = new JInternalFrame("States", true, true);
        viewSimp.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; c.weightx = 1; c.weighty = 1;
        viewSimp.getContentPane().add(viewScroll, c);
        viewSimp.setSize(400,500);
        viewSimp.setLocation(1000,250);
        viewSimp.setVisible(true);
        
        
        qubitPanel = new JPanel();
        qubitPanel.setLayout(new BoxLayout(qubitPanel, BoxLayout.PAGE_AXIS));
        for(int i = 0; i < qubitStates.size(); i++) {
        	qubitPanel.add(qubitStates.get(qubitStates.size()-i-1));
        }
        qubitScroll = new JScrollPane(qubitPanel);
        qubitView = new JInternalFrame("Qbit states", true, true);
        qubitView.setLayout(new GridBagLayout());
        GridBagConstraints d = new GridBagConstraints();
        d.fill = GridBagConstraints.BOTH; d.weightx = 1; d.weighty = 1;
        qubitView.getContentPane().add(qubitScroll, d);
        qubitView.setSize(300,200);
        qubitView.setLocation(800,000);
        qubitView.setVisible(true);
        
        
        processPanel = new JPanel();
        processPanel.setLayout(new BorderLayout());
        processPanel.add(processComp, BorderLayout.CENTER);
        processPanel.setPreferredSize(processComp.getD());
        processScroll = new JScrollPane(processPanel);
        processView = new JInternalFrame("Process graphic", true, true);
        processView.setLayout(new GridBagLayout());
        GridBagConstraints e = new GridBagConstraints();
        e.fill = GridBagConstraints.BOTH; e.weightx = 1; e.weighty = 1;
        processView.getContentPane().add(processScroll, e);
        processView.setSize(processView.getPreferredSize());
        processView.setLocation(000,180);
        processView.setVisible(true);
        
        
        ancillaPanel = new JPanel();
        ancillaPanel.setLayout(new BoxLayout(ancillaPanel, BoxLayout.PAGE_AXIS));
        for(int i = 0; i < ancillaStates.size(); i++) {
        	ancillaPanel.add(ancillaStates.get(i));
        }
        ancillaScroll = new JScrollPane(ancillaPanel);
        ancillaView = new JInternalFrame("Ancilla States", true, true);
        ancillaView.setLayout(new GridBagLayout());
        GridBagConstraints f = new GridBagConstraints();
        f.fill = GridBagConstraints.BOTH; f.weightx = 1; f.weighty = 1;
        ancillaView.getContentPane().add(ancillaScroll, f);
        ancillaView.setSize(300,250);
        ancillaView.setLocation(1100,000);
        ancillaView.setVisible(true);
        
        if(model.teleOrError().equals("Error Correction 7")) {
        	
        	//set positions and sizes
        	controls.setLocation(-9,-3);
        	controls.setSize(210,181);
        	viewDec.setLocation(180,-2);
        	viewDec.setSize(893,181);
        	viewSimp.setLocation(1053,-1);
        	viewSimp.setSize(347,779);
        	qubitView.setLocation(758,400);
        	qubitView.setSize(318,378);
        	processView.setLocation(-9,160);
        	processView.setSize(790,620);
        	ancillaView.setLocation(759,160);
        	ancillaView.setSize(316,261);
        	
        } else if(model.teleOrError().equals("Error Correction 5")) {
        	
        	//set positions and sizes
        	controls.setLocation(-9,-3);
        	controls.setSize(209,187);
        	viewDec.setLocation(181,-2);
        	viewDec.setSize(908,186);
        	viewSimp.setLocation(1085,253);
        	viewSimp.setSize(315,525);
        	qubitView.setLocation(1085,-1);
        	qubitView.setSize(312,268);
        	processView.setLocation(1,333);
        	processView.setSize(1089,445);
        	ancillaView.setLocation(753,168);
        	ancillaView.setSize(337,182);
        	
        } else {
        	
        	//set positions and sizes
        	controls.setLocation(-9,-3);
        	controls.setSize(209,187);
        	viewDec.setLocation(181,-2);
        	viewDec.setSize(908,186);
        	viewSimp.setLocation(1085,253);
        	viewSimp.setSize(315,525);
        	qubitView.setLocation(1085,-1);
        	qubitView.setSize(312,268);
        	processView.setLocation(1,333);
        	processView.setSize(1089,445);
        	ancillaView.setLocation(753,168);
        	ancillaView.setSize(337,182);
        	
        }
        
        
        pane.add(controls);
        pane.add(viewSimp);
        pane.add(qubitView);
        pane.add(processView);
        if(model.teleOrError().equals("Error Correction 7") || model.teleOrError().equals("Error Correction 5")) {
        	pane.add(viewDec);
        	pane.add(ancillaView);
        }
        
        //... finalize layout
        this.setVisible(true);
        this.setContentPane(pane);
        this.setSize(1400, 800);
        
        
        this.setTitle("Quantum process simulator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * To be used by the controller when a button is pressed
     * Updates the view with information from reading the model
     */
    public void updateState() {
    	
    	//Controls
    	count.setText("Viewing stage: "+(model.getPos()+1)+"/"+(model.maxPos()+1));
    	
    	//viewDec
    	Complex[] decode = model.getDecoded();
    	Complex[] init = model.getInitial();
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
    	if(lreal<0.0000000001 && limag<0.0000000001) {
    			lambda.setText("States are equivalent. Lambda is "+
    					l1.getReal().setScale(20, RoundingMode.HALF_DOWN)+
    					" + "+l1.getImag().setScale(20, RoundingMode.HALF_DOWN)+"i");
    	} else {
    		lambda.setText("States not equivalent");
   		}
   		decoded.setText("Decoded state is ("+model.getDecoded()[0].getReal().doubleValue()+
   				"+"+model.getDecoded()[0].getImag().doubleValue()+"i) |0> + ("+
   				model.getDecoded()[1].getReal().doubleValue()+"+"+model.getDecoded()[1].getImag().doubleValue()
   				+"i) |1>");
   		//viewDec.revalidate();
    	
    	//SimpView
    	if((model.teleOrError().equals("Error Correction 7") && ((model.maxPos()-model.getPos() < 10) || (model.maxPos()-model.getPos() > 42))) ||
    			(model.teleOrError().equals("Error Correction 5") && (model.maxPos()-model.getPos() < 9 || model.maxPos()-model.getPos() > 28)) ||
    			(model.teleOrError().equals("Teleportation"))) {
    		BigDecimal total = new BigDecimal(0);
    		for(int i = 0; i < model.getState().length; i++) {
    			total = total.add(model.getState()[i].prob());
    		}
    		states = new ArrayList<TextField>();
    		for(int i = 0; i < model.getState().length; i++) {
    			BigDecimal prob = BDround(model.getState()[i].prob().divide(total, 64, RoundingMode.HALF_DOWN)).setScale(6, RoundingMode.HALF_DOWN);
    			if(prob.doubleValue()>0) {
    				TextField newText = new TextField("Probability of state "+intToBin(i, model.getNum())+": "+prob,30);
    				newText.setEditable(false);
    				states.add(newText);
    			}
    		}
    		viewPanel.removeAll();
    		for(int i = 0; i < states.size(); i++) {
    			viewPanel.add(states.get(i));
    		}
    		viewSimp.revalidate();
    	} else {
    		viewPanel.removeAll();
    		TextField text = new TextField("Too many states to list!");
    		text.setEditable(false);
    		viewPanel.add(text);
    		viewSimp.revalidate();
    	}
    	
    	//Qbit view
    	for(int i = 0; i < qubitStates.size(); i++) {
    			BigDecimal prob = BDround(model.p(i+1)).setScale(6, RoundingMode.HALF_DOWN);
    			qubitStates.get(i).setText("Probability of qubit "+ (i+1) +" being 1: "+prob);
    	}
    	
    	//Process view
    	processView.repaint();
    	
    	//Ancilla view
    	if(model.teleOrError().equals("Error Correction 7")) {
        	String anc = "";
        	for(int i = 0; i < 6; i++) {
        		if(i<3) {
        			anc = "M";
        		} else {
        			anc = "N";
        		}
        		anc = anc + (i%3);
        		ancillaStates.get(i).setText("Value of ancilla "+ anc + " is " + model.p(i+8).doubleValue());
        	}
        } else {
        	for(int i = 0; i < 4; i++) {
        		String anc = "M"+i;
        		ancillaStates.get(i).setText("Value of ancilla "+ anc + " is " + model.p(i+6).doubleValue());
        	}
        }
    }
    
    /**
     * Adds an actionListener to the back button
     */
    public void addBackListener(ActionListener back) {
        backBtn.addActionListener(back);
    }
    
    /**
     * Adds an actionListener to the forward button
     */
    public void addForwardListener(ActionListener forward) {
        forwardBtn.addActionListener(forward);
    }
    
    /**
     * Adds an actionListener to the decode button
     */
    public void addDecodeListener(ActionListener decode) {
    	decButton.addActionListener(decode);
    }
    
    /**
     * Converts an integer to binary representation
     */
    private static String intToBin (int n, int numOfBits) {
    	String binary = "";
    	for(int i = 0; i < numOfBits; ++i, n/=2) {
    		switch (n % 2) {
    		case 0:
    			binary = "0" + binary; break;
    		case 1:
    			binary = "1" + binary; break;
    	    }
    	}
    	return binary;
    }
    
    /**
	 * Rounds a BigDecimal to 0, 0.25, -0.25, 0.5, -0.5, 1, or -1 if it is close enough
	 * @param param The BigDecimal to be rounded
	 * @return The rounded BigDecimal
	 */
	private static BigDecimal BDround(BigDecimal param) {
		if(BDcloseTo(param, 0)){
			return new BigDecimal(0);
		}
		if(BDcloseTo(param, 1)){
			return new BigDecimal(1);
		}
		if(BDcloseTo(param, 0.5)){
			return new BigDecimal(0.5);
		}
		if(BDcloseTo(param, 0.25)){
			return new BigDecimal(0.25);
		}
		if(BDcloseTo(param, -1)){
			return new BigDecimal(-1);
		}
		if(BDcloseTo(param, -0.5)){
			return new BigDecimal(-0.5);
		}
		if(BDcloseTo(param, -0.25)){
			return new BigDecimal(-0.25);
		}
		return param;
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