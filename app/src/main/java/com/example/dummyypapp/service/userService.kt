package com.example.dummyypapp.service

import com.example.dummyypapp.model.User
import com.example.dummyypapp.model.UserDataItem
import com.example.dummyypapp.model.UserResponse
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

//import retrofit2.http.GET
//import retrofit2.http.Headers

interface UserService {

    //@Headers("Accept:application/json")
   // @Headers("app-id:652456ff1a3996c04f3b560c")
    @GET("user")
    fun getUsersAsync(@Header("app-id") id: String? = "652456ff1a3996c04f3b560c"): Deferred<Response<UserResponse>>

   // @Headers("app-id:652456ff1a3996c04f3b560c")
    @GET("user")
    suspend fun getUsersAsync2(/*@Header("app-id") id: String? = "652456ff1a3996c04f3b560c"*/): Response<UserResponse>

    @GET("user/{id}")
    suspend fun getUserDetailsAsync(@Path("id") id: String): Response<UserDataItem>

/*
    @Headers("Accept:application/json")
    @GET("user")
    suspend fun getUsers(): List<User>
*/


}