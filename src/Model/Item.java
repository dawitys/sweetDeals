package Model;


import javafx.beans.property.SimpleStringProperty;

public class Item {
    private SimpleStringProperty id;
    private SimpleStringProperty itemName;
    private SimpleStringProperty catagory;
    private SimpleStringProperty price;
    private SimpleStringProperty soldStatus;


    public Item(int id, String name, String catagory,Double price,boolean status) {
        this.id = new SimpleStringProperty(String.valueOf(id));
        this.itemName = new SimpleStringProperty(name);
        this.catagory = new SimpleStringProperty(catagory);
        this.price=new SimpleStringProperty(String.valueOf(price));
        this.soldStatus=new SimpleStringProperty(String.valueOf(status));
        System.out.println(this.id.get()+this.itemName.get()+this.catagory.get()+this.price.get()+this.soldStatus.get());
    }

    public String getId() {
        return id.get();
    }
    public String getItemName() {
        return itemName.get();
    }
    public String getPrice() {
        return price.get();
    }
    public String getCatagory() {
        return catagory.get();
    }
    
    
    public void setSoldStatus(boolean status) {
        this.soldStatus=new SimpleStringProperty(String.valueOf(status));
    }
    public String getSoldStatus() {
        return soldStatus.get();
    }
}
