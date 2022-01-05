package com.vaikunj.example.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vaikunj.example.R
import com.vaikunj.example.response.Data
import com.vaikunj.example.util.MyUtils
import com.vaikunj.example.viewmodel.UserModel
import kotlinx.android.synthetic.main.common_recyclerview.*
import kotlinx.android.synthetic.main.nodafound.*
import kotlinx.android.synthetic.main.nointernetconnection.*
import kotlinx.android.synthetic.main.nointernetconnection.view.*
import kotlinx.android.synthetic.main.progressbar.*

class TaskThreeActivity : AppCompatActivity(),View.OnClickListener {

    private var y: Int = 0
    var pageNo = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var firstVisibleItemPosition: Int = 0
    private var isLoading = false
    private var isLastpage = false
    var userListData: ArrayList<Data?>? = null
    var userAdapter: UserListAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private lateinit var mUserModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_three2)
        setupViewModel()
        if (MyUtils.isInternetAvailable(this@TaskThreeActivity)) {
            setupUI()
        } else {
            nointernetMainRelativelayout.nointernetImageview.setImageDrawable(getDrawable(
                R.drawable.ic_signal_wifi_off_black_24dp
            ))
            nointernetMainRelativelayout.nointernettextview.text = getString(R.string.error_common_network)
        }

    }

    private fun setupObserver() {
        ll_no_data_found.visibility = View.GONE
        nointernetMainRelativelayout.visibility = View.GONE

        if (pageNo == 0) {
            relativeprogressBar.visibility = View.VISIBLE
            userListData?.clear()
            userAdapter?.notifyDataSetChanged()
        } else {
            relativeprogressBar.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE
            userListData!!.add(null)
            userAdapter?.notifyItemInserted(userListData!!.size - 1)
        }

        mUserModel.getUser(this@TaskThreeActivity!!,pageNo.toString())
            .observe(this@TaskThreeActivity, { userlistPojo ->
                if (userlistPojo != null )
                {
                    isLoading = false
                    ll_no_data_found.visibility = View.GONE
                    nointernetMainRelativelayout.visibility = View.GONE
                    relativeprogressBar.visibility = View.GONE

                    if (pageNo > 0) {
                        userListData?.removeAt(userListData!!.size - 1)
                        userAdapter?.notifyItemRemoved(userListData!!.size)
                    }
                        recyclerview.visibility = View.VISIBLE
                        if (pageNo == 0) {
                            userListData?.clear()
                        }
                    userlistPojo.data?.let { userListData!!.addAll(it) }
                        userAdapter?.notifyDataSetChanged()
                        pageNo += 1
                        if (userlistPojo.data?.size!! < 10) {
                            isLastpage = true
                        }

                        if (!userlistPojo.data.isNullOrEmpty()) {
                            if (userlistPojo.data.isNullOrEmpty()) {
                                ll_no_data_found.visibility = View.VISIBLE
                                recyclerview.visibility = View.GONE
                            } else {
                                ll_no_data_found.visibility = View.GONE
                                recyclerview.visibility = View.VISIBLE
                            }


                        } else {
                            ll_no_data_found.visibility = View.VISIBLE
                            recyclerview.visibility = View.GONE
                        }



                }
                else
                {

                    relativeprogressBar.visibility = View.GONE
                    try {
                        nointernetMainRelativelayout.visibility = View.VISIBLE
                        if (MyUtils.isInternetAvailable(this@TaskThreeActivity)) {
                            nointernetMainRelativelayout.nointernetImageview.setImageDrawable(getDrawable(
                                R.drawable.ic_warning_black_24dp
                            ))
                            nointernetMainRelativelayout.nointernettextview.text = getString(R.string.error_crash_error_message)
                        } else {
                            nointernetMainRelativelayout.nointernetImageview.setImageDrawable(getDrawable(
                                R.drawable.ic_signal_wifi_off_black_24dp
                            ))
                            nointernetMainRelativelayout.nointernettextview.text = getString(R.string.error_common_network)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            })
    }

    private fun setupViewModel() {

        mUserModel = ViewModelProvider(this@TaskThreeActivity)[UserModel::class.java]

    }

    private fun setupUI() {
        btnRetry.setOnClickListener(this)
        linearLayoutManager = LinearLayoutManager(this@TaskThreeActivity)
        if (userListData == null) {
            userListData = ArrayList()
            userAdapter = UserListAdapter(this@TaskThreeActivity, userListData, object : UserListAdapter.OnItemClick {
                override fun onClicled(position: Int, from: String, v: View) {



                }
            })

            recyclerview.layoutManager = linearLayoutManager
            recyclerview.adapter = userAdapter

            setupObserver()

        }

        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                y = dy
                visibleItemCount = linearLayoutManager!!.childCount
                totalItemCount = linearLayoutManager!!.itemCount
                firstVisibleItemPosition = linearLayoutManager!!.findFirstVisibleItemPosition()
                if (!isLoading && !isLastpage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= 10
                    ) {

                        isLoading = true
                        setupObserver()
                    }
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btnRetry ->{
                setupObserver()
            }
        }
    }

}