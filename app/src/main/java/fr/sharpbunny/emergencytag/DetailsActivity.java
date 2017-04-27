package fr.sharpbunny.emergencytag;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
        mMap.getUiSettings();
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setIndoorEnabled(true);
        mMap.getFocusedBuilding();



        // Add a marker in Sydney, Australia, and move the camera.
        LatLng afpa = new LatLng(43.5653607,3.842927);
        mMap.addMarker(new MarkerOptions().position(afpa).title("Marqueur sur l'AFPA"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(afpa));
    }
}