package com.example.sos__vn.screen.Register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sos__vn.R
import com.sun.americanroom.utils.addFragment

class NavigateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigate)
        addFragment(LoginFragment.newInstance(), R.id.frameLayoutNavigation)
    }
}
