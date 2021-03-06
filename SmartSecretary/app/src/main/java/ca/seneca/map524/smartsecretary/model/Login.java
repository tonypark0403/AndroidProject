package ca.seneca.map524.smartsecretary.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tony on 11/14/2016.
 */

public class Login implements Parcelable {
    private String id;
    private String password;
    private String role;

    public Login() {
    }

    public Login(String id, String password, String role) {
        this.id = id;
        this.password = password;
        this.role = role;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(password);
        dest.writeString(role);
    }

    Parcelable.Creator<Login> CREATOR = new Creator<Login>() {
        @Override
        public Login createFromParcel(Parcel parcel) {
            Login login = new Login();
            login.setId(parcel.readString());
            login.setPassword(parcel.readString());

            return login;
        }

        @Override
        public Login[] newArray(int i) {
            return new Login[0];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
