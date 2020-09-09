package com.gads2020.leaderboard.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gads2020.leaderboard.R;
import com.gads2020.leaderboard.adapters.LeaderBoardAdapter.LeaderBoardAdapter;
import com.gads2020.leaderboard.api.APIClient;
import com.gads2020.leaderboard.models.Learner.Learner;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class LearningLeadersFragment extends Fragment {

    private static final String ARG_TAB_NAME_RES_ID = "tab_name_res_id";
    private int resId;

    private RecyclerView mRecyclerView;
    private Call<JsonElement> apiCall;
    private ArrayList<Learner> mLearners;

    public static LearningLeadersFragment newInstance(int resId) {
        LearningLeadersFragment fragment = new LearningLeadersFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TAB_NAME_RES_ID, resId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resId = getArguments().getInt(ARG_TAB_NAME_RES_ID);
        }

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_leader_board, container, false);
        init(root);

        return root;
    }

    private void init(View root) {
        mRecyclerView = root.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLearners = new ArrayList<>();
        fetchData();
    }

    private void fetchData() {
        if(resId == R.string.learning_leaders) {
            fetchTopLearningLeaders();
        } else {
            fetchSkillIqLeaders();
        }
    }

    private void fetchTopLearningLeaders() {
        mLearners.clear();
        apiCall = APIClient.getInstance()
                .fetchLearningLeaders();

        apiCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                if(response.isSuccessful()) {

                    try {
                        JSONArray jsonArray = new JSONArray(response.body().toString());
                        Gson gson = new Gson();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject finalObject = jsonArray.getJSONObject(i);
                            Learner learner = gson.fromJson(finalObject.toString(), Learner.class);
                            mLearners.add(learner);
                        }

                        Toast.makeText(requireContext(), new Gson().toJson(response.body()), Toast.LENGTH_SHORT).show();
                        LeaderBoardAdapter adapter = new LeaderBoardAdapter(mLearners);
                        mRecyclerView.setAdapter(adapter);

                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Unfortunately, a parsing error occurred !", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(requireContext(), "Error : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Failure : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchSkillIqLeaders() {
        apiCall = APIClient.getInstance()
                .fetchSkillIqLeaders();

        apiCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                if(response.isSuccessful()) {

                    try {
                        JSONArray jsonArray = new JSONArray(response.body().toString());
                        Gson gson = new Gson();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject finalObject = jsonArray.getJSONObject(i);
                            Learner learner = gson.fromJson(finalObject.toString(), Learner.class);
                            mLearners.add(learner);
                        }

                        Toast.makeText(requireContext(), new Gson().toJson(response.body()), Toast.LENGTH_SHORT).show();
                        LeaderBoardAdapter adapter = new LeaderBoardAdapter(mLearners);
                        mRecyclerView.setAdapter(adapter);

                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Unfortunately, a parsing error occurred !", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(requireContext(), "Error : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Failure : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}