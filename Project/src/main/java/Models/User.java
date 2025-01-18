package Models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class User implements Serializable {
    private transient StringProperty userID;
    private transient StringProperty username;
    private transient StringProperty password;
    private transient StringProperty fullName;
    private transient ObjectProperty<LocalDate> dateOfBirth;
    private transient StringProperty phoneNumber;
    private transient StringProperty email;
    private transient DoubleProperty salary;
    private transient Access accessLevel;
    private transient ListProperty<Permission> permissionList;
    private transient ListProperty<Sector> sector;

    public User(String username, String password, String fullName, LocalDate dateOfBirth, String phoneNumber, String email, double salary, Access accessLevel, List<Permission> permissionList, List<Sector> sector) {
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
        this.permissionList = new SimpleListProperty<>(FXCollections.observableArrayList(permissionList));
        this.sector = new SimpleListProperty<>(FXCollections.observableArrayList(sector));
    }

    public User() {}

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

    public Access getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Access accessLevel) {
        this.accessLevel = accessLevel;
    }

    public ObservableList<Permission> getPermissionList() {
        return permissionList.get();
    }

    public ListProperty<Permission> permissionListProperty() {
        return permissionList;
    }

    public void setPermissionList(ObservableList<Permission> permissionList) {
        this.permissionList.set(permissionList);
    }

    public ObservableList<Sector> getSector() {
        return sector.get();
    }

    public ListProperty<Sector> sectorProperty() {
        return sector;
    }

    public void setSector(ObservableList<Sector> sector) {
        this.sector.set(sector);
    }

    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws  IOException{
        outputStream.defaultWriteObject();
        outputStream.writeUTF(this.userID.getValueSafe());
        outputStream.writeUTF(this.username.getValueSafe());
        outputStream.writeUTF(this.password.getValueSafe());
        outputStream.writeUTF(this.fullName.getValueSafe());
        outputStream.writeObject(this.dateOfBirth.getValue());
        outputStream.writeUTF(this.phoneNumber.getValueSafe());
        outputStream.writeUTF(this.email.getValueSafe());
        outputStream.writeDouble(this.salary.getValue());
        outputStream.writeObject(this.accessLevel.name());
        outputStream.writeObject(new ArrayList<>(this.sector.getValue()));
        outputStream.writeObject(new ArrayList<>(this.permissionList.getValue()));
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        this.userID = new SimpleStringProperty(inputStream.readUTF());
        this.username = new SimpleStringProperty(inputStream.readUTF());
        this.password = new SimpleStringProperty(inputStream.readUTF());
        this.fullName = new SimpleStringProperty(inputStream.readUTF());
        this.dateOfBirth = new SimpleObjectProperty<LocalDate>((LocalDate) inputStream.readObject());
        this.phoneNumber = new SimpleStringProperty(inputStream.readUTF());
        this.email = new SimpleStringProperty(inputStream.readUTF());
        this.salary = new SimpleDoubleProperty(inputStream.readDouble());
        String accessLevelName = (String) inputStream.readObject();
        this.accessLevel = Access.valueOf(accessLevelName);
        this.sector = new SimpleListProperty<Sector>(FXCollections.observableArrayList((ArrayList<Sector>) inputStream.readObject()));
        this.permissionList = new SimpleListProperty<>(FXCollections.observableArrayList((ArrayList<Permission>) inputStream.readObject()));
    }
}
