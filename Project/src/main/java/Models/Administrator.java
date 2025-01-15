package Models;

import DAO.UserFileHandler;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Administrator extends User implements Serializable {
    private final ArrayList<User> employees;
    private final UserFileHandler userFileHandler = new UserFileHandler();

    public Administrator (String username, String password, String fullName, LocalDate dateOfBirth, String phoneNumber, String email, double salary) throws IOException, ClassNotFoundException
    {
        super(username, password, fullName, dateOfBirth, phoneNumber, email, salary, Access.Administrator);
        employees = userFileHandler.selectAllUser();
    }

    public User getUser(String username) throws IOException, ClassNotFoundException {
        return userFileHandler.selectUser(username);
    }

    public void insertUser(User user) throws IOException, ClassNotFoundException {
        employees.add(user);
        userFileHandler.insertUser(user);
    }

    public void deleteUser(String username) throws IOException, ClassNotFoundException {
        userFileHandler.deleteUser(username);
    }

    public void givePermission(User user, Access accessLevel) {
        user.setAccessLevel(accessLevel);
        try {
            updateUser(user);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public void revokePermission(User user) {
//        int a = 1;
//    }

    public void updateUser(User user) throws IOException, ClassNotFoundException {
        userFileHandler.updateUser(user);
    }

    public void generateTotalIncomeReport(Date startDate, Date endDate) {

    }

    public void generateTotalCostsReport(Date startDate, Date endDate) {

    }

    public ArrayList<User> getAllEmployees() throws IOException, ClassNotFoundException {
        return userFileHandler.selectAllUser();
    }
}
