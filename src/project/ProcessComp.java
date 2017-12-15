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
	
	
	public Dimension getD() {
		return new Dimension(LeftIndent+((processLength+1)*(BoxAndSpace)),TopIndent+((num+1)*LineSpace));
	}

	public void paint(Graphics g) {
		
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		Font oldFont = g2.getFont();
		g2.setFont(oldFont.deriveFont(oldFont.getSize()*fontInc));
		this.currPos = model.getPos();
		g2.setColor(Color.BLACK);
		for(int i = 0; i < num; i++) {
			g2.drawLine(LeftIndent, TopIndent+(i*LineSpace),
					LeftIndent+((processLength+1)*(BoxAndSpace)), TopIndent+(i*LineSpace));
		}
		for(int i = 0; i < processLength; i ++) {
			String inst = instructions[i].getI();
			int control = instructions[i].getCon()-1;
			int target = instructions[i].getTar()-1;
			if(control!=-2) {
				if(i!=currPos) {
					g2.setColor(Color.BLACK);
				} else {
					g2.setColor(Color.RED);
				}
				g2.drawLine(LeftIndent+((i+1)*BoxAndSpace), TopIndent+(target*LineSpace),
						LeftIndent+((i+1)*BoxAndSpace), TopIndent+(control*LineSpace));
				g2.fillOval(LeftIndent+((i+1)*BoxAndSpace)-(blobSize/2), TopIndent+(control*LineSpace)-(blobSize/2),
						blobSize, blobSize);
			}
			g2.setColor(Color.WHITE);
			g2.fillRect(LeftIndent+((i+1)*BoxAndSpace)-(BoxWidth/2), TopIndent+(target*LineSpace)-(BoxWidth/2),
					BoxWidth, BoxWidth);
			if(i!=currPos) {
				g2.setColor(Color.BLACK);
			} else {
				g2.setColor(Color.RED);
			}
			g2.drawRect(LeftIndent+((i+1)*BoxAndSpace)-(BoxWidth/2), TopIndent+(target*LineSpace)-(BoxWidth/2),
					BoxWidth, BoxWidth);
			switch(inst) {
			case "H": g2.drawString("H", LeftIndent+((i+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "Cnot": g2.drawString("X", LeftIndent+((i+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "M": g2.drawString("M", LeftIndent+((i+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			case "CZ": g2.drawString("Z", LeftIndent+((i+1)*BoxAndSpace)-(BoxWidth/2),
					TopIndent+(target*LineSpace)+(BoxWidth/2)); break;
			default: System.out.println("Instruction unrecognised in ProcessComp.paint()"); break;
			}
			
		}
	}

	public ProcessComp(Model model) {
		this.model = model;
		this.instructions = model.getInstructions();
		this.processLength = this.instructions.length;
		this.currPos = model.getPos();
		num = model.getNum();
	}

}
