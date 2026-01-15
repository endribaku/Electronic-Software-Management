package Interfaces.DAO;

import Models.Bill;
import javafx.collections.ObservableList;

public interface IBillFileHandler {

    ObservableList<Bill> getBills();

    void insertBill(Bill bill);

    boolean saveBillToFile(Bill bill);
}
