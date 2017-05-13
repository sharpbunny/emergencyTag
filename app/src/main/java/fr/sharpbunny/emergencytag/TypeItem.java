package fr.sharpbunny.emergencytag;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Class to manage types item.
 */
public class TypeItem implements Parcelable {
    /**
     * TAG is used in log to identify origin of log
     */
    private static final String TAG = TypeItem.class.getSimpleName();

    /**
     * Id of type item
     */
    private int idType;
    private String labelType;
    private String descriptionType;

    /**
     * Constructor for Type Item
     */
    public TypeItem(){

    }

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

    public static ArrayList<TypeItem> getListFromRest(String url) {
        ArrayList<TypeItem> listTypeItem = new ArrayList<>();
        Log.i(TAG, "Trying to get list item data...");
        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response for list items
        String jsonStr = sh.makeServiceCall(url, "GET", null);
        Log.i(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                // Getting JSON message
                if (jsonObj.getInt("status")== 0){
                    listTypeItem = getTypesItemList(jsonObj);
                } else {
                    listTypeItem = null;
                }

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }

        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
        return listTypeItem;
    }

    /**
     * Extract data from json response from rest to arraylist of type item
     * @param jsonresp json of type item
     * @return arraylist of typeitem
     */
    public static ArrayList<TypeItem> getTypesItemList(JSONObject jsonresp) {
        final ArrayList<TypeItem> typeItemList = new ArrayList<>();

        try {
            // Load data
            if (jsonresp != null) {
                JSONArray items = jsonresp.getJSONArray("types");

                // Get Item objects from data
                for (int i = 0; i < items.length(); i++) {
                    TypeItem item = new TypeItem();

                    item.idType = items.getJSONObject(i).getInt("id_Type");
                    item.labelType = items.getJSONObject(i).getString("LabelType");
                    item.descriptionType = items.getJSONObject(i).getString("descriptionType");

                    typeItemList.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return typeItemList;
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
