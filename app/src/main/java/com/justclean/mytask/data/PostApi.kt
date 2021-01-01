package com.justclean.mytask.data

import com.justclean.mytask.data.network.PostDataResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST

interface PostApi {

    @GET("/posts")
    suspend fun getPostList():Response<PostDataResponse>


}