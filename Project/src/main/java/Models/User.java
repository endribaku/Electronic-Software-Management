package Models;

import java.io.Serializable;
import java.util.Date;

public abstract class User implements Serializable {

    private String username;
    private String password;
    private String fullName;
    private Date dateOfBirth;
    private String phoneNumber;
    private String email;
    private double salary;
    private Access accessLevel;

    protected User(String username, String password, String fullName, Date dateOfBirth, String phoneNumber, String email, double salary, Access accessLevel) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.salary = salary;
        this.accessLevel = accessLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Access getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Access accessLevel) {
        this.accessLevel = accessLevel;
    }

    public boolean login(String username, String password) {
        return false;
    }

    public void logout() {
        int a = 1;
    }
}
