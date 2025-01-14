package Models;

import java.io.Serializable;
import java.util.Date;

public class Cashier extends User implements Serializable {

    private Sector sector;

    public Cashier (String username, String password, String fullName, Date dateOfBirth, String phoneNumber, String email, double salary) {
        super(username, password, fullName, dateOfBirth, phoneNumber, email, salary, Access.Cashier);
    }

    public Sector getSector() {
        return sector;
    }


    public Bill createBill() {
        return new Bill(this, this.sector);
    }

    public void trackTodayBills() {

    }

    public double getTotalBills() {
        return 0;
    }
}
