package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Sector implements Serializable {
    private String sectorName;
    private ArrayList<Cashier> cashiers;
    private Manager manager;

    public Sector(String sectorName, ArrayList<Cashier> cashiers, Manager manager) {
        this.sectorName = sectorName;
        this.cashiers = cashiers;
        this.manager = manager;
    }

    public Sector(String sectorName) {
        this.sectorName = sectorName;
    }

    public ArrayList<Cashier> getCashiers() {
        return cashiers;
    }

    public String getSectorName() {
        return sectorName;
    }

    public Manager getManager() {
        return manager;
    }
}
