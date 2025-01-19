package DAO;

import Models.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class CategoryFileHandler {
    public static final String FILE_PATH = "Project/Data/categories.dat";
    private static final File DATA_FILE = new File(FILE_PATH);
    private final ObservableList<Category> categories = FXCollections.observableArrayList();

    public ObservableList<Category> getAllCategories() {
        if(categories.isEmpty()) {
            selectAllCategories();
        }
        return categories;
    }

    public void insertCategory(Category category){
        try(FileOutputStream outputStream = new FileOutputStream(DATA_FILE, true)) {
            ObjectOutputStream writer;
            if (DATA_FILE.length() > 0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(category);
            categories.add(category);
        } catch(IOException ioe) {
            ioe.getMessage();
        }
    }

    public void deleteCategory(Category category){
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            categories.remove(category);
            for(Category c : categories) {
                outputStream.writeObject(c);
            }
        } catch(EOFException eofe) {

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteAll(ArrayList<Category> categoriesToRemove) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))){
            for(Category c : categories) {
                if(!categoriesToRemove.contains(c)) {
                    outputStream.writeObject(c);
                }
            }
            categories.removeAll(categoriesToRemove);
        } catch(IOException ex) {
            ex.getMessage();
        }
    }

    public boolean updateAll() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for(Category c : categories) {
                outputStream.writeObject(c);
            }
            return true;
        } catch (IOException ex) {
            ex.getMessage();
            return false;
        }
    }

    public Category selectCategory(String categoryName){
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Category category;
            while(true) {
                category = (Category) reader.readObject();
                if(category.getName().equals(categoryName))
                    return category;
            }
        }
        catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void selectAllCategories() {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            while(true) {
                Category category = (Category) reader.readObject();
                categories.add(category);
            }
        }
        catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
