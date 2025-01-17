package DAO;

import Models.Bill;
import Models.Bill_Item;
import Models.Cashier;
import Models.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.FileHandler;

public class BillFileHandler {
    private static final String BILLS_DIRECTORY = "Project/Data/RegisteredBills";
    private static final File BILLS_FOLDER = new File(BILLS_DIRECTORY);
    public static String getBillsDirectory() {return BILLS_DIRECTORY;}

    public void saveBillToFile(Bill bill) {
        String billText = bill.generateBillText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = bill.getDateOfSale().format(formatter);
        String filename = "Bill" + bill.getBillNumber() + formattedDate + ".txt";
        try(PrintWriter fileWriter = new PrintWriter(BILLS_DIRECTORY + "/" + filename))
        {
            fileWriter.write(billText);
        } catch (IOException e) {
            System.out.println("Failed to save file: " + filename + " - " + e.getMessage());
        }
    }



    public ObservableList<Bill> getBillsFromDirectory() {
        ObservableList<Bill> bills = FXCollections.observableArrayList();
        try {
            File[] billFiles = BILLS_FOLDER.listFiles();

            for (File billFile : billFiles) {
                if (billFile.getName().endsWith(".txt") && billFile.isFile())
                {
                    bills.add(getBillFromFile(billFile));
                }
            }
        }
        catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
        }
        return bills;
    }

    public Bill getBillFromFile(File billFile) {
        try(Scanner reader =  new Scanner(billFile))
        {
            String line;
            Bill bill = new Bill();
            ArrayList<Bill_Item> items = new ArrayList<>();
            ItemFIleHandler itemFinder = new ItemFIleHandler();

            boolean isInItemsSection = false;

            while((line = reader.nextLine()) != null)
            {
                if(line.startsWith("Bill Number:"))
                {
                    bill.setBillNumber(Integer.parseInt(line.split(":")[1].trim()));
                } else if(line.startsWith("Cashier ID:")) {
                    String cashierId = line.split(":")[1].trim();
                    UserFileHandler cashierSelecter = new UserFileHandler();
                    bill.setCashier((Cashier) cashierSelecter.selectUserFromId(cashierId));
                } else if(line.startsWith("Date of Sale:")) {
                    String dateValue = line.split(":")[1].trim();
                    bill.setDateOfSale(LocalDate.parse(dateValue, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }

                if(line.startsWith("Items")) {
                    isInItemsSection = true;
                    reader.nextLine();
                    continue;
                }
                if (isInItemsSection && line.startsWith("-----------------------------------------")) {
                    isInItemsSection = false; // End of items section
                }

                if(isInItemsSection) {
                    String[] itemData = line.split("\\s{2,}");
                    String itemName = itemData[0].trim();
                    int quantity = Integer.parseInt(itemData[1].trim());
                    double price = Double.parseDouble(itemData[2].trim());


                    Item billItem = itemFinder.selectItem(itemName);
                    Bill_Item billItemToAdd = new Bill_Item(billItem, quantity, price);
                    items.add(billItemToAdd);
                }

                if (line.startsWith("Total Price: ")) {
                    double totalPrice = Double.parseDouble(line.split(":")[1].trim());
                    bill.setTotalAmount(totalPrice);
                }

            }
            bill.setItemsSold(items);
            return bill;
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a numeric value: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("Error reading the bill file: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
            return null;
        }
    }
}
