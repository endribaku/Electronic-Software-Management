package Models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

public class Supplier implements Serializable {
    private transient StringProperty supplierID;
    private transient StringProperty name;
    private transient final ListProperty<Item> suppliedItems;

    public Supplier() {
        this.supplierID = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.suppliedItems = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

    public String getSupplierID() {return supplierID.get();}
    public String getName() {return name.get();}
    public ObservableList<Item> getSuppliedItems() {return suppliedItems.get();}

    public ListProperty<Item> suppliedItemsProperty() {
        return suppliedItems;
    }
    public StringProperty nameProperty() {
        return name;
    }
    public StringProperty supplierIDProperty() {
        return supplierID;
    }


    public void setSupplierID(String supplierID) {this.supplierID.set(supplierID);}
    public void setName(String name) {this.name.set(name);}
    public void setSuppliedItems(ObservableList<Item> suppliedItems) {this.suppliedItems.set(suppliedItems);};

    public void addSuppliedItem(Item item)
    {
        if (!suppliedItems.contains(item)) {
            suppliedItems.add(item);
        };
    }
}
