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
    private JScrollPane viewScroll;
    private JPanel viewPanel;
    private ArrayList<TextField> states;
    
    private JInternalFrame qubitView;
    private JScrollPane qubitScroll;
    private JPanel qubitPanel;
    private ArrayList<TextField> qubitStates;
    
    private JInternalFrame processView;
    private JScrollPane processScroll;
    private JPanel processPanel;
    private ProcessComp processComp;
    
    private JInternalFrame ancillaView;
    private JScrollPane ancillaScroll;
    private JPanel ancillaPanel;
    private ArrayList<TextField> ancillaStates;
    
    private Model model;
    
    //======================================================= constructor
    View(Model model) {
        //... Set up the logic
        this.model = model;
        
        //... Initialize components
        /*states = new ArrayList<TextField>();
        for(int i = 0; i < model.getState().length; i++) {
        	BigDecimal prob = BDround(model.getState()[i].prob()).setScale(6, RoundingMode.DOWN);
        	states.add(new TextField("Probability of state "+intToBin(i, model.getNum())+": "+prob,30));
        	states.get(i).setEditable(false);
        }*/
        //TODO
        states = new ArrayList<TextField>();
        for(int i = 0; i < model.getState().length; i++) {
        	BigDecimal prob = BDround(model.getState()[i].prob()).setScale(6, RoundingMode.DOWN);
        	if(prob.doubleValue()>0) {
        		TextField newText = new TextField("Probability of state "+intToBin(i, model.getNum())+": "+prob,30);
        		newText.setEditable(false);
        		states.add(newText);
        	}
        }
        
        qubitStates = new ArrayList<TextField>();
        for(int i = 0; i < model.getNum(); i++) {
        	BigDecimal prob = BDround(model.p(model.getNum()-i)).setScale(6, RoundingMode.DOWN);
        	qubitStates.add(new TextField("Probability of qubit "+(model.getNum()-i)+" being 1: "+prob, 30));
        	qubitStates.get(i).setEditable(false);
        }
        
        processComp = new ProcessComp(model);
        
        ancillaStates = new ArrayList<TextField>();
        Complex[] ancillaValues = model.getAncillas();
        for(int i = 0; i < ancillaValues.length; i++) {
        	ancillaStates.add(new TextField(
        			"Probability of ancilla "+ i +" being 1: "+ ancillaValues[i].prob().doubleValue(), 30));
        	ancillaStates.get(i).setEditable(false);
        }
        
        //... Layout the components.      
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
        viewSimp.setSize(300,300);
        viewSimp.setLocation(000,200);
        viewSimp.setVisible(true);
        
        
        qubitPanel = new JPanel();
        qubitPanel.setLayout(new BoxLayout(qubitPanel, BoxLayout.PAGE_AXIS));
        for(int i = 0; i < qubitStates.size(); i++) {
        	qubitPanel.add(qubitStates.get(i));
        }
        qubitScroll = new JScrollPane(qubitPanel);
        qubitView = new JInternalFrame("Qbit states", true, true);
        qubitView.setLayout(new GridBagLayout());
        GridBagConstraints d = new GridBagConstraints();
        d.fill = GridBagConstraints.BOTH; d.weightx = 1; d.weighty = 1;
        qubitView.getContentPane().add(qubitScroll, d);
        qubitView.setSize(300,200);
        qubitView.setLocation(300,50);
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
        processView.setSize(700,(int)processView.getPreferredSize().getHeight());
        processView.setLocation(300,400);
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
        ancillaView.setSize(300,200);
        ancillaView.setLocation(700,50);
        ancillaView.setVisible(true);
        
        
        pane.add(controls);
        pane.add(viewSimp);
        pane.add(qubitView);
        pane.add(processView);
        if(model.teleOrError().equals("Error Correction")) {
        	pane.add(ancillaView);
        }
        
        //... finalize layout
        this.setVisible(true);
        this.setContentPane(pane);
        this.setSize(1000, 800);
        
        
        this.setTitle("Quantum process simulator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void updateState() {
    	//Controls
    	count.setText("Viewing stage: "+(model.getPos()+1)+"/"+(model.maxPos()+1));
    	
    	//SimpView
    	/*for(int i = 0; i < states.size(); i++) {
    		BigDecimal prob = BDround(model.getState()[i].prob()).setScale(6, RoundingMode.DOWN);
        	states.get(i).setText("Probability of state "+intToBin(i, model.getNum())+": "+prob);
    	}*/
    	//TODO
    	states = new ArrayList<TextField>();
        for(int i = 0; i < model.getState().length; i++) {
        	BigDecimal prob = BDround(model.getState()[i].prob()).setScale(6, RoundingMode.DOWN);
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
    	
    	//Qbit view
    	for(int i = 0; i < qubitStates.size(); i++) {
    		BigDecimal prob = BDround(model.p(model.getNum()-i)).setScale(6, RoundingMode.DOWN);
        	qubitStates.get(i).setText("Probability of qubit "+(qubitStates.size()-i)+" being 1 is: "+prob);
    	}
    	
    	//Process view
    	processView.repaint();
    	
    	//Ancilla view
    	Complex[] ancillaValues = model.getAncillas();
        for(int i = 0; i < ancillaValues.length; i++) {
        	ancillaStates.get(i).setText(
        			"Probability of ancilla "+ i +" being 1: "+ ancillaValues[i].prob().doubleValue());
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