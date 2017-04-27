package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddElement extends Activity {
    Spinner typeElementSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        insertionElementSpinner(typeElementSpinner);
        setContentView(R.layout.activity_add_element);
    }

    /**
     * @method Permet d'insérer les items écrits dans un string_array dans values/strings dans le Spinner
     * @param elementSpinner
     */
    private void insertionElementSpinner(Spinner elementSpinner){
        elementSpinner = (Spinner)findViewById(R.id.typeSpinner);

        //ArrayAdapter: Tableau contenant un item par case
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.typeItemArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        //Permet d'insérer les objets dans une listView
        elementSpinner.setAdapter(adapter);
    }
}
