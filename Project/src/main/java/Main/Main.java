package Main;


import Controllers.AdminController;
import DAO.CategoryFileHandler;
import Models.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        File file = new File("Project/Data/employees.dat");
        System.out.println("File exists: " + file.exists());
        Scene scene = new Scene(new AdminController().getView().getRoot(), 1500, 700); // Get the root node
        stage.setTitle("Country Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Category electronics = new Category("Electronics", new ArrayList<Item>());

        Item macbook = new Item("Macbook", electronics,
                    new Supplier("123", "Apple", new ArrayList<>()),
                    LocalDate.now(), 1000, 1200, 5);

        electronics.addItem(macbook);
        try
        {



        } catch (Exception e) {
            System.out.println("Error deleting category: " + e.getMessage());
        }


        launch(args);
    }
}