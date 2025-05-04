package PC2T_Projekt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Database {	
	public HashMap<Integer, Student> studentDatabase;
	Database(){
		studentDatabase = new HashMap<Integer, Student>();
	}
	
	public int setId() {
		if(!studentDatabase.isEmpty())
			return Collections.max(studentDatabase.keySet())+1;
		else
			return 1;
	}
	
	public void addStudent(String name, String surname, int year, String spec) {
		if (spec.toUpperCase().equals("T"))
			studentDatabase.put(setId(), new TeleStudent(name, surname, year));
		else
			studentDatabase.put(setId(), new CyberStudent(name, surname, year));
	}
	
	public Student getStudent(int id) {
		return studentDatabase.get(id);
	}
		
	public void removeStudent(int id) {
		studentDatabase.remove(id);
	}
		
	public String getInfo(int id) {
		return "ID: " + id + 
				"\nJmeno: " + studentDatabase.get(id).getName() +
				"\nPrijmeni: " + studentDatabase.get(id).getSurname() + 
				"\nRok narozeni: " + studentDatabase.get(id).getBirthYear() +
				"\nObor: " + studentDatabase.get(id).getSpec() +
				"\nZnamky: " + studentDatabase.get(id).getGrades() + 
				"\nStudijni prumer: " + studentDatabase.get(id).getGradeAvg(); 
	}
	
	
	public float getSpecGradeAvg(String spec) {
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
	
	
	public String sortByLastName() {
		if(!studentDatabase.isEmpty()) {
			List<Student> teleStudents = new ArrayList<Student>();
			List<Student> cyberStudents = new ArrayList<Student>();
			String sortedStudents = "Obor Telekomunikace:\n";
			
			for(int id:studentDatabase.keySet()) {
				if(studentDatabase.get(id).getSpec().equals("Telekomunikace"))
					teleStudents.add(studentDatabase.get(id));
				else
					cyberStudents.add(studentDatabase.get(id));
			}
			
			teleStudents.sort(Comparator.comparing(Student::getSurname));
			cyberStudents.sort(Comparator.comparing(Student::getSurname));
			
			
			for(Student stud:teleStudents) 
				sortedStudents += "\nPrijmeni: " + stud.getSurname() 
				+", jmeno: " + stud.getName()
				+", rok narozeni: " + stud.getBirthYear() 
				+", specializace: " + stud.getSpec();
			
			sortedStudents += "\n\nObor: Kyberbezpecnost:\n";
			
			for(Student stud:cyberStudents)
				sortedStudents += "\nPrijmeni: " + stud.getSurname() 
				+", jmeno: " + stud.getName() 
				+", rok narozeni: " + stud.getBirthYear() 
				+", specializace: " + stud.getSpec();
			
			return sortedStudents;
		} else
			return "Databaze je prazdna...";
	}
	
	
	public void saveToFile(int id) {
		if(studentDatabase.containsKey(id)) {
			try {
				FileWriter w = new FileWriter(id + ".txt");
				w.write(getInfo(id));
				w.close();
				System.out.println("Student ulozen...");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			System.out.println("Databaze neobsahuje studenta s " + id);
	}
	
	public void loadFromFile(int id) {
		File file = new File(System.getProperty("user.dir"), id + ".txt");
		if(file.exists()) {
			String name = "", surname = "", spec = "";
			int birthYear = 0;
			float gradeAvg = 0.0f;
			List<Integer> grades = new ArrayList<>();
			
			String line;
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
					while((line = reader.readLine())!=null) {
						if(line.startsWith("Jmeno: "))
							name = line.substring(7).trim();
						else if(line.startsWith("Prijmeni: "))
							surname = line.substring(10).trim();
						else if(line.startsWith("Rok narozeni: "))
							birthYear = Integer.parseInt(line.substring(14).trim());
						else if(line.startsWith("Obor: ")) {
							spec = line.substring(6).trim();
							if (spec.equals("Telekomunikace"))
								studentDatabase.put(id, new TeleStudent(name, surname, birthYear));
							else
								studentDatabase.put(id, new CyberStudent(name, surname, birthYear));
						} else if(line.startsWith("Znamky: ")) {
							String data = line.substring(line.indexOf(":") + 1).trim();
							if(!data.isEmpty()) {
								String[] gradeStr = data.split(",");
			                    for (String grade : gradeStr) {
			                        studentDatabase.get(id).addGrade(Integer.parseInt(grade.trim()));
			                    }
			                    studentDatabase.get(id).setGradeAvg(studentDatabase.get(id).computeGradeAvg());
							}	
						}
					}
				}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			System.out.println("Student s ID " + id + " nenalezen");
	}
	
	public Boolean isThereSuchStudent(int id) {
		if (studentDatabase.containsKey(id))
			return true;
		else
			return false;
	}
	
	public String getNumOfStudents() {
		int tStudents = 0, cStudents = 0;
		for (int id : studentDatabase.keySet()) {
			if(studentDatabase.get(id).getSpec().equals("Telekomunikace"))
				tStudents++;
			else
				cStudents++;
		}
		return "Celkovy pocet studentu: " + (tStudents + cStudents) + 
				"\nPocet studentu telekomunikace: " + tStudents +
				"\nPocet studentu kyberbezpecnosti: " + cStudents;
	}
	
	public void loadFromSQL() {
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:studentDatabase.db")){
			createTableIfNotExists(conn);
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM studentDatabase");
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String spec = rs.getString("spec");
				int birthYear = rs.getInt("birthYear");
				String gradesInString = rs.getString("grades");
				Float gradeAvg = rs.getFloat("gradeAvg");
				
				ArrayList<Integer> grades = new ArrayList<>();
				
				if(gradesInString != null && !gradesInString.isEmpty()) {
					for (String s : gradesInString.split(",")) {
						try {
							grades.add(Integer.parseInt(s.trim()));
						} catch (NumberFormatException e) {
							System.out.println("Nezadali jste spravnou znamku...");
						}
					}
				}
					
				Student stud;
				if (spec.equalsIgnoreCase("Telekomunikace"))
					stud = new TeleStudent(name, surname, birthYear);
				else if (spec.equalsIgnoreCase("Kyberbezpecnost"))
					stud = new CyberStudent(name, surname, birthYear);
				else {
					System.out.println("Neznama specializace");
					continue;
				}
				
				studentDatabase.put(id, stud);
				
				for (int g : grades)
					studentDatabase.get(id).addGrade(g);
				studentDatabase.get(id).setGradeAvg(gradeAvg);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<Integer, Student> getStudentDatabase(){
		return studentDatabase;
	}
	
	public void saveToSQL() {
		try(Connection conn = DriverManager.getConnection("jdbc:sqlite:studentDatabase.db")){
			conn.setAutoCommit(false);
			
			Statement clearStmt = conn.createStatement();
			clearStmt.executeUpdate("DELETE FROM studentDatabase");
			
			String insertSQL = "INSERT INTO studentDatabase "
					+ "(id, name, surname, spec, birthYear, grades, gradeAvg) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement pstmt = conn.prepareStatement(insertSQL);
			
			for (HashMap.Entry<Integer, Student> entry : studentDatabase.entrySet()) {
				int id = entry.getKey();
				Student stud = entry.getValue();
				
				pstmt.setInt(1, id);
				pstmt.setString(2, stud.getName());
				pstmt.setString(3, stud.getSurname());
				pstmt.setString(4, stud.getSpec());
				pstmt.setInt(5, stud.getBirthYear());
				pstmt.setString(6, stud.getGrades());
				pstmt.setFloat(7, stud.getGradeAvg());
				
				pstmt.addBatch();
			}
			
			pstmt.executeBatch();
			conn.commit();
			System.out.println("Databaze ulozena...");
		} catch (SQLException e) {
			System.out.println("Nekde se stala chyba :)");
			e.printStackTrace();
		}
	}
	
	private void createTableIfNotExists(Connection conn) throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS studentDatabase (" +
		             "id INTEGER PRIMARY KEY," +
		             "name TEXT NOT NULL," +
		             "surname TEXT NOT NULL," +
		             "spec TEXT NOT NULL," +
		             "birthyear INTEGER NOT NULL," +
		             "grades TEXT," +
		             "gradeAvg FLOAT)";
		             //")";
		
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
	}
}
	

