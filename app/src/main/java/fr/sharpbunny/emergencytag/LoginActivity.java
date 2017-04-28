package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class LoginActivity extends Activity {
    private String TAG = LoginActivity.class.getSimpleName();
    private String login="";
    private String password="";
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

        login = ChampLogin.getText().toString();
        password = ChampPassword.getText().toString();

        new GetLogin().execute();

        /**
         * accès a l'activity INFO LIST avec login et mdp "info"
         * */
        if (ChampLogin.getText().toString().equals("info") && ChampPassword.getText().toString().equals("info")) {
            Toast.makeText(this, "Welcome here !", Toast.LENGTH_SHORT).show();//correct password
            Intent intent = new Intent(this, InfoListActivity.class);
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
         * accès a l'activity CameraActivity avec login et mdp "camera"
         * */
        else if (ChampLogin.getText().toString().equals("camera") && ChampPassword.getText().toString().equals("camera")){
            Toast.makeText(this, "Welcome here !", Toast.LENGTH_SHORT).show();//correct password
            Intent intent = new Intent(this, CameraActivity.class);
            startActivity(intent);}
        /**
         accès a l'activity DetailsActivity avec login et mdp "detail"
         * */
        else if (ChampLogin.getText().toString().equals("detail") && ChampPassword.getText().toString().equals("detail")){
            Toast.makeText(this, "Welcome here !", Toast.LENGTH_SHORT).show();//correct password
            Intent intent = new Intent(this, DetailsActivity.class);
            startActivity(intent);}
        /**
         accès a l'activity DetailsActivity avec login et mdp "picture"
         * */
        else if (ChampLogin.getText().toString().equals("picture") && ChampPassword.getText().toString().equals("picture")){
            Toast.makeText(this, "Welcome here !", Toast.LENGTH_SHORT).show();//correct password
            Intent intent = new Intent(this, PictureGrowActivity.class);
            startActivity(intent);}

        else
            {
            Toast.makeText(this, "You shall not pass !", Toast.LENGTH_SHORT).show();//incorrect password
        }
    }
    private class GetLogin extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(LoginActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://10.111.61.148:3000/users";
            HashMap<String, String> params = new HashMap<>();
            params.put("login", login);
            params.put("password", password);
            String jsonStr = sh.makeServiceCall(url, "POST", params);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray response = jsonObj.getJSONArray("contacts");

                    // make use of response here

                    // "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium
                    // doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore
                    // veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim
                    // ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia
                    // consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque
                    // porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur,
                    // adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore
                    // et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis
                    // nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid
                    // ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea
                    // voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem
                    // eum fugiat quo voluptas nulla pariatur?"


                    //


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(),
                    "Got response json from server. Check LogCat for possible errors!",
                    Toast.LENGTH_LONG).show();
        }
    }
}

