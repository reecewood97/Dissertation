package project;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
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
    
    private Model model;
    
    //======================================================= constructor
    /** Constructor */
    View(Model model) {
        //... Set up the logic
        this.model = model;
        
        //... Initialize components
        states = new ArrayList<TextField>();
        for(int i = 0; i < model.getState().length; i++) {
        	states.add(new TextField("Probability of state "+intToBin(i, model.getNum())+": "+
        			Double.toString(model.getState()[i].prob().doubleValue()),40));
        	states.get(i).setEditable(false);
        }
        
        //... Layout the components.      
        pane = new JDesktopPane();
        
        controls = new JInternalFrame();
        backBtn = new JButton("Back");
        forwardBtn = new JButton("Forward");
        controls.add(backBtn, BorderLayout.WEST);
        controls.add(forwardBtn, BorderLayout.EAST);
        controls.add(new JLabel(model.teleOrError()), BorderLayout.NORTH);
        count = new TextField(model.getPos()+"/"+model.maxPos());
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
        
        pane.add(controls);
        pane.add(viewSimp);
        
        //... finalize layout
        this.setVisible(true);
        this.setContentPane(pane);
        this.setSize(1000, 800);
        
        this.setTitle("Quantum process simulator");
        // The window closing event should probably be passed to the 
        // Controller in a real program, but this is a short example.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void updateState(){
    	for(int i = 0; i < states.size(); i++) {
        	states.get(i).setText("Probability of state "+intToBin(i, model.getNum())+": "+
        			Double.toString(model.getState()[i].prob().doubleValue()));
    	}
    	count.setText(model.getPos()+"/"+model.maxPos());
    }
    
    public void addBackListener(ActionListener back) {
        backBtn.addActionListener(back);
    }
    
    public void addForwardListener(ActionListener forward) {
        forwardBtn.addActionListener(forward);
    }
    
    public static String intToBin (int n, int numOfBits) {
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
}