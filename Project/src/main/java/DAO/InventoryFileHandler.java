package DAO;

import Models.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InventoryFileHandler {
    public static final String FILE_PATH = "Project/Data/inventory.dat";
    private static final File DATA_FILE = new File(FILE_PATH);


    private static ObjectProperty<Inventory> inventory;
    private static ObservableList<Sector> sectorsList = FXCollections.observableArrayList();
    private static ObservableList<Category> categoriesList = FXCollections.observableArrayList();
    private static ObservableList<Item> itemsList = FXCollections.observableArrayList();
    private static ObservableList<Supplier> suppliersList = FXCollections.observableArrayList();
    private static ObservableList<Item> itemsSuppliedList = FXCollections.observableArrayList();

    public InventoryFileHandler() {
        inventory = getInventory();
    }


    public ObjectProperty<Inventory> getInventory() {
        ObjectProperty<Inventory> inventoryProperty = new SimpleObjectProperty<>();
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Inventory inventory = (Inventory) reader.readObject();
            inventoryProperty.set(inventory);
        } catch (FileNotFoundException e) {
            System.err.println("Data file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading the inventory file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found when reading the inventory object: " + e.getMessage());
        }
        return inventoryProperty;
    }

    public void setInventory(Inventory inventory) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            writer.writeObject(inventory);
            System.out.println("Inventory successfully saved to file.");
        } catch (FileNotFoundException e) {
            System.err.println("Data file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error writing the inventory to file: " + e.getMessage());
        }
    }

    public static ObservableList<Item> checkForLowStock() {
        ObservableList<Item> lowStockItems = FXCollections.observableArrayList();
        for(Item i : itemsList) {
            if(i.getQuantity() < 5) {
                lowStockItems.add(i);
            }
        }
        return lowStockItems;
    }

    public static boolean updateInventory(Inventory inventory) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            writer.writeObject(inventory);
            System.out.println("Inventory successfully updated in file.");
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("Data file not found: " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.err.println("Error updating the inventory file: " + e.getMessage());
            return false;
        }
    }

    public void deleteInventory() {
        try (FileWriter writer = new FileWriter(DATA_FILE, false)) {
            writer.write(""); // Empty the file
            System.out.println("Inventory file cleared successfully.");
        } catch (IOException e) {
            System.err.println("Error clearing the inventory file: " + e.getMessage());
        }
    }


    //Adders for subsections
    public void addSector(Sector newSector) {

        if (inventory == null || inventory.get() == null) {
            inventory = this.getInventory();
            if (inventory.get() == null) {
                System.err.println("Inventory is null. Load or initialize it before adding sectors.");
                return;
            }
        }
        Inventory currentInventory = inventory.get();
        currentInventory.getSectors().add(newSector);
        sectorsList.add(newSector);
        updateInventory(currentInventory);
        System.out.println("Sector added and saved successfully.");
    }

    public void addCategory(String sectorName, Category newCategory) {

        if (inventory == null || inventory.get() == null) {
            inventory = this.getInventory();
            if (inventory.get() == null) {
                System.err.println("Inventory is null. Load or initialize it before adding categories.");
                return;
            }
        }

        Inventory currentInventory = inventory.get();

        for (Sector s : sectorsList) {
            if (s.getSectorName().equals(sectorName)) {
                s.addCategory(newCategory);
            }
        }


        categoriesList.add(newCategory);
        updateInventory(currentInventory);
        System.out.println("Category added and saved successfully.");
    }

    public void addItem(Category targetCategory, Supplier targetSupplier, Item newItem) {
        if (inventory == null || inventory.get() == null) {
            inventory = this.getInventory();
            if (inventory.get() == null) {
                System.err.println("Inventory is null. Load or initialize it before adding items.");
                return;
            }
        }

        Inventory currentInventory = inventory.get();
        boolean categoryFound = false;

        for (Sector sector : currentInventory.getSectors()) {
            if (sector.getCategories().contains(targetCategory)) {
                int categoryIndex = sector.getCategories().indexOf(targetCategory);
                sector.getCategories().get(categoryIndex).getItems().add(newItem);
                categoryFound = true;
                break;
            }
        }

        boolean supplierFound = false;

        if (this.getSuppliersList().contains(targetSupplier)) {
            targetSupplier.getSuppliedItems().add(newItem);
            supplierFound = true;
        }

        if (!categoryFound) {
            System.err.println("Category not found in the inventory. Please add the category first.");
            return;
        }

        if (!supplierFound) {
            System.err.println("Supplier not found in the inventory. Please add the supplier first.");
            return;
        }

        itemsList.add(newItem);
        updateInventory(currentInventory);
        System.out.println("Item added and saved successfully.");
    }

    public void addSupplier(Supplier supplier)
    {
        if (inventory == null || inventory.get() == null) {
            inventory = this.getInventory();
            if (inventory.get() == null) {
                System.err.println("Inventory is null. Load or initialize it before adding items.");
                return;
            }
        }

        Inventory currentInventory = inventory.get();

        currentInventory.addSupplier(supplier);
        suppliersList.add(supplier);
        updateInventory(currentInventory);

        System.out.println("Supplier was added successfully.");
    }

    //Update methods for specific instances
    public boolean updateItem(String itemID, String itemName, String itemCategory, String itemSupplier, LocalDate date, double pPrice, double sPrice, int quantity) {
        boolean updated = false;
        Inventory currentInventory = inventory.get();

        // Update the specific item
        // Traverse the inventory to find the item
        for (Sector sector : currentInventory.getSectors()) {
            for (Category category : sector.getCategories()) {
                for (Item i : category.getItems()) {
                    if (i.getItemID().equals(itemID)) {
                        // Create the updated item
                        Item updatedItem = new Item(itemID, itemCategory, itemSupplier, date, pPrice, sPrice, quantity);
                        updatedItem.setName(itemName);

                        // Replace the old item with the updated one
                        category.getItems().set(category.getItems().indexOf(i), updatedItem);
                        itemsList.set(itemsList.indexOf(i), updatedItem);  // Update the ObservableList (for UI purposes)

                        System.out.println("Item updated successfully");
                        updated = true;
                        break;
                    }
                }
                if (updated) break;
            }
            if (updated) break;
        }

        // Write the updated list back to the file
        boolean saved = false;
        if (updated) {
            saved = updateInventory(currentInventory);
        }
        return (updated && saved);
    }



    public boolean updateCategory(Category category, String categoryName, String sectorName) {
        boolean updated = false;
        Inventory currentInventory = inventory.get();

        for (Sector sector : currentInventory.getSectors()) {
            for (Category c : sector.getCategories()) {
                if (c == category) {

                    sector.getCategories().remove(c);
                    c.setName(categoryName);
                    c.setSector(sectorName);


                    sector.getCategories().add(c);


                    for (Item i : c.getItems()) {
                        i.setCategory(categoryName);
                    }

                    // Update UI list
                    categoriesList.set(categoriesList.indexOf(c), c);

                    updated = true;
                    break;
                }
            }
            if (updated) break;
        }

        // Write the updated list back to the file
        boolean saved = false;
        if(updated) {
            saved = updateInventory(currentInventory);
        }
        return(updated && saved);
    }

    public boolean updateSector(Sector sector, String sectorName) {
        boolean updated = false;
        Inventory currentInventory = inventory.get();
        ObservableList<User> usersToChange = new UserFileHandler().getAllUsers();

        // Traverse the sectors and update the sector
        for (Sector s : currentInventory.getSectors()) {
            if (s == sector) {
                // Remove the old sector from the list
                currentInventory.getSectors().remove(s);

                // Update users that are affected by this sector change
                for (User u : usersToChange) {
                    for (String str : u.getSector()) {
                        if (str.equals(sector.getSectorName())) {
                            u.getSector().remove(str);
                            u.getSector().add(sectorName);  // Update user sector info
                        }
                    }
                }

                // Update categories within the sector
                for (Category c : s.getCategories()) {
                    c.setSector(sectorName);  // Update category's sector name
                }

                // Update sector's name and add it back to the list
                s.setSectorName(sectorName);
                currentInventory.getSectors().add(s);

                // Update UI list
                sectorsList.set(sectorsList.indexOf(s), s);

                updated = true;
                break;
            }
        }

        // Write the updated list back to the file
        boolean saved = false;
        if(updated) {
            saved = updateInventory(currentInventory);
            System.out.println("Sector saved to file");
        }
        return(updated && saved);
    }

    public boolean updateSupplier(String supplierID, String supplierName) {
        boolean updated = false;
        Inventory currentInventory = inventory.get();

        for (Supplier s : currentInventory.getSuppliers()) {
            if (s.getSupplierID().equals(supplierID)) {
                // Remove the old supplier and update the supplier's name
                currentInventory.getSuppliers().remove(s);
                s.setName(supplierName);

                // Update the supplied items to reflect the new supplier name
                for (Item i : s.getSuppliedItems()) {
                    i.setSupplier(supplierName);
                }

                // Add the updated supplier back to the list
                currentInventory.getSuppliers().add(s);

                // Update the suppliersList for UI purposes
                suppliersList.set(suppliersList.indexOf(s), s);

                updated = true;
                break;
            }
        }

        // Write the updated list back to the file
        boolean saved = false;
        if(updated) {
            saved = updateInventory(currentInventory);
        }
        return(updated && saved);
    }

    //Delete Methods
    public boolean deleteItem(Item item) {
        boolean deleted = false;
        Inventory currentInventory = inventory.get();

        // Traverse the sectors and categories to find and delete the item
        for (Sector sector : currentInventory.getSectors()) {
            for (Category category : sector.getCategories()) {
                if (category.getItems().contains(item)) {
                    // Remove the item from the category
                    category.getItems().remove(item);
                    deleted = true;
                    break;  // Once found, break out of the loop
                }
            }
            if (deleted) break;  // Stop once the item is deleted
        }

        // If the item was deleted, update the UI list and save the updated inventory
        if (deleted) {
            itemsList.remove(item);  // Remove from UI list
            return updateInventory(currentInventory);  // Persist the updated inventory
        }

        return false;
    }

    public void deleteCategory(Category category) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            categoriesList.remove(category);
            for(Category c : categoriesList) {
                outputStream.writeObject(c);
            }
        } catch(EOFException eofe) {

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteSector(Sector sector) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            sectorsList.remove(sector);
            for(Sector s : sectorsList) {
                outputStream.writeObject(s);
            }
        } catch(EOFException eofe) {

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteSupplier(Supplier supplier) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            suppliersList.remove(supplier);
            for(Supplier s : suppliersList) {
                outputStream.writeObject(s);
                System.out.println("Supplier deleted");
            }
        } catch(EOFException eofe) {

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    //Getters for subsections
    public ObservableList<Sector> getSectorsList() {
        // Ensure inventory is initialized
        if (inventory == null || inventory.get() == null) {
            inventory = this.getInventory();
            if (inventory.get() == null) {
                System.err.println("Inventory is null. Returning an empty sectors list.");
                return sectorsList;
            }
        }

        // Clear and populate the sectors list
        sectorsList.clear();
        sectorsList.addAll(inventory.get().getSectors());
        return sectorsList;
    }

    public ObservableList<String> getSectorNames() {
        ObservableList<String> sectorNames = FXCollections.observableArrayList();
        for (Sector s : getSectorsList()) {
            sectorNames.add(s.toString());
        }
        return sectorNames;
    }


    public ObservableList<Category> getCategoriesList() {

        if (inventory == null || inventory.get() == null) {
            inventory = getInventory();
            if (inventory.get() == null) {
                System.err.println("Inventory is null. Returning an empty categories list.");
                return categoriesList;
            }
        }
        categoriesList.clear();
        for (Sector sector : inventory.get().getSectors()) {
            categoriesList.addAll(sector.getCategories());
        }
        return categoriesList;
    }

    public ObservableList<Item> getItemsList() {
        // Ensure inventory is initialized
        if (inventory == null || inventory.get() == null) {
            inventory = this.getInventory();
            if (inventory.get() == null) {
                System.err.println("Inventory is null. Returning an empty items list.");
                return itemsList;
            }
        }

        // Clear and populate the items list
        itemsList.clear();
        for (Sector sector : inventory.get().getSectors()) {
            for (Category category : sector.getCategories()) {
                itemsList.addAll(category.getItems());
            }
        }
        return itemsList;
    }


    //To get all the subsections that the user operates
    public ObservableList<Category> getCategoriesOfUser(User user) {

        if (inventory == null || inventory.get() == null) {
            inventory = this.getInventory();
            if (inventory.get() == null) {
                System.err.println("Inventory is null. Returning an empty categories list.");
                return categoriesList;
            }
        }
        categoriesList.clear();
        for (Sector sector : getSectorsOfUser(user)) {
            categoriesList.addAll(sector.getCategories());
        }
        return categoriesList;
    }



    public ObservableList<Item> getItemsOfUser(User user) {

        if (inventory == null || inventory.get() == null) {
            inventory = getInventory();
            if (inventory.get() == null) {
                System.err.println("Inventory is null. Returning an empty items list.");
                return itemsList;
            }
        }


        itemsList.clear();
        for (Sector sector : getSectorsOfUser(user)) {
            for (Category category : sector.getCategories()) {
                itemsList.addAll(category.getItems());
            }
        }
        return itemsList;
    }

    public ObservableList<Sector> getSectorsOfUser(User user) {
        ObservableList<Sector> userSectorsList = FXCollections.observableArrayList();

        if (inventory == null || inventory.get() == null) {
            inventory = this.getInventory();
            if (inventory.get() == null) {
                System.err.println("Inventory is null. Returning an empty sectors list.");
                return userSectorsList;
            }
        }

        List<Sector> inventorySectors = inventory.get().getSectors();
        List<String> userSectors = user.getSector();

        sectorsList.clear();
        for (Sector sector : inventorySectors) {
            if (userSectors.contains(sector.toString())) {
                sectorsList.add(sector);
            }
        }

        return sectorsList;
    }


    public ObservableList<Supplier> getSuppliersList() {
        if (inventory == null || inventory.get() == null) {
            inventory = this.getInventory();
            if (inventory.get() == null) {
                System.err.println("Inventory is null. Returning an empty suppliers list.");
                return suppliersList;
            }
        }
        suppliersList.clear();
        for (Supplier supplier : inventory.get().getSuppliers()) {
            suppliersList.add(supplier);
        }

        return suppliersList;
    }

    public ObservableList<Item> getSuppliedItems()
    {
        ObservableList<Supplier> supplierList = getSuppliersList();
        itemsSuppliedList.clear();
        if(getSuppliersList() == null)
        {
            for(Supplier s: supplierList)
            {
                itemsSuppliedList.addAll(s.getSuppliedItems());
            }
        } else {
            return itemsSuppliedList;
        }
        return itemsSuppliedList;
    }

    public void updateInventoryFile() {
        Inventory currentInventory = this.getInventory().get();
        if (currentInventory != null) {
            this.updateInventory(currentInventory);
        } else {
            System.err.println("Failed to update inventory file: Inventory is null.");
        }
    }







}



