@file:Suppress("DEPRECATION")

package com.sun.americanroom.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


fun AppCompatActivity.addFragment(fragment: Fragment, id: Int) {
    supportFragmentManager.inTransaction { add(id, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, id: Int) {
    supportFragmentManager.inTransaction { replace(id, fragment).addToBackStack(null) }
}

fun AppCompatActivity.removeFragment(fragment: Fragment) {
    supportFragmentManager.inTransaction { remove(fragment) }
}

fun Fragment.addFragment(fragment: Fragment, id: Int) {
    fragmentManager?.inTransaction { add(id, fragment).addToBackStack(null) }
}

fun Fragment.replaceFragment(fragment: Fragment, id: Int) {
    fragmentManager?.inTransaction { replace(id, fragment).addToBackStack(null) }
}

fun Fragment.removeFragment(fragment: Fragment) {
    fragmentManager?.inTransaction { remove(fragment) }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}
