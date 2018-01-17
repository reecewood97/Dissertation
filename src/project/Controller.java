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
    }
    
    public void init() {
    	
    }
    
    ////////////////////////////////////////// inner class ForwardListener
    /** When the forward button is pressed
     */
    class ForwardListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	model.incPos();
        	view.updateState();
        }
    }//end inner class ForwardListener
    
    
    //////////////////////////////////////////// inner class BackListener
    /**  1. Reset model.
     *   2. Reset View.
     */    
    class BackListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            model.decPos();
            view.updateState();
        }
    }// end inner class BackListener
}
