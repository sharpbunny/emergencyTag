package fr.sharpbunny.emergencytag;
import android.*;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

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
        LatLng afpa = new LatLng(43.5653607,3.842927);
        mMap.addMarker(new MarkerOptions().position(afpa).title("Marqueur sur l'AFPA"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(afpa, 20));
    }

}
