package com.cenkeraydin.artbook.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.cenkeraydin.artbook.R
import com.cenkeraydin.artbook.databinding.FragmentArtBinding
import com.cenkeraydin.artbook.databinding.FragmentArtDetailsBinding
import com.cenkeraydin.artbook.presentation.viewModel.ArtViewModel
import com.cenkeraydin.artbook.util.Resource
import com.cenkeraydin.artbook.util.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailsFragment @Inject constructor(
    private val glide: RequestManager
): Fragment() {

    private lateinit var binding: FragmentArtDetailsBinding
    private lateinit var viewModel: ArtViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]
        binding.addImageIv.setOnClickListener {
            findNavController().navigate(R.id.action_artDetailsFragment_to_imageApiFragment)
        }
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        binding.saveBtn.setOnClickListener {
            viewModel.makeArt(
                binding.nameEt.text.toString(),
                binding.artistEt.text.toString(),
                binding.yearEt.text.toString()
            )
        }

        viewModel.selectedImageUrl.observe(viewLifecycleOwner) {
            glide.load(it).into(binding.addImageIv)
        }
        viewModel.insertArtMessage.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {

                }
            }
        }
    }
}