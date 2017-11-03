package project;

public class Test {

	public static void main(String[] args) throws Exception {
		Model model = new Model(3);
		model.teleInit(new Complex(0,0),new Complex(1,0)); //a1 = 3, a2 = 2, b1 = 3
		model.H(2);
		model.Cnot(2,1);
		model.Cnot(3, 2);
		model.H(3);
		System.out.println("done");
	}
	
}
