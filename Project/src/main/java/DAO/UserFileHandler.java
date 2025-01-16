package DAO;

import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserFileHandler {

    public static final String FILE_PATH = "Project/Data/employees.dat";
    private static final File DATA_FILE = new File(FILE_PATH);
    private final ObservableList<User> users = FXCollections.observableArrayList();

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
        } catch(IOException ioe) {
            ioe.getMessage();
        }
    }

    public void deleteUser(User user) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            users.remove(user);
            for(User u : users) {
                outputStream.writeObject(u);
            }
        } catch(EOFException eofe) {

        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    public void deleteAll(ArrayList<User> usersToRemove) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))){
        for(User u : usersToRemove) {
            if (users.containsAll(usersToRemove)) {
                users.removeAll(usersToRemove);
            } else if (users.contains(u)) {
                users.remove(u);
            }
        }
        for(User u : users) {
                outputStream.writeObject(u);
        }
    } catch(IOException ex) {
        ex.getMessage();
    }
}

public boolean updateAll() {
    try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
        for(User u : users) {
            outputStream.writeObject(u);
        }
        return true;
    } catch (IOException ex) {
        ex.getMessage();
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

    public void selectAllUser() {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            User user;
            while(true) {
                user = (User) reader.readObject();
                users.add(user);
            }
        }
        catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
