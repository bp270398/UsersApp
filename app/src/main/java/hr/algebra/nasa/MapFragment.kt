package hr.algebra.nasa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hr.algebra.nasa.R.layout


class MapFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(layout.fragment_map, container, false)
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        supportMapFragment.getMapAsync { googleMap ->

            val markerOptions = MarkerOptions()
            val latLng = LatLng(45.81048657655119, 15.941879900109452)
            markerOptions.position(latLng)
            markerOptions.title(getString(R.string.algebra))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
            googleMap.addMarker(markerOptions)
        }
        return view
    }
}