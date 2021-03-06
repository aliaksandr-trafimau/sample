package com.example.andrey_radzkov

import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale


/**
 * @author Radzkov Andrey
 */
class ControlPointsMapFragment : Fragment(), OnMapReadyCallback {
    var mMapView: MapView? = null
    var geocoder: Geocoder? = null
    private lateinit var mMap: GoogleMap

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_control_ponts_map, container, false)
        mMapView = rootView.findViewById(R.id.map)
        mMapView!!.onCreate(savedInstanceState)
        mMapView!!.getMapAsync(this)
        geocoder = Geocoder(context, Locale.getDefault())
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "Control points"
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//        mMap.setMyLocationEnabled(true)}
        // Add a marker in Sydney and move the camera
//TODO: async
        val goretskogo = "ул Горецкого, Минск"
        val sharangovicha = "ул Шаранговича 52, Минск"
        val zhukova = "ул Жукова 29, Минск"
        val minskGoretskogo = getCoordinateByAddress(goretskogo)
        val minskSharangovicha = getCoordinateByAddress(sharangovicha)
        val minskZhukova = getCoordinateByAddress(zhukova)

        if (minskSharangovicha != null) {
            mMap.addMarker(MarkerOptions().position(minskSharangovicha).title(sharangovicha))
        }
        if (minskZhukova != null) {
            mMap.addMarker(MarkerOptions().position(minskZhukova).title(zhukova))
        }
        if (minskGoretskogo != null) {
            mMap.addMarker(MarkerOptions().position(minskGoretskogo).title(goretskogo))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(minskGoretskogo, 11.0f))
        }

        mMapView!!.onResume()
    }

    fun getCoordinateByAddress(address: String): LatLng? {
        try {
            val locationsByAddress = geocoder!!.getFromLocationName(address, 1)
            if (locationsByAddress != null && locationsByAddress.isNotEmpty()) {
                val foundAddress = locationsByAddress[0]
                val addressCoordinate = LatLng(foundAddress.latitude, foundAddress.longitude)
                return addressCoordinate
            }
        } catch (e: Exception) {
            println("no internet")
        }
        return null
    }
}