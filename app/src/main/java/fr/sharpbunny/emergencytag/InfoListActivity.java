package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class InfoListActivity extends AppCompatActivity {
    /**
     *  TAG is used in log to identify origin of log
     */
    private String TAG = InfoListActivity.class.getSimpleName();

    private ListView mListView;
    private JSONObject jsonResp;

    Button addNewElementBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Entering infolist...");
        setContentView(R.layout.activity_info_list);


        addNewElementBtn = (Button)findViewById(R.id.accesNewElement);



        addNewElementBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(InfoListActivity.this,DetailsActivity.class);
                        intent.putExtra("isNewElement",true);
                        startActivity(intent);
                    }
                }
        );
        new GetJsonItem().execute();
    }
    /**
     * Async task to check login
     */
    private class GetJsonItem extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(InfoListActivity.this, "Getting Json item", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://rest.nomadi.fr/item";
            String json = sh.makeServiceCall(url, "GET", null);

            //Log.e(TAG, "Response from url: " + json);
            if (json != null) {
                try {
                    jsonResp = new JSONObject(json);
                    // Getting JSON message
                    //jsonResp = jsonObj.getString("response");

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            final Context context = InfoListActivity.this;
            // Get data to display
            final ArrayList<InfoElement> infoElements = InfoElement.getInfoElements(jsonResp, InfoListActivity.this);

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

        }
    }

}
