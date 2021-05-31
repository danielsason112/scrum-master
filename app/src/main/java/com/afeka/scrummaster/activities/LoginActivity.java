package com.afeka.scrummaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.interfaces.ResponseListener;
import com.afeka.scrummaster.layout.CreateUser;
import com.afeka.scrummaster.layout.User;
import com.afeka.scrummaster.logic.UserService;

import org.json.JSONException;

import java.io.IOException;


public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText firstnameEditText;
    private EditText lastnameEditText;
    private EditText avatarEditText;
    private Button loginButton;
    private Button registerButton;
    private UserService userService;
    private Context self;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        userService = UserService.getInstance(this);
        self = this;
        this.handler = new Handler();

    }

    @Override
    protected void onStart() {
        super.onStart();
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        firstnameEditText = findViewById(R.id.firstnameEditText);
        lastnameEditText = findViewById(R.id.lastnameEditText);
        avatarEditText = findViewById(R.id.avatarEditText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getEmailInput();
                String password = getPasswordInput();

                try {
                    userService.login(email, password, new ResponseListener<User>() {
                        @Override
                        public void onRes(User res) {
                            Log.e("login", "SUCCESS");
                            Log.e("login", userService.currentUser().toString());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("login", "ERROR");
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        registerButton.setOnClickListener(new RegisterOnClickListener());
    }

    private String getEmailInput() {
        return this.emailEditText.getText().toString();
    }

    private String getPasswordInput() {
        return this.passwordEditText.getText().toString();
    }

    private class RegisterOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (firstnameEditText.getVisibility() == View.GONE) {
                firstnameEditText.setVisibility(View.VISIBLE);
                lastnameEditText.setVisibility(View.VISIBLE);
                avatarEditText.setVisibility(View.VISIBLE);
            } else {
                CreateUser createUser = new CreateUser(
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        firstnameEditText.getText().toString(),
                        lastnameEditText.getText().toString(),
                        avatarEditText.getText().toString()
                );
                try {
                    userService.createUser(createUser, new ResponseListener<User>(){
                        @Override
                        public void onRes(User res) {
                            Log.e("register", "SUCCESS");
                            Log.e("register", userService.currentUser().toString());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("register", "ERROR");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}