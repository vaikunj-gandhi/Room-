package com.vaikunj.example.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.vaikunj.example.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        materialButtonTask1.setOnClickListener(this)
        materialButtonTask2.setOnClickListener(this)
        materialButtonTask3.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.materialButtonTask1 ->{
                   Intent(this@MainActivity, TaskActivity::class.java).apply {
                       startActivity(this)
                   }

            }
            R.id.materialButtonTask2 ->{
                   Intent(this@MainActivity, TaskTwoActivity::class.java).apply {
                       startActivity(this)
                   }

            }
            R.id.materialButtonTask3 ->{
                   Intent(this@MainActivity, TaskThreeActivity::class.java).apply {
                       startActivity(this)
                   }

            }
        }
    }

}