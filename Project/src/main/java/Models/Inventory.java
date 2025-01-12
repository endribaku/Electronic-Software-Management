package Models;

import java.io.*;
import java.util.ArrayList;

public class Inventory {
    ArrayList<Category> categories;

    public Inventory() throws ClassNotFoundException, IOException {
        try(ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream("Data\\categories.dat"));){
            while (true){
                categories.add((Category) inputStream.readObject());
            }
        }catch (EOFException e){
            System.out.println("All categories loaded successfully.");
        }
    }

    public ArrayList<Category> getCategories(){
        return categories;
    }

    public Category getCategoryByName(String name){
        for(Category c : categories){
            if(c.getName().equals(name))
                return c;
        }
        return null;
    }

    public void addCategory(Category category) throws FileNotFoundException, IOException{
        categories.add(category);
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Data\\employees.dat",true));
        outputStream.writeObject(categories.get(categories.indexOf(category)));
    }

    public void removeCategory(String name){
        for(Category c : categories ){
            if (String.valueOf(c.getName()).equals(name)){
                categories.remove(categories.indexOf(c));
            }
        }
    }
}
