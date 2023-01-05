package guiController;

import Logic.OfflineMode;
import Logic.requestType;
import client.Client;
import client.util.Config;
import client.util.Jackson;
import client.util.Response;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
public class RequestController implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    ObservableList<String> ReqType1= FXCollections.observableArrayList("recommendation"  ,  "CertificateStudent"  , "minor"  ,  "withdrawal");
    ObservableList<String> ReqType2= FXCollections.observableArrayList("recommendation"  ,  "CertificateStudent"  ,  "withdrawal"  ,  "dorm");
    ObservableList<String> ReqType3= FXCollections.observableArrayList(  "CertificateStudent"  ,  "withdrawal" ,  "defendTurn");
    @FXML
    Button menu;
    @FXML
    VBox vbox;
    @FXML
    Label replabel;
    @FXML
    VBox rep;
    @FXML
    ChoiceBox type;
    @FXML
    TextField t3;
    @FXML
    TextField t2;
    @FXML
    Button send;
    @FXML
    Label mssg;
    @FXML
    TextField b;

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
    public void sendReq(ActionEvent actionEvent) {
        //analizing req
        HashMap<String ,Object> j =OfflineMode.getInstance().getMainMenu();
        if (type.getValue() == null || t3.getText().equals("") || t2.getText().equals("")) {
            mssg.setText("Fill all Fields");
            return;
        }
        if(type.getValue().equals(requestType.withdrawal.toString()) && !t3.getText().substring(0,1).equals("v")){
            mssg.setText("withdrawal request should be sent to vice");
            return;
        }
        if(type.getValue().equals(requestType.minor.toString())){
            if(b.getText().equals("")){
                mssg.setText("Enter destination faculty number");
                return;
            }
            if(!isNumeric(b.getText())){
                mssg.setText("destination faculty number you entered isnt numeric");
                return;
            }

            Integer minGrade = Config.getConfig().getProperty(Integer.class, "minorGrade");
            Integer minUnit = Config.getConfig().getProperty(Integer.class, "minorUnit");

            if(Integer.parseInt(j.get("maenScore").toString())<minGrade ){
                mssg.setText("Minor request cant be registered because your average score is below Extend.");
                return;
            }

            String destVice="v"+j.get("faculty").toString().substring(1,2);
            if(!t3.getText().substring(0,2).equals(destVice)){
                mssg.setText("Send minor to your faculty Vice");
                return;
            }
            else{
                mssg.setText("Minor registered!");
            }
        }

        //preparing req
        String header="";
        String rep="";
        if(type.getValue().equals(requestType.minor.toString())){
            header="minor"+"&&"+b.getText()+"&&"+j.get("username")+"&&"+t3.getText();
        }
        else{
            header=type.getValue().toString()+"&&"+j.get("username")+"&&"+t3.getText();
        }

        //sending req
        Response response=Client.getInstanse(0).getServerController().sendReqForBothProfAndStu(header,rep);

        mssg.setText("Sent successfully, wait for reply!");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Response response=Client.getInstanse(0).getServerController().sendEducationalRequestRequest(OfflineMode.getInstance().getMainMenu().get("username"));
        OfflineMode.getInstance().setEducationalRequest(response.getData());

        HashMap<String ,Object> C=OfflineMode.getInstance().getEducationalRequest();

        initiateEducationalReqPage(C);
    }
    private void initiateEducationalReqPage(HashMap<String, Object> C) {
        for (Map.Entry<String ,Object> temp: C.entrySet()){
            if(temp.getValue().toString().equals("") || temp.getValue().toString().equals(" ")){
                System.out.println("pppp");
                if(OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("b")){
                    type.setItems(ReqType1);
                }
                if(OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("m")){
                    type.setItems(ReqType2);
                }
                if(OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("d")){
                    type.setItems(ReqType3);
                }

                logger.info("request page initializad successfully");
                continue;
            }
            String[] s=temp.getValue().toString().split("&&");

            String reply="";
            int indexOfReciever;
            if(s.length==4){
                indexOfReciever=2;
                reply=s[3];
            }
            else{
                indexOfReciever=3;
                reply=s[4];
            }

            if(!s[indexOfReciever].equals(OfflineMode.getInstance().getMainMenu().get("username"))){
                TextArea t=new TextArea();
                t.setText(reply);
                t.setMaxHeight(75);
                rep.getChildren().add(t);
            }
        }

        if(OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("b")){
            type.setItems(ReqType1);
        }
        if(OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("m")){
            type.setItems(ReqType2);
        }
        if(OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("d")){
            type.setItems(ReqType3);
        }

        logger.info("request page initializad successfully");
    }
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}
