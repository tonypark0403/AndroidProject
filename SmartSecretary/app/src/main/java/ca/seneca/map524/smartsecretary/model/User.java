package ca.seneca.map524.smartsecretary.model;

/**
 * Created by Tony on 11/29/2016.
 */

public class User {
    int Id;
    String FirstName;
    String LastName;
    String Password;
    String Role;

    public User(){}
    public User(int id, String firstName, String lastName, String password, String role) {
        Id = id;
        FirstName = firstName;
        Password = password;
        LastName = lastName;
        Role = role;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "[{" +
                "\"FirstName\"=\"" + FirstName + "\",\n" +
                "\"LastName\"=\"" + LastName + "\",\n" +
                "\"Password\"=\"" + Password + "\",\n" +
                "\"role\"=\"" + Role + "\"" +
                "}]";
    }
}
