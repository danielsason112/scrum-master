package com.afeka.scrummaster.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.ui.activities.AuthActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

public class RegisterFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        final TextInputEditText firstnameEditText = view.findViewById(R.id.firstname_edit_text);
        final TextInputEditText lastnameEditText = view.findViewById(R.id.lastname_edit_text);
        final TextInputEditText avatarEditText = view.findViewById(R.id.avatar_edit_text);
        MaterialButton registerButton = view.findViewById(R.id.register_button);

        registerButton.setOnClickListener(view1 -> {
            String firstname = firstnameEditText.getText().toString();
            String lastname = lastnameEditText.getText().toString();
            String avatar = avatarEditText.getText().toString();
            ((AuthActivity)getActivity()).registerUser(firstname, lastname, avatar);
        });

        return view;
    }
}