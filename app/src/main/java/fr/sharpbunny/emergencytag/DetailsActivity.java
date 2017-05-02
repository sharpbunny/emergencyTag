package fr.sharpbunny.emergencytag;
import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.ArrayList;

public class DetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<File> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recuperationImage();
        setContentView(R.layout.activity_details);


        list = imageReader(Environment.getExternalStorageDirectory())
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        GridView gridView = (GridView) findViewById(R.id.gridView);

        gridView.setAdapter(new GridAdapter(this));


    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return 0;
        }

        @Override
        public Object getItem(int position){
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override

        public View getView(int position, View convertView, ViewGroup parent){
            return null;
        }
    }
        ArrayList<File> imageReader(File root){
            ArrayList<File> a = new ArrayList<>();
            File[] files = root.listFiles();
            for(int i = 0; i <files.length; i++){
                if(files[i].isDirectory()){
                    a.addAll( imageReader(files[i] ) );


                }
                else{
                    if(files[i]getName().endsWith(".jpg") ){
                        a.add(files[i]);
                    }
                }
            }
            return a;
        }

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

    /**
     * On récupère les informations envoyées depuis l'appareil photo du smartphone
     * http://stackoverflow.com/questions/13226263/i-want-to-transfer-the-image-from-one-activity-to-another
     */
   private void recuperationImage(){
        if(getIntent().hasExtra("byteArray")){
            GridView photo = (GridView) findViewById(R.id.gridView);
            Bitmap imageBMP = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length
            );
            photo.setImageBitmap(imageBMP);
        }
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
