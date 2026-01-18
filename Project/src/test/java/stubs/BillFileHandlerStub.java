package stubs;

import Interfaces.DAO.IBillFileHandler;
import Models.Bill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BillFileHandlerStub implements IBillFileHandler {

    private final ObservableList<Bill> bills = FXCollections.observableArrayList();
    public boolean getBillsCalled = false;
    public boolean insertBillCalled = false;
    public boolean saveBillToFileCalled = false;
    public int getBillsCallCount = 0;
    public Bill lastInsertedBill = null;
    public Bill lastSavedBill = null;

    @Override
    public ObservableList<Bill> getBills() {
        getBillsCalled = true;
        getBillsCallCount++;
        return bills;
    }

    @Override
    public void insertBill(Bill bill) {
        insertBillCalled = true;
        lastInsertedBill = bill;
        bills.add(bill);
    }

    @Override
    public boolean saveBillToFile(Bill bill) {
        saveBillToFileCalled = true;
        lastSavedBill = bill;
        return true;
    }

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    public void reset() {
        getBillsCalled = false;
        insertBillCalled = false;
        saveBillToFileCalled = false;
        getBillsCallCount = 0;
        lastInsertedBill = null;
        lastSavedBill = null;
        bills.clear();
    }
}