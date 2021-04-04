package com.example.sos__vn.screen.Register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sos__vn.R
import com.example.sos__vn.screen.Home.HomeActivity
import com.sun.americanroom.utils.replaceFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onResume() {
        textViewRegister.setOnClickListener {
            replaceFragment(RegisterFragment.newInstance(), R.id.frameLayoutNavigation)
        }
        super.onResume()
        buttonLogin.setOnClickListener {
            navigateHome()
        }
    }

    private fun navigateHome() {
        var name = textInputName.text.toString()
        var age = textInputPass.text.toString()
        if (name == "nam" && age == "123456789") {
            var intent = Intent(activity, HomeActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("age", age)
            startActivity(intent)
        } else {
            textViewNotify.visibility = View.VISIBLE
            textViewNotify.text = "Wrong name or wrong password"
        }
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}
