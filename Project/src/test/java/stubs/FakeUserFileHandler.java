package stubs;

import Interfaces.DAO.IUserFileHandler;
import Models.Access;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class FakeUserFileHandler implements IUserFileHandler {
    public ObservableList<User> users = FXCollections.observableArrayList();
    public User lastDeletedUser;
    public int insertUserCalls;
    public int deleteUserCalls;
    public boolean deleteUserResult = true;

    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    @Override
    public ObservableList<User> getAllUsers() {
        return users;
    }

    @Override
    public User selectUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User selectUserFromId(String userID) {
        return null;
    }

    @Override
    public User authenticateUser(String username, String password) {
        return null;
    }

    @Override
    public void insertUser(User user) {
        insertUserCalls++;
        users.add(user);
    }

    @Override
    public boolean deleteUser(User user) {
        deleteUserCalls++;
        lastDeletedUser = user;
        users.remove(user);
        return deleteUserResult;
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
        return false;
    }

    @Override
    public boolean updateAll(ObservableList<User> users) {
        return true;
    }

    @Override
    public boolean updateProfile(
            String username,
            String fullName,
            String email,
            String password,
            String phoneNumber,
            LocalDate dateOfBirth) {
        return false;
    }
}
