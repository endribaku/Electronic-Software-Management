package Models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class Category implements Comparable<Category> , Serializable {
    private static final long serialVersionUID = 1L;
    private transient StringProperty name;
    private transient ListProperty<Item> items;
    private transient StringProperty sector;

    public Category(String name, ArrayList<Item> items, String sector)  {
        this.name = new SimpleStringProperty(name);
        this.items = new SimpleListProperty<>(FXCollections.observableArrayList(items));
        this.sector = new SimpleStringProperty(sector);
    }

    public Category(String name, ArrayList<Item> items)  {
        this.name = new SimpleStringProperty(name);
        this.items = new SimpleListProperty<>(FXCollections.observableArrayList(items));
    }

    @Override
    public int compareTo(Category other) {
        if (this.getName().equals(other.getName()))
            return 0;
        return 1;
    }

    public String getName() {
        return name.get();
    }
    public ObservableList<Item> getItemsProperty() {
        return items.get();
    }



    public ObservableList<Item> getItems() {
        return items.get();
    }

    public ListProperty<Item> itemsProperty() {
        return items;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {this.name.set(name);}

    public void setItems(ObservableList<Item> items) {this.items.set(items);}

    public void addItem(Item item){
        items.add(item);
    }

    public void removeItem(Item item){ items.remove(item);}

    public Item getItemByName(String itemName) {
        for (Item i : items) {
            if (i.getName().equals(itemName)) {
                System.out.println("Item found in category: " + name);
                return i;
            }
        }
        return null;
    }

    public void restockItem(String itemName, int restockAmount) {
        Item item = getItemByName(itemName);
        if (item != null) {
            item.restock(restockAmount);
        } else {
            System.out.println("Item not found in category: " + name);
        }
    }


    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException{
        outputStream.defaultWriteObject();
        outputStream.writeUTF(this.name.getValueSafe());
        outputStream.writeInt(this.items.size());
        for (Item i : items)
            outputStream.writeObject(i);

    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
        this.name = new SimpleStringProperty(inputStream.readUTF());
        ListProperty<Item> itemList = new SimpleListProperty<>(FXCollections.observableArrayList());
        int size = inputStream.readInt();
        for (int i = 0; i < size; i++) {
            itemList.add((Item) inputStream.readObject());
        }
        this.items = itemList;

    }

    @Override
    public String toString() {
        return name.getValueSafe();
    }
}
