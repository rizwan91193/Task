package com.justclean.mytask.data

import com.justclean.mytask.data.network.PostDataResponse
import com.justclean.mytask.data.network.DetailDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {

    @GET("/posts")
    suspend fun getPostList(): Response<PostDataResponse>

    @GET("/posts/{post_id}/comments")
    suspend fun getCommentList(@Path("post_id") id: Int): Response<DetailDataResponse>


}