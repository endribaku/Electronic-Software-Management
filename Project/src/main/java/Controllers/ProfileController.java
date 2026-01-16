package Controllers;

import DAO.UserFileHandler;
import Exceptions.InvalidCredentialsException;
import Models.User;
import Views.ProfileView;
import javafx.scene.control.Alert;

import java.time.LocalDate;

public class ProfileController {

    private ProfileView view = new ProfileView();
    private UserFileHandler handler = new UserFileHandler();
    private User currentUser;

    // Controller setting the currentUser as the one who controls
    public ProfileController(User user) {
        this.currentUser = user;
        onInitialize();
    }

    private void onInitialize() {

        this.view.getEditButton().setOnAction(e -> {
            this.view.getProfilePage().getChildren().remove(this.view.getProfileBox());
            this.view.getProfilePage().getChildren().add(this.view.getEditProfileBox());
        });

        this.view.getCancelButton().setOnAction(e -> {
            this.view.getProfilePage().getChildren().remove(this.view.getEditProfileBox());
            this.view.getProfilePage().getChildren().add(this.view.getProfileBox());
        });

        // Fill initial view fields from current user
        refreshProfileFieldsFromCurrentUser();

        this.view.getUpdateProfileButton().setOnAction(e -> {
            try {
                onEditProfile();
            } catch (InvalidCredentialsException err) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(err.getMessage());
                alert.show();
            }
        });
    }

    private void onEditProfile() throws InvalidCredentialsException {
        LocalDate dobEdit = this.view.getEditDateOfBirthTextField().getValue();
        String fnameEdit = this.view.getEditFullNameTextField().getText();
        String emailEdit = this.view.getEditEmailTextField().getText();
        String phoneEdit = this.view.getEditPhoneTextField().getText();
        String username = this.view.getEditUsernameTextField().getText();
        String passConfirm = this.view.getEditPasswordTextField().getText();

        if (!passConfirm.equals(currentUser.getPassword())) {
            throw new InvalidCredentialsException("Passwords do not match.");
        }

        boolean updated = this.handler.updateProfile(
                username,
                fnameEdit,
                emailEdit,
                passConfirm,
                phoneEdit,
                dobEdit
        );

        if (updated) {

            // ✅ reload latest user from file (ID is safest, username may change)
            User refreshed = this.handler.selectUserFromId(currentUser.getUserID());
            if (refreshed != null) {
                this.currentUser = refreshed;
            }

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Success");
            success.setHeaderText("Profile Updated Successfully");
            success.show();

            this.view.getProfilePage().getChildren().remove(this.view.getEditProfileBox());
            this.view.getProfilePage().getChildren().add(this.view.getProfileBox());

            // ✅ refresh UI fields from reloaded currentUser
            refreshProfileFieldsFromCurrentUser();

        } else {
            throw new InvalidCredentialsException("Invalid credentials. Please try again");
        }
    }

    private void refreshProfileFieldsFromCurrentUser() {
        this.view.getFullNameTextField().setText(currentUser.getFullName());
        this.view.getEmailTextField().setText(currentUser.getEmail());
        this.view.getPhoneTextField().setText(currentUser.getPhoneNumber());
        this.view.getSectorTextField().setText(currentUser.getSector().toString());
        this.view.getAccessLevelTextField().setText(currentUser.getAccessLevel().toString());
        this.view.getEmployeeIDTextField().setText(currentUser.getUserID());
        this.view.getUsernameTextField().setText(currentUser.getUsername());
        this.view.getDateOfBirthTextField().setText(currentUser.getDateOfBirth().toString());
    }

    public ProfileView getView() {
        return view;
    }

    public UserFileHandler getHandler() {
        return handler;
    }
}
