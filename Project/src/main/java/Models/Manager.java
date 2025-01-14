package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Manager extends User implements Serializable {

    private String ManagerID;
    private ArrayList<Sector> sectors;
    private Inventory inventory;
    private ArrayList<Supplier> suppliers;

    public Manager (String username, String password, String fullName, Date dateOfBirth, String phoneNumber, String email, double salary) {
        super(username, password, fullName, dateOfBirth, phoneNumber, email, salary, Access.Manager);
    }

    public String getManagerID() {
        return ManagerID;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public ArrayList<Sector> getSectors() {
        return sectors;
    }

    public ArrayList<Supplier> getSuppliers() {
        return suppliers;
    }

    public void addSector(Sector sector) {

    }

    public void addSuppliers(Supplier supplier) {

    }

    public void addItems(Item items) {

    }

    public void addCategory(Category category) {

    }

    public void removeSupplier(Supplier supplier) {

    }

    public void removeSector(Sector sector) {

    }

    public void removeCategory(Category category) {

    }

    public void restockProduct(String name, int quantity) {

    }

    public void monitorCashierPerformance(String cashierName) {

    }

    public void viewSalesStatistics(String period) {

    }

    public void checkRestockingAlerts() {

    }

    public void notifyManager() {

    }

    public void overseeInventory() {

    }
}
