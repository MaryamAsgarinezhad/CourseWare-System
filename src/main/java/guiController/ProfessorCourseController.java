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

public class ProfessorCourseController implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    List<TextField> list1 = new ArrayList<>();
    List<TextField> list2 = new ArrayList<>();
    List<TextField> list10 = new ArrayList<>();
    List<TextField> list3 = new ArrayList<>();
    List<TextField> list4 = new ArrayList<>();
    List<TextField> list5 = new ArrayList<>();
    List<JSONObject> list6 = new ArrayList<>();
    List<String> list7 = new ArrayList<>();
    List<TextField> list8 = new ArrayList<>();

    List<Integer> listttt = new ArrayList<>();


    @FXML
    Button menu;
    @FXML
    Button apply;
    @FXML
    Label mssg;
    @FXML
    HBox hbox;
    @FXML
    HBox hbox2;
    @FXML
    HBox hbox3;

    @FXML
    TextField tt;
    @FXML
    Button bb;
    @FXML
    Label ll1;
    @FXML
    Label ll2;
    @FXML
    Label ll3;
    @FXML
    Label ll4;
    @FXML
    VBox vl;
    @FXML
    VBox vr;
    @FXML
    VBox v1;
    @FXML
    VBox v3;
    @FXML
    VBox v4;
    @FXML
    VBox v5;
    @FXML
    VBox v6;
    @FXML
    VBox v10;
    @FXML
    VBox vbox;

    @FXML
    Label gist;
    @FXML
    VBox v2;
    @FXML
    Label fiterLabel;
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
    Label l10;
    @FXML
    Label lu1;
    @FXML
    Label lu2;
    @FXML
    Label ld1;
    @FXML
    Label ld2;

    @FXML
    TextField t1;
    @FXML
    TextField t2;

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
        Response response = Client.getInstanse(0).getServerController().sendProffessorCourseRequest(OfflineMode.getInstance().getMainMenu().get("username").toString());
        OfflineMode.getInstance().setProffessorCourse(response.getAnswer());

        List<HashMap<String ,Object>> S=OfflineMode.getInstance().getProffessorCourse();

        initiateProfessorCoursePage(S);
    }
    private void finall(MenuItem source) throws IOException, ParseException {
        for (int i:listttt){
            System.out.println(list3.get(i).getText());
            System.out.println(i);
            if(!list3.get(i).getText().contains("t") && !list3.get(i).getText().contains("f")){
                mssg.setText("make a temporary grade for all students before finalling grades!");
                return;
            }
        }

        String mark="";
        String[] s=list3.get(Integer.parseInt(source.getId())).getText().split("\\(");
        mark=s[0];

        if(mark.equals("")){
            mssg.setText("Enter grade");
            return;
        }
        if(s[1].equals("f)")){
            mssg.setText("Cant modify finaled score");
            return;
        }

        String header= list10.get(Integer.parseInt(source.getId())).getText().substring(1,3)+"&&"+list2.get(Integer.parseInt(source.getId())).getText().substring(1,4)+"&&"+list1.get(Integer.parseInt(source.getId())).getText().substring(1,4);

        String finalState="f";
        String Object;
        if(list4.get(Integer.parseInt(source.getId())).getText().equals("")){
            Object=" ";
        }
        else{
            Object=list4.get(Integer.parseInt(source.getId())).getText();
        }

        String profResponse;
        if(list5.get(Integer.parseInt(source.getId())).getText().equals("")){
            profResponse=" ";
        }
        else{
            profResponse=list5.get(Integer.parseInt(source.getId())).getText();
        }

        Response response = Client.getInstanse(0).getServerController().sendFinalProffessorCourseRequest(OfflineMode.getInstance().getMainMenu().get("username").toString(),header,mark,finalState,Object,profResponse);

        if(response.getStatus()== ResponseStatus.ERROR){
            mssg.setText(response.getErrorMessage());
            return;
        }
        OfflineMode.getInstance().setProffessorCourse(response.getAnswer());

        List<HashMap<String ,Object>> S=OfflineMode.getInstance().getProffessorCourse();

        clearTable();
        initiateProfessorCoursePage(S);
    }
    private void clearTable() {
        listttt.clear();
        list1.clear();
        list2.clear();
        list3.clear();
        list4.clear();
        list5.clear();
        list6.clear();
        list7.clear();
        list8.clear();
        list10.clear();

        v1.getChildren().clear();
        v2.getChildren().clear();
        v3.getChildren().clear();
        v4.getChildren().clear();
        v5.getChildren().clear();
        v1.getChildren().clear();
        v6.getChildren().clear();
        v10.getChildren().clear();
    }
    private void temp(MenuItem source) {
        String mark="";
        String[] s=list3.get(Integer.parseInt(source.getId())).getText().split("\\(");
        if(s[1].equals("f)")){
            mssg.setText("Cant modify finaled score");
            return;
        }
        mark=s[0];

        if(mark.equals("")){
            mssg.setText("Enter grade");
            return;
        }

        String header= list10.get(Integer.parseInt(source.getId())).getText().substring(1,3)+"&&"+list2.get(Integer.parseInt(source.getId())).getText().substring(1,4)+"&&"+list1.get(Integer.parseInt(source.getId())).getText().substring(1,4);

        String finalState="t";
        String Object;
        if(list4.get(Integer.parseInt(source.getId())).getText().equals("")){
            Object=" ";
        }
        else{
            Object=list4.get(Integer.parseInt(source.getId())).getText();
        }

        String profResponse;
        if(list5.get(Integer.parseInt(source.getId())).getText().equals("")){
            profResponse=" ";
        }
        else{
            profResponse=list5.get(Integer.parseInt(source.getId())).getText();
        }


        Response response = Client.getInstanse(0).getServerController().sendFinalProffessorCourseRequest(OfflineMode.getInstance().getMainMenu().get("username").toString(),header,mark,finalState,Object,profResponse);

        if(response.getStatus()== ResponseStatus.ERROR){
            mssg.setText(response.getErrorMessage());
            return;
        }

        OfflineMode.getInstance().setProffessorCourse(response.getAnswer());

        List<HashMap<String ,Object>> S=OfflineMode.getInstance().getProffessorCourse();

        clearTable();
        initiateProfessorCoursePage(S);
    }
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
    public void apply(ActionEvent event) {
        Response response = Client.getInstanse(0).getServerController().sendProffessorCourseRequest(OfflineMode.getInstance().getMainMenu().get("username").toString());
        OfflineMode.getInstance().setProffessorCourse(response.getAnswer());

        List<HashMap<String ,Object>> S=OfflineMode.getInstance().getProffessorCourse();
        List<HashMap<String ,Object>> C=new ArrayList<>();
        List<HashMap<String ,Object>> C2=new ArrayList<>();

        if(t1.getText().equals("")){
            C=S;
        }
        else{
            for (HashMap<String ,Object> temp:S){
                String s=temp.get("grade").toString().substring(temp.get("grade").toString().length()-2,temp.get("grade").toString().length()-1);
                if(temp.get("StudentName").toString().equals(t1.getText()) && s.equals("t")){
                    C.add(temp);
                }
            }
        }

        if(t2.getText().equals("")){
            C2=C;
        }
        else{
            for (HashMap<String ,Object> temp:C){
                String s=temp.get("grade").toString().substring(temp.get("grade").toString().length()-2,temp.get("grade").toString().length()-1);
                if(temp.get("ProffessorName").toString().equals(t2.getText()) && s.equals("f")){
                    C2.add(temp);
                }
            }
        }

        clearTable();
        initiateProfessorCoursePage(C2);
    }

    public void enter(ActionEvent event) {
        list8.clear();
        logger.info("starts calculating parameters");
        if(!tt.getText().equals("")){
            List<Double> fail=new ArrayList<>();

            for (TextField s:list10){
                if(s.getText().equals(tt.getText())){
                    int index=list10.indexOf(s);
                    list8.add(list3.get(index));
                }
            }

            for (TextField temp:list8) {
                if(!temp.getText().equals("")){
                    if(temp.getText().substring(temp.getText().length()-2,temp.getText().length()-1).equals("f") || temp.getText().substring(temp.getText().length()-2,temp.getText().length()-1).equals("t")){
                        String[] s=temp.getText().split("\\(");
                        fail.add(Double.parseDouble(s[0]));
                    }
                }
            }

            Double j=0.0;
            Double k=0.0;

            Double f1=0.0;
            Double f2=0.0;
            for (Double i:fail){
                if(i<10){
                    f1+=i;
                    j++;
                }
                else{
                    f2+=i;
                    k++;
                }
            }

            ll1.setText(j+" people failed.");
            ll2.setText(k+" people not failed.");
            ll3.setText("mean not failed: "+f2/k);
            ll4.setText("mean: "+(f1+f2)/(k+j));
        }

        logger.info("parameters shown");
    }
    private void initiateProfessorCoursePage(List<HashMap<String ,Object>> S) {
        for (int i = 1; i < 8; i++)  {
            int cntr=-1;
            for (HashMap<String ,Object> temp:S){
                cntr++;
                if (i == 1) {
                    TextField t = new TextField();
                    t.setText(temp.get("StudentName").toString());
                    v1.getChildren().add(t);
                    list1.add(t);
                }
                if (i == 2) {
                    TextField t = new TextField();
                    t.setText(temp.get("ProffessorName").toString());
                    v2.getChildren().add(t);
                    list2.add(t);
                }
                if (i == 3) {
                    TextField t = new TextField();
                    t.setText(temp.get("CourseName").toString());
                    v10.getChildren().add(t);
                    list10.add(t);
                }
                if (i == 4) {
                    TextField t = new TextField();
                    t.setText(temp.get("grade").toString());

                    if(OfflineMode.getInstance().getMainMenu().get("username").toString().equals(list2.get(cntr).getText().substring(1,4))){
                        listttt.add(cntr);
                    }
                    v3.getChildren().add(t);
                    list3.add(t);
                }
                if (i == 5) {
                    TextField t = new TextField();
                    t.setText(temp.get("StudentObject").toString());

                    v4.getChildren().add(t);
                    list4.add(t);

                }
                if (i == 6) {
                    TextField t = new TextField();
                    t.setText(temp.get("ProffessorResponse").toString());
                    v5.getChildren().add(t);
                    list5.add(t);
                }
                if (i == 7) {
                    if(listttt.contains(cntr)){
                        MenuButton b = new MenuButton();

                        MenuItem m1 = new MenuItem("Final");
                        m1.setId("" + cntr);
                        m1.setOnAction(event -> {
                            try {
                                finall((MenuItem) event.getSource());
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        });

                        MenuItem m2 = new MenuItem("Temporary");
                        m2.setId("" + cntr);
                        m2.setOnAction(event -> temp((MenuItem) event.getSource()));

                        b.getItems().add(m1);
                        b.getItems().add(m2);

                        v6.getChildren().add(b);
                    }
                    else{
                        Label l=new Label();
                        l.setMinHeight(25);
                        v6.getChildren().add(l);
                    }
                }
            }
        }
        if(!OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("v")){
            hbox3.setVisible(false);
            vbox.setVisible(false);
        }
    }
}
