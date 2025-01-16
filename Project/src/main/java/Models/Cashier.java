package Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Cashier extends User implements Serializable {

    private transient ObjectProperty<Sector> sector;

    public Cashier (String username, String password, String fullName, LocalDate dateOfBirth, String phoneNumber, String email, double salary) {
        super(username, password, fullName, dateOfBirth, phoneNumber, email, salary, Access.Cashier);
        this.sector = new SimpleObjectProperty<>();
    }

    public Sector getSector() {
        return sector.get();
    }
    public void setSector(Sector sector) {this.sector.set(sector);}


    public void createBill() {

    }

    public void trackTodayBills() {

    }

    public double getTotalBills() {
        return 0;
    }
}
