package fr.sharpbunny.emergencytag;

import android.content.Intent;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;


public class CameraActivity extends AppCompatActivity {
    /**
     * TAG is used in log to identify origin of log
     */
    public static final String TAG = CameraActivity.class.getSimpleName();

    private Camera camera;
    private CameraPreview mPreview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        // Create an instance of Camera
        camera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);


    }
    public void onClick(View view) {
        Toast.makeText(this, "Taking picture!!", Toast.LENGTH_LONG)
                .show();
        camera.startPreview();
        camera.takePicture(null, null,
                new CameraHandler(getApplicationContext()));
    }

    @Override
    protected void onPause() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
        super.onPause();
    }
    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();

        Intent intent = new Intent();
        //intent.putExtra("filename", file);

        setResult(RESULT_OK, intent);
        finish();
    }
}
