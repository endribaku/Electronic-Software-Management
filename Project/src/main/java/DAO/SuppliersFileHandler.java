package DAO;

import Interfaces.DAO.ISuppliersFileHandler;
import Models.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class SuppliersFileHandler implements ISuppliersFileHandler  {

    public static final String FILE_PATH = "Project/Data/suppliers.dat";

    private final File dataFile;

    private final ObservableList<Supplier> suppliers = FXCollections.observableArrayList();

    public SuppliersFileHandler() {
        this(new File(FILE_PATH));
    }

    public SuppliersFileHandler(File dataFile) {
        this.dataFile = dataFile;
    }

    public void insertSupplier(Supplier supplier) {
        try (FileOutputStream outputStream = new FileOutputStream(dataFile, true)) {
            ObjectOutputStream writer;
            if (dataFile.length() > 0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(supplier);
            suppliers.add(supplier);
        } catch (IOException ioe) {
            ioe.getMessage();
        }
    }

    public void deleteSupplier(Supplier supplier){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            suppliers.remove(supplier);
            for (Supplier s : suppliers) {
                outputStream.writeObject(s);
            }
        } catch (EOFException eofe) {

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void deleteAll(ArrayList<Supplier> suppliersToRemove) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            for (Supplier s : suppliers) {
                if (suppliers.containsAll(suppliersToRemove)) {
                    suppliers.removeAll(suppliersToRemove);
                } else if (suppliers.contains(s)) {
                    suppliers.remove(s);
                }
            }
            for (Supplier s : suppliers) {
                outputStream.writeObject(s);
            }
        } catch (IOException exception) {
            exception.getMessage();
        }
    }

    public boolean updateSupplier(String supplierID, String supplierName) {
        boolean updated = false;
        ObservableList<Supplier> currentSuppliers = getSuppliers();

        for (Supplier s : currentSuppliers) {
            if (s.getSupplierID().equals(supplierID)) {
                currentSuppliers.remove(s);
                s.setName(supplierName);
                currentSuppliers.add(s);
                updated = true;
                break;
            }
        }
        suppliers.clear();
        suppliers.setAll(currentSuppliers);

        boolean saved = false;
        if (updated) {
            saved = updateAll();
        }
        return (updated && saved);
    }

    public boolean updateAll() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            for (Supplier s : suppliers) {
                outputStream.writeObject(s);
            }
            return true;
        } catch (IOException exception) {
            exception.getMessage();
            return false;
        }
    }

    public Supplier selectSupplier(String supplierName){
        if (!dataFile.exists()) {
            return null;
        }

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(dataFile))) {
            while (true) {
                Object obj = reader.readObject();
                if (obj instanceof Supplier supplier) {
                    if (supplier.getName().equals(supplierName)) {
                        return supplier;
                    }
                }
            }
        }
        catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public ObservableList<Supplier> getSuppliers() {
        suppliers.clear();

        if (!dataFile.exists()) {
            return suppliers;
        }

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(dataFile))) {
            while (true) {
                Supplier supplier = (Supplier) reader.readObject();
                suppliers.add(supplier);
            }
        } catch (EOFException ignored) {

        } catch (IOException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
        return suppliers;
    }
}
