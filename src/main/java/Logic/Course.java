package Logic;

import java.util.ArrayList;
import java.util.List;

public class Course{
    public static List<Integer> courses=new ArrayList<>();
    public String name;
    public String faculty;
    public int number;
    public int unit;
    public int capacity;
    public String professor;
    public Degree degree;
    public String dateOfExam;
    public String dateOfClass;
    public int peopleCatched;
    public String Contents="";
    public String Homeworks="";

    public String uploadedHomeworks="";

    public List<String> markedPeople=new ArrayList<>();
    public int group;

    public void setMarkedPeople(List<String> markedPeople) {
        this.markedPeople = markedPeople;
    }

    public void setUploadedHomeworks(String uploadedHomeworks) {
        this.uploadedHomeworks = uploadedHomeworks;
    }

    public String getUploadedHomeworks() {
        return uploadedHomeworks;
    }

    public String getContents() {
        return Contents;
    }

    public String getHomeworks() {
        return Homeworks;
    }

    public void setContents(String contents) {
        Contents = contents;
    }

    public void setHomeworks(String homeworks) {
        Homeworks = homeworks;
    }

    public List<String> getMarkedPeople() {
        return markedPeople;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getGroup() {
        return group;
    }

    public void setPeopleCatched(int peopleCatched) {
        this.peopleCatched = peopleCatched;
    }

    public int getPeopleCatched() {
        return peopleCatched;
    }

    public List<String> prerequisites=new ArrayList<>();
    public List<String> corequisites=new ArrayList<>();
    public List<String> taList=new ArrayList<>();

    public static List<Integer> getCourses() {
        return courses;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public List<String> getCorequisites() {
        return corequisites;
    }

    public List<String> getTaList() {
        return taList;
    }

    public static void setCourses(List<Integer> courses) {
        Course.courses = courses;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void setCorequisites(List<String> corequisites) {
        this.corequisites = corequisites;
    }

    public void setTaList(List<String> taList) {
        this.taList = taList;
    }

    public Course(){
    }
    public List<String> students=new ArrayList<>();

    public void setDateOfClass(String dateOfClass) {
        this.dateOfClass = dateOfClass;
    }

    public String getDateOfClass() {
        return dateOfClass;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public void setDateOfExam(String dateOfExam) {
        this.dateOfExam = dateOfExam;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public List<String > getStudents() {
        return students;
    }

    public String getName() {
        return name;
    }

    public String getFaculty() {
        return this.faculty;
    }

    public int getNumber() {
        return number;
    }

    public int getUnit() {
        return unit;
    }

    public String getProfessor() {
        return professor;
    }

    public Degree getDegree() {
        return degree;
    }

    public String getDateOfExam() {
        return dateOfExam;
    }

    public Course(String dateOfClass,String name, String faculty, int number, int unit, String professor, Degree degree, String dateOfExam, List<String> students) {
        this.name = name;
        this.faculty = faculty;
        this.number = number;
        this.unit = unit;
        this.professor = professor;
        this.degree = degree;
        this.dateOfExam = dateOfExam;
        this.students = students;
        this.dateOfClass=dateOfClass;
        this.group=1;

        courses.add(this.number);
    }

    public Course(String dateOfClass,String name, String faculty, int number, int unit, String professor, Degree degree, String dateOfExam, List<String> students,List<String> ta,List<String> pre,List<String> co,int capacity,int group){
        this.name = name;
        this.faculty = faculty;
        this.number = number;
        this.unit = unit;
        this.professor = professor;
        this.degree = degree;
        this.dateOfExam = dateOfExam;
        this.students = students;
        this.dateOfClass=dateOfClass;

        this.capacity=capacity;
        this.taList=ta;
        this.prerequisites=pre;
        this.corequisites=co;
        courses.add(this.number);
        this.group=group;
    }
}
