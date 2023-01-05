package guiController;

import Logic.OfflineMode;
import Logic.requestType;
import client.Client;
import client.util.Jackson;
import client.util.Response;
import com.example.demo.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ProfessorRequestController implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    List<TextField> list1 = new ArrayList<>();
    @FXML
    HBox hbox;
    @FXML
    HBox hbox2;
    @FXML
    VBox v1;
    @FXML
    VBox v2;
    @FXML
    Label l1;
    @FXML
    Label l2;

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Response response=Client.getInstanse(0).getServerController().sendProffesorRequestRequest(OfflineMode.getInstance().getMainMenu().get("username"));
        OfflineMode.getInstance().setProfessorRequest(response.getData());

        HashMap<String ,Object> C=OfflineMode.getInstance().getProfessorRequest();
        initiateProfessorReqPage(C);
    }
    private void initiateProfessorReqPage(HashMap<String, Object> C) {
        int cntr=-1;
        for (Map.Entry<String ,Object> temp: C.entrySet()){
            if(temp.getValue().toString().equals("") || temp.getValue().toString().equals(" ")){
                continue;
            }else{
                cntr++;
                for (int i = 0; i < 2; i++) {
                    if(i==0){
                        TextField t=new TextField();
                        String[] s=temp.getValue().toString().split("&&");
                        if(s.length==4){
                            t.setText(s[0]+"&&"+s[1]+"&&"+s[2]);
                        }
                        else{
                            t.setText(s[0]+"&&"+s[1]+"&&"+s[2]+"&&"+s[3]);
                        }
                        t.setMaxHeight(75);
                        v1.getChildren().add(t);
                        list1.add(t);
                    }
                    if(i==1){
                        MenuButton b = new MenuButton();
                        b.setId("" + cntr);
                        b.setMinWidth(92);

                        MenuItem m1 = new MenuItem("Accept");
                        m1.setId("" + cntr);
                        m1.setOnAction(event -> {
                            try {
                                accept((MenuItem) event.getSource());
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        });

                        MenuItem m2 = new MenuItem("Deny");
                        m2.setId("" + cntr);
                        m2.setOnAction(event -> {
                            try {
                                deny((MenuItem) event.getSource());
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        });

                        b.getItems().add(m1);
                        b.getItems().add(m2);

                        v2.getChildren().add(b);
                        logger.info("professor request page initialized");
                    }
                }
            }
        }
    }
    private void accept(MenuItem source) throws IOException, ParseException {
        String[] s= list1.get(Integer.parseInt(source.getId())).getText().split("&&");
        Response response=Client.getInstanse(0).getServerController().sendLoginRequest(s[1],"mainmenu");

        OfflineMode.getInstance().setResponseToEducationalReq(response.getData());
        HashMap<String ,Object> student=OfflineMode.getInstance().getResponseToEducationalReq();

        String string="";
        HashMap<String ,Object> obj=OfflineMode.getInstance().getMainMenu();

        if(s[0].equals("MessageRequest")){
            string=String.format("Message request from %s accepted",NameOfUser(s[2]));

            //initiate chat
            String yourUsername=OfflineMode.getInstance().getMainMenu().get("username").toString();
            String theirUsername=s[1];
            String mssg=string;

            //set time
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH;mm;ss");
            LocalDateTime now = LocalDateTime.now();
            String lastTime=dtf.format(now);
            mssg+="  "+lastTime;

            Client.getInstanse(0).getServerController().sendFromMeToThem(yourUsername,theirUsername,mssg);
        }

        if(s[0].equals(requestType.recommendation.toString())){
            string=String.format(" I '%s' Affirm that student '%s',%n with student number '%s',%n and average score '%s' is confirmed.",obj.get("FirstName")+" "+obj.get("LastName"),student.get("FirstName")+" "+student.get("LastName"),student.get("StuNumber"),student.get("maenScore"));
        }

        if(s[0].equals(requestType.CertificateStudent.toString())){
            string=String.format(" I '%s' Affirm that student '%s',%n with student number '%s',%n and average score '%s' is studing '%s' at SUT.%n Expiration year:%s.",obj.get("FirstName")+" "+obj.get("LastName"),student.get("FirstName")+" "+student.get("LastName"),student.get("StuNumber"),student.get("maenScore"),"perfectly","1403");
        }

        if(s.length==4){
            //send to vice
            String fac=s[1];
            String vice="v"+fac+"3";

            String header=s[0]+"&&"+s[2]+"&&"+vice;
            String rep="";
            Client.getInstanse(0).getServerController().sendReqForBothProfAndStu(header,rep);

            string="Minor request registered,sent to destination faculty vice.";
            return;
        }
        else{
            if(s[0].equals(requestType.minor.toString())){
                string="minor accepted!";
            }
        }

        if(s[0].equals(requestType.withdrawal.toString())){
            string="Widrawal accepted";
        }

        if(s[0].equals(requestType.dorm.toString())){
            Random rd = new Random();
            boolean accepted=rd.nextBoolean();

            if(accepted){
                string="Dorm request accepted";
            }
            else {
                string="Dorm request not accepted";
            }
        }

        if(s[0].equals(requestType.defendTurn.toString())){
            string=String.format("At %s you can defend.",1400/7/28 );
        }

        //done
        String header=s[0]+"&&"+s[1]+"&&"+s[2];
        Client.getInstanse(0).getServerController().sendReqForBothProfAndStu(header,string);
    }
    private void deny(MenuItem source) throws IOException, ParseException {
        String[] s= list1.get(Integer.parseInt(source.getId())).getText().split("&&");
        Response response=Client.getInstanse(0).getServerController().sendLoginRequest(s[1],"mainmenu");

        OfflineMode.getInstance().setResponseToEducationalReq(response.getData());
        HashMap<String ,Object> student=OfflineMode.getInstance().getResponseToEducationalReq();

        String string="";
        HashMap<String ,Object> obj=OfflineMode.getInstance().getMainMenu();

        if(s[0].equals("MessageRequest")){
            string=String.format("Message request from %s denies!",NameOfUser(s[2]));
        }

        if(s[0].equals(requestType.recommendation.toString())){
            string="recommendation request denied!";
        }

        if(s[0].equals(requestType.CertificateStudent.toString())){
            string="CertificateStudent request denied!";
        }

        if(s[0].equals(requestType.minor.toString())){
            string="minor denied!";
        }

        if(s[0].equals(requestType.withdrawal.toString())){
            string="Widrawal denied!";
        }

        if(s[0].equals(requestType.dorm.toString())){
            Random rd = new Random();
            boolean accepted=rd.nextBoolean();

            if(accepted){
                string="Dorm request accepted";
            }
            else {
                string="Dorm request not accepted";
            }
        }

        if(s[0].equals(requestType.defendTurn.toString())){
            string="defendTurn request denied!";
        }

        //done
        String header=s[0]+"&&"+s[1]+"&&"+s[2];
        Client.getInstanse(0).getServerController().sendReqForBothProfAndStu(header,string);
    }
    private String NameOfUser(String s) {
        Response response=Client.getInstanse(0).getServerController().getNameOfUser(s);
        return response.getErrorMessage();
    }

}
