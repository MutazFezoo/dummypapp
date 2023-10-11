package com.example.dummyypapp.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummyypapp.model.UserDataItem
import com.example.dummyypapp.model.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _users = mutableStateOf<List<UserDataItem>>(emptyList())
    val users: State<List<UserDataItem>> = _users

    private val _user = mutableStateOf<UserDataItem?>(null)
    val user: State<UserDataItem?> = _user


    init {
        viewModelScope.launch {
            fetchUsers()
        }
    }

    private suspend fun fetchUsers() {
        try {
            val response = userRepository.getUsers2()
            if (response.isSuccessful) {
                response.body()?.let {
                    _users.value = it.data
                }
            } else {
                Log.d("ERROR", "${response.code()}")
            }

        } catch (t: Throwable) {
            Log.d("ERROR", "${t.message}")

        }
    }

    fun getUserById(id: String) {
        viewModelScope.launch {
            try {
                val response = userRepository.getUserById(id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _user.value = it
                    }
                } else {
                    Log.d("ERROR", "${response.code()}")
                }

            } catch (t: Throwable) {
                Log.d("ERROR", "${t.message}")

            }

        }

    }
}