package guiController;

import Logic.OfflineMode;
import client.Client;
import client.util.Response;
import com.example.demo.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.util.Config;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    static Logger logger = Logger.getLogger(loginController.class);
    @FXML
    Button logoutBtn;
    @FXML
    ColorPicker colorPicker;
    @FXML
    Pane pane;
    @FXML
    Label email;
    @FXML
    Label l1;
    @FXML
    Label l2;

    @FXML
    Label l11;
    @FXML
    MenuButton reportCard;
    @FXML
    MenuButton userprofile;
    @FXML
    MenuButton Registeration;
    @FXML
    MenuItem CourseList;
    @FXML
    Label isOff;
    @FXML
    MenuItem ProfessorList;
    @FXML
    MenuItem TemporaryGrades;
    @FXML
    MenuItem  ViewProfile;

    @FXML
    ImageView img;

    @FXML
    HBox hbox;
    @FXML
    HBox h2;
    @FXML
    VBox v1;
    @FXML
    VBox v2;
    @FXML
    VBox vvvv;
    @FXML
    MenuItem e;
    @FXML
    Button Reconnect;
    @FXML
    MenuButton selectUnits;
    @FXML
    MenuButton Courseware;

    @FXML
    public void TimeTable(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) Registeration.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TimeTableView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Entered Time Table page");
        } catch (IOException ex) {
            logger.error("couldnt find Time table page");
        }
    }
    @FXML
    public void ExamList(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) Registeration.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ExamListView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Entered ExamList page");
        } catch (IOException ex) {
            logger.error("couldnt find ExamList page");
        }
    }
    @FXML
    public void EducationalRequest(ActionEvent e) {
        if(OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("b") || OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("m") || OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("d")){
            try {
                Stage stage = (Stage) Registeration.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Request.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                stage.setScene(scene);
                stage.show();
                logger.info("Entered Student Request page");
            } catch (IOException ex) {
                logger.error("couldnt find Student Request page");
            }
        }
        else {
            try {
                Stage stage = (Stage) Registeration.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ProfessorRequestView.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                stage.setScene(scene);
                stage.show();
                logger.info("Entered Professor Request page");
            } catch (IOException ex) {
                logger.error("couldnt find Professor Request page");
            }
        }

    }
    @FXML
    public void NewUser(ActionEvent e){
        try {
            Stage stage = ((Stage) (((Node) (e.getSource())).getScene()).getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("NewUser.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Entered NewUser page");
        } catch (IOException ex) {
            logger.error("couldnt find NewUser page");
        }
    }
    private void trueLogout(ActionEvent e) {

        try {
            Stage stage = ((Stage) (((Node) (e.getSource())).getScene()).getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Logout done successfully");
        } catch (IOException ex) {
            logger.error("couldnt Logout successfully");
        }
    }
    public void Logout(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Careful!");
        alert.setHeaderText("login confirmation");
        alert.setContentText("Are you sure you want to logout?");
        if(alert.showAndWait().get() == ButtonType.OK){
            logger.info("User accepted to logout!");
            trueLogout(e);
        }
    }
    public void courselist(ActionEvent e) {
        try {
            Stage stage = (Stage) Registeration.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CourseListView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Entered CourseList page");
        } catch (IOException ex) {
            logger.error("couldnt Enter CourseList page");
        }
    }
    public void ProfessorList(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) Registeration.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ProfessorList.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Entered ProfessorList page");
        } catch (IOException ex) {
            logger.error("couldnt Enter ProfessorList page");
        }
    }
    public void TemporaryGrades(ActionEvent actionEvent) {
        if(OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("b") || OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("m") || OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("d")){
            try {
                Stage stage = (Stage) Registeration.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TemporaryGradesView.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                stage.setScene(scene);
                stage.show();
                logger.info("Entered student temporary grades page");
            } catch (IOException ex) {
                logger.error("couldnt Enter student temporary grades page");
            }
        }
        else{
            try {
                Stage stage = (Stage) Registeration.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ProfessorCourseView.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                stage.setScene(scene);
                stage.show();
                logger.info("Entered Professor Course grades page");
            } catch (IOException ex) {
                logger.error("couldnt Enter Professor Course grades page");
            }
        }
    }

    public void EducationalStatus(ActionEvent actionEvent) {
        if(OfflineMode.getInstance().isOffline() && User.getInstance(null).getJsonObject().get("isProfessor").equals("true")){
            return;
        }
        try {
            Stage stage = (Stage) Registeration.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("EducationalStatusView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Entered Educational Status page");
        } catch (IOException ex) {
            logger.error("couldnt Enter Educational Status page");
        }
    }
    public void ViewProfile(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) Registeration.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ProfileView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Entered Profile View page");
        } catch (IOException ex) {
            logger.error("couldnt Enter Profile View page");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isOff.setText(String.valueOf(OfflineMode.getInstance().isOfflineFromFirst()));
        Courseware.setVisible(false);
        selectUnits.setVisible(false);
        HashMap<String ,Object> ans= OfflineMode.getInstance().getMainMenu();

        if(!OfflineMode.getInstance().isOffline()){
            Reconnect.setVisible(false);
            initiateMainMenu(ans);
            //setvsible
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH");
            LocalDateTime now = LocalDateTime.now();
            String nowStr=dtf.format(now);

            String AddedOrNot=getjsonOfFile("added").get("isSupervisor").toString();
            String endTime= Config.getConfig().getProperty(String.class, "endOfSelectUnit");
            if(AddedOrNot.equals("false") && endTime.compareTo(nowStr)<0){
                EditUnitsCatched();
                Courseware.setVisible(true);
                writeOnadded("true");
            }
            if(AddedOrNot.equals("true") && endTime.compareTo(nowStr)<0){
                Courseware.setVisible(true);
            }
        }
        else{
            initiateMainMenu2(User.getInstance(null).getJsonObject());
        }
    }
    public void StuInit(HashMap<String ,Object> ans){
        HashMap<String ,Object> user=OfflineMode.getInstance().getMainMenu();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH");
        LocalDateTime now = LocalDateTime.now();
        String nowStr=dtf.format(now);

        if(user.get("RegistrationTime").equals(nowStr) && user.get("hasRegistrationlicense").equals("true")){
            selectUnits.setVisible(true);
        }

        Label l11=new Label();
        l11.setText("Educational Status");
        l11.setMinHeight(50);

        Label l12=new Label();
        l12.setText("Supervisor");
        l12.setMinHeight(50);

        Label l13=new Label();
        l13.setText("Registration Licence");
        l13.setMinHeight(50);

        Label l14=new Label();
        l14.setText("Registration Time");
        l14.setMinHeight(50);

        v1.getChildren().add(l11);
        v1.getChildren().add(l12);
        v1.getChildren().add(l13);
        v1.getChildren().add(l14);

        Label l21=new Label();

        l21.setText(ans.get("educationalstatus").toString());
        l21.setMinHeight(50);

        Label l22=new Label();
        l22.setText(ans.get("Supervisor").toString());
        l22.setMinHeight(50);

        boolean b;
        if(ans.get("hasRegistrationlicense").equals("true")){
            b=true;
        }
        else{
            b=false;
        }

        Label l23=new Label();
        l23.setText(b ?"Has Registration license":"Doesnt have Registration license");
        l23.setMinHeight(50);

        Label l24=new Label();
        l24.setText(ans.get("RegistrationTime").toString());
        l24.setMinHeight(50);

        v2.getChildren().add(l21);
        v2.getChildren().add(l22);
        v2.getChildren().add(l23);
        v2.getChildren().add(l24);
    }
    private void writeOnadded(String aTrue) {
        JSONObject jsonObject=getjsonOfFile("added");
        jsonObject.put("isSupervisor",aTrue);

        try(PrintWriter out=new PrintWriter(new FileWriter("array"))) {
            out.write(jsonObject.toJSONString());
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    private JSONObject getjsonOfFile(String added) {
        JSONParser jsonParser=new JSONParser();
        try (FileReader fileReader=new FileReader("array")){
            Object object=jsonParser.parse(fileReader);
            return (JSONObject)object;
        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
    private void EditUnitsCatched() {
        boolean isTA=false;
        Response response= Client.getInstanse(0).getServerController().sendMessageOfCoursewareAdding(isTA);
    }
    public void color(ActionEvent event) {
        Color color=colorPicker.getValue();
        pane.setBackground(new Background(new BackgroundFill(color,null,null)));
    }
    public void initiateMainMenu(HashMap<String ,Object> ans){
//        selectUnit.setVisible(false);
        if(!ans.get("lastEnterTime").equals("")){
            l11.setText((String) ans.get("lastEnterTime"));
        }

        if(ans.get("username").toString().substring(0,1).equals("a") || ans.get("username").toString().substring(0,1).equals("f")){
            e.setVisible(false);
        }

        l1.setText(ans.get("FirstName").toString()+" "+ans.get("LastName").toString());
        l2.setText(ans.get("Email").toString());
        try {
            if(ans.get("faculty").toString().equals("(1)mathematics")){
                img.setImage(new PersonImage().getImage("man"));
                logger.info("setting user image by its faculty");
            }
            else{
                img.setImage(new PersonImage().getImage("woman"));
            }
        } catch (FileNotFoundException ex) {
            logger.error("couldnt find user to initializa main menu page");
        }

        MenuButton b = new MenuButton();
        b.setText("Educational Service");
        b.setMinWidth(157);

        MenuItem m1 = new MenuItem("TimeTable");
        m1.setOnAction(this::TimeTable);

        MenuItem m2 = new MenuItem("ExamList");
        m2.setOnAction(this::ExamList);

        MenuItem m3 = new MenuItem("EducationalRequest");
        m3.setOnAction(this::EducationalRequest);

        b.getItems().add(m1);
        b.getItems().add(m2);
        b.getItems().add(m3);

        h2.getChildren().add(b);

        if(ans.get("username").toString().substring(0,1).equals("b") || ans.get("username").toString().substring(0,1).equals("m") || ans.get("username").toString().substring(0,1).equals("d")){
            logger.info("initializing student information in main menu");
            StuInit(ans);
        }
        else {
            if(ans.get("username").toString().substring(0,1).equals("v")){
                Button bb=new Button();
                bb.setText("Create a new User");
                bb.setMinWidth(157);
                bb.setOnAction(this::NewUser);
                h2.getChildren().add(bb);
            }
        }

        logger.info("main menu initialization done successfully");
    }
    private void StuInit2(JSONObject ans) {
        Label l11=new Label();
        l11.setText("Educational Status");
        l11.setMinHeight(50);

        Label l12=new Label();
        l12.setText("Supervisor");
        l12.setMinHeight(50);

        Label l13=new Label();
        l13.setText("Registration Licence");
        l13.setMinHeight(50);

        Label l14=new Label();
        l14.setText("Registration Time");
        l14.setMinHeight(50);

        v1.getChildren().add(l11);
        v1.getChildren().add(l12);
        v1.getChildren().add(l13);
        v1.getChildren().add(l14);

        Label l21=new Label();

        l21.setText(ans.get("educationalstatus").toString());
        l21.setMinHeight(50);

        Label l22=new Label();
        l22.setText(ans.get("Supervisor").toString());
        l22.setMinHeight(50);

        boolean b;
        if(ans.get("hasRegistrationlicense").equals("true")){
            b=true;
        }
        else{
            b=false;
        }

        Label l23=new Label();
        l23.setText(b ?"Has Registration license":"Doesnt have Registration license");
        l23.setMinHeight(50);

        Label l24=new Label();
        l24.setText(ans.get("RegistrationTime").toString());
        l24.setMinHeight(50);

        v2.getChildren().add(l21);
        v2.getChildren().add(l22);
        v2.getChildren().add(l23);
        v2.getChildren().add(l24);
    }
    public void initiateMainMenu2(JSONObject ans){
//        selectUnit.setVisible(false);
        if(!ans.get("lastEnterTime").equals("")){
            l11.setText((String) ans.get("lastEnterTime"));
        }

        if(ans.get("username").toString().substring(0,1).equals("a") || ans.get("username").toString().substring(0,1).equals("f")){
            e.setVisible(false);
        }

        l1.setText(ans.get("FirstName").toString()+" "+ans.get("LastName").toString());
        l2.setText(ans.get("Email").toString());
        try {
            if(ans.get("faculty").toString().equals("(1)mathematics")){
                img.setImage(new PersonImage().getImage("man"));
                logger.info("setting user image by its faculty");
            }
            else{
                img.setImage(new PersonImage().getImage("woman"));
            }
        } catch (FileNotFoundException ex) {
            logger.error("couldnt find user to initializa main menu page");
        }

        MenuButton b = new MenuButton();
        b.setText("Educational Service");
        b.setMinWidth(157);

        MenuItem m1 = new MenuItem("TimeTable");
        m1.setOnAction(this::TimeTable);

        MenuItem m2 = new MenuItem("ExamList");
        m2.setOnAction(this::ExamList);

        MenuItem m3 = new MenuItem("EducationalRequest");
        m3.setOnAction(this::EducationalRequest);

        b.getItems().add(m1);
        b.getItems().add(m2);
        b.getItems().add(m3);

        h2.getChildren().add(b);

        if(ans.get("username").toString().substring(0,1).equals("b") || ans.get("username").toString().substring(0,1).equals("m") || ans.get("username").toString().substring(0,1).equals("d")){
            logger.info("initializing student information in main menu");
            StuInit2(ans);
        }
        else {
            if(ans.get("username").toString().substring(0,1).equals("v")){
                Button bb=new Button();
                bb.setText("Create a new User");
                bb.setMinWidth(157);
                bb.setOnAction(this::NewUser);
                h2.getChildren().add(bb);
            }
        }

        logger.info("main menu initialization done successfully");
    }
    public void selectUnit(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) Registeration.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentSelectUnit.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Entered StudentSelectUnit page");
        } catch (IOException ex) {
            logger.error("couldnt Enter StudentSelectUnit page");
        }
    }
    public void SelectByRecomm(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) Registeration.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SelectByRecomm.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Entered SelectByRecomm page");
        } catch (IOException ex) {
            logger.error("couldnt Enter SelectByRecomm page");
        }
    }
    public void chat(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) Registeration.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Chat.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Entered Chat page");
        } catch (IOException ex) {
            logger.error("couldnt Enter Chat page");
        }
    }
    public void Messages(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) Registeration.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Messages.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Entered Messages page");
        } catch (IOException ex) {
            logger.error("couldnt Enter Messages page");
        }
    }
    public void AdminMessages(ActionEvent actionEvent) {
        if(!OfflineMode.getInstance().isOffline()){
            if(OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("A")){
                try {
                    Stage stage = (Stage) Registeration.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AdminResponse.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    stage.setScene(scene);
                    stage.show();
                    logger.info("Entered student temporary grades page");
                } catch (IOException ex) {
                    logger.error("couldnt Enter student temporary grades page");
                }
            }
            else{
                try {
                    Stage stage = (Stage) Registeration.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Admin.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    stage.setScene(scene);
                    stage.show();
                    logger.info("Entered Professor Course grades page");
                } catch (IOException ex) {
                    logger.error("couldnt Enter Professor Course grades page");
                }
            }
        }
        else{
            try {
                Stage stage = (Stage) Registeration.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Admin.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                stage.setScene(scene);
                stage.show();
                logger.info("Entered Professor Course grades page");
            } catch (IOException ex) {
                logger.error("couldnt Enter Professor Course grades page");
            }
        }
    }
    public void Courseware(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) Registeration.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Courseware.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Entered student temporary grades page");
        } catch (IOException ex) {
            logger.error("couldnt Enter student temporary grades page");
        }
    }

    public void Reconnect(ActionEvent actionEvent) {
        Client.getInstanse(0).start();
    }

    public void calender(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) Registeration.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Calender.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Entered student temporary grades page");
        } catch (IOException ex) {
            logger.error("couldnt Enter student temporary grades page");
        }
    }
}
