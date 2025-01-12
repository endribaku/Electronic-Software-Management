package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Administrator extends User implements Serializable {
    private ArrayList<User> employees;

    public Administrator (String username, String password, String fullName, Date dateOfBirth, String phoneNumber, String email, double salary) {
        super(username, password, fullName, dateOfBirth, phoneNumber, email, salary, Access.Administrator);
    }

    public void addUser(User user) {
        int a = 1;
    }

    public void deleteUser(String username) {
        int a = 1;
    }

    public void givePermission(User user, Access accessLevel) {
        int a = 1;
    }

    public void revokePermission(User user) {
        int a = 1;
    }

    public void updateUser(User user) {
        int a = 1;
    }

    public void generateTotalIncomeReport(Date startDate, Date endDate) {

    }

    public void generateTotalCostsReport(Date startDate, Date endDate) {

    }
}
