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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CoursewareController implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    @FXML
    VBox vbox;

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
        Response response=Client.getInstanse(0).getServerController().sendTimeTableRequest(usernme,isProf);
        Response response1=Client.getInstanse(0).getServerController().sendTAcourse(usernme,isProf);

        for (HashMap<String ,Object> temp:response.getAnswer()){
            Button b=new Button();
            b.setText(temp.get("number")+"-"+temp.get("group")+" >"+temp.get("name"));
            b.setOnAction(event -> loadCourse((Button) event.getSource()));
            vbox.getChildren().add(b);
        }
        for (HashMap<String ,Object> temp:response1.getAnswer()){
            Button b=new Button();
            b.setText(temp.get("number")+"-"+temp.get("group")+" >"+temp.get("name"));
            b.setOnAction(event -> loadCourse((Button) event.getSource()));
            vbox.getChildren().add(b);
        }
    }

    private void loadCourse(Button source) {
        String[] s=source.getText().split(" >");
        String[] ss=s[0].split("-");

        OfflineMode.getInstance().setCurrentCourseNum(Integer.valueOf(ss[0]));
        OfflineMode.getInstance().setCurrentCourseGroup(Integer.valueOf(ss[1]));

        try {
            Stage stage = ((Stage) (((Node) (source)).getScene()).getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CoursePage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("to CoursePage");
        } catch (IOException ex) {
            logger.error("couldnt go to CoursePage.fxml");
        }
    }

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
        }
        catch (IOException ex) {
            logger.info("couldnt go Back to main menu");
        }
    }

}
