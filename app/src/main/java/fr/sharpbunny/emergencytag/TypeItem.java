package fr.sharpbunny.emergencytag;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class to manage types item.
 */
public class TypeItem implements Parcelable {
    private int idType;
    private String labelType;
    private String descriptionType;

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public String getDescriptionType() {
        return descriptionType;
    }

    public void setDescriptionType(String descriptionType) {
        this.descriptionType = descriptionType;
    }

    // implement for parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(idType);
        out.writeString(labelType);
        out.writeString(descriptionType);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<TypeItem> CREATOR = new Parcelable.Creator<TypeItem>() {
        public TypeItem createFromParcel(Parcel in) {
            return new TypeItem(in);
        }

        public TypeItem[] newArray(int size) {
            return new TypeItem[size];
        }
    };

    // Constructor that takes a Parcel and gives you an object populated with it's values
    private TypeItem(Parcel in) {
        idType = in.readInt();
        labelType = in.readString();
        descriptionType = in.readString();
    }

    @Override
    public int hashCode() {
        return (this.getIdType());
    }
}
