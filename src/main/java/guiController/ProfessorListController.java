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
import javafx.scene.control.*;
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
import java.util.regex.Pattern;

public class ProfessorListController implements Initializable {

    static Logger logger = Logger.getLogger(loginController.class);

    List<TextField> list1 = new ArrayList<>();
    List<TextField> list2 = new ArrayList<>();
    List<TextField> list3 = new ArrayList<>();
    List<TextField> list4 = new ArrayList<>();

    @FXML
    Button btn;
    @FXML
    VBox vbox;
    @FXML
    Label filterLabel;
    @FXML
    TextField txt1;
    @FXML
    TextField txt2;
    @FXML
    TextField txt3;

    @FXML
    HBox Hbox;
    @FXML
    VBox v1;
    @FXML
    VBox v2;
    @FXML
    VBox v4;
    @FXML
    VBox v3;
    @FXML
    VBox v5;

    @FXML
    Label l1;
    @FXML
    Label l2;
    @FXML
    Label l4;
    @FXML
    Label l3;
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
        logger.info("initializing student course list started");
        Response response=Client.getInstanse(0).getServerController().sendProffesorListRequest();
        OfflineMode.getInstance().setProffesorList(response.getAnswer());

        List<HashMap<String ,Object>> S=OfflineMode.getInstance().getProffesorList();
        initiateProffesorListPage(S);
    }
    private void A(MenuItem source) {
        String[] name=list1.get(Integer.parseInt(source.getId())).getText().substring(5).split(" ");
        String first=name[0];
        String last=name[1];
        String room=list4.get(Integer.parseInt(source.getId())).getText();
        String fac=OfflineMode.getInstance().getMainMenu().get("faculty").toString();
        String usrname=list1.get(Integer.parseInt(source.getId())).getText().substring(1,4);
        String d=list3.get(Integer.parseInt(source.getId())).getText();
        String number=usrname.substring(1);

        Response response=Client.getInstanse(0).getServerController().sendAddProffesorRequest(first,last,room,fac,usrname,d,number);
        if(response.getStatus()== ResponseStatus.ERROR){
            mssg.setText(response.getErrorMessage());
            return;
        }

        OfflineMode.getInstance().setProffesorList(response.getAnswer());

        List<HashMap<String ,Object>> S=OfflineMode.getInstance().getProffesorList();

        clearTable();
        initiateProffesorListPage(S);
    }
    private void D(MenuItem source) {
        String usrname=list1.get(Integer.parseInt(source.getId())).getText().substring(1,4);

        Response response=Client.getInstanse(0).getServerController().sendDeleteProffesorRequest(usrname);
        if(response.getStatus()== ResponseStatus.ERROR){
            mssg.setText(response.getErrorMessage());
            return;
        }

        OfflineMode.getInstance().setProffesorList(response.getAnswer());

        List<HashMap<String ,Object>> S=OfflineMode.getInstance().getProffesorList();

        clearTable();
        initiateProffesorListPage(S);
    }
    private void E(MenuItem source) {
        String room=list4.get(Integer.parseInt(source.getId())).getText();
        String d=list3.get(Integer.parseInt(source.getId())).getText();
        String usrname=list1.get(Integer.parseInt(source.getId())).getText().substring(1,4);

        Response response=Client.getInstanse(0).getServerController().sendEditProffesorRequest(room,d,usrname);
        if(response.getStatus()== ResponseStatus.ERROR){
            mssg.setText(response.getErrorMessage());
            return;
        }

        OfflineMode.getInstance().setProffesorList(response.getAnswer());

        List<HashMap<String ,Object>> S=OfflineMode.getInstance().getProffesorList();

        clearTable();
        initiateProffesorListPage(S);
    }
    public void apply(ActionEvent actionEvent) throws IOException, ParseException {
        Response response=Client.getInstanse(0).getServerController().sendProffesorListRequest();
        OfflineMode.getInstance().setProffesorList(response.getAnswer());

        List<HashMap<String ,Object>> C=OfflineMode.getInstance().getProffesorList();

        List<HashMap<String ,Object>> C1 = new ArrayList<>();
        List<HashMap<String ,Object>> C2 = new ArrayList<>();
        List<HashMap<String ,Object>> C3 = new ArrayList<>();

        for (HashMap<String ,Object> item : C) {
            if (txt3.getText().equals("")) {
                C1 = C;
            } else {
                if (!isNumeric(txt3.getText())) {
                    mssg.setText("faculty should be a number");
                    return;
                } else {
                    String[] s = item.get("faculty").toString().split("\\)");
                    if (s[0].substring(1, s[0].length()).equals(txt3.getText())) {
                        C1.add(item);
                    }
                }
            }
        }

        for (HashMap<String ,Object>  item : C1) {
            if (txt1.getText().equals("")) {
                C2 = C1;
            } else {
                if ((item.get("FirstName").toString() + " " + item.get("LastName").toString()).equals(txt1.getText())) {
                    C2.add(item);
                }
            }
        }

        for (HashMap<String ,Object> item : C2) {
            if (txt2.getText().equals("")) {
                C3 = C2;
            } else {
                if (item.get("professorDegree").toString().equals(txt2.getText())) {
                    C3.add(item);
                }
            }
        }

        clearTable();

        logger.info("filters applied to professors");

        initiateProffesorListPage(C3);

        logger.info("filtered professors shown successfully");
    }
    private void clearTable() {
        v1.getChildren().clear();
        v2.getChildren().clear();
        v3.getChildren().clear();
        v4.getChildren().clear();
        v5.getChildren().clear();

        list1.clear();
        list2.clear();
        list3.clear();
        list4.clear();
    }
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
    public void initiateProffesorListPage(List<HashMap<String ,Object>> S){
        for (int i = 1; i < 6; i++) {
            int cntr=-1;
            for(HashMap<String ,Object> item: S){
                cntr++;

                if(i==1){
                    TextField t = new TextField();
                    t.setText("("+item.get("username").toString()+")"+item.get("FirstName").toString()+" "+item.get("LastName").toString());
                    v1.getChildren().add(t);
                    list1.add(t);
                }
                if(i==2){
                    TextField t = new TextField();
                    t.setText(item.get("faculty").toString());
                    v2.getChildren().add(t);
                    list2.add(t);
                }
                if(i==3){
                    TextField t = new TextField();
                    t.setText(item.get("professorDegree").toString());
                    v3.getChildren().add(t);
                    list3.add(t);
                }
                if(i==4) {
                    TextField t = new TextField();
                    t.setText(item.get("roomNumber").toString());
                    v4.getChildren().add(t);
                    list4.add(t);
                }
                if(i==5) {
                    if(OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("f")){
                        if(OfflineMode.getInstance().getMainMenu().get("faculty").toString().equals(item.get("faculty"))){
                            MenuButton b=new MenuButton();

                            MenuItem m1=new MenuItem("Edit");
                            m1.setId(""+cntr);
                            m1.setOnAction(event -> E((MenuItem) event.getSource()));

                            MenuItem m2=new MenuItem("Delete");
                            m2.setId(""+cntr);
                            m2.setOnAction(event -> D((MenuItem) event.getSource()));

                            MenuItem m3=new MenuItem("Add");
                            m3.setId(""+cntr);
                            m3.setOnAction(event -> A((MenuItem) event.getSource()));

                            b.getItems().add(m1);
                            b.getItems().add(m2);
                            b.getItems().add(m3);

                            v5.getChildren().add(b);
                        }
                        else{
                            Label l=new Label();
                            l.setMinHeight(25);
                            v5.getChildren().add(l);
                        }
                    }
                    else{
                        Label l=new Label();
                        l.setMinHeight(25);
                        v5.getChildren().add(l);
                    }

                }

                logger.info("professor list information set successfully");
            }
        }
    }
}
