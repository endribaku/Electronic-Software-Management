package Interfaces.DAO;

import Models.Access;
import Models.User;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public interface IUserFileHandler {

    // Read
    ObservableList<User> getAllUsers();
    User selectUser(String username);
    User selectUserFromId(String userID);

    // Auth
    User authenticateUser(String username, String password);

    // Create
    void insertUser(User user);

    // Delete
    boolean deleteUser(User user);

    // Update
    boolean updateUser(
            String username,
            String password,
            String fullName,
            LocalDate dob,
            String pNumber,
            String email,
            double salary,
            Access accessLevel,
            ObservableList<String> permissions,
            List<String> sector
    );

    boolean updateAll(ObservableList<User> users);
    boolean updateProfile(
            String username,
            String fullName,
            String email,
            String password,
            String phoneNumber,
            LocalDate dateOfBirth
    );
}
