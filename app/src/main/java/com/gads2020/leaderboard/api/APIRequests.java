package com.gads2020.leaderboard.api;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequests {

    @GET("/api/hours")
    Call<JsonElement> fetchLearningLeaders();

    @GET("/api/skilliq")
    Call<JsonElement> fetchSkillIqLeaders();

    @FormUrlEncoded
    @POST("/forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    Call<JsonElement> submitProject(
            @Field("entry.1877115667") String firstName,
            @Field("entry.2006916086") String lastName,
            @Field("entry.1824927963") String emailAddress,
            @Field("entry.284483984") String projectLink
    );

}
