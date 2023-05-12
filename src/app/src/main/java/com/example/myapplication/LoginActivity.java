package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(getBaseContext());

        EditText username = findViewById(R.id.Login_editText_userName);
        EditText password = findViewById(R.id.Login_editText_Password);
        Button login_bt = findViewById(R.id.Login_button_LOGIN);

        login_bt.setOnClickListener(v -> {
            String userName = username.getText().toString();
            String passWord = password.getText().toString();
            BufferedReader br;
            try {
                br = new BufferedReader(new InputStreamReader(getAssets().open("loginDetails.csv"), StandardCharsets.UTF_8));
                String line;
                boolean loginFlag = true;
                while ((line = br.readLine()) != null){
                    String[] tokens = line.split(",");
                    if (tokens[1].equals(userName) && tokens[2].equals(passWord)) {
                        User user = new User(tokens[0], tokens[1], tokens[2]);
                        Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
                        loginIntent.putExtra("USER", user);
                        startActivity(loginIntent);
                        Toast.makeText(getApplicationContext(), "welcome, " + userName, Toast.LENGTH_LONG).show();
                        loginFlag = false;
                        break;
                    }
                }
                if (loginFlag){
                    Toast.makeText(getApplicationContext(),"Incorrect username or password",Toast.LENGTH_LONG).show();
                }
                br.close();
            } catch (IOException e) {
//            throw new RuntimeException(e);
                e.printStackTrace();
            }
        });

    }
}