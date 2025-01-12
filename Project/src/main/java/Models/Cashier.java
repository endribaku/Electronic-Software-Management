package Models;

import java.io.Serializable;
import java.util.Date;

public class Cashier extends User implements Serializable {

    private String cashierID;
    private Sector sector;

    public Cashier (String username, String password, String fullName, Date dateOfBirth, String phoneNumber, String email, double salary) {
        super(username, password, fullName, dateOfBirth, phoneNumber, email, salary, Access.Cashier);
    }

    public Sector getSector() {
        return sector;
    }

    public String getCashierID() {
        return cashierID;
    }

    public void createBill() {

    }

    public void trackTodayBills() {

    }

    public double getTotalBills() {
        return 0;
    }
}
