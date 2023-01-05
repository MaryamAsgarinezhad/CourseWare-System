package client.network;

import guiController.loginController;
import Logic.OfflineMode;
import Logic.ProfessorDegree;
import Logic.Student;
import client.util.*;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerController {
    public static Stage stage;
    static Logger logger = Logger.getLogger(loginController.class);
    private PrintStream printStream;
    private Socket socket;
    private Scanner scanner;
    private final int port;
    private final ObjectMapper objectMapper;
    public ServerController(int port) {
        this.port = port;
        objectMapper = Jackson.getNetworkObjectMapper();
    }
    public void connectToServer() {
        try {
            Integer port = Config.getConfig().getProperty(Integer.class, "serverPort");
            Socket socket = new Socket(InetAddress.getLocalHost(), port);
            printStream = new PrintStream(socket.getOutputStream());
            scanner = new Scanner(socket.getInputStream());
            this.socket=socket;

            OfflineMode.getInstance().setOffline(false);
        } catch (IOException e) {
            OfflineMode.getInstance().setOffline(true);
            OfflineMode.getInstance().setOfflineFromFirst(true);
            System.out.println("Server Offline!");
        }
    }
    public void sendRequest(Request request) {
        try {
            String requestString = objectMapper.writeValueAsString(request);
            printStream.println(requestString);
            printStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Response sendLoginRequest(String username, String password) {
        Request request = new Request(RequestType.LOGIN);
        request.addData("username", username);
        request.addData("password", password);
        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendCourseListRequest() {
        Request request = new Request(RequestType.CourseList);
        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendAddCourseRequest(String name, String faculty, String number,String  unit,String professor,String degree,String dateOfExam,int capacity,String ta,String pre,String co,String classDate){
        Request request = new Request(RequestType.AddCourse);
        request.addData("name", name);
        request.addData("faculty", faculty);
        request.addData("number", number);
        request.addData("unit", unit);
        request.addData("professor", professor);
        request.addData("degree", degree);
        request.addData("dateOfExam", dateOfExam);
        request.addData("ta", ta);
        request.addData("pre", pre);
        request.addData("co", co);
        request.addData("classDate", classDate);
        request.addData("capacity", capacity);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendDeleteCourseRequest(String number, String group) {
        Request request = new Request(RequestType.DeleteCourse);
        request.addData("number", number);
        request.addData("group", group);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendEditCourseRequest(String name, String faculty, String number, String unit, String professor, String degree, String dateOfExam, String group) {
        Request request = new Request(RequestType.EditCourse);
        request.addData("name", name);
        request.addData("faculty", faculty);
        request.addData("number", number);
        request.addData("unit", unit);
        request.addData("professor", professor);
        request.addData("degree", degree);
        request.addData("dateOfExam", dateOfExam);
        request.addData("group", group);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendProffesorListRequest() {
        Request request = new Request(RequestType.ProfessorList);
        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendAddProffesorRequest(String first, String last, String room, String fac, String usrname, String d, String number) {
        Request request = new Request(RequestType.AddProffesor);
        request.addData("FirstName", first);
        request.addData("LastName", last);
        request.addData("profNumber", number);
        request.addData("username", usrname);
        request.addData("professorDegree", d);
        request.addData("faculty", fac);
        request.addData("roomNumber", room);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendEditProffesorRequest(String room, String d,String username) {
        Request request = new Request(RequestType.EditProffesor);
        request.addData("roomNumber", room);
        request.addData("professorDegree", d);
        request.addData("username", username);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendDeleteProffesorRequest(String usrname) {
        Request request = new Request(RequestType.DeleteProffesor);
        request.addData("username", usrname);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendProffessorCourseRequest(String username) {
        Request request = new Request(RequestType.ProfessorCourseList);
        request.addData("username",username);
        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendFinalProffessorCourseRequest(String username, String header, String mark, String finalState, String object, String profResponse) {
        Request request = new Request(RequestType.finalProfessorCourseList);
        request.addData("username",username);
        request.addData("header",header);
        request.addData("mark",mark);
        request.addData("finalState",finalState);
        request.addData("object",object);
        request.addData("profResponse",profResponse);
        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendTemporaryGradesRequest(String username) {
        Request request = new Request(RequestType.TemporaryGrades);
        request.addData("username",username);
        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendobjectingToProfRequest(String username, String header, String mark, String finalState, String object, String profResponse) {
        Request request = new Request(RequestType.objectingToProf);
        request.addData("username",username);
        request.addData("header",header);
        request.addData("mark",mark);
        request.addData("finalState",finalState);
        request.addData("object",object);
        request.addData("profResponse",profResponse);
        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendEducationalStatusRequest(String username) {
        Request request = new Request(RequestType.EducationalStatus);
        request.addData("username",username);
        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendEducationalRequestRequest(Object username) {
        Request request = new Request(RequestType.EducationalRequest);
        request.addData("username",username);
        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendReqForBothProfAndStu(String header, String rep) {
        Request request = new Request(RequestType.ReqForBothProfAndStu);
        request.addData("header",header);
        request.addData("rep",rep);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendProffesorRequestRequest(Object username) {
        Request request = new Request(RequestType.ProffesorRequest);
        request.addData("username",username);
        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendTimeTableRequest(Object username, boolean ispro) {
        Request request = new Request(RequestType.TimeTable);
        request.addData("username",username);
        request.addData("isprof",ispro);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response chngE(String username, String email, boolean isprof) {
        Request request = new Request(RequestType.chngE);
        request.addData("username",username);
        request.addData("email",email);
        request.addData("isprof",isprof);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response chngP(String username, String phone, boolean isprof) {
        Request request = new Request(RequestType.chngP);
        request.addData("username",username);
        request.addData("phone",phone);
        request.addData("isprof",isprof);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response NewStudent(Student student) {
        Request request = new Request(RequestType.NewStudent);
        request.addData("student",student);
        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response NewProf(String text, int parseInt, ArrayList<Object> objects, Boolean b, String text1, String text2, String text3, String fac, String text4, ArrayList<Object> objects1, ProfessorDegree d, String text5, int parseInt1, String text6) {
        Request request = new Request(RequestType.NewProf);
        request.addData("text",text);
        request.addData("parseInt",parseInt);
        request.addData("objects",objects);
        request.addData("b",b);
        request.addData("text1",text1);
        request.addData("text2",text2);
        request.addData("text3",text3);
        request.addData("text4",text4);
        request.addData("fac",fac);
        request.addData("objects1",objects1);
        request.addData("text5",text5);
        request.addData("d",d);
        request.addData("text6",text6);
        request.addData("parseInt1",parseInt1);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response chngPass(Object username, String password) {
        Request request = new Request(RequestType.chngPass);
        request.addData("username",username);
        request.addData("password",password);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response getPreviousPass(Object username) {
        Request request = new Request(RequestType.getPreviousPass);
        request.addData("username",username);
        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendCourseOfFacultyRequest(Object value) {
        Request request = new Request(RequestType.CourseOfFaculty);
        request.addData("faculty",value);
        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response markCourse(String courseNum, Object username, String group) {
        Request request = new Request(RequestType.markCourse);
        request.addData("username",username);
        request.addData("courseNum",courseNum);
        request.addData("group",group);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response catchCourse(boolean isGruopChng,String courseNum, Object username,String group) {
        Request request = new Request(RequestType.catchCourse);
        request.addData("isGruopChng",isGruopChng);
        request.addData("username",username);
        request.addData("courseNum",courseNum);
        request.addData("group",group);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response ReqCachCourseVice(String courseNum, String group, Object username) {
        Request request = new Request(RequestType.ReqCachCourseVice);
        request.addData("username",username);
        request.addData("courseNum",courseNum);
        request.addData("group",group);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response deleteUnit(String courseNum, Object username, String group) {
        Request request = new Request(RequestType.deleteUnit);
        request.addData("username",username);
        request.addData("courseNum",courseNum);
        request.addData("group",group);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response returnOtherGroups(String courseNum, String group) {
        Request request = new Request(RequestType.returnOtherGroups);
        request.addData("courseNum",courseNum);
        request.addData("group",group);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response userHasTheCourseOrNot(String username, String courseNumber, String group) {
        Request request = new Request(RequestType.userHasTheCourseOrNot);
        request.addData("username",username);
        request.addData("courseNum",courseNumber);
        request.addData("group",group);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response giveRecommendedCourses(Object username) {
        Request request = new Request(RequestType.giveRecommendedCourses);
        request.addData("username",username);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response giveMarkedCourses(Object username) {
        Request request = new Request(RequestType.giveMarkedCourses);
        request.addData("username",username);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response getAllChats(Object username) {
        Request request = new Request(RequestType.getAllChats);
        request.addData("username",username);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response getNameOfUser(String username) {
        Request request = new Request(RequestType.getNameOfUser);
        request.addData("username",username);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendFromMeToThem(String yourUsername, String theirUsername, String mssg) {
        Request request = new Request(RequestType.sendFromMeToThem);
        request.addData("yourUsername",yourUsername);
        request.addData("theirUsername",theirUsername);
        request.addData("mssg",mssg);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response newChatContacts(Object username) {
        Request request = new Request(RequestType.newChatContacts);
        request.addData("username",username);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response SendToAmin(String message, Object username) {
        Request request = new Request(RequestType.SendToAmin);
        request.addData("username",username);
        request.addData("message",message);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response getAdminMessages(Object username) {
        Request request = new Request(RequestType.getAdminMessages);
        request.addData("username",username);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendAdminResponseToStudent(String stuUsername, String message) {
        Request request = new Request(RequestType.sendAdminResponseToStudent);
        request.addData("stuUsername",stuUsername);
        request.addData("message",message);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response getAdminResponseToStudent(Object username) {
        Request request = new Request(RequestType.getAdminResponseToStudent);
        request.addData("username",username);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response getMohseniStudents() {
        Request request = new Request(RequestType.getMohseniStudents);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response getContent(String type, int courseNum,int courseGroup, String contentName) {
        Request request = new Request(RequestType.getContent);
        request.addData("type",type);
        request.addData("courseNum",courseNum);
        request.addData("courseGroup",courseGroup);
        request.addData("contentName",contentName);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response getTaaOfCourse(int num, int gp) {
        Request request = new Request(RequestType.getTaaOfCourse);
        request.addData("num",num);
        request.addData("gp",gp);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response setContent(String type, int num, int gp, String contentName, String media) {
        Request request = new Request(RequestType.setContent);
        request.addData("type",type);
        request.addData("courseNum",num);
        request.addData("courseGroup",gp);
        request.addData("contentName",contentName);
        request.addData("media",media);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response deleteContent(String type, int courseNum, int courseGroup, String contentName) {
        Request request = new Request(RequestType.deleteContent);
        request.addData("type",type);
        request.addData("courseNum",courseNum);
        request.addData("courseGroup",courseGroup);
        request.addData("contentName",contentName);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response finalContent(String type, int num, int gp, String contentName) {
        Request request = new Request(RequestType.finalContent);
        request.addData("type",type);
        request.addData("courseNum",num);
        request.addData("courseGroup",gp);
        request.addData("contentName",contentName);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendCoursePageComponents(int num, int gp) {
        Request request = new Request(RequestType.sendCoursePageComponents);
        request.addData("courseNum",num);
        request.addData("courseGroup",gp);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response deleteItem(int courseNum, int courseGroup, String contentName, int removableNumber) {
        Request request = new Request(RequestType.deleteItem);
        request.addData("courseNum",courseNum);
        request.addData("courseGroup",courseGroup);
        request.addData("contentName",contentName);
        request.addData("removableNumber",removableNumber);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response uploadHomeWork(int num, int gp, String nameOfHomeWork, String username, String homework) {
        Request request = new Request(RequestType.uploadHomeWork);
        request.addData("courseNum",num);
        request.addData("courseGroup",gp);
        request.addData("nameOfHomeWork",nameOfHomeWork);
        request.addData("username",username);
        request.addData("homework",homework);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response sendUploadedHomework(int courseNum, int courseGroup, String nameOfContent, Object username) {
        Request request = new Request(RequestType.sendUploadedHomework);
        request.addData("courseNum",courseNum);
        request.addData("courseGroup",courseGroup);
        request.addData("nameOfHomeWork",nameOfContent);
        request.addData("username",username);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Response getAllUploads(int courseNum, int courseGroup, String contentName) {
        Request request = new Request(RequestType.getAllUploads);
        request.addData("courseNum",courseNum);
        request.addData("courseGroup",courseGroup);
        request.addData("nameOfHomeWork",contentName);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response setMarkForHomework(int courseNum, int courseGroup, String nameOfHomework, String username, int mark) {
        Request request = new Request(RequestType.setMarkForHomework);
        request.addData("courseNum",courseNum);
        request.addData("courseGroup",courseGroup);
        request.addData("nameOfHomeWork",nameOfHomework);
        request.addData("username",username);
        request.addData("mark",mark);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response ViceCatchCourse(String username, String courseName) {
        Request request = new Request(RequestType.ViceCatchCourse);
        request.addData("courseName",courseName);
        request.addData("username",username);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response addTa(String username, String courseName) {
        Request request = new Request(RequestType.addTa);
        request.addData("courseName",courseName);
        request.addData("username",username);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response sendMessageOfCoursewareAdding(boolean isTA) {
        Request request = new Request(RequestType.sendMessageOfCoursewareAdding);
        request.addData("isTA",isTA);


        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response getMessagesOfStudent(Object username) {
        Request request = new Request(RequestType.getMessagesOfStudent);
        request.addData("username",username);


        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response sendTAcourse(String usernme, Boolean isProf) {
        Request request = new Request(RequestType.sendTAcourse);
        request.addData("usernme",usernme);
        request.addData("isProf",isProf);

        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Boolean checkConnection() {
        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NoSuchElementException n){
            return false;
        }
        catch (NullPointerException n){
            return false;
        }
        return true;
    }
}