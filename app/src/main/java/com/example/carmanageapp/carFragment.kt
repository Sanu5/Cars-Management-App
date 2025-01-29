package com.example.carmanageapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.carmanageapp.CarViewModel
import com.example.carmanageapp.R
import com.example.carmanageapp.databinding.FragmentCarBinding
import com.example.carmanageapp.models.CarRequest
import com.example.carmanageapp.models.CarResponse
import com.example.carmanageapp.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class carFragment : Fragment() {

    private var _binding: FragmentCarBinding? = null
    private val binding get() = _binding!!
    private val carViewModel by activityViewModels<CarViewModel>()
    private var car: CarResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun bindObservers() {
        carViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }
                is NetworkResult.Error -> {
                    // Handle error (Show Toast or Snackbar)
                }
                is NetworkResult.Loading -> {
                    // Show Loading Indicator (if needed)
                }
            }
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
