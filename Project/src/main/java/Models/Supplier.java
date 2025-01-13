package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Supplier implements Serializable {
    String supplierID;
    String name;
    ArrayList<Item> suppliedItems;

    public String getSupplierID() {return supplierID;}
    public String getName() {return name;}
    public ArrayList<Item> getSuppliedItems() {return suppliedItems;}

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public void setName(String name) {this.name = name;}

    public void setSuppliedItems(ArrayList<Item> suppliedItems) {this.suppliedItems = suppliedItems;}

    public void addSuppliedItem(Item item)
    {
        if (suppliedItems == null) suppliedItems = new ArrayList<>();
        suppliedItems.add(item);
    }
}
