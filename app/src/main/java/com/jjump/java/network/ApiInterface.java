package com.jjump.java.network;

import com.jjump.java.ProfileFragment;
import com.jjump.java.data.ReqNicknameData;
import com.jjump.java.data.ResNicknameData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("/post/nickname")
    Call<ResNicknameData> requestNickname(@Body ReqNicknameData req);
}
