package Interfaces.Views;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public interface IProfileView {

    // ==== Containers ====
    VBox getProfilePage();
    BorderPane getProfileBox();
    HBox getEditProfileBox();

    // ==== Display fields (read-only section) ====
    TextField getEmployeeIDTextField();
    TextField getFullNameTextField();
    TextField getUsernameTextField();
    TextField getEmailTextField();
    TextField getPhoneTextField();
    TextField getDateOfBirthTextField();
    TextField getAccessLevelTextField();
    TextField getSectorTextField();

    // ==== Edit inputs ====
    TextField getEditFullNameTextField();
    TextField getEditUsernameTextField();
    TextField getEditPasswordTextField();
    TextField getEditEmailTextField();
    TextField getEditPhoneTextField();
    DatePicker getEditDateOfBirthTextField();

    // ==== Wiring ====
    void onEdit(Runnable action);
    void onCancel(Runnable action);
    void onUpdateProfile(Runnable action);

    // ==== View helpers ====
    void showProfileBox();
    void showEditProfileBox();

    // ==== Messages ====
    void showInfo(String title, String message);
    void showError(String title, String message);
}

