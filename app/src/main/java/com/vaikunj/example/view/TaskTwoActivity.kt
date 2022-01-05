package com.vaikunj.example.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaikunj.example.R
import com.vaikunj.example.viewmodel.UserRoomViewModel
import com.vaikunj.example.viewmodel.UserRoomViewModelFactory
import com.vaikunj.example.db.UserRoomRepository
import com.vaikunj.example.databinding.ActivityTaskTwoBinding
import com.vaikunj.example.db.UserRoomModel
import com.vaikunj.example.db.UserRoomDatabase

class TaskTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskTwoBinding
    private lateinit var userRoomViewModel: UserRoomViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_two)
        val dao = UserRoomDatabase.getInstance(application).userRoomDAO
        val repository = UserRoomRepository(dao)
        val factory = UserRoomViewModelFactory(repository)
        userRoomViewModel = ViewModelProvider(this, factory).get(UserRoomViewModel::class.java)
        binding.myViewModel = userRoomViewModel
        binding.lifecycleOwner = this

        userRoomViewModel.message.observe(this, Observer {
           // it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it.getContentIfNotHandled().toString(), Toast.LENGTH_LONG).show()
           // }
        })

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter { selectedItem: UserRoomModel -> listItemClicked(selectedItem) }
        binding.recyclerView.adapter = adapter
        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        userRoomViewModel.getSavedSubscribers().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(userRoomModel: UserRoomModel) {
        userRoomViewModel.initUpdateAndDelete(userRoomModel)
    }


}