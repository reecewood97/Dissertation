package project;

import java.awt.*;

public class ProcessComp extends Component {
	
	private static final long serialVersionUID = 1L;
	private Model model;
	private Instruction[] instructions;
	private int num;
	private int currPos;
	private int processLength;
	private int blobSize = 10;
	private int LineSpace = 40;
	private int LeftIndent = 50;
	private int TopIndent = 30;
	private int BoxWidth = 30; // Also used for height
	private int BoxSpace = 20;
	private int BoxAndSpace = BoxWidth + BoxSpace;
	private float fontInc = 2.5F * (BoxWidth/30);
	private int x;
	
	
	public Dimension getD() {
		return new Dimension(LeftIndent+(700),TopIndent+(num*LineSpace)); //TODO fudge this too
	}

	public void paint(Graphics g) {
		
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		this.currPos = model.getPos();
		g2.setColor(Color.BLACK);
		
		//Write qubit labels
		for(int i = 0; i < num; i++) {
			if(i==3 && processLength>10) {
				g2.drawString((i+1)+"(in)", 20, TopIndent+(i*LineSpace));
			} else {
				g2.drawString((i+1)+"", 20, TopIndent+(i*LineSpace));
			}
		}
		Font oldFont = g2.getFont();
		g2.setFont(oldFont.deriveFont(oldFont.getSize()*fontInc));
		
		//Draw horizontal lines TODO fudge this when know how long they need to be
		for(int i = 0; i < num; i++) {
			g2.drawLine(LeftIndent, TopIndent+(i*LineSpace),
					LeftIndent+((processLength+1)*(BoxAndSpace)), TopIndent+(i*LineSpace));
		}
		
		int prev = -10;
		String prevInst = "";
		x = -1;
		//Draw control lines first so they are overwritten by the boxes
		for(int i = 0; i < processLength; i++) {
			String inst = instructions[i].getI();
			int control = instructions[i].getCon()-1;
			int target = instructions[i].getTar()-1;
			if(prev != control || !prevInst.equals(inst)) {
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
			if(prev != control || !prevInst.equals(inst)) {
				x++;
				prev = control;
				prevInst = inst;
			}
			g2.setColor(Color.WHITE);
			if(!(inst.equals("?")||inst.equals("Correct"))) {
				g2.fillRect(LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2), TopIndent+(target*LineSpace)-(BoxWidth/2),
						BoxWidth, BoxWidth);
			} else {
				g2.fillRect(LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2), TopIndent+(target*LineSpace)-(BoxWidth/2),
						BoxWidth, (7*LineSpace));
			}
			if(i!=currPos) {
				g2.setColor(Color.BLACK);
			} else {
				g2.setColor(Color.RED);
			}
			if(!(inst.equals("?")||inst.equals("Correct"))) {
				g2.drawRect(LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2), TopIndent+(target*LineSpace)-(BoxWidth/2),
						BoxWidth, BoxWidth);
			} else {
				g2.drawRect(LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2), TopIndent+(target*LineSpace)-(BoxWidth/2),
						BoxWidth, (7*LineSpace));
			}
			switch(inst) {
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
			case "M0": g2.drawString("M0", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "M1": g2.drawString("M1", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "M2": g2.drawString("M2", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "N0": g2.drawString("N0", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "N1": g2.drawString("N1", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "N2": g2.drawString("N2", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "?": g2.drawString("?", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "Correct": g2.drawString("c", LeftIndent+((x+1)*BoxAndSpace)-(BoxWidth/2),
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
					TopIndent+((target+6)*LineSpace)+(BoxWidth/2)); break;
			case "": break;
			default: System.out.println("Instruction unrecognised in ProcessComp.paint()"); break;
			}
			prev = control;
			prevInst = inst;
			
		}
		oldFont = g2.getFont();
		g2.setFont(oldFont.deriveFont(oldFont.getSize()/fontInc));
	}

	public ProcessComp(Model model) {
		this.model = model;
		this.instructions = model.getInstructions();
		this.processLength = this.instructions.length;
		this.currPos = model.getPos();
		num = model.getNum();
		x = -1;
	}

}
