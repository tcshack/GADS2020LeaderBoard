package com.gads2020.leaderboard.api;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIUploadClient {

    private static final String BASE_URL = "https://gadsapi.herokuapp.com";
    private static Retrofit apiUploadClient = null;
    private static APIRequests apiRequests;



    private static Retrofit getClient(){
        if(apiUploadClient == null){

            apiUploadClient = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }

        return apiUploadClient;
    }

    public static APIRequests getInstance() {
        if (apiRequests == null) {
            apiRequests = getClient().create(APIRequests.class);
            return apiRequests;
        }
        else
            return apiRequests;
    }
}
