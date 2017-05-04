package fr.sharpbunny.emergencytag;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

    public static ArrayList<InfoElement> getInfoElements(String filename, Context context) {
        final ArrayList<InfoElement> infoElementList = new ArrayList<>();
        try {
            // Load data
            String jsonString = loadJsonFromUrl("http://rest.nomadi.fr/item", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray infoelements = json.getJSONArray("response");
            Log.d(TAG, infoelements.toString());
            // Get Item objects from data
            for(int i = 0; i < infoelements.length(); i++){
                InfoElement infoelement = new InfoElement();

                infoelement.idItem = infoelements.getJSONObject(i).getInt("idItem");
                infoelement.nameItem = infoelements.getJSONObject(i).getString("nameItem");
                infoelement.latitudeItem = infoelements.getJSONObject(i).getString("latitudeItem");
                infoelement.longitudeItem = infoelements.getJSONObject(i).getString("longitudeItem");
                infoelement.typeItem = infoelements.getJSONObject(i).getString("typeItem");
                // TODO create 2 links with rest: 1 for picture, 1 for thumbnail
                infoelement.pictureItem = "http://rest.nomadi.fr/uploads/" + infoelements.getJSONObject(i).getString("pictureItem") + "?dim=50";
                Log.d(TAG,infoelement.pictureItem);
                infoelement.commentItem = infoelements.getJSONObject(i).getString("commentItem");

                infoElementList.add(infoelement);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return infoElementList;
    }

    private static String loadJsonFromUrl(String url, Context context) {
        String json = null;
        Log.d(TAG, "fetching json from: " + url);
        // TODO really load from url
        json = "{\"status\":1,\"response\":[{\"idItem\":1,\"nameItem\":\"Extincteur\",\"latitudeItem\":43.5653607,\"longitudeItem\":3.842927,\"typeItem\":\"Extincteur\",\"pictureItem\":\"surprise.jpg\",\"commentItem\":\"Au fond de la salle\"},{\"idItem\":2,\"nameItem\":\"Porte\",\"latitudeItem\":43.5653607,\"longitudeItem\":3.84292755,\"typeItem\":\"Issue de secours\",\"pictureItem\":\"surprise.jpg\",\"commentItem\":\"En haut de l'escalier\"}]}";

        return json;
    }
}