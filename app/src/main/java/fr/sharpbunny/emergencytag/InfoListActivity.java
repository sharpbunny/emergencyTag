package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InfoListActivity extends Activity {
    /**
     *  TAG is used in log to identify origin of log
     */
    private String TAG = InfoListActivity.class.getSimpleName();
    private ListView mListView;

    Button accesNewElementBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Entering infolist...");
        setContentView(R.layout.activity_info_list);

        accesNewElementBtn = (Button)findViewById(R.id.accesNewElement);

        mListView = (ListView) findViewById(R.id.listView);

        List<InfoElement> infoElements = genererInfoElements();

        InfoElementAdapter adapter = new InfoElementAdapter(InfoListActivity.this, infoElements);
        mListView.setAdapter(adapter);

        accesNewElementBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newPhotoToNewElement = new Intent(InfoListActivity.this,CameraActivity.class);
                        newPhotoToNewElement.putExtra("isNewElement",true);
                        startActivity(newPhotoToNewElement);
                    }
                }
        );
    }

    /**
     * @method Transform a Json list into infoelements list.
     * @return List Composed of infoelements from the JSON file.
     */
    private List<InfoElement> generateInfElemFromJSON(){
        HttpHandler sh = new HttpHandler();
        List<InfoElement> infoElements = new ArrayList<InfoElement>();
       // String url = "http://rest.nomadi.fr/user/login";
       // HashMap<String, String> elements = new HashMap<>();
        //String jsonStr = sh.makeServiceCall(url, "GET", elements);
        String jsonStr = "{\"status\":0,\"response\":[{\"idItem\":1,\"nameItem\":\"Extincteur\",\"latitudeItem\":43.5653607,\"longitudeItem\":3.842927,\"typeItem\":\"Extincteur\",\"pictureItem\":\"surprise.jpg\",\"commentItem\":\"Au fond de la salle\"},{\"idItem\":2,\"nameItem\":\"Porte\",\"latitudeItem\":43.5653607,\"longitudeItem\":3.84292755,\"typeItem\":\"Issue de secours\",\"pictureItem\":\"surprise.jpg\",\"commentItem\":\"En haut de l'escalier\"}]}";

        
        return infoElements;
    }


    /**
    * @method Generating some specimen infoelements to test before adding the JSON
    * */
    private List<InfoElement> genererInfoElements(){
        List<InfoElement> infoElements = new ArrayList<InfoElement>();
        infoElements.add(new InfoElement(Color.BLACK, "Florent", "Mon premier InfoElement !"));
        infoElements.add(new InfoElement(Color.BLUE, "Kevin", "C'est ici que ça se passe !"));
        infoElements.add(new InfoElement(Color.GREEN, "Logan", "Que c'est beau..."));
        infoElements.add(new InfoElement(Color.RED, "Mathieu", "Il est quelle heure ??"));
        infoElements.add(new InfoElement(Color.GRAY, "Willy", "On y est presque"));
        return infoElements;
    }

}
