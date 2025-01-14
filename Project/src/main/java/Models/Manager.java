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
        sectors.add(sector);
    }

    public void addSuppliers(Supplier supplier) {
        suppliers.add(supplier);
    }

    public void addItems(Item items) {
        if(inventory != null){
            for (Category c : inventory.getCategories()){
                if(items.getCategoryName().equals(c.getName()))
                    if(c.getItemByName(items.getName()) == null)
                        c.addItem(items);
                    else if(c.getItemByName(items.getName()) != null)
                        restockProduct(items, items.getQuantity());
            }
        }
    }

    public void addCategory(Category category) {
        inventory.getCategories().add(category);
    }

    public void removeSupplier(String supplierName) {
        for (Supplier s : suppliers){
            if(s.getName().equals(supplierName)){
                suppliers.remove(s);
                break;
            }
        }
    }

    public void removeSector(String sectorName) {
        for (Sector s: sectors){
            if(s.getSectorName().equals(sectorName))
                sectors.remove(s);
                break;
        }
    }

    public void removeCategory(Category category) {
        inventory.getCategories().remove(category);
    }

    public void restockProduct(Item items, int quantity) {
        if (inventory != null) {
            for (Category c : inventory.getCategories()) {
                if (items.getCategoryName().equals(c.getName()))
                    if (c.getItemByName(items.getName()) != null) {
                        Item itemInInventory = c.getItemByName(items.getName());
                        if (itemInInventory != null) {
                            itemInInventory.setQuantity(itemInInventory.getQuantity() + quantity);
                            break;
                        } else {
                            System.out.println("Item not found");
                            break;
                        }
                    }
            }
        }
    }

    public void monitorCashierPerformance(String cashierName) {
        ArrayList<Cashier> cashiers;
        Cashier cashierFound = null;
        for (Sector s : sectors) {
            cashiers = s.getCashiers();
            for(Cashier c : cashiers){
                if (c.getFullName().equals(cashierName)) {
                    cashierFound = c;
                    //return c.getPerformance();
                    break;
                }
            }
            if(cashierFound != null)
                break;;
        }
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
