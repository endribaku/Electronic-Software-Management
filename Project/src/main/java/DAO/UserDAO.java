package DAO;
import Models.User;

import java.io.IOException;
import java.util.ArrayList;

public interface UserDAO {
    abstract void insertUser(User user) throws IOException, ClassNotFoundException;

    abstract void updateUser(User user) throws IOException, ClassNotFoundException;

    abstract void deleteUser(String username) throws IOException, ClassNotFoundException;

    abstract User selectUser(String username) throws IOException,ClassNotFoundException;

    abstract ArrayList<User> selectAllUser() throws IOException, ClassNotFoundException;
}
