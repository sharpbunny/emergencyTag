package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Permet d'ajouter un item à la base de données en inscrivant son type, sa photo et sa description
 */
public class AddElementActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialiseComponents();


        setContentView(R.layout.activity_add_element);
    }

    /**
     * Initialise tous les éléments de la page
     */
    private void initialiseComponents(){
        Spinner typeElementSpinner = (Spinner)findViewById(R.id.typeSpinner);
        Button boutonValider = (Button)findViewById(R.id.validerPhotoButton);

        insertionElementSpinner(typeElementSpinner);
        boutonValider.setOnClickListener(clickListenerValider);
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


        }
    };
}
