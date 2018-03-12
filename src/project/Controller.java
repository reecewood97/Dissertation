package project;

import java.awt.event.*;

public class Controller {
    //... The Controller needs to interact with both the Model and View.
    private Model model;
    private View  view;
    
    //========================================================== constructor
    /** Constructor */
    Controller(Model model, View view) {
        this.model = model;
        this.view  = view;
        
        //... Add listeners to the view.
        view.addForwardListener(new ForwardListener());
        view.addBackListener(new BackListener());
        view.addDecodeListener(new DecodeListener());
    }
    
    public void init() {} //Just to get rid of that pesky warning	
    
    // inner class ForwardListener
    /** 
     * When the forward button is pressed
     */
    class ForwardListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	model.incPos();
        	view.updateState();
        }
    }//end inner class ForwardListener
    
    class DecodeListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		model.decode();
    		view.updateState();
    	}
    }
    
    // inner class BackListener
    /**  
     * When the back button is pressed
     */    
    class BackListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            model.decPos();
            view.updateState();
        }
    }// end inner class BackListener
}
