package Controllers;

import DAO.BillFileHandler;
import DAO.ItemFileHandler;
import DAO.SectorFileHandler;
import DAO.UserFileHandler;
import Models.Bill;
import Models.User;
import Views.EmployeePerformanceView;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeePerformanceController {
    private EmployeePerformanceView view = new EmployeePerformanceView();
    private UserFileHandler userFileHandler = new UserFileHandler();
    private ItemFileHandler itemFileHandler = new ItemFileHandler();
    private BillFileHandler billFileHandler = new BillFileHandler();
    private User currentUser;
    public EmployeePerformanceController() {
        this.view.getBillTableView().setItems(BillFileHandler.getBills());
        setupBillDateFilter();
        setupSearchBar();
    }

    // Controller setting the currentUser as the one who controls
    public EmployeePerformanceController(User user) {
        this.currentUser = user;
        this.view.getBillTableView().setItems(billFileHandler.getBillsFromDirectory());
        setupBillDateFilter();
        setupSearchBar();
    }
    public EmployeePerformanceView getView() {
        return view;
    }

    public UserFileHandler getUserFileHandler() {
        return userFileHandler;
    }

    public ItemFileHandler getItemFileHandler() {
        return itemFileHandler;
    }

    public BillFileHandler getBillFileHandler() {
        return billFileHandler;
    }

    public void setEditListeners(){
        this.view.getBillNumberColumn().setOnEditCommit(e->{
            billFileHandler.getBillsFromDirectory().get(e.getTablePosition().getRow()).getBillNumber();});

        this.view.getCashierColumn().setOnEditCommit(e->{
            billFileHandler.getBillsFromDirectory().get(e.getTablePosition().getRow()).getUser().toString();});

        this.view.getTotalPriceColumn().setOnEditCommit(e->{
            billFileHandler.getBillsFromDirectory().get(e.getTablePosition().getRow()).getTotalAmount();});

        this.view.getDateOfSaleColumn().setOnEditCommit(e->{
            billFileHandler.getBillsFromDirectory().get(e.getTablePosition().getRow()).getDateOfSale().toString();});

//        this.view.getEmployeePerformanceButton.setOnAction(e -> {
//            if(this.billFileHandler.updateAll()) {
//                Alert success = new Alert(Alert.AlertType.INFORMATION);
//                success.setTitle("Success");
//                success.setHeaderText("Employee Table Updated Successfully");
//                success.show();
//            } else {
//                Alert fail = new Alert(Alert.AlertType.ERROR);
//                fail.setTitle("Success");
//                fail.setHeaderText("Employee Table Update Error");
//                fail.show();
//            }
//        });
    }

    public void setupBillDateFilter(){
        ComboBox<String> billDateFilter = view.getBillDateFilter();
        TableView<Bill> billTableView = view.getBillTableView();
        ObservableList<Bill> bills = view.getBills();

        billDateFilter.setOnAction(event -> {
            String selectedFilter = billDateFilter.getValue();
            filterBills(selectedFilter, bills, billTableView);
        });
    }

    private void filterBills(String filter, ObservableList<Bill> bills, TableView<Bill> billTableView) {
        List<Bill> filteredBills;

        switch (filter) {
            case "Today's Bills":
                filteredBills = bills.stream()
                        .filter(bill -> bill.getDateOfSale().isEqual(LocalDate.now()))
                        .collect(Collectors.toList());
                break;
            case "This Month's Bills":
                filteredBills = bills.stream()
                        .filter(bill -> bill.getDateOfSale().getMonth() == LocalDate.now().getMonth() &&
                                bill.getDateOfSale().getYear() == LocalDate.now().getYear())
                        .collect(Collectors.toList());
                break;
            case "This Year's Bills":
                filteredBills = bills.stream()
                        .filter(bill -> bill.getDateOfSale().getYear() == LocalDate.now().getYear())
                        .collect(Collectors.toList());
                break;
            case "Total Bills":
            default:
                filteredBills = bills; // Show all bills
                break;
        }

        // Update the TableView with the filtered bills
        billTableView.getItems().clear();
        billTableView.getItems().addAll(filteredBills);
    }

    private void setupSearchBar() {
        TextField searchBar = view.getSearchBar();
        TableView<Bill> billTableView = view.getBillTableView();
        ObservableList<Bill> bills = view.getBills();
        FilteredList<Bill> filteredBills = view.getFilteredBills();

        // Bind the filtered list to the table view
        billTableView.setItems(filteredBills);

        // Add listener to the search bar
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredBills.setPredicate(bill -> {
                // If no search text, display all bills
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all items
                }

                // Compare cashier names ignoring case
                return bill.getUser().getUsername().toLowerCase().contains(newValue.toLowerCase());
            });
        });
    }

}
