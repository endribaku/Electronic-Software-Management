package Main;


import Controllers.LoginController;
import DAO.InventoryFileHandler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import Models.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        File file = new File("Project/Data/employees.dat");
        System.out.println("File exists: " + file.exists());
        Scene scene = new LoginController(stage).getView().getApplication();
        stage.setTitle("Store Management");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
//        // Create Items
//        Item item1 = new Item(
//                "Laptop",
//                "Electronics",
//                "TechSupplier Inc.",
//                LocalDate.of(2025, 1, 1),
//                800.00,
//                1200.00,
//                10
//        );
//
//        Item item2 = new Item(
//                "Headphones",
//                "Electronics",
//                "AudioWorld Co.",
//                LocalDate.of(2025, 1, 2),
//                50.00,
//                80.00,
//                25
//        );
//
//        Item item3 = new Item(
//                "Apple",
//                "Groceries",
//                "FreshFarms",
//                LocalDate.of(2025, 1, 3),
//                0.50,
//                1.00,
//                50
//        );
//
//        Item item4 = new Item(
//                "Milk",
//                "Groceries",
//                "DairySupplies Ltd.",
//                LocalDate.of(2025, 1, 4),
//                1.20,
//                2.00,
//                20
//        );
//
//        // Create Categories
//        ArrayList<Item> electronicsItems = new ArrayList<>();
//        electronicsItems.add(item1);
//        electronicsItems.add(item2);
//
//        Category electronics = new Category("Electronics", electronicsItems);
//
//        ArrayList<Item> groceriesItems = new ArrayList<>();
//        groceriesItems.add(item3);
//        groceriesItems.add(item4);
//
//        Category groceries = new Category("Groceries", groceriesItems);
//
//        // Create Sectors
//        ArrayList<Category> retailCategories = new ArrayList<>();
//        retailCategories.add(electronics);
//        retailCategories.add(groceries);
//
//        Sector retail = new Sector("Retail", retailCategories);
//
//        ArrayList<Category> servicesCategories = new ArrayList<>();
//        Sector services = new Sector("Services", servicesCategories); // Empty category list for Services
//
//        // Add Sectors to Inventory
//        ArrayList<Sector> inventorySectors = new ArrayList<>();
//        inventorySectors.add(retail);
//        inventorySectors.add(services);
//
//        Inventory inventory = new Inventory();
//        inventory.setSectors(FXCollections.observableArrayList(inventorySectors));
//
//        InventoryFileHandler.setInventory(inventory);
//        System.out.println("Inventory setup complete:");
//        System.out.println("Sectors: " + inventory.getSectors());
//        System.out.println("Categories in Retail Sector: " + retail.getCategories());

        launch(args);
    }
}