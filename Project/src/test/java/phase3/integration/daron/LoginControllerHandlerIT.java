package phase3.integration.daron;

import Controllers.LoginController;
import DAO.UserFileHandler;
import Exceptions.InvalidCredentialsException;
import Interfaces.Views.ILoginView;
import Models.Access;
import Models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerHandlerIT {

    private static volatile boolean FX_STARTED = false;

    @BeforeAll
    static void initJavaFx() {
        if (FX_STARTED) return;
        synchronized (LoginControllerHandlerIT.class) {
            if (FX_STARTED) return;

            CountDownLatch latch = new CountDownLatch(1);
            try {
                Platform.startup(latch::countDown);
            } catch (IllegalStateException alreadyStarted) {
                latch.countDown();
            }
            await(latch, 5);
            FX_STARTED = true;
        }
    }

    private static void runOnFxAndWait(Runnable action) {
        if (Platform.isFxApplicationThread()) {
            action.run();
            return;
        }
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try { action.run(); }
            finally { latch.countDown(); }
        });
        await(latch, 5);
    }

    private static void await(CountDownLatch latch, int seconds) {
        try {
            if (!latch.await(seconds, TimeUnit.SECONDS)) {
                throw new AssertionError("Timeout waiting for JavaFX operation.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("Interrupted waiting for JavaFX operation.", e);
        }
    }

    @TempDir
    File tempDir;

    private File dataFile;

    @BeforeEach
    void setup() {
        dataFile = new File(tempDir, "employees.dat");
    }

    @Test
    void authenticate_validCredentials_returnsUser() {
        UserFileHandler handler = new UserFileHandler(dataFile);

        User u = new User(
                "daron",
                "1234",
                "Daron Delvina",
                LocalDate.of(2000, 1, 1),
                "0690000000",
                "daron@test.com",
                500.0,
                Access.Cashier,
                FXCollections.observableArrayList("INVENTORY_ACCESS"),
                FXCollections.observableArrayList("Sales")
        );
        handler.insertUser(u);

        SpyLoginViewStub view = new SpyLoginViewStub("daron", "1234");

        Stage stage = new Stage();
        stage.setScene(new Scene(new javafx.scene.layout.StackPane()));

        new LoginController(stage, view, handler);

        view.triggerLogin();

        assertNotNull(handler.authenticateUser("daron", "1234"));
        assertEquals("Login Successful", view.lastInfoTitle);
        assertEquals("Welcome, daron!", view.lastInfoMessage);
    }

    @Test
    void authenticate_invalidCredentials_returnsNull_andShowsError() {
        UserFileHandler handler = new UserFileHandler(dataFile);

        User u = new User(
                "daron",
                "1234",
                "Daron Delvina",
                LocalDate.of(2000, 1, 1),
                "0690000000",
                "daron@test.com",
                500.0,
                Access.Cashier,
                FXCollections.observableArrayList("INVENTORY_ACCESS"),
                FXCollections.observableArrayList("Sales")
        );
        handler.insertUser(u);

        SpyLoginViewStub view = new SpyLoginViewStub("daron", "WRONG");
        Stage stage = new Stage();
        Scene baseline = new Scene(new javafx.scene.layout.StackPane());
        stage.setScene(baseline);

        new LoginController(stage, view, handler);

        view.triggerLogin();

        assertNull(handler.authenticateUser("daron", "WRONG"));
        assertEquals("Login Failed", view.lastErrorTitle);
        assertEquals(InvalidCredentialsException.MESSAGE, view.lastErrorMessage);
        assertSame(baseline, stage.getScene(), "Scene should not change on invalid login");
    }

    @Test
    void authenticate_emptyFields_rejects_andShowsError() {
        UserFileHandler handler = new UserFileHandler(dataFile);

        SpyLoginViewStub view = new SpyLoginViewStub("   ", "   ");
        Stage stage = new Stage();
        Scene baseline = new Scene(new javafx.scene.layout.StackPane());
        stage.setScene(baseline);

        new LoginController(stage, view, handler);

        view.triggerLogin();

        assertEquals("Login Failed", view.lastErrorTitle);
        assertEquals(InvalidCredentialsException.MESSAGE, view.lastErrorMessage);
        assertSame(baseline, stage.getScene(), "Scene should not change on empty input");
    }

    static class SpyLoginViewStub implements ILoginView {
        private final String username;
        private final String password;
        private Runnable loginAction;

        String lastInfoTitle;
        String lastInfoMessage;
        String lastErrorTitle;
        String lastErrorMessage;

        SpyLoginViewStub(String username, String password) {
            this.username = username;
            this.password = password;
        }

        void triggerLogin() {
            assertNotNull(loginAction, "Login action should be registered");
            runOnFxAndWait(loginAction);
        }

        @Override public String getUsernameText() { return username; }
        @Override public String getPasswordText() { return password; }

        @Override
        public void onLogin(Runnable action) {
            this.loginAction = action;
        }

        @Override
        public void showInfo(String title, String message) {
            this.lastInfoTitle = title;
            this.lastInfoMessage = message;
        }

        @Override
        public void showError(String title, String message) {
            this.lastErrorTitle = title;
            this.lastErrorMessage = message;
        }

        @Override
        public javafx.scene.Scene getApplication() {
            return null;
        }
    }
}