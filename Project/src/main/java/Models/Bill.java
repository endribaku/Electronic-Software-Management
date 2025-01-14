package Models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bill {
    private int billNumber;
    private Cashier cashier;
    private Sector sector;
    private ArrayList<Bill_Item> itemsSold;
    private Double totalAmount;
    private LocalDate dateOfSale;
    private static int counter = 0;

    public Bill(Cashier cashier, Sector sector) {
        this.billNumber = counter + 1;
        this.cashier = cashier;
        this.sector = sector;
        this.totalAmount = 0.0;
        this.dateOfSale = LocalDate.now();
    }

    public int getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber = billNumber;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public void addBillItem(Bill_Item item) {
        itemsSold.add(item);
        totalAmount += item.getTotalPrice();
    }

//    public Bill_Item getBillItem(Bill_Item item){
//        for (Bill_Item b : itemsSold){
//            if(b.getItem().equals(item.getItem()))
//                return itemsSold.get(itemsSold.indexOf(b));
//        }
//        return null;
//    }

    public void generateBillText() {
        StringBuilder billText = new StringBuilder();

        // Add header
        billText.append("-----------------------------------------\n");
        billText.append("                ELECTRONIC STORE         \n");
        billText.append("-----------------------------------------\n");
        billText.append(String.format("Bill Number:    %d%n", billNumber));
        billText.append(String.format("Cashier Name:   %s%n", cashier.getUsername())); // Assuming Cashier has a getName method
        billText.append(String.format("Sector:         %s%n", sector.getSectorName())); // Assuming Sector has a getName method
        billText.append(String.format("Date:           %s%n", dateOfSale));
        billText.append("\n");

        // Add items and quantities
        billText.append("-----------------------------------------\n");
        billText.append("Items               Quantity     Price\n");
        billText.append("-----------------------------------------\n");
        for (Bill_Item item : itemsSold) {
            billText.append(String.format("%-20s %-10d %.2f%n",
                    item.getItem().getName(), // Assuming Item has a getName method
                    item.getQuantity(),
                    item.getTotalPrice()));
        }

        // Add total amount
        billText.append("-----------------------------------------\n");
        billText.append(String.format("Total Price:        $%.2f%n", totalAmount));
        billText.append("-----------------------------------------\n");
        billText.append("         Thank you for shopping with us! \n");
        billText.append("-----------------------------------------\n");

        // Print the bill
        System.out.println(billText.toString());
    }

}
