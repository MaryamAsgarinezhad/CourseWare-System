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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class MohseniController implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    public List<TextField> list1=new ArrayList<>();
    public List<TextField> list2=new ArrayList<>();
    public List<TextField> list3=new ArrayList<>();
    public List<TextField> list4=new ArrayList<>();

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
    TextField t1;
    @FXML
    TextField t2;
    @FXML
    TextField t3;
    @FXML
    TextField t4;
    @FXML
    TextField searchByNumber;

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
    public void Apply(ActionEvent actionEvent) {
        Response response=Client.getInstanse(0).getServerController().getMohseniStudents();
        List<HashMap<String ,Object>> C=response.getAnswer();
        List<HashMap<String ,Object>> CC=modifiedByFilters(C);

        clearTable();
        initiatePage(CC);
    }
    private List<HashMap<String, Object>> modifiedByFilters(List<HashMap<String, Object>> C) {
        List<HashMap<String, Object>> C1=new ArrayList<>();
        List<HashMap<String, Object>> C2=new ArrayList<>();
        List<HashMap<String, Object>> C3=new ArrayList<>();

        if(t1.getText().equals("")){
            C1=C;
        }
        else{
            for (HashMap<String ,Object> temp:C){
                if(temp.get("faculty").toString().substring(1,2).equals(t1.getText())){
                    C1.add(temp);
                }
            }
        }

        if(t2.getText().equals("")){
            C2=C1;
        }
        else{
            for (HashMap<String ,Object> temp:C1){
                if(temp.get("year").equals(t2.getText())){
                    C2.add(temp);
                }
            }
        }

        if(t3.getText().equals("")){
            C3=C2;
        }
        else{
            for (HashMap<String ,Object> temp:C2){
                if(temp.get("degree").equals(t3.getText())){
                    C3.add(temp);
                }
            }
        }

        sendForFilteredStudents(C3);
        return C3;
    }
    private void sendForFilteredStudents(List<HashMap<String, Object>> C) {
        if(t4.getText().equals("")){
            mssg.setText("Enter A message to send to students!");
        }
        else{
            for (HashMap<String ,Object> temp:C){
                String message=t4.getText();
                String yours=OfflineMode.getInstance().getMainMenu().get("username").toString();
                String theirs=temp.get("username").toString();

                //set Time
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH;mm;ss");
                LocalDateTime now = LocalDateTime.now();
                String lastTime=dtf.format(now);
                message+="  "+lastTime;

                Client.getInstanse(0).getServerController().sendFromMeToThem(yours,theirs,message);
            }
            mssg.setText("sent successfully!");
        }
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
    private List<HashMap<String, Object>> modifiedByStuNum(List<HashMap<String, Object>> C,String key) {
        List<HashMap<String ,Object>> ans=new ArrayList<>();

        for (HashMap<String ,Object> temp:C){
            if(temp.get("username").toString().substring(0,key.length()).equals(key)){
                ans.add(temp);
            }
        }

        return ans;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OfflineMode.getInstance().setMohseni(true);
        Response response=Client.getInstanse(0).getServerController().getMohseniStudents();
        List<HashMap<String ,Object>> C=response.getAnswer();

        initiatePage(C);
    }
    private void initiatePage(List<HashMap<String, Object>> C) {
        List<HashMap<String ,Object>> C2=new ArrayList<>(C);
        List<HashMap<String ,Object>> C3=new ArrayList<>();

        for (int i = 0; i < C.size(); i++){
            HashMap<String ,Object> v=C2.get(0);

            for(HashMap<String ,Object> item: C2){
                if(item.get("year").toString().compareTo(v.get("year").toString())<0){
                    v=item;
                }
            }

            C3.add(v);
            C2.remove(v);
        }

        for (int i = 1; i < 6; i++) {
            int cntr=-1;
            for (HashMap<String ,Object> item : C3) {
                cntr++;
                if (i == 1) {
                    TextField t = new TextField();
                    t.setText(item.get("FirstName").toString()+" "+item.get("LastName").toString());
                    v1.getChildren().add(t);
                    list1.add(t);
                }
                if (i == 2) {
                    TextField t = new TextField();
                    t.setText(item.get("username").toString());
                    v2.getChildren().add(t);
                    list2.add(t);
                }
                if (i == 3) {
                    TextField t = new TextField();
                    t.setText(item.get("year").toString());
                    v3.getChildren().add(t);
                    list3.add(t);
                }
                if (i == 4) {
                    TextField t = new TextField();
                    t.setText(item.get("faculty").toString());
                    v4.getChildren().add(t);
                    list4.add(t);
                }
                if (i == 5) {
                    Button b=new Button();
                    b.setText("View");
                    b.setId(""+cntr);
                    b.setOnAction(event -> view((Button) event.getSource()));

                    v5.getChildren().add(b);
                }
                logger.info("Mohseni list information set successfully");
            }
        }
    }
    private void view(Button source) {
        String username=list2.get(Integer.parseInt(source.getId())).getText();
        Response response=Client.getInstanse(0).getServerController().sendLoginRequest(username,"mainmenu");
        OfflineMode.getInstance().setMainMenu(response.getData());


        try {
            Stage stage = ((Stage) (((Node) (source)).getScene()).getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ProfileView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("loaded profile");
        } catch (IOException ex) {
            logger.info("couldnt go to profile");
        }
    }
    public void searchByNumberla(KeyEvent keyEvent) {
        if(searchByNumber.getText().equals("")){
            Response response=Client.getInstanse(0).getServerController().getMohseniStudents();
            List<HashMap<String ,Object>> C=response.getAnswer();

            clearTable();
            initiatePage(C);
        }
        else {
            String key=searchByNumber.getText();
            Response response=Client.getInstanse(0).getServerController().getMohseniStudents();
            List<HashMap<String ,Object>> C=response.getAnswer();
            List<HashMap<String ,Object>> C1=modifiedByStuNum(C,key);

            clearTable();
            initiatePage(C1);
        }
    }
    public void chats(ActionEvent actionEvent) {
        try {
            Stage stage = ((Stage) (((Node) (actionEvent.getSource())).getScene()).getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Chat.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Back to main menu");
        } catch (IOException ex) {
            logger.info("couldnt go Back to main menu");
        }
    }
}
