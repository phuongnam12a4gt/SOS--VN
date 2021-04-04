package com.example.sos__vn.screen.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sos__vn.R
import com.example.sos__vn.model.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.libraries.places.api.net.PlacesClient

class SearchPlacesFragment : Fragment(),
    OnMapReadyCallback {

    private var map: GoogleMap? = null
    private var placesClient: PlacesClient? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationPermissionGranted: Boolean = false
    private val listLocationSelect = mutableListOf<Location>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_places, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mapFragment = childFragmentManager
            ?.findFragmentById(R.id.fragmentSearchPlaces) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        map = p0
    }

    companion object {
        fun newInstances() = SearchPlacesFragment()
    }
}
