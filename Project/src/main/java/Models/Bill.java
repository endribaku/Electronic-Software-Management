package Models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bill {
    private int billNumber;
    private User cashier;
    private Sector sector;
    private ArrayList<Bill_Item> itemsSold;
    private double totalAmount;
    private LocalDate dateOfSale;
    private static int counter = 0;

    public Bill(int billNumber, Cashier cashier, Sector sector) {
        this.billNumber = counter + 1;
        this.cashier = cashier;
        this.sector = sector;
        this.totalAmount = 0.0;
        this.dateOfSale = LocalDate.now();
    }

    public Bill(){
        this.billNumber = 1;
    }

    public int getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber = billNumber;
    }

    public User getCashier() {
        return cashier;
    }

    public void setCashier(User cashier) {
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

    public void setDateOfSale(LocalDate dateOfSale) {this.dateOfSale = dateOfSale;}

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTotalAmountfromItemsSold() {
        for (Bill_Item item : itemsSold) {
            this.totalAmount += item.getTotalPrice();
        }
    }

    public ArrayList<Bill_Item> getItemsSold() {return itemsSold;}
    public void setItemsSold(ArrayList<Bill_Item> itemsSold) {this.itemsSold = itemsSold;}

    public void addBillItem(Item item, int quantityToReduce) {

        if(item.getQuantity() == 0) {
            String message = String.format("Item %s is out of stock!", item.getName());
            sector.getManager().notifyManager(message);
            return;
        }

        if(quantityToReduce > item.getQuantity()) {
            String message = String.format("Not enough stock for item %s!", item.getName());
            return;
        }

            Bill_Item billItem = new Bill_Item(item, quantityToReduce, item.getSellingPrice());
            item.reduceStock(quantityToReduce);
            itemsSold.add(billItem);
            totalAmount += billItem.getTotalPrice();
            if(item.getQuantity() == 0)
            {
                String message = String.format("Item %s has been sold out!", item.getName());
                sector.getManager().notifyManager(message);
            }

            if(item.getCategory().needsRestocking())
            {
                String message = String.format("Category %s needs restocking!", item.getCategory().getName());
                sector.getManager().notifyManager(message);
            }

    }

//    public Bill_Item getBillItem(Bill_Item item){
//        for (Bill_Item b : itemsSold){
//            if(b.getItem().equals(item.getItem()))
//                return itemsSold.get(itemsSold.indexOf(b));
//        }
//        return null;
//    }

    public String generateBillText() {
        StringBuilder billText = new StringBuilder();

        // Add header
        billText.append("-----------------------------------------\n");
        billText.append("                ELECTRONIC STORE         \n");
        billText.append("-----------------------------------------\n");
        billText.append(String.format("Bill Number:    %d%n", billNumber));
        billText.append(String.format("Cashier ID:   %s%n", cashier.getUserID()));
//        billText.append(String.format("Sector:         %s%n", sector.getSectorName()));
        billText.append(String.format("Date:           %s%n", dateOfSale));
        billText.append("\n");

        // Add items and quantities
        billText.append("-----------------------------------------\n");
        billText.append("Items               Quantity     Price\n");
        billText.append("-----------------------------------------\n");
        for (Bill_Item item : itemsSold) {
            billText.append(String.format("%-20s %-10d %.2f%n",
                    item.getItem().getName(),
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
        return billText.toString();
    }


}
