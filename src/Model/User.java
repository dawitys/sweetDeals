package Model;


import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private SimpleStringProperty id;
    private SimpleStringProperty fullName;
    private SimpleStringProperty bogusStatus;
    private String username;
    private String password;
    private String userType;


    public User(String id, String name, String bogus) {
        this.id = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(name);
        this.bogusStatus = new SimpleStringProperty(bogus);
    }

    public User(String id, String name, String bogus,String username,String password) {
        this.id = new SimpleStringProperty(id);
        this.fullName = new SimpleStringProperty(name);
        this.bogusStatus = new SimpleStringProperty(bogus);
        this.username=username;
        this.password=password;
    }

    public User(String username,String password) {
        this.username=username;
        this.password=password;
    }
    public String getId() {
        return id.get();
    }
    public String getFullName() {
        return fullName.get();
    }
    public String getBogusStatus() {
        return bogusStatus.get();
    }
    public String getUserType() {
        return userType;
    }
    public void setBogusStatus(String bogus) {
        this.bogusStatus=new SimpleStringProperty(bogus);
    }
    public boolean authenticateUser() throws SQLException{
        if(DbConnection.getInstance().VerifyUser(username,password,"users")){
            this.userType=DbConnection.getInstance().fetchData(username,password,"users","userType");
            return true;
        }else{
            return false;
        }        
    }    
}
