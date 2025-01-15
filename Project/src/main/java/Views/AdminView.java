package Views;

import Models.*;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AdminView {
    private Administrator currentAdmin;
    ListView<User> employeesListView = new ListView<>();

//    public AdminView(Administrator currentAdmin) {
//        this.currentAdmin = currentAdmin;
//    }

    public void show(Stage primaryStage) {

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

        BorderPane Root = new BorderPane();
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
        employeeLabel.onMouseClickedProperty().set(e -> Root.setCenter(new EmployeesListView().show(currentAdmin)));

        // Scene
        Scene scene = new Scene(Root, 1500, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
        primaryStage.show();
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
}

 class EmployeesListView {
    ObservableList<Access> accessLevels = FXCollections.observableArrayList(Access.Cashier, Access.Manager, Access.Administrator);
    ComboBox<Access> accessLevelList= new ComboBox<Access>(accessLevels);

    ObservableList<User> employees;
    TableView<User> employeesTableView = new TableView<>(employees);

    public Pane show(Administrator currentAdmin) {
        HBox employeesPage = new HBox();
        employeesPage.setStyle("-fx-background-color: white; -fx-padding: 10;");
        employeesPage.setSpacing(10);

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

        Button addEmployeeButton = new Button("Register Employee");
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
            if (employeeFullName.isEmpty() || employeeUsername.isEmpty() || employeePassword.isEmpty() || employeeEmail.isEmpty() || employeePhoneNumber.isEmpty() || employeeSalary == 0 || employeeAccessLevel.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.show();
            }
            try {
                if (employeeAccessLevel.equals(Access.Cashier)) {
                    currentAdmin.addUser(new Cashier(employeeUsername, employeePassword, employeeFullName, employeeDOB, employeePhoneNumber, employeeEmail, employeeSalary));
                } else if (employeeAccessLevel.equals(Access.Manager)) {
                    currentAdmin.addUser(new Manager(employeeUsername, employeePassword, employeeFullName, employeeDOB, employeePhoneNumber, employeeEmail, employeeSalary));
                } else if (employeeAccessLevel.equals(Access.Administrator)) {
                    currentAdmin.addUser(new Administrator(employeeUsername, employeePassword, employeeFullName, employeeDOB, employeePhoneNumber, employeeEmail, employeeSalary));
                }
            } catch(Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Can't Register Employee right now");
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
        employees = FXCollections.observableArrayList();
        TableColumn<User, String> employeeFullNameColumn = new TableColumn<>("Full Name");
        //employeeFullNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFullName()); => needs to change fields to SimpleProperty's
        TableColumn<User, Access> employeeAccessLevelColumn = new TableColumn<>("Access Level");
        TableColumn<User, String> employeeSalaryColumn = new TableColumn<>("Salary");
        employeesTableView.getColumns().addAll(employeeFullNameColumn, employeeAccessLevelColumn, employeeSalaryColumn);
        employeeListBox.getChildren().addAll(employeeListLabel, employeesTableView);

        employeesPage.getChildren().addAll(addEmployeeBox, employeeListBox);

        return employeesPage;
    }
}
