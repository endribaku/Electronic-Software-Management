package Views;

import DAO.InventoryFileHandler;
import Models.Access;
import Models.Permission;
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

    TextField employeeFullNameField;
    TextField employeeUsernameField;
    TextField employeePasswordField;
    DatePicker employeeDOBField;
    TextField employeeEmailField;
    TextField employeePhoneNumberField;
    TextField employeeSalaryField;
    ObservableList<Access> accessLevels = FXCollections.observableArrayList(Access.Cashier, Access.Manager, Access.Administrator);
    ComboBox<Access> accessLevelList= new ComboBox<>(accessLevels);
    ObservableList<String> permissions = FXCollections.observableArrayList
            (Permission.BILL_GENERATION.toString(),
                    Permission.BILL_MANAGEMENT.toString(),
                    Permission.EMPLOYEE_MANAGEMENT.toString(),
                    Permission.INVENTORY_ACCESS.toString(),
                    Permission.PERFORMANCE_VIEW.toString(),
                    Permission.SUPPLIER_MANAGEMENT.toString());
    ListView<String> permissionListView = new ListView<>(permissions);
    ObservableList<String> sectors = FXCollections.observableArrayList(new InventoryFileHandler().getSectorNames());
    ListView<String> sectorListView = new ListView<>(sectors);

    Button addEmployeeButton = new Button("Register Employee");
    private static final String ADD_BUTTON_STYLE = "-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;";
    private static final String LABEL_STYLE = "-fx-text-fill: #364958; -fx-font: 11pt Helvetica;";
    private static final String FIELD_STYLE = "-fx-font: 11pt Helvetica;";

    TextField employeeEditFullNameField;
    TextField employeeEditUsernameField;
    TextField employeeEditPasswordField;
    DatePicker employeeEditDOBField;
    TextField employeeEditEmailField;
    TextField employeeEditPhoneNumberField;
    TextField employeeEditSalaryField;
    ComboBox<Access> accessLevelEditList= new ComboBox<>(accessLevels);
    ListView<String> permissionEditListView = new ListView<>(permissions);
    ListView<String> sectorEditListView = new ListView<>(sectors);
    Button updateEmployeeButton = new Button("Update Employee");
    Button cancelUpdateButton = new Button("Cancel");

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

    HBox updateTableButtons = new HBox();
    Button updateEmployeeListButton = new Button("Update Table");
    Button deleteEmployeeButton = new Button("Delete Employee");
    Button editEmployeeButton = new Button("Edit Employee");

    VBox manageEmployeeBox = new VBox();
    VBox createEmployeeBox = new VBox();
    VBox editEmployeeBox = new VBox();

    public EmployeesListView() {

        employeesPage.setStyle("-fx-background-color: white; -fx-padding: 10;");

        //Create new Employee
        manageEmployeeBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        manageEmployeeBox.setSpacing(10);
        manageEmployeeBox.setMinWidth(350);

        Label addEmployeeLabel = new Label("Register new Employee");
        addEmployeeLabel.setStyle(ADD_BUTTON_STYLE);
        GridPane addEmployeeGrid = new GridPane();
        addEmployeeGrid.setHgap(10);
        addEmployeeGrid.setVgap(10);
        Label employeeFullNameLabel = new Label("Full Name:");
        employeeFullNameLabel.setStyle(LABEL_STYLE);
        addEmployeeGrid.add(employeeFullNameLabel, 0, 0);
        employeeFullNameField = new TextField();
        employeeFullNameField.setStyle(FIELD_STYLE);
        addEmployeeGrid.add(employeeFullNameField, 1, 0);
        Label employeeUsernameLabel = new Label("Username:");
        employeeUsernameLabel.setStyle(LABEL_STYLE);
        addEmployeeGrid.add(employeeUsernameLabel, 0, 1);
        employeeUsernameField = new TextField();
        employeeUsernameField.setStyle(FIELD_STYLE);
        addEmployeeGrid.add(employeeUsernameField, 1,1);
        Label employeePasswordLabel = new Label("Password:");
        employeePasswordLabel.setStyle(LABEL_STYLE);
        addEmployeeGrid.add(employeePasswordLabel, 0, 2);
        employeePasswordField = new TextField();
        employeePasswordField.setStyle(FIELD_STYLE);
        addEmployeeGrid.add(employeePasswordField, 1, 2);
        Label employeeDOBLabel = new Label("Date of Birth:");
        employeeDOBLabel.setStyle(LABEL_STYLE);
        addEmployeeGrid.add(employeeDOBLabel, 0, 3);
        employeeDOBField = new DatePicker();
        employeeDOBField.setStyle(FIELD_STYLE);
        addEmployeeGrid.add(employeeDOBField, 1, 3);
        Label employeeEmailLabel = new Label("Email:");
        employeeEmailLabel.setStyle(LABEL_STYLE);
        addEmployeeGrid.add(employeeEmailLabel, 0, 4);
        employeeEmailField = new TextField();
        employeeEmailField.setStyle(FIELD_STYLE);
        addEmployeeGrid.add(employeeEmailField, 1, 4);
        Label employeePhoneNumberLabel = new Label("Phone Number:");
        employeePhoneNumberLabel.setStyle(LABEL_STYLE);
        addEmployeeGrid.add(employeePhoneNumberLabel, 0, 5);
        employeePhoneNumberField = new TextField();
        employeePhoneNumberField.setStyle(FIELD_STYLE);
        addEmployeeGrid.add(employeePhoneNumberField, 1, 5);
        Label employeeSalaryLabel = new Label("Salary:");
        employeeSalaryLabel.setStyle(LABEL_STYLE);
        addEmployeeGrid.add(employeeSalaryLabel, 0, 6);
        employeeSalaryField = new TextField();
        employeeSalaryField.setStyle(FIELD_STYLE);
        addEmployeeGrid.add(employeeSalaryField, 1, 6);
        Label employeeAccessLevelLabel = new Label("Access Level:");
        employeeAccessLevelLabel.setStyle(LABEL_STYLE);
        addEmployeeGrid.add(employeeAccessLevelLabel, 0, 7);
        accessLevelList.setStyle(FIELD_STYLE);
        addEmployeeGrid.add(accessLevelList, 1,7);
        Label employeePermissionsLabel = new Label("Permissions:");
        employeePermissionsLabel.setStyle(LABEL_STYLE);
        addEmployeeGrid.add(employeePermissionsLabel, 0, 8);
        Label employeeSectorLabel = new Label("Sector:");
        employeeSectorLabel.setStyle(LABEL_STYLE);
        addEmployeeGrid.add(employeeSectorLabel, 1, 8);
        permissionListView.setStyle(FIELD_STYLE);
        permissionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        addEmployeeGrid.add(permissionListView, 0, 9);
        sectorListView.setStyle(FIELD_STYLE);
        sectorListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        addEmployeeGrid.add(sectorListView, 1, 9);

        //Create User Button
        addEmployeeButton.setStyle(FIELD_STYLE);
        GridPane.setHalignment(addEmployeeButton, HPos.RIGHT);
        addEmployeeGrid.add(addEmployeeButton, 1, 10);
        createEmployeeBox.getChildren().addAll(addEmployeeLabel, addEmployeeGrid);
        manageEmployeeBox.getChildren().addAll(createEmployeeBox);
        manageEmployeeBox.setSpacing(10);

        //Edit Employee Pane
        Label editEmployeeLabel = new Label("Edit Employee");
        editEmployeeLabel.setStyle(ADD_BUTTON_STYLE);
        GridPane editEmployeeGrid = new GridPane();
        editEmployeeGrid.setHgap(10);
        editEmployeeGrid.setVgap(10);
        Label employeeEditFullNameLabel = new Label("Full Name:");
        employeeEditFullNameLabel.setStyle(LABEL_STYLE);
        editEmployeeGrid.add(employeeEditFullNameLabel, 0, 0);
        employeeEditFullNameField = new TextField();
        employeeEditFullNameField.setStyle(FIELD_STYLE);
        editEmployeeGrid.add(employeeEditFullNameField, 1, 0);
        Label employeeEditUsernameLabel = new Label("Username:");
        employeeEditUsernameLabel.setStyle(LABEL_STYLE);
        editEmployeeGrid.add(employeeEditUsernameLabel, 0, 1);
        employeeEditUsernameField = new TextField();
        employeeEditUsernameField.setStyle(FIELD_STYLE);
        editEmployeeGrid.add(employeeEditUsernameField, 1,1);
        Label employeeEditPasswordLabel = new Label("Password:");
        employeeEditPasswordLabel.setStyle(LABEL_STYLE);
        editEmployeeGrid.add(employeeEditPasswordLabel, 0, 2);
        employeeEditPasswordField = new TextField();
        employeeEditPasswordField.setStyle(FIELD_STYLE);
        editEmployeeGrid.add(employeeEditPasswordField, 1, 2);
        Label employeeEditDOBLabel = new Label("Date of Birth:");
        employeeEditDOBLabel.setStyle(LABEL_STYLE);
        editEmployeeGrid.add(employeeEditDOBLabel, 0, 3);
        employeeEditDOBField = new DatePicker();
        employeeEditDOBField.setStyle(FIELD_STYLE);
        editEmployeeGrid.add(employeeEditDOBField, 1, 3);
        Label employeeEditEmailLabel = new Label("Email:");
        employeeEditEmailLabel.setStyle(LABEL_STYLE);
        editEmployeeGrid.add(employeeEditEmailLabel, 0, 4);
        employeeEditEmailField = new TextField();
        employeeEditEmailField.setStyle(FIELD_STYLE);
        editEmployeeGrid.add(employeeEditEmailField, 1, 4);
        Label employeeEditPhoneNumberLabel = new Label("Phone Number:");
        employeeEditPhoneNumberLabel.setStyle(LABEL_STYLE);
        editEmployeeGrid.add(employeeEditPhoneNumberLabel, 0, 5);
        employeeEditPhoneNumberField = new TextField();
        employeeEditPhoneNumberField.setStyle(FIELD_STYLE);
        editEmployeeGrid.add(employeeEditPhoneNumberField, 1, 5);
        Label employeeEditSalaryLabel = new Label("Salary:");
        employeeEditSalaryLabel.setStyle(LABEL_STYLE);
        editEmployeeGrid.add(employeeEditSalaryLabel, 0, 6);
        employeeEditSalaryField = new TextField();
        employeeEditSalaryField.setStyle(FIELD_STYLE);
        editEmployeeGrid.add(employeeEditSalaryField, 1, 6);
        Label employeeEditAccessLevelLabel = new Label("Access Level:");
        employeeEditAccessLevelLabel.setStyle(LABEL_STYLE);
        editEmployeeGrid.add(employeeEditAccessLevelLabel, 0, 7);
        accessLevelEditList.setStyle(FIELD_STYLE);
        editEmployeeGrid.add(accessLevelEditList, 1,7);
        Label employeeEditPermissionsLabel = new Label("Permissions:");
        employeeEditPermissionsLabel.setStyle(LABEL_STYLE);
        editEmployeeGrid.add(employeeEditPermissionsLabel, 0, 8);
        Label employeeEditSectorLabel = new Label("Sector:");
        employeeEditSectorLabel.setStyle(LABEL_STYLE);
        editEmployeeGrid.add(employeeEditSectorLabel, 1, 8);
        permissionEditListView.setStyle(FIELD_STYLE);
        permissionEditListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        editEmployeeGrid.add(permissionEditListView, 0, 9);
        sectorEditListView.setStyle(FIELD_STYLE);
        sectorEditListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        editEmployeeGrid.add(sectorEditListView, 1, 9);

        //Update User Button
        cancelUpdateButton.setStyle(FIELD_STYLE);
        GridPane.setHalignment(cancelUpdateButton, HPos.RIGHT);
        editEmployeeGrid.add(cancelUpdateButton, 0, 10);
        updateEmployeeButton.setStyle(FIELD_STYLE);
        GridPane.setHalignment(updateEmployeeButton, HPos.RIGHT);
        editEmployeeGrid.add(updateEmployeeButton, 1, 10);
        editEmployeeBox.setSpacing(10);
        editEmployeeBox.getChildren().addAll(editEmployeeLabel, editEmployeeGrid);

        //Display Employee's List
        VBox employeeListBox = new VBox();
        Label employeeListLabel = new Label("Employee List:");
        employeeListLabel.setStyle(ADD_BUTTON_STYLE);
        employeeListBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        employeeListBox.setSpacing(10);
        employeesTableView.setEditable(true);
        employeesTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        employeesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        employeeIDColumn.setMaxWidth(100);
        employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        employeeIDColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        employeeIDColumn.setOnEditCommit(e -> e.getRowValue().setUserID(e.getNewValue()));
        employeeFullNameColumn.setPrefWidth(80);
        employeeFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        employeeFullNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        employeeFullNameColumn.setOnEditCommit(e -> e.getRowValue().setFullName(e.getNewValue()));
        employeeUsernameColumn.setPrefWidth(80);
        employeeUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        employeeUsernameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        employeeUsernameColumn.setOnEditCommit(e -> e.getRowValue().setUsername(e.getNewValue()));
        employeePasswordColumn.setPrefWidth(80);
        employeePasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        employeePasswordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        employeePasswordColumn.setOnEditCommit(e -> e.getRowValue().setPassword(e.getNewValue()));
        employeeAccessLevelColumn.setPrefWidth(80);
        employeeAccessLevelColumn.setCellValueFactory(new PropertyValueFactory<>("accessLevel"));
        employeeAccessLevelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Access.Cashier, Access.Manager, Access.Administrator));
        employeeAccessLevelColumn.setOnEditCommit(e -> e.getRowValue().setAccessLevel(e.getNewValue()));
        employeeSectorColumn.setMaxWidth(80);
        employeeSectorColumn.setCellValueFactory(new PropertyValueFactory<>("sector"));
        employeeSectorColumn.setCellFactory(ComboBoxTableCell.forTableColumn(sectors));
        employeePermissionsColumn.setMaxWidth(80);
        employeePermissionsColumn.setCellValueFactory(new PropertyValueFactory<>("permissions"));
        employeePermissionsColumn.setCellFactory(ComboBoxTableCell.forTableColumn(permissions));
        employeeDOBColumn.setPrefWidth(80);
        employeeDOBColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        employeeDOBColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        employeeDOBColumn.setOnEditCommit(e -> e.getRowValue().setDateOfBirth(e.getNewValue()));
        employeeEmailColumn.setPrefWidth(80);
        employeeEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        employeeEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        employeeEmailColumn.setOnEditCommit(e -> e.getRowValue().setEmail(e.getNewValue()));
        employeePhoneNumberColumn.setPrefWidth(80);
        employeePhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        employeePhoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        employeePhoneNumberColumn.setOnEditCommit(e -> e.getRowValue().setPhoneNumber(e.getNewValue()));
        employeeSalaryColumn.setPrefWidth(80);
        employeeSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        employeeSalaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        employeeSalaryColumn.setOnEditCommit(e -> e.getRowValue().setSalary(e.getNewValue()));
        employeesTableView.getColumns().addAll(employeeIDColumn, employeeFullNameColumn, employeeAccessLevelColumn, employeeSectorColumn, employeePermissionsColumn, employeeUsernameColumn, employeePasswordColumn, employeeDOBColumn, employeeEmailColumn, employeePhoneNumberColumn, employeeSalaryColumn);

        updateEmployeeListButton.setStyle(FIELD_STYLE);
        editEmployeeButton.setStyle("-fx-font: 11pt Helvetica");
        deleteEmployeeButton.setStyle("-fx-font: 11pt Helvetica");
        updateTableButtons.setSpacing(225);
        updateTableButtons.getChildren().addAll(updateEmployeeListButton, editEmployeeButton, deleteEmployeeButton);

        employeeListBox.getChildren().addAll(employeeListLabel, employeesTableView, updateTableButtons);
        employeesPage.getChildren().addAll(manageEmployeeBox, employeeListBox);
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

    public HBox getUpdateTableButtons() {
        return updateTableButtons;
    }

    public Button getDeleteEmployeeButton() {
        return deleteEmployeeButton;
    }

    public Button getEditEmployeeButton() {
        return editEmployeeButton;
    }

    public TextField getEmployeeEditFullNameField() {
        return employeeEditFullNameField;
    }

    public TextField getEmployeeEditUsernameField() {
        return employeeEditUsernameField;
    }

    public TextField getEmployeeEditPasswordField() {
        return employeeEditPasswordField;
    }

    public DatePicker getEmployeeEditDOBField() {
        return employeeEditDOBField;
    }

    public TextField getEmployeeEditEmailField() {
        return employeeEditEmailField;
    }

    public TextField getEmployeeEditPhoneNumberField() {
        return employeeEditPhoneNumberField;
    }

    public TextField getEmployeeEditSalaryField() {
        return employeeEditSalaryField;
    }

    public ComboBox<Access> getAccessLevelEditList() {
        return accessLevelEditList;
    }

    public ListView<String> getPermissionEditListView() {
        return permissionEditListView;
    }

    public VBox getManageEmployeeBox() {
        return manageEmployeeBox;
    }

    public VBox getEditEmployeeBox() {
        return editEmployeeBox;
    }

    public VBox getCreateEmployeeBox() {
        return createEmployeeBox;
    }

    public ListView<String> getSectorEditListView() {
        return sectorEditListView;
    }

    public Button getUpdateEmployeeButton() {
        return updateEmployeeButton;
    }

    public Button getCancelUpdateButton() {
        return cancelUpdateButton;
    }
}

