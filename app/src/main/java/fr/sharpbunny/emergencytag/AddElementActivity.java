package fr.sharpbunny.emergencytag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Permet d'ajouter un item à la base de données en inscrivant son type, sa photo et sa description
 */
public class AddElementActivity extends AppCompatActivity {

    private static final String TAG = AddElementActivity.class.getSimpleName();
    private Spinner spinner;
    private ArrayList<TypeItem> listTypeItem;

    JSONObject jsonObject = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_element);
        initialisation();
    }

    /**
     * Evenement qui permet d'envoyer vers PictureGrowActivity lorsque l'utilisateur appuie sur la
     * photo
     */
    private View.OnTouchListener agrandirImage = new View.OnTouchListener(){
        public boolean onTouch(View v, MotionEvent event){
            // Calling intent
            Intent pictureGrowIntent = new Intent(AddElementActivity.this, PictureGrowActivity.class);
            ImageView image = (ImageView)findViewById(R.id.photoItem);
            BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
            Bitmap b = drawable.getBitmap();

            //Tableau d'octets stocké en mémoire
            ByteArrayOutputStream bs = new ByteArrayOutputStream();

            //L'image est compressée puis stockée sous forme d'un tableau de données dans bs
            b.compress(Bitmap.CompressFormat.JPEG, 50, bs);

            //On envoie le tableau de byte dans l'activité pictureGrowActivity
            pictureGrowIntent.putExtra("byteArray", bs.toByteArray());
            startActivity(pictureGrowIntent);

            return true;
        }
    };

    /**
     * Initialise tous les éléments de la page
     */
    private void initialisation(){

        //ArrayAdapter: Tableau contenant un item par case
        new getListItem().execute();

        Button boutonValider = (Button)findViewById(R.id.validerPhotoButton);
        boutonValider.setOnClickListener(clickListenerValider);

        ImageView photo = (ImageView)findViewById(R.id.photoItem);
        photo.setOnTouchListener(agrandirImage);
        if(getIntent().hasExtra("byteArray")){
            photo = (ImageView)findViewById(R.id.photoItem);
            Bitmap imageBMP = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length
            );
            photo.setImageBitmap(imageBMP);
        }

    }

    /**
     * Evenement : click sur le bouton Valider
     * Permet d'enregistrer la photo, le type de l'élement pris en photo et le
     * commentaire associé dans la base de données
     */
    private View.OnClickListener clickListenerValider = new View.OnClickListener(){
        public void onClick(View v){


            TextView comment = (TextView)findViewById(R.id.textViewCommentaires);
            Log.d(TAG, "ID type: " + spinner.getSelectedItemPosition());
            try {

                jsonObject.put("idUser", 1);
                if (comment.getText() != null) {
                    jsonObject.put("commentaire", comment.getText().toString());
                }

                jsonObject.put("majItem", "2016/10/5");
                jsonObject.put("item_Lat", 43.564772);
                jsonObject.put("item_Lon", 3.845787);
                jsonObject.put("id_Type", listTypeItem.get(spinner.getSelectedItemPosition()).getIdType());

            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            Toast.makeText(AddElementActivity.this, "Connexion au serveur REST", Toast.LENGTH_SHORT).show();
            new postItem().execute();

        }
    };


    private class postItem extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(AddElementActivity.this, R.string.postingItem, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "Trying to post item data...");
            String json = jsonObject.toString();

            try{

                URL url = new URL(getResources().getString(R.string.ItemUrl));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                try {

                    conn.setRequestMethod("POST");

                    conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
                    conn.setDoOutput(true);
                    //conn.setDoInput(true);
                    conn.connect();

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(json);  //<--- sending data.

                    wr.flush();
                    BufferedReader serverAnswer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = serverAnswer.readLine()) != null) {

                        Log.i("LINE: ", line); //<--If any response from server
                        //use it as you need, if server send something back you will get it here.
                    }

                    wr.close();
                    serverAnswer.close();

                } finally {
                    conn.disconnect();
                }

            }

            catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }

    }

    private class getListItem extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(AddElementActivity.this, R.string.gettingListItem, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = getResources().getString(R.string.TypeUrl);
            listTypeItem = TypeItem.getListFromRest(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            TypeItemAdapter adapter = new TypeItemAdapter(AddElementActivity.this, android.R.layout.simple_spinner_item, listTypeItem);

            spinner =  (Spinner) findViewById(R.id.typeSpinner);
            spinner.setAdapter(adapter);

        }
    }
}
