package com.vaikunj.example.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.vaikunj.example.R
import kotlinx.android.synthetic.main.activity_task.*

class TaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        resultbtn.setOnClickListener {
            if(enterDigitis.text.toString().trim().isNullOrEmpty())
            {
                Toast.makeText(this, "Please Enter digits", Toast.LENGTH_LONG).show()

            }
            else{

                if(result() == 0){
                    cardAns.visibility= View.GONE
                }else{
                    ansTv.text="Ans. is "+result().toString()
                    cardAns.visibility= View.VISIBLE

                }
            }
        }
    }

    private fun result():Int {
        var sum = 0
        var digits= enterDigitis.text.toString().trim().toInt()
        while (digits > 0 || sum > 9) {
            if (digits === 0) {
                digits= sum
                sum = 0
            }
            sum =sum+ digits % 10
            digits /= 10
        }
        return sum

    }
}