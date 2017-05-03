package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InfoListActivity extends Activity {
    /**
     *  TAG is used in log to identify origin of log
     */
    private String TAG = InfoListActivity.class.getSimpleName();
    private ListView mListView;
    private TextView retourJson;
    private Button appuyer;
    Button accesNewElementBtn;
     EditText UsernameEt;
    EditText PasswordEt;
    BackgroundWorker backgroundworker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Entering infolist...");
        setContentView(R.layout.activity_info_list);

        accesNewElementBtn = (Button)findViewById(R.id.accesNewElement);

        mListView = (ListView) findViewById(R.id.listView);
        retourJson = (TextView) findViewById(R.id.BackJson);


        //************************connexion par url****************************************************

        UsernameEt= (EditText) findViewById(R.id.utilisateur);
        PasswordEt=(EditText) findViewById(R.id.password);
        appuyer= (Button) findViewById(R.id.button3);
       final BackgroundWorker backgroundworker = new BackgroundWorker(this);
        appuyer.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

                String username = UsernameEt.getText().toString();//stock saisi de l'utilisateur
        String password=PasswordEt.getText().toString();//stock saisi de l'utilisateur
        String type ="login";

        backgroundworker.execute(type,username,password);//Envoi les parametres saisi a la class
                                       }
                                   });
        //************************************************************************************************
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
        String url = "http://rest.nomadi.fr/user/login";
        HashMap<String, String> elements = new HashMap<>();
        String jsonStr = sh.makeServiceCall(url, "GET", elements);
        /*elements.get("");*/
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

    public void connection(){


    }
}
