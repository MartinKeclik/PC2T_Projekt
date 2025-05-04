package PC2T_Projekt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		Boolean isRunning = true;
		Scanner sc = new Scanner(System.in);
		Database studentDatabase = new Database();
		studentDatabase.loadFromSQL();
		
		while(isRunning) {
			System.out.println("\n__________________________________________________" + 
					"\n1:\tPridat studenta" + 
					"\n2:\tZadat novou znamku" +
					"\n3:\tVyhodit studenta" +
					"\n4:\tVypsat informace o studentovi" +
					"\n5:\tSpusti specialni funkci studenta" +
					"\n6:\tVypsat abecedni seznam studentu" +
					"\n7:\tCelkovy studijni prumer daneho oboru" +
					"\n8:\tVypsat celkovy pocet studentu" +
					"\n9:\tUlozit studenta do souboru" + 
					"\n10:\tNacist studenta ze souboru" +
					"\n11:\tUkoncit program");
			
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
				if (spec1.toUpperCase().equals("T") || spec1.toUpperCase().equals("K"))
					studentDatabase.addStudent(name, surname, year, spec1);
				else
					System.out.println("Nezadali jste zadny z nabizenych oboru...");
				break;
				
			case 2:
				System.out.println("Zadejte ID studenta:");
				int id2 = sc.nextInt();
				
				if(studentDatabase.isThereSuchStudent(id2)) {
					System.out.println("Zadejte znamku (1 - 5)");
					int grade = sc.nextInt();
					if(grade > 0 && grade < 6) {
						studentDatabase.getStudent(id2).addGrade(grade);
						studentDatabase.getStudent(id2).setGradeAvg(studentDatabase.getStudent(id2).computeGradeAvg());
					} else
						System.out.println("Vyberte z nabizenych znamek...");
				}
				break;
				
			case 3:
				System.out.println("Zadejte ID studenta:");
				int id3 = sc.nextInt();
				
				if(studentDatabase.isThereSuchStudent(id3))
					studentDatabase.removeStudent(id3);
				else
					System.out.println("Databaze neobsahuje studenta s ID " + id3);
				break;
				
			case 4:
				System.out.println("Zadejte ID studenta:\n");
				int id4 = sc.nextInt();
				
				if (studentDatabase.isThereSuchStudent(id4))
					System.out.println(studentDatabase.getInfo(id4));
				else
					System.out.println("Databaze neobsahuje studenta s ID " + id4);
				break;
				
			case 5:
				System.out.println("Zadejte ID studenta:");
				int id5 = sc.nextInt();
				
				if(studentDatabase.isThereSuchStudent(id5))
					System.out.println(studentDatabase.getStudent(id5).specAbility(studentDatabase, id5));
				else
					System.out.println("Databaze neobsahuje studenta s ID " + id5);
				break;
				
			case 6:
				System.out.println(studentDatabase.sortByLastName());
				break;
				
			case 7:
				System.out.println("Zadejte pozadovany obor (T... telekomunikace, K... kyberbezpecnost): ");
				String spec7 = sc.next();
				
				if (spec7.toUpperCase().equals("T"))
					System.out.println("Celkovy prumer studentu telekomunikace:\t" + studentDatabase.getSpecGradeAvg("Telekomunikace"));
				else if (spec7.toUpperCase().equals("K"))
					System.out.println("Celkovy prumer studentu kyberbezpecnosti:\t" + studentDatabase.getSpecGradeAvg("Kyberbezpecnost"));
				else
					System.out.println("Nezadali jste ani jeden z nabizenych oboru");
				break;
				
			case 8:
				System.out.println(studentDatabase.getNumOfStudents());
				break;
				
			case 9:
				System.out.println("Zadejte ID studenta: ");
				int id9 = sc.nextInt();
				studentDatabase.saveToFile(id9);
				break;
				
			case 10:
				System.out.println("Zadejte ID studenta: ");
				int id10 = sc.nextInt();
				studentDatabase.loadFromFile(id10);
				break;
				
			case 11:
				studentDatabase.saveToSQL();
				System.out.println("Konec");
				isRunning = false;
				break;
				
			default:
				System.out.println("Vyberte z nabizenych moznosti");
				break;			
			}
		}
	}
}

