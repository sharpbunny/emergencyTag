package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {

    Button mybutton;
    Button mybuttonD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mybutton = (Button) findViewById(R.id.buttonMap);
        mybutton.setOnClickListener(gotoMap);

        mybuttonD = (Button) findViewById(R.id.buttonDetail);
        mybuttonD.setOnClickListener(gotoDetail);

    }

    private View.OnClickListener gotoMap = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent =new Intent(LoginActivity.this,MapActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener gotoDetail = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent =new Intent(LoginActivity.this,DetailsActivity.class);
            startActivity(intent);
        }
    };

    public void Login(View v) {

        EditText ChampLogin = (EditText) findViewById(R.id.ChampLogin);
        EditText ChampPassword = (EditText) findViewById(R.id.ChampPassword);
        /**
         * accès a l'activity INFO LIST avec login et mdp "info"
         * */
        if (ChampLogin.getText().toString().equals("info") && ChampPassword.getText().toString().equals("info")) {
            Toast.makeText(this, "Welcome here !", Toast.LENGTH_SHORT).show();//correct password
            Intent intent = new Intent(this, PictureGrowActivity.class);
            startActivity(intent);
        }
        /**
         * accès a l'activity AddElementActivity avec login et mdp "add"
         * */
        else if (ChampLogin.getText().toString().equals("add") && ChampPassword.getText().toString().equals("add")){
            Toast.makeText(this, "Welcome here !", Toast.LENGTH_SHORT).show();//correct password

            Intent intent = new Intent(this, AddElementActivity.class);
            startActivity(intent);}

        /**
         * accès a l'activity CameraActivity avec login et mdp "add"
         * */
        else if (ChampLogin.getText().toString().equals("camera") && ChampPassword.getText().toString().equals("camera")){
            Toast.makeText(this, "Welcome here !", Toast.LENGTH_SHORT).show();//correct password
            Intent intent = new Intent(this, CameraActivity.class);
            startActivity(intent);}
        /**
         * accès a l'activity DetailsActivity avec login et mdp "add"
         * */
        else if (ChampLogin.getText().toString().equals("detail") && ChampPassword.getText().toString().equals("detail")){
            Toast.makeText(this, "Welcome here !", Toast.LENGTH_SHORT).show();//correct password
            Intent intent = new Intent(this, DetailsActivity.class);
            startActivity(intent);}



        else
            {
            Toast.makeText(this, "You shall not pass !", Toast.LENGTH_SHORT).show();//incorrect password
        }
    }
    /**
     * création Json
     * */
        public void writeJSON() {
            JSONObject object = new JSONObject();
            try {
                object.put("email", "@");
                object.put("password","pwd" );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(object);
    }
}

