package DAO;

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
    private static ObservableList<Sector> sectorsList = FXCollections.observableArrayList();
    private static ObjectProperty<Inventory> inventory;

//    public ObservableList<Category> getInventory(){
//        if(inventoryList == null)
//            selectAllInventory();
//        return inventoryList;
//    }
//
//    public void insertInventory(Inventory inventory) {
//        try(FileOutputStream outputStream = new FileOutputStream(DATA_FILE, true)) {
//            ObjectOutputStream writer;
//            if (DATA_FILE.length() > 0)
//                writer = new HeaderlessObjectOutputStream(outputStream);
//            else
//                writer = new ObjectOutputStream(outputStream);
//            writer.writeObject(inventory);
//        } catch(IOException ioe) {
//            ioe.getMessage();
//        }
//    }
//
//    public void deleteCategory(Category category) {
//        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
//            for(Category c : inventoryList) {
//                if(!c.equals(category))
//                    outputStream.writeObject(c);
//            }
//            inventoryList.remove(category);
//        } catch (IOException ex) {
//            ex.getMessage();
//        }
//    }
//
//    public void deleteAll(ArrayList<Category> categoriesToRemove) {
//        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))){
//            for(Category c : categoriesToRemove) {
//                if (!categoriesToRemove.contains(c)) {
//                    outputStream.writeObject(c);
//                }
//            }
//            inventoryList.removeAll(categoriesToRemove);
//        } catch(IOException ex) {
//            ex.getMessage();
//        }
//    }
//
//    public boolean updateAll() {
//        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
//            for(Category c : inventoryList) {
//                outputStream.writeObject(c);
//            }
//            return true;
//        } catch (IOException ex) {
//            ex.getMessage();
//            return false;
//        }
//    }
//
//    public void selectAllInventory() {
//        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
//            while(true) {
//                Category category = (Category) reader.readObject();
//                inventoryList.add(category);
//            }
//        }
//        catch (EOFException ignored) {
//        }
//        catch (IOException | ClassNotFoundException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }

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


    public static ObservableList<Sector> getSectorsList()
    {
        ObservableList<Sector> sectorsList = FXCollections.observableArrayList();

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Inventory inventory = (Inventory) reader.readObject();
            if (inventory != null) {
                sectorsList.addAll(inventory.getSectors());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Inventory file not found. Returning an empty sectors list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading inventory file: " + e.getMessage());
        }

        return sectorsList;
    }
}
