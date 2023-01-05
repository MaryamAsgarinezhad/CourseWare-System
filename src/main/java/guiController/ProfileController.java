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
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    List<TextField> tt=new ArrayList<>();
    @FXML
    Button menu;
    @FXML
    ImageView img;
    @FXML
    HBox hbox;
    @FXML
    VBox v1;
    @FXML
    VBox v2;
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
    Label l9;
    @FXML
    Label l10;
    @FXML
    Label l11;

    @FXML
    Button b1;
    @FXML
    Button b2;

    @FXML
    Label change1;
    @FXML
    Label change2;

    @FXML
    ColorPicker colorPicker;
    @FXML
    Pane pane;

    public void menu(ActionEvent e) {
        if(OfflineMode.getInstance().isOffline()){
            if(!OfflineMode.getInstance().isOfflineFromFirst()){
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
                return;
            } catch (IOException ex) {
                logger.info("couldnt go Back to main menu");
            }
        }
        if(OfflineMode.getInstance().isMohseni()){
            String username="M00";
            Response response=Client.getInstanse(0).getServerController().sendLoginRequest(username,"mainmenu");
            OfflineMode.getInstance().setMainMenu(response.getData());

            try {
                Stage stage = ((Stage) (((Node) (e.getSource())).getScene()).getWindow());
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Mohseni.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                stage.setScene(scene);
                stage.show();
                logger.info("loaded mohseni");
            } catch (IOException ex) {
                logger.info("couldnt go to mohseni");
            }
        }
        else{
            Response response = Client.getInstanse(0).getServerController().sendLoginRequest((String) OfflineMode.getInstance().getMainMenu().get("username"), "mainmenu");
            //setting offline features
            OfflineMode.getInstance().setMainMenu(response.getData());

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
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(OfflineMode.getInstance().isOffline()){
            initiateProfile2(User.getInstance(null).getJsonObject());
            return;
        }
        Response response = Client.getInstanse(0).getServerController().sendLoginRequest((String) OfflineMode.getInstance().getMainMenu().get("username"), "mainmenu");
        OfflineMode.getInstance().setMainMenu(response.getData());

        HashMap<String ,Object> j=OfflineMode.getInstance().getMainMenu();
        initiateProfile(j);
    }

    private void initiateProfile2(JSONObject j) {
        if(j.get("faculty").toString().equals("(1)mathematics")){
            try {
                img.setImage(new PersonImage().getImage("man"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                img.setImage(new PersonImage().getImage("woman"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        logger.info("getting users jsonobject to write down its info in blanks");

        Label a1=new Label();
        a1.setMinHeight(33);
        a1.setText(j.get("FirstName").toString()+" "+j.get("LastName").toString());

        Label a2=new Label();
        a2.setMinHeight(33);
        a2.setText(j.get("IDNumber").toString());

        Label a3=new Label();
        a3.setMinHeight(33);
        a3.setText(j.get("username").toString());

        Label a4=new Label();
        a4.setMinHeight(33);
        a4.setText(j.get("faculty").toString());

        Label a5=new Label();
        a5.setMinHeight(33);
        String[] s=new String[4];

        if(j.get("username").toString().substring(0,1).equals("m") || j.get("username").toString().substring(0,1).equals("b")  || j.get("username").toString().substring(0,1).equals("d")){
            s=j.get("Supervisor").toString().split("\\)");
            a5.setText(s[1]);
        }
        else{
            l5.setVisible(false);
        }

        Label a6=new Label();
        a6.setMinHeight(33);
        if(j.get("username").toString().substring(0,1).equals("m") || j.get("username").toString().substring(0,1).equals("b")  || j.get("username").toString().substring(0,1).equals("d")){
            a6.setText(j.get("year").toString());
        }
        else{
            a6.setText(j.get("roomNumber").toString());
            l6.setText("Room Number");
        }


        Label a7=new Label();
        a7.setMinHeight(33);
        if(j.get("username").toString().substring(0,1).equals("m") || j.get("username").toString().substring(0,1).equals("b")  || j.get("username").toString().substring(0,1).equals("d")){
            a7.setText(j.get("degree").toString());
        }
        else{
            a7.setText(j.get("professorDegree").toString());
        }

        Label a8=new Label();
        a8.setMinHeight(33);
        if(j.get("username").toString().substring(0,1).equals("m") || j.get("username").toString().substring(0,1).equals("b")  || j.get("username").toString().substring(0,1).equals("d")){
            a8.setText(j.get("maenScore").toString());
        }
        else{
            l8.setVisible(false);
        }

        Label a9=new Label();
        a9.setMinHeight(33);
        if(j.get("username").toString().substring(0,1).equals("m") || j.get("username").toString().substring(0,1).equals("b")  || j.get("username").toString().substring(0,1).equals("d")){
            a9.setText(j.get("educationalstatus").toString());
        }
        else{
            l9.setVisible(false);
        }

        v2.getChildren().add(a1);
        v2.getChildren().add(a2);
        v2.getChildren().add(a3);
        v2.getChildren().add(a4);
        v2.getChildren().add(a5);
        v2.getChildren().add(a6);
        v2.getChildren().add(a7);
        v2.getChildren().add(a8);
        v2.getChildren().add(a9);

        TextField t=new TextField();
        t.setMinHeight(33);
        t.setText(j.get("Email").toString());

        TextField t2=new TextField();
        t2.setMinHeight(33);
        t2.setText(j.get("phoneNumber").toString());
        v2.getChildren().add(t);
        v2.getChildren().add(t2);

        tt.add(t);
        tt.add(t2);

        b1.setVisible(false);
        b2.setVisible(false);
        logger.info("user info written successfully");
    }

    private void initiateProfile(HashMap<String, Object> j) {
        if(j.get("faculty").toString().equals("(1)mathematics")){
            try {
                img.setImage(new PersonImage().getImage("man"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                img.setImage(new PersonImage().getImage("woman"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        logger.info("getting users jsonobject to write down its info in blanks");

        Label a1=new Label();
        a1.setMinHeight(33);
        a1.setText(j.get("FirstName").toString()+" "+j.get("LastName").toString());

        Label a2=new Label();
        a2.setMinHeight(33);
        a2.setText(j.get("IDNumber").toString());

        Label a3=new Label();
        a3.setMinHeight(33);
        a3.setText(j.get("username").toString());

        Label a4=new Label();
        a4.setMinHeight(33);
        a4.setText(j.get("faculty").toString());

        Label a5=new Label();
        a5.setMinHeight(33);
        String[] s=new String[4];

        if(j.get("username").toString().substring(0,1).equals("m") || j.get("username").toString().substring(0,1).equals("b")  || j.get("username").toString().substring(0,1).equals("d")){
            s=j.get("Supervisor").toString().split("\\)");
            a5.setText(s[1]);
        }
        else{
            l5.setVisible(false);
        }

        Label a6=new Label();
        a6.setMinHeight(33);
        if(j.get("username").toString().substring(0,1).equals("m") || j.get("username").toString().substring(0,1).equals("b")  || j.get("username").toString().substring(0,1).equals("d")){
            a6.setText(j.get("year").toString());
        }
        else{
            a6.setText(j.get("roomNumber").toString());
            l6.setText("Room Number");
        }


        Label a7=new Label();
        a7.setMinHeight(33);
        if(j.get("username").toString().substring(0,1).equals("m") || j.get("username").toString().substring(0,1).equals("b")  || j.get("username").toString().substring(0,1).equals("d")){
            a7.setText(j.get("degree").toString());
        }
        else{
            a7.setText(j.get("professorDegree").toString());
        }

        Label a8=new Label();
        a8.setMinHeight(33);
        if(j.get("username").toString().substring(0,1).equals("m") || j.get("username").toString().substring(0,1).equals("b")  || j.get("username").toString().substring(0,1).equals("d")){
            a8.setText(j.get("maenScore").toString());
        }
        else{
            l8.setVisible(false);
        }

        Label a9=new Label();
        a9.setMinHeight(33);
        if(j.get("username").toString().substring(0,1).equals("m") || j.get("username").toString().substring(0,1).equals("b")  || j.get("username").toString().substring(0,1).equals("d")){
            a9.setText(j.get("educationalstatus").toString());
        }
        else{
            l9.setVisible(false);
        }

        v2.getChildren().add(a1);
        v2.getChildren().add(a2);
        v2.getChildren().add(a3);
        v2.getChildren().add(a4);
        v2.getChildren().add(a5);
        v2.getChildren().add(a6);
        v2.getChildren().add(a7);
        v2.getChildren().add(a8);
        v2.getChildren().add(a9);

        TextField t=new TextField();
        t.setMinHeight(33);
        t.setText(j.get("Email").toString());

        TextField t2=new TextField();
        t2.setMinHeight(33);
        t2.setText(j.get("phoneNumber").toString());
        v2.getChildren().add(t);
        v2.getChildren().add(t2);

        tt.add(t);
        tt.add(t2);
        logger.info("user info written successfully");
    }
    public void chngE(ActionEvent event) {
        if(tt.get(0).getText().equals("")){
            change1.setText("Fill the Email field!");
        }
        else{
            String email=tt.get(0).getText();
            boolean isprof=false;
            String s=OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1);
            if(s.equals("a") || s.equals("v") || s.equals("f")){
                isprof=true;
            }

            Response response=Client.getInstanse(0).getServerController().chngE(OfflineMode.getInstance().getMainMenu().get("username").toString(),email,isprof);

            OfflineMode.getInstance().setMainMenu(response.getData());

            HashMap<String ,Object> j=OfflineMode.getInstance().getMainMenu();

            ClearTable();
            initiateProfile(j);

            change1.setText("Email Changes Successfully!");
            logger.info("Email Changes Successfully!");
        }
    }
    private void ClearTable() {
        v2.getChildren().clear();
        v2.getChildren().clear();
        v2.getChildren().clear();
        v2.getChildren().clear();
        v2.getChildren().clear();
        v2.getChildren().clear();
        v2.getChildren().clear();
        v2.getChildren().clear();
        v2.getChildren().clear();
    }
    public void chngP(ActionEvent event) {
        if(tt.get(1).getText().equals("")){
            change1.setText("Fill the phoneNumber field!");
        }
        else{
            String phone=tt.get(1).getText();
            boolean isprof=false;
            String s=OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1);
            if(s.equals("a") || s.equals("v") || s.equals("f")){
                isprof=true;
            }

            Response response=Client.getInstanse(0).getServerController().chngP(OfflineMode.getInstance().getMainMenu().get("username").toString(),phone,isprof);

            OfflineMode.getInstance().setMainMenu(response.getData());

            HashMap<String ,Object> j=OfflineMode.getInstance().getMainMenu();

            ClearTable();
            initiateProfile(j);

            change2.setText("phoneNumber Changed Successfully!");
            logger.info("phoneNumber Changed Successfully!");
        }
    }
    public void color(ActionEvent event) {
        Color color=colorPicker.getValue();
        pane.setBackground(new Background(new BackgroundFill(color,null,null)));
    }

}
