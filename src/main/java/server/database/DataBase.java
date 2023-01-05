package server.database;

import Logic.*;
import client.util.Config;
import client.util.Jackson;
import com.google.gson.Gson;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.response.Response;
import server.response.ResponseStatus;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataBase {
    private List<Faculty> faculties=new ArrayList<>();

    public static void setInstance(DataBase instance) {
        DataBase.instance = instance;
    }
    public List<Faculty> getFaculties() {
        return faculties;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public List<Course> getCourses() {
        return courses;
    }
    private List<Student> students=new ArrayList<>();
    private List<Professor> professors=new ArrayList<>();
    private List<Course> courses=new ArrayList<>();
    private static DataBase instance;

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    private DataBase() {
        ObjectMapper objectMapper=Jackson.getNetworkObjectMapper();
        //read
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("data")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject jsonObject=(JSONObject)obj;

            List<Faculty> facs=objectMapper.readValue(jsonObject.get("facs").toString(),List.class);
            List<Student> stus=objectMapper.readValue(jsonObject.get("stus").toString(),List.class);
            List<Professor> profs=objectMapper.readValue(jsonObject.get("profs").toString(),List.class);
            List<Course> courses=objectMapper.readValue(jsonObject.get("courses").toString(),List.class);

            setCourses(courses);
            setFaculties(facs);
            setStudents(stus);
            setProfessors(profs);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataBase getInstance(){
        if(instance==null){
            instance=new DataBase();
            return instance;
        }
        else {
            return instance;
        }
    }

    public Response login(String username,String password){
        for(Person s:ListOfPeople()){
            if(s.username.equals(username) && s.password.equals(password)){
                return personFile(s);
            }
            if(s.username.equals(username) && password.equals("mainmenu")){
                return personFile(s);
            }
        }

        return new Response(ResponseStatus.ERROR);
    }
    public Response personFile(Person person){
        for(Person s:ListOfPeople()){
            if(person.username.equals(s.username)){
                Response response=new Response(ResponseStatus.OK,ObjectToHashmap(s));

                //set last time
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH;mm;ss");
                LocalDateTime now = LocalDateTime.now();
                String lastTime=dtf.format(now);
                person.setLastEnterTime(lastTime);

                return response;
            }
        }
        return null;
    }
    public List<Person> ListOfPeople(){
        List<Person> answer=new ArrayList<>();

        for(Student s:this.students){
            answer.add(s);
        }
        for(Professor s:this.professors){
            answer.add(s);
        }

        return answer;
    }

    public static void main(String[] args){
        Professor admin=new Professor("Sina","Mahmoodi","mahmoodi@gmail.com","","A00","admin");
        Professor mohseni=new Professor("Aghaye","Mohseni","mohseni@gmail.com","","M00","mohseni");

        //faculty1
        Faculty f1=new Faculty("mathematics",1,"(f14)Hamidreza Fanaei","(v13)Shahram Khazaei",new ArrayList<>(Arrays.asList("a11","a12","v13","f14")),new ArrayList<>(Arrays.asList(11,12)),new ArrayList<>(Arrays.asList("m11","d12")));
        Professor p11=new Professor("679367387",11,new ArrayList<>(),true,"Kasra","Alishahi","alishahi@sharif.edu","(1)mathematics","a11",new ArrayList<>(Arrays.asList(11)), ProfessorDegree.Assistant,"66163344",10,"ffyi");
        Professor p12=new Professor("679367387",12,new ArrayList<>(),true,"Alireza","Zarei","zarei@sharif.edu","(1)mathematics","a12",new ArrayList<>(Arrays.asList(12)),ProfessorDegree.Assistant,"66164545",11,"vkvh");
        Professor p13=new Professor("679367387",13,new ArrayList<>(),true,"Shahram","Khazaei","shahram@sharif.edu","(1)mathematics","v13",new ArrayList<>(),ProfessorDegree.Vice,"66164595",12,"cjcgj");
        Professor p14=new Professor("679367387",14,new ArrayList<>(),true,"Hamidreza","Fanaei","fanaei@sharif.edu","(1)mathematics","f14",new ArrayList<>(),ProfessorDegree.Boss,"66164898",13,"hjd;hj");

        Course x1=new Course("MONDAY and SATURDAY at 9;30","Game Theory","(1)mathematics",11,3,"(a11)Kasra Alishahi", Degree.master,"FRIDAY. 1400/5/21 AT 12;00",new ArrayList<>(Arrays.asList("m11","d12")));
        Course y1=new Course("MONDAY and SATURDAY at 9;30","Topology","(1)mathematics",12,3,"(a12)Alireza Zarei", Degree.doc,"SATURDAY. 1400/5/21 AT 12;00",new ArrayList<>(Arrays.asList("m11","d12")));

        Student s11=new Student(new ArrayList<>(),"Maryam","asgari","maryyyyyy@gmail.com","(1)mathematics","m11",11,"0521323908","09124592594",1390,EducationalStatus.studying,"(a11)Kasra Alishahi",true,"2022/08/12 17",new ArrayList<>(),new ArrayList<>(Arrays.asList(11,12)),18,Degree.master,"cjfjj");
        Student s12=new Student(new ArrayList<>(),"Ali","Ahmadi","ahmadiAli@gmail.com","(1)mathematics","d12",12,"0522345777","0912345657",1390,EducationalStatus.studying,"(a11)Kasra Alishahi",true,"2022/08/07 16;00",new ArrayList<>(),new ArrayList<>(Arrays.asList(11,12)),15,Degree.doc,"cjfjj");


        //faculty2
        Faculty f2=new Faculty("aerospace",2,"(f24)Rasoul Jlili","(v23)Abbas Ebrahimi",new ArrayList<>(Arrays.asList("a21","a22","v23","f24")),new ArrayList<>(Arrays.asList(21,22)),new ArrayList<>(Arrays.asList("d21")));
        Professor p21=new Professor("679367387",21,new ArrayList<>(),true,"Hassan","Haddadpour","hadad@sharif.edu","(2)aerospace","a21",new ArrayList<>(Arrays.asList(21)),ProfessorDegree.Assistant,"66161212",10,"gjgjg");
        Professor p22=new Professor("679367387",22,new ArrayList<>(),true,"Maryam","Kiani","kiani@sharif.edu","(2)aerospace","a22",new ArrayList<>(Arrays.asList(22)),ProfessorDegree.Assistant,"66161313",11,"lbllll");
        Professor p23=new Professor("679367387",23,new ArrayList<>(),true,"Abbas","Ebrahimi","ebi@sharif.edu","(2)aerospace","v23",new ArrayList<>(),ProfessorDegree.Vice,"66161415",12,"ckhc;glhk");
        Professor p24=new Professor("679367387",24,new ArrayList<>(),true,"Rasoul","Jalili","jalil@sharif.edu","(2)aerospace","f24",new ArrayList<>(),ProfessorDegree.Boss,"66166878",13,"j;okh;f");


        Course x2=new Course("MONDAY and SATURDAY at 9;30","Flight Dynamics","(2)aerospace",21,3,"(a21)Hassan Haddadpour", Degree.doc,"SATURDAY. 1400/5/21 AT 12;00",new ArrayList<>(Arrays.asList("d21")));
        Course y2=new Course("MONDAY and SATURDAY at 9;30","Mechanics of Matrial","(2)aerospace",22,3,"(a22)Maryam Kiani", Degree.doc,"SATURDAY. 1400/5/21 AT 12;00",new ArrayList<>(Arrays.asList("d21")));

        Student s21=new Student(new ArrayList<>(),"zahra","shahbazi","zahraaaa@gmail.com","(2)aerospace","d21",21,"05213878708","0912135394",1391,EducationalStatus.studying,"(a21)Hassan Haddadpour",true,"2022/08/07 16;00",new ArrayList<>(),new ArrayList<>(Arrays.asList(21,22)),17,Degree.doc,"jlkgjk");


        //faculty3
        Faculty f3=new Faculty("Mechanics",3,"(f34)Mohsen Asghari","(v33)Arya Alasi",new ArrayList<>(Arrays.asList("a31","a32","v33","f34")),new ArrayList<>(Arrays.asList(31,32)),new ArrayList<>(Arrays.asList("m31")));
        Professor p31=new Professor("679367387",31,new ArrayList<>(),true,"Javad","Akbari","akbar@sharif.edu","(3)Mechanics","a31",new ArrayList<>(Arrays.asList(31)),ProfessorDegree.Assistant,"66163334",10,"c;nkcgh");
        Professor p32=new Professor("679367387",32,new ArrayList<>(),true,"Mehdi","Behzad","bahzad@sharif.edu","(3)Mechanics","a32",new ArrayList<>(Arrays.asList(32)),ProfessorDegree.Assistant,"66164645",11,"kkkkkk");
        Professor p33=new Professor("679367387",33,new ArrayList<>(),true,"Arya","Alasti","alast@sharif.edu","(3)Mechanics","v33",new ArrayList<>(),ProfessorDegree.Vice,"66144595",12,"kkkkjpo");
        Professor p34=new Professor("679367387",34,new ArrayList<>(),true,"Mohsen","Asghari","asghari@sharif.edu","(3)Mechanics","f34",new ArrayList<>(),ProfessorDegree.Boss,"66264898",13,"cjglgchlg");


        Course x3=new Course("MONDAY and SATURDAY at 9;30","Static","(3)Mechanics",31,3,"(a31)Javad Akbari", Degree.master,"SATURDAY. 1400/5/21 AT 12;00",new ArrayList<>(Arrays.asList("m31")));
        Course y3=new Course("MONDAY and SATURDAY at 9;30","Heat Transfer","(3)Mechanics",32,3,"(a32)Mehdi Behzad", Degree.master,"SATURDAY. 1400/5/21 AT 12;00",new ArrayList<>(Arrays.asList("m31")));

        Student s31=new Student(new ArrayList<>(),"Arian","Hesari","Arian123@gmail.com","(3)mechanics","m31",31,"0521323908","09127772594",1395,EducationalStatus.studying,"(a31)Javad Akbari",true,"2022/08/07 15;00",new ArrayList<>(),new ArrayList<>(Arrays.asList(31,32)),17,Degree.master,"jfxdfs");


        //faculty4
        Faculty f4=new Faculty("Computer",4,"(f44)Hamid Zarabi","(v43)Mohammad Ghodsi",new ArrayList<>(Arrays.asList("a41","a42","v43","f44")),new ArrayList<>(Arrays.asList(41,42)),new ArrayList<>(Arrays.asList("b41")));
        Professor p41=new Professor("679367387",41,new ArrayList<>(),true,"Ali","Abam","abam@sharif.edu","(4)Computer","a41",new ArrayList<>(Arrays.asList(41)),ProfessorDegree.Assistant,"66163344",10,"lfkxgj;z");
        Professor p42=new Professor("679367387",42,new ArrayList<>(),true,"Hossein","Sameti","sameti@sharif.edu","(4)Computer","a42",new ArrayList<>(Arrays.asList(42)),ProfessorDegree.Assistant,"66164545",11,"zdzasda");
        Professor p43=new Professor("679367387",43,new ArrayList<>(),true,"Mohammad","Ghodsi","ghodsi@sharif.edu","(4)Computer","v43",new ArrayList<>(),ProfessorDegree.Vice,"66164595",12,"xxx");
        Professor p44=new Professor("679367387",44,new ArrayList<>(),true,"Hamid","Zarabi","zarabi@sharif.edu","(4)Computer","f44",new ArrayList<>(),ProfessorDegree.Boss,"66164898",13,"cgjf");


        Course x4=new Course("MONDAY and SATURDAY at 9;30","Network","(4)Computer",41,3,"(a41)Ali Abam", Degree.bachelor,"SATURDAY. 1400/5/21 AT 12;00",new ArrayList<>(Arrays.asList("b41")));
        Course y4=new Course("MONDAY and SATURDAY at 9;30","OS","(4)Computer",42,3,"(a42)Hossein Sameti", Degree.bachelor,"SATURDAY. 1400/5/21 AT 12;00",new ArrayList<>(Arrays.asList("b41")));

        Student s41=new Student(new ArrayList<>(),"Sadra","Amini","sadraamini@gmail.com","(4)Computer","b41",41,"0521323977","09214592594",1394,EducationalStatus.studying,"(a41)Ali Abam",true,"2022/08/07 16;00",new ArrayList<>(),new ArrayList<>(Arrays.asList(41,42)),18,Degree.bachelor,"c;hk;fdkg");


        //faculty5
        Faculty f5=new Faculty("Maaref",5,"(f54)Mojtaba Atarodi","(v53)Amin Aminzade",new ArrayList<>(Arrays.asList("a51","a52","v53","f54")),new ArrayList<>(Arrays.asList(51,52)),new ArrayList<>(Arrays.asList("b51")));
        Professor p51=new Professor("679367387",51,new ArrayList<>(),true,"Ali","Abbaspour","abbaspour@sharif.edu","(5)Maaref","a51",new ArrayList<>(),ProfessorDegree.Assistant,"66163344",10,"waeae");
        Professor p52=new Professor("679367387",52,new ArrayList<>(),true,"Arash","Amini","Amini@sharif.edu","(5)Maaref","a52",new ArrayList<>(),ProfessorDegree.Assistant,"66164545",11,"lkkk;");
        Professor p53=new Professor("679367387",53,new ArrayList<>(),true,"Amin","Aminzade","aminzade@sharif.edu","(5)Maaref","v53",new ArrayList<>(),ProfessorDegree.Vice,"66164595",12,"ddzd");
        Professor p54=new Professor("679367387",54,new ArrayList<>(),true,"Mojtaba","Atarodi","atarodi@sharif.edu","(5)Maaref","f54",new ArrayList<>(),ProfessorDegree.Boss,"66164898",13,"zdzsfs");


        Course x5=new Course("MONDAY and SATURDAY at 9;30","Control","(5)Maaref",51,3,"(a51)Ali Abbaspor", Degree.bachelor,"SATURDAY. 1400/5/21 AT 12;00",new ArrayList<>(Arrays.asList("b51")));
        Course y5=new Course("MONDAY and SATURDAY at 9;30","Power","(5)Maaref",52,3,"(a52)Arash Amini", Degree.bachelor,"SATURDAY. 1400/5/21 AT 12;00",new ArrayList<>(Arrays.asList("b51")));

        Student s51=new Student(new ArrayList<>(),"sama","mosavi","sama123@gmail.com","(5)Maaref","b51",51,"0521323908","09304592594",1392,EducationalStatus.studying,"(a51)Ali Abbaspor",true,"2022/08/07 16;00",new ArrayList<>(),new ArrayList<>(Arrays.asList(51,52)),18,Degree.bachelor,"fklf");

        //adding to data base
        DataBase.getInstance().getFaculties().add(f1);
        DataBase.getInstance().getFaculties().add(f2);
        DataBase.getInstance().getFaculties().add(f3);
        DataBase.getInstance().getFaculties().add(f4);
        DataBase.getInstance().getFaculties().add(f5);

        DataBase.getInstance().getStudents().add(s11);
        DataBase.getInstance().getStudents().add(s12);
        DataBase.getInstance().getStudents().add(s21);
        DataBase.getInstance().getStudents().add(s31);
        DataBase.getInstance().getStudents().add(s41);
        DataBase.getInstance().getStudents().add(s51);

        DataBase.getInstance().getProfessors().add(p11);
        DataBase.getInstance().getProfessors().add(p12);
        DataBase.getInstance().getProfessors().add(p13);
        DataBase.getInstance().getProfessors().add(p14);

        DataBase.getInstance().getProfessors().add(admin);
        DataBase.getInstance().getProfessors().add(mohseni);

        DataBase.getInstance().getProfessors().add(p21);
        DataBase.getInstance().getProfessors().add(p22);
        DataBase.getInstance().getProfessors().add(p23);
        DataBase.getInstance().getProfessors().add(p24);

        DataBase.getInstance().getProfessors().add(p31);
        DataBase.getInstance().getProfessors().add(p32);
        DataBase.getInstance().getProfessors().add(p33);
        DataBase.getInstance().getProfessors().add(p34);

        DataBase.getInstance().getProfessors().add(p41);
        DataBase.getInstance().getProfessors().add(p42);
        DataBase.getInstance().getProfessors().add(p43);
        DataBase.getInstance().getProfessors().add(p44);

        DataBase.getInstance().getProfessors().add(p51);
        DataBase.getInstance().getProfessors().add(p52);
        DataBase.getInstance().getProfessors().add(p53);
        DataBase.getInstance().getProfessors().add(p54);

        DataBase.getInstance().getCourses().add(x1);
        DataBase.getInstance().getCourses().add(y1);

        DataBase.getInstance().getCourses().add(x2);
        DataBase.getInstance().getCourses().add(y2);

        DataBase.getInstance().getCourses().add(x3);
        DataBase.getInstance().getCourses().add(y3);

        DataBase.getInstance().getCourses().add(x4);
        DataBase.getInstance().getCourses().add(y4);

        DataBase.getInstance().getCourses().add(x5);
        DataBase.getInstance().getCourses().add(y5);

        WriteOnFile(DataBase.getInstance());
    }
    private static void WriteOnFile(DataBase instance) {
        ObjectMapper objectMapper=Jackson.getNetworkObjectMapper();
        try {
            String facs=objectMapper.writeValueAsString(instance.getFaculties());
            String stus=objectMapper.writeValueAsString(instance.getStudents());
            String profs=objectMapper.writeValueAsString(instance.getProfessors());
            String courses=objectMapper.writeValueAsString(instance.getCourses());

            Writer writer=new Writer(facs,courses,profs,stus);
            String  AllData=objectMapper.writeValueAsString(writer);

            try(FileWriter fw1=new FileWriter("data")) {
                fw1.write(AllData);
                DataBase.getInstance().makeFiles();
            }
            catch (IOException e){
                e.printStackTrace();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response CourseList() {
        WriteOnFile(DataBase.getInstance());

        List<HashMap<String ,Object>> finalAns=new ArrayList<>();

        for (Course temp:this.getCourses()){
            finalAns.add(ObjectToHashmap(temp));
        }

        Response response=new Response(ResponseStatus.OK,finalAns);
        return response;
    }
    public HashMap<String ,Object> ObjectToHashmap(Object object){
        HashMap<String ,Object> data=new HashMap<>();

        ObjectMapper objectMapper= Jackson.getNetworkObjectMapper();
        try {
            String ans=objectMapper.writeValueAsString(object);
            String[] s11=ans.substring(1,ans.length()-1).split(",\"");

            List<String> s1=new ArrayList<>();
            for(String temp:s11){
                s1.add(temp);
            }

            int cntr=0;
            for (int i = 0; i <s1.size() ; i++) {
                if(!s1.get(i).contains(":")){
                    cntr++;
                }
            }

            for (int j = 0; j < cntr; j++) {
                for (int i = 0; i <s1.size() ; i++) {
                    if(!s1.get(i).contains(":")){
                        s1.set(i-1,s1.get(i-1)+s1.get(i));
                        s1.remove(i);
                    }
                }
            }

            for (String temp:s1){
                String[] s2=temp.split(":");

                String first="";
                if(s2[0].substring(0,1).equals("\"")){
                    first=s2[0].substring(1,s2[0].length()-1);
                }
                else {
                    first=s2[0].substring(0,s2[0].length()-1);
                }

                String last="";
                if(!s2[1].substring(s2[1].length()-1).equals("\"") && !s2[1].substring(s2[1].length()-1).equals("]")){
                    last=s2[1];
                }
                else{
                    last=s2[1].substring(1,s2[1].length()-1);
                }

                if(!first.equals("password")){
                    data.put(first,last);
                }
            }

            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Response AddCourse(String name, String faculty, String number, String unit, String professor, String degree, String dateOfExam, String ta, String pre, String co, String classDate, Integer capacity) {
        String[] p=professor.split(",");

        int group=0;
        for(String trueProf:p){
            group++;

            Degree d= Logic.Degree.doc;
            if(degree.equals("doc")){
                d= Logic.Degree.doc;
            }
            if(degree.equals("bachelor")){
                d= Logic.Degree.bachelor;
            }
            if(degree.equals("master")){
                d= Logic.Degree.master;
            }

            List<String> taList=infoToList(ta);
            List<String> preList=infoToList(pre);
            List<String> coList=infoToList(co);

            Course course=new Course(classDate,name,faculty,Integer.valueOf(number),Integer.valueOf(unit),trueProf,d,dateOfExam,new ArrayList<>(),taList,preList,coList,capacity,group);

            for (Faculty f:this.faculties){
                if(f.getNumber()==Integer.valueOf(course.getFaculty().substring(1,2))){
                    f.getCourses().add(course.getNumber());
                }
            }

            for (Professor s:this.professors){
                if(s.getUsername().equals(course.getProfessor().substring(1,4))){
                    s.getCourses().add(course.getNumber());
                }
            }

            this.getCourses().add(course);
        }
        return CourseList();
    }
    private List<String> infoToList(String ta) {
        String[] s=ta.split(",");
        List<String> ans=new ArrayList<>();

        for (String temp:s){
            ans.add(temp);
        }
        return ans;
    }
    public Response DeleteCourse(String number, String group) {
        Course course=findCourseFromNumber(Integer.valueOf(number),Integer.valueOf(group));

        for (Faculty f:this.faculties){
            if(f.getCourses().contains(course)){
                f.getCourses().remove(course);
            }
        }

        for (Student s:this.students){
            if(s.getCourses().contains(course)){
                s.getCourses().remove(course);
            }
        }

        for (Professor s:this.professors){
            if(s.getCourses().contains(course)){
                s.getCourses().remove(course);
            }
        }

        this.getCourses().remove(course);

        return CourseList();
    }
    public Course findCourseFromNumber(int number,int group){
        for (Course temp:this.courses){
            if(temp.getNumber()==number && temp.getGroup()==group){
                return temp;
            }
        }
        return null;
    }
    public Response EditCourse(String name, String faculty, String number, String unit, String professor, String degree, String dateOfExam, String group) {
        Course course=findCourseFromNumber(Integer.parseInt(number),Integer.parseInt(group));
        Degree d= Logic.Degree.doc;
        if(degree.equals("doc")){
            d= Logic.Degree.doc;
        }
        if(degree.equals("bachelor")){
            d= Logic.Degree.bachelor;
        }
        if(degree.equals("master")){
            d= Logic.Degree.master;
        }

        course.setName(name);
        course.setDegree(d);
        course.setFaculty(faculty);
        course.setDateOfExam(dateOfExam);
        course.setProfessor(professor);
        course.setUnit(Integer.parseInt(unit));

        return CourseList();
    }
    public Response ProffesorList() {
        WriteOnFile(DataBase.getInstance());

        List<HashMap<String ,Object>> finalAns=new ArrayList<>();

        for (Professor temp:this.getProfessors()){
            finalAns.add(ObjectToHashmap(temp));
        }

        Response response=new Response(ResponseStatus.OK,finalAns);
        return response;
    }
    public Response AddProfessor(String firstName, String lastName, String profNumber, String username, String professorDegree, String faculty, String roomNumber) {
        ProfessorDegree d=ProfessorDegree.Assistant;
        if(professorDegree.equals("Vice")){
            Response response=new Response(ResponseStatus.ERROR);
            response.setErrorMessage("Vice cant be added!");
            return response;
        }
        if(professorDegree.equals("Boss")){
            Response response=new Response(ResponseStatus.ERROR);
            response.setErrorMessage("Boss cant be added!");
            return response;
        }

        Professor professor=new Professor("",Integer.parseInt(profNumber),new ArrayList<>(),true,firstName,lastName,"",faculty,username,new ArrayList<>(),d,"",Integer.parseInt(roomNumber),"");

        for (Faculty f:this.faculties){
            if(f.getNumber()==Integer.valueOf(professor.getFaculty().substring(1,2))){
                f.getProfessors().add(professor.getUsername());
            }
        }
        this.getProfessors().add(professor);
        return ProffesorList();
    }
    public Response EditProfessor(String professorDegree, String roomNumber,String username) {
        ProfessorDegree d=ProfessorDegree.Assistant;
        if(professorDegree.equals("Vice")){
            d=ProfessorDegree.Vice;
        }

        if(professorDegree.equals("Boss")){
            Response response=new Response(ResponseStatus.ERROR);
            response.setErrorMessage("Boss cant become Vice or Assistant!");
            return response;
        }

        Professor professor=findPrpfessorFromNumber(username);

        professor.setRoomNumber(Integer.parseInt(roomNumber));
        professor.setProfessorDegree(d);
        if(d==ProfessorDegree.Assistant){
            professor.setUsername("a"+professor.getProfNumber());
        }

        if(d==ProfessorDegree.Vice){
            fireVice(Integer.valueOf(username.substring(1,2)));

            professor.setProfessorDegree(ProfessorDegree.Vice);
            professor.setUsername("v"+professor.getProfNumber());
            assignVice(professor.getUsername());
        }

        updateFacultyListOfProfessors(Integer.valueOf(username.substring(1,2)));
        return ProffesorList();
    }
    private void updateFacultyListOfProfessors(int faculty) {
        for (Faculty f:this.faculties){
            if(f.getNumber()==faculty){
                List<String> profList=new ArrayList<>();
                for (Professor professor:this.getProfessors()){
                    if(professor.username.substring(1,2).equals(String.valueOf(faculty))){
                        profList.add(professor.getUsername());
                    }
                }
                f.setProfessors(profList);
            }
        }
    }
    private void assignVice(String username) {
        int facNumber=Integer.parseInt(username.substring(1,2));

        for (Faculty f:this.faculties){
            if(f.getNumber()==facNumber){
//                f.getProfessors().remove((String) "a"+)
                f.setVice(username);
            }
        }
    }
    private void fireVice(int faculty) {
        for (Faculty f:this.faculties){
            if(f.getNumber()==faculty){
                for(String s:f.getProfessors()){
                    Professor professor=findPrpfessorFromNumber(s);
                    if(professor.getProfessorDegree()==ProfessorDegree.Vice){
                        professor.setProfessorDegree(ProfessorDegree.Assistant);
                        professor.setUsername("a"+professor.getProfNumber());
                    }
                }
            }
        }
    }
    private Professor findPrpfessorFromNumber(String username) {
        for (Professor professor:this.getProfessors()){
            if(professor.getUsername().equals(username)){
                return professor;
            }
        }
        return null;
    }
    private Student findStudentFromNumber(String username) {
        for (Student student:this.getStudents()){
            if(student.getUsername().equals(username)){
                return student;
            }
        }
        return null;
    }
    public Response DeleteProfessor(String username) {
        if(username.substring(0,1).equals("f")){
            Response response=new Response(ResponseStatus.ERROR);
            response.setErrorMessage("Boss cant be deleted!");
            return response;
        }

        Professor professor=findPrpfessorFromNumber(username);

        this.getProfessors().remove(professor);

        updateFacultyListOfProfessors(Integer.parseInt(username.substring(1,2)));

        for (Student student:this.getStudents()){
            if(student.getSupervisor().substring(1,4).equals(username)){
                student.setSupervisor("");
            }
        }

        for (Course course:this.getCourses()){
            if(course.getProfessor().substring(1,4).equals(username)){
                course.setProfessor("");
            }
        }

        return ProffesorList();
    }
    public Response ProfessorCourseList(String username) {
        WriteOnFile(DataBase.getInstance());

        String facNumber=username.substring(1,2);
        List<HashMap<String ,Object>> ans=new ArrayList<>();

        for(Course C:this.getCourses()){
            if(String.valueOf(C.getNumber()).substring(0,1).equals(facNumber)){
                for(String studentUsername:C.getStudents()){
                    HashMap<String ,Object> tempAns=new HashMap<>();

                    Student student=findStudentFromNumber(studentUsername);

                    tempAns.put("StudentName","("+studentUsername+")"+student.GetFullName());
                    tempAns.put("ProffessorName",C.getProfessor());
                    tempAns.put("CourseName","("+C.getNumber()+")"+C.getName());
                    tempAns.put("grade",findGradeComponents(student.getScores(),String.valueOf(C.getNumber()))[4] + "("+findGradeComponents(student.getScores(),String.valueOf(C.getNumber()))[3]+")");
                    tempAns.put("StudentObject",findGradeComponents(student.getScores(),String.valueOf(C.getNumber()))[5]);
                    tempAns.put("ProffessorResponse",findGradeComponents(student.getScores(),String.valueOf(C.getNumber()))[6]);

                    ans.add(tempAns);
                }
            }
        }

        Response response=new Response(ResponseStatus.OK, ans);
        return response;
    }
    public List<String> findListOfGrades(String grades){
        List<String> ans=new ArrayList<>();

        if(!grades.contains("#")){
            ans.add(grades);
            return ans;
        }

        String[] s=grades.split("#");
        for (String temp:s){
            ans.add(temp);
        }
        return ans;
    }
    public String[] findGradeComponents(String gradesString,String courseNumber) {
        List<String> grades=findListOfGrades(gradesString);
        String grade="";

        for (String temp:grades){
            String[] ss=temp.split("&&");
            if(ss[0].equals(courseNumber)){
                grade=temp;
            }
        }

        if(grade.equals("")){
            String[] ans=new String[] {"","","","","","",""};
            return ans;
        }
        String[] ans=grade.split("&&");

        return ans;
    }
    public Response finalProfessorCourseList(String header, String mark, String username, String finalState, String object, String profResponse) {
        if(Double.valueOf(mark)>20 || Double.valueOf(mark)<0){
            Response response=new Response(ResponseStatus.ERROR);
            response.setErrorMessage("Invalid range for grade!");
            return response;
        }

        String[] s=header.split("&&");
        DeleteGradeFromHeader(s[2],header);
        addGrade(s[2],header,finalState,mark,object,profResponse);

        return ProfessorCourseList(username);
    }
    private void addGrade(String s, String header, String finalState, String mark, String object, String profResponse) {
        if(finalState.equals("f") && Integer.valueOf(mark)>=10){
            String[] ss=header.split("&&");
            int courseNum= Integer.parseInt(ss[0]);

            Student student=findStudentFromNumber(ss[2]);
            student.getCourses().remove((Object)courseNum);
            student.getPassedCourses().add(courseNum);
        }
        String ans=header+"&&"+finalState+"&&"+mark+"&&"+object+"&&"+profResponse;

        Student student=findStudentFromNumber(s);
        List<String> list=findListOfGrades(student.getScores());
        list.add(ans);

        String anss="";
        for (String temp:list){
            anss+="#"+temp;
        }
        student.setScores(anss.substring(1));
    }
    private void DeleteGradeFromHeader(String s, String header) {
        Student student=findStudentFromNumber(s);
        List<String> list=findListOfGrades(student.getScores());

        String removable="";
        for (String temp:list){
            if(temp.contains(header)){
                removable=temp;
            }
        }
        list.remove(removable);

        String ans="";
        for (String temp:list){
            ans+="#"+temp;
        }

        if(ans.equals("")){
            student.setScores("");
        }
        else{
            student.setScores(ans.substring(1));
        }
    }
    public Response TemporaryGrades(String username) {
        WriteOnFile(DataBase.getInstance());

        List<HashMap<String,Object>> ans=new ArrayList<>();
        Student student=findStudentFromNumber(username);

        for (Course course:this.getCourses()){
            if(course.getStudents().contains(username)){
                HashMap<String ,Object> tempAns=new HashMap<>();

                if(findGradeComponents(student.getScores(),String.valueOf(course.getNumber()))[3].equals("t") ){
                    tempAns.put("CourseName","("+course.getNumber()+")"+course.getName());
                    tempAns.put("ProffessorName",course.getProfessor());
                    tempAns.put("grade",findGradeComponents(student.getScores(),String.valueOf(course.getNumber()))[4] + "(t)");
                    tempAns.put("StudentObject",findGradeComponents(student.getScores(),String.valueOf(course.getNumber()))[5]);
                    tempAns.put("ProffessorResponse",findGradeComponents(student.getScores(),String.valueOf(course.getNumber()))[6]);

                    ans.add(tempAns);
                }
            }
        }
        Response response=new Response(ResponseStatus.OK,ans);
        return response;
    }
    public Response objectingToProf(String header, String mark, String username, String finalState, String object, String profResponse) {
        String[] s=header.split("&&");
        DeleteGradeFromHeader(s[2],header);
        addGrade(s[2],header,finalState,mark,object,profResponse);

        return TemporaryGrades(username);
    }

    public Response EducationalStatus(String username) {
        List<HashMap<String,Object>> ans=new ArrayList<>();
        Student student=findStudentFromNumber(username);

        for (Course course:this.getCourses()){
            if(course.getStudents().contains(username)){
                HashMap<String ,Object> tempAns=new HashMap<>();

                tempAns.put("CourseName","("+course.getNumber()+")"+course.getName());
                tempAns.put("grade",findGradeComponents(student.getScores(),String.valueOf(course.getNumber()))[4] + "("+findGradeComponents(student.getScores(),String.valueOf(course.getNumber()))[3]+")");
                tempAns.put("unit",course.getUnit());

                ans.add(tempAns);
            }
        }

        Response response=new Response(ResponseStatus.OK,ans);
        return response;
    }
    public Response EducationalRequest(String username) {
        HashMap<String ,Object> tempAns=new HashMap<>();
        Student student=findStudentFromNumber(username);
        List<String> reqs=findListOfGrades(student.getRequests());

        int i=0;
        for (String temp:reqs){
            i++;
            tempAns.put(String.valueOf(i),temp);
        }

        Response response=new Response(ResponseStatus.OK,tempAns);
        return response;
    }
    public Response ReqForBothProfAndStu(String rep, String header) {
        String[] s=header.split("&&");
        String stuUsername="";
        String profUsername="";

        if(s.length==3){
            stuUsername=s[1];
            profUsername=s[2];
        }
        else{
            stuUsername=s[2];
            profUsername=s[3];
        }

        DeleteRequestFromHeader(stuUsername,header,true);
        DeleteRequestFromHeader(profUsername,header,false);

        addReq(stuUsername,header,rep,true);
        addReq(profUsername,header,rep,false);

        WriteOnFile(DataBase.getInstance());
        return new Response(ResponseStatus.OK);
    }
    private void DeleteRequestFromHeader(String s, String header,boolean isStudent) {
        if(isStudent(s)){
            Student student=findStudentFromNumber(s);
            List<String> list=findListOfGrades(student.getRequests());

            String removable="";
            for (String temp:list){
                if(temp.contains(header)){
                    removable=temp;
                }
            }
            list.remove(removable);

            String ans="";
            for (String temp:list){
                ans+="#"+temp;
            }

            if(ans.equals("")){
                student.setRequests("");
            }
            else{
                student.setRequests(ans.substring(1));
            }
        }
        else{
            Professor professor=findPrpfessorFromNumber(s);
            List<String> list=findListOfGrades(professor.getRequests());

            String removable="";
            for (String temp:list){
                if(temp.contains(header)){
                    removable=temp;
                }
            }
            list.remove(removable);

            String ans="";
            for (String temp:list){
                ans+="#"+temp;
            }

            if(ans.equals("")){
                professor.setRequests("");
            }
            else{
                professor.setRequests(ans.substring(1));
            }
        }
    }
    private void addReq(String s, String header, String rep,boolean isStudent) {
        if(rep.equals("")){
            rep=" ";
        }
        if(isStudent(s)){
            String ans=header+"&&"+rep;

            Student student=findStudentFromNumber(s);
            List<String> list=findListOfGrades(student.getRequests());
            list.add(ans);

            String anss="";
            for (String temp:list){
                anss+="#"+temp;
            }
            student.setRequests(anss.substring(1));
        }
        else{
            String ans=header+"&&"+rep;

            Professor professor=findPrpfessorFromNumber(s);
            List<String> list=findListOfGrades(professor.getRequests());
            list.add(ans);
            String anss="";
            for (String temp:list){
                anss+="#"+temp;
            }
            professor.setRequests(anss.substring(1));
        }
    }
    public Response ProffesorRequest(String username) {
        HashMap<String ,Object> tempAns=new HashMap<>();

        if(!isStudent(username)){
            Professor professor=findPrpfessorFromNumber(username);
            List<String> reqs=findListOfGrades(professor.getRequests());

            int i=0;
            for (String temp:reqs){
                i++;
                tempAns.put(String.valueOf(i),temp);
            }

            Response response=new Response(ResponseStatus.OK,tempAns);
            return response;
        }
        else{
            Student student=findStudentFromNumber(username);
            List<String> reqs=findListOfGrades(student.getRequests());

            int i=0;
            for (String temp:reqs){
                if(temp.equals("")){
                    continue;
                }
                String[] s=temp.split("&&");
                if(s[0].equals("MessageRequest") && !s[1].equals(username)){
                    i++;
                    tempAns.put(String.valueOf(i),temp);
                }
            }

            Response response=new Response(ResponseStatus.OK,tempAns);
            return response;
        }
    }
    public Response TimeTable(String username, boolean isprof) {
        List<HashMap<String,Object>> ans=new ArrayList<>();

        if(isprof){
            Professor professor=findPrpfessorFromNumber(username);
            for (int courseName:professor.getCourses()){
                HashMap<String ,Object> tempAns=new HashMap<>();

                Course course=findCourseFromNumber(courseName,1);
                tempAns=ObjectToHashmap(course);
                ans.add(tempAns);
            }
        }
        else {
            Student student=findStudentFromNumber(username);
            for (Course course:this.getCourses()){
                if(course.getStudents().contains(username) && student.getCourses().contains(course.getNumber())){
                    HashMap<String ,Object> tempAns=new HashMap<>();
                    tempAns=ObjectToHashmap(course);
                    ans.add(tempAns);
                }
            }
        }

        Response response=new Response(ResponseStatus.OK,ans);
        return response;
    }
    public Response chngE(String username, String email, boolean isprof) {
        if(isprof){
            Professor professor=findPrpfessorFromNumber(username);
            professor.setEmail(email);
        }
        else {
            Student student=findStudentFromNumber(username);
            student.setEmail(email);
        }

        WriteOnFile(DataBase.getInstance());
        return login(username,"mainmenu");
    }
    public Response chngP(String username, String phone, boolean isprof) {
        if(isprof){
            Professor professor=findPrpfessorFromNumber(username);
            professor.setPhoneNumber(phone);
        }
        else {
            Student student=findStudentFromNumber(username);
            student.setPhoneNumber(phone);
        }

        WriteOnFile(DataBase.getInstance());
        return login(username,"mainmenu");
    }
    public Response NewStudent(Student realStudent) {
        this.getStudents().add(realStudent);

        String fac=realStudent.getFaculty().substring(3);
        Faculty faculty=findFaculty(fac);

        faculty.getStudents().add(realStudent.getUsername());

        WriteOnFile(DataBase.getInstance());
        return new Response(ResponseStatus.OK);
    }
    private Faculty findFaculty(String fac) {
        for (Faculty faculty:this.getFaculties()){
            if(faculty.getName().equals(fac)){
                return faculty;
            }
        }
        return null;
    }
    public Response NewProf(String text1, String text2, String text3, String text4, String text5, String text6, String text, int parseInt, int parseInt1, ProfessorDegree d, ArrayList<Object> objects1, String fac, boolean b, ArrayList<Object> objects) {
        Professor professor=new Professor(text,parseInt,new ArrayList<>(), b, text1, text2, text3, fac, text4, new ArrayList<>(), d, text5, parseInt1, text6);

        this.getProfessors().add(professor);

        String facc=professor.getFaculty().substring(3);
        Faculty faculty=findFaculty(facc);

        faculty.getProfessors().add(professor.getUsername());

        WriteOnFile(DataBase.getInstance());
        return new Response(ResponseStatus.OK);
    }
    public Response chngPass(String username, String password) {
        String s=username.substring(0,1);

        if(s.equals("b") || s.equals("m") || s.equals("d")){
            Student student=findStudentFromNumber(username);
            student.setPassword(password);
        }
        else{
            Professor professor=findPrpfessorFromNumber(username);
            professor.setPassword(password);
        }

        WriteOnFile(DataBase.getInstance());
        return new Response(ResponseStatus.OK);
    }
    public Response getPreviousPass(String username) {
        String s=username.substring(0,1);

        String pass;
        if(s.equals("b") || s.equals("m") || s.equals("d")){
            Student student=findStudentFromNumber(username);
            pass=student.getPassword();
        }
        else{
            Professor professor=findPrpfessorFromNumber(username);
            pass=professor.getPassword();
        }

        HashMap<String ,Object> ans=new HashMap<>();
        ans.put("pass",pass);
        return new Response(ResponseStatus.OK,ans);
    }
    public Response CourseOfFaculty(String facName) {
        WriteOnFile(DataBase.getInstance());

        Faculty faculty=findFaculty(facName);
        List<HashMap<String ,Object>> ans=new ArrayList<>();

        int i=0;
        for (Integer courseNum:faculty.getCourses()){
            if(i>0){
                i--;
                continue;
            }
            HashMap<String ,Object> tempAns=new HashMap<>();

            List<Course> list=findCoursesWithSameNumber(courseNum);
            i=list.size()-1;
            for (Course course:list){
                tempAns=ObjectToHashmap(course);
                ans.add(tempAns);
            }
        }

        Response response=new Response(ResponseStatus.OK,ans);
        return response;
    }
    public List<Course> findCoursesWithSameNumber(int courseNum){
        List<Course> ans=new ArrayList<>();
        for (Course course:this.getCourses()){
            if(course.getNumber()==courseNum){
                ans.add(course);
            }
        }
        return ans;
    }
    public Response markCourse(String username, String courseNum, String group) {
        Course course=findCourseFromNumber(Integer.valueOf(courseNum),Integer.parseInt(group));
        Student student=findStudentFromNumber(username);

        if(course.getMarkedPeople().contains(username)){
            course.getMarkedPeople().remove(username);
            student.getMarkedCourses().remove(Integer.valueOf(courseNum));
        }
        else {
            course.getMarkedPeople().add(username);
            student.getMarkedCourses().add(Integer.valueOf(courseNum));
        }

        WriteOnFile(DataBase.getInstance());
        return new Response(ResponseStatus.OK);
    }
    public Response catchCourse(String username, String courseNum, String group, boolean isGruopChng) {
        Integer maxUnit = Config.getConfig().getProperty(Integer.class, "maxUnitPerTerm");

        String fac=courseNum.substring(0,1);
        Student student=findStudentFromNumber(username);

        if(student.getCourses().size()>maxUnit){
            //dont allow to catch
        }

        if(capacityCheck(username,courseNum,group) && prerequisiteCheck(username,courseNum,group) &&
                examTimeCheck(username,courseNum,group) && classTimeCheck(username,courseNum,group) &&
                theologyCheck(username,courseNum,group))
        {
            Course course=findCourseFromNumber(Integer.valueOf(courseNum),Integer.valueOf(group));

            if(!course.getStudents().contains(username)){
                course.getStudents().add(username);
                course.setCapacity(course.getCapacity()-1);
                course.setPeopleCatched(course.getPeopleCatched()+1);
                student.getCourses().add(Integer.valueOf(courseNum));
            }
            return CourseOfFaculty(getNumberAndFindNameOfFaculty(fac));
        }
        else{
            if(isGruopChng && prerequisiteCheck(username,courseNum,group) &&
                    !capacityCheck(username,courseNum,group) && examTimeCheck(username,courseNum,group) &&
                    classTimeCheck(username,courseNum,group) && theologyCheck(username,courseNum,group))
            {
                Response response=new Response(ResponseStatus.ERROR);
                response.setErrorMessage("Group changing request sent to vice because of no capacity");
                return response;
            }
            else{
                Response response=new Response(ResponseStatus.ERROR);
                response.setErrorMessage("Not able to catch the course because of capacity, tima collapse, prerequisites or more thanone course from theology!");
                return response;
            }
        }
    }
    private String getNumberAndFindNameOfFaculty(String fac) {
        switch (fac){
            case "1"->{
                return "mathematics";
            }

            case "2"->{
                return "aerospace";
            }

            case "3"->{
                return "Mechanics";
            }

            case "4"->{
                return "Computer";
            }

            case "5"->{
                return "Maaref";
            }

            default -> {
                return null;
            }
        }
    }
    private boolean theologyCheck(String username, String courseNum, String group) {
        Course course=findCourseFromNumber(Integer.parseInt(courseNum),Integer.parseInt(group));
        Student student=findStudentFromNumber(username);

        if(course.getFaculty().substring(1,2).equals("5")){
            for(int courseNumber:student.getCourses()){
                if(String.valueOf(courseNumber).substring(0,1).equals(5)){
                    System.out.println(" theo false");
                    return false;
                }
            }
            return true;
        }
        else{
            return true;
        }
    }
    private boolean classTimeCheck(String username, String courseNum, String group) {
        Course course=findCourseFromNumber(Integer.parseInt(courseNum),Integer.parseInt(group));
        Student student=findStudentFromNumber(username);

        String[] classTime=course.getDateOfClass().split(";");
        //todo
        for (int temp:student.getCourses()){
            List<Course> list=findCoursesWithSameNumber(temp);
            for (Course item:list){
                String[] s=item.getDateOfClass().split(";");
                if(s[0].equals(classTime[0])){
                    System.out.println(" class false");
                    return false;
                }
            }
        }

        return true;
    }
    private boolean examTimeCheck(String username, String courseNum, String group) {
        Course course=findCourseFromNumber(Integer.parseInt(courseNum),Integer.parseInt(group));
        Student student=findStudentFromNumber(username);

        String[] examTime=course.getDateOfExam().split(";");
        //todo
        for (int temp:student.getCourses()){

            List<Course> list=findCoursesWithSameNumber(temp);
            for (Course item:list){
                String[] s=item.getDateOfExam().split(";");
                if(s[0].equals(examTime[0])){
                    System.out.println(" exam false");
                    return false;
                }
            }
        }

        return true;
    }
    private boolean capacityCheck(String username, String courseNum, String group) {
        Course course=findCourseFromNumber(Integer.parseInt(courseNum),Integer.parseInt(group));

        if(course.getCapacity()==0){
            System.out.println(" capa false");

            return false;
        }
        return true;
    }
    private boolean prerequisiteCheck(String username, String courseNum, String group) {
        Course course=findCourseFromNumber(Integer.parseInt(courseNum),Integer.parseInt(group));
        Student student=findStudentFromNumber(username);

        for (String temp:course.getPrerequisites()){
            boolean b=false;
            for (int temp2:student.getPassedCourses()){
                if(String.valueOf(temp2).equals(temp.substring(1))){
                    b=true;
                }
            }

            if(b==false){
                System.out.println(" pre false");

                return false;
            }
            else{
                continue;
            }
        }

        return true;
    }
    public Response ReqCachCourseVice(String username, String courseNum, String group) {
        WriteOnFile(DataBase.getInstance());

        String rep="";
        Course course=findCourseFromNumber(Integer.parseInt(courseNum),Integer.parseInt(group));
        Faculty faculty=findFaculty(course.getFaculty().substring(3));
        String  viceUsername=faculty.getVice().substring(1,4);
        String Request="CatchCourse"+"&&"+courseNum+"---"+group+"&&"+username+"&&"+viceUsername;

        Professor vice=findPrpfessorFromNumber(viceUsername);

        ReqForBothProfAndStu(rep,Request);

        return new Response(ResponseStatus.OK);
    }
    public Response deleteUnit(String username, String courseNum, String group) {
        String fac=courseNum.substring(0,1);
        Course course=findCourseFromNumber(Integer.valueOf(courseNum),Integer.valueOf(group));
        Student student=findStudentFromNumber(username);

        if(course.getStudents().contains(username)){
            course.getStudents().remove(username);
            course.setCapacity(course.getCapacity()+1);
            course.setPeopleCatched(course.getPeopleCatched()-1);
            student.getCourses().remove(Integer.valueOf(courseNum));
        }

        return CourseOfFaculty(getNumberAndFindNameOfFaculty(fac));
    }
    public Response returnOtherGroups( String courseNum, String group) {
        HashMap<String,Object> ans=findOtherGroups(Integer.valueOf(courseNum),Integer.valueOf(group));

        if(ans.size()==0){
            return new Response(ResponseStatus.ERROR);
        }
        else{
            return new Response(ResponseStatus.OK,ans);
        }
    }
    private HashMap<String,Object> findOtherGroups(Integer courseNum, Integer gp) {
        HashMap<String,Object> ans=new HashMap<>();

        int i=0;
        for (Course course:this.getCourses()){
            i++;
            if(course.getNumber()==courseNum && course.getGroup()!=gp){
                ans.put(String.valueOf(i),course.getGroup());
            }
        }

        return ans;
    }
    public Response userHasTheCourseOrNot(String username, String courseNum, String group) {
        Course course=findCourseFromNumber(Integer.parseInt(courseNum),Integer.parseInt(group));

        if(course.getStudents().contains(username)){
            return new Response(ResponseStatus.OK);
        }
        else{
            return new Response(ResponseStatus.ERROR);
        }
    }
    public Response giveRecommendedCourses(String username) {
        List<HashMap<String,Object>> ans=new ArrayList<>();
        List<Course> c1=new ArrayList<>();
        List<Course> c2=new ArrayList<>();
        List<Course> c3=new ArrayList<>();

        for (Course course:this.getCourses()){
            if(prerequisiteCheck(username,String.valueOf(course.getNumber()),String.valueOf(course.getGroup()))){
                c1.add(course);
            }
        }

        System.out.println(c1);

        for (Course course:c1){
            if(capacityCheck(username,String.valueOf(course.getNumber()),String.valueOf(course.getGroup()))){
                c2.add(course);
            }
        }

        for (Course course:c2){
            if(degreeCheck(username,String.valueOf(course.getNumber()),String.valueOf(course.getGroup()))){
                c3.add(course);
            }
        }

        for (Course course:c3){
            HashMap<String ,Object> tempAns=new HashMap<>();
            tempAns=ObjectToHashmap(course);
            ans.add(tempAns);
        }

        return new Response(ResponseStatus.OK,ans);
    }
    private boolean degreeCheck(String username, String courseNum, String group) {
        Course course=findCourseFromNumber(Integer.parseInt(courseNum),Integer.parseInt(group));
        Student student=findStudentFromNumber(username);

        if(course.getDegree()==student.getDegree()){
            return true;
        }
        else{
            return false;
        }
    }
    public Response giveMarkedCourses(String username) {
        List<HashMap<String,Object>> ans=new ArrayList<>();

        for (Course course:this.getCourses()){
            if(course.getMarkedPeople().contains(username)){
                HashMap<String,Object> tempAns=new HashMap<>();
                tempAns=ObjectToHashmap(course);
                ans.add(tempAns);
            }
        }
        return new Response(ResponseStatus.OK,ans);
    }
    public Response getAllChats(String username) {
        WriteOnFile(DataBase.getInstance());
        return realGetAllChats(username);
    }

    public Response realGetAllChats(String username){
        HashMap<String,Object> ans=new HashMap<>();

        int i=0;
        if(isStudent(username)){
            Student student=findStudentFromNumber(username);
            List<String> list=findListOfGrades(student.getChats());
            for (String temp:list){
                i++;
                ans.put(String.valueOf(i),temp);
            }
        }
        else{
            Professor professor=findPrpfessorFromNumber(username);
            List<String> list=findListOfGrades(professor.getChats());
            for (String temp:list){
                i++;
                ans.put(String.valueOf(i),temp);
            }
        }
        return new Response(ResponseStatus.OK,ans);
    }
    private boolean isStudent(String username) {
        String letter=username.substring(0,1);

        if(letter.equals("a") || letter.equals("v") || letter.equals("f") || letter.equals("M") || letter.equals("A")){
            return false;
        }
        else{
            return true;
        }
    }
    public Response getNameOfUser(String username) {
        String ans;

        if(isStudent(username)){
            Student student=findStudentFromNumber(username);
            ans=student.GetFullName();
        }
        else{
            Professor professor=findPrpfessorFromNumber(username);
            ans=professor.GetFullName();
        }
        Response response=new Response(ResponseStatus.ERROR);
        response.setErrorMessage(ans);
        return response;
    }
    public Response sendFromMeToThem(String yourUsername, String theirUsername, String mssg) {
        String realMssg=mssg+"--"+yourUsername;

        if(isStudent(yourUsername)){
            Student student=findStudentFromNumber(yourUsername);
            student.setChats(ModifiedChat(student.getChats(),realMssg,theirUsername));
        }
        else{
            Professor professor=findPrpfessorFromNumber(yourUsername);
            professor.setChats(ModifiedChat(professor.getChats(),realMssg,theirUsername));
        }

        if(isStudent(theirUsername)){
            Student student=findStudentFromNumber(theirUsername);
            student.setChats(ModifiedChat(student.getChats(),realMssg,yourUsername));
        }
        else{
            Professor professor=findPrpfessorFromNumber(theirUsername);
            professor.setChats(ModifiedChat(professor.getChats(),realMssg,yourUsername));
        }

        return getAllChats(yourUsername);
    }
    private String ModifiedChat(String chats, String mssg, String header) {
        List<String> list=findListOfGrades(chats);
        int index=-1;
        String previousChat="";

        for (String temp:list){
            String[] s=temp.split("::->");
            if(s[0].equals(header)){
                index=list.indexOf(temp);
                previousChat=temp;
            }
        }

        if(previousChat.equals("")){
            previousChat=header+"::->"+mssg;
            list.add(previousChat);
        }
        else{
            previousChat+="&&"+mssg;
            list.remove(index);
            list.add(index,previousChat);
        }

        String ans="";
        for (String temp:list){
            ans+="#"+temp;
        }

        if(ans.equals("")){
            return "";
        }
        else{
            return ans.substring(1);
        }
    }
    public Response newChatContacts(String username) {
        if(username.equals("M00")){
            return null;
        }
        HashMap<String ,Object> ans=new HashMap<>();

        int i=0;
        if(isStudent(username)){
            Student student=findStudentFromNumber(username);
            String facName=student.getFaculty().substring(3);
            Faculty faculty=findFaculty(facName);

            for (String temp:faculty.getStudents()){
                Student student1=findStudentFromNumber(temp);
                if(student1.getYear()==student.getYear() && !student1.getUsername().equals(student.getUsername())){
                    i++;
                    ans.put(String.valueOf(i),"("+temp+")"+student1.GetFullName());
                }
            }
            i++;
            ans.put(String.valueOf(i),student.getSupervisor());
            return new Response(ResponseStatus.OK,ans);
        }

        else{
            Professor professor=findPrpfessorFromNumber(username);
            String letter=username.substring(0,1);
            if (letter.equals("a")){
                for (Student student:this.getStudents()){
                    if(student.getSupervisor().substring(5).equals(professor.GetFullName())){
                        i++;
                        ans.put(String.valueOf(i),"("+student.getUsername()+")"+student.GetFullName());
                    }
                }
                return new Response(ResponseStatus.OK,ans);

            }
            else{
                String facname=professor.getFaculty().substring(3);
                Faculty faculty=findFaculty(facname);

                for (String stuNum:faculty.getStudents()){
                    i++;
                    Student student=findStudentFromNumber(stuNum);
                    ans.put(String.valueOf(i),"("+student.getUsername()+")"+student.GetFullName());
                }
                return new Response(ResponseStatus.OK,ans);
            }
        }
    }
    public Response SendToAmin(String username, String message) {
        String trueMessage=username+"&&"+message;

        Professor admin=findPrpfessorFromNumber("A00");
        List<String> list=findListOfGrades(admin.getAdminMessages());
        list.add(trueMessage);

        String finalmssg="";
        for (String temp:list){
            finalmssg+="#"+temp;
        }

        if(finalmssg.equals("")){
            admin.setAdminMessages("");
        }
        else{
            admin.setAdminMessages(finalmssg.substring(1));
        }

        return new Response(ResponseStatus.OK);
    }
    public Response getAdminMessages(String username) {
        Professor admin=findPrpfessorFromNumber(username);
        List<String> list=findListOfGrades(admin.getAdminMessages());
        HashMap<String ,Object> ans=new HashMap<>();

        int i=0;
        for (String temp:list){
            i++;
            ans.put(String.valueOf(i),temp);
        }
        return new Response(ResponseStatus.OK,ans);
    }
    public Response sendAdminResponseToStudent(String stuUsername, String message) {
        Student student=findStudentFromNumber(stuUsername);
        List<String> list=findListOfGrades(student.getAdminReply());
        list.add(message);

        String finalmssg="";
        for (String temp:list){
            finalmssg+="#"+temp;
        }

        if(finalmssg.equals("")){
            student.setAdminReply("");
        }
        else{
            student.setAdminReply(finalmssg.substring(1));
        }

        return new Response(ResponseStatus.OK);
    }
    public Response getAdminResponseToStudent(String username) {
        Student student=findStudentFromNumber(username);
        HashMap<String ,Object> ans=new HashMap<>();
        List<String> list=findListOfGrades(student.getAdminReply());

        int i=0;
        for (String temp:list){
            i++;
            ans.put(String.valueOf(i),temp);
        }
        return new Response(ResponseStatus.OK,ans);
    }
    public Response getMohseniStudents() {
        List<HashMap<String ,Object>> ans=new ArrayList<>();

        for (Student student:this.getStudents()){
            HashMap<String ,Object> tempAns=new HashMap<>();
            tempAns=ObjectToHashmap(student);
            ans.add(tempAns);
        }

        return new Response(ResponseStatus.OK,ans);
    }
    public Response getContent(String type, Integer courseNum, Integer courseGroup, String contentName) {
        Course course=findCourseFromNumber(courseNum,courseGroup);
        String string="";

        if(type.equals("content")){
            string=course.getContents();
        }
        if(type.equals("homework")){
            string=course.getHomeworks();
        }

        List<String > list=findListOfGrades(string);
        String content=findContentFromName(list,contentName);


        return new Response(ResponseStatus.OK,splitContent(content));
    }
    private HashMap<String, Object> splitContent(String content) {
        HashMap<String, Object> ans=new HashMap<>();

        String[] s=content.split("&&&");
        if(s.length==2){
            ans.put("start",s[1]);
        }

        if(s.length==3){
            ans.put("start",s[1]);
            ans.put("end",s[2]);
        }

        String[] ss=s[0].split("&&");

        int i=0;
        for (String temp:ss){
            i++;
            ans.put(String.valueOf(i),temp);
        }
        return ans;
    }
    private String findContentFromName(List<String> list, String contentName) {
        for (String temp:list){
            String[] s=temp.split("&&");
            if(s[0].equals(contentName)){
                return temp;
            }
        }
        return null;
    }
    public Response getTaaOfCourse(Integer num, Integer gp) {
        List<String> ta=findCourseFromNumber(num,gp).taList;
        HashMap<String,Object> ans=new HashMap<>();

        int i=0;
        for (String temp:ta){
            i++;
            ans.put(String.valueOf(i),temp);
        }

        return new Response(ResponseStatus.OK,ans);
    }
    public Response setContent(String type, Integer courseNum, Integer courseGroup, String contentName, String media) {
        Course course=findCourseFromNumber(courseNum,courseGroup);
        String string="";

        if(type.equals("content")){
            string=course.getContents();
        }
        if(type.equals("homework")){
            string=course.getHomeworks();
        }

        List<String > list=findListOfGrades(string);
        List<String > newList=findContentFromNameAndReplace(list,contentName,media);

        demultiplexContent(newList,type,course);

        WriteOnFile(DataBase.getInstance());
        return getContent(type,courseNum,courseGroup,contentName);

    }
    private void demultiplexContent(List<String> newList,String type,Course course) {
        String ans="";

        for (String temp:newList){
            if(temp.equals("")){
                continue;
            }
            ans+="#"+temp;
        }

        if(ans.equals("")){
            if(type.equals("content")){
                course.setContents("");
            }
            if(type.equals("upload")){
                course.setUploadedHomeworks("");
            }
            if(type.equals("homework")){
                course.setHomeworks("");
            }
        }
        else{
            if(type.equals("content")){
                course.setContents(ans.substring(1));
            }
            if(type.equals("upload")){
                course.setUploadedHomeworks(ans.substring(1));
            }
            if(type.equals("homework")){
                course.setHomeworks(ans.substring(1));
            }
        }
    }
    private List<String> findContentFromNameAndReplace(List<String> list, String contentName, String media) {
        String removable="";

        for (String temp:list){
            String[] s=temp.split("&&");
            if(s[0].equals(contentName)){
                removable=temp;
            }
        }

        if (removable.equals("")){
            removable=contentName+"&&"+media;
            list.add(removable);
        }
        else{
            String[] s=removable.split("&&");

            String ans="";
            int i=0;
            for (String temp:s){
                i++;
                if(i==2){
                    ans+="&&"+media;
                }
                ans+="&&"+temp;
            }

            list.remove(removable);
            removable=ans.substring(2);
            list.add(removable);
        }

        return list;
    }
    public Response deleteContent(String type, Integer courseNum, Integer courseGroup, String contentName) {
        Course course=findCourseFromNumber(courseNum,courseGroup);
        String string="";

        if(type.equals("content")){
            string=course.getContents();
        }
        if(type.equals("homework")){
            string=course.getHomeworks();
        }

        List<String > list=findListOfGrades(string);
        List<String > newList=findContentFromNameAnddelete(list,contentName);

        demultiplexContent(newList,type,course);

        WriteOnFile(DataBase.getInstance());
        return new Response(ResponseStatus.OK);
    }
    private List<String> findContentFromNameAnddelete(List<String> list, String contentName) {
        String removable="";

        for (String temp:list){
            String[] s=temp.split("&&");
            if(s[0].equals(contentName)){
                removable=temp;
            }
        }

        if (!removable.equals("")){
            list.remove(removable);
        }
        return list;
    }
    public Response finalContent(String type, Integer courseNum, Integer courseGroup, String contentName) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH;mm;ss");
        LocalDateTime now = LocalDateTime.now();
        String lastTime=dtf.format(now);

        Course course=findCourseFromNumber(courseNum,courseGroup);
        String string="";

        if(type.equals("content")){
            string=course.getContents();
        }
        if(type.equals("homework")){
            string=course.getHomeworks();
        }

        List<String > list=findListOfGrades(string);
        List<String > newList=findContentFromNameAndReplaceTime(list,contentName,lastTime);

        demultiplexContent(newList,type,course);
        WriteOnFile(DataBase.getInstance());
        return new Response(ResponseStatus.OK);
    }
    private List<String> findContentFromNameAndReplaceTime(List<String> list, String contentName, String lastTime) {
        String removable="";

        for (String temp:list){
            String[] s=temp.split("&&");
            if(s[0].equals(contentName)){
                removable=temp;
            }
        }

        if (removable.equals("")){
            removable=contentName+"&&&"+lastTime;
            list.add(removable);
        }
        else{
            list.remove(removable);
            removable+="&&&"+lastTime;
            list.add(removable);
        }

        return list;
    }
    public Response sendCoursePageComponents(Integer courseNum, Integer courseGroup) {
        List<HashMap<String ,Object>> real=new ArrayList<>();
        HashMap<String ,Object> ans=new HashMap<>();
        HashMap<String ,Object> ans2=new HashMap<>();
        HashMap<String ,Object> ans3=new HashMap<>();

        Course course=findCourseFromNumber(courseNum,courseGroup);
        int i=0;
        for (String temp:findListOfGrades(course.getContents())){
            i++;
            String[] s=temp.split("&&");
            String[] ss=temp.split("&&&");

            if (s[0].equals("")){
                continue;
            }
            ans.put("content"+i,s[0]);

            if(ss.length==2){
                ans3.put(s[0],ss[1]);
            }
        }
        i=0;
        for (String temp:findListOfGrades(course.getHomeworks())){
            i++;
            String[] s=temp.split("&&");

            if (s[0].equals("")){
                continue;
            }

            ans.put("homework"+i,s[0]);

            ans2.put(s[0],s[3]+"startAt"+s[1]);
        }
        real.add(ans);
        real.add(ans2);
        real.add(ans3);
        return new Response(ResponseStatus.OK,real);
    }
    public Response deleteItem(Integer courseNum, Integer courseGroup, String contentName, Integer removableNumber) {
        Course course=findCourseFromNumber(courseNum,courseGroup);
        String string="";
        string=course.getContents();

        List<String > list=findListOfGrades(string);
        List<String > newList=findContentFromNameAndDeleteRemovable(list,contentName,removableNumber);

        demultiplexContent(newList,"content",course);

        WriteOnFile(DataBase.getInstance());
        return getContent("content",courseNum,courseGroup,contentName);
    }
    private List<String> findContentFromNameAndDeleteRemovable(List<String> list, String contentName, int removableNumber){
        String removable="";

        for (String temp:list){
            String[] s=temp.split("&&");
            if(s[0].equals(contentName)){
                removable=temp;
            }
        }

        if (!removable.equals("")){
            list.remove(removable);
            String[] s=removable.split("&&&");
            String[] ss=s[0].split("&&");

            List<String > newS=new ArrayList<>();

            for (String temp:s){
                newS.add(temp);
            }
            newS.remove(0);

            LinkedList<String > newSS=new LinkedList<>();

            for (String temp:ss){
                newSS.add(temp);
            }
            newSS.remove(removableNumber);

            String ans="";
            for (String temp:newSS){
                ans+="&&"+temp;
            }

            for (String temp:newS){
                ans+="&&&"+temp;
            }

            if(!ans.equals("")){
                list.add(ans.substring(2));
            }
        }
        return list;
    }
    public Response uploadHomeWork(String nameOfHomeWork, Integer courseNum, Integer courseGroup, String username, String homework){
        Course course=findCourseFromNumber(courseNum,courseGroup);
        List<String> list=findListOfGrades(course.getUploadedHomeworks());
        List<String > newList=findHomeworkFromNameAndUpload(list,nameOfHomeWork,homework,username);

        demultiplexContent(newList,"upload",course);

        WriteOnFile(DataBase.getInstance());
        return getContent("homework",courseNum,courseGroup,nameOfHomeWork);
    }
    private List<String> findHomeworkFromNameAndUpload(List<String> list, String nameOfHomeWork, String homework, String username) {
        String message=username+"-->"+homework;
        String removable="";

        for (String temp:list){
            String[] s=temp.split("&&");
            if(s[0].equals(nameOfHomeWork)){
                removable=temp;
            }
        }

        if (removable.equals("")){
            removable=nameOfHomeWork+"&&"+message;
            list.add(removable);
        }
        else{
            list.remove(removable);
            removable+="&&"+message;
            list.add(removable);
        }

        return list;
    }
    public Response sendUploadedHomework(String nameOfHomeWork, Integer courseNum, Integer courseGroup, String username) {
        Course course=findCourseFromNumber(courseNum,courseGroup);
        List<String> list=findListOfGrades(course.getUploadedHomeworks());
        HashMap<String,Object> ans=findHomeworkFromNameAndReturn(list,nameOfHomeWork,username);

        Response response=new Response(ResponseStatus.ERROR);
        if(ans.size()==0){
            response.setErrorMessage(null);
        }
        else{
            response.setErrorMessage(ans.get(String.valueOf(0)).toString());
            response.setData(ans);
        }
        return response;
    }
    private HashMap<String ,Object> findHomeworkFromNameAndReturn(List<String> list, String nameOfHomeWork, String username) {
        String removable="";
        HashMap<String ,Object> ans=new HashMap<>();

        for (String temp:list){
            String[] s=temp.split("&&");
            if(s[0].equals(nameOfHomeWork)){
                removable=temp;
            }
        }

        if (removable.equals("")){
            return ans;
        }
        else{
            String[] s=removable.split("&&");

            int i=0;
            for (String temp:s){
                i++;
                if(i==1){
                    continue;
                }
                String[] ss=temp.split("-->");

                if(ss[0].equals(username)){
                    ans.put("0",ss[1]);

                    if(ss.length==3){
                        ans.put("1",ss[2]);
                    }
                    else{
                        ans.put("1",null);
                    }
                }

            }
        }
        return ans;
    }
    public Response getAllUploads(Integer courseNum, Integer courseGroup, String nameOfHomeWork) {
        Course course=findCourseFromNumber(courseNum,courseGroup);
        List<String> list=findListOfGrades(course.getUploadedHomeworks());
        String string=findHomeworkFromName(list,nameOfHomeWork);

        if(string.equals("")){
            return new Response(ResponseStatus.ERROR);
        }

        HashMap<String ,Object> ans=new HashMap<>();
        HashMap<String ,Object> ans2=new HashMap<>();

        String[] s=string.split("&&");
        int i=0;

        for (String temp:s){
            i++;
            if(i==1){
                continue;
            }

            String[] ss=temp.split("-->");
            ans.put(ss[0],ss[1]);

            if(ss.length==3){
                ans2.put(ss[0],ss[2]);
            }
            else{
                ans2.put(ss[0],"");
            }
        }
        List<HashMap<String ,Object>> realAns=new ArrayList<>();
        realAns.add(ans2);
        Response response=new Response(ResponseStatus.OK,ans);
        response.setAnswer(realAns);

        return response;
    }
    private String findHomeworkFromName(List<String> list, String nameOfHomeWork) {
        String removable="";
        for (String temp:list){
            String[] s=temp.split("&&");
            if(s[0].equals(nameOfHomeWork)){
                removable=temp;
            }
        }
        return removable;
    }
    public Response setMarkForHomework(Integer courseNum, Integer courseGroup, String nameOfHomeWork, int mark, String username) {
        Course course=findCourseFromNumber(courseNum,courseGroup);
        List<String> list=findListOfGrades(course.getUploadedHomeworks());
        List<String > newList=findHomeworkFromNameAndSetMark(list,nameOfHomeWork,mark,username);

        demultiplexContent(newList,"upload",course);

        WriteOnFile(DataBase.getInstance());
        return getContent("homework",courseNum,courseGroup,nameOfHomeWork);
    }
    private List<String> findHomeworkFromNameAndSetMark(List<String> list, String nameOfHomeWork, int mark, String username) {
        String message="-->"+mark;
        String removable="";

        for (String temp:list){
            String[] s=temp.split("&&");
            if(s[0].equals(nameOfHomeWork)){
                removable=temp;
            }
        }

        List<String> sss=new ArrayList<>();

        if (removable.equals("")){
            return list;
        }
        else{
            String[] s=removable.split("&&");

            String removable2="";
            int i=0;
            for (String temp:s){
                sss.add(temp);
                i++;
                if(i==1){
                    continue;
                }
                String[] ss=temp.split("-->");

                if(ss[0].equals(username)){
                    removable2=temp;
                }
            }

            sss.remove(removable2);
            removable2+=message;
            sss.add(removable2);
        }

        list.remove(removable);
        removable="";

        for (String temp:sss){
            removable+="&&"+temp;
        }
        list.add(removable.substring(2));

        return list;
    }

    public Response ViceCatchCourse(String courseName, String username) {
        String[] s=courseName.split("---");
        Course course=findCourseFromNumber(Integer.valueOf(s[0]),Integer.valueOf(s[1]));
        Student student=findStudentFromNumber(username);

        if(!course.getStudents().contains(username)){
            course.getStudents().add(username);
            course.setCapacity(course.getCapacity()-1);
            course.setPeopleCatched(course.getPeopleCatched()+1);
            student.getCourses().add(Integer.valueOf(s[0]));
        }

        student.getMessage().add("You added as an student to the course "+course.getName());

        WriteOnFile(DataBase.getInstance());
        return new Response(ResponseStatus.OK);
    }

    public Response addTA(String courseName, String username) {
        String[] s=courseName.split("---");
        Course course=findCourseFromNumber(Integer.valueOf(s[0]),Integer.valueOf(s[1]));
        Student student=findStudentFromNumber(username);

        if(!course.getTaList().contains(username)){
            course.getTaList().add(username);
        }
        System.out.println(course.getTaList()+" ppp");

        student.getMessage().add("You added as a TA to the course "+course.getName());

        WriteOnFile(DataBase.getInstance());
        return new Response(ResponseStatus.OK);
    }

    public Response sendMessageOfCoursewareAdding(boolean isTA) {
        for (Course course:this.getCourses()){
            String message="";
            if(isTA){
                message="You added as a TA to the course "+course.getName();
            }
            else{
                message="You added as an student to the course "+course.getName();
            }

            for(String stuName:course.getStudents()){
                Student student=findStudentFromNumber(stuName);
                student.getMessage().add(message);
            }
        }

        WriteOnFile(DataBase.getInstance());
        return new Response(ResponseStatus.OK);
    }

    public Response getMessagesOfStudent(String username) {
        Student student=findStudentFromNumber(username);
        HashMap<String,Object> ans=new HashMap<>();

        int i=0;
        for (String message:student.getMessage()){
            i++;
            ans.put(String.valueOf(i),message);
        }
        return new Response(ResponseStatus.OK,ans);
    }

    public Response sendTAcourse(String usernme, Boolean isProf) {
        List<HashMap<String,Object>> ans=new ArrayList<>();

        if(isProf){
            Response response=new Response(ResponseStatus.OK,ans);
        }
        else {
            Student student=findStudentFromNumber(usernme);
            for (Course course:this.getCourses()){
                if(course.getTaList().contains(usernme)){
                    HashMap<String ,Object> tempAns=new HashMap<>();
                    tempAns=ObjectToHashmap(course);
                    ans.add(tempAns);
                }
            }
        }

        Response response=new Response(ResponseStatus.OK,ans);
        return response;
    }

    public void makeFiles(){
        Gson gson = new Gson();

        for (int i = 0; i < this.getFaculties().size(); i++) {
            String string_f=gson.toJson(this.getFaculties().get(i));

            int j=i+1;
            try(FileWriter fw1=new FileWriter("f"+j)) {
                fw1.write(string_f);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        for (int i = 0; i < this.getStudents().size(); i++) {
            String string_s=gson.toJson(this.getStudents().get(i));

            String s=this.getStudents().get(i).getUsername();
            try(FileWriter fw1=new FileWriter((String) s)) {
                fw1.write(string_s);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            ObjectMapper objectMapper=Jackson.getNetworkObjectMapper();

            String value= null;
            try {
                value = objectMapper.writeValueAsString(EducationalStatus(s).getAnswer());
                Jackson.writeOnFile(value, s,"educationalStatusPage");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                String chatStr=objectMapper.writeValueAsString(realGetAllChats(s).getData());
                Jackson.writeOnFile(chatStr, s,"chatPage");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < this.getProfessors().size(); i++) {
            String string_s=gson.toJson(this.getProfessors().get(i));

            String s=this.getProfessors().get(i).getUsername();
            try(FileWriter fw1=new FileWriter((String) s)) {
                fw1.write(string_s);
            }
            catch (IOException e){
                e.printStackTrace();
            }

            ObjectMapper objectMapper=Jackson.getNetworkObjectMapper();
            try {
                String chatStr=objectMapper.writeValueAsString(realGetAllChats(s).getData());
                Jackson.writeOnFile(chatStr, s,"chatPage");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < this.getCourses().size(); i++) {
            String string_s=gson.toJson(this.getCourses().get(i));

            String s="c"+this.getCourses().get(i).getNumber();
            try(FileWriter fw1=new FileWriter((String) s)) {
                fw1.write(string_s);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}