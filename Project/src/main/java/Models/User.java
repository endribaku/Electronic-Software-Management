package Models;

import javafx.beans.property.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


public abstract class User implements Serializable {
    private transient StringProperty userID;
    private transient StringProperty username;
    private transient StringProperty password;
    private transient StringProperty fullName;
    private transient ObjectProperty<LocalDate> dateOfBirth;
    private transient StringProperty phoneNumber;
    private transient StringProperty email;
    private transient DoubleProperty salary;
    private transient Access accessLevel;

    protected User(String username, String password, String fullName, LocalDate dateOfBirth, String phoneNumber, String email, double salary, Access accessLevel) {
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

    public Access getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Access accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void logout() {
        int a = 1;
    }

}
