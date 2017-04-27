package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class InfoListActivity extends Activity {


    protected ListView displayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_list);

        Button btnAcessNewElement = (Button)findViewById(R.id.accesNewElement);
        btnAcessNewElement.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(InfoListActivity.this,AddElementActivity.class);
                        startActivity(intent);
                    }
                }
        );

        displayList = (ListView)findViewById(R.id.listElement);

        List<InfoElement> infoelements = genererInfoElementsRandom();
        InfoElementAdapter adapter = new InfoElementAdapter(InfoListActivity.this,infoelements);
        displayList.setAdapter(adapter);
    }

    private List<InfoElement> genererInfoElementsRandom() {
        List<InfoElement> infoelements = new ArrayList<>();
        infoelements.add(new InfoElement(Color.BLACK, "Florent", "Mon premier InfoElement !"));
        infoelements.add(new InfoElement(Color.BLUE, "Kevin", "C'est ici que ça se passe !"));
        infoelements.add(new InfoElement(Color.GREEN, "Logan", "Que c'est beau..."));
        infoelements.add(new InfoElement(Color.RED, "Mathieu", "Il est quelle heure ??"));
        infoelements.add(new InfoElement(Color.GRAY, "Willy", "On y est presque"));
        return infoelements;
    }


}
