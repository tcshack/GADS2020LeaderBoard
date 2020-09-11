package com.gads2020.leaderboard;

public interface DataFetchCallBack {

    // This callback is aimed to notified the Activity holder, when
    // API request has been successful or not.

    void onSuccess();
    void onFailure(String errorMessage);
}
