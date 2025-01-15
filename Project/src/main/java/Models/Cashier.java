package Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Cashier extends User implements Serializable {

    private Sector sector;

    public Cashier (String username, String password, String fullName, LocalDate dateOfBirth, String phoneNumber, String email, double salary) {
        super(username, password, fullName, dateOfBirth, phoneNumber, email, salary, Access.Cashier);
    }

    public Sector getSector() {
        return sector;
    }


    public void createBill() {

    }

    public void trackTodayBills() {

    }

    public double getTotalBills() {
        return 0;
    }
}
