package guiController;

import Logic.OfflineMode;
import client.Client;
import client.util.FileConverter;
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
import javafx.scene.layout.HBox;
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

public class NewHomeworkController implements Initializable {
    //    D://maryam//phase2-files
    private String firstOfUername;
    private String nameOfContent;
    private TextArea upleadingFile;
    static Logger logger = Logger.getLogger(loginController.class);
    List<Label> names=new ArrayList<>();
    List<TextField> marks=new ArrayList<>();

    ObservableList<String> observableList= FXCollections.observableArrayList("Media","Text");
    @FXML
    VBox operation;
    @FXML
    TextField release;
    @FXML
    TextField deadline;
    @FXML
    TextField allowable;
    @FXML
    TextField name;
    @FXML
    TextField explanation;
    @FXML
    TextField address;
    @FXML
    ChoiceBox type;
    @FXML
    VBox vbox;
    @FXML
    VBox StuName;
    @FXML
    VBox file;
    @FXML
    VBox mark;
    @FXML
    HBox hbox;
    @FXML
    HBox profHbox;
    @FXML
    Label mssg;
    @FXML
    VBox v2;
    @FXML
    Label grade;
    @FXML
    Button uploaddd;

    public void register(ActionEvent actionEvent) {
        if(release.getText().equals("") || name.getText().equals("") || deadline.getText().equals("") || allowable.getText().equals("") ||
                explanation.getText().equals("") || address.getText().equals("") || type.getValue()==null ){
            mssg.setText("Fill all fields!");
        }
        else{
            nameOfContent=name.getText();
            List<String > items=new ArrayList<>();
            items.add(type.getValue().toString());

            String path=address.getText();
            String[] s=path.split("\\.");
            String fileType=s[s.length-1];
            String messg="TypeOfFile->"+fileType+"::::"+ FileConverter.encode(path);
            items.add(messg);

            items.add(explanation.getText());
            items.add(allowable.getText());
            items.add(deadline.getText());
            items.add(release.getText());

            for (String temp:items){
                String media=temp;
                String type="homework";
                int num=OfflineMode.getInstance().getCurrentCourseNum();
                int gp=OfflineMode.getInstance().getCurrentCourseGroup();
                String contentName=name.getText();

                Response response=Client.getInstanse(0).getServerController().setContent(type,num,gp,contentName,media);
                mssg.setText("done!");
            }

            String type="homework";
            int num=OfflineMode.getInstance().getCurrentCourseNum();
            int gp=OfflineMode.getInstance().getCurrentCourseGroup();
            String contentName=name.getText();

            Client.getInstanse(0).getServerController().finalContent(type,num,gp,contentName);
        }

        OfflineMode.getInstance().setNewingContent(false);
        String type="homework";
        int courseNum=OfflineMode.getInstance().getCurrentCourseNum();
        int courseGroup=OfflineMode.getInstance().getCurrentCourseGroup();
        String contentName=name.getText();

        Response response= Client.getInstanse(0).getServerController().getContent(type,courseNum,courseGroup,contentName);
        HashMap<String ,Object> C=response.getData();

        if (!isStudentOfCourse()){
            profHbox.setVisible(true);
        }
        hbox.setVisible(true);
        vbox.setVisible(false);
        initiatePage(C);
    }
    public void coursePage(ActionEvent actionEvent) {
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        type.setItems(observableList);
        setInvisible();
        String username=OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1);
        firstOfUername=username;

