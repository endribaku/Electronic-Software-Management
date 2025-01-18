package Models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class Inventory implements Serializable{
    private transient ListProperty<Sector> sectors = new SimpleListProperty<>(FXCollections.observableArrayList());
    private transient ListProperty<User> managers = new SimpleListProperty<>(FXCollections.observableArrayList());

    public Inventory(ListProperty<Sector> sectors, ListProperty<User> managers) {
        this.sectors = new SimpleListProperty<>(sectors);
        this.managers = new SimpleListProperty<>(managers);
    }

    public Inventory(){

    }

    public ObservableList<User> getManagers() {
        return managers.get();
    }

    public ListProperty<User> managersProperty() {
        return managers;
    }

    public void setManagers(ObservableList<User> managers) {
        this.managers.set(managers);
    }

    public ObservableList<Sector> getSectors() {
        return sectors.get();
    }

    public ListProperty<Sector> sectorsProperty() {
        return sectors;
    }

    public void setSectors(ObservableList<Sector> sectors) {
        this.sectors.set(sectors);
    }

    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(sectors.getValue());
        outputStream.writeObject(managers.getValue());
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
        this.sectors = new SimpleListProperty<>(FXCollections.observableArrayList((ArrayList<Sector>) inputStream.readObject()));
        this.managers = new SimpleListProperty<>(FXCollections.observableArrayList((ArrayList<User>) inputStream.readObject()));
    }





}
