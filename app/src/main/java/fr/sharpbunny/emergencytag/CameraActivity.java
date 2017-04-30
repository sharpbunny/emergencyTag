package fr.sharpbunny.emergencytag;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CameraActivity extends AppCompatActivity {
    /**
     * TAG is used in log to identify origin of log
     */
    public static final String TAG = CameraActivity.class.getSimpleName();

    private Bitmap bitmap;
    Button boutonCamera;
    ImageView imageView;
    String mCurrentPhotoPath;
    private Camera camera;
    private int cameraId = 0;
    static final int CAM_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        boutonCamera = (Button) findViewById(R.id.boutonCamera);
        imageView = (ImageView) findViewById(R.id.image_view);


        /** On lance la caméra avec ce listener  pour l'envoi des photographies.
         * Test de condidition si le boolean de chris est true, ou celui de Vona est true.
         * Envoi la photo soit chez la page de Julien ou celle de Vona.**/

        boutonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show some info in log debug
                Log.d(TAG, "Got click on camera button");
                dispatchTakePictureIntent();

            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        // Test de putextra pour l'envoyer sur la gridView de DetailsActivity.
        //cameraIntent.putExtra("NouvellePhoto", gridView);

        //if(getIntent().getExtras().getBoolean("isNewElement")){
        //   cameraIntent.setClass(CameraActivity.this, AddElementActivity.class);
        //    Bitmap imageBMP = null;
        //    ByteArrayOutputStream EncodageImageEnByte = new ByteArrayOutputStream();
        //    imageBMP.compress(Bitmap.CompressFormat.JPEG, 50, EncodageImageEnByte);
        //    cameraIntent.putExtra(("byteArray"), EncodageImageEnByte.toByteArray());

        //    cameraIntent.putExtra("NouvellePhoto", photoItem);
        //}



                /*if(getIntent().getExtras().getBoolean("isNewPicture") == true){
                    cameraIntent.putExtra("NouvellePhoto", gridView);
                }
                Boolean SiLeBooleenABienEteEnvoyeDeVona = getIntent().getExtras().getBoolean("nomdubooleengeneral");
                Boolean SiLeBooleenABienEteEnvoyeDeChris = getIntent().getExtras().getBoolean("nomdubooleengeneral");
                */
        // Test de bytearray pour l'envoyer sur l'imageview d'AddElementActivity.

        // Ensure that there's a camera activity to handle the intent
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                // trying to create an empty file for the picture
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(TAG, "Can't create image");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                // we provide uri of the picture to ensure camera app will be able
                // to write in our private directory @see Fileprovider mechanism
                // in this way other apps can't access our pictures
                Uri photoURI = FileProvider.getUriForFile(CameraActivity.this,
                        "fr.sharpbunny.emergencytag.fileprovider",
                        photoFile);
                Log.d(TAG, "Passing param: " + photoURI);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                Log.d(TAG, "Launching camera intent with param: " + photoURI);
                startActivityForResult(cameraIntent, CAM_REQUEST);
            }
        }

        // show some info in log debug
        Log.d(TAG, "OK all init are done...");

    }

    /**
     * Method to create a picture usable with Fileprovider.
     * @return filename where we store picture
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d(TAG, "create image file" + mCurrentPhotoPath);

        return image;
    }

    /**
     * Method called on activity return.
     * @param requestCode code of activity
     * @param resultCode result code of activity
     * @param data Data returned by the intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data ){
        InputStream stream = null;
        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK) {
            // show some info in log debug
            Log.d(TAG, "Back from Mediastore camera intent");
            Log.d(TAG, "Request code: " + requestCode);
            Log.d(TAG, "result code: " + resultCode);
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //imageView.setImageBitmap(imageBitmap);

        }
    }

}


