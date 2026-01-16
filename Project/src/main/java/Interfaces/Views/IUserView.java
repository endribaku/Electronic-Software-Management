package Interfaces.Views;

import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public interface IUserView {

    BorderPane getRoot();

    // Home + UI containers
    HBox getHomePage();
    FlowPane getButtonGrid();
    VBox getSidebar();

    // Labels (sidebar)
    Label getHomeLabel();
    Label getInventoryLabel();
    Label getEmployeeLabel();
    Label getPerformanceLabel();
    Label getBillGenerateLabel();
    Label getSuppliersLabel();
    Label getProfileLabel();

    // Welcome label
    Label getHomeWelcomeLabel();

    // Menu bar
    Menu getMenu();
    MenuItem getHomeItem();
    MenuItem getInventoryItem();
    MenuItem getEmployeeItem();
    MenuItem getExitItem();

    Menu getBillMenu();
    MenuItem getNewBillItem();
    MenuItem getViewPerformanceItem();
    MenuItem getViewBillItem();

    Menu getProfileMenu();
    MenuItem getProfileItem();
    MenuItem getLogoutItem();

    // Home buttons
    Button getGenerateBillButton();
    Button getManageEmployeeButton();
    Button getViewPerformanceButton();
    Button getManageSuppliersButton();
    Button getManageInventoryButton();
    Button getProfileButton();

    // Chart
    PieChart getPieChart();
}
