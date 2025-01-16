package Views;

import DAO.UserFileHandler;
import Models.*;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AdminView extends Pane {

    BorderPane Root = new BorderPane();

    public AdminView() {

        //MenuBar
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        MenuItem homeItem = new MenuItem("Home");
        MenuItem inventoryItem = new MenuItem("Inventory");
        MenuItem employeeItem = new MenuItem("Employees");
        MenuItem exitItem = new MenuItem("Exit");
        menu.getItems().addAll(homeItem, exitItem, inventoryItem, employeeItem);
        Menu billMenu = new Menu("Bills");
        MenuItem newBillItem = new MenuItem("Generate Bill");
        MenuItem viewBillItem = new MenuItem("View Bills");
        billMenu.getItems().addAll(newBillItem, viewBillItem);
        Menu profileMenu = new Menu("Profile");
        MenuItem profileItem = new MenuItem("View Profile");
        MenuItem logoutItem = new MenuItem("Logout");
        profileMenu.getItems().addAll(profileItem, logoutItem);
        menuBar.getMenus().addAll(menu, billMenu, profileMenu);

        Root.setTop(menuBar);

        // Sidebar
        VBox sidebar = new VBox(15);
        sidebar.setStyle("-fx-background-color: #364958; -fx-padding: 20;");
        Label homeLabel = new Label("Home");
        homeLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label inventoryLabel = new Label("Inventory");
        inventoryLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label employeeLabel = new Label("Employees");
        employeeLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        sidebar.getChildren().addAll(homeLabel, inventoryLabel, employeeLabel);
        sidebar.setPrefWidth(150);
        Root.setLeft(sidebar);

        // Home page
        GridPane homePage = new GridPane();
        homePage.setHgap(10);
        homePage.setVgap(10);
        homePage.setStyle("-fx-background-color: white;");

        // Sales chart
        LineChart<String, Number> lineChart = createLineChart();
        homePage.add(lineChart, 0, 0);

        // Pie chart
        PieChart pieChart = createPieChart();
        homePage.add(pieChart, 1, 0);

        Root.setCenter(homePage);

        // Add content to center
        homeLabel.onMouseClickedProperty().set(e -> Root.setCenter(homePage));
        inventoryLabel.onMouseClickedProperty().set(e -> Root.setCenter(new InventoryView().show()));
        employeeLabel.onMouseClickedProperty().set(e -> Root.setCenter(new EmployeesListView().getEmployeesPage()));
    }

    public BorderPane getRoot() {
        return Root;
    }

    private LineChart<String, Number> createLineChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Sales Over Time");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sales");
        series.getData().add(new XYChart.Data<>("Mon", 1000));
        series.getData().add(new XYChart.Data<>("Tue", 2000));
        series.getData().add(new XYChart.Data<>("Wed", 3000));
        series.getData().add(new XYChart.Data<>("Thu", 4000));
        series.getData().add(new XYChart.Data<>("Fri", 3500));
        lineChart.getData().add(series);
        return lineChart;
    }

    private PieChart createPieChart() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Sales by City");
        pieChart.getData().add(new PieChart.Data("City A", 40));
        pieChart.getData().add(new PieChart.Data("City B", 30));
        pieChart.getData().add(new PieChart.Data("City C", 20));
        pieChart.getData().add(new PieChart.Data("City D", 10));
        return pieChart;
    }

    public class EmployeesListView {

        UserFileHandler userFileHandler = new UserFileHandler();

        TextField employeeFullNameField = new TextField();
        TextField employeeUsernameField = new TextField();
        TextField employeePasswordField = new TextField();
        DatePicker employeeDOBField = new DatePicker();
        TextField employeeEmailField = new TextField();
        TextField employeePhoneNumberField = new TextField();
        TextField employeeSalaryField = new TextField();
        ObservableList<Access> accessLevels = FXCollections.observableArrayList(Access.Cashier, Access.Manager, Access.Administrator);
        ComboBox<Access> accessLevelList= new ComboBox<Access>(accessLevels);

        Button addEmployeeButton = new Button("Register Employee");

        ObservableList<User> employees;
        TableView<User> employeesTableView = new TableView<>(employees);
        TableColumn<User, String> employeeIDColumn = new TableColumn<>("ID");
        TableColumn<User, String> employeeFullNameColumn = new TableColumn<>("Full Name");
        TableColumn<User, String> employeeUsernameColumn = new TableColumn<>("Username");
        TableColumn<User, String> employeePasswordColumn = new TableColumn<>("Password");
        TableColumn<User, Access> employeeAccessLevelColumn = new TableColumn<>("Access Level");
        TableColumn<User, LocalDate> employeeDOBColumn = new TableColumn<>("Date of Birth");
        TableColumn<User, String> employeeEmailColumn = new TableColumn<>("Email");
        TableColumn<User, String> employeePhoneNumberColumn = new TableColumn<>("Phone Number");
        TableColumn<User, Number> employeeSalaryColumn = new TableColumn<>("Salary");

        Button updateEmployeeListButton = new Button("Update Table");
        HBox employeesPage = new HBox();

        public EmployeesListView() {

            employeesPage.setStyle("-fx-background-color: white; -fx-padding: 10;");

            //Create new Employee
            VBox addEmployeeBox = new VBox();
            addEmployeeBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
            addEmployeeBox.setSpacing(10);
            Label addEmployeeLabel = new Label("Register new Employee");
            addEmployeeLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
            GridPane addEmployeeGrid = new GridPane();
            addEmployeeGrid.setHgap(10);
            addEmployeeGrid.setVgap(10);
            Label employeeFullNameLabel = new Label("Full Name:");
            employeeFullNameLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeFullNameLabel, 0, 0);
            TextField employeeFullNameField = new TextField();
            employeeFullNameField.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeFullNameField, 1, 0);
            Label employeeUsernameLabel = new Label("Username:");
            employeeUsernameLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeUsernameLabel, 0, 1);
            TextField employeeUsernameField = new TextField();
            employeeUsernameField.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeUsernameField, 1,1);
            Label employeePasswordLabel = new Label("Password:");
            employeePasswordLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeePasswordLabel, 0, 2);
            TextField employeePasswordField = new TextField();
            employeePasswordField.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeePasswordField, 1, 2);
            Label employeeDOBLabel = new Label("Date of Birth:");
            employeeDOBLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeDOBLabel, 0, 3);
            DatePicker employeeDOBField = new DatePicker();
            employeeDOBField.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeDOBField, 1, 3);
            Label employeeEmailLabel = new Label("Email:");
            employeeEmailLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeEmailLabel, 0, 4);
            TextField employeeEmailField = new TextField();
            employeeEmailField.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeEmailField, 1, 4);
            Label employeePhoneNumberLabel = new Label("Phone Number:");
            employeePhoneNumberLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeePhoneNumberLabel, 0, 5);
            TextField employeePhoneNumberField = new TextField();
            employeePhoneNumberField.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeePhoneNumberField, 1, 5);
            Label employeeSalaryLabel = new Label("Salary:");
            employeeSalaryLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeSalaryLabel, 0, 6);
            TextField employeeSalaryField = new TextField();
            employeeSalaryField.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeSalaryField, 1, 6);
            Label employeeAccessLevelLabel = new Label("Access Level:");
            employeeAccessLevelLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(employeeAccessLevelLabel, 0, 7);
            accessLevelList.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeGrid.add(accessLevelList, 1,7);
            //Create User Button
            addEmployeeButton.setStyle("-fx-font: 11pt Helvetica;");
            addEmployeeButton.setOnAction(e -> {
                String employeeFullName = employeeFullNameField.getText();
                String employeeUsername = employeeUsernameField.getText();
                String employeePassword = employeePasswordField.getText();
                LocalDate employeeDOB = employeeDOBField.getValue();
                String employeeEmail = employeeEmailField.getText();
                String employeePhoneNumber = employeePhoneNumberField.getText();
                if(employeeDOB == null)
                    employeeDOB = LocalDate.now();
                if(employeeSalaryField.getText().isEmpty())
                    employeeSalaryField.setText("0");
                double employeeSalary = Double.parseDouble(employeeSalaryField.getText());
                Access employeeAccessLevel = accessLevelList.getSelectionModel().getSelectedItem();
                if(employeeAccessLevel == null)
                    employeeAccessLevel = Access.Cashier;
                if (employeeFullName.isEmpty() || employeeUsername.isEmpty() || employeePassword.isEmpty() || employeeEmail.isEmpty() || employeePhoneNumber.isEmpty() || employeeSalary == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Input");
                    alert.show();
                }
                try {
                    if (employeeAccessLevel.equals(Access.Cashier)) {
                        userFileHandler.insertUser(new Cashier(employeeUsername, employeePassword, employeeFullName, employeeDOB, employeePhoneNumber, employeeEmail, employeeSalary));
                    } else if (employeeAccessLevel.equals(Access.Manager)) {
                        userFileHandler.insertUser(new Manager(employeeUsername, employeePassword, employeeFullName, employeeDOB, employeePhoneNumber, employeeEmail, employeeSalary));
                    } else if (employeeAccessLevel.equals(Access.Administrator)) {
                        userFileHandler.insertUser(new Administrator(employeeUsername, employeePassword, employeeFullName, employeeDOB, employeePhoneNumber, employeeEmail, employeeSalary));
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Employee Registered Successfully");
                    alert.show();
                } catch(FileNotFoundException fnfe) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("File not found");
                    alert.setHeaderText("input file not found");
                    alert.show();
                } catch(IOException ioe) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("input file problem");
                    alert.show();
                } catch(ClassNotFoundException cnfe) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("boel file problem");
                    alert.show();
                }
            });
            GridPane.setHalignment(addEmployeeButton, HPos.RIGHT);
            addEmployeeGrid.add(addEmployeeButton, 1, 8);
            addEmployeeBox.getChildren().addAll(addEmployeeLabel, addEmployeeGrid);

            //Display Employee's List
            VBox employeeListBox = new VBox();
            Label employeeListLabel = new Label("Employee List:");
            employeeListLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
            employeeListBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
            employeeListBox.setSpacing(10);
            employeeIDColumn.setMinWidth(100);
            employeeIDColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userID"));
            employeeFullNameColumn.setMinWidth(100);
            employeeFullNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("fullName"));
            employeeUsernameColumn.setMinWidth(100);
            employeeUsernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
            employeePasswordColumn.setMinWidth(100);
            employeePasswordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
            employeeAccessLevelColumn.setMinWidth(100);
            employeeAccessLevelColumn.setCellValueFactory(new PropertyValueFactory<User, Access>("accessLevel"));
            employeeDOBColumn.setMinWidth(100);
            employeeDOBColumn.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dateOfBirth"));
            employeeEmailColumn.setMinWidth(100);
            employeeEmailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
            employeePhoneNumberColumn.setMinWidth(100);
            employeePhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("phoneNumber"));
            employeeSalaryColumn.setMinWidth(100);
            employeeSalaryColumn.setCellValueFactory(new PropertyValueFactory<User, Number>("salary"));
            employeesTableView.getColumns().addAll(employeeIDColumn, employeeFullNameColumn, employeeAccessLevelColumn, employeeUsernameColumn, employeePasswordColumn, employeeDOBColumn, employeeEmailColumn, employeePhoneNumberColumn, employeeSalaryColumn);
            employeesTableView.setPrefWidth(1000);
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

        public ObservableList<Access> getAccessLevels() {
            return accessLevels;
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

        public TableColumn<User, Number> getEmployeeSalaryColumn() {
            return employeeSalaryColumn;
        }

        public Button getUpdateEmployeeListButton() {
            return updateEmployeeListButton;
        }
    }
}


