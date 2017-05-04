package fr.sharpbunny.emergencytag;
import android.*;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;


public class DetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ProgressDialog progressDialog;
    private String path = "http://rest.nomadi.fr/item/1";
    private URL url;
    private StringBuffer response;
    private String responseText;
    private int responseCode;
    Item item = null;
    int id;
    String commentaire;
    int item_Lat;
    int item_Lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        new GetServerData().execute();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        GridView gridView = (GridView) findViewById(R.id.gridView);

        gridView.setAdapter(new GridAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(DetailsActivity.this, PictureGrowActivity.class);
                startActivity(i);
            }
        });

        Button mybuttonA = (Button) findViewById(R.id.ajout);
        mybuttonA.setOnClickListener(gotoCamera);
    }

    private View.OnClickListener gotoCamera = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent =new Intent(DetailsActivity.this,CameraActivity.class);
            intent.putExtra("ajouterNouvellePhoto",true);
            startActivity(intent);
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // activate my location control
            mMap.setMyLocationEnabled(true);
            // activate zoom ui controls
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }

        // Add a marker in AFPA, FRANCE, and move the camera.
        /*LatLng afpa = new LatLng(43,9);
        mMap.addMarker(new MarkerOptions().position(afpa).title("Marqueur sur zone"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(afpa, 20));*/

    }
    class GetServerData extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

            progressDialog = new ProgressDialog(DetailsActivity.this);
            progressDialog.setMessage("Fetching data");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return getWebServiceResponseData();

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            // Dismiss the progress dialog
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            TextView textNom = (TextView) findViewById(R.id.textNom);
            TextView textCom = (TextView) findViewById(R.id.textCom);

            LatLng afpa = new LatLng(item.getLat(),item.getLon());
            mMap.addMarker(new MarkerOptions().position(afpa).title("Marqueur sur zone"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(afpa, 20));

            textNom.setText(""+item.getId());
            textCom.setText(""+item.getCommentaire());
        }
    }

    protected Void getWebServiceResponseData() {

        try {

            url = new URL(path);
            Log.d(TAG, "ServerData: " + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            responseCode = conn.getResponseCode();
            Log.d(TAG, "Response code: " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                // Reading response from input Stream
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;
                response = new StringBuffer();

                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                in.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        responseText = response.toString();
        //Call ServerData() method to call webservice and store result in response
        //  response = service.ServerData(path, postDataParams);*/
        Log.d(TAG, "data:" + responseText);
        try {

            JSONObject jsonObj = new JSONObject(responseText);
            JSONArray itemDetail = jsonObj.getJSONArray("item");

            for (int i = 0; i < itemDetail.length(); i++) {
                JSONObject c = itemDetail.getJSONObject(i);
                id = c.getInt("idItem");
                commentaire = c.getString("commentaire");
                item_Lat = c.getInt("item_Lat");
                item_Lon = c.getInt("item_Lon");
                Log.d(TAG, "idUser:" + id);
                Log.d(TAG, "commentaire:" + commentaire);
                Log.d(TAG, "item_Lat:" + item_Lat);
                Log.d(TAG, "item_Lon:" + item_Lon);
                item = new Item(id,commentaire,item_Lon,item_Lat);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public class Item {

        int id;
        String commentaire;
        int longitude;
        int latitude;

        public Item (int id, String commentaire , int longitude, int latitude) {
            super();
            this.id = id;
            this.commentaire = commentaire;
            this.longitude = longitude;
            this.latitude = latitude;
        }
        public int getId() {return id;}
        public void setId(int id) {this.id = id;}
        public String getCommentaire() {return commentaire;}
        public void setCommentaire(String commentaire) {this.commentaire = commentaire;}
        public int getLon() {return longitude;}
        public void setLon(int longitude) { this.longitude = longitude; }
        public int getLat() {return latitude;}
        public void setLat(int latitude) {this.latitude = latitude;}
    }
}
