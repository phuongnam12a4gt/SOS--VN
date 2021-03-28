package com.example.sos__vn.utils

import android.app.Application

class MyApplication :Application()
{
    override fun onCreate() {
        super.onCreate()
        DataLocalManager.init(applicationContext)
    }
}