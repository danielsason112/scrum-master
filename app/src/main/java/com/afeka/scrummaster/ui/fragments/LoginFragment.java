package com.afeka.scrummaster.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.ui.activities.AuthActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment {

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        final TextInputLayout emailTextInput = view.findViewById(R.id.email_text_input);
        final TextInputEditText emailEditText = view.findViewById(R.id.email_edit_text);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_text);
        MaterialButton nextButton = view.findViewById(R.id.next_button);

        nextButton.setOnClickListener(view1 -> {
            if (!isEmailValid(emailEditText.getText())) {
                emailTextInput.setError(getString(R.string.error_email));
            } else if (!isPasswordValid(passwordEditText.getText())) {
                passwordTextInput.setError(getString(R.string.password_error));
            } else {
                ((AuthActivity)getActivity()).login(emailEditText.getText().toString(), passwordEditText.getText().toString());
            }

        });

        emailEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                passwordTextInput.setError(null);
                return false;
            }
        });

        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    passwordTextInput.setError(null);
                return false;
            }
        });

        return view;
    }

    private boolean isPasswordValid(Editable password) {
        return password != null && password.length() >= 6;
    }

    private boolean isEmailValid(Editable email) {
        return email != null;
    }
}