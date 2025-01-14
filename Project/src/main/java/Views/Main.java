package Views;

import Views.AdministratorInterface.AHomePage;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //hap user files edhe i bo read & save in arraylist
        //new LoginPage().start(stage);
        new AHomePage().start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}