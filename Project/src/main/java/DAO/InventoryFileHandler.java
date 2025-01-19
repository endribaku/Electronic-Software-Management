package DAO;

import Models.Item;
import Models.Category;
import Models.Inventory;
import Models.Sector;
import Models.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class InventoryFileHandler {
    public static final String FILE_PATH = "Project/Data/inventory.dat";
    private static final File DATA_FILE = new File(FILE_PATH);

    private static ObjectProperty<Inventory> inventory;
    private static ObservableList<Sector> sectorsList = FXCollections.observableArrayList();
    private static ObservableList<Category> categoriesList = FXCollections.observableArrayList();
    private static ObservableList<Item> itemsList = FXCollections.observableArrayList();

    public static boolean updateAll() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for(Sector s : sectorsList) {
                outputStream.writeObject(s);
            }
            return true;
        } catch (IOException ex) {
            ex.getMessage();
            return false;
        }
    }




    public static ObjectProperty<Inventory> getInventory() {
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

    public static void setInventory(Inventory inventory) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            writer.writeObject(inventory);
            System.out.println("Inventory successfully saved to file.");
        } catch (FileNotFoundException e) {
            System.err.println("Data file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error writing the inventory to file: " + e.getMessage());
        }
    }

    public static void updateInventory(Inventory inventory) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            writer.writeObject(inventory);
            System.out.println("Inventory successfully updated in file.");
        } catch (FileNotFoundException e) {
            System.err.println("Data file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error updating the inventory file: " + e.getMessage());
        }
    }

    public static void deleteInventory()
    {
        try (FileWriter writer = new FileWriter(DATA_FILE, false)) { // Overwrite file with empty content
            writer.write(""); // Empty the file
            System.out.println("Inventory file cleared successfully.");
        } catch (IOException e) {
            System.err.println("Error clearing the inventory file: " + e.getMessage());
        }
    }





    //Adders for subsections
    public static void addSector(Sector newSector) {

        if (inventory == null || inventory.get() == null) {
            inventory = getInventory();
            if (inventory.get() == null) {
                System.err.println("Inventory is null. Load or initialize it before adding sectors.");
                return;
            }
        }
        Inventory currentInventory = inventory.get();
        currentInventory.getSectors().add(newSector);
        sectorsList.add(newSector); // Update observable list
        updateInventory(currentInventory); // Persist changes
        System.out.println("Sector added and saved successfully.");
    }

    public static void addCategory(String sectorName, Category newCategory) {

        if (inventory == null || inventory.get() == null) {
            inventory = getInventory();
            if (inventory.get() == null) {
                System.err.println("Inventory is null. Load or initialize it before adding categories.");
                return;
            }
        }

        Inventory currentInventory = inventory.get();

        for(Sector s: sectorsList)
        {
            if(s.getSectorName().equals(sectorName))
            {
                s.addCategory(newCategory);
            }
        }


        categoriesList.add(newCategory); // Update observable list
        updateInventory(currentInventory); // Persist changes
        System.out.println("Category added and saved successfully.");
    }


    public static void addItem(Category targetCategory, Item newItem) {
        if (inventory == null || inventory.get() == null) {
            inventory = getInventory();
            if (inventory.get() == null) {
                System.err.println("Inventory is null. Load or initialize it before adding items.");
                return;
            }
        }

        Inventory currentInventory = inventory.get();
        boolean categoryFound = false;

        for (Sector sector : currentInventory.getSectors()) {
            if (sector.getCategories().contains(targetCategory)) {
                // Add the item to the category
                int categoryIndex = sector.getCategories().indexOf(targetCategory);
                sector.getCategories().get(categoryIndex).getItems().add(newItem);
                categoryFound = true;
                break;
            }
        }

        if (!categoryFound) {
            System.err.println("Category not found in the inventory. Please add the category first.");
            return;
        }

        itemsList.add(newItem);
        updateInventory(currentInventory);
        System.out.println("Item added and saved successfully.");
    }

    //Getters for subsections
    public static ObservableList<Sector> getSectorsList() {
        // Ensure inventory is initialized
        if (inventory == null || inventory.get() == null) {
            inventory = getInventory();
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

    public static ObservableList<Category> getCategoriesList() {

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

    public static ObservableList<Item> getItemsList() {
        // Ensure inventory is initialized
        if (inventory == null || inventory.get() == null) {
            inventory = getInventory();
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

    public static ObservableList<String> getSectorNames()
    {
        ObservableList<String> sectorNames = FXCollections.observableArrayList();
        for(Sector s: getSectorsList())
        {
            sectorNames.add(s.toString());
        }
        return sectorNames;
    }



}
