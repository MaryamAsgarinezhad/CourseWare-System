package guiController;

import Logic.OfflineMode;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;


public class TimeTableController implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    @FXML
    Button menu;
    @FXML
    Label l1;
    @FXML
    Label l2;
    @FXML
    Label l3;
    @FXML
    Label l4;

    @FXML
    HBox hbox;
    @FXML
    VBox v1;
    @FXML
    VBox v2;
    @FXML
    VBox v3;
    @FXML
    VBox v4;

    @FXML
    TextArea t1;
    @FXML
    TextArea t2;
    @FXML
    TextArea t3;
    @FXML
    TextArea t4;

    public void menu(ActionEvent e) {
        if(!OfflineMode.getInstance().isOffline()){
            Response response = Client.getInstanse(0).getServerController().sendLoginRequest((String) OfflineMode.getInstance().getMainMenu().get("username"), "mainmenu");
            //setting offline features
            OfflineMode.getInstance().setMainMenu(response.getData());
        }
        else{
            if(!OfflineMode.getInstance().isOfflineFromFirst()){
                String username=OfflineMode.getInstance().getMainMenu().get("username").toString();
                JSONObject jsonObject= Jackson.getjsonOfFile(username);
                User.setInstance(new User(jsonObject));
            }
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
        boolean ispro=false;

        if(OfflineMode.getInstance().isOffline()){
            System.out.println("sal");
            initiateTimeTablePage2(User.getInstance(null).getJsonObject());
            return;
        }

        String s=OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1);
        if(s.equals("a") || s.equals("v") || s.equals("f")){
            ispro=true;
        }

        Response response=Client.getInstanse(0).getServerController().sendTimeTableRequest(OfflineMode.getInstance().getMainMenu().get("username"),ispro);
        OfflineMode.getInstance().setTimeTable(response.getAnswer());

        List<HashMap<String ,Object>> C=OfflineMode.getInstance().getTimeTable();

        initiateTimeTablePage(C);
    }
    private void initiateTimeTablePage2(JSONObject jsonObject) {
        System.out.println("sal");
        String[] ss=jsonObject.get("courses").toString().substring(1,jsonObject.get("courses").toString().length()-1).split(",");
        System.out.println("sal");

        List<JSONObject> jsonObjects=new ArrayList<>();
        for (String temp:ss){
            if(temp.equals("")){
                continue;
            }
            jsonObjects.add(Jackson.getjsonOfFile("c"+temp));
        }

        for (int i = 1; i < 5; i++) {
            String s="";
            for(JSONObject item: jsonObjects){

                if(i==1){
                    s+=String.format("%s%n",item.get("name").toString());
                    t1.setText(s);
                }
                if(i==2){
                    s+=String.format("%s%n",item.get("professor").toString());
                    t2.setText(s);
                }
                if(i==3){
                    s+=String.format("%s%n",item.get("faculty").toString());
                    t3.setText(s);
                }
                if(i==4) {
                    s+=String.format("%s%n",item.get("dateOfClass").toString());
                    t4.setText(s);
                }

                logger.info("time table initialization finished");
            }
        }
    }
    private void initiateTimeTablePage(List<HashMap<String, Object>> C) {
        for (int i = 1; i < 5; i++) {
            String s="";
            for(HashMap<String, Object> item: C){
                if(i==1){
                    s+=String.format("%s%n",item.get("name").toString());
                    t1.setText(s);
                }
                if(i==2){
                    s+=String.format("%s%n",item.get("professor").toString());
                    t2.setText(s);
                }
                if(i==3){
                    s+=String.format("%s%n",item.get("faculty").toString());
                    t3.setText(s);
                }
                if(i==4) {
                    s+=String.format("%s%n",item.get("dateOfClass").toString());
                    t4.setText(s);
                }

                logger.info("time table initialization finished");
            }
        }
    }
}
