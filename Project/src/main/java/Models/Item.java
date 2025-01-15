package Models;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {
    String itemID;
    String name;
    Category category;
    String supplier;
    Date purchaseDate;
    double purchasePrice;
    double sellingPrice;
    int quantity;

    public Item(String itemID, String name, Category category,
                String supplier, Date purchaseDate,
                double purchasePrice, double sellingPrice, int quantity) {
        this.itemID = itemID;
        this.name = name;
        this.category = category;
        this.supplier = supplier;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void reduceStock(int quantityToReduce) {
        // Proceed to deduct the stock safely
        quantity -= quantityToReduce;
    }
}
