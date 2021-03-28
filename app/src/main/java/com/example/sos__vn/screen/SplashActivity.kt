package com.example.sos__vn.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.sos__vn.R
import com.example.sos__vn.utils.AppUtils
import com.example.sos__vn.utils.DataLocalManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (!DataLocalManager.getFirstInstall()) {
            DataLocalManager.setFirst(true)
            var intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
            startActivity(intent)
        } else {
            loadData()
        }
    }

     fun loadData() {
        if (AppUtils.isNetWorkAvailable(this)) {
            Handler().postDelayed(object : Runnable {
                override fun run() {
                    var intent = Intent(this@SplashActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }, 3000)
            Toast.makeText(this, "NetWork Connect ", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "NetWork DisConnect ", Toast.LENGTH_LONG).show()
        }
    }
}
