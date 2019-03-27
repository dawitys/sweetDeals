/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import Model.DbConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
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
public class SweetDealController implements Initializable {

    @FXML
    private Button login_btn;
    @FXML
    private PasswordField password_input;
    @FXML
    private TextField username_input;
    @FXML
    private Hyperlink create_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        create_btn.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                try{
                    Parent adminParent = FXMLLoader.load(getClass().getResource("/view/signUpView.fxml"));
                    Scene adminScene = new Scene(adminParent);
                    Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    adminStage.hide();
                    adminStage.setScene(adminScene);
                    adminStage.setTitle("SignUp Panel");
                    adminStage.show();
                }catch(Exception e){
                    e.printStackTrace();
                }  
            }
        });
        login_btn.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                String username=username_input.getText();
                String password=password_input.getText();
                Model.User user=new Model.User(username,password);
                try {
                    if(user.authenticateUser()){
                        switch(user.getUserType()){
                            case("client"):
                                try{
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/itemsListView.fxml"));
                                    Parent adminParent = loader.load();
                                    ItemsListViewController controller = loader.<ItemsListViewController>getController();
                                    String uId=DbConnection.getInstance().fetchDataID(username,password,"users","Id");
                                    controller.initVariable(uId);
                                    Scene adminScene = new Scene(adminParent);
                                    Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    adminStage.hide();
                                    adminStage.setScene(adminScene);
                                    adminStage.setTitle("Items Panel");
                                    adminStage.show();
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                break;
                            case("admin"):
                                try{
                                    Parent adminParent = FXMLLoader.load(getClass().getResource("/view/adminOptionView.fxml"));
                                    Scene adminScene = new Scene(adminParent);
                                    Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    adminStage.hide();
                                    adminStage.setScene(adminScene);
                                    adminStage.setTitle("Option Panel");
                                    adminStage.show();
                                }catch(Exception e){
                                    e.printStackTrace();
                                }  
                                break;
                        }
                    }else{
                        TrayNotification tray = new TrayNotification("Error","Invalid username/password", NotificationType.WARNING);
                        tray.showAndDismiss(Duration.millis(3000));
                    }
                }catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }  
        });
    }    
    
}
