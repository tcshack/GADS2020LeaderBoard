package com.gads2020.leaderboard.api;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIClient {

    private static Retrofit retrofit;
    private static APIRequests apiRequests;
    private static final String BASE_URL = "https://gadsapi.herokuapp.com";
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB

    static Context context;

    public static APIRequests getInstance() {
        if (apiRequests == null) {

            //Cache cache = new Cache(App.getContext().getCacheDir(), cacheSize);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                   // .cache(cache)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain)
                                throws IOException {
                            Request request = chain.request();
                            /*Request newRequest;
                            newRequest = request.newBuilder()
                                    .addHeader("Authorization", "Token " + new AppPreferences(App.getContext()).getUserToken())
                                    .build();*/
                            return chain.proceed(request);
                        }
                    })
                    .connectTimeout(4, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            apiRequests = retrofit.create(APIRequests.class);

            return apiRequests;

        }
        else {
            return apiRequests;
        }
    }
}
