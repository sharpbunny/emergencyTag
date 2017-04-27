package fr.sharpbunny.emergencytag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import fr.sharpbunny.emergencytag.R;

public class PictureGrowActivity extends AppCompatActivity {

    Button back;
    ImageView img;
    String ImgGet;

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
}