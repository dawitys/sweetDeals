/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Model.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.Predicate;
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
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author MY
 */
public class UsersListViewController implements Initializable {

    @FXML
    private Hyperlink lg;
    @FXML
    private TableView<User> user_table_view;
    @FXML
    private TableColumn<User, String> userId;
    @FXML
    private TableColumn<User, String> userFullName;
    @FXML
    private TableColumn<User,String> user_bogous;
    @FXML
    private Button deleteUser_btn;
    
    ObservableList<User> list = FXCollections.observableArrayList();
    @FXML
    private TextField find_key;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        find_key.setOnMouseClicked(new EventHandler(){
            @Override
            public void handle(Event event) {
                setDisable();
                FilteredList<User> filteredData = new FilteredList<>(list, e -> true);
                System.out.println("filtering");
                find_key.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super User>) new Predicate<User>() {
                    @Override
                    public boolean test(User user) {
                        String lowerCase = newValue.toLowerCase();
                        if (newValue.isEmpty()) {
                            return true;
                        } else if (user.getId().contains(newValue)) {
                            return true;
                        } else if (user.getFullName().toLowerCase().contains(lowerCase)) {
                            return true;
                        } else if (user.getBogusStatus() .toLowerCase().contains(lowerCase)) {
                            return true;
                        }
                        return false;
                        
                    }
                });
            });
            SortedList<User> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(user_table_view.comparatorProperty());
            
            user_table_view.setItems(sortedList);
            }
        });
        lg.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                try{
                    Parent adminParent = FXMLLoader.load(getClass().getResource("/view/adminOptionView.fxml"));
                    Scene adminScene = new Scene(adminParent);
                    Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    adminStage.hide();
                    adminStage.setScene(adminScene);
                    adminStage.setTitle("Admin Option");
                    adminStage.show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }   
        });
        deleteUser_btn.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                int selectedUserId=Integer.parseInt(user_table_view.getSelectionModel().getSelectedItem().getId());
                try{
                    Statement st=DbConnection.getInstance().getConnection().createStatement();
                    String query = "DELETE FROM `sweetDeals`.`users`WHERE id="+selectedUserId+";";
                    st.executeUpdate(query);
                    
                    System.out.println("Deleted");
                    TrayNotification tray = new TrayNotification("Deleted","Seleced User has lost his account.", NotificationType.INFORMATION);
                    tray.showAndDismiss(Duration.millis(3000));
                    Refresh();
                }catch(Exception e){
                    e.printStackTrace();
                }
                
            } 
        });
        try{
            loadTable();
            setDisable();
        }catch(Exception ex){}
    }    

    private void loadTable() {
        try{
            Statement st=DbConnection.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT Id, fullName, bogusReported FROM  `users` WHERE userType =  'client';");
            while(rs.next()){
                String id=String.valueOf(rs.getInt("Id"));
                String name=rs.getString("fullName");
                String isBogus=String.valueOf(rs.getBoolean("bogusReported"));
                
                System.out.println(id);
                System.out.println(name);
                System.out.println(isBogus);

                list.add(new User(id,name,isBogus));
            } 
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        userId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        userFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        user_bogous.setCellValueFactory(new PropertyValueFactory<>("bogusStatus"));
      
        user_table_view.setOnMousePressed((MouseEvent event) -> {
            setEnable();
        });
        user_table_view.setItems(list);
    }

    private void setDisable() {
        deleteUser_btn.setDisable(true);
    }
    private void setEnable() {
        deleteUser_btn.setDisable(false);
    }
 
    private void Refresh() {
        list.removeAll(list);
        user_table_view.setItems(null);
        loadTable();
    }
}
