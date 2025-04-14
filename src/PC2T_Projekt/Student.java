package PC2T_Projekt;

import java.util.ArrayList;

public class Student {
	String name, surname, spec;
	int birthYear;
	float gradeAvg;
	ArrayList<Integer> grades;
	
	Student(String name, String surname, int birthYear) {
		this.name = name;
		this.surname = surname;
		this.birthYear = birthYear;
		this.gradeAvg = 0.f;
		this.grades = new ArrayList<Integer>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public int getBirthYear() {
		return this.birthYear;
	}
	
	public String getSpec() {
		return this.spec;
	}
	
	public float getGradeAvg() {
		return this.gradeAvg;
	}
	
	public ArrayList<Integer> getGrades(){
		return this.grades;
	}
	
	public void addGrade(int grade) {
		this.grades.add(grade);
	}
	
	public float computeGradeAvg() {
		float numOfGrades = this.grades.size();
		float gradesSum = 0.f;
		if(numOfGrades != 0) {
			for(int grade : this.grades) {
				gradesSum += grade;
			}
			return gradesSum/numOfGrades;
		} else {
			return 0.f;
		}
	}
	
	public void setGradeAvg(float gradeAvg) {
		this.gradeAvg = gradeAvg;		
	}
	
	
	/*
	public String specAbility() {
		return "Toto je specialni funkce studenta";
	}
	*/
}
