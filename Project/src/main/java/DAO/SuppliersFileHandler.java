package DAO;

import Models.Category;
import Models.Supplier;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class SuppliersFileHandler {
    public static final String FILE_PATH = "Project/Data/suppliers.dat";
    private static final File DATA_FILE = new File(FILE_PATH);
    private final ObservableList<Supplier> suppliers = FXCollections.observableArrayList();

    public ObservableList<Supplier> getAllSuppliers() {
        if(suppliers.isEmpty()) {
            selectAllSuppliers();
        }
        return suppliers;
    }

    public void insertSupplier(Supplier supplier) {
        try(FileOutputStream outputStream = new FileOutputStream(DATA_FILE, true)) {
            ObjectOutputStream writer;
            if (DATA_FILE.length() > 0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(supplier);
        } catch(IOException ioe) {
            ioe.getMessage();
        }
    }

    public void deleteSupplier(Supplier supplier){
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            suppliers.remove(supplier);
            for(Supplier s : suppliers) {
                outputStream.writeObject(s);
            }
        } catch(EOFException eofe) {

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteAll(ArrayList<Supplier> suppliersToRemove) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))){
            for(Supplier s : suppliers) {
                if (suppliers.containsAll(suppliersToRemove)) {
                    suppliers.removeAll(suppliersToRemove);
                } else if (suppliers.contains(s)) {
                    suppliers.remove(s);
                }
            }
            for(Supplier s : suppliers) {
                outputStream.writeObject(s);
            }
        } catch(IOException ex) {
            ex.getMessage();
        }
    }

    public boolean updateAll() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for(Supplier s : suppliers) {
                outputStream.writeObject(s);
            }
            return true;
        } catch (IOException ex) {
            ex.getMessage();
            return false;
        }
    }

    public Supplier selectSupplier(String supplierName){
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Supplier supplier;
            while(true) {
                supplier = (Supplier) reader.readObject();
                if(supplier.getName().equals(supplierName))
                    return supplier;
            }
        }
        catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void selectAllSuppliers() {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Supplier supplier;
            while(true) {
                supplier = (Supplier) reader.readObject();
                suppliers.add(supplier);
            }
        }
        catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
