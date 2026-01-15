package Interfaces.DAO;

import Models.Supplier;
import javafx.collections.ObservableList;

public interface ISuppliersFileHandler {
    ObservableList<Supplier> getSuppliers();
    boolean updateAll();
}
