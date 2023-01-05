package Logic;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Person{
    public static List<String> professors=new ArrayList<>();
    public String IDNumber;
    public List<Integer> courses;
    public ProfessorDegree professorDegree;
    public String phoneNumber;
    public int roomNumber;
    public boolean isSupervisor;
    public String requests="";
    public String AdminMessages="";
    public List<String> objects=new ArrayList<>();
    public int profNumber;

    public String RequestforLesson="";

    public void setAdminMessages(String adminMessages) {
        AdminMessages = adminMessages;
    }

    public String getAdminMessages() {
        return AdminMessages;
    }

    public String getRequestforLesson() {
        return RequestforLesson;
    }

    public void setRequestforLesson(String requestforLesson) {
        RequestforLesson = requestforLesson;
    }

    public static List<String> getProfessors() {
        return professors;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public String getRequests() {
        return requests;
    }

    public static void setProfessors(List<String> professors) {
        Professor.professors = professors;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public void setRequests(String requests) {
        this.requests = requests;
    }

    public int getProfNumber() {
        return profNumber;
    }

    public void setProfNumber(int profNumber) {
        this.profNumber = profNumber;
    }

    public void setObjects(List<String > objects) {
        this.objects = objects;
    }

    public List<String > getObjects() {
        return objects;
    }

    public void setCourses(List<Integer> courses) {
        this.courses = courses;
    }

    public void setProfessorDegree(ProfessorDegree professorDegree) {
        this.professorDegree = professorDegree;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setSupervisor(boolean supervisor) {
        isSupervisor = supervisor;
    }

    public List<Integer> getCourses() {
        return courses;
    }

    public ProfessorDegree getProfessorDegree() {
        return professorDegree;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isSupervisor() {
        return isSupervisor;
    }

    public Professor(String IDNumber,int profNumber,List<String > requests, boolean isSupervisor, String firstName, String lastName, String email, String faculty, String username, List<Integer> courses, ProfessorDegree professorDegree, String phoneNumber, int roomNumber,String password) {
        super(firstName, lastName, email, faculty, username,password,true);
        this.profNumber=profNumber;
        this.IDNumber=IDNumber;
        this.courses = courses;
        this.professorDegree = professorDegree;
        this.phoneNumber = phoneNumber;
        this.roomNumber = roomNumber;
        this.isSupervisor=isSupervisor;
        this.password=password;

        Professor.professors.add(this.username);
    }

    public Professor(String firstName, String lastName, String email, String faculty, String username,String password) {
        super(firstName, lastName, email, faculty, username,password,true);
    }

    public Professor(){
    }

    public String GetFullName(){
        return this.getFirstName() + " " + this.getLastName();
    }
}
