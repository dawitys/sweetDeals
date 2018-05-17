/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import tray.notification.*;
import Model.DbConnection;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author MY
 */
public class AddFormViewController implements Initializable {

    @FXML
    private Button submit_btn;
    @FXML
    private TextField items_name;
    @FXML
    private TextField items_catagory;
    @FXML
    private TextField items_price;
    
    public String seller;
    @FXML
    private Button uplode_pic;
    
    public String fileDir;
    
    public void initVariable(String sent){
        this.seller = sent;
        System.out.println(this.seller);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(seller);
        
        final FileChooser fileChooser = new FileChooser();
        
        uplode_pic.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        fileDir=getDir(file);
                        System.out.println(fileDir);
                    }
                }
            });
        submit_btn.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                System.out.println("Submit button");
                String iName=items_name.getText();
                String iCatagory=items_catagory.getText();
                Double iPrice=Double.parseDouble(items_price.getText());
                try{
                    Statement st=DbConnection.getInstance().getConnection().createStatement();
                    String query = "INSERT INTO sweetdeals.items (itemName, catagory, price, sellerId) VALUES ('" + iName + "','" + iCatagory + "','" + iPrice + "',"+seller+");";
                    st.executeUpdate(query);
                    String query2 = "INSERT INTO  `sweetdeals`.`itemImage` (`itemName` ,`uri`) VALUES ('"+iName+"','"+fileDir+"');";
                    st.executeUpdate(query2);
                    System.out.println("Succesful Insertion");
                    TrayNotification tray = new TrayNotification("Success","Succesful Insertion", NotificationType.INFORMATION);
                    tray.showAndDismiss(Duration.millis(3000));
                }catch(Exception e){
                    e.printStackTrace();
                }
                try{
                    Parent adminParent = FXMLLoader.load(getClass().getResource("/view/itemsListView.fxml"));
                    Scene adminScene = new Scene(adminParent);
                    Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    adminStage.hide();
                    adminStage.setScene(adminScene);
                    adminStage.setTitle("Items Panel");
                    adminStage.show();
                }catch(Exception e){
                    e.printStackTrace();
                }           
            } 
        });     
    }   
        public String getDir(File file){
            String dir=file.getAbsolutePath();
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<dir.length();i++){
                if(dir.substring(i, i+1).equalsIgnoreCase("\\")){
                    sb.append("\\"+"\\");
                }else{
                    sb.append(dir.substring(i, i+1));
                }
            }
            return sb.toString();
    }
    
}
