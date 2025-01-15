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

    public void addItem(Item item) {

        for (Category c: inventory.getCategories())
        {
            if(item.getCategory().getName().equals(c.getName()))
            {
                c.addItem(item);
                return;
            }
        }

        //If Category was not found
        System.out.println("Category not found for item to be added");

    }

    public void addCategory(Category category) {
        inventory.getCategories().add(category);
    }

    public void removeSupplier(String supplierName) {
        for (Supplier s : suppliers) {
            if (s.getName().equals(supplierName)) {
                suppliers.remove(s);
                return;
            }
        }
        System.out.println("Supplier name was not found");
    }

    public void removeSector(String sectorName) {
        for (Sector s : sectors) {
            if (s.getSectorName().equals(sectorName))
            {
                sectors.remove(s);
                return;
            }
        }
        System.out.println("Sector name was not found");
    }

    public void removeCategory(String categoryName) {

        ArrayList<Category> categoriesIterator = inventory.getCategories();
        for (Category c : categoriesIterator) {
            if(c.getName().equals(categoryName))
            {
                inventory.getCategories().remove(c);
                return;
            }
        }
        System.out.println("Category name was not found");
    }

    public void restockProduct(String name, int quantity) {
        //Finding item by name

        for (Category c : inventory.getCategories()) {
            for(Item i : c.getItems())
            {
                if(i.getName().equals(name))
                {
                    i.setQuantity(i.getQuantity() + quantity);
                    return;
                }
            }
        }

        System.out.println("Item name was not found");
    }

    public void monitorCashierPerformance(String cashierName) {
        // Finding cashier
        Cashier cashier = null;

        for (Sector sector : sectors) {
            ArrayList<Cashier> cashiers = new ArrayList<Cashier>();

            for (Cashier c : sector.getCashiers()) {
                if (c.getUsername().equals(cashierName)) {
                    cashier = c;
                    break;
                }
            }
            if (cashier != null) break;
        }

        if (cashier == null) return;

        //Monitor cashier performance by getting information from files
    }

    public void viewSalesStatistics(String period) {

    }

    public void checkCategoryRestockingAlerts() {
        for (Category category : inventory.getCategories())
        {
            if(category.needsRestocking())
            {
                System.out.println("Category " + category.getName() + " needs restocking.");
            }
        }
    }


    public void checkItemRestockingAlerts() {
        for(Category category : inventory.getCategories())
        {
            for(Item item : category.getItems())
            {
                if(item.getQuantity() < 5)
                {
                    System.out.println("Item " + item.getName() + " needs restocking." + "Item Quantity: " + item.getQuantity());
                }
            }
        }
    }

    public String notifyManager(String message) {
        return "Notification sent to Manager: " + message;
    }

    public void overseeInventory() {

    }

    public void removeItem(String itemName) {
        for (Category category : inventory.getCategories()) {
            ArrayList<Item> items = category.getItems(); 
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getName().equals(itemName)) {
                    items.remove(i);
                    System.out.println("Item " + itemName + " has been removed.");
                    return;
                }
            }
        }
        System.out.println("Item " + itemName + " not found.");
    }
}
