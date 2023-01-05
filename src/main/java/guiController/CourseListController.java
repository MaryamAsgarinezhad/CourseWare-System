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

public class CourseListController implements Initializable {

    static Logger logger = Logger.getLogger(loginController.class);

    List<TextField> names = new ArrayList<>();
    List<TextField> units = new ArrayList<>();
    List<TextField> numbers = new ArrayList<>();
    List<TextField> faculties = new ArrayList<>();
    List<TextField> professors = new ArrayList<>();
    List<TextField> degrees = new ArrayList<>();
    List<TextField> dates = new ArrayList<>();

    List<TextField> groups = new ArrayList<>();

    @FXML
    HBox hbox2;

    @FXML
    VBox vbox1;

    @FXML
    TextField info1;
    @FXML
    TextField info2;
    @FXML
    TextField info3;
    @FXML
    TextField info4;
    @FXML
    TextField info5;
    @FXML
    Button menu;
    @FXML
    Button btn;
    @FXML
    Label filterLabel;
    @FXML
    TextField txt1;
    @FXML
    TextField txt2;
    @FXML
    TextField txt3;
    @FXML
    HBox hbox;
    @FXML
    VBox name;
    @FXML
    VBox group;
    @FXML
    VBox unit;
    @FXML
    VBox number;
    @FXML
    VBox faculty;
    @FXML
    VBox proffessor;
    @FXML
    VBox Date;
    @FXML
    VBox Degree;
    @FXML
    VBox edit;

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
    Label l6;
    @FXML
    Label l7;
    @FXML
    Label l8;
    @FXML
    Label mssg;
    @FXML
    public void E(MenuItem source){
        String name=names.get(Integer.parseInt(source.getId())).getText().substring(5);
        String unit=units.get(Integer.parseInt(source.getId())).getText();
        String prof=professors.get(Integer.parseInt(source.getId())).getText();
        String number=numbers.get(Integer.parseInt(source.getId())).getText();
        String degree=degrees.get(Integer.parseInt(source.getId())).getText();
        String fac=faculties.get(Integer.parseInt(source.getId())).getText();
        String date=dates.get(Integer.parseInt(source.getId())).getText();
        String group=groups.get(Integer.parseInt(source.getId())).getText();


        Response response = Client.getInstanse(0).getServerController().sendEditCourseRequest(name,fac,number,unit,prof,degree,date,group);
        OfflineMode.getInstance().setCourseList(response.getAnswer());

        List<HashMap<String ,Object>> S=OfflineMode.getInstance().getCourseList();

        clearTable();
        initiatePage(S);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Response response = Client.getInstanse(0).getServerController().sendCourseListRequest();
        OfflineMode.getInstance().setCourseList(response.getAnswer());

        List<HashMap<String ,Object>> S=OfflineMode.getInstance().getCourseList();

        logger.info("initializing student course list started");

        initiatePage(S);
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
            ex.printStackTrace();
            logger.info("couldnt go Back to main menu");
        }
    }
    private void A(MenuItem source) {
        if(info1.getText().equals("") || info2.getText().equals("") ||info3.getText().equals("") ||info4.getText().equals("") ||info5.getText().equals("")){
            mssg.setText("fill all additional info!");
            return;
        }
        String name=names.get(Integer.parseInt(source.getId())).getText().substring(5);
        String unit=units.get(Integer.parseInt(source.getId())).getText();
        String prof=professors.get(Integer.parseInt(source.getId())).getText();
        String number=numbers.get(Integer.parseInt(source.getId())).getText();
        String degree=degrees.get(Integer.parseInt(source.getId())).getText();
        String fac=faculties.get(Integer.parseInt(source.getId())).getText();
        String date=dates.get(Integer.parseInt(source.getId())).getText();

        int capacity=Integer.parseInt(info4.getText());
        String ta= info1.getText();
        String pre= info2.getText();
        String co=info3.getText();
        String classDate=info5.getText();

        Response response = Client.getInstanse(0).getServerController().sendAddCourseRequest(name,fac,number,unit,prof,degree,date,capacity,ta,pre,co,classDate);
        OfflineMode.getInstance().setCourseList(response.getAnswer());

        List<HashMap<String ,Object>> S=OfflineMode.getInstance().getCourseList();

        clearTable();
        initiatePage(S);
    }
    private void D(MenuItem source){
        String number=numbers.get(Integer.parseInt(source.getId())).getText();
        String group=groups.get(Integer.parseInt(source.getId())).getText();

        Response response = Client.getInstanse(0).getServerController().sendDeleteCourseRequest(number,group);
        OfflineMode.getInstance().setCourseList(response.getAnswer());

        List<HashMap<String ,Object>> S=OfflineMode.getInstance().getCourseList();

        clearTable();
        initiatePage(S);
    }

    public void apply(ActionEvent event) throws IOException, ParseException {
        Response response = Client.getInstanse(0).getServerController().sendCourseListRequest();

        OfflineMode.getInstance().setCourseList(response.getAnswer());

        List<HashMap<String ,Object>> C=OfflineMode.getInstance().getCourseList();

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
                    if (s[0].substring(1).equals(txt3.getText())) {
                        C1.add(item);
                    }
                }
            }
        }

        for (HashMap<String ,Object> item : C1) {
            if (txt1.getText().equals("")) {
                C2 = C1;
            } else {
                String[] s = item.get("professor").toString().split("\\)");
                if (s[0].substring(1).equals(txt1.getText())) {
                    C2.add(item);
                }
            }
        }

        for (HashMap<String ,Object> item : C2) {
            if (txt2.getText().equals("")) {
                C3 = C2;
            } else {
                if (!isNumeric(txt2.getText())) {
                    mssg.setText("unit should be a number");
                    return;
                } else {
                    if (item.get("unit").toString().equals(txt2.getText())) {
                        C3.add(item);
                    }
                }
            }
        }

        clearTable();

        logger.info("the filtered course list created");

        initiatePage(C3);
    }
    private void clearTable() {
        name.getChildren().clear();
        unit.getChildren().clear();
        number.getChildren().clear();
        faculty.getChildren().clear();
        Degree.getChildren().clear();
        Date.getChildren().clear();
        proffessor.getChildren().clear();
        edit.getChildren().clear();
        group.getChildren().clear();

        groups.clear();
        names.clear();
        units.clear();
        numbers.clear();
        faculties.clear();
        professors.clear();
        degrees.clear();
        dates.clear();
    }
    Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    public boolean isNumeric(String strNum){
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
    public void initiatePage(List<HashMap<String ,Object>> S){
        vbox1.setVisible(false);
        for (int i = 1; i < 10; i++) {
            int cntr=-1;
            for (HashMap<String ,Object> item : S) {
                cntr++;
                if (i == 1) {
                    TextField t = new TextField();
                    t.setText("(c"+item.get("number")+")"+item.get("name").toString());
                    name.getChildren().add(t);
                    names.add(t);
                }
                if (i == 2) {
                    TextField t = new TextField();
                    t.setText(String.format("%s%n", item.get("unit").toString()));
                    unit.getChildren().add(t);
                    units.add(t);
                }
                if (i == 3) {
                    TextField t = new TextField();
                    t.setText(String.format("%s%n", item.get("number").toString()));
                    number.getChildren().add(t);
                    numbers.add(t);
                }
                if (i == 4) {
                    TextField t = new TextField();
                    t.setText(String.format("%s%n", item.get("faculty").toString()));
                    faculty.getChildren().add(t);
                    faculties.add(t);
                }
                if (i == 5) {
                    TextField t = new TextField();
                    t.setText(String.format("%s%n", item.get("professor").toString()));
                    proffessor.getChildren().add(t);
                    professors.add(t);
                }
                if (i == 6) {
                    TextField t = new TextField();
                    t.setText(String.format("%s%n", item.get("degree").toString()));
                    Degree.getChildren().add(t);
                    degrees.add(t);
                }
                if (i == 7) {
                    TextField t = new TextField();
                    t.setText(String.format("%s%n", item.get("dateOfExam").toString()));
                    Date.getChildren().add(t);
                    dates.add(t);
                }
                if (i == 8) {
                    if(OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("v")){
                        vbox1.setVisible(true);
                        if(OfflineMode.getInstance().getMainMenu().get("faculty").toString().equals(item.get("faculty"))){
                            System.out.println(item.get("faculty"));
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

                            edit.getChildren().add(b);
                        }
                        else{
                            Label l=new Label();
                            l.setMinHeight(25);
                            edit.getChildren().add(l);
                        }
                    }
                    else{
                        Label l=new Label();
                        l.setMinHeight(25);
                        edit.getChildren().add(l);
                    }
                }

                if (i == 9) {
                    TextField t = new TextField();
                    t.setText(String.format("%s%n", item.get("group").toString()));
                    group.getChildren().add(t);
                    groups.add(t);
                }
                logger.info("course list information set successfully");
            }
        }
    }
}