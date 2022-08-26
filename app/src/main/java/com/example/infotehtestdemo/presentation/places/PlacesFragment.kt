package com.example.infotehtestdemo.presentation.places

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.*
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infotehtestdemo.R
import com.example.infotehtestdemo.databinding.FragmentPlacesBinding
import com.example.infotehtestdemo.presentation.main.MainActivity
import com.example.infotehtestdemo.utils.SpacerItemDecorator
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PlacesFragment : Fragment() {

    companion object {
        fun newInstance() = PlacesFragment()
    }

    private var _binding: FragmentPlacesBinding? = null
    private val viewModel: PlacesViewModel by viewModels()
    private var placesAdapter : PlacesAdapter? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlacesBinding.inflate(inflater, container, false)
        val view = binding.root

        observers()
        initBindUI()
        return view
    }

    private fun setRecyclerInset(view: View){
        ViewCompat.setOnApplyWindowInsetsListener(view) { insetView, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = insets.bottom)
            WindowInsetsCompat.CONSUMED

        }
    }

    private fun initBindUI(){
        binding.placesRecycler.adapter = placesAdapter
        binding.placesRecycler.layoutManager = LinearLayoutManager(context)
        binding.placesRecycler.addItemDecoration(SpacerItemDecorator(5))
        setRecyclerInset(binding.placesRecycler)
        val activity = requireActivity() as MainActivity
        activity.showBackButton(false)
        val toolbar : MaterialToolbar? = activity.findViewById(R.id.topAppBar)
        toolbar?.title = getString(R.string.place)
    }

    private fun observers(){
        viewModel.places.observe(viewLifecycleOwner) {
            placesAdapter = PlacesAdapter(it, requireContext())
            binding.placesRecycler.adapter = placesAdapter
            binding.placesRecycler.layoutManager = LinearLayoutManager(context)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        binding.searchEditText.doAfterTextChanged {
            viewModel.searchPlaces(it.toString())
        }
        lifecycleScope.launchWhenStarted {
            viewModel.error.collectLatest {
                Toast.makeText(this@PlacesFragment.requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

}