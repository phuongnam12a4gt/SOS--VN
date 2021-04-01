package com.example.sos__vn.screen.Register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sos__vn.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places

class RegisterLocationFragment : Fragment(),
    OnMapReadyCallback,
    GoogleMap.OnMarkerDragListener {

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var mGeoDataClient = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mapFragment = childFragmentManager
            ?.findFragmentById(R.id.mapViewDragRetrieveLocation) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        p0?.let {
            val perthLocation = LatLng(-30.1, 120.86)
            val perth = p0.addMarker(
                MarkerOptions()
                    .position(perthLocation)
                    .draggable(true)
                    .title("Melbourne")
                    .snippet("Population: 4,137,400")
            )
            p0.moveCamera(CameraUpdateFactory.newLatLng(perthLocation))
            perth.isDraggable = true
            p0.setOnMarkerDragListener(this)
        }
    }

    override fun onMarkerDragEnd(p0: Marker?) {
        Log.i("TAG", p0?.position.toString())
    }

    override fun onMarkerDragStart(p0: Marker?) {
        Log.i("TAG", "DragStart")
    }

    override fun onMarkerDrag(p0: Marker?) {
        Log.i("TAG", "DragEnd")
    }

    companion object {
        fun newInstance() = RegisterLocationFragment()
    }
}
