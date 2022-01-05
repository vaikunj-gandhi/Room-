package com.vaikunj.example.viewmodel

import android.util.Patterns
import androidx.lifecycle.*
import com.vaikunj.example.util.Event
import com.vaikunj.example.db.UserRoomModel
import com.vaikunj.example.db.UserRoomRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserRoomViewModel(private val repository: UserRoomRepository) : ViewModel() {
    private var isUpdateOrDelete = false
    private lateinit var userRoomModelToUpdateOrDelete: UserRoomModel
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun initUpdateAndDelete(userRoomModel: UserRoomModel) {
        inputName.value = userRoomModel.name
        inputEmail.value = userRoomModel.email
        isUpdateOrDelete = true
        userRoomModelToUpdateOrDelete = userRoomModel
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    fun saveOrUpdate() {
        if (inputName.value == null) {
            statusMessage.value = Event("Please enter  name")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter  email address")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter a correct email address")
        } else {
            if (isUpdateOrDelete) {
                userRoomModelToUpdateOrDelete.name = inputName.value!!
                userRoomModelToUpdateOrDelete.email = inputEmail.value!!
                updateSubscriber(userRoomModelToUpdateOrDelete)
            } else {
                val name = inputName.value!!
                val email = inputEmail.value!!
                insertSubscriber(UserRoomModel(0, name, email))
                inputName.value = ""
                inputEmail.value = ""
            }
        }
    }

    private fun insertSubscriber(userRoomModel: UserRoomModel) = viewModelScope.launch {
        val newRowId = repository.insert(userRoomModel)
        if (newRowId > -1) {
            statusMessage.value = Event("Inserted Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }


    private fun updateSubscriber(userRoomModel: UserRoomModel) = viewModelScope.launch {
        val noOfRows = repository.update(userRoomModel)
        if (noOfRows > 0) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRows Row Updated Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun getSavedSubscribers() = liveData {
        repository.subscribers.collect {
            emit(it)
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            deleteSubscriber(userRoomModelToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    private fun deleteSubscriber(userRoomModel: UserRoomModel) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(userRoomModel)
        if (noOfRowsDeleted > 0) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    private fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Subscribers Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }
}