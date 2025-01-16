package Models;

import javafx.beans.property.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Item implements Serializable {
    private transient StringProperty itemID;
    private transient StringProperty name;
    private transient ObjectProperty<Category> category;
    private transient StringProperty supplier;
    private transient ObjectProperty<Date> purchaseDate;
    private transient DoubleProperty purchasePrice;
    private transient DoubleProperty sellingPrice;
    private transient IntegerProperty quantity;

    public Item(String name, Category category,
                String supplier, Date purchaseDate,
                double purchasePrice, double sellingPrice, int quantity) {
        this.itemID = new SimpleStringProperty(UUID.randomUUID().toString());
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleObjectProperty<>(category);
        this.supplier = new SimpleStringProperty(supplier);
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

    public String getSupplier() {
        return supplier.get();
    }

    public void setSupplier(String supplier) {
        this.supplier.set(supplier);
    }

    public Date getPurchaseDate() {
        return purchaseDate.get();
    }

    public void setPurchaseDate(Date purchaseDate) {
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
}
