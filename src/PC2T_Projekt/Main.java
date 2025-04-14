package PC2T_Projekt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Boolean isRunning = true;
		Scanner sc = new Scanner(System.in);
		HashMap<Integer, Student> studentDatabase = new HashMap<Integer, Student>();
		
		while(isRunning) {
			System.out.println("1:\tPridat studenta" + 
					"\n2:\tZadat novou znamku" +
					"\n3:\tVyhodit studenta" +
					"\n4:\tVypsat informace o studentovi" +
					"\n5:\tSpusti specialni funkci studenta" +
					"\n6:\tVypsat abecedni seznam studentu" +
					"\n7:\tCelkovy studijni prumer daneho oboru" +
					"\n8:\tUlozit studenta do souboru" + 
					"\n9:\tNacist studenta ze souboru" +
					"\n10:\tUkoncit program");
			
			int choice = sc.nextInt();
			switch(choice) {
			case 1:
				System.out.println("Zadejte jmeno studenta:\n");
				String name = sc.next();
				System.out.println("Zadejte prijmeni studenta:\n");
				String surname = sc.next();
				System.out.println("Zadejte rok narozeni studenta:\n");
				int year = sc.nextInt();
				System.out.println("Zadejte obor (T... telekomunikace, K... kyberpecnost:");
				String spec1 = sc.next();
				int id = Database.setId(studentDatabase);
				if (spec1.equals("T") || spec1.equals("t"))
					studentDatabase.put(id, new TeleStudent(name, surname, year));
				else if (spec1.equals("K") || spec1.equals("k")) 
					studentDatabase.put(id, new CyberStudent(name, surname, year));
				else
					System.out.println("Nezadali jste zadny z nabizenych oboru...");
				break;
			case 2:
				System.out.println("Zadejte ID studenta:");
				int id2 = sc.nextInt();
				if (studentDatabase.containsKey(id2)) {
					System.out.println("Zadejte znamku (1 - 5):");
					int grade = sc.nextInt();
					if(grade > 0 && grade < 6) {
						studentDatabase.get(id2).addGrade(grade);
						studentDatabase.get(id2).setGradeAvg(studentDatabase.get(id2).computeGradeAvg());
					} else 
						System.out.println("Nepovolena znamka...");
				} else 
					System.out.println("Databaze neobsahuje studenta s id " + id2);
				
				break;
				
			case 3:
				System.out.println("Zadejte ID studenta:");
				int id3 = sc.nextInt();
				if(studentDatabase.containsKey(id3))
					studentDatabase.remove(id3);
				else
					System.out.println("Databaze neobsahuje studenta s ID " + id3);
				break;
			case 4:
				System.out.println("Zadejte ID studenta:\n");
				int id4 = sc.nextInt();
				if (studentDatabase.containsKey(id4)) 
					System.out.println(Database.getInfo(studentDatabase, id4));
				else 
					System.out.println("Databaze neobsahuje studenta s id " + id4);
				break;
			case 5:
				System.out.println("Zadejte ID studenta:");
				int id5 = sc.nextInt();
				if(studentDatabase.get(id5).getSpec().equals("Telekomunikace"))
					System.out.println(studentDatabase.get(id5).getName() +
							" " + studentDatabase.get(id5).getSurname() +
							" v Morseove abecede:\n " + TeleStudent.specAbility(studentDatabase, id5));
				else
					System.out.println(CyberStudent.specAbility());
				break;
			case 6:
				
				break;
			case 7:
				System.out.println("Zadejte pozadovany obor (T... telekomunikace, K... kyberbezpecnost): ");
				String spec7 = sc.next();
				if (spec7.equals("T") || spec7.equals("t"))
					System.out.println("Celkovy prumer studentu telekomunikace:\t" + Database.getSpecGradeAvg(studentDatabase, "Telekomunikace"));
				else if (spec7.equals("K") || spec7.equals("k"))
					System.out.println("Celkovy prumer studentu kyberbezpecnosti:\t" + Database.getSpecGradeAvg(studentDatabase, "Kyberbezpecnost"));
				else
					System.out.println("Nezadali jste ani jeden z nabizenych oboru");
				break;
			case 8:
				break;
			case 9:
				break;
			case 10:
				isRunning = false;
				break;
			}
		}
	}
}

