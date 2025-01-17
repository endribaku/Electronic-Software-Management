package Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

    public class ProfileView {

        private VBox contentBox;
        private final Label nameLabel;
        private final Label emailLabel;
        private final Label phoneLabel;
        private final ImageView profileImageView;
        private final Button editButton;

        public ProfileView() {

            Image profilePicture = new Image("");
            profileImageView = new ImageView();
            profileImageView.setFitWidth(150);
            profileImageView.setFitHeight(150);
            profileImageView.setPreserveRatio(true);

            nameLabel = new Label("Name: ");
            nameLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            emailLabel = new Label("Email: ");
            phoneLabel = new Label("Phone: ");

            // VBox for user details
            VBox detailsBox = new VBox(10, nameLabel, emailLabel, phoneLabel);
            detailsBox.setAlignment(Pos.CENTER_LEFT);
            detailsBox.setPadding(new Insets(10));

            // Edit button
            editButton = new Button("Edit Profile");
            HBox buttonBox = new HBox(editButton);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setPadding(new Insets(10));

            // Main layout
            contentBox = new VBox(20, profileImageView, detailsBox, buttonBox);
            contentBox.setPadding(new Insets(20));

        }

        public VBox getProfilePage() {
            return contentBox;
        }

        public void setProfileImage(Image image) {
            profileImageView.setImage(image);
        }

        public void setName(String name) {
            nameLabel.setText("Name: " + name);
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
    }
