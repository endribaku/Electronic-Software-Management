package DAO;

import Models.Category;
import Models.Item;

import java.io.*;
import java.util.ArrayList;

public class CategoryFileHandler implements CategoryDao {

    @Override
    public void insertCategory(Category category) throws IOException, ClassNotFoundException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Data\\categories.dat",true));
        outputStream.writeObject(category);
    }

    @Override
    public void updateCategory(Category category) throws IOException, ClassNotFoundException {
        ArrayList<Category> updatedList = selectAllCategories();

        for(Category c : updatedList){
            c.setName(category.getName());
        }

        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Data\\categories.dat",true));){
            for(Category c : updatedList){
                outputStream.writeObject(c);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteCategory(String categoryname) throws IOException, ClassNotFoundException {
        ArrayList<Category> updatedList = selectAllCategories();

        for(Category c : updatedList){
            if(c.getName().equals(categoryname))
                updatedList.remove(c);
        }

        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Data\\categories.dat",true));){
            for(Category c : updatedList){
                outputStream.writeObject(c);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Category selectCateogry(String categoryname) throws IOException, ClassNotFoundException {
        try(ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream("Data\\categories.dat"));){
            while (true){
                if(inputStream.readObject() instanceof Category)
                    if(((Category) inputStream.readObject()).getName().equals(categoryname))
                        return (Category) inputStream.readObject();
            }
        }catch (EOFException e){
            System.out.println("Entire file has been traversed.");
        }
        return null;
    }

    @Override
    public ArrayList<Category> selectAllCategories() throws IOException, ClassNotFoundException {
        ArrayList<Category> categoriesFound = new ArrayList<>();
        try(ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream("Data\\categories.dat"));){
            while (true){
                if(inputStream.readObject() instanceof Category)
                    categoriesFound.add((Category) inputStream.readObject());
            }
        }catch (EOFException e){
            System.out.println("Entire file has been traversed.");
        }
        return categoriesFound;
    }
}
