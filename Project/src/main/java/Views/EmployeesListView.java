package Views;

import DAO.UserFileHandler;
import Models.Access;
import Models.Permission;
import Models.Sector;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.time.LocalDate;

public class EmployeesListView {

        HBox employeesPage = new HBox();

        TextField employeeFullNameField = new TextField();
        TextField employeeUsernameField = new TextField();
        TextField employeePasswordField = new TextField();
        DatePicker employeeDOBField = new DatePicker();
        TextField employeeEmailField = new TextField();
        TextField employeePhoneNumberField = new TextField();
        TextField employeeSalaryField = new TextField();
        ObservableList<Access> accessLevels = FXCollections.observableArrayList(Access.Cashier, Access.Manager, Access.Administrator);
        ComboBox<Access> accessLevelList= new ComboBox<Access>(accessLevels);
        ObservableList<String> permissions = FXCollections.observableArrayList
                (Permission.BILL_GENERATION.toString(),
                        Permission.BILL_MANAGEMENT.toString(),
                        Permission.EMPLOYEE_MANAGEMENT.toString(),
                        Permission.INVENTORY_ACCESS.toString(),
                        Permission.PERFORMANCE_VIEW.toString(),
                        Permission.SUPPLIER_MANAGEMENT.toString());
        ListView<String> permissionListView = new ListView<>(permissions);


    //Change to toString of each sector available

        ObservableList<String> sectors = FXCollections.observableArrayList("Electronics", "Mobile Devices", "IT");
        ListView<String> sectorListView = new ListView<>(sectors);

        Button addEmployeeButton = new Button("Register Employee");

        TableView<User> employeesTableView = new TableView<>();
        TableColumn<User, String> employeeIDColumn = new TableColumn<>("ID");
        TableColumn<User, String> employeeFullNameColumn = new TableColumn<>("Full Name");
        TableColumn<User, String> employeeUsernameColumn = new TableColumn<>("Username");
        TableColumn<User, String> employeePasswordColumn = new TableColumn<>("Password");
        TableColumn<User, Access> employeeAccessLevelColumn = new TableColumn<>("Access Level");


    TableColumn<User, String> employeeSectorColumn = new TableColumn<>("Sector");
        TableColumn<User, String> employeePermissionsColumn = new TableColumn<>("Permissions");
        TableColumn<User, LocalDate> employeeDOBColumn = new TableColumn<>("Date of Birth");
        TableColumn<User, String> employeeEmailColumn = new TableColumn<>("Email");
        TableColumn<User, String> employeePhoneNumberColumn = new TableColumn<>("Phone Number");
        TableColumn<User, Double> employeeSalaryColumn = new TableColumn<>("Salary");

        Button updateEmployeeListButton = new Button("Update Table");


