package Models;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.EnumSet;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient StringProperty userID;
    private transient StringProperty username;
    private transient StringProperty password;
    private transient StringProperty fullName;
    private transient ObjectProperty<LocalDate> dateOfBirth;
    private transient StringProperty phoneNumber;
    private transient StringProperty email;
    private transient DoubleProperty salary;
    private transient Access accessLevel;
    private EnumSet<Permission> permissions;
    private List<String> sector;

    public User(String username, String password, String fullName, LocalDate dateOfBirth, String phoneNumber, String email, double salary,
                Access accessLevel, ObservableList<String> permissions, ObservableList<String> sectorList) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.fullName = new SimpleStringProperty(fullName);
        this.dateOfBirth = new SimpleObjectProperty<>(dateOfBirth != null ? dateOfBirth : LocalDate.now());
        if(dateOfBirth == null)
            this.dateOfBirth.set(LocalDate.now());
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.salary = new SimpleDoubleProperty(salary);
        this.accessLevel = accessLevel;
        this.userID = new SimpleStringProperty(UUID.randomUUID().toString());
        this.permissions = EnumSet.noneOf(Permission.class); // Initialize the EnumSet
        for (String permissionString : permissions) {
            try {
                Permission permission = Permission.valueOf(permissionString); // Case-sensitive match
                this.permissions.add(permission);
            } catch (IllegalArgumentException _) {

                System.err.println("Invalid permission: " + permissionString);
            }
        }
        this.sector = new ArrayList<>(sectorList);
    }


    public User(String username, Access accessLevel) {
        this.username = new SimpleStringProperty(username);
        this.accessLevel = accessLevel;
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public StringProperty userIDProperty() {
        return userID;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public ObjectProperty<LocalDate> dateOfBirthProperty() {
        return dateOfBirth;
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public DoubleProperty salaryProperty() {
        return salary;
    }

    public String getUserID() {return userID.get();}

    public void setUserID(String userID) {this.userID.set(userID);}

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getFullName() {
        return fullName.get();
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth.get();
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public double getSalary() {
        return salary.get();
    }

    public void setSalary(double salary) {
        this.salary.set(salary);
    }

    public Access getAccessLevel() {return accessLevel;}

    public void setAccessLevel(Access accessLevel) {this.accessLevel = accessLevel;}

    public EnumSet<Permission> getPermissions() {return permissions;}

    public void setPermissions(EnumSet<Permission> permissions) {this.permissions = permissions;}

    public List<String> getSector() {return sector;}

    public void setSector(List<String> sector) {this.sector = sector;}

    public void addSector(String sector) {this.sector.add(sector);}

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // Serialize non-transient fields
        out.writeObject(userID.get());
        out.writeObject(username.get());
        out.writeObject(password.get());
        out.writeObject(fullName.get());
        out.writeObject(dateOfBirth.get());
        out.writeObject(phoneNumber.get());
        out.writeObject(email.get());
        out.writeDouble(salary.get());
        out.writeObject(accessLevel);
    }
    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Deserialize non-transient fields
        this.userID = new SimpleStringProperty((String) in.readObject());
        this.username = new SimpleStringProperty((String) in.readObject());
        this.password = new SimpleStringProperty((String) in.readObject());
        this.fullName = new SimpleStringProperty((String) in.readObject());
        this.dateOfBirth = new SimpleObjectProperty<>((LocalDate) in.readObject());
        this.phoneNumber = new SimpleStringProperty((String) in.readObject());
        this.email = new SimpleStringProperty((String) in.readObject());
        this.salary = new SimpleDoubleProperty(in.readDouble());
        this.accessLevel = (Access) in.readObject();
    }

    @Override
    public String toString()
    {
        return getFullName();
    }

}
