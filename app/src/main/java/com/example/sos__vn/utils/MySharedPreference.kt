package com.example.sos__vn.utils

import android.content.Context

class MySharedPreference(private var context: Context) {
    fun putBooleanValue(key: String, value: Boolean) {
        var sharedpreference =
            context.getSharedPreferences(MY_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        var editor = sharedpreference.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBooleanValue(key: String): Boolean {
        var sharedpreference =
            context.getSharedPreferences(MY_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        return sharedpreference.getBoolean(key, false)
    }

    companion object {
        const val MY_SHARED_PREFERENCE = "MY_SHARED_PREFERENCE"
    }
}
