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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CalenderController implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    @FXML
    VBox calender;

    public void menu(ActionEvent actionEvent) {
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
            Stage stage = ((Stage) (((Node) (actionEvent.getSource())).getScene()).getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Back to main menu");
        }
        catch (IOException ex) {
            logger.info("couldnt go Back to main menu");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String usernme=OfflineMode.getInstance().getMainMenu().get("username").toString();
        Boolean isProf;

        if(OfflineMode.getInstance().getMainMenu().get("isProfessor").toString().equals("true")){
            isProf=true;
        }
        else{
            isProf=false;
        }

        Response response1=Client.getInstanse(0).getServerController().sendTimeTableRequest(usernme,isProf);

        for (HashMap<String ,Object> item:response1.getAnswer()){
            int num = Integer.parseInt(item.get("number").toString());
            int gp = Integer.parseInt(item.get("group").toString());

            Response response = Client.getInstanse(0).getServerController().sendCoursePageComponents(num, gp);
            if(response.getAnswer().size()==0){
                return;
            }

            for (Map.Entry<String,Object> temp:response.getAnswer().get(1).entrySet()){
                Label label=new Label();
                String[] deadline=temp.getValue().toString().split("startAt");
                label.setText(temp.getKey()+" deadline ->>  "+deadline[0]);
                calender.getChildren().add(label);
            }

        }
    }
}
