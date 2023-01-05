package server;

import Logic.ProfessorDegree;
import Logic.Student;
import server.database.DataBase;
import server.network.ClientHandler;
import server.request.Request;
import server.response.Response;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static InetAddress inetAddress;
    public static Server instance;

    public static Server getInstance(int port){
        if(instance==null){
            instance=new Server(port);
        }
        return instance;
    }

    public static void setInstance(Server server){
        instance=server;
    }
    public static int serverp;

    private final ArrayList<ClientHandler> clients; // All of clients
    private static int clientCount = 0;

    private ServerSocket serverSocket;
//    private Library library;

    private final int port;
    public static boolean running;

    public static InetAddress getInetAddress() {
        return inetAddress;
    }

    public static int getServerp() {
        return serverp;
    }

    public ArrayList<ClientHandler> getClients() {
        return clients;
    }

    public static int getClientCount() {
        return clientCount;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public int getPort() {
        return port;
    }

    public Server(int port) {
        this.port = port;
        clients = new ArrayList<>();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            running = true;

            listenForNewConnection();
        } catch (IOException e) {
            e.printStackTrace(); // Failed to run the server
        }
    }

    private void listenForNewConnection() {
        while (running) {
            try {
                clientCount++;
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientCount, this, socket);
                clients.add(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @SuppressWarnings("unused")
    public void clientDisconnected(ClientHandler clientHandler) {
        // Remove client from clients
        clients.remove(clientHandler);
    }
    public void handleRequest(int clientId, Request request) {
        System.out.println(request);
        switch (request.getRequestType()) {
            case LOGIN -> {
                Response response = DataBase.getInstance().login((String) request.getData("username"), (String) request.getData("password"));
                findClientAndSendResponse(clientId, response);
            }

            case CourseList -> {
                Response response = DataBase.getInstance().CourseList();
                findClientAndSendResponse(clientId, response);
            }

            case AddCourse -> {
                Response response = DataBase.getInstance().AddCourse((String) request.getData("name"),
                        (String) request.getData("faculty"),(String) request.getData("number"),
                        (String) request.getData("unit"),(String) request.getData("professor"),
                        (String) request.getData("degree"),(String) request.getData("dateOfExam"),
                        (String) request.getData("ta"),(String) request.getData("pre"),
                        (String) request.getData("co"),(String) request.getData("classDate"),
                        (Integer) request.getData("capacity"));
                findClientAndSendResponse(clientId, response);
            }

            case DeleteCourse -> {
                Response response = DataBase.getInstance().DeleteCourse((String) request.getData("number"),
                        (String) request.getData("group"));
                findClientAndSendResponse(clientId, response);
            }

            case EditCourse -> {
                Response response = DataBase.getInstance().EditCourse((String) request.getData("name"),
                        (String) request.getData("faculty"),(String) request.getData("number"),
                        (String) request.getData("unit"),(String) request.getData("professor"),
                        (String) request.getData("degree"),(String) request.getData("dateOfExam"),
                        (String) request.getData("group"));
                findClientAndSendResponse(clientId, response);
            }

            case ProfessorList -> {
                Response response = DataBase.getInstance().ProffesorList();
                findClientAndSendResponse(clientId, response);
            }

            case AddProffesor -> {
                Response response = DataBase.getInstance().AddProfessor((String) request.getData("FirstName"),
                        (String) request.getData("LastName"),(String) request.getData("profNumber"),
                        (String) request.getData("username"),(String) request.getData("professorDegree"),
                        (String) request.getData("faculty"),(String) request.getData("roomNumber"));
                findClientAndSendResponse(clientId, response);
            }

            case EditProffesor -> {
                Response response = DataBase.getInstance().EditProfessor((String) request.getData("professorDegree"),
                        (String) request.getData("roomNumber"),(String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case DeleteProffesor -> {
                Response response = DataBase.getInstance().DeleteProfessor((String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case ProfessorCourseList -> {
                Response response = DataBase.getInstance().ProfessorCourseList((String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case finalProfessorCourseList -> {
                Response response = DataBase.getInstance().finalProfessorCourseList((String) request.getData("header"),
                        (String) request.getData("mark"),(String) request.getData("username"),
                        (String) request.getData("finalState"),(String) request.getData("object"),
                        (String) request.getData("profResponse"));
                findClientAndSendResponse(clientId, response);
            }

            case TemporaryGrades -> {
                Response response = DataBase.getInstance().TemporaryGrades((String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case objectingToProf -> {
                Response response = DataBase.getInstance().objectingToProf((String) request.getData("header"),
                        (String) request.getData("mark"),(String) request.getData("username"),
                        (String) request.getData("finalState"),(String) request.getData("object"),
                        (String) request.getData("profResponse"));
                findClientAndSendResponse(clientId, response);
            }

            case EducationalStatus -> {
                Response response = DataBase.getInstance().EducationalStatus((String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case EducationalRequest -> {
                Response response = DataBase.getInstance().EducationalRequest((String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case ReqForBothProfAndStu -> {
                Response response = DataBase.getInstance().ReqForBothProfAndStu((String) request.getData("rep"),
                        (String) request.getData("header"));
                findClientAndSendResponse(clientId, response);
            }

            case ProffesorRequest -> {
                Response response = DataBase.getInstance().ProffesorRequest((String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case TimeTable -> {
                Response response = DataBase.getInstance().TimeTable((String) request.getData("username"),
                        (boolean) request.getData("isprof"));
                findClientAndSendResponse(clientId, response);
            }

            case chngE -> {
                Response response = DataBase.getInstance().chngE((String) request.getData("username"),
                        (String) request.getData("email"), (boolean) request.getData("isprof"));
                findClientAndSendResponse(clientId, response);
            }

            case chngP -> {
                Response response = DataBase.getInstance().chngP((String) request.getData("username"),
                        (String) request.getData("phone"), (boolean) request.getData("isprof"));
                findClientAndSendResponse(clientId, response);
            }

            case NewStudent -> {
                Response response = DataBase.getInstance().NewStudent((Student) request.getData("student"));
                findClientAndSendResponse(clientId, response);
            }

            case NewProf -> {
                Response response = DataBase.getInstance().NewProf((String) request.getData("text1"),
                        (String) request.getData("text2"),(String) request.getData("text3"),
                        (String) request.getData("text4"),(String) request.getData("text5"),
                        (String) request.getData("text6"),(String) request.getData("text"),
                        (int) request.getData("parseInt"),(int) request.getData("parseInt"),
                        (ProfessorDegree) request.getData("d"),(ArrayList<Object>) request.getData("objects1"),
                        (String) request.getData("fac"),(boolean) request.getData("b"),
                        (ArrayList<Object>) request.getData("objects"));
                findClientAndSendResponse(clientId, response);
            }

            case chngPass -> {
                Response response = DataBase.getInstance().chngPass((String) request.getData("username"),
                        (String) request.getData("password"));
                findClientAndSendResponse(clientId, response);
            }

            case getPreviousPass -> {
                Response response = DataBase.getInstance().getPreviousPass((String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case CourseOfFaculty -> {
                Response response = DataBase.getInstance().CourseOfFaculty((String) request.getData("faculty"));
                findClientAndSendResponse(clientId, response);
            }

            case markCourse -> {
                Response response = DataBase.getInstance().markCourse((String) request.getData("username"),
                        (String) request.getData("courseNum"),(String) request.getData("group"));
                findClientAndSendResponse(clientId, response);
            }

            case catchCourse -> {
                Response response = DataBase.getInstance().catchCourse((String) request.getData("username"),
                        (String) request.getData("courseNum"),(String) request.getData("group"),
                        (boolean) request.getData("isGruopChng"));
                findClientAndSendResponse(clientId, response);
            }

            case ReqCachCourseVice -> {
                Response response = DataBase.getInstance().ReqCachCourseVice((String) request.getData("username"),
                        (String) request.getData("courseNum"),(String) request.getData("group"));
                findClientAndSendResponse(clientId, response);
            }

            case deleteUnit -> {
                Response response = DataBase.getInstance().deleteUnit((String) request.getData("username"),
                        (String) request.getData("courseNum"),(String) request.getData("group"));
                findClientAndSendResponse(clientId, response);
            }

            case returnOtherGroups -> {
                Response response = DataBase.getInstance().returnOtherGroups((String) request.getData("courseNum"),
                        (String) request.getData("group"));
                findClientAndSendResponse(clientId, response);
            }

            case userHasTheCourseOrNot -> {
                Response response = DataBase.getInstance().userHasTheCourseOrNot((String) request.getData("username"),
                        (String) request.getData("courseNum"),(String) request.getData("group"));
                findClientAndSendResponse(clientId, response);
            }

            case giveRecommendedCourses -> {
                Response response = DataBase.getInstance().giveRecommendedCourses((String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case giveMarkedCourses -> {
                Response response = DataBase.getInstance().giveMarkedCourses((String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case getAllChats -> {
                Response response = DataBase.getInstance().getAllChats((String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case getNameOfUser -> {
                Response response = DataBase.getInstance().getNameOfUser((String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case sendFromMeToThem -> {
                Response response = DataBase.getInstance().sendFromMeToThem((String) request.getData("yourUsername"),
                        (String) request.getData("theirUsername"),(String) request.getData("mssg"));
                findClientAndSendResponse(clientId, response);
            }

            case newChatContacts -> {
                Response response = DataBase.getInstance().newChatContacts((String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case SendToAmin -> {
                Response response = DataBase.getInstance().SendToAmin((String) request.getData("username"),
                        (String) request.getData("message"));
                findClientAndSendResponse(clientId, response);
            }

            case getAdminMessages -> {
                Response response = DataBase.getInstance().getAdminMessages((String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case sendAdminResponseToStudent -> {
                Response response = DataBase.getInstance().sendAdminResponseToStudent((String) request.getData("stuUsername"),
                        (String) request.getData("message"));
                findClientAndSendResponse(clientId, response);
            }

            case getAdminResponseToStudent -> {
                Response response = DataBase.getInstance().getAdminResponseToStudent((String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case getMohseniStudents -> {
                Response response = DataBase.getInstance().getMohseniStudents();
                findClientAndSendResponse(clientId, response);
            }

            case getContent -> {
                Response response = DataBase.getInstance().getContent((String) request.getData("type"),
                        (Integer) request.getData("courseNum"), (Integer) request.getData("courseGroup"),
                        (String) request.getData("contentName"));
                findClientAndSendResponse(clientId, response);
            }

            case getTaaOfCourse -> {
                Response response = DataBase.getInstance().getTaaOfCourse((Integer) request.getData("num"),
                        (Integer) request.getData("gp"));
                findClientAndSendResponse(clientId, response);
            }

            case setContent -> {
                Response response = DataBase.getInstance().setContent((String) request.getData("type"),
                        (Integer) request.getData("courseNum"), (Integer) request.getData("courseGroup"),
                        (String) request.getData("contentName"),(String) request.getData("media"));
                findClientAndSendResponse(clientId, response);
            }

            case deleteContent -> {
                Response response = DataBase.getInstance().deleteContent((String) request.getData("type"),
                        (Integer) request.getData("courseNum"), (Integer) request.getData("courseGroup"),
                        (String) request.getData("contentName"));
                findClientAndSendResponse(clientId, response);
            }

            case uploadHomeWork -> {
                Response response = DataBase.getInstance().uploadHomeWork((String) request.getData("nameOfHomeWork"),
                        (Integer) request.getData("courseNum"), (Integer) request.getData("courseGroup"),
                        (String) request.getData("username"), (String) request.getData("homework"));
                findClientAndSendResponse(clientId, response);
            }

            case sendUploadedHomework -> {
                Response response = DataBase.getInstance().sendUploadedHomework((String) request.getData("nameOfHomeWork"),
                        (Integer) request.getData("courseNum"), (Integer) request.getData("courseGroup"),
                        (String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case finalContent -> {
                Response response = DataBase.getInstance().finalContent((String) request.getData("type"),
                        (Integer) request.getData("courseNum"), (Integer) request.getData("courseGroup"),
                        (String) request.getData("contentName"));
                findClientAndSendResponse(clientId, response);
            }

            case sendCoursePageComponents -> {
                Response response = DataBase.getInstance().sendCoursePageComponents((Integer) request.getData("courseNum"),
                        (Integer) request.getData("courseGroup"));
                findClientAndSendResponse(clientId, response);
            }

            case deleteItem -> {
                Response response = DataBase.getInstance().deleteItem((Integer) request.getData("courseNum"),
                        (Integer) request.getData("courseGroup"),(String) request.getData("contentName"),
                        (Integer) request.getData("removableNumber"));
                findClientAndSendResponse(clientId, response);
            }

            case getAllUploads -> {
                Response response = DataBase.getInstance().getAllUploads((Integer) request.getData("courseNum"),
                        (Integer) request.getData("courseGroup"),(String) request.getData("nameOfHomeWork"));
                findClientAndSendResponse(clientId, response);
            }

            case setMarkForHomework -> {
                Response response = DataBase.getInstance().setMarkForHomework((Integer) request.getData("courseNum"),
                        (Integer) request.getData("courseGroup"),(String) request.getData("nameOfHomeWork"),
                        (Integer) request.getData("mark"),(String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case ViceCatchCourse -> {
                Response response = DataBase.getInstance().ViceCatchCourse((String) request.getData("courseName"),
                        (String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case addTa -> {
                Response response = DataBase.getInstance().addTA((String) request.getData("courseName"),
                        (String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case sendMessageOfCoursewareAdding -> {
                Response response =DataBase.getInstance().sendMessageOfCoursewareAdding((boolean) request.getData("isTA"));
                findClientAndSendResponse(clientId,response);
            }

            case getMessagesOfStudent -> {
                Response response = DataBase.getInstance().getMessagesOfStudent((String) request.getData("username"));
                findClientAndSendResponse(clientId, response);
            }

            case sendTAcourse -> {
                Response response = DataBase.getInstance().sendTAcourse((String) request.getData("usernme"),
                        (Boolean) request.getData("isProf"));
                findClientAndSendResponse(clientId, response);
            }
        }
    }
    private ClientHandler getClientHandler(int clientId) {
        for(ClientHandler clientHandler: clients) {
            if (clientHandler.getId() == clientId) {
                return clientHandler;
            }
        }
        return null;
    }
    private void findClientAndSendResponse(int clientId, Response response) {
        ClientHandler clientHandler = getClientHandler(clientId);
        if (clientHandler != null) {
            clientHandler.sendResponse(response);
        }
    }

}