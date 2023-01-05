package guiController;

import Logic.*;
import client.Client;
import client.util.Jackson;
import client.util.Response;
import client.util.ResponseStatus;
import com.example.demo.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class NewUserController implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    ObservableList<String> status= FXCollections.observableArrayList("studiying"  ,  "graduated"  , "withdrawed");
    ObservableList<String> has= FXCollections.observableArrayList("yes"  ,  "no");
    ObservableList<String> stuDegree= FXCollections.observableArrayList("doc"  ,  "master" , "bachelor");
    ObservableList<String> proDegree= FXCollections.observableArrayList("Assistant"  ,  "Vice" , "Boss");

    @FXML
    Button menu;
    @FXML
    HBox h;
    @FXML
    VBox v1;
    @FXML
    TextField t1;
    @FXML
    TextField t2;
    @FXML
    ChoiceBox t3;
    @FXML
    TextField t4;
    @FXML
    TextField t5;
    @FXML
    TextField t6;
    @FXML
    TextField t7;
    @FXML
    TextField t8;
    @FXML
    TextField t9;
    @FXML
    TextField t10;
    @FXML
    TextField t11;
    @FXML
    TextField t12;
    @FXML
    ChoiceBox t13;
    @FXML
    ChoiceBox t14;
    @FXML
    DatePicker t15;
    @FXML
    TextField t16;

    @FXML
    VBox v2;
    @FXML
    TextField k;
    @FXML
    TextField k1;
    @FXML
    ChoiceBox k2;
    @FXML
    TextField k3;
    @FXML
    TextField k4;
    @FXML
    TextField k5;
    @FXML
    ChoiceBox k6;
    @FXML
    TextField k7;
    @FXML
    TextField k8;
    @FXML
    TextField k9;
    @FXML
    TextField k10;

    @FXML
    VBox v;
    @FXML
    Label a1;
    @FXML
    Label a2;
    @FXML
    Label a3;
    @FXML
    Label a4;
    @FXML
    Label a5;
    @FXML
    Label a6;
    @FXML
    Label a7;
    @FXML
    Label a8;
    @FXML
    Label a9;
    @FXML
    Label a10;
    @FXML
    Label a11;
    @FXML
    Label a12;
    @FXML
    Label a13;
    @FXML
    Label a14;
    @FXML
    Label a15;
    @FXML
    Label a16;

    @FXML
    Label mssg;
    @FXML
    Button b1;
    @FXML
    Button b2;

    @FXML
    TextField P1;
    @FXML
    TextField P2;

    public void menu(ActionEvent e) {
        if(!OfflineMode.getInstance().isOffline()){
            Response response = Client.getInstanse(0).getServerController().sendLoginRequest((String) OfflineMode.getInstance().getMainMenu().get("username"), "mainmenu");
            //setting offline features
            OfflineMode.getInstance().setMainMenu(response.getData());
        }
        else{
            String username=OfflineMode.getInstance().getMainMenu().get("username").toString();
            JSONObject jsonObject= Jackson.getjsonOfFile(username);
            User.setInstance(new User(jsonObject));
        }

        try {
            Stage stage = ((Stage) (((Node) (e.getSource())).getScene()).getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Back to main menu");
        } catch (IOException ex) {
            logger.info("couldnt go Back to main menu");
        }
    }
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
    public void addStudent(ActionEvent event) {
        if(t1.getText().equals("") || t2.getText().equals("") || t3.getSelectionModel().isEmpty() || t4.getText().equals("") || t5.getText().equals("") || t6.getText().equals("") || t7.getText().equals("") || t8.getText().equals("") || t10.getText().equals("") || t11.getText().equals("") || t12.getText().equals("") || t13.getSelectionModel().isEmpty() || t14.getSelectionModel().isEmpty() || t15.getValue() == null || t16.getText().equals("")){
            mssg.setText("fill all fields!");
        }
        else{
            Pattern pattern = Pattern.compile("\\d\\d\\:\\d\\d");
            if(!pattern.matcher(t16.getText()).matches()){
                mssg.setText("Enter Registration time in right foramt");
                return;
            }
            if(!(isNumeric(t5.getText()) && isNumeric(t10.getText()) && isNumeric(t12.getText()))){
                mssg.setText("Fill numeric fields with a number");
                return;
            }
            else{
                String[] s=t16.getText().split(":");
                date date=new date(t15.getValue().getYear(),t15.getValue().getMonthValue(),t15.getValue().getDayOfMonth(),Integer.parseInt(s[0]),Integer.parseInt(s[1]),t15.getValue().getDayOfWeek());
                EducationalStatus E=EducationalStatus.studying;
                if(t13.getValue().equals("studying")){
                    E=EducationalStatus.studying;
                }
                if(t13.getValue().equals("graduated")){
                    E=EducationalStatus.graduated;
                }
                if(t13.getValue().equals("withdrawed")){
                    E=EducationalStatus.withdrawed;
                }

                Boolean b=true;
                if(t14.getValue().equals("yes")){
                    b=true;
                }
                else {
                    b=false;
                }

                String fac="";
                if(t5.getText().equals("1")){
                    fac="(1)mathematics";
                }
                if(t5.getText().equals("2")){
                    fac="(2)aerospace";
                }
                if(t5.getText().equals("3")){
                    fac="(3)Mechanics";
                }
                if(t5.getText().equals("4")){
                    fac="(4)Computer";
                }
                if(t5.getText().equals("5")){
                    fac="(5)electronics";
                }

                Degree d=Degree.doc;
                if(t3.getValue().equals("doc")){
                    d=Degree.doc;
                }
                if(t3.getValue().equals("bachelor")){
                    d=Degree.bachelor;
                }
                if(t3.getValue().equals("master")){
                    d=Degree.master;
                }

                Student student=new Student(new ArrayList<>(),t1.getText(),t2.getText(),t4.getText(),fac,t6.getText(),Integer.parseInt(t10.getText()),t11.getText(),t8.getText(),Integer.parseInt(t12.getText()),E,t7.getText(),b,date.toString1(),new ArrayList<>(),new ArrayList<>(),0,d,P1.getText());
                logger.info("new user object made");

                Response response=Client.getInstanse(0).getServerController().NewStudent(student);
                if(response.getStatus()== ResponseStatus.OK){
                    mssg.setText("New student added successfully!");
                }
                else{
                    mssg.setText("New student didnt add successfully.");
                }
            }
        }
    }
    public void addProfessor(ActionEvent event) {
        if(k.getText().equals("") || k1.getText().equals("") || k3.getText().equals("") || k4.getText().equals("") || k5.getText().equals("") || k2.getSelectionModel().isEmpty() || k7.getText().equals("") || k8.getText().equals("") || k6.getSelectionModel().isEmpty()){
            mssg.setText("fill all fields!");
        }
        else{
            if( ! (isNumeric(k4.getText()) && isNumeric(k8.getText())) && isNumeric(k9.getText()) ){
                mssg.setText("Fill numeric fields with a number");
                return;
            }
            Boolean b=true;
            if(k6.getValue().equals("yes")){
                b=true;
            }
            else {
                b=false;
            }

            ProfessorDegree d=ProfessorDegree.Boss;
            if(k2.getValue().equals("Vice")){
                d=ProfessorDegree.Vice;
            }
            if(k2.getValue().equals("Boss")){
                d=ProfessorDegree.Boss;
            }
            if(k2.getValue().equals("Assistant")){
                d=ProfessorDegree.Assistant;
            }

            String fac="";
            if(k4.getText().equals("1")){
                fac="(1)mathematics";
            }
            if(k4.getText().equals("2")){
                fac="(2)aerospace";
            }
            if(k4.getText().equals("3")){
                fac="(3)Mechanics";
            }
            if(k4.getText().equals("4")){
                fac="(4)Computer";
            }
            if(k4.getText().equals("5")){
                fac="(5)electronics";
            }

            logger.info("new user object built");
            Response response=Client.getInstanse(0).getServerController().NewProf(k10.getText(),Integer.parseInt(k9.getText()),new ArrayList<>(),b,k.getText(),k1.getText(),k3.getText(),fac,k5.getText(),new ArrayList<>(),d,k7.getText(),Integer.parseInt(k8.getText()),P2.getText());

            if(response.getStatus()== ResponseStatus.OK){
                mssg.setText("New professor added successfully!");
            }
            else{
                mssg.setText("New professor didnt add successfully.");
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        t13.setItems(status);
        t14.setItems(has);
        t3.setItems(stuDegree);
        k2.setItems(proDegree);
        k6.setItems(has);
    }
}
