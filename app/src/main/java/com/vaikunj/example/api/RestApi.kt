package com.vaikunj.example.api

import com.vaikunj.example.response.User
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface RestApi {

    @GET("api/users")
     fun getUser(@Query("page") page: Int? = null): Call<User>

    }
