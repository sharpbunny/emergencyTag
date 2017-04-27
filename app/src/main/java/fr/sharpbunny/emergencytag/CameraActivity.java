package fr.sharpbunny.emergencytag;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
    // CHEZ CHRIS DEBUT
        if (!UtilisateurNaPasDeCamera()) {
        boutonCamera.setEnabled(false);
    }
    /** On lance la caméra avec ce listener **/
        boutonCamera.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = getFile();
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            cameraIntent.putExtra("Nouvellephoto", photoItem); // J'envoie l'image dans le photoItem.
            cameraIntent.putExtra("NouvellePhoto", gridView); // J'envoie l'image dans la gridView de Liste Details (Vona) afin d'afficher la photo
            startActivityForResult(cameraIntent, CAM_REQUEST);

        }
    });
    //CHEZ CHRIS FIN
}
    /**
     * Fonction qui vérifie si l'utilisateur possède un outil de capture d'écran sur son smartphone. On appelle alors le package android qui vérifie l'exsitence d'un tel outil.
     * @return
     */
    // CHEZ CHRIS DEBUT
    public boolean UtilisateurNaPasDeCamera()
    {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
    // CHEZ CHRIS FIN
    /**
     * Fonction vérifiant si l'utilisateur possède ou non un outil de capture d'écran.
     * @return
     */

        // CHEZ MOI DEBUT POUR ENREGISTRER LA PHOTO
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
    // CHEZ MOI FIN POUR ENREGISTRER LA PHOTO
}

