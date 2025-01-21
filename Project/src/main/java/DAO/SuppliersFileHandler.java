package DAO;

import Models.*;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class SuppliersFileHandler {
    public static final String FILE_PATH = "Project/Data/suppliers.dat";
    private static final File DATA_FILE = new File(FILE_PATH);

    private static final ObservableList<Supplier> suppliers = FXCollections.observableArrayList();

//    public static ObservableList<Supplier> getAllSuppliers() {
//        if(suppliers.isEmpty()) {
//            getSuppliers();
//        }
//        return suppliers;
//    }

    public void insertSupplier(Supplier supplier) {
        try(FileOutputStream outputStream = new FileOutputStream(DATA_FILE, true)) {
            ObjectOutputStream writer;
            if (DATA_FILE.length() > 0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(supplier);
            suppliers.add(supplier);
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

    public boolean updateSupplier(String supplierID, String supplierName) {
        boolean updated = false;
        ObservableList<Supplier> currentSuppliers = getSuppliers();

        // Update the specific sector
        for(Supplier s : currentSuppliers) {
            if(s.getSupplierID().equals(supplierID)) {
                currentSuppliers.remove(s);
                s.setName(supplierName);
                currentSuppliers.add(s);
                updated = true;
                break;
            }
        }
        suppliers.clear();
        suppliers.setAll(currentSuppliers);

        // Write the updated list back to the file
        boolean saved = false;
        if(updated) {
            saved = updateAll();
        }
        return(updated && saved);
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
            while(true) {
                if(reader.readObject() instanceof Supplier) {
                    if(((Supplier) reader.readObject()).getName().equals(supplierName)) {
                        return (Supplier) reader.readObject();
                    }
                }
            }
        }
        catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static ObservableList<Supplier> getSuppliers() {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            while(true) {
                Supplier supplier = (Supplier) reader.readObject();
                suppliers.add(supplier);
            }
        }catch(EOFException ignored) {

        }catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return suppliers;
    }
}
