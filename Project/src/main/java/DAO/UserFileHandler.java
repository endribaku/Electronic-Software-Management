package DAO;

import Models.User;

import java.io.*;
import java.util.ArrayList;

public class UserFileHandler implements UserDAO {

    @Override
    public void insertUser(User user) throws IOException, ClassNotFoundException{
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Project/Data/employees.dat",true));
        outputStream.writeObject(user);
    }

    @Override
    public void updateUser(User user) throws IOException, ClassNotFoundException {
        ArrayList<User> updatedList = selectAllUser();

        for(User u : updatedList){
            if(u.getUserID().equals(user.getUserID()))
                u.setUsername(user.getUsername());
                u.setAccessLevel(user.getAccessLevel());
                u.setDateOfBirth(user.getDateOfBirth());
                u.setEmail(user.getEmail());
                u.setPassword(user.getPassword());
                u.setFullName(user.getFullName());
                u.setPhoneNumber(user.getPhoneNumber());
                u.setSalary(user.getSalary());
        }

        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Project/Data/employees.dat",true));){
            for(User u : updatedList){
                outputStream.writeObject(u);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteUser(String username) throws IOException, ClassNotFoundException{
        ArrayList<User> updatedList = selectAllUser();

        for(User u : updatedList){
            if(u.getUsername().equals(username))
            updatedList.remove(u);
        }

        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Project/Data/employees.dat",true));){
            for(User u : updatedList){
                outputStream.writeObject(u);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public User selectUser(String username) throws IOException,ClassNotFoundException {
        try(ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream("Project/Data/employees.dat"));){
            while (true){
                if(inputStream.readObject() instanceof User)
                    if(((User) inputStream.readObject()).getUsername().equals(username)){
                        return (User)inputStream.readObject();
                    }
            }
        }catch (EOFException e){
            System.out.println("Entire file has been traversed.");
        }
        return null;
    }

    @Override
    public ArrayList<User> selectAllUser() throws IOException, ClassNotFoundException {
        ArrayList<User> usersFound = new ArrayList<>();
        try(ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream("Project/Data/employees.dat"));){
            while (true){
                if(inputStream.readObject() instanceof User)
                    usersFound.add((User) inputStream.readObject());
            }
        }catch (EOFException e){
            System.out.println("Entire file has been traversed.");
        }
        return usersFound;
    }
}
