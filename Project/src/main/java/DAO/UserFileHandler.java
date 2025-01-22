package DAO;

import Models.Access;
import Models.Permission;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class UserFileHandler {

    public static final String FILE_PATH = "Project/Data/employees.dat";
    private static final File DATA_FILE = new File(FILE_PATH);

    public ObservableList<User> getUsers() {
        return users;
    }

    private final ObservableList<User> users = FXCollections.observableArrayList();

    public UserFileHandler() {
        selectAllUser();
    }

    public ObservableList<User> getAllUsers() {
        if(users.isEmpty()) {
            selectAllUser();
        }
        return users;
    }

    public void insertUser(User user) {
        try(FileOutputStream outputStream = new FileOutputStream(DATA_FILE, true)) {
            ObjectOutputStream writer;
            if (DATA_FILE.length() > 0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(user);
            users.add(user);
        } catch(IOException ioe) {
            ioe.getMessage();
        }
    }

    public boolean deleteUser(User user) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for(User u : users) {
                if(!u.equals(user))
                    outputStream.writeObject(u);
            }
            users.remove(user);
            return true;
        } catch (IOException ex) {
            ex.getMessage();
            return false;
        }
    }

    public void deleteAll(ArrayList<User> usersToRemove) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))){
            for(User u : users) {
                if (!usersToRemove.contains(u)) {
                    outputStream.writeObject(u);
                }
            }
            users.removeAll(usersToRemove);
        } catch(IOException ex) {
            ex.getMessage();
        }
    }

    public boolean updateUser(String username, String password, String fullName, LocalDate dob, String pNumber, String email, double salary, Access accessLevel, ObservableList<String> permissions, List<String> sector) {
        boolean updated = false;

        // Update the specific sector
        for(User u : users) {
            if(u.getUsername().equals(username)) {

//                u.setPassword(password);
//                u.setFullName(fullName);
//                u.setDateOfBirth(dob);
//                u.setPhoneNumber(pNumber);
//                u.setEmail(email);
//                u.setSalary(salary);
//                u.setAccessLevel(accessLevel);

                EnumSet<Permission> permissionsSet = EnumSet.noneOf(Permission.class);
                for (String permissionString : permissions) {
                    try {
                        Permission permission = Permission.valueOf(permissionString);
                        permissionsSet.add(permission);
                    } catch (IllegalArgumentException e) {

                        System.err.println("Invalid permission: " + permissionString);
                    }
                }
//                u.setPermissions(permissions);
//                u.setSector(sector);
                User newUser = new User(username, password, fullName, dob, pNumber, email, salary, accessLevel, permissions, (ObservableList<String>) sector);
                users.set(users.indexOf(u), newUser);
                System.out.println("User updated successfully");
                updated = true;
                break;
            }
        }

        // Write the updated list back to the file
        boolean saved = false;
        if(updated) {
            saved = updateAll(users);
            System.out.println("Users saved in file");
        } else {
            System.out.println("Users not saved in file");
        }
        return(updated && saved);
    }

    public boolean updateProfile(String username, String fullName, String email, String password, String phoneNumber, LocalDate dateOfBirth) {
        boolean updated = false;

        // Update the specific sector
        for(User u : users) {
            if(u.getUsername().equals(username)) {
                users.remove(u);
                u.setPassword(password);
                u.setFullName(fullName);
                u.setDateOfBirth(dateOfBirth);
                u.setPhoneNumber(phoneNumber);
                u.setEmail(email);
                users.add(u);
                updateAll(users);
                System.out.println("User updated successfully");
                updated = true;
                break;
            }
        }

        // Write the updated list back to the file
        boolean saved = false;
        if(updated) {
            saved = updateAll(users);
            System.out.println("Users saved in file");
        }
        return(updated && saved);
    }

    public boolean updateAll(ObservableList<User> users) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for(User u : users) {
                outputStream.writeObject(u);
            }
            System.out.println("Users successfully updated in file.");
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("Data file not found: " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.err.println("Error updating the inventory file: " + e.getMessage());
            return false;
        }
    }

    public User selectUser(String username) {
        try(ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream(DATA_FILE));){
            while (true){
                if(inputStream.readObject() instanceof User)
                    if(((User) inputStream.readObject()).getUsername().equals(username)){
                        return (User)inputStream.readObject();
                    }
            }
        }catch (EOFException e){

        }catch(IOException | ClassNotFoundException ex) {
            ex.getMessage();
        }
        return null;
    }

    public User selectUserFromId(String userID) {
        try(ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream(DATA_FILE));){
            while (true){
                if(inputStream.readObject() instanceof User)
                    if(((User) inputStream.readObject()).getUserID().equals(userID)){
                        return (User)inputStream.readObject();
                    }
            }
        }catch (EOFException e){

        }catch(IOException | ClassNotFoundException ex) {
            ex.getMessage();
        }
        return null;
    }

    public void selectAllUser() {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            while(true) {
                User user = (User) reader.readObject();
                users.add(user);
            }
        }
        catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public User authenticateUser(String username, String password) {
        try(ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream(DATA_FILE));){
            while (true) {
                Object obj = inputStream.readObject();

                // Check if the object is a User instance
                if (obj instanceof User) {
                    User user = (User) obj;

                    System.out.println("Username: " + user.getUsername());
                    System.out.println("Password: " + user.getPassword());

                    // Check username and password
                    if (user.getUsername().equals(username.trim()) &&
                            user.getPassword().equals(password.trim())) {
                        return user; // Return the matched User
                    }
                }
            }
        }catch (EOFException e){

        }catch(IOException | ClassNotFoundException ex) {
            ex.getMessage();
        }
        return null;
    }
}
