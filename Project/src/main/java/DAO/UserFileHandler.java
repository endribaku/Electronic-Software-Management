package DAO;

import Interfaces.DAO.IUserFileHandler;
import Models.Access;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserFileHandler implements IUserFileHandler {

    public static final String FILE_PATH = "Project/Data/employees.dat";

    private final File dataFile;

    public ObservableList<User> getUsers() {
        return users;
    }

    private final ObservableList<User> users = FXCollections.observableArrayList();

    public UserFileHandler() {
        this(new File(FILE_PATH));
    }

    public UserFileHandler(File dataFile) {
        this.dataFile = dataFile;
        selectAllUser();
    }

    @Override
    public ObservableList<User> getAllUsers() {
        if (users.isEmpty()) {
            selectAllUser();
        }
        return users;
    }

    @Override
    public void insertUser(User user) {
        try (FileOutputStream outputStream = new FileOutputStream(dataFile, true)) {
            ObjectOutputStream writer;
            if (dataFile.length() > 0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(user);
            users.add(user);
        } catch (IOException exception) {
            exception.getMessage();
        }
    }

    @Override
    public boolean deleteUser(User user) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            for (User u : users) {
                if (!u.equals(user))
                    outputStream.writeObject(u);
            }
            users.remove(user);
            return true;
        } catch (IOException exception) {
            exception.getMessage();
            return false;
        }
    }

    public void deleteAll(ArrayList<User> usersToRemove) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            for (User u : users) {
                if (!usersToRemove.contains(u)) {
                    outputStream.writeObject(u);
                }
            }
            users.removeAll(usersToRemove);
        } catch (IOException exception) {
            exception.getMessage();
        }
    }

    @Override
    public boolean updateUser(
            String username,
            String password,
            String fullName,
            LocalDate dob,
            String pNumber,
            String email,
            double salary,
            Access accessLevel,
            ObservableList<String> permissions,
            List<String> sector) {

        User existingUser = findUserByUsername(username);
        if (existingUser == null) {
            return false;
        }

        User updatedUser = new User(
                username,
                password,
                fullName,
                dob,
                pNumber,
                email,
                salary,
                accessLevel,
                permissions,
                FXCollections.observableArrayList(sector)
        );

        users.set(users.indexOf(existingUser), updatedUser);

        return updateAll(users);
    }

    public boolean updateProfile(
            String username,
            String fullName,
            String email,
            String password,
            String phoneNumber,
            LocalDate dateOfBirth) {

        boolean updated = false;

        for (User u : users) {
            if (u.getUsername().equals(username)) {
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

        boolean saved = false;
        if (updated) {
            saved = updateAll(users);
            System.out.println("Users saved in file");
        }
        return (updated && saved);
    }

    @Override
    public boolean updateAll(ObservableList<User> users) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            for (User u : users) {
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
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(dataFile))) {
            while (true) {
                Object obj = inputStream.readObject();
                if (obj instanceof User user) {
                    if (user.getUsername().equals(username)) {
                        return user;
                    }
                }
            }
        } catch (EOFException e) {
            // End of file reached
        } catch (IOException | ClassNotFoundException exception) {
            exception.getMessage();
        }
        return null;
    }

    public User selectUserFromId(String userID) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(dataFile))) {
            while (true) {
                Object obj = inputStream.readObject();
                if (obj instanceof User user) {
                    if (user.getUserID().equals(userID)) {
                        return user;
                    }
                }
            }
        } catch (EOFException e) {
            // End of file reached
        } catch (IOException | ClassNotFoundException exception) {
            exception.getMessage();
        }
        return null;
    }

    public void selectAllUser() {
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(dataFile))) {
            while (true) {
                User user = (User) reader.readObject();
                users.add(user);
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public User authenticateUser(String username, String password) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(dataFile))) {

            while (true) {
                Object obj = inputStream.readObject();

                if (obj instanceof User user) {
                    if (credentialsMatch(user, username, password)) {
                        return user;
                    }
                }
            }

        } catch (EOFException e) {
            // End of file reached
        } catch (IOException | ClassNotFoundException e) {
            e.getMessage();
        }
        return null;
    }

    boolean credentialsMatch(User user, String username, String password) {
        return user.getUsername().equals(username.trim())
                && user.getPassword().equals(password.trim());
    }

    User findUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }
}
