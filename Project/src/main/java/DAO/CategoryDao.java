package DAO;

import Models.Category;
import Models.Item;

import java.io.IOException;
import java.util.ArrayList;

public interface CategoryDao {
    abstract void insertCategory(Category category) throws IOException, ClassNotFoundException;

    abstract void updateCategory(Category category) throws IOException, ClassNotFoundException;

    abstract void deleteCategory(String categoryname) throws IOException, ClassNotFoundException;

    abstract Category selectCateogry(String categoryname) throws IOException,ClassNotFoundException;

    abstract ArrayList<Category> selectAllCategories() throws IOException, ClassNotFoundException;
}
