package com.afeka.scrummaster.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.afeka.scrummaster.ui.fragments.LoginFragment;
import com.afeka.scrummaster.interfaces.NavigationHost;
import com.afeka.scrummaster.R;
import com.afeka.scrummaster.ui.fragments.RegisterFragment;
import com.afeka.scrummaster.interfaces.ResponseListener;
import com.afeka.scrummaster.layout.CreateUser;
import com.afeka.scrummaster.layout.User;
import com.afeka.scrummaster.logic.UserService;

import org.json.JSONException;

import java.util.Objects;

public class AuthActivity extends AppCompatActivity implements NavigationHost {
    private UserService userService;
    private String userEmail;
    private String userPassword;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Objects.requireNonNull(getSupportActionBar()).hide();

        handler = new Handler();

        userService = UserService.getInstance(getApplicationContext());

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    public void login(String email, String password) {
        try {
            userService.login(email, password, new ResponseListener<User>() {
                @Override
                public void onRes(User res) {
                    Log.d("LOGIN", "SUCCESS");
                    handler.post(() -> finish());
                }

                @Override
                public void onError(Exception e) {
                    Log.d("LOGIN", "ERROR");
                    userEmail = email;
                    userPassword = password;
                    navigateTo(new RegisterFragment(), true);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void registerUser(String firstname, String lastname, String avatar) {
        try {
            userService.createUser(
                    new CreateUser(
                            userEmail,
                            userPassword,
                            firstname,
                            lastname,
                            avatar
                    ),
                    new ResponseListener<User>() {
                        @Override
                        public void onRes(User res) {
                            Log.d("SIGN_UP", "SUCCESS");
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    }
            );
        } catch (Exception e) {
            Log.e("SIGN_UP", "ERROR");
        }
    }
}