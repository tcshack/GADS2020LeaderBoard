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

import com.gads2020.leaderboard.LearnerType;
import com.gads2020.leaderboard.R;
import com.gads2020.leaderboard.models.Learner.Learner;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {

    private final ArrayList<Learner> mLearners;
    private final LearnerType mLearnerType;
    private Context mContext;

    public LeaderBoardAdapter(ArrayList<Learner> mLearners, Context context, LearnerType learnerType) {
        this.mLearners = mLearners == null ? new ArrayList<Learner>() : mLearners;
        this.mContext = context;
        this.mLearnerType = learnerType;
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

        if(mLearnerType == LearnerType.LEARNING_LEADER) {
            holder.summary.setText(mContext.getString(R.string.learner_hours_desc, mLearner.getHours(), mLearner.getCountry()));
        } else {
            holder.summary.setText(mContext.getString(R.string.learner_skill_iq_desc, mLearner.getScore(), mLearner.getCountry()));
        }

        try {
            Picasso.get()
                    .load(mLearner.getBadgeUrl())
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_image_black_24dp)
                    .into(holder.badgeImage);
        }catch (Exception e) {
            holder.badgeImage.setImageResource(R.drawable.ic_image_black_24dp);
        }

    }

    @Override
    public int getItemCount() {
        return mLearners.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

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
