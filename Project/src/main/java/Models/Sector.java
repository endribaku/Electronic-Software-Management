package Models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class Sector implements Serializable {
    private transient StringProperty sectorName;
    private transient ListProperty<Cashier> cashiers = new SimpleListProperty<>(FXCollections.observableArrayList());
    private transient ObjectProperty<Manager> manager;

    public Sector(String sectorName, ArrayList<Cashier> cashiers, Manager manager) {
        this.sectorName = new SimpleStringProperty(sectorName);
        this.cashiers.set(FXCollections.observableArrayList(cashiers));
        this.manager = new SimpleObjectProperty<>(manager);
    }

    public Sector() {
        this.sectorName = new SimpleStringProperty();
        this.manager = new SimpleObjectProperty<>();
    }

    public ObservableList<Cashier> getCashiers() {
        return cashiers.get();
    }
    public String getSectorName() {return sectorName.get();}
    public Manager getManager() {
        return manager.get();
    }


    public StringProperty sectorNameProperty() {return sectorName;}
    public ObjectProperty<Manager> managerProperty() {return manager;}
    public ListProperty<Cashier> cashiersProperty() {return cashiers;}

    public void setSectorName(String sectorName) {this.sectorName.set(sectorName);}
    public void setCashiers(ObservableList<Cashier> cashiers) {this.cashiers.set(cashiers);}
    public void setManager(Manager manager) {this.manager.set(manager);}

    public void addCashier(Cashier cashier)
    {
        this.cashiers.add(cashier);
    }

    public void removeCashier(String username)
    {
        for (Cashier cashier : cashiers) {
            if (cashier.getUsername().equals(username)) {
                cashiers.remove(cashier);
            }
        }
    }

    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeUTF(this.sectorName.getValueSafe());
        outputStream.writeInt(this.cashiers.size());
        for (Cashier c : cashiers)
            outputStream.writeObject(c);
        outputStream.writeObject(manager.getValue());
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
        this.sectorName = new SimpleStringProperty(inputStream.readUTF());
        int size = inputStream.readInt();
        ListProperty<Cashier> cashierList = new SimpleListProperty<>(FXCollections.observableArrayList());
        for (int i = 0; i < size; i++) {
            cashierList.add((Cashier) inputStream.readObject());
        }
        this.cashiers = cashierList;
        this.manager = new SimpleObjectProperty<Manager>((Manager) inputStream.readObject());
    }
}
