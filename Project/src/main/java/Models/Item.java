package Models;

import javafx.beans.property.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Item implements Serializable {
    private transient StringProperty itemID;
    private transient StringProperty name;
    private transient ObjectProperty<Category> category;
    private transient ObjectProperty<Supplier> supplier;
    private transient ObjectProperty<LocalDate> purchaseDate;
    private transient DoubleProperty purchasePrice;
    private transient DoubleProperty sellingPrice;
    private transient IntegerProperty quantity;

    public Item(String name, Category category,
                Supplier supplier, LocalDate purchaseDate,
                double purchasePrice, double sellingPrice, int quantity) {
        this.itemID = new SimpleStringProperty(UUID.randomUUID().toString());
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleObjectProperty<>(category);
        this.supplier = new SimpleObjectProperty(supplier);
        this.purchaseDate = new SimpleObjectProperty<>(purchaseDate);
        this.purchasePrice = new SimpleDoubleProperty(purchasePrice);
        this.sellingPrice = new SimpleDoubleProperty(sellingPrice);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public String getItemID() {
        return itemID.get();
    }

    public void setItemID(String itemID) {
        this.itemID.set(itemID);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Category getCategory() {
        return category.get();
    }

    public void setCategory(Category category) {
        this.category.set(category);
    }

    public Supplier getSupplier() {
        return supplier.get();
    }

    public void setSupplier(Supplier supplier) {
        this.supplier.set(supplier);
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate.get();
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate.set(purchaseDate);
    }

    public double getPurchasePrice() {
        return purchasePrice.get();
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice.set(purchasePrice);
    }

    public double getSellingPrice() {
        return sellingPrice.get();
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice.set(sellingPrice);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public void reduceStock(int quantityToReduce) {
        // Proceed to deduct the stock safely
        int getQuantity = getQuantity();
        getQuantity -= quantityToReduce;
        setQuantity(getQuantity);
    }

    @Override
    public String toString() {
        return getName();
    }

    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeUTF(this.itemID.getValueSafe());
        outputStream.writeUTF(this.name.getValueSafe());
        outputStream.writeObject(this.category.getValue());
        outputStream.writeObject(this.supplier.getValue());
        outputStream.writeObject(this.purchaseDate.getValue());
        outputStream.writeDouble(this.purchasePrice.getValue());
        outputStream.writeDouble(this.sellingPrice.getValue());
        outputStream.writeInt(this.quantity.getValue());
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
        this.itemID = new SimpleStringProperty(inputStream.readUTF());
        this.name = new SimpleStringProperty(inputStream.readUTF());
        this.category = new SimpleObjectProperty<>((Category) inputStream.readObject());
        this.supplier = new SimpleObjectProperty<>((Supplier) inputStream.readObject());
        this.purchaseDate = new SimpleObjectProperty<>((LocalDate) inputStream.readObject());
        this.purchasePrice = new SimpleDoubleProperty(inputStream.readDouble());
        this.sellingPrice = new SimpleDoubleProperty(inputStream.readDouble());
        this.quantity = new SimpleIntegerProperty(inputStream.readInt());
    }

    public void restock(int quantityToRestock) {
        int getQuantity = getQuantity();
        getQuantity += quantityToRestock;
        setQuantity(getQuantity);
    }
}
