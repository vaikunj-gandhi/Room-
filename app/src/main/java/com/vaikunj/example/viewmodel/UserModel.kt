package com.vaikunj.example.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vaikunj.example.api.RestCallback
import com.vaikunj.example.api.RestClient
import com.vaikunj.example.response.User

import retrofit2.Response

class UserModel : ViewModel() {

    lateinit var languageresponse: LiveData<User>
    lateinit var mContext: Context
    var page: String?=null

    fun getUser(
            context: Context,
            page: String
    ): LiveData<User> {
        this.page = page

        this.mContext = context

        languageresponse = getUserApi()

        return languageresponse
    }

    private fun getUserApi(): LiveData<User> {
        val data = MutableLiveData<User>()

        var call = RestClient.get()!!.getUser(page?.toInt()!!)
        call!!.enqueue(object : RestCallback<User>(mContext) {
            override fun Success(response: Response<User>) {
                data.value = response.body()
            }

            override fun failure() {
                try {
                    data.value = null!!
                } catch (e: Exception) {
                }
            }

        })

        return data
    }

}