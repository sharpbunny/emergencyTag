package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void Login(View v)
    {
        {
            EditText ChampLogin = (EditText) findViewById(R.id.ChampLogin);
            EditText ChampPassword = (EditText) findViewById(R.id.ChampPassword);
            if (ChampLogin.getText().toString().equals("info") && ChampPassword.getText().toString().equals("info")) {
                Toast.makeText(this, "Welcome here !", Toast.LENGTH_SHORT).show();//correct password
                Intent intent = new Intent(this, InfoList.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "You shall not pass !", Toast.LENGTH_SHORT).show();//incorrect password

            }
        }
    }
}
