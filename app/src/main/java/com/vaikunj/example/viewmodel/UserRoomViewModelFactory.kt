package com.vaikunj.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vaikunj.example.db.UserRoomRepository

class UserRoomViewModelFactory(
        private val repository: UserRoomRepository
        ):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
     if(modelClass.isAssignableFrom(UserRoomViewModel::class.java)){
         return UserRoomViewModel(repository) as T
     }
        throw IllegalArgumentException("Unknown View Model class")
    }

}