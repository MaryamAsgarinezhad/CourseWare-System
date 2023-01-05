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

public class ExamListController  implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    @FXML
    HBox hbox;
    @FXML
    VBox v1;
    @FXML
    VBox v2;
    @FXML
    Label l1;
    @FXML
    Label l2;
    @FXML
    TextArea t1;
    @FXML
    TextArea t2;

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
            initiateExamListPage2(User.getInstance(null).getJsonObject());
            return;
        }

        String s=OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1);
        if(s.equals("a") || s.equals("v") || s.equals("f")){
            ispro=true;
        }

        Response response=Client.getInstanse(0).getServerController().sendTimeTableRequest(OfflineMode.getInstance().getMainMenu().get("username"),ispro);
        OfflineMode.getInstance().setExamList(response.getAnswer());

        List<HashMap<String ,Object>> C=OfflineMode.getInstance().getExamList();

        initiateExamListPage(C);
    }

    private void initiateExamListPage2(JSONObject jsonObject) {
        String[] ss=jsonObject.get("courses").toString().substring(1,jsonObject.get("courses").toString().length()-1).split(",");
        List<JSONObject> jsonObjects=new ArrayList<>();
        for (String temp:ss){
            if(temp.equals("")){
                continue;
            }
            jsonObjects.add(Jackson.getjsonOfFile("c"+temp));
        }

        List<JSONObject> jsonObjects2=new ArrayList<>(jsonObjects);
        List<JSONObject> jsonObjects3=new ArrayList<>();

        for (int i = 0; i < jsonObjects.size(); i++){
            JSONObject v=jsonObjects2.get(0);

            for(JSONObject item: jsonObjects2){
                if(Double.parseDouble(priority(item.get("dateOfExam").toString()))<Double.parseDouble(priority(v.get("dateOfExam").toString()))){
                    v=item;
                }
            }

            jsonObjects3.add(v);
            jsonObjects2.remove(v);
        }

        for (int i = 1; i < 3; i++) {
            String S="";
            for(JSONObject item: jsonObjects3){
                if(i==1){
                    S+=String.format("%s%n",item.get("name").toString());
                    t1.setText(S);
                }
                if(i==2){
                    S+=String.format("%s%n",item.get("dateOfExam").toString());
                    t2.setText(S);
                }
            }
        }

    }

    private void initiateExamListPage(List<HashMap<String, Object>> C) {
        List<HashMap<String ,Object>> C2=new ArrayList<>(C);
        List<HashMap<String ,Object>> C3=new ArrayList<>();

        for (int i = 0; i < C.size(); i++){
            HashMap<String ,Object> v=C2.get(0);

            for(HashMap<String ,Object> item: C2){
                if(Double.parseDouble(priority(item.get("dateOfExam").toString()))<Double.parseDouble(priority(v.get("dateOfExam").toString()))){
                    v=item;
                }
            }

            C3.add(v);
            C2.remove(v);
        }

        for (int i = 1; i < 3; i++) {
            String S="";
            for(HashMap<String ,Object> item: C3){
                if(i==1){
                    S+=String.format("%s%n",item.get("name").toString());
                    t1.setText(S);
                }
                if(i==2){
                    S+=String.format("%s%n",item.get("dateOfExam").toString());
                    t2.setText(S);
                }
            }
        }
    }

    public String priority(String string) {
        String[] s=string.split("\\.");

        String[] ss=s[1].split("/");

        String[] s2=ss[2].split(" AT ");
        String[] s3=s2[1].split(";");

        String year=ss[0].substring(1);
        String mounth=ss[1];
        if(mounth.length()==1){
            mounth=0+mounth;
        }

        String dayMounth=s2[0];
        if(dayMounth.length()==1){
            dayMounth=0+dayMounth;
        }

        String hour = s3[0];
        if(hour.length()==1){
            hour=0+hour;
        }

        String minute = s3[1];
        if(minute.length()==1){
            minute=0+minute;
        }

        logger.info("priority of the date determied");
        return year+mounth+dayMounth+hour+minute;
    }
}
