package com.example.sos__vn.utils

import android.content.Context

class DataLocalManager {
    lateinit var mysharedpreference: MySharedPreference

    companion object {
        const val PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL"
        private var instance: DataLocalManager? = null

        fun init(context: Context) {
            this.getInstance().mysharedpreference = MySharedPreference(context)
        }

        fun setFirst(isFirst: Boolean) {
            DataLocalManager.getInstance().mysharedpreference.putBooleanValue(
                PREF_FIRST_INSTALL,
                isFirst
            )
        }

        fun getFirstInstall(): Boolean {
            return DataLocalManager.getInstance().mysharedpreference.getBooleanValue(
                PREF_FIRST_INSTALL
            )
        }

        fun getInstance(): DataLocalManager {
            if (instance == null) {
                instance = DataLocalManager()
            }
            return instance as DataLocalManager
        }
    }
}