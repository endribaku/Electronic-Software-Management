package Models;

import DAO.UserFileHandler;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Administrator extends User implements Serializable {

//    private final UserFileHandler  = new UserFileHandler();
    private transient ListProperty<User> employees;
    public Administrator (String username, String password, String fullName, LocalDate dateOfBirth, String phoneNumber, String email, double salary) throws IOException, ClassNotFoundException
    {
        super(username, password, fullName, dateOfBirth, phoneNumber, email, salary, Access.Administrator);
        this.employees = new SimpleListProperty<>();
    }

    public ObservableList<User> getEmployees() {
        return employees.get();
    }

    public ListProperty<User> employeesProperty() {
        return employees;
    }

    public void setEmployees(ObservableList<User> employees) {
        this.employees.set(FXCollections.observableArrayList(employees));;
    }

    //    public User getUser(String username) throws IOException, ClassNotFoundException {
//        return userFileHandler.selectUser(username);
//    }
//
//    public void insertUser(User user) throws IOException, ClassNotFoundException {
//        userFileHandler.insertUser(user);
//        employees = userFileHandler.selectAllUser();
//    }
//
//    public void deleteUser(String username) throws IOException, ClassNotFoundException {
//        userFileHandler.deleteUser(username);
//    }
//
//    public void givePermission(User user, Access accessLevel) {
//        user.setAccessLevel(accessLevel);
//        try {
//            updateUser(user);
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
////    public void revokePermission(User user) {
////        int a = 1;
////    }
//
//    public void updateUser(User user) throws IOException, ClassNotFoundException {
//        userFileHandler.updateUser(user);
//    }
//
//    public void generateTotalIncomeReport(Date startDate, Date endDate) {
//
//    }
//
//    public void generateTotalCostsReport(Date startDate, Date endDate) {
//
//    }
//
//    public ArrayList<User> getAllEmployees() throws IOException, ClassNotFoundException {
//        return userFileHandler.selectAllUser();
//    }

    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException{
        outputStream.defaultWriteObject();
        outputStream.writeInt(employees.size());
        for (User u : employees)
            outputStream.writeObject(u);
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException,ClassNotFoundException{
        int size = inputStream.readInt();
        ListProperty<User> employeesList = new SimpleListProperty<>(FXCollections.observableArrayList());
        for (int i = 0; i < size; i++) {
            employeesList.add((User) inputStream.readObject());
        }
        this.employees = employeesList;
    }
}
