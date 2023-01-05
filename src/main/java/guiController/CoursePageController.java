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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CoursePageController implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    private String username;
    @FXML
    TextField contentName;
    @FXML
    Label taLabel;
    @FXML
    Label mssg;
    @FXML
    VBox v1;
    @FXML
    VBox v2;
    @FXML
    VBox calender;
    @FXML
    TextField AddStu;
    @FXML
    Button b1;
    @FXML
    Button b2;
    @FXML
    HBox hhh;
    public void Courseware(ActionEvent actionEvent) {
        if(!OfflineMode.getInstance().isOffline()){
            try {
                Stage stage = ((Stage) (((Node) (actionEvent.getSource())).getScene()).getWindow());
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Courseware.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                stage.setScene(scene);
                stage.show();
                logger.info("Back to course page");
            } catch (IOException ex) {
                logger.info("couldnt go Back to course page");
            }
        }
        else{
            String username=OfflineMode.getInstance().getMainMenu().get("username").toString();
            JSONObject jsonObject= Jackson.getjsonOfFile(username);
            User.setInstance(new User(jsonObject));

            try {
                Stage stage = ((Stage) (((Node) (actionEvent.getSource())).getScene()).getWindow());
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                stage.setScene(scene);
                stage.show();
                logger.info("Back to course page");
            } catch (IOException ex) {
                logger.info("couldnt go Back to course page");
            }
        }
    }
    public void sortByrelease(ActionEvent actionEvent) {
        List<Button> buttons=new ArrayList<>();
        for (Node b:v2.getChildren()){
            buttons.add((Button) b);
        }
        int size=buttons.size();
        List<Button> buttons2=new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Button b=buttons.get(0);

            for (Button temp:buttons){
                if(comparative1(temp).compareTo(comparative1(b))<0){
                    b=temp;
                }
            }

            buttons.remove(b);
            buttons2.add(b);
        }

        v2.getChildren().clear();
        for (Button b:buttons2){
            v2.getChildren().add(b);
        }
    }
    private String  comparative1(Button temp) {
        String[] s=temp.getId().split("startAt");
        return s[1];
    }
    public void sortByTimeRemained(ActionEvent actionEvent) {List<Button> buttons=new ArrayList<>();
        for (Node b:v2.getChildren()){
            buttons.add((Button) b);
        }
        int size=buttons.size();
        List<Button> buttons2=new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Button b=buttons.get(0);

            for (Button temp:buttons){
                if(comparative2(temp).compareTo(comparative2(b))<0){
                    b=temp;
                }
            }

            buttons.remove(b);
            buttons2.add(b);
        }

        v2.getChildren().clear();
        for (Button b:buttons2){
            v2.getChildren().add(b);
        }
    }
    private String comparative2(Button temp) {
        String[] s=temp.getId().split("startAt");
        return s[0];
    }
    public void newEducationalContent(ActionEvent actionEvent) {
        if(contentName.getText().equals("")){
            mssg.setText("Enter name of content!");
        }
        else{
            OfflineMode.getInstance().setCurrentContent(contentName.getText());
            OfflineMode.getInstance().setNewingContent(true);
            try {
                Stage stage = ((Stage) (((Node) (actionEvent.getSource())).getScene()).getWindow());
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("NewContent.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                stage.setScene(scene);
                stage.show();
                logger.info("Back to content");
            } catch (IOException ex) {
                logger.info("couldnt go to content");
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username=OfflineMode.getInstance().getMainMenu().get("username").toString();
        int num = OfflineMode.getInstance().getCurrentCourseNum();
        int gp = OfflineMode.getInstance().getCurrentCourseGroup();

        Response response = Client.getInstanse(0).getServerController().sendCoursePageComponents(num, gp);
        if(response.getAnswer().size()==0){
            return;
        }

        Object[] objects1=response.getAnswer().get(1).entrySet().toArray();
        int i=-1;

        if(OfflineMode.getInstance().getMainMenu().get("isProfessor").toString().equals("false")){
            b2.setVisible(false);
            b1.setVisible(false);
            contentName.setVisible(false);
            hhh.setVisible(false);
        }
        for (Map.Entry<String, Object> temp : response.getAnswer().get(0).entrySet()) {
            if (temp.getKey().substring(0, 7).equals("content")) {
                Button button = new Button();
                button.setText("content--" + (String) temp.getValue());
                button.setOnAction(event -> OpenContent((Button) event.getSource()));
                v1.getChildren().add(button);
            }
            if (temp.getKey().substring(0, 8).equals("homework")) {
                i++;
                Button button = new Button();
                String[] vals=objects1[i].toString().split("=");
                button.setId(vals[1]);
                button.setText("homework--" + vals[0]);
                button.setOnAction(event -> OpenHomework((Button) event.getSource()));
                v2.getChildren().add(button);
            }
        }

        for (Map.Entry<String,Object> temp:response.getAnswer().get(1).entrySet()){
            Label label=new Label();
            String[] deadline=temp.getValue().toString().split("startAt");
            label.setText(temp.getKey()+" deadline ->>  "+deadline[0]);
            calender.getChildren().add(label);
        }

    }
    private void OpenHomework(Button source) {
        OfflineMode.getInstance().setNewingContent(false);
        String[] s=source.getText().split("--");
        String type=s[0];
        OfflineMode.getInstance().setCurrentContent(s[1]);
        OfflineMode.getInstance().setCurrenttype(type);

        try {
            Stage stage = ((Stage) (((Node) (source)).getScene()).getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("NewHomework.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Back to NewHomework");
        } catch (IOException ex) {
            logger.info("couldnt go Back to NewHomework");
        }
    }
    private void OpenContent(Button source) {
        OfflineMode.getInstance().setNewingContent(false);
        String[] s=source.getText().split("--");
        String type=s[0];
        OfflineMode.getInstance().setCurrentContent(s[1]);
        OfflineMode.getInstance().setCurrenttype(type);

        try {
            Stage stage = ((Stage) (((Node) (source)).getScene()).getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("NewContent.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Back to content");
        } catch (IOException ex) {
            logger.info("couldnt go Back to content");
        }
    }
    public void newHomework(ActionEvent actionEvent) {
        OfflineMode.getInstance().setNewingContent(true);
        OfflineMode.getInstance().setCurrenttype("homework");
        try {
            Stage stage = ((Stage) (((Node) (actionEvent.getSource())).getScene()).getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("NewHomework.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Back to NewHomework");
        } catch (IOException ex) {
            logger.info("couldnt go to NewHomework");
        }
    }
    public void addStu(ActionEvent actionEvent) {
        if(AddStu.getText().equals("")){
            mssg.setText("Enter Username!");
        }
        else{
            String num= String.valueOf(OfflineMode.getInstance().getCurrentCourseNum());
            String gp= String.valueOf(OfflineMode.getInstance().getCurrentCourseGroup());
            String courseName= num+"---"+gp;
            String username=AddStu.getText();

            Response response=Client.getInstanse(0).getServerController().ViceCatchCourse(username,courseName);
            mssg.setText("Student added!");
        }
    }
    public void addTA(ActionEvent actionEvent) {
        if(AddStu.getText().equals("")){
            mssg.setText("Enter Username!");
        }
        else{
            String num= String.valueOf(OfflineMode.getInstance().getCurrentCourseNum());
            String gp= String.valueOf(OfflineMode.getInstance().getCurrentCourseGroup());
            String courseName= num+"---"+gp;
            String username=AddStu.getText();

            Response response=Client.getInstanse(0).getServerController().addTa(username,courseName);
            mssg.setText("TA added!");
        }
    }
}
