package com.example.lenovo.onlinetrending;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Lenovo on 23-06-2018.
 */

public interface NewsApi {
    @GET("/v2/top-headlines?country=in&apiKey=1502412d354c419b867a91a99f9ebdd6")
    Call<List<Newspojo>> getNewscall();
}
