package Models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bill implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private transient IntegerProperty billNumber;
    private transient StringProperty username;
    private transient ListProperty<Bill_Item> itemsSold;
    private transient DoubleProperty totalAmount;
    private transient ObjectProperty<LocalDate> dateOfSale;
    private static final String BILLS_DIRECTORY = "Project/Data/BillsRepository";
    private static int counter = loadNextBillNumber();

    public Bill(User user, ListProperty<Bill_Item> itemsSold, double totalAmount) {
        this.billNumber = new SimpleIntegerProperty(counter);
        this.username = new SimpleStringProperty(user.getUsername());
        this.itemsSold = new SimpleListProperty<>(itemsSold);
        this.totalAmount = new SimpleDoubleProperty(totalAmount);
        this.dateOfSale = new SimpleObjectProperty<>(LocalDate.now());
    }

    public Bill() {
        this.billNumber = new SimpleIntegerProperty(counter);
        this.username = new SimpleStringProperty();
        this.itemsSold = new SimpleListProperty<>(FXCollections.observableArrayList());
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

    public double getTotalAmountfromItemsSold(ListProperty<Bill_Item> soldItems) throws IllegalArgumentException, IOException {
        if (soldItems == null || soldItems.isEmpty()) {
            return 0.0;
        }
        double calculatedTotal = 0.0;

        for (Bill_Item item : soldItems) {
            if (item == null) {
                throw new IllegalArgumentException("Bill items list contains null item");
            }
            double itemTotal = item.getTotalPrice();

            if (itemTotal < 0) {
                throw new IllegalArgumentException(
                        "Item total price cannot be negative. Item: " + item.getItem().getName());
            }
            calculatedTotal += itemTotal;

            if (calculatedTotal > 1000000.0) {
                throw new IllegalArgumentException(
                        "Total amount exceeds maximum allowed limit of $1,000,000");
            }
        }
        this.totalAmount.set(calculatedTotal);
        return calculatedTotal;
    }

//    public double getTotalAmountfromItemsSold() {
//        for (Bill_Item item : itemsSold) {
//            this.totalAmount.set(this.totalAmount.get() + item.getTotalPrice());
//        }
//        return this.totalAmount.get();
//    }
//
//    public String getUsername() {
//        return username.get();
//    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setUser(User user) {
        this.username.set(user.getUsername());
    }

    // custom method to collect bill nr from text file
    private static int loadNextBillNumber() {
        File directory = new File(BILLS_DIRECTORY);

        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
            return 1;
        }

        int maxBillNumber = 0;
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

        return maxBillNumber + 1;
    }

    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeInt(billNumber.get());
        outputStream.writeUTF(username.get());
        outputStream.writeObject(new ArrayList<>(itemsSold.get()));
        outputStream.writeDouble(totalAmount.get());
        outputStream.writeObject(dateOfSale.get());
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();

        // Deserialize the values and initialize the transient properties
        this.billNumber = new SimpleIntegerProperty(inputStream.readInt());
        this.username = new SimpleStringProperty(inputStream.readUTF());
        List<Bill_Item> itemsSoldList = (List<Bill_Item>) inputStream.readObject();
        this.itemsSold = new SimpleListProperty<>(FXCollections.observableArrayList(itemsSoldList));
        this.totalAmount = new SimpleDoubleProperty(inputStream.readDouble());
        this.dateOfSale = new SimpleObjectProperty<>((LocalDate) inputStream.readObject());
    }

    @Override
    public String toString() {
        return "Bill Nr." + billNumber.get();
    }

    public String getUsername() {
        return username.get();
    }
}
