package Views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ProfileView {

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

        private final TextField employeeIDTextField;
        private final TextField fullNameTextField;
        private final TextField usernameTextField;
        private final TextField emailTextField;
        private final TextField phoneTextField;
        private final TextField dateOfBirthTextField;
        private final TextField accessLevelTextField;
        private final TextField sectorTextField;

        private final HBox editProfileBox;

        public ProfileView() {

            Image profilePicture = new Image("/profile.png");
            profileImageView = new ImageView(profilePicture);
            profileImageView.setFitWidth(150);
            profileImageView.setFitHeight(150);
            profileImageView.setPreserveRatio(true);

            //Profile Details
            employeeIDLabel = new Label("ID:");
            employeeIDLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            fullNameLabel = new Label("Name:");
            fullNameLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            usernameLabel = new Label("Username:");
            usernameLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            emailLabel = new Label("Email:");
            emailLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            dateOfBirthLabel = new Label("Date of Birth:");
            dateOfBirthLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            phoneLabel = new Label("Phone:");
            phoneLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            accessLevelLabel = new Label("Access Level:");
            accessLevelLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            sectorLabel = new Label("Sector:");
            sectorLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

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

            BorderPane profileBox = new BorderPane();
            profileBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 10px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");

            GridPane detailsBox = new GridPane();
            detailsBox.setHgap(10);
            detailsBox.setVgap(10);
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
            detailsBox.setPadding(new Insets(0, 20, 0, 40));
            // Buttons
            VBox buttons = new VBox();
            editButton = new Button("Edit Profile");
            logout = new Button("Logout");
            buttons.getChildren().addAll(editButton, logout);
            buttons.setSpacing(210);

            profileBox.setLeft(profileImageView);
            profileBox.setRight(buttons);
            profileBox.setCenter(detailsBox);
            profileBox.setPadding(new Insets(20));
            //profileBox.getChildren().addAll(profileImageView, detailsBox, buttons);
            //profileBox.setSpacing(75);
            editButton.setStyle("-fx-font: 11pt Helvetica;");
            logout.setStyle("-fx-font: 11pt Helvetica;");

            //Edit Profile
            editProfileBox = new HBox();

            Image profilePictureUpdate = new Image("/profile.png");
            ImageView profileImageViewUpdate = new ImageView(profilePictureUpdate);
            profileImageViewUpdate.setFitWidth(150);
            profileImageViewUpdate.setFitHeight(150);
            profileImageViewUpdate.setPreserveRatio(true);

            //Profile Details
            Label editFullNameLabel = new Label("Name:");
            editFullNameLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            Label editUsernameLabel = new Label("Username:");
            editUsernameLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            Label editEmailLabel = new Label("Email:");
            editEmailLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            Label editPasswordLabel = new Label("Password:");
            editPasswordLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            Label editDateOfBirthLabel = new Label("Date of Birth:");
            editDateOfBirthLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            Label editPhoneLabel = new Label("Phone:");
            editPhoneLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

            TextField editFullNameTextField = new TextField();
            TextField editUsernameTextField = new TextField();
            TextField editPasswordTextField = new TextField();
            TextField editEmailTextField = new TextField();
            TextField editPhoneTextField = new TextField();
            DatePicker editDateOfBirthTextField = new DatePicker();

            editProfileBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 10px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
            GridPane editDetailsBox = new GridPane();

            editDetailsBox.setHgap(10);
            editDetailsBox.setVgap(10);
            editDetailsBox.add(editFullNameLabel, 0, 0);
            editDetailsBox.add(editFullNameTextField, 1, 0);
            editDetailsBox.add(editUsernameLabel, 0, 1);
            editDetailsBox.add(editUsernameTextField, 1, 1);
            editDetailsBox.add(editEmailLabel, 0, 2);
            editDetailsBox.add(editEmailTextField, 1, 2);
            editDetailsBox.add(editPasswordLabel, 0, 3);
            editDetailsBox.add(editPasswordTextField, 1, 3);
            editDetailsBox.add(editPhoneLabel, 0, 4);
            editDetailsBox.add(editPhoneTextField, 1, 4);
            editDetailsBox.add(editDateOfBirthLabel, 0, 5);
            editDetailsBox.add(editDateOfBirthTextField, 1, 5);

            VBox updateButtons = new VBox();
            updateButtons.setSpacing(20);
            updateProfileButton = new Button("Update Profile");
            updateProfileButton.setStyle("-fx-font: 11pt Helvetica;");
            Button cancelButton = new Button("Cancel");
            cancelButton.setStyle("-fx-font: 11pt Helvetica;");
            updateButtons.getChildren().addAll(updateProfileButton, cancelButton);

            editProfileBox.getChildren().addAll(profileImageViewUpdate, editDetailsBox, updateButtons);
            editProfileBox.setSpacing(75);

            // Main layout
            profilePage = new VBox(profileBox);
            profilePage.setSpacing(10);
            profilePage.setPadding(new Insets(20));
            editButton.setOnAction(e -> {
                profilePage.getChildren().remove(profileBox);
                profilePage.getChildren().add(editProfileBox);
            });
            cancelButton.setOnAction(e -> {
                profilePage.getChildren().remove(editProfileBox);
                profilePage.getChildren().add(profileBox);
            });

        }

        public VBox getProfilePage() {
            return profilePage;
        }

        public void setProfileImage(Image image) {
            profileImageView.setImage(image);
        }

        public void setName(String name) {
            fullNameLabel.setText("Name: " + name);
        }

        public void setEmail(String email) {
            emailLabel.setText("Email: " + email);
        }

        public void setPhone(String phone) {
            phoneLabel.setText("Phone: " + phone);
        }

        public Button getEditButton() {
            return editButton;
        }

        public ImageView getProfileImageView() {
            return profileImageView;
        }

        public Button getLogout() {
            return logout;
        }

        public TextField getEmployeeIDTextField() {
            return employeeIDTextField;
        }

        public TextField getFullNameTextField() {
            return fullNameTextField;
        }

        public TextField getUsernameTextField() {
            return usernameTextField;
        }

        public TextField getEmailTextField() {
            return emailTextField;
        }

        public TextField getPhoneTextField() {
            return phoneTextField;
        }

        public TextField getDateOfBirthTextField() {
            return dateOfBirthTextField;
        }

        public TextField getAccessLevelTextField() {
            return accessLevelTextField;
        }

        public TextField getSectorTextField() {
            return sectorTextField;
        }
    }
