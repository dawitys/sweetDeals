/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Model.DbConnection;
import java.net.URL;
import java.sql.ResultSet;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MY
 */
public class ItemPicViewController implements Initializable {

    @FXML
    private Hyperlink lg;
    @FXML
    private ImageView item_pic_view;
    @FXML
    private Label item_name;
    @FXML
    private Label seller_name;
    
    public int itemId;

    /**
     * Initializes the controller class.
     */
    public void initVar(String id){
        itemId=Integer.parseInt(id);
    }
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        String imgUri="";
        String itemName="";
        try{
            Statement st=DbConnection.getInstance().getConnection().createStatement(); 
            String query1 = "SELECT itemName FROM  items WHERE  items.itemId ="+itemId+";";
            ResultSet rs=st.executeQuery(query1);
            while (rs.next()){
                itemName=rs.getString("itemName");
            }
            String query2 = "SELECT uri FROM  itemimage WHERE  itemName ='"+itemName+"';";
            ResultSet rs2=st.executeQuery(query2);
            while (rs2.next()){
                imgUri=rs2.getString("uri");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        item_name.setText(itemName);
        System.out.println(imgUri);
        lg.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
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
    
}
