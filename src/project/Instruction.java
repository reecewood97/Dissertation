package project;

public class Instruction {
	
	private String inst;
	private int control;
	private int target;

	public Instruction(String inst, int control, int target) {
		this.inst = inst;
		this.control = control;
		this.target = target;
	}
	
	public Instruction(String inst, int target) {
		this.inst = inst;
		this.control = -1;
		this.target = target;
	}
	
	public String getI(){
		return inst;
	}
	
	public int getCon() {
		return control;
	}

	public int getTar() {
		return target;
	}
}
