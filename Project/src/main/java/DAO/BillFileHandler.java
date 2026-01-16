package DAO;

import Interfaces.DAO.IBillFileHandler;
import Models.Bill;
import Models.Bill_Item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.format.DateTimeFormatter;


public class BillFileHandler implements IBillFileHandler {
    private static final String BILLS_DIRECTORY = "Project/Data/BillsRepository";
    private static final String FILE_PATH = "Project/Data/bills.dat";
    private static final File DATA_FILE = new File(FILE_PATH);
    private static final ObservableList<Bill> bills = FXCollections.observableArrayList();
    public static final String SEPARATOR =
            "-----------------------------------------\n";

    @Override
    public ObservableList<Bill> getBills() {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            bills.clear();
            while(true) {
                Object obj = reader.readObject();
                if (obj instanceof Bill) {
                    Bill bill = (Bill) obj;
                    bills.add(bill);
                }
            }
        }catch(EOFException eof) {

        }catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return bills;
    }

    @Override
    public void insertBill(Bill bill) {
        try(FileOutputStream outputStream = new FileOutputStream(DATA_FILE, true)) {
            ObjectOutputStream writer;
            if (DATA_FILE.length() > 0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(bill);
            bills.add(bill);
        } catch(IOException ioe) {
            ioe.getMessage();
        }
    }

    public boolean saveBillToFile(Bill bill) {
        String billText = generateBillText(bill);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = bill.getDateOfSale().format(formatter);
        String filename = "Bill" + bill.getBillNumber() + formattedDate +  ".txt";
        try(PrintWriter fileWriter = new PrintWriter(BILLS_DIRECTORY + "/" + filename))
        {
            fileWriter.write(billText);
            return true;
        } catch (IOException e) {
            System.out.println("Failed to save file: " + filename + " - " + e.getMessage());
            return false;
        }
    }



    public String generateBillText(Bill bill) {
        StringBuilder billText = new StringBuilder();

        // Add header
        billText.append(SEPARATOR);
        billText.append("                ELECTRONIC STORE         \n");
        billText.append(SEPARATOR);
        billText.append(String.format("Bill Number:    %d%n", bill.getBillNumber()));
        billText.append(String.format("Cashier Name:   %s%n", bill.getUsername()));
        billText.append(String.format("Date:           %s%n", bill.getDateOfSale().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        billText.append("%n");

        // Add items and quantities
        billText.append(SEPARATOR);
        billText.append("Items               Quantity     Price\n");
        billText.append(SEPARATOR);
        for (Bill_Item item : bill.itemsSoldProperty()) {
            billText.append(String.format("%-20s %-10d %.2f%n",
                    item.getItem().getName(),
                    item.getQuantity(),
                    item.getTotalPrice()));
        }

        // Add total amount
        billText.append(SEPARATOR);
        billText.append(String.format("Total Price:        $%.2f%n", bill.getTotalAmount()));
        billText.append(SEPARATOR);
        billText.append("         Thank you for shopping with us! \n");
        billText.append(SEPARATOR);

        // Print the bill
        return billText.toString();
    }










}
