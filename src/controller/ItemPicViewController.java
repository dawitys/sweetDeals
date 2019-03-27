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
    private Label items_name;
    
    private String sellerId;
    public int itemId;
    private String itemsName;
    public String imgUri="";

    /**
     * Initializes the controller class.
     */
    public void initVar(String user,String id,String seller){
        this.sellerId=user;
        itemId=Integer.parseInt(id);
        itemsName=seller;
        try{
            Statement st=DbConnection.getInstance().getConnection().createStatement();
            String query2 = "SELECT uri FROM  itemimage WHERE  itemName ='"+itemsName+"';";
            ResultSet rs2=st.executeQuery(query2);
            while (rs2.next()){
                imgUri=rs2.getString("uri");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        items_name.setText(itemsName);
        System.out.println(imgUri);
        String ur=encode(imgUri);
        
        if(!"".equals(imgUri)){
            Image i=new Image(ur);
            item_pic_view.setImage(i);
        }else{
            Image i=new Image("/view/default.png");
            item_pic_view.setImage(i);
        }
    }
    private static String encode(String i){
        String out ="file:/"+i.replace("\\","/");
        return out;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lg.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/itemsListView.fxml"));
                    Parent adminParent = loader.load();
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
