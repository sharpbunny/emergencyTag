package fr.sharpbunny.emergencytag;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Entering infolist...");

        setContentView(R.layout.activity_info_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton addNewElementBtn = (FloatingActionButton)findViewById(R.id.accesNewElement);

        addNewElementBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(InfoListActivity.this,AddElementActivity.class);
                        intent.putExtra("isNewElement",true);
                        startActivity(intent);
                    }
                }
        );

        // Calling async task to get item list
        new GetJsonItem().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_map) {
            // go to map activity
            Intent intent = new Intent(InfoListActivity.this,MapActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_add) {
            // go to map activity
            Intent intent = new Intent(InfoListActivity.this,AddElementActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Async task to get item list
     */
    private class GetJsonItem extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(InfoListActivity.this, "Getting Json item", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            // Making a request to url and getting response
            HttpHandler sh = new HttpHandler();
            String json = sh.makeServiceCall(getResources().getString(R.string.ItemUrl), "GET", null);

            Log.i(TAG, "Response from url: " + json);
            if (json != null) {
                try {
                    // Getting JSON object
                    jsonResp = new JSONObject(json);

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
            Item item = new Item();
            final ArrayList<Item> infoElements = item.getItemsList(jsonResp);

            // Create adapter
            InfoElementAdapter adapter = new InfoElementAdapter(InfoListActivity.this, infoElements);

            // Create list view
            mListView = (ListView) findViewById(R.id.listView);
            mListView.setAdapter(adapter);

            // Set what happens when a list view item is clicked
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Item selectedInfoElement = infoElements.get(position);
                    // Calling intent with item details
                    Intent intent = new Intent(context, DetailsActivity.class);
                    // use parcelable to send item object as extra
                    intent.putExtra("item_extra", (Parcelable) selectedInfoElement);

                    startActivity(intent);
                }
            });
        }
    }
}
