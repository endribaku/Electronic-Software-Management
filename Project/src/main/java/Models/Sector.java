package Models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class Sector implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient StringProperty sectorName;
    private transient ListProperty<Category> categories = new SimpleListProperty<>(FXCollections.observableArrayList());


    public Sector(String sectorName, ArrayList<Category> categories) {
        this.sectorName = new SimpleStringProperty(sectorName);
        this.categories.set(FXCollections.observableArrayList(categories));
    }

    public Sector() {
        this.sectorName = new SimpleStringProperty();
        this.categories = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

    public String getSectorName() {return sectorName.get();}
    public StringProperty sectorNameProperty() {return sectorName;}

    public void setSectorName(String sectorName) {this.sectorName.set(sectorName);}

    public void addCategory(Category category)
    {
        categories.add(category);
    }

    public void removeCategory(Category category)
    {
        categories.remove(category);
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

    public Category getCategoryByName(String categoryName)
    {
        for(Category c : categories.get())
        {
            if(c.getName().equals(categoryName))
            {
                return c;
            }
        }
        return null;
    }

    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeUTF(this.sectorName.getValueSafe());
        outputStream.writeInt(this.categories.size());
        for(Category c : categories) {
            outputStream.writeObject(c);
        }
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
        this.sectorName = new SimpleStringProperty(inputStream.readUTF());
        int size = inputStream.readInt();
        ListProperty<Category> categoryList = new SimpleListProperty<>(FXCollections.observableArrayList());
        for (int i = 0; i < size; i++) {
            categoryList.add((Category) inputStream.readObject());
        }
        this.categories = categoryList;
    }

    @Override
    public String toString() {
        return sectorName.getValueSafe();
    }
}
