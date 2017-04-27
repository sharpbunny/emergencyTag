package fr.sharpbunny.emergencytag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import fr.sharpbunny.emergencytag.R;

import static fr.sharpbunny.emergencytag.R.id.gridView;

public class PictureGrowActivity extends AppCompatActivity {

    Button back;
    ImageView img;
    String ImgGet;

    Button boutonCamera;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Recupére l'image envoyer a cette fenêtre et la décode
        if(getIntent().hasExtra("byteArray")) {
            ImageView previewThumbnail = new ImageView(this);
            Bitmap b = BitmapFactory.decodeByteArray(
             getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            previewThumbnail.setImageBitmap(b);
        }

        setContentView(R.layout.activity_picture_grow);
        final ImageView zoom = (ImageView) findViewById(R.id.imageView2);
        final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        zoom.startAnimation(zoomAnimation);

        back = (Button) findViewById(R.id.retourWindows);
       }

       public void click() {
           img = (ImageView)findViewById(R.id.imageView2);

       }

    public void backOnPreviousWindows(View view){
        Intent previous = new Intent(this,InfoListActivity.class);
        startActivity(previous);

    }

    public void camera(){
        boutonCamera = (Button) findViewById(R.id.boutonCamera);
        imageView = (ImageView) findViewById(R.id.image_view);
    }

        /** On lance la caméra avec ce listener  pour l'envoi des photographies.**/
        boutonCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                // Test de putextra pour l'envoyer sur la gridView de DetailsActivity.
                cameraIntent.putExtra("NouvellePhoto", gridView);
                // Test de bytearray pour l'envoyer sur l'imageview d'AddElementActivity.
                Bitmap imageBMP = null;
                ByteArrayOutputStream EncodageImageEnByte = new ByteArrayOutputStream();
                imageBMP.compress(Bitmap.CompressFormat.JPEG, 50, EncodageImageEnByte);
                cameraIntent.putExtra(("byteArray"), EncodageImageEnByte.toByteArray());
                startActivityForResult(cameraIntent, CAM_REQUEST);
    }

    public void traitementdimage(){
        Bitmap b = null;
        ByteArrayOutputStream bs = new ByteArrayOutputStream(); //Tableau d'octets stocké en mémoire

        //L'image est compressée puis stockée sous forme d'un tableau de données dans bs
        b.compress(Bitmap.CompressFormat.JPEG, 50, bs);

        //On envoie le tableau de byte dans l'activité pictureGrowActivity
      //  pictureGrowIntent.putExtra("byteArray", bs.toByteArray());
    }
}