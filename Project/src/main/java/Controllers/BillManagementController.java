package Controllers;

import DAO.BillFileHandler;
import Views.BillGenerateView;
import Views.BillManagementView;
import javafx.event.ActionEvent;
import Models.*;

public class BillManagementController {
    private BillManagementView managementView = new BillManagementView();
    private BillGenerateView generateView = new BillGenerateView();
    private BillFileHandler billFileHandler = new BillFileHandler();

    public BillManagementController() {
        this.generateView.getAddToBillButton().setOnAction(e -> onAddToBill(e));
    }

    public BillManagementView getManagementView() {
        return managementView;
    }

    public BillGenerateView getGenerateView() {
        return generateView;
    }

    public BillFileHandler getBillFileHandler() {
        return billFileHandler;
    }

    private void onAddToBill(ActionEvent actionEvent) {
        Item billItem = this.generateView.getItemListView().getSelectionModel().getSelectedItem();

    }
}
