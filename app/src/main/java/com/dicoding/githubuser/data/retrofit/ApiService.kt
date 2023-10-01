package com.dicoding.githubuser.data.retrofit

import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.data.response.FollowResponseItem
import com.dicoding.githubuser.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getListFollowers(
        @Path("username") username: String
    ): Call<ArrayList<FollowResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getListFollowing(
        @Path("username") username: String
    ): Call<ArrayList<FollowResponseItem>>
}