package fr.sharpbunny.emergencytag;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EmergMapActivity extends MapActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
