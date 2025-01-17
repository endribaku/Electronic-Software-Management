package Models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class Inventory implements Serializable{
    private transient ListProperty<Category> categories = new SimpleListProperty<>(FXCollections.observableArrayList());
    private transient ListProperty<Manager> managers = new SimpleListProperty<>(FXCollections.observableArrayList());

    public Inventory(ListProperty<Category> categories, ListProperty<Manager> managers) {
        this.categories = new SimpleListProperty<>(categories);
        this.managers = new SimpleListProperty<>(managers);
    }

    public Inventory(){

    }

    public ObservableList<Category> getCategories() {return categories.get();}

    public ListProperty<Category> categoriesProperty() {return categories;}

    public void setCategories(ObservableList<Category> categories) {this.categories.set(categories);}

    public ObservableList<Manager> getManagers() {return managers.get();}

    public ListProperty<Manager> managersProperty() {return managers;}

    public void setManagers(ObservableList<Manager> managers) {this.managers.set(managers);}

    public Category getCategoryByName(String name){
        for(Category c : categories){
            if(c.getName().equals(name))
                return c;
        }
        return null;
    }

    public void addCategory(Category category) throws FileNotFoundException, IOException{
        categories.add(category);
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Data\\employees.dat",true));
        outputStream.writeObject(categories.get(categories.indexOf(category)));
    }

    public void removeCategory(String name){
        for(Category c : categories ){
            if (String.valueOf(c.getName()).equals(name)){
                categories.remove(categories.indexOf(c));
            }
        }
    }

    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException{
        outputStream.defaultWriteObject();
        outputStream.writeInt(this.categories.size());
        for (Category c : categories)
            outputStream.writeObject(c);
        outputStream.writeInt(this.managers.size());
        for (Manager m : managers)
            outputStream.writeObject(m);
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
        int size1 = inputStream.readInt();
        ListProperty<Category> categoryList = new SimpleListProperty<>(FXCollections.observableArrayList());
        for (int i = 0; i < size1; i++) {
            categoryList.add((Category) inputStream.readObject());
        }
        this.categories = categoryList;

        int size2 = inputStream.readInt();
        ListProperty<Manager> managersList = new SimpleListProperty<>(FXCollections.observableArrayList());
        for (int i = 0; i < size2; i++) {
            managersList.add((Manager) inputStream.readObject());
        }
        this.managers = managersList;
    }
}
