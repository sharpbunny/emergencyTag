package fr.sharpbunny.emergencytag;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static fr.sharpbunny.emergencytag.R.id.gridView;
import static fr.sharpbunny.emergencytag.R.id.photoItem;

public class CameraActivity extends AppCompatActivity {
    Button boutonCamera;
    ImageView imageView;
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
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

                // Test de putextra pour l'envoyer sur la gridView de DetailsActivity.
                /*cameraIntent.putExtra("NouvellePhoto", gridView);*/
                startActivityForResult(cameraIntent, CAM_REQUEST);

                if(getIntent().getExtras().getBoolean("isNewElement") == true){
                    cameraIntent.setClass(CameraActivity.this, AddElementActivity.class);
                    Bitmap imageBMP = BitmapFactory.decodeFile(file.getAbsolutePath());
                    ByteArrayOutputStream EncodageImageEnByte = new ByteArrayOutputStream();
                    imageBMP.compress(Bitmap.CompressFormat.JPEG, 50, EncodageImageEnByte);
                    cameraIntent.putExtra(("byteArray"), EncodageImageEnByte.toByteArray());

                    cameraIntent.putExtra("NouvellePhoto", photoItem);
                }



                /*if(getIntent().getExtras().getBoolean("isNewPicture") == true){
                    cameraIntent.putExtra("NouvellePhoto", gridView);
                }
                Boolean SiLeBooleenABienEteEnvoyeDeVona = getIntent().getExtras().getBoolean("nomdubooleengeneral");
                Boolean SiLeBooleenABienEteEnvoyeDeChris = getIntent().getExtras().getBoolean("nomdubooleengeneral");
                */
                // Test de bytearray pour l'envoyer sur l'imageview d'AddElementActivity.
               /*
                startActivityForResult(cameraIntent, CAM_REQUEST);*/
            }
        });
    }
    /**
     * Méthode pour enregistrer les photos dans le téléphone, avec un nom précis pour chaque photo.
     * @return
     */
    private File getFile() {
        File folder = new File("sdcard/camera_app");

        if (!folder.exists()) {
            folder.mkdir();
        }

        File image_file = new File(folder, "cam_image.jpg");

        return image_file;
    }
    /**
     * Méthode créée afin de rewrite le chemin de la photo, pour le moment, cela évite de trop stocker sur le téléphone test.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data ){
        String path ="sdcard/camera_app/cam_image.jpg";
        imageView.setImageDrawable(Drawable.createFromPath(path));
    }

}


