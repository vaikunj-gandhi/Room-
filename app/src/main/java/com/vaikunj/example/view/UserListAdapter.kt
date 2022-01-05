package com.vaikunj.example.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vaikunj.example.R
import com.vaikunj.example.response.Data
import kotlinx.android.synthetic.main.item_taskthree.view.*


class UserListAdapter(val context: Activity,
                          val userList: ArrayList<Data?>?,
                          val onItemClick: OnItemClick
) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_taskthree, parent, false)

        return UserListViewHolder(v)
    }

    override fun getItemCount(): Int {

        return userList!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is UserListViewHolder) {
            holder.bind(userList!![position], holder.adapterPosition, onItemClick)
        }
    }

    class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(userList: Data?, adapterPosition: Int, onItemClick: OnItemClick) = with(itemView) {

            name.text=userList?.firstName+" "+userList?.lastName
            email.text=userList?.email
            Glide.with(this)
                .load(userList?.avatar)
                .into(image);
            itemView.setOnClickListener {
                onItemClick.onClicled(adapterPosition, "delete", itemView)
            }
        }
    }

    interface OnItemClick {
        fun onClicled(position: Int, from: String, v: View)

    }
}