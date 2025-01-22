package Main;


import Controllers.LoginController;
import DAO.InventoryFileHandler;
import DAO.UserFileHandler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        // Create Items for Categories
        Item laptop = new Item("Laptop", "Electronics", "TechSupplier", LocalDate.now().minusDays(30), 500.0, 700.0, 25);
        Item smartphone = new Item("Smartphone", "Electronics", "TechSupplier", LocalDate.now().minusDays(20), 300.0, 500.0, 40);

        Item sofa = new Item("Sofa", "Furniture", "HomeSupplier", LocalDate.now().minusDays(60), 200.0, 350.0, 10);
        Item table = new Item("Table", "Furniture", "HomeSupplier", LocalDate.now().minusDays(50), 150.0, 250.0, 15);

        Item keyboard = new Item("Keyboard", "Electronics", "PeripheralSupplier", LocalDate.now().minusDays(15), 25.0, 40.0, 50);
        Item mouse = new Item("Mouse", "Electronics", "PeripheralSupplier", LocalDate.now().minusDays(10), 15.0, 25.0, 75);

        // Create Categories and Assign Items
        ArrayList<Item> electronicsItems = new ArrayList<>();
        electronicsItems.add(laptop);
        electronicsItems.add(smartphone);

        ArrayList<Item> furnitureItems = new ArrayList<>();
        furnitureItems.add(sofa);
        furnitureItems.add(table);

        ArrayList<Item> peripheralItems = new ArrayList<>();
        peripheralItems.add(keyboard);
        peripheralItems.add(mouse);

        Category electronics = new Category("Electronics", electronicsItems, "Technology");
        Category furniture = new Category("Furniture", furnitureItems, "Home");
        Category peripherals = new Category("Peripherals", peripheralItems, "Technology");

        // Create Sectors and Assign Categories
        ArrayList<Category> techCategories = new ArrayList<>();
        techCategories.add(electronics);
        techCategories.add(peripherals);

        ArrayList<Category> homeCategories = new ArrayList<>();
        homeCategories.add(furniture);

        Sector techSector = new Sector("Technology", techCategories);
        Sector homeSector = new Sector("Home", homeCategories);

        // Create Suppliers and Assign Items
        ArrayList<Item> techSupplierItems = new ArrayList<>();
        techSupplierItems.add(laptop);
        techSupplierItems.add(smartphone);

        ArrayList<Item> homeSupplierItems = new ArrayList<>();
        homeSupplierItems.add(sofa);
        homeSupplierItems.add(table);

        ArrayList<Item> peripheralSupplierItems = new ArrayList<>();
        peripheralSupplierItems.add(keyboard);
        peripheralSupplierItems.add(mouse);

        Supplier techSupplier = new Supplier("TechSupplier", techSupplierItems);
        Supplier homeSupplier = new Supplier("HomeSupplier", homeSupplierItems);
        Supplier peripheralSupplier = new Supplier("PeripheralSupplier", peripheralSupplierItems);

        // Create Inventory and Assign Sectors and Suppliers
        Inventory inventory = new Inventory();

        inventory.addSector(techSector);
        inventory.addSector(homeSector);

        inventory.addSupplier(techSupplier);
        inventory.addSupplier(homeSupplier);
        inventory.addSupplier(peripheralSupplier);

        // Print Inventory Details
        System.out.println("Inventory Sectors:");
        for (Sector sector : inventory.getSectors()) {
            System.out.println("Sector: " + sector.getSectorName());
            for (Category category : sector.getCategories()) {
                System.out.println("  Category: " + category.getName());
                for (Item item : category.getItems()) {
                    System.out.println("    Item: " + item.getName() + ", Quantity: " + item.getQuantity());
                }
            }
        }

        System.out.println("\nInventory Suppliers:");
        for (Supplier supplier : inventory.getSuppliers()) {
            System.out.println("Supplier: " + supplier.getName());
            for (Item item : supplier.getSuppliedItems()) {
                System.out.println("  Item: " + item.getName() + ", Quantity: " + item.getQuantity());
            }
        }

//        new InventoryFileHandler().setInventory(inventory);

        ObservableList<String> permissions = FXCollections.observableArrayList();
        permissions.add("INVENTORY_ACCESS");
        permissions.add("EMPLOYEE_MANAGEMENT");



        // Sample sectors
        List<String> sectors = new ArrayList<>();
        sectors.add("Sales");
        sectors.add("HR");

        // Sample Access Level (assuming you have an enum named Access with an ADMIN value)
        Access accessLevel = Access.Administrator;

        // Create the User object
        User newUser = new User(
                "john_doe",                        // Username
                "password123",                     // Password
                "John Doe",                        // Full name
                LocalDate.of(1990, 5, 15),         // Date of birth
                "555-1234",                        // Phone number
                "john.doe@example.com",            // Email
                75000.00,                          // Salary
                accessLevel,                       // Access level (ADMIN)
                permissions,                       // Permissions
                FXCollections.observableArrayList(sectors) // Sectors
        );





        launch(args);
    }
}