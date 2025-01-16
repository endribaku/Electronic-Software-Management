package DAO;

import Models.Category;
import Models.Inventory;
import Models.Supplier;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class InventoryFileHandler {
    public static final String FILE_PATH = "Project/Data/inventory.dat";
    private static final File DATA_FILE = new File(FILE_PATH);
    private static Inventory inventory = null;

    public Inventory getInventory(){
        if(inventory == null)
            selectInventory();
        return inventory;
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

    public void selectInventory() {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))){
            inventory = (Inventory) reader.readObject();
        }catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
