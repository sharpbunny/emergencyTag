package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Permet d'ajouter un item à la base de données en inscrivant son type, sa photo et sa description
 */
public class AddElementActivity extends Activity {


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
        Bitmap b = BitmapFactory.decodeResource(getResources(),
                R.drawable.surprise);

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
     * @method Permet d'insérer les items écrits dans un string_array dans values/strings dans le Spinner
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
        if(getIntent().hasExtra("biteArray")){
            ImageView photo = (ImageView)findViewById(R.id.photoItem);
            Bitmap imageBMP = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("biteArray").length
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

            creationObjetJSON(jsonAEnvoyer);
            connexionAuServeurREST();


        }
    };


    /**
     * Permet de se connecter au serveur REST
     */
    private void connexionAuServeurREST(){
        try{
            URL url = new URL("http://rest.nomadi.fr/item");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.setRequestMethod("POST");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());


        }

        catch(MalformedURLException e){
            e.printStackTrace();
        }

        catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * On créé le JSON à envoyer avec la méthode POST
     */
    private void creationObjetJSON(JSONObject json){
        Spinner typeDeLItem = (Spinner)findViewById(R.id.typeSpinner);
        TextView commentaire = (TextView)findViewById(R.id.textViewCommentaires);

        //On ajoute les éléments dans l'objet JSON
        try{
            json.put("typeItem", typeDeLItem.getSelectedItem().toString());
            if(commentaire.getText().length() > 0){
                json.put("commentaire", commentaire.getText().toString());
            }
        }

        catch(JSONException e){
            e.printStackTrace();
        }

    }
}
