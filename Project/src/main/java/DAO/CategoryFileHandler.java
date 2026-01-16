package DAO;

import Interfaces.DAO.ICategoryFileHandler;
import Models.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.List;

public class CategoryFileHandler implements ICategoryFileHandler {

    private final File dataFile;
    private final ObservableList<Category> categories = FXCollections.observableArrayList();

    public CategoryFileHandler() {
        this(new File("Project/Data/categories.dat"));
    }

    public CategoryFileHandler(File dataFile) {
        this.dataFile = dataFile;
    }

    public ObservableList<Category> getAllCategories() {
        if (categories.isEmpty()) {
            selectAllCategories();
        }
        return categories;
    }

    public void insertCategory(Category category) {
        try (FileOutputStream outputStream = new FileOutputStream(dataFile, true)) {
            ObjectOutputStream writer;
            if (dataFile.length() > 0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(category);
            categories.add(category);
        } catch (IOException ioe) {
            ioe.getMessage();
        }
    }

    public void deleteCategory(Category category) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            categories.remove(category);
            for (Category c : categories) {
                outputStream.writeObject(c);
            }
        } catch (EOFException _) {
            // End of file reached: normal termination of object stream
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteAll(List<Category> categoriesToRemove) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            for (Category c : categories) {
                if (!categoriesToRemove.contains(c)) {
                    outputStream.writeObject(c);
                }
            }
            categories.removeAll(categoriesToRemove);
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    public boolean updateAll() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            for (Category c : categories) {
                outputStream.writeObject(c);
            }
            return true;
        } catch (IOException ex) {
            ex.getMessage();
            return false;
        }
    }

    public Category selectCategory(String categoryName) {
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(dataFile))) {
            Category category;
            while (true) {
                category = (Category) reader.readObject();
                if (category.getName().equals(categoryName))
                    return category;
            }
        } catch (EOFException _) {
            // End of file reached: normal termination of object stream
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void selectAllCategories() {
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(dataFile))) {
            while (true) {
                Category category = (Category) reader.readObject();
                categories.add(category);
            }
        } catch (EOFException _) {
            // End of file reached: normal termination of object stream
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
