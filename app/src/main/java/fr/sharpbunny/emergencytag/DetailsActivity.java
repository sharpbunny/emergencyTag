package fr.sharpbunny.emergencytag;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class DetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    private static final int ACTIVITY_CAMERA = 1;

    private String comment;
    private Double item_Lat;
    private Double item_Lon;
    private boolean coordsOk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        // check if intent was called with extra
        if (getIntent().hasExtra("item_extra")) {
            Log.d(TAG, "Intent called with Parcelable item extra");
            Item item = (Item) getIntent().getParcelableExtra("item_extra");
            TextView textNom = (TextView) findViewById(R.id.textNom);
            TextView textCom = (TextView) findViewById(R.id.textCom);

            comment = item.getComment();
            item_Lat = item.getItemLatitude();
            item_Lon = item.getItemLongitude();
            coordsOk = true;

            textNom.setText(item.getComment());
            textCom.setText(item.getDescriptionType());

            final ArrayList<Picture> pictures = item.getPictureListItem();

            GridView gridView = (GridView) findViewById(R.id.gridView);
            gridView.setAdapter(new GridAdapter(this, pictures));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    Picture selectedPicture = pictures.get(position);

                    Intent intent = new Intent(DetailsActivity.this, PictureGrowActivity.class);
                    intent.putExtra("url_photo", selectedPicture.getPictureUrl());
                    startActivity(intent);
                }
            });
        }
    }

    public void gotoCamera(View v) {

        Intent intent =new Intent(DetailsActivity.this,CameraActivity.class);
        intent.putExtra("addPicture",true);
        startActivity(intent);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // activate my location control
            googleMap.setMyLocationEnabled(true);
            // activate zoom ui controls
            googleMap.getUiSettings().setZoomControlsEnabled(true);
        }
        Log.d(TAG, googleMap.getCameraPosition().toString());
        if (coordsOk) {
            LatLng marker = new LatLng(item_Lat, item_Lon);
            googleMap.addMarker(new MarkerOptions().position(marker).title(comment));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 18));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (ACTIVITY_CAMERA) : {
                if (resultCode == CameraActivity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    String returnValue = data.getStringExtra("filename");
                }
                break;
            }
        }
    }
}
