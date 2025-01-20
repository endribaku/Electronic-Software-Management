package Models;

import javafx.beans.property.*;

import java.io.*;

public class Bill_Item implements Serializable {
    private transient StringProperty itemID;
    private transient StringProperty name;
    private transient ObjectProperty<Item> item;
    private transient IntegerProperty quantity;
    private transient DoubleProperty unitPrice;
    private transient DoubleProperty totalPrice;

    public Bill_Item(Item item, int quantity) {
        this.item = new SimpleObjectProperty<Item>(item);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.unitPrice = new SimpleDoubleProperty(item.getSellingPrice());
        this.totalPrice = new SimpleDoubleProperty(getQuantity() * getUnitPrice());
        this.itemID = new SimpleStringProperty(item.getItemID());
        this.name = new SimpleStringProperty(item.getName());
    }

    public Bill_Item(Item item) {
        this.item = new SimpleObjectProperty<Item>(item);
        this.quantity = new SimpleIntegerProperty();
        this.unitPrice = new SimpleDoubleProperty();
        this.totalPrice = new SimpleDoubleProperty();
    }

    public Item getItem() {
        return item.get();
    }

    public ObjectProperty<Item> itemProperty() {
        return item;
    }

    public void setItem(Item item) {
        this.item.set(item);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public double getUnitPrice() {
        return unitPrice.get();
    }

    public DoubleProperty unitPriceProperty() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice.set(unitPrice);
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public String getItemID() {
        return itemID.get();
    }

    public StringProperty itemIDProperty() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID.set(itemID);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @Serial
    private void readObject(ObjectInputStream reader) throws IOException, ClassNotFoundException {
        reader.defaultReadObject();
        this.item = new SimpleObjectProperty<>((Item) reader.readObject());
        this.quantity = new SimpleIntegerProperty(reader.readInt());
        this.unitPrice = new SimpleDoubleProperty(reader.readDouble());
        this.totalPrice = new SimpleDoubleProperty(reader.readDouble());
        this.itemID = new SimpleStringProperty(reader.readUTF());
        this.name = new SimpleStringProperty(reader.readUTF());
    }

    @Serial
    private void writeObject(ObjectOutputStream writer) throws IOException {
        writer.defaultWriteObject();
        writer.writeObject(this.item.get());
        writer.writeInt(this.quantity.get());
        writer.writeDouble(this.unitPrice.get());
        writer.writeDouble(this.totalPrice.get());
        writer.writeUTF(this.itemID.getValueSafe());
        writer.writeUTF(this.name.getValueSafe());
    }

    @Override
    public String toString() {
        return getItem().getName();
    }
}
