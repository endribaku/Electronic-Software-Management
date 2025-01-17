package Models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class Category implements Comparable<Category> , Serializable {
    private transient StringProperty name;
    private transient ListProperty<Item> items;
    private static int stockThreshold = 5;

    public Category()  {
        this.name = new SimpleStringProperty();
        this.items = new SimpleListProperty<>(FXCollections.observableArrayList());
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
    public int getStockThreshold() {return stockThreshold;}
    public ObservableList<Item> getItemsProperty() {return items.get();}


    public void setName(String name) {this.name.set(name);}

    public void setItems(ObservableList<Item> items) {this.items.set(items);}


    public boolean needsRestocking(){
        int stock = 0;
        for(Item i : items){
            stock += i.getQuantity();
        }
        if(stock < stockThreshold){
            System.out.println("Needs restocking.");

            return true;
        }

        return false;
    }

    public void addItem(Item item){
        items.add(item);
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
        int sie = inputStream.readInt();
        ListProperty<Item> itemList = new SimpleListProperty<>(FXCollections.observableArrayList());
        for (int i = 0; i < sie; i++) {
            itemList.add((Item) inputStream.readObject());
        }
        this.items = itemList;
    }
}
