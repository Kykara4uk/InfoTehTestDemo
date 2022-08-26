package com.example.infotehtestdemo.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.infotehtestdemo.R
import com.example.infotehtestdemo.databinding.FragmentDetailBinding
import com.example.infotehtestdemo.domain.models.CurrentWeather
import com.example.infotehtestdemo.presentation.main.MainActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.roundToInt


@AndroidEntryPoint
class DetailFragment : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private var _binding: FragmentDetailBinding? = null
    private val viewModel: DetailViewModel by viewModels()
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        initBindUI()
        observers()

        return view
    }

    private fun bindWeather(weather: CurrentWeather){
        binding.tempTV.text = weather.temp.roundToInt().toString()
        binding.descriptionTV.text = weather.description
        binding.minTempTV.text = weather.tempMin.roundToInt().toString()
        binding.maxTempTV.text = weather.tempMax.roundToInt().toString()
        binding.humidityTV.text = weather.humidity.toString()
        binding.windSpeedTV.text = weather.windSpeed.toString()

        binding.weatherGroup.isVisible = true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        viewModel.place.observe(viewLifecycleOwner){
            val position = LatLng(it.coordinates.lat, it.coordinates.lon)
            googleMap.addMarker(
                MarkerOptions()
                    .position(position)
                    .title(it.name)
            )
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 8F))
        }


    }

    private fun initBindUI(){
        viewModel.setPlace(args.place)


        val activity = requireActivity() as MainActivity
        activity.showBackButton(true)

        val toolbar : MaterialToolbar? = activity.findViewById(R.id.topAppBar)
        toolbar?.title = args.place.name

        val mapFragment = childFragmentManager.findFragmentById(binding.mapFragment.id) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun observers(){
        viewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }

        lifecycleScope.launchWhenStarted {
            viewModel.error.collectLatest {
                Toast.makeText(this@DetailFragment.requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.weather.observe(viewLifecycleOwner){
            bindWeather(it)
        }
    }

}