package Models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements Serializable{
    private static final long serialVersionUID = 1L;
    private transient ListProperty<Sector> sectors = new SimpleListProperty<>(FXCollections.observableArrayList());
    private transient ListProperty<Manager> managers = new SimpleListProperty<>(FXCollections.observableArrayList());
    private transient ListProperty<Supplier> suppliers = new SimpleListProperty<>(FXCollections.observableArrayList());

    public Inventory(ListProperty<Sector> sectors, ListProperty<Manager> managers, ListProperty<Supplier> suppliers) {
        this.sectors = new SimpleListProperty<>(sectors);
        this.managers = new SimpleListProperty<>(managers);
        this.suppliers = new SimpleListProperty<>(suppliers);

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

    public ObservableList<Supplier> getSuppliers() {return suppliers.get();}

    public ListProperty<Supplier> suppliersProperty() {return suppliers;}

    public void setSuppliers(ObservableList<Supplier> suppliers) {this.suppliers.set(suppliers);}


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

    public void addSupplier(Supplier supplier) {this.suppliers.add(supplier);}
    public void removeSupplier(String supplierName) {
        for(Supplier s: suppliers)
        {
            if(s.getName().equals(supplierName))
            {
                suppliers.remove(s);
                return;
            }
        }
    }




    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(new ArrayList<>(sectors.get())); // Serialize as ArrayList
        outputStream.writeObject(new ArrayList<>(managers.get()));
        outputStream.writeObject(new ArrayList<>(suppliers.get()));
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
        inputStream.defaultReadObject();
        List<Sector> loadedSectors = (List<Sector>) inputStream.readObject();
        List<Manager> loadedManagers = (List<Manager>) inputStream.readObject();
        List<Supplier> loadedSuppliers = (List<Supplier>) inputStream.readObject();

        this.sectors = new SimpleListProperty<>(FXCollections.observableArrayList(loadedSectors));
        this.managers = new SimpleListProperty<>(FXCollections.observableArrayList(loadedManagers));
        this.suppliers = new SimpleListProperty<>(FXCollections.observableArrayList(loadedSuppliers));
    }
}






