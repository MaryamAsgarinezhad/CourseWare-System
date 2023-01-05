package Logic;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Faculty {

    public transient static List<Faculty> allFaculties=new ArrayList<>();
    public static int[] ans=new int[2000];
    @Expose
    public String name;
    public int number;
    public String boss;
    public String vice;
    public List<String> professors=new ArrayList<>();
    public List<Integer> courses=new ArrayList<>();
    public List<String> students=new ArrayList<>();

    public Faculty(){

    }
    public Faculty(String name, int number){
        this.name=name;
        this.number=number;
    }
    public Faculty(String name, int number, String boss, String vice, List<String> professors, List<Integer> courses, List<String > students) {

        this.name = name;
        this.number = number;
        this.boss = boss;
        this.vice = vice;
        this.professors = professors;
        this.courses = courses;
        this.students = students;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public void setVice(String vice) {
        this.vice = vice;
    }

    public void setProfessors(List<String> professors) {
        this.professors = professors;
    }

    public void setCourses(List<Integer> courses) {
        this.courses = courses;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getBoss() {
        return boss;
    }

    public String getVice() {
        return vice;
    }

    public List<String> getProfessors() {
        return professors;
    }

    public List<Integer> getCourses() {
        return courses;
    }

    public List<String> getStudents() {
        return students;
    }
}
