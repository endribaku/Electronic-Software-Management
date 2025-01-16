package DAO;

import Models.Item;
import Models.User;

import java.io.IOException;
import java.util.ArrayList;

public interface ItemDao {
    abstract void insertItem(Item item) throws IOException, ClassNotFoundException;

    abstract void updateItem(Item item) throws IOException, ClassNotFoundException;

    abstract void deleteItem(String itemname) throws IOException, ClassNotFoundException;

    abstract Item selectItem(String itemname) throws IOException,ClassNotFoundException;

    abstract ArrayList<Item> selectAllItems() throws IOException, ClassNotFoundException;
}
