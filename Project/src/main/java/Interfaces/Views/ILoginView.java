package Interfaces.Views;

import javafx.scene.Scene;

public interface ILoginView {
    String getUsernameText();
    String getPasswordText();

    void onLogin(Runnable action);

    void showInfo(String title, String message);
    void showError(String title, String message);

    Scene getApplication();
}
