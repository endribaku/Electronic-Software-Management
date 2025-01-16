package Models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
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
}
