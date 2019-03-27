/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Model.DbConnection;
import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author MY
 */
public class SignUpViewController implements Initializable {

    @FXML
    private TextField fullName_field;
    @FXML
    private TextField username_field;
    @FXML
    private Button signUp_btn;
    @FXML
    private PasswordField pwd_field;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        signUp_btn.setOnAction(new EventHandler(){
            
            @Override
            public void handle(Event event) {
                String fullName=fullName_field.getText();
                String password=pwd_field.getText();
                String username=username_field.getText();
                if("".equals(username) || "".equals(password)){
                    TrayNotification tray = new TrayNotification("Invalid","Required fields can not be empty", NotificationType.ERROR);
                    tray.showAndDismiss(Duration.millis(3000));
                }else if(username.length()<8 ){
                    TrayNotification tray = new TrayNotification("Invalid","Username too Short", NotificationType.ERROR);
                    tray.showAndDismiss(Duration.millis(3000));
                }else if(password.length()<8 ){
                    TrayNotification tray = new TrayNotification("Invalid","Password too Short", NotificationType.ERROR);
                    tray.showAndDismiss(Duration.millis(3000));
                }else{
                    try{
                        Statement st=DbConnection.getInstance().getConnection().createStatement();
                        String query = "INSERT INTO sweetdeals.users (username, password, fullName, userType) VALUES('" + username + "','" + password + "','" + fullName + "','client');";
                        st.executeUpdate(query);
                        System.out.println("Succesful SignUp");
                        TrayNotification tray = new TrayNotification("Welcome","You have joined sweetDeals", NotificationType.INFORMATION);
                        tray.showAndDismiss(Duration.millis(3000));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    try{
                        Parent adminParent = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
                        Scene adminScene = new Scene(adminParent);
                        Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        adminStage.hide();
                        adminStage.setScene(adminScene);
                        adminStage.setTitle("Main Panel");
                        adminStage.show();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
            
        });
    }    
    
}
