package PC2T_Projekt;

import java.util.HashMap;


public class TeleStudent extends Student{

	private static final HashMap<Character, String> MORSE_CODE = new HashMap<>();

    static {
        MORSE_CODE.put('A', ".-");
        MORSE_CODE.put('B', "-...");
        MORSE_CODE.put('C', "-.-.");
        MORSE_CODE.put('D', "-..");
        MORSE_CODE.put('E', ".");
        MORSE_CODE.put('F', "..-.");
        MORSE_CODE.put('G', "--.");
        MORSE_CODE.put('H', "....");
        MORSE_CODE.put('I', "..");
        MORSE_CODE.put('J', ".---");
        MORSE_CODE.put('K', "-.-");
        MORSE_CODE.put('L', ".-..");
        MORSE_CODE.put('M', "--");
        MORSE_CODE.put('N', "-.");
        MORSE_CODE.put('O', "---");
        MORSE_CODE.put('P', ".--.");
        MORSE_CODE.put('Q', "--.-");
        MORSE_CODE.put('R', ".-.");
        MORSE_CODE.put('S', "...");
        MORSE_CODE.put('T', "-");
        MORSE_CODE.put('U', "..-");
        MORSE_CODE.put('V', "...-");
        MORSE_CODE.put('W', ".--");
        MORSE_CODE.put('X', "-..-");
        MORSE_CODE.put('Y', "-.--");
        MORSE_CODE.put('Z', "--..");
        MORSE_CODE.put('0', "-----");
        MORSE_CODE.put('1', ".----");
        MORSE_CODE.put('2', "..---");
        MORSE_CODE.put('3', "...--");
        MORSE_CODE.put('4', "....-");
        MORSE_CODE.put('5', ".....");
        MORSE_CODE.put('6', "-....");
        MORSE_CODE.put('7', "--...");
        MORSE_CODE.put('8', "---..");
        MORSE_CODE.put('9', "----.");
        MORSE_CODE.put(' ', "//");
    }

	
	TeleStudent(String name, String surname, int birthYear) {
		super(name, surname, birthYear);
		this.spec = "Telekomunikace";
	}
	
	
	@Override
	public String specAbility(Database studentDatabase, int id) {
		if (studentDatabase.isThereSuchStudent(id)) {
			String name = studentDatabase.getStudent(id).getName();
			String surname = studentDatabase.getStudent(id).getSurname();
			String nameInMorse = ""; String surnameInMorse = "";
			
			if(!name.isEmpty()) {
				for (char ch:name.toUpperCase().toCharArray())
					nameInMorse += (MORSE_CODE.get(ch)) + " ";
			}
			
			if(!surname.isEmpty()) {
				for (char ch:surname.toUpperCase().toCharArray())
					surnameInMorse += (MORSE_CODE.get(ch)) + " ";
			}
			return "Jmeno: " + studentDatabase.getStudent(id).getName() + 
					", Morseova abeceda: " + nameInMorse + 
					"\nPrijmeni: " + studentDatabase.getStudent(id).getSurname() + 
					", Morseova abeceda: " + surnameInMorse;			
		} else
			return "Databaze neobsahuje studenta s ID " + id;
	}
}
