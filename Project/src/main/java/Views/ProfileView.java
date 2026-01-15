package Views;

import Interfaces.Views.IProfileView;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ProfileView implements IProfileView {

    // CSS constants
    private static final String LABEL_STYLE = "-fx-font-size: 16; -fx-font-weight: bold;";
    private static final String BUTTON_STYLE = "-fx-font: 11pt Helvetica;";
    private static final String BOX_STYLE = "-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 10px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;";

    private final VBox profilePage;

    private final Label employeeIDLabel;
    private final Label fullNameLabel;
    private final Label usernameLabel;
    private final Label emailLabel;
    private final Label phoneLabel;
    private final ImageView profileImageView;
    private final Label dateOfBirthLabel;
    private final Label accessLevelLabel;
    private final Label sectorLabel;

    private final Button editButton;
    private final Button logout;
    private final Button updateProfileButton;
    private final Button cancelButton = new Button("Cancel");

    private final TextField employeeIDTextField;
    private final TextField fullNameTextField;
    private final TextField usernameTextField;
    private final TextField emailTextField;
    private final TextField phoneTextField;
    private final TextField dateOfBirthTextField;
    private final TextField accessLevelTextField;
    private final TextField sectorTextField;

    private final TextField editFullNameTextField = new TextField();
    private final TextField editUsernameTextField = new TextField();
    private final TextField editPasswordTextField = new TextField();
    private final TextField editEmailTextField = new TextField();
    private final TextField editPhoneTextField = new TextField();
    private final DatePicker editDateOfBirthTextField = new DatePicker();

    private final HBox editProfileBox;
    private final BorderPane profileBox = new BorderPane();

    public ProfileView() {

        // Profile image
        Image profilePicture = new Image("/profile.png");
        profileImageView = new ImageView(profilePicture);
        profileImageView.setFitWidth(150);
        profileImageView.setFitHeight(150);
        profileImageView.setPreserveRatio(true);

        // Profile labels
        employeeIDLabel = new Label("ID:");
        fullNameLabel = new Label("Name:");
        usernameLabel = new Label("Username:");
        emailLabel = new Label("Email:");
        dateOfBirthLabel = new Label("Date of Birth:");
        phoneLabel = new Label("Phone:");
        accessLevelLabel = new Label("Access Level:");
        sectorLabel = new Label("Sector:");

        employeeIDLabel.setStyle(LABEL_STYLE);
        fullNameLabel.setStyle(LABEL_STYLE);
        usernameLabel.setStyle(LABEL_STYLE);
        emailLabel.setStyle(LABEL_STYLE);
        dateOfBirthLabel.setStyle(LABEL_STYLE);
        phoneLabel.setStyle(LABEL_STYLE);
        accessLevelLabel.setStyle(LABEL_STYLE);
        sectorLabel.setStyle(LABEL_STYLE);

        // Profile text fields
        employeeIDTextField = new TextField();
        fullNameTextField = new TextField();
        usernameTextField = new TextField();
        emailTextField = new TextField();
        phoneTextField = new TextField();
        dateOfBirthTextField = new TextField();
        accessLevelTextField = new TextField();
        sectorTextField = new TextField();

        employeeIDTextField.setEditable(false);
        fullNameTextField.setEditable(false);
        usernameTextField.setEditable(false);
        emailTextField.setEditable(false);
        phoneTextField.setEditable(false);
        dateOfBirthTextField.setEditable(false);
        accessLevelTextField.setEditable(false);
        sectorTextField.setEditable(false);

        profileBox.setStyle(BOX_STYLE);

        // GridPane for profile details
        GridPane detailsBox = new GridPane();
        detailsBox.setHgap(10);
        detailsBox.setVgap(10);
        detailsBox.setPadding(new Insets(0, 20, 0, 40));

        detailsBox.add(employeeIDLabel, 0, 0);
        detailsBox.add(fullNameLabel, 0, 1);
        detailsBox.add(emailLabel, 0, 2);
        detailsBox.add(phoneLabel, 0, 3);
        detailsBox.add(usernameLabel, 0, 4);
        detailsBox.add(dateOfBirthLabel, 0, 5);
        detailsBox.add(accessLevelLabel, 0, 6);
        detailsBox.add(sectorLabel, 0, 7);

        detailsBox.add(employeeIDTextField, 1, 0);
        detailsBox.add(fullNameTextField, 1, 1);
        detailsBox.add(emailTextField, 1, 2);
        detailsBox.add(phoneTextField, 1, 3);
        detailsBox.add(usernameTextField, 1, 4);
        detailsBox.add(dateOfBirthTextField, 1, 5);
        detailsBox.add(accessLevelTextField, 1, 6);
        detailsBox.add(sectorTextField, 1, 7);

        // Buttons
        editButton = new Button("Edit Profile");
        logout = new Button("Logout");
        editButton.setStyle(BUTTON_STYLE);
        logout.setStyle(BUTTON_STYLE);

        VBox buttons = new VBox(editButton, logout);
        buttons.setSpacing(210);

        profileBox.setLeft(profileImageView);
        profileBox.setRight(buttons);
        profileBox.setCenter(detailsBox);
        profileBox.setPadding(new Insets(20));

        // Edit profile layout
        editProfileBox = new HBox();
        editProfileBox.setStyle(BOX_STYLE);
        editProfileBox.setSpacing(75);

        ImageView profileImageViewUpdate = new ImageView(new Image("/profile.png"));
        profileImageViewUpdate.setFitWidth(150);
        profileImageViewUpdate.setFitHeight(150);
        profileImageViewUpdate.setPreserveRatio(true);

        // Edit labels
        Label editFullNameLabel = new Label("Name:");
        Label editUsernameLabel = new Label("Username:");
        Label editEmailLabel = new Label("Email:");
        Label editPasswordLabel = new Label("Confirm Password:");
        Label editDateOfBirthLabel = new Label("Date of Birth:");
        Label editPhoneLabel = new Label("Phone:");

        editFullNameLabel.setStyle(LABEL_STYLE);
        editUsernameLabel.setStyle(LABEL_STYLE);
        editEmailLabel.setStyle(LABEL_STYLE);
        editPasswordLabel.setStyle(LABEL_STYLE);
        editDateOfBirthLabel.setStyle(LABEL_STYLE);
        editPhoneLabel.setStyle(LABEL_STYLE);

        GridPane editDetailsBox = new GridPane();
        editDetailsBox.setHgap(10);
        editDetailsBox.setVgap(10);

        editDetailsBox.add(editFullNameLabel, 0, 0);
        editDetailsBox.add(editFullNameTextField, 1, 0);
        editDetailsBox.add(editUsernameLabel, 0, 1);
        editDetailsBox.add(editUsernameTextField, 1, 1);
        editDetailsBox.add(editEmailLabel, 0, 2);
        editDetailsBox.add(editEmailTextField, 1, 2);
        editDetailsBox.add(editDateOfBirthLabel, 0, 3);
        editDetailsBox.add(editDateOfBirthTextField, 1, 3);
        editDetailsBox.add(editPhoneLabel, 0, 4);
        editDetailsBox.add(editPhoneTextField, 1, 4);
        editDetailsBox.add(editPasswordLabel, 0, 6);
        editDetailsBox.add(editPasswordTextField, 1, 6);

        VBox updateButtons = new VBox();
        updateProfileButton = new Button("Update Profile");
        updateProfileButton.setStyle(BUTTON_STYLE);
        cancelButton.setStyle(BUTTON_STYLE);
        updateButtons.getChildren().addAll(updateProfileButton, cancelButton);
        updateButtons.setSpacing(20);

        editProfileBox.getChildren().addAll(profileImageViewUpdate, editDetailsBox, updateButtons);

        // Main layout
        profilePage = new VBox(profileBox);
        profilePage.setSpacing(10);
        profilePage.setPadding(new Insets(20));

        // Button actions
        editButton.setOnAction(e -> {
            profilePage.getChildren().remove(profileBox);
            profilePage.getChildren().add(editProfileBox);
        });

        cancelButton.setOnAction(e -> {
            profilePage.getChildren().remove(editProfileBox);
            profilePage.getChildren().add(profileBox);
        });
    }

    // Getters and setters
    public VBox getProfilePage() { return profilePage; }
    public void setProfileImage(Image image) { profileImageView.setImage(image); }
    public void setName(String name) { fullNameLabel.setText("Name: " + name); }
    public void setEmail(String email) { emailLabel.setText("Email: " + email); }
    public void setPhone(String phone) { phoneLabel.setText("Phone: " + phone); }
    public Button getEditButton() { return editButton; }
    public ImageView getProfileImageView() { return profileImageView; }
    public Button getLogout() { return logout; }
    public TextField getEmployeeIDTextField() { return employeeIDTextField; }
    public TextField getFullNameTextField() { return fullNameTextField; }
    public TextField getUsernameTextField() { return usernameTextField; }
    public TextField getEmailTextField() { return emailTextField; }
    public TextField getPhoneTextField() { return phoneTextField; }
    public TextField getDateOfBirthTextField() { return dateOfBirthTextField; }
    public TextField getAccessLevelTextField() { return accessLevelTextField; }
    public TextField getSectorTextField() { return sectorTextField; }
    public Button getUpdateProfileButton() { return updateProfileButton; }
    public TextField getEditFullNameTextField() { return editFullNameTextField; }
    public TextField getEditUsernameTextField() { return editUsernameTextField; }
    public TextField getEditPasswordTextField() { return editPasswordTextField; }
    public TextField getEditEmailTextField() { return editEmailTextField; }
    public TextField getEditPhoneTextField() { return editPhoneTextField; }
    public DatePicker getEditDateOfBirthTextField() { return editDateOfBirthTextField; }
    public HBox getEditProfileBox() { return editProfileBox; }
    public BorderPane getProfileBox() { return profileBox; }
    public Button getCancelButton() { return cancelButton; }

    @Override
    public void onEdit(Runnable action) {
        editButton.setOnAction(e -> action.run());
    }

    @Override
    public void onCancel(Runnable action) {
        cancelButton.setOnAction(e -> action.run());
    }

    @Override
    public void onUpdateProfile(Runnable action) {
        updateProfileButton.setOnAction(e -> action.run());
    }

    @Override
    public void showProfileBox() {
        if (profilePage.getChildren().contains(editProfileBox)) {
            profilePage.getChildren().remove(editProfileBox);
        }
        if (!profilePage.getChildren().contains(profileBox)) {
            profilePage.getChildren().add(profileBox);
        }
    }

    @Override
    public void showEditProfileBox() {
        if (profilePage.getChildren().contains(profileBox)) {
            profilePage.getChildren().remove(profileBox);
        }
        if (!profilePage.getChildren().contains(editProfileBox)) {
            profilePage.getChildren().add(editProfileBox);
        }
    }

    @Override
    public void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.show();
    }

    @Override
    public void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.show();
    }

}