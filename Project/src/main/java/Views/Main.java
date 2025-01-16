package Views;


import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        File file = new File("Project/Data/employees.dat");
        System.out.println("File exists: " + file.exists());
        new LoginView().show(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}