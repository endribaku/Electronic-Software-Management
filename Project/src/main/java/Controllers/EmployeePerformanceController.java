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
        this.view.getBills().addAll(billFileHandler.getBillsFromDirectory());
        setupBillDateFilter();
        setupSearchBar();
    }

    // Controller setting the currentUser as the one who controls
    public EmployeePerformanceController(User user) {
        this.currentUser = user;
        this.view.getBills().addAll(BillFileHandler.getBills());

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
                return bill.getUsername().toLowerCase().contains(newValue.toLowerCase());
            });
        });
    }

}
