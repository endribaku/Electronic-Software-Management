package Views;

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
        ObservableList<Permission> permissions = FXCollections.observableArrayList(Permission.BillGeneration, Permission.EmployeeManagement, Permission.PerformanceView, Permission.BillManagement, Permission.InventoryAccess, Permission.SupplierManagement);
        ListView<Permission> permissionList = new ListView<Permission>(permissions);
        ObservableList<Sector> sectors = FXCollections.observableArrayList(new Sector("Electronics", new User()));
        ListView<Sector> sectorsList = new ListView<Sector>(sectors);

        Button createEmployeeButton = new Button("Register Employee");

        TableView<User> employeesTableView = new TableView<>();
        TableColumn<User, String> employeeIDColumn = new TableColumn<>("ID");
        TableColumn<User, String> employeeFullNameColumn = new TableColumn<>("Full Name");
        TableColumn<User, String> employeeUsernameColumn = new TableColumn<>("Username");
        TableColumn<User, String> employeePasswordColumn = new TableColumn<>("Password");
        TableColumn<User, Access> employeeAccessLevelColumn = new TableColumn<>("Access Level");
        TableColumn<User, LocalDate> employeeDOBColumn = new TableColumn<>("Date of Birth");
        TableColumn<User, String> employeeEmailColumn = new TableColumn<>("Email");
        TableColumn<User, String> employeePhoneNumberColumn = new TableColumn<>("Phone Number");
        TableColumn<User, Double> employeeSalaryColumn = new TableColumn<>("Salary");
        TableColumn<User, Permission> employeePermissionsColumn = new TableColumn<>("Permissions");
        TableColumn<User, Sector> employeeSectorColumn = new TableColumn<>("Sector");

        Button updateEmployeeListButton = new Button("Update Table");

        Button editEmployeeButton = new Button("Edit Employee");
        Button updateEmployeeButton = new Button("Update Employee");
        Button addEmployeeButton = new Button("Add Employee");
        Button deleteEmployeeButton = new Button("Delete Employee");

        TextField editEmployeeFullNameField = new TextField();
        TextField editEmployeeUsernameField = new TextField();
        TextField editEmployeePasswordField = new TextField();
        DatePicker editEmployeeDOBField = new DatePicker();
        TextField editEmployeeEmailField = new TextField();
        TextField editEmployeePhoneNumberField = new TextField();
        TextField editEmployeeSalaryField = new TextField();
        ComboBox<Access> editAccessLevelList= new ComboBox<Access>(accessLevels);
        ListView<Permission> editPermissionList = new ListView<Permission>(permissions);
        ListView<Sector> editSectorsList = new ListView<Sector>(sectors);

        public EmployeesListView() {

            employeesPage.setStyle("-fx-background-color: white; -fx-padding: 10;");

            //Create new Employee
            VBox addEmployeeBox = new VBox();
            HBox addEmployeeHeaderBox = new HBox();
            addEmployeeBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
            addEmployeeBox.setSpacing(10);
            addEmployeeBox.setMinWidth(350);
            Label addEmployeeLabel = new Label("Register new Employee");
            addEmployeeLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
            editEmployeeButton.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeHeaderBox.getChildren().addAll(addEmployeeLabel, editEmployeeButton);
            addEmployeeHeaderBox.setSpacing(130);
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
            Label employeePermissionsList = new Label("Permissions:");
            employeePermissionsList.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeePermissionsList, 0, 8);
            permissionList.setStyle("-fx-font: 11pt Helvetica;");
            permissionList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            addEmployeeGrid.add(permissionList, 0, 9);
            Label employeeSectorLabel = new Label("Sector:");
            employeeSectorLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeSectorLabel, 1, 8);
            sectorsList.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(sectorsList, 1, 9);

            //Edit Employee Pane
            HBox editEmployeeHeaderBox = new HBox();
            Label editEmployeeLabel = new Label("Edit Employee");
            editEmployeeLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
            addEmployeeButton.setStyle("-fx-font: 11pt Helvetica;");
            GridPane.setHalignment(addEmployeeButton, HPos.RIGHT);
            editEmployeeHeaderBox.getChildren().addAll(editEmployeeLabel, addEmployeeButton);
            editEmployeeHeaderBox.setSpacing(210);
            GridPane editEmployeeGrid = new GridPane();
            editEmployeeGrid.setHgap(10);
            editEmployeeGrid.setVgap(10);
            Label editEmployeeFullNameLabel = new Label("Full Name:");
            editEmployeeFullNameLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeeFullNameLabel, 0, 0);
            editEmployeeFullNameField = new TextField();
            editEmployeeFullNameField.setStyle("-fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeeFullNameField, 1, 0);
            Label editEmployeeUsernameLabel = new Label("Username:");
            editEmployeeUsernameLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeeUsernameLabel, 0, 1);
            editEmployeeUsernameField = new TextField();
            editEmployeeUsernameField.setStyle("-fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeeUsernameField, 1,1);
            Label editEmployeePasswordLabel = new Label("Password:");
            editEmployeePasswordLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeePasswordLabel, 0, 2);
            editEmployeePasswordField = new TextField();
            editEmployeePasswordField.setStyle("-fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeePasswordField, 1, 2);
            Label editEmployeeDOBLabel = new Label("Date of Birth:");
            editEmployeeDOBLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeeDOBLabel, 0, 3);
            editEmployeeDOBField = new DatePicker();
            editEmployeeDOBField.setStyle("-fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeeDOBField, 1, 3);
            Label editEmployeeEmailLabel = new Label("Email:");
            editEmployeeEmailLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeeEmailLabel, 0, 4);
            editEmployeeEmailField = new TextField();
            editEmployeeEmailField.setStyle("-fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeeEmailField, 1, 4);
            Label editEmployeePhoneNumberLabel = new Label("Phone Number:");
            editEmployeePhoneNumberLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeePhoneNumberLabel, 0, 5);
            editEmployeePhoneNumberField = new TextField();
            editEmployeePhoneNumberField.setStyle("-fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeePhoneNumberField, 1, 5);
            Label editEmployeeSalaryLabel = new Label("Salary:");
            editEmployeeSalaryLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeeSalaryLabel, 0, 6);
            editEmployeeSalaryField = new TextField();
            editEmployeeSalaryField.setStyle("-fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeeSalaryField, 1, 6);
            Label editEmployeeAccessLevelLabel = new Label("Access Level:");
            editEmployeeAccessLevelLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeeAccessLevelLabel, 0, 7);
            editAccessLevelList.setStyle("-fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editAccessLevelList, 1,7);
            Label editEmployeePermissionsList = new Label("Permissions:");
            editEmployeePermissionsList.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeePermissionsList, 0, 8);
            editPermissionList.setStyle("-fx-font: 11pt Helvetica;");
            editPermissionList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            editEmployeeGrid.add(editPermissionList, 0, 9);
            Label editEmployeeSectorLabel = new Label("Sector:");
            editEmployeeSectorLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editEmployeeSectorLabel, 1, 8);
            editSectorsList.setStyle("-fx-font: 11pt Helvetica;");
            editEmployeeGrid.add(editSectorsList, 1, 9);

            updateEmployeeButton.setStyle("-fx-font: 11pt Helvetica;");
            GridPane.setHalignment(updateEmployeeButton, HPos.RIGHT);
            editEmployeeGrid.add(updateEmployeeButton, 1, 10);

            //Create User Button
            createEmployeeButton.setStyle("-fx-font: 11pt Helvetica;");
            GridPane.setHalignment(createEmployeeButton, HPos.RIGHT);
            addEmployeeGrid.add(createEmployeeButton, 1, 10);

            addEmployeeBox.getChildren().addAll(addEmployeeHeaderBox, addEmployeeGrid);

            editEmployeeButton.setOnAction(e -> {
                addEmployeeBox.getChildren().clear();
                addEmployeeBox.getChildren().addAll(editEmployeeHeaderBox, editEmployeeGrid);
            });
            addEmployeeButton.setOnAction(e -> {
                addEmployeeBox.getChildren().clear();
                addEmployeeBox.getChildren().addAll(addEmployeeHeaderBox, addEmployeeGrid);
            });

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
            employeeFullNameColumn.setMaxWidth(100);
            employeeFullNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("fullName"));
            employeeFullNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            employeeFullNameColumn.setOnEditCommit(e -> e.getRowValue().setFullName(e.getNewValue()));
            employeeUsernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
            employeeUsernameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            employeeUsernameColumn.setOnEditCommit(e -> e.getRowValue().setUsername(e.getNewValue()));
            employeePasswordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
            employeePasswordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            employeePasswordColumn.setOnEditCommit(e -> e.getRowValue().setPassword(e.getNewValue()));
            employeeAccessLevelColumn.setCellValueFactory(new PropertyValueFactory<User, Access>("accessLevel"));
            employeeAccessLevelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new Access[] { Access.Cashier, Access.Manager, Access.Administrator }));
            employeeAccessLevelColumn.setOnEditCommit(e -> e.getRowValue().setAccessLevel(e.getNewValue()));
            employeeSectorColumn.setCellValueFactory(new PropertyValueFactory<User, Sector>("sector"));
            employeeSectorColumn.setCellFactory(ComboBoxTableCell.forTableColumn(sectors.toArray(new Sector[0])));
            employeeDOBColumn.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dateOfBirth"));
            employeeDOBColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
            employeeDOBColumn.setOnEditCommit(e -> e.getRowValue().setDateOfBirth(e.getNewValue()));
            employeeEmailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
            employeeEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            employeeEmailColumn.setOnEditCommit(e -> e.getRowValue().setEmail(e.getNewValue()));
            employeePhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("phoneNumber"));
            employeePhoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            employeePhoneNumberColumn.setOnEditCommit(e -> e.getRowValue().setPhoneNumber(e.getNewValue()));
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
            employeePermissionsColumn.setCellValueFactory(new PropertyValueFactory<User, Permission>("Permission"));
            employeePermissionsColumn.setCellFactory(ComboBoxTableCell.forTableColumn(permissions.toArray(new Permission[0])));
            employeeAccessLevelColumn.setOnEditCommit(e -> e.getRowValue().setAccessLevel(e.getNewValue()));
            employeeSalaryColumn.setOnEditCommit(e -> e.getRowValue().setSalary(e.getNewValue()));
            employeesTableView.getColumns().addAll(employeeIDColumn, employeeFullNameColumn, employeeAccessLevelColumn, employeeSectorColumn, employeeUsernameColumn, employeePasswordColumn, employeeDOBColumn, employeeEmailColumn, employeePhoneNumberColumn, employeeSalaryColumn, employeePermissionsColumn);
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

        public Button getCreateEmployeeButton() {
            return createEmployeeButton;
        }

    public ObservableList<Access> getAccessLevels() {
        return accessLevels;
    }

    public ObservableList<Permission> getPermissions() {
        return permissions;
    }

    public ListView<Permission> getPermissionList() {
        return permissionList;
    }

    public ObservableList<Sector> getSectors() {
        return sectors;
    }

    public ListView<Sector> getSectorsList() {
        return sectorsList;
    }

    public TableColumn<User, Permission> getEmployeePermissionsColumn() {
        return employeePermissionsColumn;
    }

    public TableColumn<User, Sector> getEmployeeSectorColumn() {
        return employeeSectorColumn;
    }

    public Button getEditEmployeeButton() {
        return editEmployeeButton;
    }

    public Button getUpdateEmployeeButton() {
        return updateEmployeeButton;
    }

    public Button getDeleteEmployeeButton() {
        return deleteEmployeeButton;
    }

    public Button getAddEmployeeButton() {
        return addEmployeeButton;
    }

    public TextField getEditEmployeeFullNameField() {
        return editEmployeeFullNameField;
    }

    public TextField getEditEmployeePasswordField() {
        return editEmployeePasswordField;
    }

    public TextField getEditEmployeeUsernameField() {
        return editEmployeeUsernameField;
    }

    public DatePicker getEditEmployeeDOBField() {
        return editEmployeeDOBField;
    }

    public TextField getEditEmployeeEmailField() {
        return editEmployeeEmailField;
    }

    public TextField getEditEmployeePhoneNumberField() {
        return editEmployeePhoneNumberField;
    }

    public TextField getEditEmployeeSalaryField() {
        return editEmployeeSalaryField;
    }

    public ComboBox<Access> getEditAccessLevelList() {
        return editAccessLevelList;
    }

    public ListView<Permission> getEditPermissionList() {
        return editPermissionList;
    }

    public ListView<Sector> getEditSectorsList() {
        return editSectorsList;
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
    }

