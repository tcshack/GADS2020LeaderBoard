package com.gads2020.leaderboard.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import com.gads2020.leaderboard.R;
import com.gads2020.leaderboard.DataFetchCallBack;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.gads2020.leaderboard.adapters.SectionsPagerAdapter;

public class LeaderBoardActivity extends AppCompatActivity implements DataFetchCallBack {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        MaterialCardView btnSubmit = findViewById(R.id.btn_submit);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(getResources().getString(R.string.progress_bar_title));
        mProgressDialog.setMessage(getResources().getString(R.string.please_wait));
        mProgressDialog.show();
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeaderBoardActivity.this, SubmissionActivity.class));
            }
        });

    }

    @Override
    public void onSuccess() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onFailure(String errorMessage) {
        mProgressDialog.dismiss();
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}