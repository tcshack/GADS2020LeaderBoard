package com.gads2020.leaderboard.adapters.LeaderBoardAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gads2020.leaderboard.R;
import com.gads2020.leaderboard.models.Learner.Learner;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.JsonElement;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {

    private final ArrayList<Learner> mLearners;

    public LeaderBoardAdapter(ArrayList<Learner> mLearners) {
        this.mLearners = mLearners == null ? new ArrayList<Learner>() : mLearners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.learner_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Learner mLearner = mLearners.get(position);
        holder.name.setText(mLearner.getName());
        //holder.summary.setText(String.format("%s %s", order.getCustomer().getFirst_name(), order.getCustomer().getLast_name()));
    }

    @Override
    public int getItemCount() {
        return mLearners.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView badgeImage;
        TextView summary;

        ViewHolder(final View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            badgeImage = (ImageView) v.findViewById(R.id.badge_image);
            summary = (TextView) v.findViewById(R.id.summary);
        }
    }


}
