package Views;


import Controllers.AdminController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        File file = new File("Project/Data/employees.dat");
        System.out.println("File exists: " + file.exists());
        Scene scene = new Scene(new AdminController().getView().getRoot(), 1500, 700); // Get the root node
        stage.setTitle("Country Management");
        stage.setScene(scene);
        stage.show();
        new AdminController().getView().getRoot();
    }

    public static void main(String[] args) {
        launch(args);
    }
}