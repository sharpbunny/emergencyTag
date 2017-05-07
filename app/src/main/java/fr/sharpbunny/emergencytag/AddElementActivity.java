package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Permet d'ajouter un item à la base de données en inscrivant son type, sa photo et sa description
 */
public class AddElementActivity extends Activity {

    private static final String TAG = AddElementActivity.class.getSimpleName();

    JSONObject item = new JSONObject();

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
            envoyerImagePourLAgrandir();

            return true;
        }
    };

    /**
     * Lorsque l'utilisateur appuie sur l'image, celle-ci est envoyée à GrowPictureActivity. Elle sera
     * alors affichée en plein écran.
     */
    private void envoyerImagePourLAgrandir(){
        //Déclaration des objets
        Intent pictureGrowIntent = new Intent(this, PictureGrowActivity.class);
        ImageView image = (ImageView)findViewById(R.id.photoItem);
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap b = drawable.getBitmap();

        ByteArrayOutputStream bs = new ByteArrayOutputStream(); //Tableau d'octets stocké en mémoire

        //L'image est compressée puis stockée sous forme d'un tableau de données dans bs
        b.compress(Bitmap.CompressFormat.JPEG, 50, bs);

        //On envoie le tableau de byte dans l'activité pictureGrowActivity
        pictureGrowIntent.putExtra("byteArray", bs.toByteArray());
        startActivity(pictureGrowIntent);
    }

    /**
     * Initialise tous les éléments de la page
     */
    private void initialisation(){
        Spinner typeElementSpinner = null;
        typeElementSpinner = (Spinner)findViewById(R.id.typeSpinner);
        Button boutonValider = (Button)findViewById(R.id.validerPhotoButton);
        ImageView photo = (ImageView)findViewById(R.id.photoItem);

        insertionElementSpinner(typeElementSpinner);
        boutonValider.setOnClickListener(clickListenerValider);
        photo.setOnTouchListener(agrandirImage);
        recuperationImage();

    }

    /**
     * Permet d'insérer les items écrits dans un string_array dans values/strings dans le Spinner
     * @param elementSpinner
     */
    private void insertionElementSpinner(Spinner elementSpinner){
        //ArrayAdapter: Tableau contenant un item par case
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.typeItemArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);



        //Permet d'insérer les objets dans une listView
        elementSpinner.setAdapter(adapter);
    }

    /**
     * On récupère les informations envoyées depuis l'appareil photo du smartphone
     * http://stackoverflow.com/questions/13226263/i-want-to-transfer-the-image-from-one-activity-to-another
     */
    private void recuperationImage(){
        if(getIntent().hasExtra("byteArray")){
            ImageView photo = (ImageView)findViewById(R.id.photoItem);
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
            JSONObject jsonAEnvoyer = new JSONObject();

            item = creationObjetJSON(jsonAEnvoyer);
            Toast.makeText(AddElementActivity.this, "Connexion au serveur REST", Toast.LENGTH_SHORT).show();
            connexionAuServeurREST();


        }
    };


    /**
     * Permet de se connecter au serveur REST
     */
    private void connexionAuServeurREST(){

        new postItem().execute();

    }

    /**
     * On créé le JSON à envoyer avec la méthode POST
     */
    private JSONObject creationObjetJSON(JSONObject json){
        Spinner typeDeLItem = (Spinner)findViewById(R.id.typeSpinner);
        TextView commentaire = (TextView)findViewById(R.id.textViewCommentaires);

        try {

            json.put("idUser", 1);
            if (commentaire.getText() != null) {
                json.put("commentaire", commentaire.getText().toString());
            }

            json.put("majItem", "2016/10/5");
            json.put("item_Lat", 30);
            json.put("item_Lon", 54);
            json.put("id_Type", 1);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }


    private class postItem extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(AddElementActivity.this, R.string.postingItem, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "On entre dans doInBackground");
            String json = item.toString();
            Spinner typeDeLItem = (Spinner)findViewById(R.id.typeSpinner);
            TextView commentaire = (TextView)findViewById(R.id.textViewCommentaires);
            try{

                URL url = new URL(getResources().getString(R.string.ItemUrl));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                try {

                    conn.setRequestMethod("POST");

                    conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
                    conn.setDoOutput(true);
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
}
