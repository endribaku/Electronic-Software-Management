package Models;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Category implements Comparable<Category>{
    String name;
    ArrayList<Item> items;

    public Category() throws ClassNotFoundException, IOException {
        try(ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream("Data\\items.dat"));){
            while (true){
                Item item = (Item) inputStream.readObject();
                if(item.getCategory().compareTo(this) == 0)
                    items.add(item);
            }
        }catch (EOFException e){
            System.out.println("All items for the specific category have been loaded successfully.");
        }
    }

    @Override
    public int compareTo(Category other) {
        if (this.getName().equals(other.getName()))
            return 0;
        return 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean needsRestocking(){
        int stock = 0;
        for(Item i : items){
            stock += i.getQuantity();
        }
        if(stock < 5){
            System.out.println("Needs restocking.");
            return true;
        }

        return false;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public Item getItemByName(String name){
        for(Item i:items){
            if(i.getName().equals(name))
                return i;
        }
        return null;
    }
}
