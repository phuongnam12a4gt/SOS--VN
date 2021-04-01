package com.example.sos__vn.screen.Register

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.sos__vn.R
import com.sun.americanroom.utils.addFragment
import com.sun.americanroom.utils.replaceFragment
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.activity?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.menuservice,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerService.adapter = adapter
            }
            spinnerService.onItemSelectedListener = this
        }
    }

    override fun onResume() {
        super.onResume()
        buttonSelectLocation.setOnClickListener {
            replaceFragment(RegisterLocationFragment.newInstance(), R.id.frameLayoutNavigation)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        buttonSelectLocation.visibility = View.GONE
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> buttonSelectLocation.visibility = View.GONE
            1 -> buttonSelectLocation.visibility = View.VISIBLE
            2 -> buttonSelectLocation.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance() = RegisterFragment()
    }
}
