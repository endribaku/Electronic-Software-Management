package DAO;

import Models.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class ItemFileHandler {
    public static final String FILE_PATH = "Project/Data/items.dat";
    private static final File DATA_FILE = new File(FILE_PATH);
    private final ObservableList<Item> items = FXCollections.observableArrayList();

    public ObservableList<Item> getAllItems() {
        if(items.isEmpty()) {
            selectAllItems();
        }
        return items;
    }


    public void insertItem(Item item){
        try(FileOutputStream outputStream = new FileOutputStream(DATA_FILE, true)) {
            ObjectOutputStream writer;
            if (DATA_FILE.length() > 0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(item);
            items.add(item);
        } catch(IOException ioe) {
            ioe.getMessage();
        }
    }

    public void deleteItem(Item item){
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            items.remove(item);
            for(Item i : items) {
                outputStream.writeObject(i);
            }
        } catch(EOFException eofe) {

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteAll(ArrayList<Item> itemsToRemove) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))){
            for(Item i : items) {
                if (!itemsToRemove.contains(i)) {
                    outputStream.writeObject(i);
                }
            }
            items.removeAll(itemsToRemove);
        } catch(IOException ex) {
            ex.getMessage();
        }
    }

    public boolean updateAll() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for(Item i : items) {
                outputStream.writeObject(i);
            }
            return true;
        } catch (IOException ex) {
            ex.getMessage();
            return false;
        }
    }

    public Item selectItem(String itemName){
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Item item;
            while(true) {
                item = (Item) reader.readObject();
                if(item.getName().equals(itemName))
                    return item;
            }
        }
        catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void selectAllItems() {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            while(true) {
                Item item = (Item) reader.readObject();
                items.add(item);
            }
        }
        catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
