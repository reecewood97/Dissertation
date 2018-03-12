package project;

public class Instruction {
	//This class is for storing instructions for the teleportation and error correction processes
	
	private String inst; //The String representing the instruction
	private int control; //The control qubit for this instruction
	private int target;  //The target qubit for this instruction

	public Instruction(String inst, int control, int target) {
		this.inst = inst;
		this.control = control;
		this.target = target;
	}
	
	/**
	 * For when no control qubit is necessary
	 */
	public Instruction(String inst, int target) {
		this.inst = inst;
		this.control = -1;
		this.target = target;
	}
	
	/**
	 * @return The string representing the instruction
	 */
	public String getI(){
		return inst;
	}
	
	/**
	 * @return The control qubit
	 */
	public int getCon() {
		return control;
	}

	/**
	 * @return The target qubit
	 */
	public int getTar() {
		return target;
	}
}
