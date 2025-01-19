package Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Cashier extends User implements Serializable {

    private transient ObjectProperty<Sector> sector;

    public Cashier (String username, String password, String fullName, LocalDate dateOfBirth, String phoneNumber, String email, double salary, ObservableList<String> permissions, ObservableList<String> sectors) {
        super(username, password, fullName, dateOfBirth, phoneNumber, email, salary, Access.Cashier, permissions, sectors);
        this.sector = new SimpleObjectProperty<>();
    }

    public Sector getSectors() {
        return sector.get();
    }
    public void setSector(Sector sector) {this.sector.set(sector);}

    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(sector.getValue());
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException,ClassNotFoundException{
        this.sector = new SimpleObjectProperty<Sector>((Sector) inputStream.readObject());
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
