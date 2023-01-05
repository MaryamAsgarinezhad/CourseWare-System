package guiController;

import Logic.OfflineMode;
import client.Client;
import client.util.Jackson;
import client.util.Response;
import client.util.ResponseStatus;
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
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class TemporaryGradesController implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    List<TextField> stuList=new ArrayList<>();

    List<String> profUsername=new ArrayList<>();
    List<TextField> courseList=new ArrayList<>();
    List<TextField> grade=new ArrayList<>();
    List<TextField> prfessorList=new ArrayList<>();

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
    Label l5;

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
    VBox v5;
    @FXML
    Label mssg;


    @FXML
    public void send(Button source) throws IOException, ParseException {
        String mark="";
        String[] s=grade.get(Integer.parseInt(source.getId())).getText().split("\\(");
        if(s[1].equals("f)")){
            mssg.setText("Cant modify finaled score");
            return;
        }
        mark=s[0];

        if(mark.equals("")){
            mssg.setText("Enter grade");
            return;
        }

        String header= courseList.get(Integer.parseInt(source.getId())).getText().substring(1,3)+"&&"+profUsername.get(Integer.parseInt(source.getId()))+"&&"+OfflineMode.getInstance().getMainMenu().get("username");

        String finalState="t";
        String Object;
        if(stuList.get(Integer.parseInt(source.getId())).getText().equals("")){
            Object=" ";
        }
        else{
            Object=stuList.get(Integer.parseInt(source.getId())).getText();
        }

        String profResponse;
        if(prfessorList.get(Integer.parseInt(source.getId())).getText().equals("")){
            profResponse=" ";
        }
        else{
            profResponse=prfessorList.get(Integer.parseInt(source.getId())).getText();
        }


        Response response = Client.getInstanse(0).getServerController().sendobjectingToProfRequest(OfflineMode.getInstance().getMainMenu().get("username").toString(),header,mark,finalState,Object,profResponse);

        if(response.getStatus()== ResponseStatus.ERROR){
            mssg.setText(response.getErrorMessage());
            return;
        }

        OfflineMode.getInstance().setTemporaryGrades(response.getAnswer());

        List<HashMap<String ,Object>> S=OfflineMode.getInstance().getTemporaryGrades();

        clearTable();
        initiateTemporaryGradesPage(S);
    }
    private void clearTable() {
        stuList.clear();
        courseList.clear();
        prfessorList.clear();
        grade.clear();

        v1.getChildren().clear();
        v2.getChildren().clear();
        v3.getChildren().clear();
        v4.getChildren().clear();
        v5.getChildren().clear();
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
        } catch (IOException ex) {
            logger.info("couldnt go Back to main menu");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Response response = Client.getInstanse(0).getServerController().sendTemporaryGradesRequest((String) OfflineMode.getInstance().getMainMenu().get("username"));
        OfflineMode.getInstance().setTemporaryGrades(response.getAnswer());

        List<HashMap<String ,Object>> S=OfflineMode.getInstance().getTemporaryGrades();
        initiateTemporaryGradesPage(S);
    }
    private void initiateTemporaryGradesPage(List<HashMap<String, Object>> S) {
        for (int i = 1; i < 6; i++) {
            int a=-1;
            for(HashMap<String ,Object> item: S){
                a++;
                if(i==1){
                    TextField t=new TextField();

                    //training
                    profUsername.add(item.get("ProffessorName").toString().substring(1,4));
                    //test
                    t.setText(item.get("CourseName").toString());
                    v1.getChildren().add(t);
                    courseList.add(t);
                }
                if(i==2){
                    TextField t=new TextField();
                    t.setText(item.get("grade").toString());
                    v2.getChildren().add(t);
                    grade.add(t);
                }
                if(i==3){
                    TextField t=new TextField();
                    t.setText(item.get("StudentObject").toString());
                    v3.getChildren().add(t);
                    stuList.add(t);
                }
                if(i==4){
                    TextField t=new TextField();
                    t.setText(item.get("ProffessorResponse").toString());
                    v4.getChildren().add(t);
                    prfessorList.add(t);
                }
                if(i==5){
                    Button b=new Button();
                    b.setId(""+a);
                    b.setText("send");
                    v5.getChildren().add(b);
                    b.setOnAction(event -> {
                        try {
                            send((Button) event.getSource());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    });
                }
                logger.info("students grades shown successfully");
            }
        }
    }
}
