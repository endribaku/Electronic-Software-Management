package DAO;

import Models.Bill;
import Models.Bill_Item;
import Models.Cashier;
import Models.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class BillFileHandler {
    private static final String BILLS_DIRECTORY = "Project/Data/BillsRepository";
    private static final String FILE_PATH = "Project/Data/bills.dat";
    private static final File BILLS_FOLDER = new File(BILLS_DIRECTORY);
    private static final File DATA_FILE = new File(FILE_PATH);
    public static String getBillsTextFiles() {return BILLS_DIRECTORY;}
    public static String getBillsFilePath() {return FILE_PATH;}
    private static final ObservableList<Bill> bills = FXCollections.observableArrayList();
    private static final ObservableList<Bill_Item> billItems = FXCollections.observableArrayList();

    public static ObservableList<Bill> getBills() {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            while(true) {
                Bill bill = (Bill) reader.readObject();
                bills.add(bill);
            }
        }catch(EOFException eof) {

        }catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return bills;
    }

    public static void insertBill(Bill bill) {
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

    public static void deleteBill(Bill bill) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            bills.remove(bill);
            for(Bill b : bills) {
                outputStream.writeObject(b);
            }
        } catch(EOFException eofe) {

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteAllBills(ArrayList<Bill> billsToRemove) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))){
            for(Bill b : bills) {
                if(!billsToRemove.contains(b)) {
                    outputStream.writeObject(b);
                }
            }
            bills.removeAll(billsToRemove);
        } catch(IOException ex) {
            ex.getMessage();
        }
    }

    public static boolean updateAllBills() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for(Bill b : bills) {
                outputStream.writeObject(b);
            }
            return true;
        } catch (IOException ex) {
            ex.getMessage();
            return false;
        }
    }

    public static Bill selectBillbyNumber(int billNumber) {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            while(true) {
                if(reader.readObject() instanceof Bill) {
                    if(((Bill) reader.readObject()).getBillNumber() == billNumber) {
                        return (Bill) reader.readObject();
                    }
                }
            }
        }catch(EOFException ignored) {

        }catch(IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Bill not found. Returning null.");
        return null;
    }

    public static Bill selectBillbyDate(LocalDate date) {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            while(true) {
                if(reader.readObject() instanceof Bill) {
                    if(((Bill) reader.readObject()).getDateOfSale().isEqual(date)) {
                        return (Bill) reader.readObject();
                    }
                }
            }
        }catch (EOFException eof) {

        }catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Bill not found. Returning null.");
        return null;
    }

    public static Bill selectBillByCashierName(String cashierName) {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            while(true) {
                if(reader.readObject() instanceof Bill) {
                    if(((Bill) reader.readObject()).getUser().getUsername().equals(cashierName)) {
                        return (Bill) reader.readObject();
                    }
                }
            }
        }catch (EOFException eof) {

        }catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Bill not found. Returning null.");
        return null;
    }

    public boolean saveBillToFile(Bill bill) {
        String billText = generateBillText(bill);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = bill.getDateOfSale().format(formatter);
        String filename = "Bill" + bill.getBillNumber() + ".txt";
        try(PrintWriter fileWriter = new PrintWriter(BILLS_DIRECTORY + "/" + filename))
        {
            fileWriter.write(billText);
            return true;
        } catch (IOException e) {
            System.out.println("Failed to save file: " + filename + " - " + e.getMessage());
            return false;
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
        try(Scanner reader =  new Scanner(billFile)) {
            Bill bill = new Bill();
            String line;
            ObservableList<Bill_Item> items = FXCollections.observableArrayList();
            ItemFileHandler itemFinder = new ItemFileHandler();

            boolean isInItemsSection = false;

            while((line = reader.nextLine()) != null)
            {
                if(line.startsWith("Bill Number:")) {
                    bill.setBillNumber(Integer.parseInt(line.split(":")[1].trim()));
                }  else if(line.startsWith("Cashier ID:"))
                {
                    String cashierId = line.split(":")[1].trim();
                    UserFileHandler cashierSelecter = new UserFileHandler();
                    bill.setUser((Cashier) cashierSelecter.selectUserFromId(cashierId));
                }
                else if(line.startsWith("Cashier User Name:")) {
                    String cashierName = line.split(":")[1].trim();
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
                    Bill_Item billItemToAdd = new Bill_Item(billItem, quantity);
                    items.add(billItemToAdd);
                }

                if (line.startsWith("Total Price: ")) {
                    String priceString = line.split(":")[1].trim();
                    priceString = priceString.replace("$", "");
                    double totalPrice = Double.parseDouble(priceString);
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



    public String generateBillText(Bill bill) {
        StringBuilder billText = new StringBuilder();

        // Add header
        billText.append("-----------------------------------------\n");
        billText.append("                ELECTRONIC STORE         \n");
        billText.append("-----------------------------------------\n");
        billText.append(String.format("Bill Number:    %d%n", bill.getBillNumber()));
        billText.append(String.format("Cashier Name:   %s%n", bill.getUser().getUsername()));
        billText.append(String.format("Date:           %s%n", bill.getDateOfSale().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        billText.append("%n");

        // Add items and quantities
        billText.append("-----------------------------------------\n");
        billText.append("Items               Quantity     Price\n");
        billText.append("-----------------------------------------\n");
        for (Bill_Item item : bill.itemsSoldProperty()) {
            billText.append(String.format("%-20s %-10d %.2f%n",
                    item.getItem().getName(),
                    item.getQuantity(),
                    item.getTotalPrice()));
        }

        // Add total amount
        billText.append("-----------------------------------------\n");
        billText.append(String.format("Total Price:        $%.2f%n", bill.getTotalAmount()));
        billText.append("-----------------------------------------\n");
        billText.append("         Thank you for shopping with us! \n");
        billText.append("-----------------------------------------\n");

        // Print the bill
        System.out.println(billText.toString());
        return billText.toString();
    }

    public static ObservableList<Bill_Item> getBillItems() {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            while(true) {
                Bill_Item billItem = (Bill_Item) reader.readObject();
                billItems.add(billItem);
            }
        }catch(EOFException eof) {

        }catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return billItems;
    }

    public static void insertBillItem(Bill_Item billItem) {
        try(FileOutputStream outputStream = new FileOutputStream(DATA_FILE, true)) {
            ObjectOutputStream writer;
            if (DATA_FILE.length() > 0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(billItem);
            billItems.add(billItem);
        } catch(IOException ioe) {
            ioe.getMessage();
        }
    }

    public static void deleteBillItem(Bill_Item billItem) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            billItems.remove(billItem);
            for(Bill_Item b : billItems) {
                outputStream.writeObject(b);
            }
        } catch(EOFException eofe) {

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteAllBillItem(ArrayList<Bill_Item> billItemsToRemove) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))){
            for(Bill_Item b : billItems) {
                if(!billItemsToRemove.contains(b)) {
                    outputStream.writeObject(b);
                }
            }
            bills.removeAll(billItemsToRemove);
        } catch(IOException ex) {
            ex.getMessage();
        }
    }

    public static boolean updateAllBillItems() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for(Bill_Item b : billItems) {
                outputStream.writeObject(b);
            }
            return true;
        } catch (IOException ex) {
            ex.getMessage();
            return false;
        }
    }
}
