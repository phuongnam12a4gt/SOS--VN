package com.example.sos__vn.screen.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.sos__vn.R
import com.google.android.material.navigation.NavigationView
import com.sun.americanroom.utils.addFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        supportActionBar?.setTitle("Show surrounding services")
        addFragment(SearchPlacesFragment.newInstances(), R.id.content_frame)
        nav_view.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                item.setChecked(true)
                drawer_layout.closeDrawers()
                return true
            }
        }
        )
        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerClosed(drawerView: View) {
                Toast.makeText(this@HomeActivity, "Dong ngan keo", Toast.LENGTH_LONG).show()
            }

            override fun onDrawerOpened(drawerView: View) {
                Toast.makeText(this@HomeActivity, "Trang thai mo ngan keo", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}