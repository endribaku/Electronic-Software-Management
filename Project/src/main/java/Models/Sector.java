package Models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class Sector implements Serializable {
    private transient StringProperty sectorName;
    private transient ListProperty<User> cashiers = new SimpleListProperty<>(FXCollections.observableArrayList());
    private transient ObjectProperty<User> manager;
    private transient ListProperty<Category> categories = new SimpleListProperty<>(FXCollections.observableArrayList());


    public Sector(String sectorName, ArrayList<User> cashiers, User manager, ArrayList<Category> categories) {
        this.sectorName = new SimpleStringProperty(sectorName);
        this.cashiers.set(FXCollections.observableArrayList(cashiers));
        this.manager = new SimpleObjectProperty<>(manager);
        this.categories.set(FXCollections.observableArrayList(categories));
    }

    public Sector() {
        this.sectorName = new SimpleStringProperty();
        this.manager = new SimpleObjectProperty<>();
    }

    public Sector(String sectorName, User manager) {
        this.sectorName = new SimpleStringProperty(sectorName);
        this.manager = new SimpleObjectProperty<>(manager);
    }

    public ObservableList<User> getCashiers() {
        return cashiers.get();
    }
    public String getSectorName() {return sectorName.get();}
    public User getManager() {
        return manager.get();
    }


    public StringProperty sectorNameProperty() {return sectorName;}
    public ObjectProperty<User> managerProperty() {return manager;}
    public ListProperty<User> cashiersProperty() {return cashiers;}

    public void setSectorName(String sectorName) {this.sectorName.set(sectorName);}
    public void setCashiers(ObservableList<User> cashiers) {this.cashiers.set(cashiers);}
    public void setManager(User manager) {this.manager.set(manager);}

    public void addCashier(User cashier)
    {
        this.cashiers.add(cashier);
    }

    public void removeCashier(String username)
    {
        for (User cashier : cashiers) {
            if (cashier.getUsername().equals(username)) {
                cashiers.remove(cashier);
            }
        }
    }

    public void addCategory(Category category)
    {
        categories.add(category);
    }

    public ObservableList<Category> getCategories() {
        return categories.get();
    }

    public ListProperty<Category> categoriesProperty() {
        return categories;
    }

    public void setCategories(ObservableList<Category> categories) {
        this.categories.set(categories);
    }

    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeUTF(this.sectorName.getValueSafe());
        outputStream.writeObject(new ArrayList<>(this.cashiers.getValue()));
//        outputStream.writeInt(this.cashiers.size());
//        for (User c : cashiers)
//            outputStream.writeObject(c);
        outputStream.writeObject(manager.getValue());
        outputStream.writeObject(new ArrayList<>(this.categories.getValue()));
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
        this.sectorName = new SimpleStringProperty(inputStream.readUTF());
        int size = inputStream.readInt();
        ListProperty<User> cashierList = new SimpleListProperty<>(FXCollections.observableArrayList());
        for (int i = 0; i < size; i++) {
            cashierList.add((User) inputStream.readObject());
        }
        this.cashiers = cashierList;
        this.manager = new SimpleObjectProperty<User>((User) inputStream.readObject());
        this.categories = new SimpleListProperty<>(FXCollections.observableArrayList((ArrayList<Category>) inputStream.readObject()));
    }

    @Override
    public String toString() {
        return sectorName.getValueSafe();
    }
}
