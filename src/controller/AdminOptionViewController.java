/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
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
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MY
 */
public class AdminOptionViewController implements Initializable {

    @FXML
    private Hyperlink logout_link;
    @FXML
    private Button user_btn;
    @FXML
    private Button itembtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user_btn.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                try{
                    Parent adminParent = FXMLLoader.load(getClass().getResource("/view/usersListView.fxml"));
                    Scene adminScene = new Scene(adminParent);
                    Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    adminStage.hide();
                    adminStage.setScene(adminScene);
                    adminStage.setTitle("Users Managment");
                    adminStage.show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            
        });
        itembtn.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                try{
                    Parent adminParent = FXMLLoader.load(getClass().getResource("/view/itemsListView.fxml"));
                    Scene adminScene = new Scene(adminParent);
                    Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    adminStage.hide();
                    adminStage.setScene(adminScene);
                    adminStage.setTitle("Items Managment");
                    adminStage.show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            
        });
        logout_link.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                try{
                    Parent adminParent = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
                    Scene adminScene = new Scene(adminParent);
                    Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    adminStage.hide();
                    adminStage.setScene(adminScene);
                    adminStage.setTitle("Main Window");
                    adminStage.show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            
        });
    }    
    
}
