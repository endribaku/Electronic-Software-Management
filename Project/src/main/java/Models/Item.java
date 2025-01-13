package Models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Item implements Serializable, Cloneable {
    private String itemID;
    private String name;
    private Category category;
    private String supplier;
    private Date purchaseDate;
    private double purchasePrice;
    private double sellingPrice;
    private int quantity;

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

    public void setPurchaseDate(int day, int month, int year) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        this.purchaseDate = c.getTime();
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

    @Override
    public String toString() {
        return name + "     " + sellingPrice;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Item) {
            return this.itemID.equals(((Item)o).getItemID());
        }
        return false;
    }

    @Override
    public Object clone() {
        try {
            Item temp = (Item) super.clone();
            temp.purchaseDate = (Date)purchaseDate.clone();
            return temp;
        }
        catch(CloneNotSupportedException e) {
            return null;
        }
    }
}
