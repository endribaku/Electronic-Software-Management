package DAO;

import Models.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.List;

public class ItemFileHandler {

    public static final String FILE_PATH = "Project/Data/items.dat";
    private static final File DATA_FILE = new File(FILE_PATH);

    private final ObservableList<Item> items = FXCollections.observableArrayList();

    public ObservableList<Item> getAllItems() {
        if (items.isEmpty()) {
            selectAllItems();
        }
        return items;
    }

    public void insertItem(Item item) {
        items.add(item);
        updateAll();
    }

    public void deleteItem(Item item) {
        items.remove(item);
        updateAll();
    }

    public void deleteAll(List<Item> itemsToRemove) {
        items.removeAll(itemsToRemove);
        updateAll();
    }

    public boolean updateAll() {
        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for (Item i : items) {
                outputStream.writeObject(i);
            }
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public Item selectItem(String itemName) {
        selectAllItems();
        return items.stream()
                .filter(i -> i.getName().equals(itemName))
                .findFirst()
                .orElse(null);
    }

    public void selectAllItems() {
        items.clear();
        if (!DATA_FILE.exists()) {
            return;
        }
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {

            boolean endOfFile = false;

            while (!endOfFile) {
                try {
                    items.add((Item) reader.readObject());
                } catch (EOFException _) {
                    endOfFile = true;
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}