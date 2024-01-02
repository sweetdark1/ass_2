package com.example.bvbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    Button registerButton;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        setUpSharedPrefs();
    }

    void setUpViews() {
        username = findViewById(R.id.username_input);
        password = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        setUpLoginListener();
        registerButton = findViewById(R.id.register_button);
        setUpRegisterListener();
    }

    void setUpSharedPrefs() {
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    void setUpLoginListener() {
        loginButton.setOnClickListener(v -> {
            String usernameText = username.getText().toString();
            String passwordText = password.getText().toString();
            if (!usernameText.isEmpty() &&
                    !passwordText.isEmpty()) {
                showToast("Logging in...");
                if (sharedPref.contains("user_names")) {
                    Set<String> usernames = sharedPref.getStringSet("user_names", null);
                    if (usernames.contains(usernameText.toLowerCase())) {
                        try {
                            if (sharedPref.getString(usernameText.toLowerCase(), null).equals(passwordText)) {
                                showToast("Logged in, Redirecting...");
                                loginRedirect();
                            } else {
                                wrongCredentials(true);
                            }
                        } catch (NullPointerException e) {
                            wrongCredentials(false);
                        }
                    } else {
                        wrongCredentials(false);
                    }
                } else {
                    wrongCredentials(false);
                }

            } else {
                wrongCredentials(false);
            }
        });
    }

    void setUpRegisterListener() {
        registerButton.setOnClickListener(v -> {
            String usernameText = username.getText().toString();
            String passwordText = password.getText().toString();
            if (!usernameText.isEmpty() &&
                    !passwordText.isEmpty()) {
                showToast("Registering...");
                Set<String> usernames = new HashSet<>();
                if (sharedPref.contains("user_names")) {
                    usernames.addAll(sharedPref.getStringSet("user_names", null));
                }
                usernames.add(usernameText.toLowerCase());
                editor.putStringSet("user_names", usernames);
                editor.putString(usernameText.toLowerCase(), passwordText);
                editor.commit();
                editor.apply();
                showToast("Registered, Redirecting...");
                loginRedirect();
            } else {
                wrongCredentials(false);
            }
        });
    }

    public void loginRedirect() {
        Intent i = new Intent(getApplicationContext(),SecondActivity.class);
        startActivity(i);
    }

    void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    void wrongCredentials(boolean password) {
        if (password)
            showToast("Wrong Credentials");
        else
            showToast("Wrong password");
        username.setVisibility(View.VISIBLE);
        username.setBackgroundColor(Color.RED);
    }
}