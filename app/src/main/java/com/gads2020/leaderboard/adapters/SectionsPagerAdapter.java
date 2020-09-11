package com.gads2020.leaderboard.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.gads2020.leaderboard.enums.LearnerType;
import com.gads2020.leaderboard.R;
import com.gads2020.leaderboard.ui.fragments.LearningLeadersFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.learning_leaders, R.string.skill_iq_leaders};
    private static final LearnerType[] LEARNER_TYPES = new LearnerType[]{LearnerType.LEARNING_LEADER, LearnerType.SKILL_IQ_LEADER};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return LearningLeadersFragment.newInstance(LEARNER_TYPES[position]);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}