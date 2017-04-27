package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class InfoListActivity extends Activity {

    protected ListView displayList;
    String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_list);

        displayList = (ListView)findViewById(R.id.listElement);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(InfoListActivity.this,
                android.R.layout.simple_list_item_1, prenoms);
        displayList.setAdapter(adapter);
    }

}
