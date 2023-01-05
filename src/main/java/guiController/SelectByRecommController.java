package guiController;

import Logic.OfflineMode;
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

public class SelectByRecommController implements Initializable {

    ObservableList<String> ReqType1= FXCollections.observableArrayList("Recommended","Marked");
    static Logger logger = Logger.getLogger(loginController.class);
    public String cn;
    public String gp;

    List<TextField> list1=new ArrayList<>();
    List<TextField> list2=new ArrayList<>();
    List<TextField> list3=new ArrayList<>();
    List<TextField> list4=new ArrayList<>();
    List<TextField> list5=new ArrayList<>();
    List<TextField> list6=new ArrayList<>();
    List<TextField> list7=new ArrayList<>();
    List<TextField> list8=new ArrayList<>();
    List<MenuButton> list9=new ArrayList<>();
    List<TextField> list10=new ArrayList<>();

    @FXML
    ChoiceBox choiceBox;
    @FXML
    VBox v1;
    @FXML
    VBox v2;
    @FXML
    VBox v3;
    @FXML
    VBox v4;
    @FXML
    VBox v5;
    @FXML
    VBox v6;
    @FXML
    VBox v7;
    @FXML
    VBox v8;
    @FXML
    VBox v9;
    @FXML
    VBox v10;
    @FXML
    Label mssg;
    @FXML
    VBox vbox1;
    @FXML
    ChoiceBox choiceBox1;


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
        choiceBox.setItems(ReqType1);
    }
    private void clearTable() {
        v1.getChildren().clear();
        v2.getChildren().clear();
        v3.getChildren().clear();
        v4.getChildren().clear();
        v5.getChildren().clear();
        v9.getChildren().clear();
        v6.getChildren().clear();
        v7.getChildren().clear();
        v8.getChildren().clear();
        v10.getChildren().clear();

        list10.clear();
        list1.clear();
        list2.clear();
        list3.clear();
        list4.clear();
        list5.clear();
        list6.clear();
        list7.clear();
        list8.clear();
        list9.clear();
    }
    private void showComponents(List<HashMap<String, Object>> S) {
        clearTable();
        for (int i = 1; i < 11; i++) {
            int cntr=-1;
            for (HashMap<String ,Object> item : S) {
                cntr++;
                if (i == 1) {
                    TextField t = new TextField();
                    t.setText("("+item.get("number")+")"+item.get("name").toString());
                    v1.getChildren().add(t);
                    list1.add(t);
                }
                if (i == 2) {
                    TextField t = new TextField();
                    t.setText(String.format("%s%n", item.get("professor").toString()));
                    v2.getChildren().add(t);
                    list2.add(t);
                }
                if (i == 3) {
                    TextField t = new TextField();
                    t.setText(String.format("%s%n", item.get("unit").toString()));
                    v3.getChildren().add(t);
                    list3.add(t);
                }
                if (i == 4) {
                    TextField t = new TextField();
                    t.setText(String.format("%s%n", item.get("group").toString()));
                    v4.getChildren().add(t);
                    list4.add(t);
                }
                if (i == 5) {
                    TextField t = new TextField();
                    t.setText(String.format("%s%n", item.get("dateOfExam").toString()));
                    v5.getChildren().add(t);
                    list5.add(t);
                }
                if (i == 6) {
                    TextField t = new TextField();
                    t.setText(String.format("%s%n", item.get("dateOfClass").toString()));
                    v6.getChildren().add(t);
                    list6.add(t);
                }
                if (i == 7) {
                    TextField t = new TextField();
                    t.setText(String.format("%s%n", item.get("capacity").toString()));
                    v7.getChildren().add(t);
                    list7.add(t);
                }
                if (i == 8) {
                    TextField t = new TextField();
                    t.setText(String.format("%s%n", item.get("peopleCatched").toString()));
                    v8.getChildren().add(t);
                    list8.add(t);
                }
                if (i == 9) {
                    if(OfflineMode.getInstance().getMainMenu().get("isProfessor").toString().equals("false")){
                        //TODO
                        if(!userHasTheCourse(item.get("number").toString(),item.get("group").toString())){
                            MenuButton b=new MenuButton();

                            MenuItem m1=new MenuItem("Mark/Unmark");
                            m1.setId(""+cntr);
                            m1.setOnAction(event -> Mark((MenuItem) event.getSource()));

                            MenuItem m2=new MenuItem("Catch");
                            m2.setId(""+cntr);
                            m2.setOnAction(event -> Catch((MenuItem) event.getSource()));

                            MenuItem m3=new MenuItem("Request from Vice");
                            m3.setId(""+cntr);
                            m3.setOnAction(event -> ReqVice((MenuItem) event.getSource()));

                            b.getItems().add(m1);
                            b.getItems().add(m2);
                            b.getItems().add(m3);

                            v9.getChildren().add(b);
                            list9.add(b);
                        }
                        else{
                            MenuButton b=new MenuButton();

                            MenuItem m1=new MenuItem("Mark/Unmark");
                            m1.setId(""+cntr);
                            m1.setOnAction(event -> Mark((MenuItem) event.getSource()));

                            MenuItem m2=new MenuItem("Delete");
                            m2.setId(""+cntr);
                            m2.setOnAction(event -> Delete((MenuItem) event.getSource()));

                            MenuItem m3=new MenuItem("Change Group");
                            m3.setId(""+cntr);
                            m3.setOnAction(event -> ChngGroup((MenuItem) event.getSource()));

                            b.getItems().add(m1);
                            b.getItems().add(m2);
                            b.getItems().add(m3);

                            v9.getChildren().add(b);
                            list9.add(b);
                        }
                    }
                }
                if (i == 1) {
                    TextField t = new TextField();
                    t.setText(String.format("%s%n", item.get("degree").toString()));
                    v10.getChildren().add(t);
                    list10.add(t);
                }
                logger.info("course list information set successfully");
            }
        }
    }
    private void ChngGroup(MenuItem source) {
        String courseNum=list1.get(Integer.parseInt(source.getId())).getText().substring(1,3);
        cn=courseNum;
        String group=list4.get(Integer.parseInt(source.getId())).getText();
        gp=group;
        Response response=Client.getInstanse(0).getServerController().returnOtherGroups(courseNum,group);

        if(response.getStatus()== ResponseStatus.ERROR){
            mssg.setText("this course doesnt have any other group!");
        }
        else{
            vbox1.setVisible(true);
            ObservableList<String> OBLIST= FXCollections.observableArrayList();

            for (Object temp:response.getData().values()){
                OBLIST.add(String.valueOf(temp));
            }

            choiceBox1.setItems(OBLIST);
            mssg.setText("select group!");
        }

    }
    private void Delete(MenuItem source) {
        String courseNum=list1.get(Integer.parseInt(source.getId())).getText().substring(1,3);
        String group=list4.get(Integer.parseInt(source.getId())).getText();
        Response response=Client.getInstanse(0).getServerController().deleteUnit(courseNum,OfflineMode.getInstance().getMainMenu().get("username"),group);

        update();
    }
    private void ReqVice(MenuItem source) {
        String courseNum=list1.get(Integer.parseInt(source.getId())).getText().substring(1,3);
        String group=list4.get(Integer.parseInt(source.getId())).getText();
        Response response=Client.getInstanse(0).getServerController().ReqCachCourseVice(courseNum,group,OfflineMode.getInstance().getMainMenu().get("username"));

        mssg.setText("sent to vice!");
    }
    private void Catch(MenuItem source) {
        String courseNum=list1.get(Integer.parseInt(source.getId())).getText().substring(1,3);
        String group=list4.get(Integer.parseInt(source.getId())).getText();
        Response response=Client.getInstanse(0).getServerController().catchCourse(false,courseNum,OfflineMode.getInstance().getMainMenu().get("username"),group);

        if(response.getStatus()==ResponseStatus.ERROR){
            mssg.setText(response.getErrorMessage());
        }
        else{
            update();
        }
    }
    private void Mark(MenuItem source) {
        String courseNum=list1.get(Integer.parseInt(source.getId())).getText().substring(1,3);
        String group=list4.get(Integer.parseInt(source.getId())).getText();
        Response response=Client.getInstanse(0).getServerController().markCourse(courseNum,OfflineMode.getInstance().getMainMenu().get("username"),group);

        update();
    }
    public void chngSelectedGroup(ActionEvent actionEvent) {
        Response response=Client.getInstanse(0).getServerController().deleteUnit(cn,OfflineMode.getInstance().getMainMenu().get("username"),gp);
        Response response1=Client.getInstanse(0).getServerController().catchCourse(true,cn,OfflineMode.getInstance().getMainMenu().get("username"),choiceBox1.getValue().toString());

        if(response1.getStatus()==ResponseStatus.ERROR){
            mssg.setText(response1.getErrorMessage());
            Response response2=Client.getInstanse(0).getServerController().ReqCachCourseVice(cn,gp,OfflineMode.getInstance().getMainMenu().get("username"));
        }
        update();
    }
    private boolean userHasTheCourse(String CourseNumber, String group) {
        String username=OfflineMode.getInstance().getMainMenu().get("username").toString();
        Response response=Client.getInstanse(0).getServerController().userHasTheCourseOrNot(username,CourseNumber,group);

        if(response.getStatus()==ResponseStatus.OK){
            return true;
        }
        else{
            return false;
        }
    }
    private void update(){
        if(choiceBox.getValue().equals("Recommended")){
            Response response=Client.getInstanse(0).getServerController().giveRecommendedCourses(OfflineMode.getInstance().getMainMenu().get("username"));
            List<HashMap<String ,Object>> R=response.getAnswer();
            showComponents(R);
        }
        if(choiceBox.getValue().equals("Marked")){
            Response response=Client.getInstanse(0).getServerController().giveMarkedCourses(OfflineMode.getInstance().getMainMenu().get("username"));
            List<HashMap<String ,Object>> R=response.getAnswer();
            showComponents(R);
        }
    }
    public void selectMode(ActionEvent actionEvent) {
        if(choiceBox.getValue()==null){
            mssg.setText("Enter Faculty!");
            return;
        }
        update();
    }
}
