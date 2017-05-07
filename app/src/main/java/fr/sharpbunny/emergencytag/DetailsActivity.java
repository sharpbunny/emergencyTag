package fr.sharpbunny.emergencytag;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;


public class DetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    private GoogleMap mMap;

    //ProgressDialog progressDialog;
    private String urlPhoto = "";
    private String urlThumbnail = "";
    //private StringBuffer response;

    //int id;
    private String commentaire;
    private Double item_Lat;
    private Double item_Lon;
    //int idUser;
    //int idType;
    //Bitmap bitmap;

    //int idItem;
    //String nameItem;
    //Double latItem;
    //Double lonItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        //new GetServerData().execute();

        // check if intent was called with extra
        if(getIntent().hasExtra("item_extra")) {
            Log.d(TAG, "Intent called with Parcelable item extra");
            Item item = (Item) getIntent().getParcelableExtra("item_extra");
            TextView textNom = (TextView) findViewById(R.id.textNom);
            TextView textCom = (TextView) findViewById(R.id.textCom);

            commentaire = item.getComment();
            item_Lat = item.getItemLatitude();
            item_Lon = item.getItemLongitude();

            //LatLng marker = new LatLng(item.getItemLatitude(),item.getItemLongitude());
            //mMap.addMarker(new MarkerOptions().position(marker).title(item.getComment()));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 20));

            textNom.setText(item.getComment());
            textCom.setText(item.getDescriptionType());

            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            //imageView.setImageBitmap(bitmap);

            try {
                urlPhoto = item.getPictureListItem().get(0).getPictureUrl();
                urlThumbnail = item.getPictureListItem().get(0).getThumbnailUrl();
            }catch (Exception e) {
                Log.i(TAG, "No picture for this item...");
            }
            // Use Picasso to load the image. Temporarily have a placeholder in case it's slow to load
            try {
                Log.i(TAG, "Picture url: " + urlThumbnail);
                Picasso.with(DetailsActivity.this).load(urlThumbnail).placeholder(R.mipmap.emergency)
                        .into(imageView);
            }catch (Exception e) {
                Log.i(TAG, "Can't load picture...");
            }

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    envoyerImagePourLAgrandir();
                }
            });
        }


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

        LatLng marker = new LatLng(item_Lat, item_Lon);
        mMap.addMarker(new MarkerOptions().position(marker).title(commentaire));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 20));

    }

    private void envoyerImagePourLAgrandir() {
        //Déclaration des objets
        Intent pictureGrowIntent = new Intent(this, PictureGrowActivity.class);

        pictureGrowIntent.putExtra("url_photo", urlPhoto);
        startActivity(pictureGrowIntent);
    }


    /*protected Void getWebServiceResponseData() {

        try {

            String path = "http://rest.nomadi.fr/item/5";
            url = new URL(path);
            Log.d(TAG, "ServerData: " + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
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

        String responseText = response.toString();
        //Call ServerData() method to call webservice and store result in response
        //  response = service.ServerData(path, postDataParams);
        Log.d(TAG, "data:" + responseText);
        try {

            JSONObject jsonObj = new JSONObject(responseText);
            JSONArray itemDetail = jsonObj.getJSONArray("item");

            for (int i = 0; i < itemDetail.length(); i++) {
                JSONObject c = itemDetail.getJSONObject(i);
                id = c.getInt("idItem");
                commentaire = c.getString("commentaire");
                item_Lat = c.getDouble("item_Lat");
                item_Lon = c.getDouble("item_Lon");
                idUser = c.getInt("idUser");
                idType = c.getInt("id_Type");
                Log.d(TAG, "idUser:" + id);
                Log.d(TAG, "commentaire:" + commentaire);
                Log.d(TAG, "item_Lat:" + item_Lat);
                Log.d(TAG, "item_Lon:" + item_Lon);
                Item item = new Item(id, commentaire, item_Lat, item_Lon, idUser, idType);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }*/


    /*private Bitmap downloadBitmap(String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();

            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpsURLConnection.HTTP_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {

                bitmap = BitmapFactory.decodeStream(inputStream);
                Log.d(TAG, "downloadBitmap: "+bitmap );
                return bitmap;
            }
        } catch (Exception e) {
            Log.e("URLCONNECTIONERROR", e.toString());
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            Log.e("ImageDownloader", "Error downloading image from " + url);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();

            }
        }
        return null;
    }*/

    /*class GetServerData extends AsyncTask {

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
            downloadBitmap("http://imageurl");
            return getWebServiceResponseData();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            // Dismiss the progress dialog
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            Intent detailIntent = getIntent();

            idItem = detailIntent.getIntExtra("idItem", 0);
            nameItem = detailIntent.getStringExtra("nameItem");
            latItem = detailIntent.getDoubleExtra("latitudeItem", 0);
            lonItem = detailIntent.getDoubleExtra("longitudeItem", 0);
            commentaire = detailIntent.getStringExtra("commentaire");




            TextView textNom = (TextView) findViewById(R.id.textNom);
            TextView textCom = (TextView) findViewById(R.id.textCom);

            LatLng afpa = new LatLng(latItem,lonItem);
            mMap.addMarker(new MarkerOptions().position(afpa).title("Marqueur sur zone"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(afpa, 20));

            textNom.setText(""+idItem);
            textCom.setText(""+nameItem);

            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    envoyerImagePourLAgrandir();
                }
            });
        }
    }*/
}
