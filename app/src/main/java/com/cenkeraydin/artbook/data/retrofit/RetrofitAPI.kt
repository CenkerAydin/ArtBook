package com.cenkeraydin.artbook.data.retrofit

import com.cenkeraydin.artbook.data.model.ImageResponse
import com.cenkeraydin.artbook.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String =API_KEY
    ): Response<ImageResponse>
}