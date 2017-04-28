package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class InfoListActivity extends Activity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_list);
        mListView = (ListView) findViewById(R.id.listView);

        List<InfoElement> infoElements = genererInfoElements();

        InfoElementAdapter adapter = new InfoElementAdapter(InfoListActivity.this, infoElements);
        mListView.setAdapter(adapter);
    }

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
