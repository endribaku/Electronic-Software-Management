package stubs;

import Interfaces.DAO.IUserFileHandler;
import Models.Access;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class UserFileHandlerStub implements IUserFileHandler {

    private final ObservableList<User> users = FXCollections.observableArrayList();
    public boolean getAllUsersCalled = false;
    public boolean authenticateUserCalled = false;
    public boolean insertUserCalled = false;
    public boolean deleteUserCalled = false;
    public boolean updateUserCalled = false;

    @Override
    public ObservableList<User> getAllUsers() {
        getAllUsersCalled = true;
        return users;
    }

    @Override
    public User selectUser(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User selectUserFromId(String userID) {
        return users.stream()
                .filter(u -> u.getUserID().equals(userID))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User authenticateUser(String username, String password) {
        authenticateUserCalled = true;
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void insertUser(User user) {
        insertUserCalled = true;
        users.add(user);
    }

    @Override
    public boolean deleteUser(User user) {
        deleteUserCalled = true;
        return users.remove(user);
    }

    @Override
    public boolean updateUser(String username, String password, String fullName,
                              LocalDate dob, String pNumber, String email, double salary,
                              Access accessLevel, ObservableList<String> permissions,
                              List<String> sector) {
        updateUserCalled = true;
        return true;
    }

    @Override
    public boolean updateAll(ObservableList<User> users) {
        return true;
    }

    @Override
    public boolean updateProfile(String username, String fullName, String email,
                                 String password, String phoneNumber, LocalDate dateOfBirth) {
        return true;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void reset() {
        getAllUsersCalled = false;
        authenticateUserCalled = false;
        insertUserCalled = false;
        deleteUserCalled = false;
        updateUserCalled = false;
        users.clear();
    }
}
