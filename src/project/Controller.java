package project;

import java.awt.event.*;

public class Controller {

    private Model model;
    private View  view;
    
    Controller(Model model, View view) {
        this.model = model;
        this.view  = view;
    }
    
   /**
    * Adds listeners to the view
    */
    public void addListeners() {
        view.addForwardListener(new ForwardListener());
        view.addBackListener(new BackListener());
        view.addDecodeListener(new DecodeListener());
    }	
    
   /** 
    * For when the forward button is pressed
    */
    class ForwardListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	model.incPos();
        	view.updateState();
        }
    }
    
    /**
     * For when the decode button is pressed
     */
    class DecodeListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		model.decode();
    		view.updateState();
    	}
    }
    
    /**  
     * For when the back button is pressed
     */    
    class BackListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            model.decPos();
            view.updateState();
        }
    }
}
