package fr.sharpbunny.emergencytag;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
            EditText login = (EditText) findViewById(R.id.ChampLogin);
            EditText password = (EditText) findViewById(R.id.ChampPassword);
            if (login.getText().toString().equals("1234") && password.getText().toString().equals("1234")) {
                Toast.makeText(this, "Welcome here !", Toast.LENGTH_SHORT).show();//correct password
                Intent intent = new Intent(this, InfoList.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "You shall not pass !", Toast.LENGTH_SHORT).show();//incorrect password

            }
        }
    }
}
