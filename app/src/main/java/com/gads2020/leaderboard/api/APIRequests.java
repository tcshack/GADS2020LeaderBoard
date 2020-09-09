package com.gads2020.leaderboard.api;

import com.google.gson.JsonElement;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIRequests {

    @GET("/api/hours")
    Call<JsonElement> fetchLearningLeaders();

    @GET("/api/skilliq")
    Call<JsonElement> fetchSkillIqLeaders();

}
