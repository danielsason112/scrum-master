package com.afeka.scrummaster.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.interfaces.ResponseListener;
import com.afeka.scrummaster.layout.Task;
import com.afeka.scrummaster.layout.User;
import com.afeka.scrummaster.logic.UserService;
import com.afeka.scrummaster.ui.activities.TaskDetailActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.text.SimpleDateFormat;

public class TaskDetailFragment extends Fragment {
    private Task task;

    public TaskDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        task = ((TaskDetailActivity)getActivity()).getTask();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        final TextView nameTextView = view.findViewById(R.id.detail_task_name);
        nameTextView.setText(task.getName());
        final TextView descriptionTextView = view.findViewById(R.id.detail_task_description);
        descriptionTextView.setText(task.getDescription());
        final TextView statusTextView = view.findViewById(R.id.detail_task_status);
        statusTextView.setText(task.getTaskStatus().text());
        final ImageView avatarImg = view.findViewById(R.id.detail_task_image);
        try {
            UserService.getInstance(getContext()).getUserById(task.getCreator(), new ResponseListener<User>() {
                @Override
                public void onRes(User res) {
                    Picasso.get().load(res.getAvatar()).into(avatarImg);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final TextView creatorTextView = view.findViewById(R.id.detail_task_creator);
        creatorTextView.setText(task.getCreator());
        final TextView createdTextView = view.findViewById(R.id.detail_task_created);
        createdTextView.setText(new SimpleDateFormat("dd-MM-yyyy").format(task.getCreatedAt()));
        final Button editTaskButton = view.findViewById(R.id.edit_task_button);
        if (UserService.getInstance(getContext()).currentUser().getEmail().equals(task.getCreator())) {
            editTaskButton.setVisibility(View.VISIBLE);
            editTaskButton.setOnClickListener(view1 ->
                    ((TaskDetailActivity)getActivity()).navigateTo(new EditTaskFragment(), true));
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        task = ((TaskDetailActivity)getActivity()).getTask();
    }
}