        public EmployeesListView() {

            employeesPage.setStyle("-fx-background-color: white; -fx-padding: 10;");

            //Create new Employee
            VBox addEmployeeBox = new VBox();
            addEmployeeBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
            addEmployeeBox.setSpacing(10);
            addEmployeeBox.setMinWidth(350);
            Label addEmployeeLabel = new Label("Register new Employee");
            addEmployeeLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
            GridPane addEmployeeGrid = new GridPane();
            addEmployeeGrid.setHgap(10);
            addEmployeeGrid.setVgap(10);
            Label employeeFullNameLabel = new Label("Full Name:");
            employeeFullNameLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeFullNameLabel, 0, 0);
            employeeFullNameField = new TextField();
            employeeFullNameField.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeFullNameField, 1, 0);
            Label employeeUsernameLabel = new Label("Username:");
            employeeUsernameLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeUsernameLabel, 0, 1);
            employeeUsernameField = new TextField();
            employeeUsernameField.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeUsernameField, 1,1);
            Label employeePasswordLabel = new Label("Password:");
            employeePasswordLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeePasswordLabel, 0, 2);
            employeePasswordField = new TextField();
            employeePasswordField.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeePasswordField, 1, 2);
            Label employeeDOBLabel = new Label("Date of Birth:");
            employeeDOBLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeDOBLabel, 0, 3);
            employeeDOBField = new DatePicker();
            employeeDOBField.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeDOBField, 1, 3);
            Label employeeEmailLabel = new Label("Email:");
            employeeEmailLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeEmailLabel, 0, 4);
            employeeEmailField = new TextField();
            employeeEmailField.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeEmailField, 1, 4);
            Label employeePhoneNumberLabel = new Label("Phone Number:");
            employeePhoneNumberLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeePhoneNumberLabel, 0, 5);
            employeePhoneNumberField = new TextField();
            employeePhoneNumberField.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeePhoneNumberField, 1, 5);
            Label employeeSalaryLabel = new Label("Salary:");
            employeeSalaryLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeSalaryLabel, 0, 6);
            employeeSalaryField = new TextField();
            employeeSalaryField.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeSalaryField, 1, 6);
            Label employeeAccessLevelLabel = new Label("Access Level:");
            employeeAccessLevelLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeAccessLevelLabel, 0, 7);
            accessLevelList.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(accessLevelList, 1,7);
            Label employeePermissionsLabel = new Label("Permissions:");
            employeePermissionsLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeePermissionsLabel, 0, 8);
            Label employeeSectorLabel = new Label("Sector:");
            employeeSectorLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeSectorLabel, 1, 8);
            permissionListView.setStyle("-fx-font: 11pt Helvetica;");
            permissionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            addEmployeeGrid.add(permissionListView, 0, 9);
            sectorListView.setStyle("-fx-font: 11pt Helvetica;");
            sectorListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            addEmployeeGrid.add(sectorListView, 1, 9);
            //Create User Button
            addEmployeeButton.setStyle("-fx-font: 11pt Helvetica;");
            GridPane.setHalignment(addEmployeeButton, HPos.RIGHT);
            addEmployeeGrid.add(addEmployeeButton, 1, 10);
            addEmployeeBox.getChildren().addAll(addEmployeeLabel, addEmployeeGrid);

            //Display Employee's List
            VBox employeeListBox = new VBox();
            Label employeeListLabel = new Label("Employee List:");
            employeeListLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
            employeeListBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
            employeeListBox.setSpacing(10);
            employeesTableView.setEditable(true);
            employeesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            employeesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            employeeIDColumn.setMaxWidth(100);
            employeeIDColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userID"));
            employeeIDColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            employeeIDColumn.setOnEditCommit(e -> e.getRowValue().setUserID(e.getNewValue()));
            employeeFullNameColumn.setPrefWidth(80);
            employeeFullNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("fullName"));
            employeeFullNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            employeeFullNameColumn.setOnEditCommit(e -> e.getRowValue().setFullName(e.getNewValue()));
            employeeUsernameColumn.setPrefWidth(80);
            employeeUsernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
            employeeUsernameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            employeeUsernameColumn.setOnEditCommit(e -> e.getRowValue().setUsername(e.getNewValue()));
            employeePasswordColumn.setPrefWidth(80);
            employeePasswordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
            employeePasswordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            employeePasswordColumn.setOnEditCommit(e -> e.getRowValue().setPassword(e.getNewValue()));
            employeeAccessLevelColumn.setPrefWidth(80);
            employeeAccessLevelColumn.setCellValueFactory(new PropertyValueFactory<User, Access>("accessLevel"));
            employeeAccessLevelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new Access[] { Access.Cashier, Access.Manager, Access.Administrator }));
            employeeAccessLevelColumn.setOnEditCommit(e -> e.getRowValue().setAccessLevel(e.getNewValue()));
            employeeSectorColumn.setMaxWidth(80);
            employeeSectorColumn.setCellValueFactory(new PropertyValueFactory<>("sector"));
            employeeSectorColumn.setCellFactory(ComboBoxTableCell.forTableColumn(sectors));
            employeePermissionsColumn.setMaxWidth(80);
            employeePermissionsColumn.setCellValueFactory(new PropertyValueFactory<>("permissions"));
            employeePermissionsColumn.setCellFactory(ComboBoxTableCell.forTableColumn(permissions));
            employeeDOBColumn.setPrefWidth(80);
            employeeDOBColumn.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dateOfBirth"));
            employeeDOBColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
            employeeDOBColumn.setOnEditCommit(e -> e.getRowValue().setDateOfBirth(e.getNewValue()));
            employeeEmailColumn.setPrefWidth(80);
            employeeEmailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
            employeeEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            employeeEmailColumn.setOnEditCommit(e -> e.getRowValue().setEmail(e.getNewValue()));
            employeePhoneNumberColumn.setPrefWidth(80);
            employeePhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("phoneNumber"));
            employeePhoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            employeePhoneNumberColumn.setOnEditCommit(e -> e.getRowValue().setPhoneNumber(e.getNewValue()));
            employeeSalaryColumn.setPrefWidth(80);
            employeeSalaryColumn.setCellValueFactory(new PropertyValueFactory<User, Double>("salary"));
            employeeSalaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(
                    new DoubleStringConverter() {
                        @Override
                        public Double fromString(String value) {
                            try {
                                return super.fromString(value);
                            } catch (NumberFormatException e) {
                                return 0.0;
                            }
                        }
                    }
            ));
            employeeSalaryColumn.setOnEditCommit(e -> e.getRowValue().setSalary(e.getNewValue()));
            employeesTableView.getColumns().addAll(employeeIDColumn, employeeFullNameColumn, employeeAccessLevelColumn, employeeSectorColumn, employeePermissionsColumn, employeeUsernameColumn, employeePasswordColumn, employeeDOBColumn, employeeEmailColumn, employeePhoneNumberColumn, employeeSalaryColumn);
            updateEmployeeListButton.setStyle("-fx-font: 11pt Helvetica;");

            employeeListBox.getChildren().addAll(employeeListLabel, employeesTableView, updateEmployeeListButton);
            employeesPage.getChildren().addAll(addEmployeeBox, employeeListBox);
            employeesPage.setSpacing(10);
        }


        public HBox getEmployeesPage() {
            return employeesPage;
        }

        public TextField getEmployeeFullNameField() {
            return employeeFullNameField;
        }

        public TextField getEmployeeUsernameField() {
            return employeeUsernameField;
        }

        public TextField getEmployeePasswordField() {
            return employeePasswordField;
        }

        public DatePicker getEmployeeDOBField() {
            return employeeDOBField;
        }

        public TextField getEmployeeEmailField() {
            return employeeEmailField;
        }

        public TextField getEmployeePhoneNumberField() {
            return employeePhoneNumberField;
        }

        public TextField getEmployeeSalaryField() {
            return employeeSalaryField;
        }

        public ComboBox<Access> getAccessLevelList() {
            return accessLevelList;
        }

        public Button getAddEmployeeButton() {
            return addEmployeeButton;
        }

        public TableView<User> getEmployeesTableView() {
            return employeesTableView;
        }

        public TableColumn<User, String> getEmployeeIDColumn() {
            return employeeIDColumn;
        }

        public TableColumn<User, String> getEmployeeFullNameColumn() {
            return employeeFullNameColumn;
        }

        public TableColumn<User, String> getEmployeeUsernameColumn() {
            return employeeUsernameColumn;
        }

        public TableColumn<User, String> getEmployeePasswordColumn() {
            return employeePasswordColumn;
        }

        public TableColumn<User, Access> getEmployeeAccessLevelColumn() {
            return employeeAccessLevelColumn;
        }

        public TableColumn<User, LocalDate> getEmployeeDOBColumn() {
            return employeeDOBColumn;
        }

        public TableColumn<User, String> getEmployeePhoneNumberColumn() {
            return employeePhoneNumberColumn;
        }

        public TableColumn<User, String> getEmployeeEmailColumn() {
            return employeeEmailColumn;
        }

        public TableColumn<User, Double> getEmployeeSalaryColumn() {
            return employeeSalaryColumn;
        }

        public Button getUpdateEmployeeListButton() {
            return updateEmployeeListButton;
        }


        public ListView<String> getSectorListView() {
            return sectorListView;
        }

        public void setSectorListView(ListView<String> sectorListView) {
            this.sectorListView = sectorListView;
        }

        public ObservableList<String> getSectors() {
            return sectors;
        }

        public void setSectors(ObservableList<String> sectors) {
            this.sectors = sectors;
        }

        public ListView<String> getPermissionListView() {
            return permissionListView;
        }

        public void setPermissionListView(ListView<String> permissionListView) {
            this.permissionListView = permissionListView;
        }

        public ObservableList<String> getPermissions() {
            return permissions;
        }

        public void setPermissions(ObservableList<String> permissions) {
            this.permissions = permissions;
        }

    public ObservableList<Access> getAccessLevels() {
        return accessLevels;
    }

    public TableColumn<User, String> getEmployeeSectorColumn() {
        return employeeSectorColumn;
    }

    public TableColumn<User, String> getEmployeePermissionsColumn() {
        return employeePermissionsColumn;
    }

}

