package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {

    private Connection connection;
    private String address = "jdbc:mysql://localhost:3306/sweetdeals";
    private String user = "root";
    private String pass = "";
    private static DbConnection ourInstance = new DbConnection();

    public static DbConnection getInstance() {
        if (ourInstance==null){
            ourInstance=new DbConnection();
             System.out.println("db succes");
        }
        return ourInstance;
    }
    
    public Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(address,user,pass);
        return connection;
    }
    public String fetchData(String uname,String pass,String table,String fetchable) throws SQLException{
        String userType="";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT "+fetchable+" FROM "+table+" where username= '"+uname+"' and password ='"+pass+"';");
        while(resultSet.next()){
            userType=resultSet.getString("userType");
            System.out.println(userType);
        }
        return userType;
    }
    public String fetchDataID(String uname,String pass,String table,String fetchable) throws SQLException{
        String userType="";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT "+fetchable+" FROM "+table+" where username= '"+uname+"' and password ='"+pass+"';");
        while(resultSet.next()){
            userType=resultSet.getString("Id");
            System.out.println(userType);
        }
        return userType;
    }

    public boolean VerifyUser(String uname, String passw, String table) throws SQLException {
        boolean isAuthorized=false;
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM "+table+" where username= '"+uname+"' and password ='"+passw+"';");
        while(resultSet.next()){
            isAuthorized=true;
        }
        return isAuthorized;
    }
}
