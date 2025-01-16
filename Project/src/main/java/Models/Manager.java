package Models;

import DAO.CategoryFileHandler;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Manager extends User implements Serializable {

    private transient StringProperty ManagerID = new SimpleStringProperty();
    private transient ListProperty<Sector> sectors = new SimpleListProperty<>(FXCollections.observableArrayList());
    private transient ObjectProperty<Inventory> inventory = new SimpleObjectProperty<>();
    private transient ListProperty<Supplier> suppliers = new SimpleListProperty<>(FXCollections.observableArrayList());

    public Manager (String username, String password, String fullName, LocalDate dateOfBirth, String phoneNumber, String email, double salary) {
        super(username, password, fullName, dateOfBirth, phoneNumber, email, salary, Access.Manager);
    }


    //Getters and Setters
    public String getManagerID() {return ManagerID.get();}
    public Inventory getInventory() {return inventory.get();}
    public ObservableList<Sector> getSectors() {return sectors.get();}
    public ObservableList<Supplier> getSuppliers() {return suppliers.get();}

    public StringProperty managerIDProperty() {return ManagerID;}
    public ObjectProperty<Inventory> inventoryProperty() {return inventory;}
    public ListProperty<Sector> sectorsProperty() {return sectors;}
    public ListProperty<Supplier> suppliersProperty() {return suppliers;}

    public void setManagerID(String managerID) {this.ManagerID.set(managerID);}
    public void setInventory(Inventory inventory) {this.inventory.set(inventory);}
    public void setSectors(ArrayList<Sector> sectors) {this.sectors.set(FXCollections.observableArrayList(sectors));}
    public void setSuppliers(ArrayList<Supplier> suppliers) {this.suppliers.set(FXCollections.observableArrayList(suppliers));}





    public void addSector(Sector sector) {
        sectors.add(sector);
    }

    public void addSuppliers(Supplier supplier) {
        suppliers.add(supplier);
    }

    public void addItem(Item item) {

        for (Category c: inventory.get().getCategories())
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
        inventory.get().getCategories().add(category);
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
//        ArrayList<Category> categoriesIterator = inventory.get().getCategories();
//        for (Category c : categoriesIterator) {
//            if(c.getName().equals(categoryName))
//            {
//                inventory.get().getCategories().remove(c);
//                return;
//            }
//        }
//        System.out.println("Category name was not found");
        try
        {
            CategoryFileHandler categoryHandler = new CategoryFileHandler();
            Category category = categoryHandler.selectCategory(categoryName);
            categoryHandler.deleteCategory(category);
        }
        catch (Exception e)
        {
            System.out.println("Category couldn't be deleted");
        }

    }

    public void restockProduct(String name, int quantity) {
        //Finding item by name

//        for (Category c : inventory.getCategories()) {
//            for(Item i : c.getItems())
//            {
//                if(i.getName().equals(name))
//                {
//                    i.setQuantity(i.getQuantity() + quantity);
//                    return;
//                }
//            }
//        }
//
//        System.out.println("Item name was not found");
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
//        for (Category category : inventory.getCategories())
//        {
//            if(category.needsRestocking())
//            {
//                System.out.println("Category " + category.getName() + " needs restocking.");
//            }
//        }
    }


    public void checkItemRestockingAlerts() {
//        for(Category category : inventory.getCategories())
//        {
//            for(Item item : category.getItems())
//            {
//                if(item.getQuantity() < 5)
//                {
//                    System.out.println("Item " + item.getName() + " needs restocking." + "Item Quantity: " + item.getQuantity());
//                }
//            }
//        }
    }

    public String notifyManager(String message) {
        return "Notification sent to Manager: " + message;
    }

    public void overseeInventory() {

    }

    public void removeItem(String itemName) {
//        for (Category category : inventory.getCategories()) {
//            ArrayList<Item> items = category.getItems();
//            for (int i = 0; i < items.size(); i++) {
//                if (items.get(i).getName().equals(itemName)) {
//                    items.remove(i);
//                    System.out.println("Item " + itemName + " has been removed.");
//                    return;
//                }
//            }
//        }
//        System.out.println("Item " + itemName + " not found.");
    }
}
