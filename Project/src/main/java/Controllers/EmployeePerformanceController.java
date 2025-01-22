package Controllers;

import DAO.BillFileHandler;
import DAO.ItemFileHandler;
import DAO.UserFileHandler;
import Models.Bill;
import Models.User;
import Views.EmployeePerformanceView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeePerformanceController {
    private EmployeePerformanceView view = new EmployeePerformanceView();
    private UserFileHandler userFileHandler = new UserFileHandler();
    private ItemFileHandler itemFileHandler = new ItemFileHandler();
    private BillFileHandler billFileHandler = new BillFileHandler();
    private User currentUser;

    // Controller setting the currentUser as the one who controls
    public EmployeePerformanceController(User user) {
        this.currentUser = user;
        this.view.getBills().addAll(BillFileHandler.getBills());

        this.view.getLineChartWeekly().getData().add(calculateSeriesWeekly());
        this.view.getLineChartMonthly().getData().add(calculateSeriesMonthly());
        this.view.getLineChartTotal().getData().add(calculateSeriesTotal());
        this.view.getLineChartYearly().getData().add(calculateSeriesYearly());
        calculatePieChartWeekly();
        calculatePieChartMonthly();
        calculatePieChartYearly();
        calculatePieChartTotal();

        this.view.getEmployeePerformanceFilter().setOnAction(e -> {
            if(this.view.getEmployeePerformanceFilter().getSelectionModel().getSelectedItem().equals("This Week's Performance")) {
                this.view.getChartsBox().getChildren().clear();
                this.view.getChartsBox().getChildren().addAll(this.view.getHeaderCharts(), this.view.getWeeklyCharts());
            }
            if(this.view.getEmployeePerformanceFilter().getSelectionModel().getSelectedItem().equals("This Month's Performance")) {
                this.view.getChartsBox().getChildren().clear();
                this.view.getChartsBox().getChildren().addAll(this.view.getHeaderCharts(), this.view.getMonthlyCharts());
            }
            if(this.view.getEmployeePerformanceFilter().getSelectionModel().getSelectedItem().equals("This Year's Performance")) {
                this.view.getChartsBox().getChildren().clear();
                this.view.getChartsBox().getChildren().addAll(this.view.getHeaderCharts(), this.view.getYearlyCharts());
            }
            if(this.view.getEmployeePerformanceFilter().getSelectionModel().getSelectedItem().equals("Total Performance")) {
                this.view.getChartsBox().getChildren().clear();
                this.view.getChartsBox().getChildren().addAll(this.view.getHeaderCharts(), this.view.getTotalCharts());
            }
        });

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

        ObservableList<Bill> filteredBillsComplete = FXCollections.observableArrayList(filteredBills);

        // Update the TableView with the filtered bills
        billTableView.setItems(filteredBillsComplete);
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

    private XYChart.Series<String, Number> calculateSeriesWeekly() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);

        // Map to hold total earnings for each day of the week
        Map<LocalDate, Double> dailyEarnings = new HashMap<>();

        List<Bill> allBills = BillFileHandler.getBills();

        // Calculate total earnings for each day of the current week
        for (Bill bill : allBills) {
            LocalDate billDate = bill.getDateOfSale();
            if (billDate.isAfter(startOfWeek.minusDays(1)) && billDate.isBefore(startOfWeek.plusDays(7))) {
                double amount = bill.getTotalAmount();
                dailyEarnings.put(billDate, dailyEarnings.getOrDefault(billDate, 0.0) + amount);
            }
        }

        // Add data to the series for each day of the week
        for (int i = 0; i < 7; i++) {
            LocalDate date = startOfWeek.plusDays(i);
            double totalEarnings = dailyEarnings.getOrDefault(date, 0.0);
            this.view.getSeriesWeekly().getData().add(new XYChart.Data<>(date.getDayOfWeek().name(), totalEarnings)); // Use day name as x-value
        }

        return this.view.getSeriesWeekly();
    }

    private XYChart.Series<String, Number> calculateSeriesMonthly() {
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        // Map to hold total earnings for each day of the current month
        Map<Integer, Double> dailyEarnings = new HashMap<>();

        List<Bill> allBills = BillFileHandler.getBills();

        // Calculate total earnings for each day of the current month
        for (Bill bill : allBills) {
            if (bill.getDateOfSale().getYear() == currentYear && bill.getDateOfSale().getMonthValue() == currentMonth) {
                int day = bill.getDateOfSale().getDayOfMonth();
                double amount = bill.getTotalAmount();

                dailyEarnings.put(day, dailyEarnings.getOrDefault(day, 0.0) + amount);
            }
        }

        // Add data to the series
        for (int day = 1; day <= today.lengthOfMonth(); day++) {
            double totalEarnings = dailyEarnings.getOrDefault(day, 0.0);
            this.view.getSeriesMonthly().getData().add(new XYChart.Data<>(String.valueOf(day), totalEarnings));
        }

        return this.view.getSeriesMonthly();
    }

    private XYChart.Series<String, Number> calculateSeriesYearly() {
        int currentYear = LocalDate.now().getYear();

        // Map to hold total earnings for each month
        Map<Month, Double> monthlyEarnings = new HashMap<>();

        List<Bill> allBills = BillFileHandler.getBills();

        // Calculate total earnings for each month of the current year
        for (Bill bill : allBills) {
            if (bill.getDateOfSale().getYear() == currentYear) {
                Month month = bill.getDateOfSale().getMonth();
                double amount = bill.getTotalAmount();

                monthlyEarnings.put(month, monthlyEarnings.getOrDefault(month, 0.0) + amount);
            }
        }

        // Add data to the series
        for (Month month : Month.values()) {
            double totalEarnings = monthlyEarnings.getOrDefault(month, 0.0);
            this.view.getSeriesYearly().getData().add(new XYChart.Data<>(month.getDisplayName(TextStyle.FULL, Locale.ENGLISH), totalEarnings)); // month.getValue() gives the month number (1-12)
        }

        return this.view.getSeriesYearly();
    }

    private XYChart.Series<String, Number> calculateSeriesTotal() {

        List<Bill> allBills = BillFileHandler.getBills();

        Map<Integer, Double> yearlyEarnings = new HashMap<>();

        // Calculate total earnings for each day
        for (Bill bill : allBills) {
            int year = bill.getDateOfSale().getYear();
            double amount = bill.getTotalAmount();

            yearlyEarnings.put(year, yearlyEarnings.getOrDefault(year, 0.0) + amount);
        }

        // Calculate cumulative earnings
        for (Map.Entry<Integer, Double> entry : yearlyEarnings.entrySet()) {
            this.view.getSeriesTotal().getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
        }

        return this.view.getSeriesTotal();
    }

    private void calculatePieChartWeekly() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);

        // Map to hold total earnings for each cashier
        Map<String, Double> cashierEarnings = new HashMap<>();

        // Calculate total earnings for each cashier for the week
        for (int i = 0; i < 7; i++) {
            LocalDate date = startOfWeek.plusDays(i);
            List<Bill> billsForDay = getBillsForDay(date);

            for (Bill bill : billsForDay) {
                String cashierName = bill.getUsername();
                double amount = bill.getTotalAmount();

                // Aggregate earnings by user
                cashierEarnings.put(cashierName, cashierEarnings.getOrDefault(cashierName, 0.0) + amount);
            }
        }

        // Add data to the PieChart
        for (Map.Entry<String, Double> entry : cashierEarnings.entrySet()) {
            this.view.getPieChartWeekly().getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private void calculatePieChartMonthly() {
        LocalDate today = LocalDate.now();
        Month currentMonth = today.getMonth();
        int currentYear = today.getYear();

        // Map to hold total earnings for each cashier
        Map<String, Double> cashierEarnings = new HashMap<>();

        // Calculate total earnings for each cashier for the current month
        for (int day = 1; day <= today.lengthOfMonth(); day++) {
            LocalDate date = LocalDate.of(currentYear, currentMonth, day);
            List<Bill> billsForDay = getBillsForDay(date);

            for (Bill bill : billsForDay) {
                String cashierName = bill.getUsername();
                double amount = bill.getTotalAmount();

                // Aggregate earnings by user
                cashierEarnings.put(cashierName, cashierEarnings.getOrDefault(cashierName, 0.0) + amount);
            }
        }

        // Add data to the PieChart
        for (Map.Entry<String, Double> entry : cashierEarnings.entrySet()) {
            this.view.getPieChartMonthly().getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private void calculatePieChartYearly() {
        int currentYear = LocalDate.now().getYear();

        // Map to hold total earnings for each cashier
        Map<String, Double> cashierEarnings = new HashMap<>();

        List<Bill> allBills = BillFileHandler.getBills();

        // Calculate total earnings for each cashier for the current year
        for (Bill bill : allBills) {
            if (bill.getDateOfSale().getYear() == currentYear) {
                String cashierName = bill.getUsername();
                double amount = bill.getTotalAmount();

                // Aggregate earnings by cashier
                cashierEarnings.put(cashierName, cashierEarnings.getOrDefault(cashierName, 0.0) + amount);
            }
        }

        // Add data to the PieChart
        for (Map.Entry<String, Double> entry : cashierEarnings.entrySet()) {
            this.view.getPieChartYearly().getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private void calculatePieChartTotal() {
        // Map to hold total earnings for each cashier
        Map<String, Double> cashierEarnings = new HashMap<>();

        List<Bill> allBills = BillFileHandler.getBills();

        // Calculate total earnings for each user
        for (Bill bill : allBills) {
            String cashierName = bill.getUsername();
            double amount = bill.getTotalAmount();

            // Aggregate earnings by user
            cashierEarnings.put(cashierName, cashierEarnings.getOrDefault(cashierName, 0.0) + amount);
        }

        // Add data to the PieChart
        for (Map.Entry<String, Double> entry : cashierEarnings.entrySet()) {
            this.view.getPieChartTotal().getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    public List<Bill> getBillsForDay(LocalDate date) {
        List<Bill> bills = BillFileHandler.getBills();
        return bills.stream()
                .filter(bill -> bill.getDateOfSale().isEqual(date))
                .collect(Collectors.toList());
    }
}
