package DAO;

import Models.Item;
import Models.User;

import java.io.*;
import java.util.ArrayList;

public class ItemFIleHandler implements ItemDao {

    @Override
    public void insertItem(Item item) throws IOException, ClassNotFoundException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Data\\items.dat",true));
        outputStream.writeObject(item);
    }

    @Override
    public void updateItem(Item item) throws IOException, ClassNotFoundException {
        ArrayList<Item> updatedList = selectAllItems();

        for(Item t : updatedList){
            t.setName(item.getName());
            t.setCategory(item.getCategory());
            t.setSupplier(item.getSupplier());
            t.setPurchaseDate(item.getPurchaseDate());
            t.setPurchasePrice(item.getPurchasePrice());
            t.setSellingPrice(item.getSellingPrice());
            t.setQuantity(item.getQuantity());

        }

        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Data\\items.dat",true));){
            for(Item t : updatedList){
                outputStream.writeObject(t);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteItem(String itemname) throws IOException, ClassNotFoundException {
        ArrayList<Item> updatedList = selectAllItems();

        for(Item t : updatedList){
            if(t.getName().equals(itemname))
                updatedList.remove(t);
        }

        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Data\\items.dat",true));){
            for(Item t : updatedList){
                outputStream.writeObject(t);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Item selectItem(String itemname) throws IOException, ClassNotFoundException {
        try(ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream("Data\\items.dat"));){
            while (true){
                if(inputStream.readObject() instanceof Item)
                    if(((Item) inputStream.readObject()).getName().equals(itemname))
                        return (Item) inputStream.readObject();
            }
        }catch (EOFException e){
            System.out.println("Entire file has been traversed.");
        }
        return null;
    }

    @Override
    public ArrayList<Item> selectAllItems() throws IOException, ClassNotFoundException {
        ArrayList<Item> itemsFound = new ArrayList<>();
        try(ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream("Data\\items.dat"));){
            while (true){
                if(inputStream.readObject() instanceof Item)
                    itemsFound.add((Item) inputStream.readObject());
            }
        }catch (EOFException e){
            System.out.println("Entire file has been traversed.");
        }
        return itemsFound;
    }
}
