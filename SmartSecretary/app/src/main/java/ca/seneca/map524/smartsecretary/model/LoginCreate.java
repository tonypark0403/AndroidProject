package ca.seneca.map524.smartsecretary.model;

/**
 * Created by Tony on 11/15/2016.
 */

public class LoginCreate {
    String FirstName;
    String LastName;
    String Password;
    String role;

    public LoginCreate(String firstName, String password) {
        FirstName = firstName;
        Password = password;
        LastName = "test";
        role = "USER";
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

    @Override
    public String toString() {
        return "[{" +
                "\"FirstName\"=\"" + FirstName + "\",\n" +
                "\"LastName\"=\"" + LastName + "\",\n" +
                "\"Password\"=\"" + Password + "\",\n" +
                "\"role\"=\"" + role + "\"" +
                "}]";
    }
}
