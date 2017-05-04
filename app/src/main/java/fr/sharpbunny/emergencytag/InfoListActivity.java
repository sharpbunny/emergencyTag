package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class InfoListActivity extends AppCompatActivity {
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
        Intent intent = getIntent();
        // Get the extras (if there are any)
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("token")) {
                String token = extras.getString("token");
                Toast.makeText(InfoListActivity.this, "Called with token:" + token,
                        Toast.LENGTH_SHORT).show();
                // TODO: Do something with the value of isNew.
            }
        }

        setContentView(R.layout.activity_info_list);

        final Context context = this;

        addNewElementBtn = (Button)findViewById(R.id.accesNewElement);

        // Get data to display
        final ArrayList<InfoElement> infoElements = InfoElement.getInfoElements("http://rest.nomadi.fr/item", this);

        // Create adapter
        InfoElementAdapter adapter = new InfoElementAdapter(InfoListActivity.this, infoElements);

        // Create list view
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(adapter);

        // Set what happens when a list view item is clicked
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InfoElement selectedInfoElement = infoElements.get(position);
                // Calling intent with item details
                Intent detailIntent = new Intent(context, DetailsActivity.class);
                detailIntent.putExtra("idItem", selectedInfoElement.idItem);
                detailIntent.putExtra("nameItem", selectedInfoElement.nameItem);
                detailIntent.putExtra("latitudeItem", selectedInfoElement.latitudeItem);
                detailIntent.putExtra("longitudeItem", selectedInfoElement.longitudeItem);

                startActivity(detailIntent);
            }

        });
        addNewElementBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(InfoListActivity.this,DetailsActivity.class);
                        intent.putExtra("isNewElement",true);
                        startActivity(intent);
                    }
                }
        );
    }

}
