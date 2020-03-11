package com.orellana.androidinstagram;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText password;
    EditText userText;
    Boolean signUpMode = true;
    TextView buttonChangeText;
    Button signUpButton;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonChangeText){
            signUpButton = findViewById(R.id.signUpButton);
            if (signUpMode){
                signUpMode = false;
                signUpButton.setText("Login");
                buttonChangeText.setText("Or, Signup");
            } else {
                signUpMode = true;
                signUpButton.setText("Signup");
                buttonChangeText.setText("Or, Login");
            }

        }

    }

    public void signUp(View view) {

        userText = findViewById(R.id.userText);
        password = findViewById(R.id.password);

        if (userText.getText().toString().equals("") || password.getText().toString().equals("")){
            Toast.makeText(this, "User and Password are required", Toast.LENGTH_SHORT).show();
        } else if (userText.getText().length() <= 3){
            Toast.makeText(this, "The user name is to short", Toast.LENGTH_SHORT).show();
        } else if (password.getText().length() <= 4){
            Toast.makeText(this, "The password is to short", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonChangeText = findViewById(R.id.buttonChangeText);
        buttonChangeText.setOnClickListener(this);
    }
}
