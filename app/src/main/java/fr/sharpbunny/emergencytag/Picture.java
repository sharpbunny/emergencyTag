package fr.sharpbunny.emergencytag;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class to manage picture.
 */
public class Picture implements Parcelable {
    private int idPhoto;
    private String datePhoto;
    private String pictureUrl;
    private String thumbnailUrl;

    public Picture () {

    }

    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getDatePhoto() {
        return datePhoto;
    }

    public void setDatePhoto(String datePhoto) {
        this.datePhoto = datePhoto;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    // implement for parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(idPhoto);
        out.writeString(datePhoto);
        out.writeString(pictureUrl);
        out.writeString(thumbnailUrl);

    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Picture> CREATOR = new Parcelable.Creator<Picture>() {
        public Picture createFromParcel(Parcel in) {
            return new Picture(in);
        }

        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };

    // Constructor that takes a Parcel and gives you an object populated with it's values
    private Picture(Parcel in) {
        idPhoto = in.readInt();
        datePhoto = in.readString();
        pictureUrl = in.readString();
        thumbnailUrl = in.readString();

    }

    @Override
    public int hashCode() {
        return (this.getIdPhoto());
    }
}
