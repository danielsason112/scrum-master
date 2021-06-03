package com.afeka.scrummaster.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.data.Team;
import com.afeka.scrummaster.databinding.RcTeamBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {
    private final List<Team> mTeams;
    private final boolean isMember;
    private final onActionButtonClick listener;

    public interface onActionButtonClick {
        void onClick(Team team);
    }

    public TeamsAdapter(List<Team> mTeams, boolean isMember, onActionButtonClick listener) {
        this.mTeams = mTeams;
        this.isMember = isMember;
        this.listener = listener;
    }

    @NotNull
    @Override
    public TeamsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeamsAdapter.ViewHolder(RcTeamBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder( @NotNull TeamsAdapter.ViewHolder holder, int position) {
        holder.mTeam = mTeams.get(position);
        holder.mNameView.setText(mTeams.get(position).getName());
        holder.mActionButton.setText(isMember ? R.string.to_board_button : R.string.join_button);
        holder.mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(holder.mTeam);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameView;
        public final Button mActionButton;
        public Team mTeam;

        public ViewHolder( RcTeamBinding binding) {
            super(binding.getRoot());
            mNameView = binding.cardTeamName;
            mActionButton = binding.joinButton;
        }
    }
}
