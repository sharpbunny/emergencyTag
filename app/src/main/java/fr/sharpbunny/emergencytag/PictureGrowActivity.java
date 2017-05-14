package fr.sharpbunny.emergencytag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class PictureGrowActivity extends AppCompatActivity {

    private static String TAG = PictureGrowActivity.class.getSimpleName();

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_picture_grow);

        if (getIntent().hasExtra("url_photo")) {

            imageView = (ImageView) findViewById(R.id.imageView);

            // Use Picasso to load the image. Temporarily have a placeholder in case it's slow to load
            try {
                String url = getIntent().getStringExtra("url_photo");
                Picasso.with(PictureGrowActivity.this).load(url).placeholder(R.mipmap.emergency)
                        .into(imageView);
            } catch (Exception e) {
                Log.i(TAG, "No picture for this item...");
            }
        }
    }
}


