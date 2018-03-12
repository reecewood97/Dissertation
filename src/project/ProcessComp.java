package project;

import java.awt.*;

public class ProcessComp extends Component {
	
	private static final long serialVersionUID = 1L;
	private Model model;
	private String teleOrError;
	private Instruction[] instructions;
	private int num; 		//Number of qubits
	private int currPos; 	//Current position in the process
	private int processLength; //Length of the process
	private int blobSize = 7;	//Various sizes for consistent drawing
	private int LineSpace = 40;
	private int LeftIndent = 50;
	private int TopIndent = 30;
	private int BoxWidth = 30; // Also used for height
	private int BoxSpace = 8;
	private int BoxAndSpace = BoxWidth + BoxSpace;
	private float fontInc = 2.5F * (BoxWidth/30); //Font size
	private int x; //A variable used to keep track of previous instructions to see if the current one can be "stacked"
	
	/**
	 * @return The preferred size of ProcessComp
	 */
	public Dimension getD() {
		return new Dimension(975,TopIndent+(num*LineSpace));
	}

	public void paint(Graphics g) {
		
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		this.currPos = model.getPos();
		g2.setColor(Color.BLACK);
		
		//Write qubit labels, with (in) written next to the input qubits
		for(int i = 0; i < num; i++) {
			if((i==3 && teleOrError.equals("Error Correction 7")) || (i==0 && teleOrError.equals("Teleportation"))
					|| i==0 && teleOrError.equals("Error Correction 5")) {
				g2.drawString((i+1)+"(in)", 20, TopIndent+(i*LineSpace));
			} else {
				g2.drawString((i+1)+"", 20, TopIndent+(i*LineSpace));
			}
		}
		//Need to change the font size between writing the labels on the qubits and the labels on the boxes
		Font oldFont = g2.getFont();
		g2.setFont(oldFont.deriveFont(oldFont.getSize()*fontInc));
		
		//Draw horizontal lines
		for(int i = 0; i < num; i++) {
			g2.drawLine(LeftIndent, TopIndent+(i*LineSpace),
					LeftIndent+(40*BoxAndSpace), TopIndent+(i*LineSpace));
		}
		
		int prev = -10;
		String prevInst = "";
		x = -1;
		//Draw control lines first so they are overwritten by the boxes
		for(int i = 0; i < processLength; i++) {
			String inst = instructions[i].getI();
			int control = instructions[i].getCon()-1;
			int target = instructions[i].getTar()-1;
			if(!(prev == control && 
					((prevInst.equals("Cnot") && inst.equals("CZ")) || 
							(prevInst.equals("CZ") && inst.equals("Cnot")) || 
							prevInst.equals(inst)))) { //Checks whether the current instruction can be "stacked"
				x++;
				prev = control;
				prevInst = inst;
			}
			if(control!=-2) {
				g2.setColor(Color.BLACK);
				g2.drawLine(LeftIndent+((x+1)*BoxAndSpace), TopIndent+(target*LineSpace),
						LeftIndent+((x+1)*BoxAndSpace), TopIndent+(control*LineSpace));
				g2.fillOval(LeftIndent+((x+1)*BoxAndSpace)-(blobSize/2), TopIndent+(control*LineSpace)-(blobSize/2),
						blobSize, blobSize);
			}
			prev = control;
			prevInst = inst;
		}
		
		
		prev = -10;
		prevInst = "";
		x = -1;
		//Draw boxes
		for(int i = 0; i < processLength; i++) {
			String inst = instructions[i].getI();
			int control = instructions[i].getCon();
			int target = instructions[i].getTar()-1;
			if(!(prev == control && 
					((prevInst.equals("Cnot") && inst.equals("CZ")) || 
							(prevInst.equals("CZ") && inst.equals("Cnot")) || 
							prevInst.equals(inst)))) { //Checks whether the current instruction can be "stacked"
				x++;
				prev = control;
				prevInst = inst;
			}
			g2.setColor(Color.WHITE); //To overwrite the background lines
			if(!(inst.equals("?")||inst.equals("Correct")||inst.equals("enc"))) { //Some instructions need bigger boxes
				if(inst.equals("M")) {
					g2.fillOval(LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2)-4, TopIndent+(target*LineSpace)-(BoxWidth/2)-2,
							BoxWidth+6, BoxWidth+6);
				} else {
					g2.fillRect(LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2), TopIndent+(target*LineSpace)-(BoxWidth/2),
							BoxWidth, BoxWidth);
				}
			} else {
				int a = 7;
				if(teleOrError.equals("Error Correction 5")) {
					a = 5;
				}
				g2.fillRect(LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2), TopIndent+(target*LineSpace)-(BoxWidth/2),
						BoxWidth, (a*LineSpace));
			}
			if(i!=currPos) {
				g2.setColor(Color.BLACK);
			} else {
				g2.setColor(Color.RED); //Red if this is the instruction next to be performed
			}
			if(!(inst.equals("?")||inst.equals("Correct")||inst.equals("enc"))) { //Some instructions need bigger boxes
				if(inst.equals("M")) {
					g2.drawOval(LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2)-4, TopIndent+(target*LineSpace)-(BoxWidth/2)-2,
							BoxWidth+6, BoxWidth+6);
				} else {
					g2.drawRect(LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2), TopIndent+(target*LineSpace)-(BoxWidth/2),
							BoxWidth, BoxWidth);
				}
			} else {
				int a = 7;
				if(teleOrError.equals("Error Correction 5")) {
					a = 5;
				}
				g2.drawRect(LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2), TopIndent+(target*LineSpace)-(BoxWidth/2),
						BoxWidth, (a*LineSpace));
			}
			switch(inst) { //Draw the right character on the box saying which instruction it is
			case "H": g2.drawString("H", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "Cnot": g2.drawString("X", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "M": g2.drawString("M", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "CZ": g2.drawString("Z", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "X": g2.drawString("X", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "Y": g2.drawString("Y", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "Z": g2.drawString("Z", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "?": g2.drawString("?", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "Correct": 
				if(teleOrError.equals("Error Correction 7")) {
					g2.drawString("c", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+0)*LineSpace)+(BoxWidth/2));
					g2.drawString("o", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+1)*LineSpace)+(BoxWidth/2)); 
					g2.drawString("r", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+2)*LineSpace)+(BoxWidth/2));
					g2.drawString("r", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+3)*LineSpace)+(BoxWidth/2)); 
					g2.drawString("e", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+4)*LineSpace)+(BoxWidth/2));
					g2.drawString("c", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+5)*LineSpace)+(BoxWidth/2)); 
					g2.drawString("t", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+6)*LineSpace)+(BoxWidth/2));
				} else {
					g2.drawString("l", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+0)*LineSpace)+(BoxWidth/2));
					g2.drawString("o", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+1)*LineSpace)+(BoxWidth/2)); 
					g2.drawString("g", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+2)*LineSpace)+(BoxWidth/2));
					g2.drawString("i", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+3)*LineSpace)+(BoxWidth/2)); 
					g2.drawString("c", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+4)*LineSpace)+(BoxWidth/2));
				} break;
			case "enc":
				if(teleOrError.equals("Error Correction 7")) {
					g2.drawString("e", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+0)*LineSpace)+(BoxWidth/2));
					g2.drawString("n", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+1)*LineSpace)+(BoxWidth/2)); 
					g2.drawString("c", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+2)*LineSpace)+(BoxWidth/2));
					g2.drawString("o", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+3)*LineSpace)+(BoxWidth/2)); 
					g2.drawString("d", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+4)*LineSpace)+(BoxWidth/2));
					g2.drawString("e", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+5)*LineSpace)+(BoxWidth/2)); 
				} else {
					g2.drawString("e", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+1)*LineSpace)+(BoxWidth/2));
					g2.drawString("n", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+2)*LineSpace)+(BoxWidth/2)); 
					g2.drawString("c", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
							TopIndent+((target+3)*LineSpace)+(BoxWidth/2));
				} break;
			case "": break;
			default: System.out.println("Instruction unrecognised in ProcessComp.paint()"); break;
			}
			prev = control;
			prevInst = inst;
			
		}
		//Need to change the font size between writing the labels on the qubits and the labels on the boxes
		oldFont = g2.getFont();
		g2.setFont(oldFont.deriveFont(oldFont.getSize()/fontInc));
	}

	public ProcessComp(Model model) {
		this.model = model;
		this.teleOrError = model.teleOrError();
		this.instructions = model.getInstructions();
		this.processLength = this.instructions.length;
		this.currPos = model.getPos();
		num = model.getNum();
		x = -1;
	}

}