        if(!OfflineMode.getInstance().isNewingContent()){
            hbox.setVisible(true);
            if(!isStudentOfCourse()){
                profHbox.setVisible(true);
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
        int courseNum=OfflineMode.getInstance().getCurrentCourseNum();
        int courseGroup=OfflineMode.getInstance().getCurrentCourseGroup();

        int i=0;
        for (Map.Entry<String ,Object> temp:C.entrySet()){
            i++;
            if(i==8){
                break;
            }
            if(i==5){
                TextArea t=new TextArea();
                t.setPrefHeight(86);
                t.setText((String) temp.getValue());
                v2.getChildren().add(t);
            }
            else{
                String item=(String) temp.getValue();
                if(i==7){
                    nameOfContent=((Label) v2.getChildren().get(0)).getText();

                    if (firstOfUername.equals("a") || firstOfUername.equals("v") || firstOfUername.equals("f") || isTa(courseNum,courseGroup)){
                        grade.setVisible(false);
                        uploaddd.setVisible(false);
                        continue;
                    }
                    HashMap<String,Object> ans=findStudentUplodedFile(OfflineMode.getInstance().getMainMenu().get("username"),courseNum,courseGroup,nameOfContent);

                    String itemm="";
                    if(ans.size()==0){
                        itemm=null;
                    }
                    else{
                        itemm=ans.get("0").toString();
                    }

                    if(itemm!=null){
                        if(item.equals("Text")){
                            TextArea t=new TextArea();
                            t.setMaxHeight(40);
                            t.setText(itemm);
                            v2.getChildren().add(t);
                        }
                        if(item.equals("Media")){
                            String[] t=itemm.split("::::");
                            String stringOfFile=t[1];
                            String[] tt=t[0].split(">");
                            String typeOfFile=tt[1];

                            Button button=new Button();
                            button.setPrefHeight(40);
                            button.setText("download "+typeOfFile);
                            button.setId(stringOfFile);
                            button.setOnAction(event -> Download((Button) event.getSource()));

                            v2.getChildren().add(button);
                        }
                    }
                    else{
                        TextArea t=new TextArea();
                        t.setPrefHeight(40);
                        if(item.equals("Text")){
                            t.setPromptText("Write text!");
                        }
                        if(item.equals("Media")){
                            t.setPromptText("Write address!");
                        }
                        v2.getChildren().add(t);
                        upleadingFile=t;
                    }

                    if(firstOfUername.equals("a") || firstOfUername.equals("v") || firstOfUername.equals("f") || isTa(courseNum,courseGroup)){
                        grade.setVisible(false);
                    }
                    else{
                        if(ans.size()!=0){
                            Label label=new Label();
                            label.setPrefHeight(40);
                            label.setText(ans.get("1").toString());
                            v2.getChildren().add(label);
                        }
                    }
                }
                else{
                    if(item.length()>14){
                        if(item.substring(0,12).equals("TypeOfFile->")){
                            String[] t=item.split("::::");
                            String stringOfFile=t[1];
                            String[] tt=t[0].split(">");
                            String typeOfFile=tt[1];

                            Button button=new Button();
                            button.setPrefHeight(40);
                            button.setText("download "+typeOfFile);
                            button.setId(stringOfFile);
                            button.setOnAction(event -> Download((Button) event.getSource()));

                            v2.getChildren().add(button);
                            continue;
                        }
                    }

                    Label t=new Label();
                    t.setPrefHeight(40);
                    t.setText(item);
                    v2.getChildren().add(t);
                }
            }
        }

        if(profHbox.isVisible()){
            String contentName=nameOfContent;

            Response response=Client.getInstanse(0).getServerController().getAllUploads(courseNum,courseGroup,contentName);

            if (response.getStatus().equals(ResponseStatus.ERROR)){
                return;
            }
            else{
                HashMap<String ,Object> c=response.getData();
                Object[] c2=response.getAnswer().get(0).values().toArray();
                for (int j = 0; j < 4; j++) {
                    int cntr=-1;
                    for (Map.Entry<String ,Object> temp:c.entrySet()){
                        cntr++;
                        if(j==0){
                            Label l=new Label();

                            if(!isTa(courseNum,courseGroup)){
                                l.setText(temp.getKey());
                            }
                            l.setId(temp.getKey());
                            l.setMinHeight(25);
                            l.setPrefWidth(195);
                            StuName.getChildren().add(l);
                            names.add(l);
                        }
                        if(j==1){
                            String item=(String) temp.getValue();
                            if(item.length()>14){
                                if(item.substring(0,12).equals("TypeOfFile->")){
                                    String[] t=item.split("::::");
                                    String stringOfFile=t[1];
                                    String[] tt=t[0].split(">");
                                    String typeOfFile=tt[1];

                                    Button button=new Button();
                                    button.setText("download "+typeOfFile);
                                    button.setMaxHeight(25);
                                    button.setPrefWidth(188);
                                    button.setId(stringOfFile);
                                    button.setOnAction(event -> Download((Button) event.getSource()));

                                    file.getChildren().add(button);
                                    continue;
                                }
                            }

                            TextArea t=new TextArea();
                            t.setMaxHeight(25);
                            t.setPrefWidth(188);
                            t.setText(item);
                            file.getChildren().add(t);
                        }
                        if(j==2){
                            TextField t=new TextField();
                            t.setPromptText("Enter mark");

                            if(c2.length!=0){
                                t.setText(c2[cntr].toString());
                            }
                            t.setMinHeight(25);
                            t.setPrefWidth(94);
                            mark.getChildren().add(t);
                            marks.add(t);
                        }
                        if(j==3){
                            Button button=new Button();
                            button.setText("Regiter grade!");
                            button.setMinHeight(25);
                            button.setPrefWidth(94);
                            button.setOnAction(event -> regGrade((Button) event.getSource()));
                            button.setId(String.valueOf(cntr));
                            operation.getChildren().add(button);
                        }
                    }
                }
            }
        }
    }
    private void regGrade(Button source) {
        TextField textField=marks.get(Integer.parseInt(source.getId()));
        if(textField.getText().equals("")){
            mssg.setText("Enter a mark first!");
        }
        else{
            int mark=Integer.valueOf(textField.getText());
            int courseNum=OfflineMode.getInstance().getCurrentCourseNum();
            int courseGroup=OfflineMode.getInstance().getCurrentCourseGroup();
            String nameOfHomework=nameOfContent;
            String username=names.get(Integer.parseInt(source.getId())).getId();

            Response response=Client.getInstanse(0).getServerController().setMarkForHomework(courseNum,courseGroup,nameOfHomework,username,mark);

            clearTable();
            initiatePage(response.getData());
        }
    }
    private HashMap<String, Object> findStudentUplodedFile(Object username, int courseNum, int courseGroup, String nameOfContent) {
        Response response=Client.getInstanse(0).getServerController().sendUploadedHomework(courseNum,courseGroup,nameOfContent,username);

        return response.getData();
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
        hbox.setVisible(false);
        profHbox.setVisible(false);
    }
    private boolean isStudentOfCourse() {
        int num= OfflineMode.getInstance().getCurrentCourseNum();
        int gp=OfflineMode.getInstance().getCurrentCourseGroup();

        if(isTa(num,gp)){
            return false;
        }

        if(firstOfUername.equals("b") || firstOfUername.equals("d") || firstOfUername.equals("m")){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isTa(int num,int gp) {
        Response response= Client.getInstanse(0).getServerController().getTaaOfCourse(num,gp);

        for (Object temp:response.getData().values()){
            if(OfflineMode.getInstance().getMainMenu().get("username").toString().equals((String)temp)){
                return true;
            }
        }
        return false;
    }

    public void upload(ActionEvent actionEvent) {
        if(upleadingFile.getText().equals("")){
            mssg.setText("Enter your homework!");
            return;
        }
        String homework;
        String nameOfHomeWork=nameOfContent;
        String username=OfflineMode.getInstance().getMainMenu().get("username").toString();
        int num=OfflineMode.getInstance().getCurrentCourseNum();
        int gp=OfflineMode.getInstance().getCurrentCourseGroup();
        if(upleadingFile.getPromptText().equals("Write address!")){
            String path=upleadingFile.getText();
            String[] s=path.split("\\.");
            String fileType=s[s.length-1];
            homework="TypeOfFile->"+fileType+"::::"+ FileConverter.encode(path);
        }
        else{
            homework=upleadingFile.getText();
        }
        
        Response response=Client.getInstanse(0).getServerController().uploadHomeWork(num,gp,nameOfHomeWork,username,homework);
        HashMap<String ,Object> C=response.getData();
        
        clearTable();
        initiatePage(C);
    }
    private void clearTable() {
        v2.getChildren().clear();
        StuName.getChildren().clear();
        file.getChildren().clear();
        mark.getChildren().clear();
        operation.getChildren().clear();

        names.clear();
        marks.clear();
    }
}
