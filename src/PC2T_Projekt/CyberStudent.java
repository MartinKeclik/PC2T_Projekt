package PC2T_Projekt;

public class CyberStudent extends Student {

	CyberStudent(String name, String surname, int birthYear) {
		super(name, surname, birthYear);
		this.spec = "Kyberbezpecnost";
	}
	
	public static String specAbility() {
		return "Tohle neni morseova abeceda";
	}

	@Override
	public String specAbility(Database studentDatabase, int id) {
		Student student = studentDatabase.getStudent(id);
	    if (student == null) {
	        return "Student s ID " + id + " neexistuje.";
	    }
	    return "Jmeno: " + student.getName() + ", HashCode: " + student.getName().hashCode() +
	           "\nPrijmeni: " + student.getSurname() + ", HashCode: " + student.getSurname().hashCode();
	}

}
