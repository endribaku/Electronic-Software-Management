package Models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;

public class Supplier implements Serializable {
    private transient StringProperty supplierID;
    private transient StringProperty name;
    private transient ListProperty<Item> suppliedItems;

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
        }
    }

    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeUTF(this.supplierID.getValueSafe());
        outputStream.writeUTF(this.name.getValueSafe());
        outputStream.writeInt(this.suppliedItems.size());
        for (Item s : suppliedItems)
            outputStream.writeObject(s);
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
        this.supplierID = new SimpleStringProperty(inputStream.readUTF());
        this.name = new SimpleStringProperty(inputStream.readUTF());
        int size = inputStream.readInt();
        ListProperty<Item> itemsList = new SimpleListProperty<>(FXCollections.observableArrayList());
        for (int i = 0; i < size; i++) {
            itemsList.add((Item) inputStream.readObject());
        }
        this.suppliedItems = itemsList;
    }
}
