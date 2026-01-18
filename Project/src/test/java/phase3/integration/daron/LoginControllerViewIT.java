package phase3.integration.daron;

import Controllers.LoginController;
import Interfaces.DAO.IUserFileHandler;
import Models.Access;
import Models.User;
import Views.LoginView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LoginControllerViewIT {

    private static volatile boolean FX_STARTED = false;

    @BeforeAll
    static void initJavaFx() {
        if (FX_STARTED) return;
        synchronized (LoginControllerViewIT.class) {
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

    private Stage stage;
    private LoginView realViewSpy;
    private IUserFileHandler handlerMock;

    @BeforeEach
    void setup() {
        handlerMock = mock(IUserFileHandler.class);

        realViewSpy = Mockito.spy(new LoginView());
        doNothing().when(realViewSpy).showInfo(anyString(), anyString());
        doNothing().when(realViewSpy).showError(anyString(), anyString());

        runOnFxAndWait(() -> {
            stage = new Stage();
            stage.setScene(new Scene(realViewSpy.getRoot()));
        });

        new LoginController(stage, realViewSpy, handlerMock);
    }

    @Test
    void loginButton_callsAuthenticateUser() {
        runOnFxAndWait(() -> {
            realViewSpy.getUsernameTextField().setText("daron");
            realViewSpy.getPasswordTextField().setText("1234");
        });

        when(handlerMock.authenticateUser("daron", "1234")).thenReturn(null);

        runOnFxAndWait(() -> realViewSpy.getBtLogin().fire());

        verify(handlerMock, times(1)).authenticateUser("daron", "1234");
    }

    @Test
    void success_showsWelcomeMessage() {
        runOnFxAndWait(() -> {
            realViewSpy.getUsernameTextField().setText("daron");
            realViewSpy.getPasswordTextField().setText("1234");
        });

        User loggedIn = new User(
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

        when(handlerMock.authenticateUser("daron", "1234")).thenReturn(loggedIn);

        runOnFxAndWait(() -> realViewSpy.getBtLogin().fire());

        verify(realViewSpy, times(1)).showInfo("Login Successful", "Welcome, daron!");
        assertNotNull(stage.getScene(), "Stage should still have a scene");
    }

    @Test
    void failure_showsInvalidCredentialsMessage() {
        runOnFxAndWait(() -> {
            realViewSpy.getUsernameTextField().setText("daron");
            realViewSpy.getPasswordTextField().setText("wrong");
        });

        when(handlerMock.authenticateUser("daron", "wrong")).thenReturn(null);

        runOnFxAndWait(() -> realViewSpy.getBtLogin().fire());

        verify(realViewSpy, times(1)).showError("Login Failed", "Invalid username or password.");
    }
}