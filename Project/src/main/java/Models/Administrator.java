package Models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;

public class Administrator extends User implements Serializable {

    private transient ListProperty<User> employees;
    public Administrator (String username, String password, String fullName, LocalDate dateOfBirth, String phoneNumber, String email, double salary, ObservableList<String> permissions, ObservableList<String> sectors)
    {

        super(username, password, fullName, dateOfBirth, phoneNumber, email, salary, Access.Administrator, permissions, sectors);
        this.employees = new SimpleListProperty<>();
    }

    public ObservableList<User> getEmployees() {
        return employees.get();
    }

    public ListProperty<User> employeesProperty() {
        return employees;
    }

    public void setEmployees(ObservableList<User> employees) {
        this.employees.set(FXCollections.observableArrayList(employees));
    }


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
