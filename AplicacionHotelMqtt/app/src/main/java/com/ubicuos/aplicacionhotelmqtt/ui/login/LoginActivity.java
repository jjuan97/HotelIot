package com.ubicuos.aplicacionhotelmqtt.ui.login;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ubicuos.aplicacionhotelmqtt.MainActivity;
import com.ubicuos.aplicacionhotelmqtt.R;

public class LoginActivity extends AppCompatActivity {

    public EditText usernameEditText, passwordEditText;
    public Button loginButton;
    public ProgressBar loadingProgressBar;
    FirebaseAuth loginFirebaseAuth;
    public FirebaseAuth.AuthStateListener stateListenerAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        loginFirebaseAuth = FirebaseAuth.getInstance();
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);

        stateListenerAuth = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser hotelFirebaseUser = loginFirebaseAuth.getCurrentUser();
                if (hotelFirebaseUser != null){
                    Toast.makeText(LoginActivity.this, getString(R.string.login_ready),Toast.LENGTH_SHORT).show();
                    Intent toMain = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(toMain);
                }
                else{
                    Toast.makeText(LoginActivity.this, getString(R.string.login_please),Toast.LENGTH_SHORT).show();
                }
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameEditText.getText().toString();
                String pass = passwordEditText.getText().toString();

                if (email.isEmpty()) {
                    usernameEditText.setError(getString(R.string.empty_username));
                    usernameEditText.requestFocus();
                } else if (pass.isEmpty()) {
                    passwordEditText.setError(getString(R.string.empty_password));
                    passwordEditText.requestFocus();
                } else if (email.isEmpty() && pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, getString(R.string.empty_fields), Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pass.isEmpty())) {
                    loginFirebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                            } else {
                                loadingProgressBar.setVisibility(View.VISIBLE);
                                String welcome = getString(R.string.welcome);
                                Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
                                Intent intentToMain = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intentToMain);
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loginFirebaseAuth.addAuthStateListener(stateListenerAuth);
    }
}
