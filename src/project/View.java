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
    
    private JInternalFrame controls;
    private JButton forwardBtn;
    private JButton backBtn;
    private TextField count;
    
    private JInternalFrame viewSimp;
    private ArrayList<TextField> states;
    
    private JInternalFrame qubitView;
    private ArrayList<TextField> qubitStates;
    
    private Model model;
    
    //======================================================= constructor
    /** Constructor */
    View(Model model) {
        //... Set up the logic
        this.model = model;
        
        //... Initialize components
        states = new ArrayList<TextField>();
        for(int i = 0; i < model.getState().length; i++) {
        	BigDecimal prob = BDround(model.getState()[i].prob()).setScale(5, RoundingMode.DOWN);
        	states.add(new TextField("Probability of state "+intToBin(i, model.getNum())+": "+prob,40));
        	states.get(i).setEditable(false);
        }
        
        qubitStates = new ArrayList<TextField>();
        for(int i = 0; i < model.getNum(); i++) {
        	BigDecimal prob = BDround(model.p(model.getNum()-i)).setScale(5, RoundingMode.DOWN);
        	qubitStates.add(new TextField("Probability of qubit "+(i+1)+" being 1: "+prob, 40));
        	qubitStates.get(i).setEditable(false);
        }
        
        //... Layout the components.      
        pane = new JDesktopPane();
        
        controls = new JInternalFrame();
        backBtn = new JButton("Back");
        forwardBtn = new JButton("Forward");
        controls.add(backBtn, BorderLayout.WEST);
        controls.add(forwardBtn, BorderLayout.EAST);
        controls.add(new JLabel(model.teleOrError()), BorderLayout.NORTH);
        count = new TextField((model.getPos()+1)+"/"+(model.maxPos()+1));
        count.setEditable(false);
        controls.add(count, BorderLayout.SOUTH);
        controls.setSize(400,100);
        controls.setLocation(0,0);
        controls.setVisible(true);
        
        viewSimp = new JInternalFrame();
        viewSimp.setLayout(new FlowLayout());
        for(int i = 0; i < states.size(); i++) {
        	viewSimp.add(states.get(i));
        }
        viewSimp.setSize(500,400);
        viewSimp.setLocation(000,200);
        viewSimp.setVisible(true);
        
        qubitView = new JInternalFrame();
        qubitView.setLayout(new FlowLayout());
        for(int i = 0; i < qubitStates.size(); i++) {
        	qubitView.add(qubitStates.get(i));
        }
        qubitView.setSize(500,400);
        qubitView.setLocation(400,200);
        qubitView.setVisible(true);
        
        pane.add(controls);
        pane.add(viewSimp);
        pane.add(qubitView);
        
        //... finalize layout
        this.setVisible(true);
        this.setContentPane(pane);
        this.setSize(1000, 800);
        
        this.setTitle("Quantum process simulator");
        // The window closing event should probably be passed to the 
        // Controller in a real program, but this is a short example.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void updateState() {
    	//Controls
    	count.setText((model.getPos()+1)+"/"+(model.maxPos()+1));
    	
    	//SimpView
    	for(int i = 0; i < states.size(); i++) {
    		BigDecimal prob = BDround(model.getState()[i].prob()).setScale(5, RoundingMode.DOWN);
        	states.get(i).setText("Probability of state "+intToBin(i, model.getNum())+": "+prob);
    	}
    	
    	//Qbit view
    	for(int i = 0; i < qubitStates.size(); i++) {
    		BigDecimal prob = BDround(model.p(model.getNum()-i)).setScale(5, RoundingMode.DOWN);
        	qubitStates.get(i).setText("Probability of qubit "+(i+1)+" being 1 is: "+prob);
    	}
    }
    
    public void addBackListener(ActionListener back) {
        backBtn.addActionListener(back);
    }
    
    public void addForwardListener(ActionListener forward) {
        forwardBtn.addActionListener(forward);
    }
    
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
	 * Rounds a BigDecimal to 1, 0, 0.5, or 0.25 if it is close enough
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