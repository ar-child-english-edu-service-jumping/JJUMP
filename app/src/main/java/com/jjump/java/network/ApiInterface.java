package com.jjump.java.network;

import com.jjump.java.data.Record;
import com.jjump.java.data.RequestDto;
import com.jjump.java.data.ResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("login")
    Call<ResponseDto> requestLogin(@Body RequestDto req);

    @POST("search/word")
    Call<ResponseDto> requestWordMean(@Body RequestDto req);

    @POST("post/nickname")
    Call<ResponseDto> requestNickRegister(@Body RequestDto req);

    @POST("search/record")
    Call<List<Record>> requestWordSearched(@Body RequestDto req);

}
