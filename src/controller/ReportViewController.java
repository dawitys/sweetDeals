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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MY
 */
public class ReportViewController implements Initializable {

    @FXML
    private Button submit_btn;
    public String reportedSellerId;
    
    public String sellerId;
    private int itemId;
    private String itemName;
    @FXML
    private TextArea comm_field;

    /**
     * Initializes the controller class.
     */
    public void initVariable(String sellerId,String reported,String iId,String iName){
        this.reportedSellerId=reported;
        this.itemName=iName;
        this.itemId= Integer.parseInt(iId); 
    }
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        submit_btn.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                int sId = Integer.parseInt(reportedSellerId);
                String reason=comm_field.getText();
                try{
                    Statement st=DbConnection.getInstance().getConnection().createStatement();
                    System.out.println(reportedSellerId);
                    int rsId=Integer.parseInt(reportedSellerId);
                    String query = "UPDATE  `sweetdeals`.`users` SET  `bogusReported` =  '1' WHERE  `users`.`Id` ="+rsId+";";
                    st.executeUpdate(query);
                    
                    String query2 = "INSERT INTO `sweetdeals`.`reports` (`itemId`, `itemName`, `sellerId`, `reason`) VALUES ("+itemId+", '"+itemName+"', "+sId+", '"+reason+"');";
                    st.executeUpdate(query2);
                    
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
