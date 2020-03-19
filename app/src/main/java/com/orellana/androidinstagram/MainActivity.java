package com.orellana.androidinstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText password;
    EditText userText;
    Boolean signUpMode = true;
    TextView buttonChangeText;
    Button signUpButton;
    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference Users;

    public void showUserList() {
        Intent intent =  new Intent(getApplicationContext(), UserListActivity.class);
        //startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Users = database.getReference("Users");

        userText = findViewById(R.id.userText);
        password = findViewById(R.id.password);

        signUpButton = findViewById(R.id.signUpButton);
        buttonChangeText = findViewById(R.id.buttonChangeText);
        buttonChangeText.setOnClickListener(this);
        signUpButton.setText("Signup");
        progressDialog = new ProgressDialog(this);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            showUserList();
        }
    }

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

        String user = userText.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (user.equals("") || pass.equals("")){
            Toast.makeText(this, "User and Password are required", Toast.LENGTH_SHORT).show();
        } else if (user.length() <= 3){
            Toast.makeText(this, "The user name is to short", Toast.LENGTH_SHORT).show();
        } else if (pass.length() <= 4){
            Toast.makeText(this, "The password is to short", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Loading");
            progressDialog.show();
            if (signUpMode) {
                createAcount(user, pass);
            } else {
                signIn(user, pass);
            }
        }
    }

    public void createAcount(final String userText, final String password){
        mAuth.createUserWithEmailAndPassword(userText, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication success", Toast.LENGTH_SHORT).show();
                            putInDB(userText);
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public void signIn(String user, String password) {
        mAuth.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            showUserList();
        }
    }

    public void putInDB(String email) {
        String id = Users.push().getKey();
        Users usuario = new Users(id,email,email);
        Users.child("usuarios").child(id).setValue(usuario);
    }

}
