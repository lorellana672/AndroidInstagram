package com.orellana.androidinstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String password;
    String userText;
    Boolean signUpMode = true;
    TextView buttonChangeText;
    Button signUpButton;
    ProgressDialog progressDialog;

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
        } else {
            progressDialog.setMessage("Loading");
            progressDialog.show();
            createAcount(userText, password);
        }
    }

    public void createAcount(String userText, String password){
        mAuth.createUserWithEmailAndPassword(userText, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        signUpButton = findViewById(R.id.signUpButton);
        buttonChangeText = findViewById(R.id.buttonChangeText);
        buttonChangeText.setOnClickListener(this);
        signUpButton.setText("Signup");

        progressDialog = new ProgressDialog(this);
    }


}
