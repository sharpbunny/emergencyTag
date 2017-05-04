package fr.sharpbunny.emergencytag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOError;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import layout.PictureGrowFrags;

public class PictureGrowActivity extends AppCompatActivity {

    Button back;
    Button rest;
    ImageView imageView;
    String inActiveDate;
    Button Activephoto;
     android.hardware.Camera mCamera;
    File imagerecu;
   static final int CAM_REQUEST = 1;
  // static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_grow);

        Activephoto = (Button) findViewById(R.id.photo);
        imageView = (ImageView) findViewById(R.id.imageView2);
        rest = (Button)findViewById(R.id.button2);
        //Recupére l'image envoyer a cette fenêtre et la décode
        if(getIntent().hasExtra("byteArray")) {

            //Associe imageView avec le Layout  ativity_picture_grow.xml
            imageView = (ImageView) findViewById(R.id.imageView2);
            /**
             * Decode le byteArray reçu dans la vue pour le recomposé en image;
             *une fois recomposé on la place dans notre variable image imageView
             */
            Bitmap b = BitmapFactory.decodeByteArray(
             getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            imageView.setImageBitmap(b);

            /**
             * Zoom l'image
             */
            final ImageView zoom = imageView;
            final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
            zoom.startAnimation(zoomAnimation);

            back = (Button) findViewById(R.id.retourWindows);
        }
        else{
            imageView = (ImageView) findViewById(R.id.imageView2);
            imageView.setImageResource(R.drawable.surprise);
            final ImageView zoom = imageView;
            final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
            zoom.startAnimation(zoomAnimation);
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date date = cal.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        String inActiveDate ;
        try {
            inActiveDate = format1.format(date);
            Toast.makeText(getApplicationContext(),inActiveDate, Toast.LENGTH_LONG).show();
            //System.out.println(inActiveDate );
        } catch (Exception e1) {

            e1.printStackTrace();
        }

        //Activé la photo
        Activephoto.setOnClickListener(new View.OnClickListener() {

            @Override
                public void onClick (View v){
               // if(v==  Activephoto) {
                    try {
                        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File file = getFile();
                        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        startActivityForResult(camera_intent, CAM_REQUEST);
                    } catch (IOError e) {
                        Toast.makeText(getApplicationContext(), "impossible to create le dossier", Toast.LENGTH_LONG).show();
                        Log.d("TAG", "Creation du dossier impossible");
                    }
               // }
            }

        });

        //ajout un fragment a la page
        rest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View v) {

                //PictureGrowFrags fragment;
                /*fragment = new PictureGrowFrags();
                FragmentManager fm = getFragmentManager();
                android.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_place,fragment);*/
                PictureGrowFrags overviewFragment = new PictureGrowFrags();

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_place, overviewFragment).commit();

                TextView test=(TextView) findViewById(R.id.textView3);
                                Toast.makeText(getApplicationContext(), "rest test", Toast.LENGTH_LONG).show();
            }
        });
        /**
         * Zoom l'image
        /*final ImageView zoom = imageView;
        final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        zoom.startAnimation(zoomAnimation);*/
       }

    public void backOnPreviousWindows(View view){
        Intent previous = new Intent(this,InfoListActivity.class);
        startActivity(previous);

    }


    // CHEZ MOI DEBUT POUR ENREGISTRER LA PHOTO
    /**
     * Méthode pour enregistrer les photos dans le téléphone, avec un nom précis pour chaque photo.
     * //@return
     */
    private File getFile() {
        File image_file = null;
        File folder = new File("sdcard/cam_app");

        if (!folder.exists()) {

            boolean imageIsStoredInFolder = folder.mkdirs();
            if(!imageIsStoredInFolder){
                Log.e("Storage error : ", "Picture couldn't be stored on the phone storage");
            }

        }

        //nowString = Calendar.getInstance().toString();
           Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 1);
            Date date = cal.getTime();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHMMSS");
            inActiveDate = format1.format(date);

           // Toast.makeText(getApplicationContext(),inActiveDate, Toast.LENGTH_LONG).show();
            //System.out.println(inActiveDate );*/
        image_file = new File(folder,inActiveDate+"emergency.jpg");
        return image_file;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String path = "sdcard/camera_app/"+inActiveDate+"emergency.jpg";
        imageView.setImageDrawable(Drawable.createFromPath(path));
        final ImageView zoom = imageView;
        final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        zoom.startAnimation(zoomAnimation);

       /* if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

        }*/
    }
    /*public void camera(View v) {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getFile();
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(camera_intent,CAM_REQUEST);
    }*/


  /*  public void camera(View v) {

        boolean qOpened = false;
        try {
            releaseCameraAndPreview();
            mCamera = Camera.open(id);
            qOpened = (mCamera != null);
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void releaseCameraAndPreview() {
        mPreview.setCamera(null);
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            final ImageView zoom = imageView;
            final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
            zoom.startAnimation(zoomAnimation);
        }
    }*/
   // public void camera(View v){

        /** On lance la caméra avec ce listener  pour l'envoi des photographies.**/
       /* boutonCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                // Test de putextra pour l'envoyer sur la gridView de DetailsActivity.
               // cameraIntent.putExtra("NouvellePhoto", gridView);
                // Test de bytearray pour l'envoyer sur l'imageview d'AddElementActivity.
              /*  Bitmap imageBMP = null;
                ByteArrayOutputStream EncodageImageEnByte = new ByteArrayOutputStream();
                imageBMP.compress(Bitmap.CompressFormat.JPEG, 50, EncodageImageEnByte);
                cameraIntent.putExtra(("byteArray"), EncodageImageEnByte.toByteArray());
                startActivityForResult(cameraIntent, CAM_REQUEST);


            }
        });

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getFile();
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));


    }*/




}



