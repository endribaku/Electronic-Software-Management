package Models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient IntegerProperty billNumber;
    private transient ObjectProperty<User> user;
    private transient ListProperty<Bill_Item> itemsSold;
    private transient DoubleProperty totalAmount;
    //private transient ObjectProperty<Sector> sector;
    private transient ObjectProperty<LocalDate> dateOfSale;
    private static final String BILLS_DIRECTORY = "Project/Data/BillsRepository";
    private static int counter = loadNextBillNumber();

    public Bill(User user, ListProperty<Bill_Item> itemsSold, double totalAmount) {
        this.billNumber = new SimpleIntegerProperty(counter);
        this.user = new SimpleObjectProperty<>(user);
        this.itemsSold = new SimpleListProperty<Bill_Item>(itemsSold);
        this.totalAmount = new SimpleDoubleProperty(totalAmount);
        this.dateOfSale = new SimpleObjectProperty<>(LocalDate.now());
    }

    public Bill() {
        this.billNumber = new SimpleIntegerProperty(counter);
        this.user = new SimpleObjectProperty<>();
        this.itemsSold = new SimpleListProperty<Bill_Item>(FXCollections.observableArrayList());
        this.totalAmount = new SimpleDoubleProperty();
        this.dateOfSale = new SimpleObjectProperty<>(LocalDate.now());
        counter++;
    }

    public int getBillNumber() {
        return billNumber.get();
    }

    public IntegerProperty billNumberProperty() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber.set(billNumber);
    }

    public User getUser() {
        return user.get();
    }

    public ObjectProperty<User> userProperty() {
        return user;
    }

    public void setUser(User user) {
        this.user.set(user);
    }

    public ListProperty<Bill_Item> itemsSoldProperty() {
        return itemsSold;
    }

    public void setItemsSold(ObservableList<Bill_Item> itemsSold) {
        this.itemsSold.set(itemsSold);
    }

    public double getTotalAmount() {
        return totalAmount.get();
    }

    public DoubleProperty totalAmountProperty() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount.set(totalAmount);
    }

//    public Sector getSector() {
//        return sector.get();
//    }
//
//    public ObjectProperty<Sector> sectorProperty() {
//        return sector;
//    }
//
//    public void setSector(Sector sector) {
//        this.sector.set(sector);
//    }

    public LocalDate getDateOfSale() {
        return dateOfSale.get();
    }

    public ObjectProperty<LocalDate> dateOfSaleProperty() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale.set(dateOfSale);
    }

    public void setTotalAmountfromItemsSold() {
        for (Bill_Item item : itemsSold) {
            this.totalAmount.set(this.totalAmount.get() + item.getTotalPrice());
        }
    }

    public ObservableList<Bill_Item> getItemsSold() {
        return itemsSold.get();
    }

    public double getTotalAmountfromItemsSold() {
        for (Bill_Item item : itemsSold) {
            this.totalAmount.set(this.totalAmount.get() + item.getTotalPrice());
        }
        return this.totalAmount.get();
    }

//    public void addBillItem(Item item, int quantityToReduce) {
//
//        if(item.getQuantity() == 0) {
//            String message = String.format("Item %s is out of stock!", item.getName());
//            //sector.getManager().notifyManager(message);
//            return;
//        }
//
//        if(quantityToReduce > item.getQuantity()) {
//            String message = String.format("Not enough %s items in stock!", item.getName());
//            return;
//        }
//
//            Bill_Item billItem = new Bill_Item(item, quantityToReduce, item.getSellingPrice());
//            item.reduceStock(quantityToReduce);
//            itemsSold.add(billItem);
//            totalAmount += billItem.getTotalPrice();
//            if(item.getQuantity() == 0)
//            {
//                String message = String.format("Item %s has been sold out!", item.getName());
//                //sector.getManager().notifyManager(message);
//            }
//
//            if(item.needsRestocking())
//            {
//                String message = String.format("Category %s needs restocking!", item.getCategory());
//                //sector.getManager().notifyManager(message);
//            }
//
//    }

//    public Bill_Item getBillItem(Bill_Item item){
//        for (Bill_Item b : itemsSold){
//            if(b.getItem().equals(item.getItem()))
//                return itemsSold.get(itemsSold.indexOf(b));
//        }
//        return null;
//    }

//    public String generateBillText() {
//        StringBuilder billText = new StringBuilder();
//
//        // Add header
//        billText.append("-----------------------------------------\n");
//        billText.append("                ELECTRONIC STORE         \n");
//        billText.append("-----------------------------------------\n");
//        billText.append(String.format("Bill Number:    %d%n", billNumber));
//        billText.append(String.format("Cashier ID:   %s%n", user.getUser()));
////        billText.append(String.format("Sector:         %s%n", sector.getSectorName()));
//        billText.append(String.format("Date:           %s%n", dateOfSale));
//        billText.append("\n");
//
//        // Add items and quantities
//        billText.append("-----------------------------------------\n");
//        billText.append("Items               Quantity     Price\n");
//        billText.append("-----------------------------------------\n");
//        for (Bill_Item item : itemsSold) {
//            billText.append(String.format("%-20s %-10d %.2f%n",
//                    item.getItem().getName(),
//                    item.getQuantity(),
//                    item.getTotalPrice()));
//        }
//
//        // Add total amount
//        billText.append("-----------------------------------------\n");
//        billText.append(String.format("Total Price:        $%.2f%n", totalAmount));
//        billText.append("-----------------------------------------\n");
//        billText.append("         Thank you for shopping with us! \n");
//        billText.append("-----------------------------------------\n");
//
//        // Print the bill
//        System.out.println(billText.toString());
//        return billText.toString();
//    }


    // custom method to collect bill nr from text file
    private static int loadNextBillNumber() {
        File directory = new File(BILLS_DIRECTORY);

        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs(); // Create directory if it doesn't exist
            return 1; // Start at 1 if no bills exist
        }

        int maxBillNumber = 0;
        // Updated regex to match "Bill{number}{date}.txt"
        Pattern pattern = Pattern.compile("Bill(\\d+)\\d{2}-\\d{2}-\\d{4}\\.txt");

        for (File file : directory.listFiles()) {
            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.matches()) {
                int billNumber = Integer.parseInt(matcher.group(1));
                if (billNumber > maxBillNumber) {
                    maxBillNumber = billNumber;
                }
            }
        }

        return maxBillNumber + 1; // Start at the next number
    }

    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeInt(billNumber.get());
        outputStream.writeObject(user.get());
        outputStream.writeObject(new ArrayList<>(itemsSold));
        outputStream.writeDouble(totalAmount.get());
        outputStream.writeObject(dateOfSale.get());
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();

        // Deserialize the values and initialize the transient properties
        this.billNumber = new SimpleIntegerProperty(inputStream.readInt());
        this.user = new SimpleObjectProperty<>((User) inputStream.readObject());
        List<Bill_Item> itemsSoldList = (List<Bill_Item>) inputStream.readObject();
        this.itemsSold = new SimpleListProperty<>(FXCollections.observableArrayList(itemsSoldList));
        this.totalAmount = new SimpleDoubleProperty(inputStream.readDouble());
        this.dateOfSale = new SimpleObjectProperty<>((LocalDate) inputStream.readObject());
    }

    @Override
    public String toString() {
        return "Bill Nr." + billNumber.get();
    }

}
