package com.orellana.androidinstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRegistrar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String password;
    String userText;
    Boolean signUpMode = true;
    TextView buttonChangeText;
    Button signUpButton;

    private FirebaseAuth mAuth;

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

        userText = findViewById(R.id.userText).toString().trim();
        password = findViewById(R.id.password).toString().trim();

        if (userText.equals("") || password.equals("")){
            Toast.makeText(this, "User and Password are required", Toast.LENGTH_SHORT).show();
        } else if (userText.length() <= 3){
            Toast.makeText(this, "The user name is to short", Toast.LENGTH_SHORT).show();
        } else if (password.length() <= 4){
            Toast.makeText(this, "The password is to short", Toast.LENGTH_SHORT).show();
        }

        mAuth.createUserWithEmailAndPassword(userText, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "User registered", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Error. User not registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonChangeText = findViewById(R.id.buttonChangeText);
        buttonChangeText.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }


}
