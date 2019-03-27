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
public class BuyFormViewController implements Initializable {

    @FXML
    private TextField name_box;
    @FXML
    private Button submit_button;
    @FXML
    private TextField adress__box;
    
    public String sellerId;
    public int itemId;

    /**
     * Initializes the controller class.
     */
   
    public void initiateVarId(String i,String j){
        this.itemId=Integer.parseInt(i);
        this.sellerId=j;
        System.out.println(sellerId);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        submit_button.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                String buyer=name_box.getText();
                String buyerAdress =adress__box.getText();
                try{
                    Statement st=DbConnection.getInstance().getConnection().createStatement();
                    String query = "INSERT INTO `sweetdeals`.`transaction` (`buyer`, `buyerAdress`) VALUES ('"+buyer+"','"+buyerAdress+"');";
                    st.executeUpdate(query);
                    String query2= "UPDATE  `sweetdeals`.`items` SET  `soldOut` =  '1' WHERE  `items`.`itemId` ="+itemId+";";
                    st.executeUpdate(query2);
                    System.out.println("Succesful Insertion");
                    TrayNotification tray = new TrayNotification("Succesful Transaction","Item will be delivered soon", NotificationType.INFORMATION);
                    tray.showAndDismiss(Duration.millis(5000));
                }catch(Exception e){
                    e.printStackTrace();
                }
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/itemsListView.fxml"));
                    Parent adminParent=loader.load();
                    ItemsListViewController controller = loader.<ItemsListViewController>getController();
                    controller.initVariable(sellerId);
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
    
}
