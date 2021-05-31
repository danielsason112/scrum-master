package com.afeka.scrummaster.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afeka.scrummaster.data.Team;
import com.afeka.scrummaster.databinding.FragmentTeamBinding;

import java.util.List;

public class TeamsListAdapter extends RecyclerView.Adapter<TeamsListAdapter.ViewHolder> {

    public interface onTeamClickListener {
        void onTeamClick(Team team);
    }

    public static final String[] TASK_STATUS_HEX_COLORS = {"#E76F51", "#F4A261", "#E9C46A", "#2A9D8F", "#264653"};

    private final List<Team> mValues;
    private onTeamClickListener listener;

    public TeamsListAdapter(List<Team> items, onTeamClickListener listener) {
        mValues = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentTeamBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        holder.bindListener(listener, mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final View mWrapperView;
        public Team mItem;

        public ViewHolder(FragmentTeamBinding binding) {
            super(binding.getRoot());
            mIdView = binding.teamName;
            mWrapperView = binding.teamContentWrapper;
        }

        public void bindListener(final onTeamClickListener listener, Team team) {
            mWrapperView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onTeamClick(team);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }

}