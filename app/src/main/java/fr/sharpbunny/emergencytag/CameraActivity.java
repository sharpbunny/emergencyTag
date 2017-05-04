package fr.sharpbunny.emergencytag;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import static fr.sharpbunny.emergencytag.R.id.gridView;
import static fr.sharpbunny.emergencytag.R.id.photoItem;

public class CameraActivity extends AppCompatActivity {
    /**
     * TAG is used in log to identify origin of log
     */
    public static final String TAG = CameraActivity.class.getSimpleName();

    Button boutonCamera;
    ImageView imageView;
    Bitmap b;
    File file;

    public final static int TAKE_PICTURE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        boutonCamera = (Button) findViewById(R.id.boutonCamera);
        imageView = (ImageView) findViewById(R.id.image_view);

        /**
         * Method on Click to activate the camera on the cell. * */
        boutonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                file = getFile();
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }
        });
    }
    /**
     * Method using the getfile() function so as to record the picture and store it in the cell's database.
     * @return
     */

    private File getFile() {
        File folder = new File("sdcard/camera_app");
        Calendar DateActuelle = Calendar.getInstance();
        DateActuelle.add(Calendar.DATE, 1);
        Date date = DateActuelle.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHMMSS");
        String inActiveDate;
        inActiveDate = format1.format(date);


        if (!folder.exists()) {
            folder.mkdir();
        }

        File image_file = new File(folder, inActiveDate + "cam_image.jpg");

        return image_file;
    }
    /**
     * Method using a try catch in order to put the path to the image registered. And send the picture in the AddElementActivity (photoView) as a new element. Or it will be send in the DetailsActivity(GridView) as a new picture.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data ){
        Intent addElementIntent = new Intent(CameraActivity.this, AddElementActivity.class);
        final boolean AjouterNouvelElement =  getIntent().getExtras().getBoolean("isNewElement");
        final boolean AjouterNouvellePhoto = getIntent().getExtras().getBoolean("ajouterNouvellePhoto");
       Uri uri = Uri.fromFile(file);

        if(requestCode == TAKE_PICTURE){

                    if( AjouterNouvelElement == true){

                        try{
                            b = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }

                        ByteArrayOutputStream bs = new ByteArrayOutputStream(); //Tableau d'octets stocké en mémoire

                        // Convert picture into bitmap format.
                        b.compress(Bitmap.CompressFormat.JPEG, 50, bs);

                        //Sending bytearray in the AddElementActivity.
                        addElementIntent.putExtra("byteArray", bs.toByteArray());
                        startActivity(addElementIntent);
                    }
                    if(AjouterNouvellePhoto == true){
                        try{
                            b = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }

                        ByteArrayOutputStream bs = new ByteArrayOutputStream(); //Tableau d'octets stocké en mémoire

                        // Convert picture into bitmap format.
                        b.compress(Bitmap.CompressFormat.JPEG, 50, bs);

                        //Sending bytearray in the AddElementActivity.
                        addElementIntent.putExtra("byteArray", bs.toByteArray());
                        startActivity(addElementIntent);
                    }

                    }
        }
    }




