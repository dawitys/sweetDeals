package Model;


import javafx.beans.property.SimpleStringProperty;

public class Item {
    private SimpleStringProperty itemId;
    private SimpleStringProperty itemName;
    private SimpleStringProperty itemCatagory;
    private SimpleStringProperty itemPrice;
    private SimpleStringProperty soldStatus;


    public Item(int id, String name, String catagory,Double price,boolean status) {
        this.itemId = new SimpleStringProperty(String.valueOf(id));
        this.itemName = new SimpleStringProperty(name);
        this.itemCatagory = new SimpleStringProperty(catagory);
        this.itemPrice=new SimpleStringProperty(String.valueOf(price));
        this.soldStatus=new SimpleStringProperty(String.valueOf(status));
//        System.out.println(this.itemId.get()+this.itemName.get()+this.itemCatagory.get()+this.itemPrice.get()+this.soldStatus.get());
    }

    public String getId() {
        return itemId.get();
    }
    public String getItemName() {
        return itemName.get();
    }
    public String getPrice() {
        return itemPrice.get();
    }
    public String getCatagory() {
        return itemCatagory.get();
    }
    
    
    public void setSoldStatus(boolean status) {
        this.soldStatus=new SimpleStringProperty(String.valueOf(status));
    }
    public String getSoldStatus() {
        return soldStatus.get();
    }
}
