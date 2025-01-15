package Models;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Administrator extends User implements Serializable {
    private ArrayList<User> employees;

    public Administrator (String username, String password, String fullName, LocalDate dateOfBirth, String phoneNumber, String email, double salary) throws IOException, ClassNotFoundException
    {
        super(username, password, fullName, dateOfBirth, phoneNumber, email, salary, Access.Administrator);
        try(ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream("Data\\employees.dat"));){
            while (true){
                employees.add((User)inputStream.readObject());
            }
        }catch (EOFException e){
            System.out.println("All users loaded successfully.");
        }
    }

    public void addUser(User user) throws FileNotFoundException, IOException {
        employees.add(user);
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Data\\employees.dat",true));
        outputStream.writeObject(employees.get(employees.indexOf(user)));
    }

    public void deleteUser(String username) {
        for(User u : employees ){
            if (String.valueOf(u.getUsername()).equals(username)){
                employees.remove(employees.indexOf(u));
            }
        }
    }

    public void givePermission(User user, Access accessLevel) {

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

    public ArrayList<User> getEmployees() {return employees;}
}
