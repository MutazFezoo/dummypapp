package com.example.dummyypapp.model.repository

import com.example.dummyypapp.model.User
import com.example.dummyypapp.model.UserDataItem
import com.example.dummyypapp.model.UserResponse
import com.example.dummyypapp.service.UserService
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userService: UserService) {
    suspend fun getUsers(): Response<UserResponse> {
        return userService.getUsersAsync().await()
    }

     suspend fun getUsers2(): Response<UserResponse> = userService.getUsersAsync2()
     suspend fun getUserById(id: String): Response<UserDataItem> = userService.getUserDetailsAsync(id)


/*    suspend fun getUsers(): Response<UserResponse> =
        userService.getUsersAsync().await()*/
}