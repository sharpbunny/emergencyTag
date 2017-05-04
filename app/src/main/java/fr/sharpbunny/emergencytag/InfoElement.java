package fr.sharpbunny.emergencytag;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;


public class InfoElement {
    public static final String TAG = InfoElement.class.getSimpleName();

    public int idItem;
    public String nameItem;
    public String latitudeItem;
    public String longitudeItem;
    public String typeItem;
    public String pictureItem;
    public String commentItem;

    public static ArrayList<InfoElement> getInfoElements(JSONObject jsonresp, Context context) {
        final ArrayList<InfoElement> infoElementList = new ArrayList<>();
        try {
            // Load data
            //JSONObject json = new JSONObject(jsonresp);
            if (jsonresp != null) {
                JSONArray infoelements = jsonresp.getJSONArray("response");
                //Log.d(TAG, infoelements.toString());
                // Get Item objects from data
                for (int i = 0; i < infoelements.length(); i++) {
                    InfoElement infoelement = new InfoElement();

                    infoelement.idItem = infoelements.getJSONObject(i).getInt("idItem");
                    infoelement.nameItem = infoelements.getJSONObject(i).getString("commentaire");
                    infoelement.latitudeItem = infoelements.getJSONObject(i).getString("item_Lat");
                    infoelement.longitudeItem = infoelements.getJSONObject(i).getString("item_Lon");
                    infoelement.typeItem = infoelements.getJSONObject(i).getString("LabelType");
                    // picture are in an array
                    //infoelement.pictureItem = "http://rest.nomadi.fr/uploads/" + infoelements.getJSONObject(i).getString("pictureItem") + "?dim=60x60";
                    infoelement.pictureItem = "http://rest.nomadi.fr/uploads/Koala.jpg?dim=40x40";
                    Log.d(TAG, infoelement.pictureItem);
                    infoelement.commentItem = infoelements.getJSONObject(i).getString("commentaire");

                    infoElementList.add(infoelement);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return infoElementList;
    }

}