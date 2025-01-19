package Models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements Serializable{
    private transient ListProperty<Sector> sectors = new SimpleListProperty<>(FXCollections.observableArrayList());
    private transient ListProperty<Manager> managers = new SimpleListProperty<>(FXCollections.observableArrayList());

    public Inventory(ListProperty<Sector> sectors, ListProperty<Manager> managers) {
        this.sectors = new SimpleListProperty<>(sectors);
        this.managers = new SimpleListProperty<>(managers);
    }

    public Inventory(){

    }

    public ObservableList<Manager> getManagers() {
        return managers.get();
    }

    public ListProperty<Manager> managersProperty() {
        return managers;
    }

    public void setManagers(ObservableList<Manager> managers) {
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


    public void addSector(Sector sector) {sectors.add(sector);}
    public void removeSector(Sector sector) {sectors.remove(sector);}


    public void addCategory(String sectorName, Category category)
    {
        for(Sector s : sectors.get())
        {
            if(s.getSectorName().equals(sectorName))
            {
                s.addCategory(category);
            }
        }
    }

    public void removeCategory(String sectorName, Category category)
    {
        for(Sector s : sectors.get())
        {
            if(s.getSectorName().equals(sectorName))
            {
                s.removeCategory(category);
            }
        }
    }




    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(new ArrayList<>(sectors.get())); // Serialize as ArrayList
        outputStream.writeObject(new ArrayList<>(managers.get()));
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
        inputStream.defaultReadObject();
        List<Sector> loadedSectors = (List<Sector>) inputStream.readObject();
        List<Manager> loadedManagers = (List<Manager>) inputStream.readObject();

        this.sectors = new SimpleListProperty<>(FXCollections.observableArrayList(loadedSectors));
        this.managers = new SimpleListProperty<>(FXCollections.observableArrayList(loadedManagers));
    }
}






