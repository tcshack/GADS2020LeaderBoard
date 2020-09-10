package com.gads2020.leaderboard.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gads2020.leaderboard.Constants;
import com.gads2020.leaderboard.LearnerType;
import com.gads2020.leaderboard.R;
import com.gads2020.leaderboard.adapters.LeaderBoardAdapter.LeaderBoardAdapter;
import com.gads2020.leaderboard.api.APIClient;
import com.gads2020.leaderboard.callbacks.DataFetchCallBack.DataFetchCallBack;
import com.gads2020.leaderboard.models.Learner.Learner;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LearningLeadersFragment extends Fragment {

    private static final String ARG_LEARNER_TYPE = "learner_type";
    private LearnerType learnerType;

    private RecyclerView mRecyclerView;
    private Call<JsonElement> apiCall;
    private ArrayList<Learner> mLearners;
    private DataFetchCallBack mDataFetchListener;

    static LearningLeadersFragment newInstance(LearnerType type) {
        LearningLeadersFragment fragment = new LearningLeadersFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_LEARNER_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            if (requireContext() instanceof DataFetchCallBack) {
                mDataFetchListener = (DataFetchCallBack) requireContext();
            } else {
                throw new RuntimeException(requireContext().toString()
                        + " must implement DataFetchCallBack");
            }
        }catch (Exception ignore) {}
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            learnerType = (LearnerType) getArguments().getSerializable(ARG_LEARNER_TYPE);
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
        if(learnerType == LearnerType.LEARNING_LEADER) {
            fetchTopLearningLeaders();
        } else {
            fetchSkillIqLeaders();
        }
    }

    private void fetchTopLearningLeaders() {
        mLearners.clear();
        apiCall = APIClient.getInstance(Constants.API_BASE_URL)
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

                        LeaderBoardAdapter adapter = new LeaderBoardAdapter(mLearners, requireContext(), learnerType);
                        mRecyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        mDataFetchListener.onFailure(requireContext().getResources().getString(R.string.something_wrong));
                    }

                } else {
                    mDataFetchListener.onFailure(requireContext().getResources().getString(R.string.could_not_fetch_data));
                }

            }

            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                mDataFetchListener.onFailure(requireContext().getResources().getString(R.string.network_error));
            }
        });
    }

    private void fetchSkillIqLeaders() {
        apiCall = APIClient.getInstance(Constants.API_BASE_URL)
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

                        LeaderBoardAdapter adapter = new LeaderBoardAdapter(mLearners, requireContext(), learnerType);
                        mRecyclerView.setAdapter(adapter);
                        mDataFetchListener.onSuccess();

                    } catch (JSONException e) {
                        mDataFetchListener.onFailure(requireContext().getResources().getString(R.string.something_wrong));
                    }

                } else {
                    mDataFetchListener.onFailure(requireContext().getResources().getString(R.string.could_not_fetch_data));
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                mDataFetchListener.onFailure(requireContext().getResources().getString(R.string.network_error));
            }
        });
    }
}