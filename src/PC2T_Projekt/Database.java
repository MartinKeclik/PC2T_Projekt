package PC2T_Projekt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class Database {	
	Database(){
		HashMap<Integer, Student> studentDatabase = new HashMap<Integer,Student>();
	}
	
	
	public static int setId(HashMap<Integer, Student> studentDatabase) {
		if(!studentDatabase.isEmpty())
			return Collections.max(studentDatabase.keySet())+1;
		else
			return 1;
	}
	
	
	public static String getInfo(HashMap<Integer, Student> studentDatabase, int id) {
		return "ID:\t " + id + 
				"\nJmeno:\t" + studentDatabase.get(id).getName() +
				"\nPrijmeni:\t" + studentDatabase.get(id).getSurname() + 
				"\nRok narozeni:\t" + studentDatabase.get(id).getBirthYear() +
				"\nObor:\t" + studentDatabase.get(id).getSpec() +
				"\nZnamky:\t" + studentDatabase.get(id).getGrades() + 
				"\nStudijni prumer:\t" + studentDatabase.get(id).getGradeAvg();
	}
	
	public static float getSpecGradeAvg(HashMap<Integer, Student> studentDatabase, String spec) {
		if(!studentDatabase.isEmpty()) {
			Set <Integer> ids = studentDatabase.keySet();
			int numOfStudents = 0;
			float avgSum = 0;
			for (int id : ids) {
				if (studentDatabase.get(id).getSpec().equals(spec)) {
					avgSum += studentDatabase.get(id).getGradeAvg();
					numOfStudents++;
				}
			
			}
			return avgSum/numOfStudents;
		} else
			return 0.f;
	}
}
