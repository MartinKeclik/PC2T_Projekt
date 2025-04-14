package PC2T_Projekt;

public class CyberStudent extends Student {

	CyberStudent(String name, String surname, int birthYear) {
		super(name, surname, birthYear);
		this.spec = "Kyberbezpecnost";
	}
	
	public static String specAbility() {
		return "Tohle neni morseova abeceda";
	}

}
