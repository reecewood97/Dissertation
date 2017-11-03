package project;

public class Test {

	public static void main(String[] args) throws Exception {
		Model model = new Model(3);
		model.teleInit(new Complex(1,0),new Complex(0,0));
		model.H(1);
		model.H(2);
		model.H(3);
		System.out.println("done");
	}
	
}
