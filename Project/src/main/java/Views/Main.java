package Views;

import Views.AdministratorInterface.AHomePage;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //hap user files edhe i bo read & save in arraylist
        new LoginPage().show(stage);
        //new AHomePage().show(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}