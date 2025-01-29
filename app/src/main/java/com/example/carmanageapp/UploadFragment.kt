package com.example.carmanageapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carmanageapp.adapters.ImagePreviewAdapter
import com.example.carmanageapp.databinding.FragmentUploadBinding
import com.example.carmanageapp.models.CarRequest
import com.example.carmanageapp.models.UserRequest
import com.example.carmanageapp.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadFragment : Fragment() {

    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!
    private val carViewModel by viewModels<CarViewModel>()
    private val selectedImages = mutableListOf<Uri>()
    private lateinit var imageAdapter: ImagePreviewAdapter
    private val PICK_IMAGES_REQUEST = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        bindHandlers()
        bindObservers()
    }

    private fun setupRecyclerView() {
        imageAdapter = ImagePreviewAdapter(selectedImages)
        binding.recyclerViewImages.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = imageAdapter
        }
    }

    private fun bindHandlers() {
        binding.btnAddImages.setOnClickListener {
            openGallery()
        }

        binding.btnSubmit.setOnClickListener {
            val carRequest = getCarRequest()

            carViewModel.createCar(carRequest)
        }


    }

    private fun getCarRequest(): CarRequest{
        val title = binding.etTitle.text.toString()
        val description = binding.etDescription.text.toString()
        val tags = binding.etTags.text.toString().split(",").map { it.trim() }
        val imageUrls = selectedImages.map { it.toString() }

        return CarRequest(
            title = title,
            description = description,
            image = imageUrls,
            tags = tags
        )
    }

    private fun bindObservers() {
        carViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    // Navigate back after success
                    requireActivity().onBackPressed()
                }
                is NetworkResult.Error -> {
                    // Show error message
                }
                is NetworkResult.Loading -> {
                    // Show loading state
                }
            }
        })
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Pictures"), PICK_IMAGES_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGES_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.let {
                if (it.clipData != null) {
                    for (i in 0 until it.clipData!!.itemCount) {
                        val imageUri = it.clipData!!.getItemAt(i).uri
                        selectedImages.add(imageUri)
                    }
                } else {
                    it.data?.let { uri -> selectedImages.add(uri) }
                }
                imageAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
