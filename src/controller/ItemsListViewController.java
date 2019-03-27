/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Model.*;
import java.io.DataInputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author MY
 */
public class ItemsListViewController implements Initializable {

    @FXML
    private TableView<Item> item_table_view;
    @FXML
    private TableColumn<Item, String> item_id_col;
    @FXML
    private TableColumn<Item, String> item_name_col;
    @FXML
    private TableColumn<Item, String> item_catagory_col;
    @FXML
    private TableColumn<Item, String> item_price_col;
    @FXML
    private TableColumn<Item, String> sold_col;
    @FXML
    private Button search_btn;
    @FXML
    private Button see_pic_btn;
    @FXML
    private Button buy_btn;
    @FXML
    private Button report_btn;
    @FXML
    private Hyperlink logout_link;
    @FXML
    private Button add_item_btn;
    @FXML
    private TextField find_key;
    
    private String sellerId;
    private String reportedSellerId;
    private String itemId;
    private String itemName;
    ObservableList<Item> list = FXCollections.observableArrayList();
    
    public void initVariable(String sent){
        this.sellerId = sent;
        System.out.println("To ilv initiated with seller id:"+sellerId);
    }

    private void setEnable(){
        report_btn.setDisable(false);
        buy_btn.setDisable(false);
        see_pic_btn.setDisable(false);
    }
    private void setDisable(){
        report_btn.setDisable(true);
        buy_btn.setDisable(true);
        see_pic_btn.setDisable(true);
    }
    
    public void loadTable() throws SQLException {
        try{
            Statement st=DbConnection.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT itemId,itemName,Catagory,price,soldOut FROM `items`;");
            while(rs.next()){
                int id=rs.getInt("itemId");
                String name=rs.getString("itemName");
                String catagory=rs.getString("Catagory");
                Double price=rs.getDouble("price");
                boolean isSold=rs.getBoolean("soldOut");
                
                list.add(new Item(id,name,catagory,price,isSold));
            } 
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        item_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        item_name_col.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        item_catagory_col.setCellValueFactory(new PropertyValueFactory<>("catagory"));
        item_price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        sold_col.setCellValueFactory(new PropertyValueFactory<>("soldStatus"));
        item_table_view.setOnMousePressed((MouseEvent event) -> {
            setEnable();
            itemId=item_table_view.getSelectionModel().getSelectedItem().getId();
            itemName=item_table_view.getSelectionModel().getSelectedItem().getItemName();
        });
        item_table_view.setItems(list);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        find_key.setOnMouseClicked(new EventHandler(){
            @Override
            public void handle(Event event) {
                setDisable();
                FilteredList<Item> filteredData = new FilteredList<>(list, e -> true);
                System.out.println("filtering");
                find_key.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Item>) new Predicate<Item>() {
                    @Override
                    public boolean test(Item item) {
                        String lowerCase = newValue.toLowerCase();
                        if (newValue.isEmpty()) {
                            return true;
                        } else if (item.getId().contains(newValue)) {
                            return true;
                        } else if (item.getItemName().toLowerCase().contains(lowerCase)) {
                            return true;
                        } else if (item.getCatagory().toLowerCase().contains(lowerCase)) {
                            return true;
                        } else if (item.getSoldStatus().toLowerCase().contains(lowerCase)) {
                            return true;
                        }
                        return false;
                        
                    }
                });
            });
            SortedList<Item> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(item_table_view.comparatorProperty());
            
            item_table_view.setItems(sortedList);}
        });
        report_btn.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reportView.fxml"));
                    Parent adminParent = loader.load();
                    ReportViewController controller = loader.<ReportViewController>getController();
                    int a= Integer.parseInt(itemId);
                    Statement st=DbConnection.getInstance().getConnection().createStatement();
                    String queryS="SELECT sellerId FROM items WHERE itemId ="+ a +";";
                    ResultSet rs = st.executeQuery(queryS);
                    while(rs.next()){
                        reportedSellerId=String.valueOf(rs.getInt("sellerId"));
                    }
                    controller.initVariable(sellerId,reportedSellerId,itemId,itemName);
                    Scene adminScene = new Scene(adminParent);
                    Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    adminStage.hide();
                    adminStage.setScene(adminScene);
                    adminStage.setTitle("Login");
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
                    adminStage.setTitle("Login");
                    adminStage.show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        see_pic_btn.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/itemPicView.fxml"));
                    Parent parent=loader.load();
                    ItemPicViewController controller = loader.<ItemPicViewController>getController();
                    controller.initVar(sellerId,itemId,itemName);
                    Scene adminScene = new Scene(parent);
                    Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    adminStage.hide();
                    adminStage.setScene(adminScene);
                    adminStage.setTitle("Item Picture");
                    adminStage.show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        add_item_btn.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addFormView.fxml"));
                    Parent adminParent = loader.load();
                    AddFormViewController controller = loader.<AddFormViewController>getController();
                    controller.initVariable(sellerId);
                    Scene adminScene = new Scene(adminParent);
                    Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    adminStage.hide();
                    adminStage.setScene(adminScene);
                    adminStage.setTitle("Add item Panel");
                    adminStage.show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        buy_btn.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/buyFormView.fxml"));
                    Parent adminParent = loader.load();
                    BuyFormViewController controller = loader.<BuyFormViewController>getController();
                    controller.initiateVarId(itemId,sellerId);
                    Scene adminScene = new Scene(adminParent);
                    Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    adminStage.hide();
                    adminStage.setScene(adminScene);
                    adminStage.setTitle("Buy Item Panel");
                    adminStage.show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }              
        });
        try {
            loadTable();
            setDisable();
        } catch (SQLException ex) {
            Logger.getLogger(ItemsListViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("initiated with seller id:"+sellerId);

    }    
    
}
