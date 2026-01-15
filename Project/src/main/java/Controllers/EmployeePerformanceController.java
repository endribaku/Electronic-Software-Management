package Controllers;

import DAO.BillFileHandler;
import DAO.ItemFileHandler;
import DAO.UserFileHandler;
import Interfaces.DAO.IBillFileHandler;
import Interfaces.DAO.IItemFileHandler;
import Interfaces.DAO.IUserFileHandler;
import Interfaces.Views.IEmployeePerformanceView;
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

    // ✅ keep field NAMES unchanged (only types switched to interfaces)
    private IEmployeePerformanceView view = new EmployeePerformanceView();
    private IUserFileHandler userFileHandler = new UserFileHandler();
    private IItemFileHandler itemFileHandler = new ItemFileHandler();
    private IBillFileHandler billFileHandler = new BillFileHandler();

    private User currentUser;

    // ✅ same public constructor you already had
    public EmployeePerformanceController(User user) {
        this(
                user,
                new EmployeePerformanceView(),
                new UserFileHandler(),
                new ItemFileHandler(),
                new BillFileHandler()
        );
    }

    // ✅ injection-friendly constructor for integration tests (mock view/dao here)
    public EmployeePerformanceController(
            User user,
            IEmployeePerformanceView view,
            IUserFileHandler userFileHandler,
            IItemFileHandler itemFileHandler,
            IBillFileHandler billFileHandler
    ) {
        this.currentUser = user;
        this.view = view;
        this.userFileHandler = userFileHandler;
        this.itemFileHandler = itemFileHandler;
        this.billFileHandler = billFileHandler;

        // bills load (no static call)
        this.view.getBills().clear();
        this.view.getBills().addAll(billFileHandler.getBills());

        // charts init
        initCharts();

        // filter for which charts to show
        this.view.getEmployeePerformanceFilter().setOnAction(event -> updateChartsLayout());

        setupBillDateFilter();
        setupSearchBar();

        // initial layout based on default combobox value
        updateChartsLayout();
    }

    public IEmployeePerformanceView getView() {
        return view;
    }

    public IUserFileHandler getUserFileHandler() {
        return userFileHandler;
    }

    public IItemFileHandler getItemFileHandler() {
        return itemFileHandler;
    }

    public IBillFileHandler getBillFileHandler() {
        return billFileHandler;
    }

    // -----------------------------
    // Constructor helpers
    // -----------------------------

    private void initCharts() {
        // ensure series are clean before adding data
        view.getSeriesWeekly().getData().clear();
        view.getSeriesMonthly().getData().clear();
        view.getSeriesYearly().getData().clear();
        view.getSeriesTotal().getData().clear();

        view.getLineChartWeekly().getData().clear();
        view.getLineChartMonthly().getData().clear();
        view.getLineChartYearly().getData().clear();
        view.getLineChartTotal().getData().clear();

        view.getPieChartWeekly().getData().clear();
        view.getPieChartMonthly().getData().clear();
        view.getPieChartYearly().getData().clear();
        view.getPieChartTotal().getData().clear();

        view.getLineChartWeekly().getData().add(calculateSeriesWeekly());
        view.getLineChartMonthly().getData().add(calculateSeriesMonthly());
        view.getLineChartTotal().getData().add(calculateSeriesTotal());
        view.getLineChartYearly().getData().add(calculateSeriesYearly());

        calculatePieChartWeekly();
        calculatePieChartMonthly();
        calculatePieChartYearly();
        calculatePieChartTotal();
    }

    private void updateChartsLayout() {
        ComboBox<String> filter = view.getEmployeePerformanceFilter();
        String selected = filter.getSelectionModel().getSelectedItem();

        // be safe
        if (selected == null) {
            selected = "This Week's Performance";
        }

        view.getChartsBox().getChildren().clear();
        view.getChartsBox().getChildren().add(view.getHeaderCharts());

        if (selected.equals("This Week's Performance")) {
            view.getChartsBox().getChildren().add(view.getWeeklyCharts());
        }
        if (selected.equals("This Month's Performance")) {
            view.getChartsBox().getChildren().add(view.getMonthlyCharts());
        }
        if (selected.equals("This Year's Performance")) {
            view.getChartsBox().getChildren().add(view.getYearlyCharts());
        }
        if (selected.equals("Total Performance")) {
            view.getChartsBox().getChildren().add(view.getTotalCharts());
        }
    }

    // -----------------------------
    // Filters / Search (kept methods)
    // -----------------------------

    public void setupBillDateFilter() {
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

            case "This Week's Bills": {
                LocalDate today = LocalDate.now();
                LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
                LocalDate endOfWeek = startOfWeek.plusDays(6);

                filteredBills = bills.stream()
                        .filter(bill -> !bill.getDateOfSale().isBefore(startOfWeek)
                                && !bill.getDateOfSale().isAfter(endOfWeek))
                        .collect(Collectors.toList());
                break;
            }

            case "This Month's Bills":
                filteredBills = bills.stream()
                        .filter(bill -> bill.getDateOfSale().getMonth() == LocalDate.now().getMonth()
                                && bill.getDateOfSale().getYear() == LocalDate.now().getYear())
                        .collect(Collectors.toList());
                break;

            case "This Year's Bills":
                filteredBills = bills.stream()
                        .filter(bill -> bill.getDateOfSale().getYear() == LocalDate.now().getYear())
                        .collect(Collectors.toList());
                break;

            case "Total Bills":
            default:
                filteredBills = bills; // show all
                break;
        }

        ObservableList<Bill> filteredBillsComplete = FXCollections.observableArrayList(filteredBills);
        billTableView.setItems(filteredBillsComplete);
    }

    private void setupSearchBar() {
        TextField searchBar = view.getSearchBar();
        TableView<Bill> billTableView = view.getBillTableView();
        FilteredList<Bill> filteredBills = view.getFilteredBills();

        billTableView.setItems(filteredBills);

        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredBills.setPredicate(bill -> matchesSearch(bill, newValue))
        );
    }

    // -----------------------------
    // Series calculations (kept methods)
    // -----------------------------

    private XYChart.Series<String, Number> calculateSeriesWeekly() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);

        Map<LocalDate, Double> dailyEarnings = new HashMap<>();
        List<Bill> allBills = billFileHandler.getBills();

        for (Bill bill : allBills) {
            LocalDate billDate = bill.getDateOfSale();

            if (!billDate.isBefore(startOfWeek) && billDate.isBefore(startOfWeek.plusDays(7))) {
                double amount = bill.getTotalAmount();

                try {
                    classifyBillAmount(amount);
                } catch (IllegalArgumentException validationError) {
                    continue;
                }

                dailyEarnings.put(billDate, dailyEarnings.getOrDefault(billDate, 0.0) + amount);
            }
        }

        for (int i = 0; i < 7; i++) {
            LocalDate date = startOfWeek.plusDays(i);
            double totalEarnings = dailyEarnings.getOrDefault(date, 0.0);

            view.getSeriesWeekly().getData().add(
                    new XYChart.Data<>(date.getDayOfWeek().name(), totalEarnings)
            );
        }

        return view.getSeriesWeekly();
    }

    private XYChart.Series<String, Number> calculateSeriesMonthly() {
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        Map<Integer, Double> dailyEarnings = new HashMap<>();
        List<Bill> allBills = billFileHandler.getBills();

        for (Bill bill : allBills) {
            if (bill.getDateOfSale().getYear() == currentYear
                    && bill.getDateOfSale().getMonthValue() == currentMonth) {

                int day = bill.getDateOfSale().getDayOfMonth();
                double amount = bill.getTotalAmount();

                try {
                    classifyBillAmount(amount);
                } catch (IllegalArgumentException validationError) {
                    continue;
                }

                dailyEarnings.put(day, dailyEarnings.getOrDefault(day, 0.0) + amount);
            }
        }

        for (int day = 1; day <= today.lengthOfMonth(); day++) {
            double totalEarnings = dailyEarnings.getOrDefault(day, 0.0);
            view.getSeriesMonthly().getData().add(new XYChart.Data<>(String.valueOf(day), totalEarnings));
        }

        return view.getSeriesMonthly();
    }

    private XYChart.Series<String, Number> calculateSeriesYearly() {
        int currentYear = LocalDate.now().getYear();

        Map<Month, Double> monthlyEarnings = new HashMap<>();
        List<Bill> allBills = billFileHandler.getBills();

        for (Bill bill : allBills) {
            if (bill.getDateOfSale().getYear() == currentYear) {
                Month month = bill.getDateOfSale().getMonth();
                double amount = bill.getTotalAmount();

                try {
                    classifyBillAmount(amount);
                } catch (IllegalArgumentException validationError) {
                    continue;
                }

                monthlyEarnings.put(month, monthlyEarnings.getOrDefault(month, 0.0) + amount);
            }
        }

        for (Month month : Month.values()) {
            double totalEarnings = monthlyEarnings.getOrDefault(month, 0.0);
            view.getSeriesYearly().getData().add(
                    new XYChart.Data<>(month.getDisplayName(TextStyle.FULL, Locale.ENGLISH), totalEarnings)
            );
        }

        return view.getSeriesYearly();
    }

    private XYChart.Series<String, Number> calculateSeriesTotal() {
        List<Bill> allBills = billFileHandler.getBills();

        Map<Integer, Double> yearlyEarnings = new HashMap<>();

        for (Bill bill : allBills) {
            int year = bill.getDateOfSale().getYear();
            double amount = bill.getTotalAmount();

            try {
                classifyBillAmount(amount);
            } catch (IllegalArgumentException validationError) {
                continue;
            }

            yearlyEarnings.put(year, yearlyEarnings.getOrDefault(year, 0.0) + amount);
        }

        for (Map.Entry<Integer, Double> entry : yearlyEarnings.entrySet()) {
            view.getSeriesTotal().getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
        }

        return view.getSeriesTotal();
    }

    // -----------------------------
    // Pie charts (kept methods)
    // -----------------------------

    private void calculatePieChartWeekly() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);

        Map<String, Double> cashierEarnings = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            LocalDate date = startOfWeek.plusDays(i);
            List<Bill> billsForDay = getBillsForDay(date);

            for (Bill bill : billsForDay) {
                String cashierName = bill.getUsername();
                double amount = bill.getTotalAmount();

                try {
                    classifyBillAmount(amount);
                } catch (IllegalArgumentException validationError) {
                    continue;
                }

                cashierEarnings.put(cashierName, cashierEarnings.getOrDefault(cashierName, 0.0) + amount);
            }
        }

        for (Map.Entry<String, Double> entry : cashierEarnings.entrySet()) {
            view.getPieChartWeekly().getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private void calculatePieChartMonthly() {
        LocalDate today = LocalDate.now();
        Month currentMonth = today.getMonth();
        int currentYear = today.getYear();

        Map<String, Double> cashierEarnings = new HashMap<>();

        for (int day = 1; day <= today.lengthOfMonth(); day++) {
            LocalDate date = LocalDate.of(currentYear, currentMonth, day);
            List<Bill> billsForDay = getBillsForDay(date);

            for (Bill bill : billsForDay) {
                String cashierName = bill.getUsername();
                double amount = bill.getTotalAmount();

                try {
                    classifyBillAmount(amount);
                } catch (IllegalArgumentException validationError) {
                    continue;
                }

                cashierEarnings.put(cashierName, cashierEarnings.getOrDefault(cashierName, 0.0) + amount);
            }
        }

        for (Map.Entry<String, Double> entry : cashierEarnings.entrySet()) {
            view.getPieChartMonthly().getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private void calculatePieChartYearly() {
        int currentYear = LocalDate.now().getYear();

        Map<String, Double> cashierEarnings = new HashMap<>();
        List<Bill> allBills = billFileHandler.getBills();

        for (Bill bill : allBills) {
            if (bill.getDateOfSale().getYear() == currentYear) {
                String cashierName = bill.getUsername();
                double amount = bill.getTotalAmount();

                try {
                    classifyBillAmount(amount);
                } catch (IllegalArgumentException validationError) {
                    continue;
                }

                cashierEarnings.put(cashierName, cashierEarnings.getOrDefault(cashierName, 0.0) + amount);
            }
        }

        for (Map.Entry<String, Double> entry : cashierEarnings.entrySet()) {
            view.getPieChartYearly().getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private void calculatePieChartTotal() {
        Map<String, Double> cashierEarnings = new HashMap<>();
        List<Bill> allBills = billFileHandler.getBills();

        for (Bill bill : allBills) {
            String cashierName = bill.getUsername();
            double amount = bill.getTotalAmount();

            try {
                classifyBillAmount(amount);
            } catch (IllegalArgumentException validationError) {
                continue;
            }

            cashierEarnings.put(cashierName, cashierEarnings.getOrDefault(cashierName, 0.0) + amount);
        }

        for (Map.Entry<String, Double> entry : cashierEarnings.entrySet()) {
            view.getPieChartTotal().getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    // -----------------------------
    // Other controller logic (kept)
    // -----------------------------

    public List<Bill> getBillsForDay(LocalDate date) {
        List<Bill> bills = billFileHandler.getBills();
        return bills.stream()
                .filter(bill -> bill.getDateOfSale().isEqual(date))
                .collect(Collectors.toList());
    }

    public boolean matchesSearch(Bill bill, String query) {
        if (query == null || query.isEmpty()) {
            return true;
        }

        return bill.getUsername()
                .toLowerCase()
                .contains(query.toLowerCase());
    }

    public static int classifyBillAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Bill amount cannot be negative");
        }
        if (amount == 0) {
            return 0;
        }
        return 1;
    }
}
