package fr.sharpbunny.emergencytag;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class to manage users.
 */
public class User implements Parcelable {
    private int idUser;
    private String nameUser;
    private String firstnameUser;
    private String emailUser;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idType) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String labelType) {
        this.nameUser = nameUser;
    }

    public String getFirstnameUser() {
        return firstnameUser;
    }

    public void setFirstnameUser(String firstnameUser) {
        this.firstnameUser = firstnameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;

    }

    // implement for parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(idUser);
        out.writeString(nameUser);
        out.writeString(firstnameUser);
        out.writeString(emailUser);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // Constructor that takes a Parcel and gives you an object populated with it's values
    private User(Parcel in) {
        idUser = in.readInt();
        nameUser = in.readString();
        firstnameUser = in.readString();
        emailUser = in.readString();
    }

    @Override
    public int hashCode() {
        return (this.getIdUser());
    }
}
