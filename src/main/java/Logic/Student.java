package Logic;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person{

    public int StuNumber;
    public int UnitsPassed;
    public String IDNumber;
    public String phoneNumber;
    public int year;
    public EducationalStatus educationalstatus;
    public String Supervisor;
    public boolean hasRegistrationlicense;
    public String RegistrationTime;
    public String MessageToAdmin="";
    public String AdminReply="";

    public List<Integer> courses=new ArrayList<>();
    public List<Integer > passedCourses=new ArrayList<>();
    public List<Integer > markedCourses=new ArrayList<>();

    public List<String> message=new ArrayList<>();

    public List<String> admin=new ArrayList<>();

    public String requests="";

    public void setAdmin(List<String> admin) {
        this.admin = admin;
    }

    public List<String> getAdmin() {
        return admin;
    }

    public void setAdminReply(String adminReply) {
        AdminReply = adminReply;
    }
    public String getAdminReply() {
        return AdminReply;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessageToAdmin(String messageToAdmin) {
        MessageToAdmin = messageToAdmin;
    }

    public String getMessageToAdmin() {
        return MessageToAdmin;
    }

    public void setMarkedCourses(List<Integer> markedCourses) {
        this.markedCourses = markedCourses;
    }

    public void setPassedCourses(List<Integer> passedCourses) {
        this.passedCourses = passedCourses;
    }

    public List<Integer> getPassedCourses() {
        return passedCourses;
    }

    public List<Integer> getMarkedCourses() {
        return markedCourses;
    }

    public String getRequests() {
        return requests;
    }

    public void setRequests(String requests) {
        this.requests = requests;
    }

    public int maenScore;
    public Degree degree;
    public String scores="";
    public String GetFullName(){
        return this.getFirstName()+" "+this.getLastName();
    }
    public Student(){
    }

    public Student(List<String> requests,String firstName, String lastName, String email, String faculty, String username, int stuNumber, String  IDNumber, String phoneNumber, int year, EducationalStatus educationalstatus, String supervisor, boolean hasRegistrationlicense, String registrationTime, List<String> scores, List<Integer> courses, int maenScore, Degree degree,String password) {
        super(firstName, lastName, email, faculty,username,password,false);
        StuNumber = stuNumber;
        this.IDNumber = IDNumber;
        this.phoneNumber = phoneNumber;
        this.year = year;
        this.educationalstatus = educationalstatus;
        Supervisor = supervisor;
        this.hasRegistrationlicense = hasRegistrationlicense;
        RegistrationTime = registrationTime;
        this.courses = courses;
        this.maenScore = maenScore;
        this.degree = degree;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getScores() {
        return scores;
    }

    public void setUnitsPassed(int unitsPassed) {
        UnitsPassed = unitsPassed;
    }

    public int getUnitsPassed() {
        return UnitsPassed;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getYear() {
        return year;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setStuNumber(int stuNumber) {
        StuNumber = stuNumber;
    }

    public void setEducationalstatus(EducationalStatus educationalstatus) {
        this.educationalstatus = educationalstatus;
    }

    public void setSupervisor(String supervisor) {
        Supervisor = supervisor;
    }

    public void setHasRegistrationlicense(boolean hasRegistrationlicense) {
        this.hasRegistrationlicense = hasRegistrationlicense;
    }

    public void setRegistrationTime(String registrationTime) {
        RegistrationTime = registrationTime;
    }


    public void setCourses(List<Integer> courses) {
        this.courses = courses;
    }


    public void setMaenScore(int maenScore) {
        this.maenScore = maenScore;
    }



    public int getStuNumber() {
        return StuNumber;
    }

    public EducationalStatus getEducationalstatus() {
        return educationalstatus;
    }

    public String getSupervisor() {
        return Supervisor;
    }

    public boolean isHasRegistrationlicense() {
        return hasRegistrationlicense;
    }

    public String getRegistrationTime() {
        return RegistrationTime;
    }

    public List<Integer> getCourses() {
        return courses;
    }

    public int getMaenScore() {
        return maenScore;
    }
}
