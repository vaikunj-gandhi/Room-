package com.vaikunj.example.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vaikunj.example.R
import com.vaikunj.example.databinding.ListItemBinding
import com.vaikunj.example.db.UserRoomModel

class UserAdapter(private val clickListener: (UserRoomModel) -> Unit) :
        RecyclerView.Adapter<MyViewHolder>() {
    private val subscribersList = ArrayList<UserRoomModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position], clickListener)
    }

    fun setList(userRoomModels: List<UserRoomModel>) {
        subscribersList.clear()
        subscribersList.addAll(userRoomModels)

    }

}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userRoomModel: UserRoomModel, clickListener: (UserRoomModel) -> Unit) {
        binding.nameTextView.text = userRoomModel.name
        binding.emailTextView.text = userRoomModel.email
        binding.listItemLayout.setOnClickListener {
            clickListener(userRoomModel)
        }
    }
}