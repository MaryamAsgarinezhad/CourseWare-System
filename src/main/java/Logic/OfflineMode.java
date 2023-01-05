package Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfflineMode {
    private boolean isMohseni=false;
    private boolean isOffline=false;

    private boolean isOfflineFromFirst=false;
    private int currentCourseNum;
    private int currentCourseGroup;
    private String currentContent;
    private String currenttype;
    private String currentExam;
    private String currentHomework;
    private boolean isNewingContent;
    //attributes

    public void setOfflineFromFirst(boolean offlineFromFirst) {
        isOfflineFromFirst = offlineFromFirst;
    }

    public boolean isOfflineFromFirst() {
        return isOfflineFromFirst;
    }

    public void setOffline(boolean offline) {
        isOffline = offline;
    }

    public boolean isOffline() {
        return isOffline;
    }

    private static OfflineMode instance;
    private HashMap<String ,Object> mainMenu=new HashMap<>();
    private List<HashMap<String ,Object>> courseList=new ArrayList<>();
    private List<HashMap<String ,Object>> proffesorList=new ArrayList<>();
    private List<HashMap<String ,Object>> ProffessorCourse=new ArrayList<>();
    private List<HashMap<String ,Object>> TemporaryGrades=new ArrayList<>();
    private List<HashMap<String ,Object>> EducationalStatus=new ArrayList<>();

    private List<HashMap<String ,Object>> TimeTable=new ArrayList<>();

    private List<HashMap<String ,Object>> ExamList=new ArrayList<>();

    private HashMap<String ,Object> ResponseToEducationalReq=new HashMap<>();

    private HashMap<String ,Object> EducationalRequest=new HashMap<>();

    private HashMap<String ,Object> ProfessorRequest=new HashMap<>();

    private HashMap<String ,Object> Chat=new HashMap<>();

    //functions
    public void setCurrenttype(String currenttype) {
        this.currenttype = currenttype;
    }

    public String getCurrenttype() {
        return currenttype;
    }

    public void setNewingContent(boolean newingContent) {
        isNewingContent = newingContent;
    }

    public boolean isNewingContent() {
        return isNewingContent;
    }

    public void setCurrentContent(String currentContent) {
        this.currentContent = currentContent;
    }

    public void setCurrentExam(String currentExam) {
        this.currentExam = currentExam;
    }

    public void setCurrentHomework(String currentHomework) {
        this.currentHomework = currentHomework;
    }

    public String getCurrentHomework() {
        return currentHomework;
    }

    public String getCurrentContent() {
        return currentContent;
    }

    public String getCurrentExam() {
        return currentExam;
    }

    public void setCurrentCourseNum(int currentCourseNum) {
        this.currentCourseNum = currentCourseNum;
    }

    public int getCurrentCourseNum() {
        return currentCourseNum;
    }

    public void setCurrentCourseGroup(int currentCourseGroup) {
        this.currentCourseGroup = currentCourseGroup;
    }

    public int getCurrentCourseGroup() {
        return currentCourseGroup;
    }

    public void setMohseni(boolean mohseni) {
        isMohseni = mohseni;
    }

    public boolean isMohseni() {
        return isMohseni;
    }

    public void setChat(HashMap<String, Object> chat) {
        Chat = chat;
    }

    public HashMap<String, Object> getChat() {
        return Chat;
    }

    public void setExamList(List<HashMap<String, Object>> examList) {
        ExamList = examList;
    }

    public List<HashMap<String, Object>> getExamList() {
        return ExamList;
    }

    public List<HashMap<String, Object>> getTimeTable() {
        return TimeTable;
    }

    public void setTimeTable(List<HashMap<String, Object>> timeTable) {
        TimeTable = timeTable;
    }

    public void setResponseToEducationalReq(HashMap<String, Object> responseToEducationalReq) {
        ResponseToEducationalReq = responseToEducationalReq;
    }

    public HashMap<String, Object> getResponseToEducationalReq() {
        return ResponseToEducationalReq;
    }

    public void setProfessorRequest(HashMap<String, Object> professorRequest) {
        ProfessorRequest = professorRequest;
    }

    public HashMap<String, Object> getProfessorRequest() {
        return ProfessorRequest;
    }

    public void setEducationalRequest(HashMap<String, Object> educationalRequest) {
        EducationalRequest = educationalRequest;
    }

    public HashMap<String, Object> getEducationalRequest() {
        return EducationalRequest;
    }

    public List<HashMap<String, Object>> getCourseList() {
        return courseList;
    }

    public List<HashMap<String, Object>> getEducationalStatus() {
        return EducationalStatus;
    }

    public void setEducationalStatus(List<HashMap<String, Object>> educationalStatus) {
        EducationalStatus = educationalStatus;
    }

    public void setProffessorCourse(List<HashMap<String, Object>> proffessorCourse) {
        ProffessorCourse = proffessorCourse;
    }

    public void setTemporaryGrades(List<HashMap<String, Object>> temporaryGrades) {
        TemporaryGrades = temporaryGrades;
    }

    public List<HashMap<String, Object>> getProffessorCourse() {
        return ProffessorCourse;
    }

    public List<HashMap<String, Object>> getTemporaryGrades() {
        return TemporaryGrades;
    }

    public void setCourseList(List<HashMap<String, Object>> courseList) {
        this.courseList = courseList;
    }

    public void setMainMenu(HashMap<String, Object> mainMenu) {
        this.mainMenu = mainMenu;
    }

    public static void setInstance(OfflineMode instance) {
        OfflineMode.instance = instance;
    }

    public HashMap<String, Object> getMainMenu() {
        return mainMenu;
    }

    public void setProffesorList(List<HashMap<String, Object>> proffesorList) {
        this.proffesorList = proffesorList;
    }

    public List<HashMap<String, Object>> getProffesorList() {
        return proffesorList;
    }

    private OfflineMode(){

    }

    public static OfflineMode getInstance(){
        if (instance==null){
            instance=new OfflineMode();
            return instance;
        }
        else {
            return instance;
        }
    }
}
