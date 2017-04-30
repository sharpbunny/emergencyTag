package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class InfoListActivity extends Activity {
    /**
     *  TAG is used in log to identify origin of log
     */
    private String TAG = InfoListActivity.class.getSimpleName();
    private ListView mListView;

    Button addNewElementBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Entering infolist...");
        setContentView(R.layout.activity_info_list);

        addNewElementBtn = (Button)findViewById(R.id.accesNewElement);

        mListView = (ListView) findViewById(R.id.listView);

        List<InfoElement> infoElements = genererInfoElements();

        InfoElementAdapter adapter = new InfoElementAdapter(InfoListActivity.this, infoElements);
        mListView.setAdapter(adapter);

        addNewElementBtn.setOnClickListener(
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
     * Generating some specimen infoelements to test before adding the JSON
     * @return list of infoelements
     */
    private List<InfoElement> genererInfoElements(){
        List<InfoElement> infoElements = new ArrayList<InfoElement>();
        infoElements.add(new InfoElement(Color.BLACK, "Florent", "Mon premier InfoElement !"));
        infoElements.add(new InfoElement(Color.BLUE, "Kevin", "C'est ici que ça se passe !"));
        infoElements.add(new InfoElement(Color.GREEN, "Logan", "Que c'est beau..."));
        infoElements.add(new InfoElement(Color.RED, "Mathieu", "Il est quelle heure ??"));
        infoElements.add(new InfoElement(Color.GRAY, "Willy", "On y est presque"));
        infoElements.add(new InfoElement(Color.BLACK, "Florent", "Mon premier InfoElement !"));
        infoElements.add(new InfoElement(Color.BLUE, "Kevin", "C'est ici que ça se passe !"));
        infoElements.add(new InfoElement(Color.GREEN, "Logan", "Que c'est beau..."));
        infoElements.add(new InfoElement(Color.RED, "Mathieu", "Il est quelle heure ??"));
        infoElements.add(new InfoElement(Color.GRAY, "Willy", "On y est presque"));
        infoElements.add(new InfoElement(Color.BLACK, "Florent", "Mon premier InfoElement !"));
        infoElements.add(new InfoElement(Color.BLUE, "Kevin", "C'est ici que ça se passe !"));
        infoElements.add(new InfoElement(Color.GREEN, "Logan", "Que c'est beau..."));
        infoElements.add(new InfoElement(Color.RED, "Mathieu", "Il est quelle heure ??"));
        infoElements.add(new InfoElement(Color.GRAY, "Willy", "On y est presque"));
        return infoElements;
    }

}
