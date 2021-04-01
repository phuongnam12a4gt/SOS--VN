package com.example.sos__vn.screen.Register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sos__vn.R
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
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}
