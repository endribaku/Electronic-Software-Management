package Models;


import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class Manager extends User implements Serializable {

    private transient StringProperty ManagerID = new SimpleStringProperty();
    private transient ListProperty<Sector> sectors = new SimpleListProperty<>(FXCollections.observableArrayList());
    private transient ObjectProperty<Inventory> inventory = new SimpleObjectProperty<>();
    private transient ListProperty<Supplier> suppliers = new SimpleListProperty<>(FXCollections.observableArrayList());

    public Manager (String username, String password, String fullName, LocalDate dateOfBirth, String phoneNumber, String email, double salary, ObservableList<String> permissions, ObservableList<String> sectors) {
        super(username, password, fullName, dateOfBirth, phoneNumber, email, salary, Access.Manager, permissions, sectors);
    }


    //Getters and Setters
    public String getManagerID() {return ManagerID.get();}
    public Inventory getInventory() {return inventory.get();}
    public ObservableList<Sector> getSectors() {return sectors.get();}
    public ObservableList<Supplier> getSuppliers() {return suppliers.get();}

    public StringProperty managerIDProperty() {return ManagerID;}
    public ObjectProperty<Inventory> inventoryProperty() {return inventory;}
    public ListProperty<Sector> sectorsProperty() {return sectors;}
    public ListProperty<Supplier> suppliersProperty() {return suppliers;}

    public void setManagerID(String managerID) {this.ManagerID.set(managerID);}
    public void setInventory(Inventory inventory) {this.inventory.set(inventory);}
    public void setSectors(ArrayList<Sector> sectors) {this.sectors.set(FXCollections.observableArrayList(sectors));}


    public String notifyManager(String message) {
        return "Notification sent to Manager: " + message;
    }





    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException{
        outputStream.defaultWriteObject();
        outputStream.writeUTF(this.ManagerID.getValueSafe());
        outputStream.writeInt(this.sectors.size());
        for (Sector s : sectors)
            outputStream.writeObject(s);
        outputStream.writeObject(this.inventory.getValue());
        outputStream.writeInt(this.suppliers.size());
        for (Supplier s : suppliers)
            outputStream.writeObject(s);
    }

    @Serial void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
        this.ManagerID = new SimpleStringProperty(inputStream.readUTF());
        int size1 = inputStream.readInt();
        ListProperty<Sector> sectorsList = new SimpleListProperty<>(FXCollections.observableArrayList());
        for (int i = 0; i < size1; i++){
            sectorsList.add((Sector) inputStream.readObject());
        }
        this.sectors = sectorsList;

        this.inventory = new SimpleObjectProperty<Inventory>((Inventory) inputStream.readObject());
        int size2 = inputStream.readInt();
        ListProperty<Supplier> suppliersList = new SimpleListProperty<>(FXCollections.observableArrayList());
        for (int i = 0; i < size2; i++){
            suppliersList.add((Supplier) inputStream.readObject());
        }
        this.suppliers = suppliersList;
    }

}
