package com.jjump.java.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    private static Retrofit retrofit;

    // Http 통신을 위한 Retrofit 객체반환
    public static Retrofit getRetrofit() {
        if( retrofit == null )
        {
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl( "http://10.0.2.2:3000/" );
            builder.addConverterFactory( GsonConverterFactory.create() );  // 받아오는 Json 구조의 데이터를 객체 형태로 변환

            retrofit = builder.build();
        }

        return retrofit;
    }
}