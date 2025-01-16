package DAO;

import Models.Category;
import Models.Inventory;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class InventoryFileHandler {
    public static final String FILE_PATH = "Project/Data/inventory.dat";
    private static final File DATA_FILE = new File(FILE_PATH);
    private static ObservableList<Category> inventoryList = FXCollections.observableArrayList();

    public ObservableList<Category> getInventory(){
        if(inventoryList == null)
            selectAllInventory();
        return inventoryList;
    }

    public void insertInventory(Inventory inventory) {
        try(FileOutputStream outputStream = new FileOutputStream(DATA_FILE, true)) {
            ObjectOutputStream writer;
            if (DATA_FILE.length() > 0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(inventory);
        } catch(IOException ioe) {
            ioe.getMessage();
        }
    }

    public void deleteCategory(Category category) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for(Category c : inventoryList) {
                if(!c.equals(category))
                    outputStream.writeObject(c);
            }
            inventoryList.remove(category);
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    public void deleteAll(ArrayList<Category> categoriesToRemove) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))){
            for(Category c : categoriesToRemove) {
                if (!categoriesToRemove.contains(c)) {
                    outputStream.writeObject(c);
                }
            }
            inventoryList.removeAll(categoriesToRemove);
        } catch(IOException ex) {
            ex.getMessage();
        }
    }

    public boolean updateAll() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for(Category c : inventoryList) {
                outputStream.writeObject(c);
            }
            return true;
        } catch (IOException ex) {
            ex.getMessage();
            return false;
        }
    }

    public void selectAllInventory() {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            while(true) {
                Category category = (Category) reader.readObject();
                inventoryList.add(category);
            }
        }
        catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
