package com.example.intermediatedua.presentation.mapsScreen

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.intermediatedua.R
import com.example.intermediatedua.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        lifecycleScope.launch {
            when(val response = mapsViewModel.getLatLong() ){
                is MapsViewModel.MapsResult.Success -> {
                    val story = response.data.listStory
                    story.forEach{ listStoryItem ->
                        val latLng = LatLng(listStoryItem.lat as Double, listStoryItem.lon as Double)
                        googleMap.addMarker(MarkerOptions().position(latLng).title(listStoryItem.name))
                        boundsBuilder.include(latLng)
                    }
                    val bounds: LatLngBounds = boundsBuilder.build()
                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            bounds,
                            resources.displayMetrics.widthPixels,
                            resources.displayMetrics.heightPixels,
                            300
                        )
                    )

                }
                is MapsViewModel.MapsResult.Error -> {

                }
            }
        }

    }

    private var _binding : FragmentMapsBinding? = null
    private val binding : FragmentMapsBinding
        get() = _binding!!
    private val mapsViewModel by viewModels<MapsViewModel>()
    private val boundsBuilder  = LatLngBounds.builder()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}