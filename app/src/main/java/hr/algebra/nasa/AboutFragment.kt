package hr.algebra.nasa

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hr.algebra.nasa.MapFragment
import hr.algebra.nasa.R.*
import hr.algebra.nasa.R.id.map_container
import hr.algebra.nasa.R.layout.fragment_about
import hr.algebra.nasa.databinding.ActivityMapsBinding


/**
 * A simple [Fragment] subclass.
 * Use the [AboutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        // Open fragment
        this.requireActivity().supportFragmentManager
            .beginTransaction().replace(map_container, MapFragment() as Fragment)
            .commit()

        return inflater.inflate(fragment_about, container, false)
    }
}