package com.afeka.scrummaster.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afeka.scrummaster.layout.Task;
import com.afeka.scrummaster.databinding.FragmentItemBinding;

import java.util.List;

public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.ViewHolder> {

    public interface onTaskClickListener {
        void onTaskClick(Task task);
    }

    public static final String[] TASK_STATUS_HEX_COLORS = {"#E76F51", "#F4A261", "#E9C46A", "#2A9D8F", "#264653"};

    private final List<Task> mValues;
    private onTaskClickListener listener;

    public TasksListAdapter(List<Task> items, onTaskClickListener listener) {
        mValues = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        holder.mContentView.setText(mValues.get(position).getDescription());
        GradientDrawable border = new GradientDrawable();
        border.setColor(Color.parseColor(TASK_STATUS_HEX_COLORS[mValues.get(position).getTaskStatus().ordinal()]));
        border.setStroke(1, 0xFFCCCCCC);
        holder.mWrapperView.setBackground(border);
//        holder.mWrapperView.setBackgroundColor(Color.parseColor(TASK_STATUS_HEX_COLORS[mValues.get(position).getTaskStatus().ordinal()]));
        holder.bindListener(listener, mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final View mWrapperView;
        public Task mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            mWrapperView = binding.taskContentWrapper;
        }

        public void bindListener(final onTaskClickListener listener, Task task) {
            mWrapperView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onTaskClick(task);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}