package com.afeka.scrummaster.ui.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afeka.scrummaster.databinding.RcTaskBinding;
import com.afeka.scrummaster.layout.Task;
import com.afeka.scrummaster.layout.User;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private final List<Task> mTasks;
    private final Map<String, User> mUsers;
    private final TasksAdapter.onTaskClick listener;
    public static final String[] TASK_STATUS_HEX_COLORS = {"#E76F51", "#F4A261", "#E9C46A", "#2A9D8F", "#264653"};


    public interface onTaskClick {
        void onClick(Task task);
    }

    public TasksAdapter(List<Task> tasks, List<User> users, TasksAdapter.onTaskClick listener) {
        this.mTasks = tasks;
        this.listener = listener;
        this.mUsers = new HashMap<>();
        for (User user :
                users) {
            mUsers.put(user.getEmail(), user);
        }
    }

    @NotNull
    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TasksAdapter.ViewHolder(RcTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder( @NotNull TasksAdapter.ViewHolder holder, int position) {
        Task task = mTasks.get(position);
        holder.mTask = task;
        holder.mNameView.setText(task.getName());
        holder.mDescriptionView.setText(task.getDescription());
        holder.materialCardView.setBackgroundColor(Color.parseColor(TASK_STATUS_HEX_COLORS[task.getTaskStatus().ordinal()]));
        String userAvatarUrl = mUsers.get(task.getCreator()).getAvatar();
        Picasso.get().load(userAvatarUrl).into(holder.mImageView);
        holder.mStatus.setText(task.getTaskStatus().text());
        holder.materialCardView.setOnClickListener(view -> listener.onClick(task));
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameView;
        public final TextView mDescriptionView;
        public final TextView mStatus;
        public final MaterialCardView materialCardView;
        public final ShapeableImageView mImageView;
        public Task mTask;

        public ViewHolder(RcTaskBinding binding) {
            super(binding.getRoot());
            mNameView = binding.cardTaskName;
            materialCardView = binding.cardTask;
            mImageView = binding.cardTaskImage;
            mDescriptionView = binding.cardTaskDescription;
            mStatus = binding.cardTaskStatus;
        }
    }
}
