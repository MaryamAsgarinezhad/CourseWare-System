package guiController;

import Logic.OfflineMode;
import client.Client;
import client.util.FileConverter;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;

public class NewContentController implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    List<Node> nodes=new ArrayList<>();
    private String firstOfUername;
    private int numberOfContents=0;
    @FXML
    VBox vbox;
    @FXML
    VBox buttonBox;
    @FXML
    VBox itemsBox;
    @FXML
    Button courseware;
    @FXML
    Label mssg;
    @FXML
    TextField t1;
    @FXML
    TextArea t2;
    @FXML
    MenuButton apearWhileCreatin;
    @FXML
    Button deleteContent;
    public void postText(ActionEvent actionEvent) {
        if(t2.getText().equals("")){
            mssg.setText("write something");
        }
        else{
            String media=t2.getText();
            String type="content";
            int num=OfflineMode.getInstance().getCurrentCourseNum();
            int gp=OfflineMode.getInstance().getCurrentCourseGroup();
            String contentName=OfflineMode.getInstance().getCurrentContent();

            Response response=Client.getInstanse(0).getServerController().setContent(type,num,gp,contentName,media);
            mssg.setText("done!");
        }

        if(numberOfContents<5){
            if(deleteContent.isVisible()){
                vbox.setVisible(false);
                String type=OfflineMode.getInstance().getCurrenttype();
                int courseNum=OfflineMode.getInstance().getCurrentCourseNum();
                int courseGroup=OfflineMode.getInstance().getCurrentCourseGroup();
                String contentName=OfflineMode.getInstance().getCurrentContent();

                Response response= Client.getInstanse(0).getServerController().getContent(type,courseNum,courseGroup,contentName);
                HashMap<String ,Object> C=response.getData();
                clearTable();

                initiatePage(C);
            }
            else {
                apearWhileCreatin.setVisible(true);
                vbox.setVisible(false);
            }
        }
        else {
            finall();
        }
    }
    private void finall() {
        String type="content";
        int num=OfflineMode.getInstance().getCurrentCourseNum();
        int gp=OfflineMode.getInstance().getCurrentCourseGroup();
        String contentName=OfflineMode.getInstance().getCurrentContent();

        Client.getInstanse(0).getServerController().finalContent(type,num,gp,contentName);

        //initialize
        setInvisible();
        deleteContent.setVisible(true);
        Response response= Client.getInstanse(0).getServerController().getContent(type,num,gp,contentName);
        HashMap<String ,Object> C=response.getData();

        initiatePage(C);
    }
    public void postMedia(ActionEvent actionEvent) {
        if(t1.getText().equals("")){
            mssg.setText("write url");
        }
        else{
            String path=t1.getText();
            String[] s=path.split("\\.");
            String fileType=s[s.length-1];
            String messg="TypeOfFile->"+fileType+"::::"+ FileConverter.encode(path);

            String type="content";
            int num=OfflineMode.getInstance().getCurrentCourseNum();
            int gp=OfflineMode.getInstance().getCurrentCourseGroup();
            String contentName=OfflineMode.getInstance().getCurrentContent();

            Response response=Client.getInstanse(0).getServerController().setContent(type,num,gp,contentName,messg);
            mssg.setText("done!");
        }

        if(numberOfContents<5){
            if(deleteContent.isVisible()){
                vbox.setVisible(false);
                String type=OfflineMode.getInstance().getCurrenttype();
                int courseNum=OfflineMode.getInstance().getCurrentCourseNum();
                int courseGroup=OfflineMode.getInstance().getCurrentCourseGroup();
                String contentName=OfflineMode.getInstance().getCurrentContent();

                Response response= Client.getInstanse(0).getServerController().getContent(type,courseNum,courseGroup,contentName);
                HashMap<String ,Object> C=response.getData();
                clearTable();
                initiatePage(C);
            }
            else {
                apearWhileCreatin.setVisible(true);
                vbox.setVisible(false);
            }
        }
        else {
            finall();
        }
    }
    public void deleteContent(ActionEvent actionEvent) {
        String type="content";
        int courseNum=OfflineMode.getInstance().getCurrentCourseNum();
        int courseGroup=OfflineMode.getInstance().getCurrentCourseGroup();
        String contentName=OfflineMode.getInstance().getCurrentContent();

        Client.getInstanse(0).getServerController().deleteContent(type,courseNum,courseGroup,contentName);
    }
    public void courseware(ActionEvent actionEvent) {
        if(!OfflineMode.getInstance().isOffline()){
            try {
                Stage stage = ((Stage) (((Node) (actionEvent.getSource())).getScene()).getWindow());
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CoursePage.fxml"));
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
                logger.info("Back to main menu");
            } catch (IOException ex) {
                logger.info("couldnt go Back to main menu");
            }
        }
    }
    public void newItem(ActionEvent actionEvent) {
        apearWhileCreatin.setVisible(false);
        vbox.setVisible(true);
    }
    public void registerContent(ActionEvent actionEvent) {
        finall();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInvisible();
        String username=OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1);
        firstOfUername=username;

        if(!OfflineMode.getInstance().isNewingContent()){
            if(username.equals("a") || username.equals("f") || username.equals("v")){
                deleteContent.setVisible(true);
            }
            String type=OfflineMode.getInstance().getCurrenttype();
            int courseNum=OfflineMode.getInstance().getCurrentCourseNum();
            int courseGroup=OfflineMode.getInstance().getCurrentCourseGroup();
            String contentName=OfflineMode.getInstance().getCurrentContent();

            Response response= Client.getInstanse(0).getServerController().getContent(type,courseNum,courseGroup,contentName);
            HashMap<String ,Object> C=response.getData();

            initiatePage(C);
        }
        else{
            vbox.setVisible(true);
        }
    }
    private void initiatePage(HashMap<String, Object> C) {
        numberOfContents=0;
        for (int i = 0; i < 2; i++) {
            int cntr=-1;
            for (Map.Entry<String ,Object> temp1:C.entrySet()){
                if(temp1.getKey().equals("1") || temp1.getKey().equals("start") || temp1.getKey().equals("end")){
                    continue;
                }
                cntr++;
                Object temp=temp1.getValue();
                if(i==0){
                    String item=(String) temp;
                    if(item.length()>14){
                        if(item.substring(0,12).equals("TypeOfFile->")){
                            numberOfContents++;

                            String[] t=item.split("::::");
                            String stringOfFile=t[1];
                            String[] tt=t[0].split(">");
                            String typeOfFile=tt[1];

                            Button button=new Button();
                            button.setMinWidth(400);
                            button.setText("download "+typeOfFile);
                            button.setId(stringOfFile);
                            button.setOnAction(event -> Download((Button) event.getSource()));

                            itemsBox.getChildren().add(button);
                            continue;
                        }
                    }
                    numberOfContents++;

                    TextArea t=new TextArea();
                    t.setMaxHeight(85);
                    t.setText(item);
                    itemsBox.getChildren().add(t);
                    nodes.add(t);
                }
                if(i==1){
                    if(isStudentOfCourse()){
                        continue;
                    }
                    else{
                        MenuButton menuButton=new MenuButton();

                        MenuItem m1=new MenuItem();
                        m1.setText("Modify");
                        m1.setId(""+cntr);
                        m1.setOnAction(event -> modify((MenuItem) event.getSource()));

                        MenuItem m2=new MenuItem();
                        m2.setText("Add");
                        m2.setId(""+cntr);
                        m2.setOnAction(event -> Add((MenuItem) event.getSource()));

                        MenuItem m3=new MenuItem();
                        m3.setText("delete");
                        m3.setId(""+cntr);
                        m3.setOnAction(event -> Delete((MenuItem) event.getSource()));

                        if(firstOfUername.equals("a") || firstOfUername.equals("v") || firstOfUername.equals("f")){
                            menuButton.getItems().add(m1);
                            menuButton.getItems().add(m2);
                            menuButton.getItems().add(m3);

                        }
                        else{
                            menuButton.getItems().add(m1);
                            menuButton.getItems().add(m2);
                        }
                        buttonBox.getChildren().add(menuButton);
                    }
                }
            }

        }
    }
    private boolean isStudentOfCourse() {
        int num=OfflineMode.getInstance().getCurrentCourseNum();
        int gp=OfflineMode.getInstance().getCurrentCourseGroup();
        Response response=Client.getInstanse(0).getServerController().getTaaOfCourse(num,gp);

        for (Object temp:response.getData().values()){
            if(OfflineMode.getInstance().getMainMenu().get("username").toString().equals((String)temp)){
                return false;
            }
        }

        if(firstOfUername.equals("b") || firstOfUername.equals("d") || firstOfUername.equals("m")){
            return true;
        }
        else{
            return false;
        }
    }
    private void Delete(MenuItem source) {
        int courseNum=OfflineMode.getInstance().getCurrentCourseNum();
        int courseGroup=OfflineMode.getInstance().getCurrentCourseGroup();
        String contentName=OfflineMode.getInstance().getCurrentContent();
        int removableNumber=Integer.parseInt(source.getId())+1;

        Response response=Client.getInstanse(0).getServerController().deleteItem(courseNum,courseGroup,contentName,removableNumber);

        clearTable();
        initiatePage(response.getData());
    }
    private void clearTable() {
        itemsBox.getChildren().clear();
        buttonBox.getChildren().clear();
        nodes.clear();
    }
    private void Add(MenuItem source) {
        if(numberOfContents<5){
            vbox.setVisible(true);
        }
        else{
            mssg.setText("item capacity is full!");
        }
    }
    private void modify(MenuItem source) {
        Node node=nodes.get(Integer.parseInt(source.getId()));
        if(!(node instanceof TextArea)){
            mssg.setText("Cant modify a file,delete it and add again!");
            return;
        }
        else{
            int courseNum=OfflineMode.getInstance().getCurrentCourseNum();
            int courseGroup=OfflineMode.getInstance().getCurrentCourseGroup();
            String contentName=OfflineMode.getInstance().getCurrentContent();
            int removableNumber=Integer.parseInt(source.getId())+1;

            Client.getInstanse(0).getServerController().deleteItem(courseNum,courseGroup,contentName,removableNumber);
            Response response=Client.getInstanse(0).getServerController().setContent("content",courseNum,courseGroup,contentName,((TextArea) node).getText());

            clearTable();
            initiatePage(response.getData());
        }
    }
    private void Download(Button source) {
        String[] s=source.getText().split(" ");
        String typeOfFile=s[1];
        String stringOfFile=source.getId();
        byte[] bytesOfFile= FileConverter.decode(stringOfFile);

        downloadToSpecificPath(typeOfFile,bytesOfFile);
    }
    private void downloadToSpecificPath(String typeOfFile, byte[] bytesOfFile) {
        switch (typeOfFile) {
            case "jpg" -> {
                try {
                    OutputStream downloads=new FileOutputStream("ax.jpg");
                    downloads.write(bytesOfFile);
                    downloads.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            case "png" -> {
                try {
                    OutputStream downloads=new FileOutputStream("solier.png");
                    downloads.write(bytesOfFile);
                    downloads.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            case "pdf" -> {
                try {
                    OutputStream downloads=new FileOutputStream("network.pdf");
                    downloads.write(bytesOfFile);
                    downloads.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            case "mp4" -> {
                try {
                    OutputStream downloads=new FileOutputStream("film.mp4");
                    downloads.write(bytesOfFile);
                    downloads.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            case "ogg" -> {
                try {
                    OutputStream downloads=new FileOutputStream("voice.ogg");
                    downloads.write(bytesOfFile);
                    downloads.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            default -> {
            }
        }
    }
    private void setInvisible() {
        vbox.setVisible(false);
        apearWhileCreatin.setVisible(false);
        deleteContent.setVisible(false);
    }
}
