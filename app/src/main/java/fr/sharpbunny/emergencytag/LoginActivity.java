package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class LoginActivity extends Activity {
    /**
     * TAG is used in log to identify origin of log
     */
    private String TAG = LoginActivity.class.getSimpleName();

    private String login = "";
    private String password = "";
    private boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    /**
     *
     * @param v
     */
    public void Login(View v) {

        EditText ChampLogin = (EditText) findViewById(R.id.ChampLogin);
        EditText ChampPassword = (EditText) findViewById(R.id.ChampPassword);

        login = ChampLogin.getText().toString();
        password = ChampPassword.getText().toString();

        // hack to login with rest without typing anything
        if (login.isEmpty()) {
            login = "sam@soung.ue";
            password = "inburnwetrust";
        }

        new GetLogin().execute();
    }

    /**
     * Async task to check login
     */
    private class GetLogin extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(LoginActivity.this, R.string.requestAccess, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            // take url for login in resource string file
            String url = getResources().getString(R.string.LoginUrl);
            HashMap<String, String> params = new HashMap<>();
            params.put("email", login);
            params.put("pwd", password);
            String jsonStr = sh.makeServiceCall(url, "POST", params);

            Log.i(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON message
                    if (jsonObj.getString("message").equals("Connexion OK")){
                        connected = true;
                    } else {
                        connected = false;
                    }

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
            if (connected) {
                Intent intent = new Intent(getApplicationContext(), InfoListActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, R.string.accessDenied,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}

