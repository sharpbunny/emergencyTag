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

/**
 *
 */
public class PictureGrowActivity extends AppCompatActivity {

    Button back;
    Button boutonCamera;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_picture_grow);
        //Recupére l'image envoyer a cette fenêtre et la décode
        if(getIntent().hasExtra("byteArray")) {

            //Associe imageView avec le Layout  ativity_picture_grow.xml
            imageView = (ImageView) findViewById(R.id.imageView2);

            // Decode le byteArray reçu dans la vue pour le recomposé en image;
            // une fois recomposé on la place dans notre variable image imageView
            Bitmap b = BitmapFactory.decodeByteArray(
             getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            imageView.setImageBitmap(b);
        }

        // Zoom l'image
        /*final ImageView zoom = imageView;
        final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        zoom.startAnimation(zoomAnimation);*/

        back = (Button) findViewById(R.id.retourWindows);
       }

    public void backOnPreviousWindows(View view){
        Intent previous = new Intent(this,InfoListActivity.class);
        startActivity(previous);

    }
}