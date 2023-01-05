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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdminResponseController implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    List<javafx.scene.control.TextField> list1=new ArrayList<javafx.scene.control.TextField>();
    List<javafx.scene.control.TextField> list2=new ArrayList<javafx.scene.control.TextField>();
    @FXML
    VBox v1;
    @FXML
    VBox v2;
    @FXML
    VBox v3;
    @FXML
    Label mssg;

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
        Response response=Client.getInstanse(0).getServerController().getAdminMessages(OfflineMode.getInstance().getMainMenu().get("username"));

        HashMap<String ,Object> C=response.getData();
        initatePage(C);
    }
    private void initatePage(HashMap<String, Object> C) {
        int cntr=-1;
        for (Map.Entry<String ,Object> temp: C.entrySet()){
            if(temp.getValue().toString().equals("") || temp.getValue().toString().equals(" ")){
                continue;
            }else{
                cntr++;
                for (int i = 0; i < 3; i++) {
                    if(i==0){
                        javafx.scene.control.TextField t=new javafx.scene.control.TextField();
                        t.setText(temp.getValue().toString());

                        v1.getChildren().add(t);
                        list1.add(t);
                    }
                    if(i==2){
                        javafx.scene.control.TextField t=new javafx.scene.control.TextField();

                        v2.getChildren().add(t);
                        list2.add(t);
                    }
                    if(i==1){
                        javafx.scene.control.Button b = new Button();
                        b.setText("send");
                        b.setId("" + cntr);
                        b.setMinWidth(65);
                        b.setOnAction(event -> sendToStudent((Button) event.getSource()));

                        v3.getChildren().add(b);
                        logger.info("professor request page initialized");
                    }
                }
            }
        }
    }
    private void sendToStudent(Button source) {
        TextField t=list2.get(Integer.parseInt(source.getId()));

        if(t.getText().equals("")){
            mssg.setText("Enter a text!");
        }
        else{
            TextField t1=list1.get(Integer.parseInt(source.getId()));
            String[] s=t1.getText().split("&&");
            String stuUsername=s[0];
            String message=t.getText();
            Client.getInstanse(0).getServerController().sendAdminResponseToStudent(stuUsername,message);

            mssg.setText("sent successfully!");
        }
    }
}
